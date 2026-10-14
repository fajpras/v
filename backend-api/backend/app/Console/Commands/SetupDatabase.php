<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use PDO;
use Throwable;

class SetupDatabase extends Command
{
    protected $signature = 'ven:setup-db';
    protected $description = 'Membuat database, user, dan role MySQL sesuai .env';

    public function handle(): int
    {
        $host = env('DB_HOST', '127.0.0.1');
        $host = $host === 'localhost' ? '127.0.0.1' : $host; // paksa TCP
        $port = env('DB_PORT', '3306');
        $db   = env('DB_DATABASE');
        $ch   = env('VEN_DB_CLIENT_HOST', 'localhost');
        $root = env('MYSQL_ROOT_USER', 'root');

        if (! $db || ! preg_match('/^[A-Za-z0-9_]+$/', $db)) {
            $this->error('DB_DATABASE kosong atau berisi karakter tidak valid.');
            return self::FAILURE;
        }

        $akun = [];
        foreach (['APP', 'USER', 'ADMIN', 'DBA'] as $k) {
            $u = env("DB_{$k}_USERNAME");
            $p = env("DB_{$k}_PASSWORD");
            if (! $u || ! $p) {
                $this->error("DB_{$k}_USERNAME / DB_{$k}_PASSWORD belum diisi di .env");
                return self::FAILURE;
            }
            $akun[$k] = [$u, $p];
        }

        $rootPass = (string) $this->secret("Password MySQL untuk '{$root}' (kosongkan jika tidak ada)");

        try {
            $pdo = new PDO("mysql:host={$host};port={$port};charset=utf8mb4", $root, $rootPass, [
                PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            ]);

            $q = fn (string $v) => $pdo->quote($v);
            $h = $q($ch);
            $acc = fn (string $k) => $q($akun[$k][0]).'@'.$h;

            $sql = [
                "CREATE DATABASE IF NOT EXISTS `{$db}` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
                "CREATE ROLE IF NOT EXISTS 'admin_role', 'user_role'",
            ];

            foreach ($akun as $k => [$u, $p]) {
                $sql[] = "CREATE USER IF NOT EXISTS ".$acc($k)." IDENTIFIED BY ".$q($p);
                $sql[] = "ALTER USER ".$acc($k)." IDENTIFIED BY ".$q($p);
            }

            $sql[] = "GRANT ALL PRIVILEGES ON `{$db}`.* TO ".$acc('DBA')." WITH GRANT OPTION";
            $sql[] = "GRANT SELECT, INSERT, UPDATE ON `{$db}`.* TO 'user_role'";
            $sql[] = "GRANT SELECT, INSERT, UPDATE, DELETE ON `{$db}`.* TO 'admin_role'";
            $sql[] = "GRANT SELECT, INSERT, UPDATE, DELETE ON `{$db}`.* TO ".$acc('APP');
            $sql[] = "GRANT 'user_role' TO ".$acc('USER');
            $sql[] = "GRANT 'admin_role' TO ".$acc('ADMIN');
            $sql[] = "SET DEFAULT ROLE 'user_role' TO ".$acc('USER');
            $sql[] = "SET DEFAULT ROLE 'admin_role' TO ".$acc('ADMIN');
            $sql[] = "FLUSH PRIVILEGES";

            foreach ($sql as $s) {
                $pdo->exec($s);
            }
        } catch (Throwable $e) {
            $this->error('Gagal: '.$e->getMessage());
            return self::FAILURE;
        }

        $this->info('Selesai. Lanjut: php artisan migrate --database=mysql_dba');
        return self::SUCCESS;
    }
}