package com.praktikum.whitebox.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Kalkulator Diskon - Path Coverage")
public class KalkulatorDiskonTest {
    private KalkulatorDiskon kalkulatorDiskon;
    @BeforeEach
    void setUp() {
        kalkulatorDiskon = new KalkulatorDiskon();
    }

    //Anotasi untuk menjalankan tes dengan berbagai parameter
    @ParameterizedTest
    @DisplayName("Test hitung diskon - berbagai kombinasi kuantitas dan tipe pelanggan")
    //Anotasi untuk menyediakan data parameterized test dari sumber CSV
    @CsvSource({
    // kuantitas, tipePelanggan, expectedDiskon
            "1, 'BARU', 20", // 2% dari 1000
            "5, 'BARU', 350", // 5% + 2% = 7% dari 1000*5
            "10, 'REGULER', 1500", // 10% + 5% = 15% dari 1000*10
            "50, 'PREMIUM', 12500", // 15% + 10% = 25% dari 1000*50
            "100, 'PREMIUM', 30000",// 20% + 10% = 30% (maksimal) dari1000*100
            "200, 'PREMIUM', 60000" // 20% + 10% = 30% (maksimal) dari1000*200
    })
    void testHitungDiskonVariousCases(int kuantitas, String tipePelanggan, double expectedDiskon) {
        double harga = 1000;
        double diskon = kalkulatorDiskon.hitungDiskon(harga, kuantitas, tipePelanggan);
        assertEquals(expectedDiskon, diskon, 0.001);
    }

    @Test
    @DisplayName("Test hitung diskon - parameter invalid")
    void testHitungDiskonInvalidParameters() {
        // Harga negatif
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {kalkulatorDiskon.hitungDiskon(-1000, 5, "REGULER");});
        assertEquals("Harga dan kuantitas harus positif", exception.getMessage());

        // Kuantitas nol
        exception = assertThrows(IllegalArgumentException.class, () -> {kalkulatorDiskon.hitungDiskon(1000, 0, "REGULER");
        });
        assertEquals("Harga dan kuantitas harus positif",
                exception.getMessage());
    }

    @Test
    @DisplayName("Test hitung harga setelah diskon")
    void testHitungHargaSetelahDiskon() {
        double harga = 1000;
        int kuantitas = 10;
        String tipePelanggan = "REGULER";
        double hargaSetelahDiskon = kalkulatorDiskon.hitungHargaSetelahDiskon(harga, kuantitas, tipePelanggan);
        double expectedTotal = 1000 * 10; // 10000
        double expectedDiskon = 10000 * 0.15; // 1500 (10% + 5%)
        double expectedHargaAkhir = expectedTotal - expectedDiskon; //8500
        assertEquals(expectedHargaAkhir, hargaSetelahDiskon, 0.001);
    }

    //Anotasi untuk menjalankan tes dengan berbagai parameter
    @ParameterizedTest
    @DisplayName("Test kategori diskon berdasarkan persentase")
    //Anotasi untuk menyediakan data parameterized test dari sumber CSV
    @CsvSource({
            "0.0, 'TANPA_DISKON'",
            "0.05, 'DISKON_RINGAN'",
            "0.09, 'DISKON_RINGAN'",
            "0.10, 'DISKON_SEDANG'",
            "0.15, 'DISKON_SEDANG'",
            "0.19, 'DISKON_SEDANG'",
            "0.20, 'DISKON_BESAR'",
            "0.25, 'DISKON_BESAR'",
            "0.30, 'DISKON_BESAR'"
    })

    void testGetKategoriDiskon(double persentaseDiskon, String
            expectedKategori) {String kategori = kalkulatorDiskon.getKategoriDiskon(persentaseDiskon);
        assertEquals(expectedKategori, kategori);
    }

    @Test
    @DisplayName("Test boundary values untuk kuantitas diskon")
    void testBoundaryValuesKuantitas() {
        double harga = 1000;

    // Boundary: 4 -> 5 (mulai dapat diskon 5%)
        double diskon4 = kalkulatorDiskon.hitungDiskon(harga, 4, "BARU");
        double diskon5 = kalkulatorDiskon.hitungDiskon(harga, 5, "BARU");
        assertTrue(diskon5 > diskon4);

    // Boundary: 9 -> 10 (naik ke diskon 10%)
        double diskon9 = kalkulatorDiskon.hitungDiskon(harga, 9, "BARU");
        double diskon10 = kalkulatorDiskon.hitungDiskon(harga, 10, "BARU");
        assertTrue(diskon10 > diskon9);

    // Boundary: 49 -> 50 (naik ke diskon 15%)
        double diskon49 = kalkulatorDiskon.hitungDiskon(harga, 49, "BARU");
        double diskon50 = kalkulatorDiskon.hitungDiskon(harga, 50, "BARU");
        assertTrue(diskon50 > diskon49);

    // Boundary: 99 -> 100 (naik ke diskon 20%)
        double diskon99 = kalkulatorDiskon.hitungDiskon(harga, 99, "BARU");
        double diskon100 = kalkulatorDiskon.hitungDiskon(harga, 100, "BARU");
        assertTrue(diskon100 > diskon99);
    }

    // ========== TEST CASES TAMBAHAN UNTUK COVERAGE ==========

    @Test
    @DisplayName("Test diskon kuantitas = 4 (boundary sebelum 5)")
    void testDiskonKuantitas4() {
        double hasil = kalkulatorDiskon.hitungDiskon(1000, 4, "BARU");
        // total = 4000, diskon kuantitas = 0%, diskon BARU = 2%
        // nilai diskon = 4000 * 0.02 = 80
        assertEquals(80.0, hasil, 0.01);
    }

    @Test
    @DisplayName("Test diskon kuantitas = 9 (boundary sebelum 10)")
    void testDiskonKuantitas9() {
        double hasil = kalkulatorDiskon.hitungDiskon(1000, 9, "PREMIUM");
        // total = 9000, diskon kuantitas = 5%, diskon PREMIUM = 10%
        // total diskon = 15%, nilai diskon = 9000 * 0.15 = 1350
        assertEquals(1350.0, hasil, 0.01);
    }

    @Test
    @DisplayName("Test diskon kuantitas = 49 (boundary sebelum 50)")
    void testDiskonKuantitas49() {
        double hasil = kalkulatorDiskon.hitungDiskon(1000, 49, "BARU");
        // total = 49000, diskon kuantitas = 10%, diskon BARU = 2%
        // total diskon = 12%, nilai diskon = 49000 * 0.12 = 5880
        assertEquals(5880.0, hasil, 0.01);
    }

    @Test
    @DisplayName("Test diskon kuantitas = 99 (boundary sebelum 100)")
    void testDiskonKuantitas99() {
        double hasil = kalkulatorDiskon.hitungDiskon(1000, 99, "PREMIUM");
        // total = 99000, diskon kuantitas = 15%, diskon PREMIUM = 10%
        // total diskon = 25%, nilai diskon = 99000 * 0.25 = 24750
        assertEquals(24750.0, hasil, 0.01);
    }

    @Test
    @DisplayName("Test diskon kuantitas = 1 (minimum valid)")
    void testDiskonKuantitas1() {
        double hasil = kalkulatorDiskon.hitungDiskon(5000, 1, "PREMIUM");
        // total = 5000, diskon kuantitas = 0%, diskon PREMIUM = 10%
        // nilai diskon = 5000 * 0.10 = 500
        assertEquals(500.0, hasil, 0.01);
    }

    @Test
    @DisplayName("Test diskon dengan tipe pelanggan mixed case")
    void testDiskonTipeMixedCase() {
        double hasil1 = kalkulatorDiskon.hitungDiskon(1000, 10, "PrEmIuM");
        assertEquals(2000.0, hasil1, 0.01);

        double hasil2 = kalkulatorDiskon.hitungDiskon(1000, 10, "ReGuLeR");
        assertEquals(1500.0, hasil2, 0.01);

        double hasil3 = kalkulatorDiskon.hitungDiskon(1000, 10, "BaRu");
        assertEquals(1200.0, hasil3, 0.01);
    }

    @Test
    @DisplayName("Test hitung harga setelah diskon - kuantitas besar")
    void testHitungHargaSetelahDiskonKuantitasBesar() {
        double hasil = kalkulatorDiskon.hitungHargaSetelahDiskon(500, 100, "PREMIUM");
        // total = 50000, diskon = 15000 (30%), harga setelah = 35000
        assertEquals(35000.0, hasil, 0.01);
    }

    @Test
    @DisplayName("Test hitung harga setelah diskon - tanpa diskon kuantitas")
    void testHitungHargaSetelahDiskonTanpaDiskonKuantitas() {
        double hasil = kalkulatorDiskon.hitungHargaSetelahDiskon(2000, 3, "BARU");
        // total = 6000, diskon = 120 (2%), harga setelah = 5880
        assertEquals(5880.0, hasil, 0.01);
    }

    @Test
    @DisplayName("Test get kategori diskon dengan boundary 10%")
    void testGetKategoriDiskonBoundary10() {
        assertEquals("DISKON_SEDANG", kalkulatorDiskon.getKategoriDiskon(0.10));
        assertEquals("DISKON_RINGAN", kalkulatorDiskon.getKategoriDiskon(0.0999));
        assertEquals("DISKON_SEDANG", kalkulatorDiskon.getKategoriDiskon(0.10));
    }

    @Test
    @DisplayName("Test get kategori diskon dengan boundary 20%")
    void testGetKategoriDiskonBoundary20() {
        assertEquals("DISKON_BESAR", kalkulatorDiskon.getKategoriDiskon(0.20));
        assertEquals("DISKON_SEDANG", kalkulatorDiskon.getKategoriDiskon(0.1999));
        assertEquals("DISKON_BESAR", kalkulatorDiskon.getKategoriDiskon(0.20));
    }

    @Test
    @DisplayName("Test get kategori diskon dengan persentase desimal")
    void testGetKategoriDiskonDesimal() {
        // TANPA_DISKON: <= 0
        assertEquals("TANPA_DISKON", kalkulatorDiskon.getKategoriDiskon(0));
        assertEquals("TANPA_DISKON", kalkulatorDiskon.getKategoriDiskon(-0.01));

        // DISKON_RINGAN: > 0 dan < 0.10
        assertEquals("DISKON_RINGAN", kalkulatorDiskon.getKategoriDiskon(0.01));   // 1%
        assertEquals("DISKON_RINGAN", kalkulatorDiskon.getKategoriDiskon(0.055));  // 5.5%
        assertEquals("DISKON_RINGAN", kalkulatorDiskon.getKategoriDiskon(0.09));   // 9%

        // DISKON_SEDANG: >= 0.10 dan < 0.20
        assertEquals("DISKON_SEDANG", kalkulatorDiskon.getKategoriDiskon(0.10));   // 10%
        assertEquals("DISKON_SEDANG", kalkulatorDiskon.getKategoriDiskon(0.155));  // 15.5%
        assertEquals("DISKON_SEDANG", kalkulatorDiskon.getKategoriDiskon(0.19));   // 19%

        // DISKON_BESAR: >= 0.20
        assertEquals("DISKON_BESAR", kalkulatorDiskon.getKategoriDiskon(0.20));    // 20%
        assertEquals("DISKON_BESAR", kalkulatorDiskon.getKategoriDiskon(0.255));   // 25.5%
        assertEquals("DISKON_BESAR", kalkulatorDiskon.getKategoriDiskon(0.30));    // 30%
    }

    @ParameterizedTest
    @CsvSource({
            "100, 5, PREMIUM, 75",
            "100, 5, REGULER, 50",
            "100, 5, BARU, 35",
            "100, 5, UNKNOWN, 25"
    })
    @DisplayName("Parameterized test - boundary kuantitas 5 dengan berbagai tipe")
    void testBoundaryKuantitas5BerbagaiTipe(double harga, int kuantitas,
                                            String tipe, double expected) {
        double hasil = kalkulatorDiskon.hitungDiskon(harga, kuantitas, tipe);
        assertEquals(expected, hasil, 0.1);
    }

    @ParameterizedTest
    @CsvSource({
            "100, 10, PREMIUM, 200",
            "100, 10, REGULER, 150",
            "100, 10, BARU, 120",
            "100, 10, UNKNOWN, 100"
    })
    @DisplayName("Parameterized test - boundary kuantitas 10 dengan berbagai tipe")
    void testBoundaryKuantitas10BerbagaiTipe(double harga, int kuantitas,
                                             String tipe, double expected) {
        double hasil = kalkulatorDiskon.hitungDiskon(harga, kuantitas, tipe);
        assertEquals(expected, hasil, 0.1);
    }

    @ParameterizedTest
    @CsvSource({
            "100, 50, PREMIUM, 1250",
            "100, 50, REGULER, 1000",
            "100, 50, BARU, 850",
            "100, 50, UNKNOWN, 750"
    })
    @DisplayName("Parameterized test - boundary kuantitas 50 dengan berbagai tipe")
    void testBoundaryKuantitas50BerbagaiTipe(double harga, int kuantitas,
                                             String tipe, double expected) {
        double hasil = kalkulatorDiskon.hitungDiskon(harga, kuantitas, tipe);
        assertEquals(expected, hasil, 0.1);
    }

    @ParameterizedTest
    @CsvSource({
            "100, 100, PREMIUM, 3000",
            "100, 100, REGULER, 2500",
            "100, 100, BARU, 2200",
            "100, 100, UNKNOWN, 2000"
    })
    @DisplayName("Parameterized test - boundary kuantitas 100 dengan berbagai tipe")
    void testBoundaryKuantitas100BerbagaiTipe(double harga, int kuantitas,
                                              String tipe, double expected) {
        double hasil = kalkulatorDiskon.hitungDiskon(harga, kuantitas, tipe);
        assertEquals(expected, hasil, 0.1);
    }

    @Test
    @DisplayName("Test edge case - harga dengan banyak desimal")
    void testHargaBanyakDesimal() {
        double hasil = kalkulatorDiskon.hitungDiskon(999.99, 10, "PREMIUM");
        // total = 9999.9, diskon = 20%, nilai diskon = 1999.98
        assertEquals(1999.98, hasil, 0.01);
    }

    @Test
    @DisplayName("Test edge case - kombinasi nilai ekstrem")
    void testKombinasiNilaiEkstrem() {
        double hasil = kalkulatorDiskon.hitungDiskon(0.01, 1, "BARU");
        // total = 0.01, diskon = 2%, nilai diskon = 0.0002
        assertEquals(0.0002, hasil, 0.0001);
    }

    @Test
    @DisplayName("Test konsistensi multiple calls")
    void testKonsistensiMultipleCalls() {
        for (int i = 0; i < 5; i++) {
            double hasil = kalkulatorDiskon.hitungDiskon(1000, 50, "PREMIUM");
            assertEquals(12500.0, hasil, 0.01);
        }
    }

    @Test
    @DisplayName("Test semua path diskon kuantitas dengan tipe null")
    void testSemuaPathDiskonKuantitasDenganTipeNull() {
        // < 5: tidak ada diskon
        assertEquals(0, kalkulatorDiskon.hitungDiskon(1000, 4, null), 0.01);

        // >= 5 && < 10: diskon 5%
        assertEquals(250, kalkulatorDiskon.hitungDiskon(1000, 5, null), 0.01);

        // >= 10 && < 50: diskon 10%
        assertEquals(1000, kalkulatorDiskon.hitungDiskon(1000, 10, null), 0.01);

        // >= 50 && < 100: diskon 15%
        assertEquals(7500, kalkulatorDiskon.hitungDiskon(1000, 50, null), 0.01);

        // >= 100: diskon 20%
        assertEquals(20000, kalkulatorDiskon.hitungDiskon(1000, 100, null), 0.01);
    }

    @Test
    @DisplayName("Test verifikasi maksimal diskon tidak pernah exceed 30%")
    void testVerifikasiMaksimalDiskonTidakExceed30() {
        // Test berbagai kombinasi untuk memastikan tidak ada yang > 30%
        int[] kuantitas = {1, 5, 10, 50, 100, 200};
        String[] tipe = {"PREMIUM", "REGULER", "BARU", "UNKNOWN", null};

        for (int k : kuantitas) {
            for (String t : tipe) {
                double total = 1000 * k;
                double nilaiDiskon = kalkulatorDiskon.hitungDiskon(1000, k, t);
                double persenDiskon = (nilaiDiskon / total) * 100;

                assertTrue(persenDiskon <= 30.0,
                        "Diskon " + persenDiskon + "% untuk kuantitas " + k +
                                " dan tipe " + t + " melebihi 30%");
            }
        }
    }

    @Test
    @DisplayName("Test integrasi getKategoriDiskon dengan hitungDiskon")
    void testIntegrasiGetKategoriDenganHitungDiskon() {
        double nilaiDiskon = kalkulatorDiskon.hitungDiskon(1000, 5, "REGULER");
        double total = 1000 * 5;
        double persenDiskon = nilaiDiskon / total;  // Ini menghasilkan 0.10 (10%)

        String kategori = kalkulatorDiskon.getKategoriDiskon(persenDiskon);
        assertEquals("DISKON_SEDANG", kategori);  // 0.10 termasuk DISKON_SEDANG
    }
}
