<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        $hasIdEntitas = Schema::hasColumn('log_aktifitas', 'id_entitas');
        $hasJenis = Schema::hasColumn('log_aktifitas', 'jenis');

        if (! $hasIdEntitas && $hasJenis) {
            return;
        }

        Schema::table('log_aktifitas', function (Blueprint $table) use ($hasIdEntitas, $hasJenis) {
            if ($hasIdEntitas) {
                $table->dropColumn('id_entitas');
            }

            if (! $hasJenis) {
                $table->enum('jenis', ['pameran', 'karya', 'profil', 'pengguna'])->nullable();
            }
        });
    }

    public function down(): void
    {
        $hasIdEntitas = Schema::hasColumn('log_aktifitas', 'id_entitas');
        $hasJenis = Schema::hasColumn('log_aktifitas', 'jenis');

        if ($hasIdEntitas && ! $hasJenis) {
            return;
        }

        Schema::table('log_aktifitas', function (Blueprint $table) use ($hasIdEntitas, $hasJenis) {
            if ($hasJenis) {
                $table->dropColumn('jenis');
            }

            if (! $hasIdEntitas) {
                $table->uuid('id_entitas')->nullable();
            }
        });
    }
};
