package com.praktikum.whitebox.service;

import com.praktikum.whitebox.model.Produk;
import com.praktikum.whitebox.repository.RepositoryProduk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Service Inventaris dengan Mocking")
public class ServiceInventarisTest {
    @Mock
    private RepositoryProduk mockRepositoryProduk;
    private ServiceInventaris serviceInventaris;
    private Produk produkTest;
    @BeforeEach
    void setUp() {
        serviceInventaris = new ServiceInventaris(mockRepositoryProduk);
        produkTest = new Produk("PROD001", "Laptop Gaming", "Elektronik", 15000000, 10, 5);
    }

    @Test
    @DisplayName("Tambah produk berhasil - semua kondisi valid")
    void testTambahProdukBerhasil() {
        // Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        when(mockRepositoryProduk.simpan(produkTest)).thenReturn(true);
        // Act
        boolean hasil = serviceInventaris.tambahProduk(produkTest);
        // Assert
        assertTrue(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD001");
        verify(mockRepositoryProduk).simpan(produkTest);
    }

    @Test
    @DisplayName("Tambah produk gagal - produk sudah ada")
    void testTambahProdukGagalSudahAda() {
        // Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        // Act
        boolean hasil = serviceInventaris.tambahProduk(produkTest);
        // Assert
        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD001");
        verify(mockRepositoryProduk, never()).simpan(any(Produk.class));
    }

    @Test
    @DisplayName("Keluar stok berhasil - stok mencukupi")
    void testKeluarStokBerhasil() {
        // Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001", 5)).thenReturn(true);
        // Act
        boolean hasil = serviceInventaris.keluarStok("PROD001", 5);
        // Assert
        assertTrue(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 5);
    }

    @Test
    @DisplayName("Keluar stok gagal - stok tidak mencukupi")
    void testKeluarStokGagalStokTidakMencukupi() {
        // Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        // Act
        boolean hasil = serviceInventaris.keluarStok("PROD001", 15);
        // Assert
        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).updateStok(anyString(),
                anyInt());
    }

    @Test
    @DisplayName("Hitung total nilai inventaris")
    void testHitungTotalNilaiInventaris() {
        // Arrange
        Produk produk1 = new Produk("PROD001", "Laptop", "Elektronik", 10000000, 2, 1);
        Produk produk2 = new Produk("PROD002", "Mouse", "Elektronik", 500000, 5, 2);
        Produk produkNonAktif = new Produk("PROD003", "Keyboard", "Elektronik", 300000, 3, 1);
        produkNonAktif.setAktif(false);
        List<Produk> semuaProduk = Arrays.asList(produk1, produk2, produkNonAktif);
        when(mockRepositoryProduk.cariSemua()).thenReturn(semuaProduk);
        // Act
        double totalNilai = serviceInventaris.hitungTotalNilaiInventaris();
        // Assert
        double expected = (10000000 * 2) + (500000 * 5); // hanya produk aktif
        assertEquals(expected, totalNilai, 0.001);
        verify(mockRepositoryProduk).cariSemua();
    }

    @Test
    @DisplayName("Get produk stok menipis")
    void testGetProdukStokMenipis() {
        // Arrange
        Produk produkStokAman = new Produk("PROD001", "Laptop", "Elektronik", 10000000, 10, 5);
        Produk produkStokMenipis = new Produk("PROD002", "Mouse", "Elektronik", 500000, 3, 5);
        List<Produk> produkMenipis = Collections.singletonList(produkStokMenipis);
        when(mockRepositoryProduk.cariProdukStokMenipis()).thenReturn(produkMenipis);

        // Act
        List<Produk> hasil = serviceInventaris.getProdukStokMenipis();

        // Assert
        assertEquals(1, hasil.size());
        assertEquals("PROD002", hasil.get(0).getKode());
        verify(mockRepositoryProduk).cariProdukStokMenipis();
    }

    // ========== TEST CASES TAMBAHAN ==========

    @Test
    @DisplayName("Test tambah produk dengan kode kosong")
    void testTambahProdukKodeKosong() {
        Produk produk = new Produk("", "Laptop Gaming", "Elektronik",
                15000000, 10, 5);

        boolean hasil = serviceInventaris.tambahProduk(produk);

        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).simpan(any());
    }

    @Test
    @DisplayName("Test tambah produk dengan nama terlalu pendek")
    void testTambahProdukNamaTerlaluPendek() {
        Produk produk = new Produk("PROD010", "AB", "Elektronik",
                15000000, 10, 5);

        boolean hasil = serviceInventaris.tambahProduk(produk);

        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).simpan(any());
    }

    @Test
    @DisplayName("Test hapus produk dengan kode null")
    void testHapusProdukKodeNull() {
        boolean hasil = serviceInventaris.hapusProduk(null);

        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).hapus(anyString());
    }

    @Test
    @DisplayName("Test hapus produk dengan kode kosong")
    void testHapusProdukKodeKosong() {
        boolean hasil = serviceInventaris.hapusProduk("");

        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).hapus(anyString());
    }

    @Test
    @DisplayName("Test cari produk by kode tidak ditemukan")
    void testCariProdukByKodeTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD999")).thenReturn(Optional.empty());

        Optional<Produk> hasil = serviceInventaris.cariProdukByKode("PROD999");

        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Test cari produk by nama dengan hasil kosong")
    void testCariProdukByNamaKosong() {
        when(mockRepositoryProduk.cariByNama("TidakAda")).thenReturn(List.of());

        List<Produk> hasil = serviceInventaris.cariProdukByNama("TidakAda");

        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Test cari by kategori dengan hasil kosong")
    void testCariByKategoriKosong() {
        when(mockRepositoryProduk.cariByKategori("Olahraga")).thenReturn(List.of());

        List<Produk> hasil = serviceInventaris.cariProdukByKategori("Olahraga");

        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Test update stok produk tidak ditemukan")
    void testUpdateStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD999")).thenReturn(Optional.empty());

        boolean hasil = serviceInventaris.updateStok("PROD999", 20);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test update stok dengan stok negatif")
    void testUpdateStokNegatif() {
        Produk produk = new Produk("PROD015", "Laptop", "Elektronik", 10000000, 10, 5);

        boolean hasil = serviceInventaris.updateStok("PROD015", -5);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test keluar stok produk tidak ditemukan")
    void testKeluarStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD999")).thenReturn(Optional.empty());

        boolean hasil = serviceInventaris.keluarStok("PROD999", 5);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test keluar stok dengan jumlah nol")
    void testKeluarStokJumlahNol() {
        Produk produk = new Produk("PROD016", "Mouse", "Elektronik", 250000, 10, 5);

        boolean hasil = serviceInventaris.keluarStok("PROD016", 0);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test keluar stok melebihi stok tersedia")
    void testKeluarStokMelebihiTersedia() {
        Produk produk = new Produk("PROD017", "Keyboard", "Elektronik", 800000, 5, 3);

        when(mockRepositoryProduk.cariByKode("PROD017")).thenReturn(Optional.of(produk));

        boolean hasil = serviceInventaris.keluarStok("PROD017", 10);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test masuk stok produk tidak ditemukan")
    void testMasukStokProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD999")).thenReturn(Optional.empty());

        boolean hasil = serviceInventaris.masukStok("PROD999", 5);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test masuk stok dengan jumlah nol")
    void testMasukStokJumlahNol() {
        Produk produk = new Produk("PROD018", "Speaker", "Elektronik", 500000, 10, 5);

        boolean hasil = serviceInventaris.masukStok("PROD018", 0);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test get produk stok menipis dengan list kosong")
    void testGetProdukStokMenipisKosong() {
        when(mockRepositoryProduk.cariProdukStokMenipis()).thenReturn(List.of());

        List<Produk> hasil = serviceInventaris.getProdukStokMenipis();

        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Test get produk stok habis dengan list kosong")
    void testGetProdukStokHabisKosong() {
        when(mockRepositoryProduk.cariProdukStokHabis()).thenReturn(List.of());

        List<Produk> hasil = serviceInventaris.getProdukStokHabis();

        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Test hitung total nilai inventaris dengan list kosong")
    void testHitungTotalNilaiInventarisKosong() {
        when(mockRepositoryProduk.cariSemua()).thenReturn(List.of());

        double total = serviceInventaris.hitungTotalNilaiInventaris();

        assertEquals(0.0, total, 0.01);
    }

    @Test
    @DisplayName("Test hapus produk yang ada dengan repository hapus success")
    void testHapusProdukAdaDenganGetKosongSetelahHapus() {
        Produk produk = new Produk("PROD025", "Test", "Elektronik", 10000, 5, 3);

        produk.setStok(0);

        when(mockRepositoryProduk.cariByKode("PROD025")).thenReturn(Optional.of(produk));

        when(mockRepositoryProduk.hapus("PROD025")).thenReturn(true);

        boolean hasil = serviceInventaris.hapusProduk("PROD025");

        assertTrue(hasil);
        verify(mockRepositoryProduk).hapus("PROD025");
    }

    @Test
    @DisplayName("Test masuk stok dengan produk yang tidak aktif")
    void testMasukStokProdukTidakAktif() {
        Produk produk = new Produk("PROD026", "Test", "Elektronik", 10000, 5, 3);
        produk.setAktif(false);

        when(mockRepositoryProduk.cariByKode("PROD026")).thenReturn(Optional.of(produk));

        boolean hasil = serviceInventaris.masukStok("PROD026", 10);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test masuk stok berhasil dengan produk aktif")
    void testMasukStokBerhasilDenganProdukAktif() {
        Produk produk = new Produk("PROD027", "Test", "Elektronik", 10000, 5, 3);

        when(mockRepositoryProduk.cariByKode("PROD027")).thenReturn(Optional.of(produk));
        when(mockRepositoryProduk.updateStok(eq("PROD027"), anyInt())).thenReturn(true);

        boolean hasil = serviceInventaris.masukStok("PROD027", 10);

        assertTrue(hasil);

        verify(mockRepositoryProduk).updateStok("PROD027", 15);
    }

    @Test
    @DisplayName("Test update stok dengan produk tidak aktif")
    void testUpdateStokProdukTidakAktif() {
        Produk produk = new Produk("PROD028", "Test", "Elektronik", 10000, 5, 3);
        produk.setAktif(false);

        when(mockRepositoryProduk.cariByKode("PROD028")).thenReturn(Optional.of(produk));

        boolean hasil = serviceInventaris.updateStok("PROD028", 20);

        assertFalse(hasil);
    }

    @Test
    @DisplayName("Test cari produk by kode dengan optional empty dari repository")
    void testCariProdukByKodeOptionalEmpty() {
        when(mockRepositoryProduk.cariByKode("PROD999")).thenReturn(Optional.empty());

        Optional<Produk> hasil = serviceInventaris.cariProdukByKode("PROD999");

        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Test keluar stok dengan produk berhasil")
    void testKeluarStokDenganProdukGetSetelahOptional() {
        Produk produk = new Produk("PROD029", "Test", "Elektronik", 10000, 20, 3);

        when(mockRepositoryProduk.cariByKode("PROD029")).thenReturn(Optional.of(produk));
        when(mockRepositoryProduk.updateStok(eq("PROD029"), anyInt())).thenReturn(true);

        boolean hasil = serviceInventaris.keluarStok("PROD029", 5);

        assertTrue(hasil);

        verify(mockRepositoryProduk).updateStok("PROD029", 15);
    }

    @Test
    @DisplayName("Test keluar stok dengan jumlah tepat sama dengan stok")
    void testKeluarStokJumlahTepatSamaDenganStok() {
        Produk produk = new Produk("PROD030", "Test", "Elektronik", 10000, 10, 3);

        when(mockRepositoryProduk.cariByKode("PROD030")).thenReturn(Optional.of(produk));
        when(mockRepositoryProduk.updateStok(eq("PROD030"), anyInt())).thenReturn(true);

        boolean hasil = serviceInventaris.keluarStok("PROD030", 10);

        assertTrue(hasil);

        verify(mockRepositoryProduk).updateStok("PROD030", 0);
    }

    @Test
    @DisplayName("Test hitung total stok")
    void testHitungTotalStok() {
        Produk produk1 = new Produk("PROD031", "Produk 1", "Elektronik", 10000, 10, 3);
        Produk produk2 = new Produk("PROD032", "Produk 2", "Fashion", 20000, 5, 2);
        Produk produk3 = new Produk("PROD033", "Produk 3", "Makanan", 5000, 20, 5);

        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(produk1, produk2, produk3));

        int total = serviceInventaris.hitungTotalStok();

        assertEquals(35, total);
    }

    @Test
    @DisplayName("Test hitung total stok dengan produk aktif")
    void testHitungTotalStokDenganProdukAktif() {
        Produk produk1 = new Produk("PROD031", "Produk 1", "Elektronik", 10000, 10, 3);
        Produk produk2 = new Produk("PROD032", "Produk 2", "Fashion", 20000, 5, 2);
        Produk produk3 = new Produk("PROD033", "Produk 3", "Makanan", 5000, 20, 5);

        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(produk1, produk2, produk3));

        int total = serviceInventaris.hitungTotalStok();

        assertEquals(35, total);
    }

    @Test
    @DisplayName("Test hitung total stok dengan list kosong")
    void testHitungTotalStokListKosong() {
        when(mockRepositoryProduk.cariSemua()).thenReturn(List.of());

        int total = serviceInventaris.hitungTotalStok();

        assertEquals(0, total);
    }

    @Test
    @DisplayName("Test hitung total stok dengan produk tidak aktif")
    void testHitungTotalStokDenganProdukTidakAktif() {
        Produk produk1 = new Produk("PROD034", "Produk 1", "Elektronik", 10000, 10, 3);
        Produk produk2 = new Produk("PROD035", "Produk 2", "Fashion", 20000, 5, 2);
        produk2.setAktif(false); // Set produk 2 tidak aktif
        Produk produk3 = new Produk("PROD036", "Produk 3", "Makanan", 5000, 20, 5);

        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(produk1, produk2, produk3));

        int total = serviceInventaris.hitungTotalStok();

        assertEquals(30, total);
    }

    @Test
    @DisplayName("Test hitung total stok dengan semua produk tidak aktif")
    void testHitungTotalStokSemuaTidakAktif() {
        Produk produk1 = new Produk("PROD037", "Produk 1", "Elektronik", 10000, 10, 3);
        produk1.setAktif(false);
        Produk produk2 = new Produk("PROD038", "Produk 2", "Fashion", 20000, 5, 2);
        produk2.setAktif(false);

        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(produk1, produk2));

        int total = serviceInventaris.hitungTotalStok();

        assertEquals(0, total);
    }
}

