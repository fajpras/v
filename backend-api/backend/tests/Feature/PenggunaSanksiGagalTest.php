<?php

namespace Tests\Feature;

use App\Models\Pengguna;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Tests\TestCase;

class PenggunaSanksiGagalTest extends TestCase
{
    protected function setUp(): void
    {
        parent::setUp();
        $this->assertSame(
            'ven_testing',
            DB::connection('mysql')->getDatabaseName(),
            'Test harus memakai database ven_testing, bukan database aplikasi.'
        );
    }

    public function test_gagal_memberikan_sanksi_pada_akun_admin(): void
    {
        $admin1 = Pengguna::updateOrCreate(
            ['email' => 'admin@dummy.test'],
            [
                'nama'           => 'Admin Uji',
                'nama_panggilan' => 'adminuji',
                'kata_sandi'     => Hash::make('password123'),
                'role'           => 'admin',
                'status'         => 'aktif',
            ]
        );

        $admin2 = Pengguna::updateOrCreate(
            ['email' => 'admin2@dummy.test'],
            [
                'nama'           => 'Admin Dua Uji',
                'nama_panggilan' => 'admin2uji',
                'kata_sandi'     => Hash::make('password123'),
                'role'           => 'admin',
                'status'         => 'aktif',
            ]
        );

        DB::connection('mysql')->table('sanksi_akun')
            ->where('id_pengguna', $admin2->id_pengguna)->delete();

        $payload = [
            'jenis_sanksi' => 'diblokir',
            'alasan'       => 'Uji coba sanksi sesama admin',
            'mulai'        => now()->toDateTimeString(),
            'permanen'     => true,
        ];
        $response = $this->actingAs($admin1)
            ->postJson("/api/admin/pengguna/{$admin2->id_pengguna}/sanksi", $payload);

        $response->assertStatus(403);

        $this->assertDatabaseMissing('sanksi_akun', [
            'id_pengguna' => $admin2->id_pengguna,
        ], 'mysql');

        $this->assertDatabaseHas('pengguna', [
            'id_pengguna' => $admin2->id_pengguna,
            'status'      => 'aktif',
        ], 'mysql');

    }
}