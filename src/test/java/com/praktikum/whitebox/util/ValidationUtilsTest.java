package com.praktikum.whitebox.util;

import com.praktikum.whitebox.model.Kategori;
import com.praktikum.whitebox.model.Produk;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Validation Utils")
public class ValidationUtilsTest {

    // ========== BOUNDARY VALUE ANALYSIS - isValidKodeProduk ==========

    @Test
    @DisplayName("Validasi kode produk null - return false")
    void testValidasiKodeProdukNull() {
        assertFalse(ValidationUtils.isValidKodeProduk(null));
    }

    @Test
    @DisplayName("Validasi kode produk kosong - return false")
    void testValidasiKodeProdukKosong() {
        assertFalse(ValidationUtils.isValidKodeProduk(""));
    }

    @Test
    @DisplayName("Validasi kode produk hanya spasi - return false")
    void testValidasiKodeProdukHanyaSpasi() {
        assertFalse(ValidationUtils.isValidKodeProduk("   "));
    }

    @Test
    @DisplayName("Validasi kode produk format valid - huruf dan angka")
    void testValidasiKodeProdukFormatValid() {
        assertTrue(ValidationUtils.isValidKodeProduk("PROD001"));
        assertTrue(ValidationUtils.isValidKodeProduk("ABC123"));
        assertTrue(ValidationUtils.isValidKodeProduk("P01"));
    }

    @Test
    @DisplayName("Validasi kode produk dengan huruf kecil - valid (regex menerima)")
    void testValidasiKodeProdukHurufKecil() {
        // Regex ^[A-Za-z0-9]{3,10}$ menerima huruf kecil
        assertTrue(ValidationUtils.isValidKodeProduk("prod001"));
        assertTrue(ValidationUtils.isValidKodeProduk("abc123"));
    }

    @Test
    @DisplayName("Validasi kode produk dengan huruf campuran")
    void testValidasiKodeProdukHurufCampuran() {
        assertTrue(ValidationUtils.isValidKodeProduk("PrOd001"));
        assertTrue(ValidationUtils.isValidKodeProduk("AbC123"));
    }

    @Test
    @DisplayName("Validasi kode produk terlalu pendek (< 3 karakter)")
    void testValidasiKodeProdukTerlaluPendek() {
        assertFalse(ValidationUtils.isValidKodeProduk("AB"));
        assertFalse(ValidationUtils.isValidKodeProduk("A1"));
        assertFalse(ValidationUtils.isValidKodeProduk("12"));
    }

    @Test
    @DisplayName("Validasi kode produk tepat 3 karakter (boundary minimum)")
    void testValidasiKodeProdukTigaKarakter() {
        assertTrue(ValidationUtils.isValidKodeProduk("ABC"));
        assertTrue(ValidationUtils.isValidKodeProduk("A12"));
        assertTrue(ValidationUtils.isValidKodeProduk("123"));
    }

    @Test
    @DisplayName("Validasi kode produk tepat 10 karakter (boundary maksimum)")
    void testValidasiKodeProduk10Karakter() {
        assertTrue(ValidationUtils.isValidKodeProduk("ABCD123456"));
        assertTrue(ValidationUtils.isValidKodeProduk("1234567890"));
    }

    @Test
    @DisplayName("Validasi kode produk lebih dari 10 karakter")
    void testValidasiKodeProdukLebihDari10() {
        assertFalse(ValidationUtils.isValidKodeProduk("ABCD1234567"));
        assertFalse(ValidationUtils.isValidKodeProduk("12345678901"));
    }

    @Test
    @DisplayName("Validasi kode produk dengan karakter khusus")
    void testValidasiKodeProdukKarakterKhusus() {
        assertFalse(ValidationUtils.isValidKodeProduk("PROD-001"));
        assertFalse(ValidationUtils.isValidKodeProduk("PROD_001"));
        assertFalse(ValidationUtils.isValidKodeProduk("PROD@001"));
        assertFalse(ValidationUtils.isValidKodeProduk("PROD 001"));
    }

    @Test
    @DisplayName("Validasi kode produk hanya huruf")
    void testValidasiKodeProdukHanyaHuruf() {
        assertTrue(ValidationUtils.isValidKodeProduk("PRODUK"));
        assertTrue(ValidationUtils.isValidKodeProduk("ABC"));
    }

    @Test
    @DisplayName("Validasi kode produk hanya angka")
    void testValidasiKodeProdukHanyaAngka() {
        assertTrue(ValidationUtils.isValidKodeProduk("123456"));
        assertTrue(ValidationUtils.isValidKodeProduk("999"));
    }

    // ========== BOUNDARY VALUE ANALYSIS - isValidNama ==========

    @Test
    @DisplayName("Validasi nama null - return false")
    void testValidasiNamaNull() {
        assertFalse(ValidationUtils.isValidNama(null));
    }

    @Test
    @DisplayName("Validasi nama kosong - return false")
    void testValidasiNamaKosong() {
        assertFalse(ValidationUtils.isValidNama(""));
    }

    @Test
    @DisplayName("Validasi nama hanya spasi - return false")
    void testValidasiNamaHanyaSpasi() {
        assertFalse(ValidationUtils.isValidNama("   "));
    }

    @Test
    @DisplayName("Validasi nama terlalu pendek (< 3 karakter)")
    void testValidasiNamaTerlaluPendek() {
        assertFalse(ValidationUtils.isValidNama("AB"));
        assertFalse(ValidationUtils.isValidNama("A"));
    }

    @Test
    @DisplayName("Validasi nama tepat 3 karakter (boundary minimum valid)")
    void testValidasiNamaTigaKarakter() {
        assertTrue(ValidationUtils.isValidNama("ABC"));
        assertTrue(ValidationUtils.isValidNama("A B"));
    }

    @Test
    @DisplayName("Validasi nama terlalu panjang (> 100 karakter)")
    void testValidasiNamaTerlaluPanjang() {
        String namaPanjang = "A".repeat(101);
        assertFalse(ValidationUtils.isValidNama(namaPanjang));
    }

    @Test
    @DisplayName("Validasi nama tepat 100 karakter (boundary maksimum valid)")
    void testValidasiNamaSeratusKarakter() {
        String nama100 = "A".repeat(100);
        assertTrue(ValidationUtils.isValidNama(nama100));
    }

    @Test
    @DisplayName("Validasi nama dengan panjang normal")
    void testValidasiNamaNormal() {
        assertTrue(ValidationUtils.isValidNama("Laptop Gaming"));
        assertTrue(ValidationUtils.isValidNama("Mouse Wireless"));
    }

    @Test
    @DisplayName("Validasi nama dengan angka")
    void testValidasiNamaDenganAngka() {
        assertTrue(ValidationUtils.isValidNama("iPhone 15 Pro"));
    }

    @Test
    @DisplayName("Validasi nama dengan spasi di awal/akhir")
    void testValidasiNamaDenganSpasi() {
        assertTrue(ValidationUtils.isValidNama("  Produk Valid  "));
    }

    @Test
    @DisplayName("Validasi nama dengan karakter khusus")
    void testValidasiNamaDenganKarakterKhusus() {
        assertTrue(ValidationUtils.isValidNama("Laptop @Gaming!"));
        assertTrue(ValidationUtils.isValidNama("Mouse-Wireless"));
    }

    // ========== BOUNDARY VALUE ANALYSIS - isValidHarga ==========

    @Test
    @DisplayName("Validasi harga positif - return true")
    void testValidasiHargaPositif() {
        assertTrue(ValidationUtils.isValidHarga(100.0));
        assertTrue(ValidationUtils.isValidHarga(0.01));
        assertTrue(ValidationUtils.isValidHarga(999999.99));
    }

    @Test
    @DisplayName("Validasi harga nol - return false")
    void testValidasiHargaNol() {
        assertFalse(ValidationUtils.isValidHarga(0));
    }

    @Test
    @DisplayName("Validasi harga negatif - return false")
    void testValidasiHargaNegatif() {
        assertFalse(ValidationUtils.isValidHarga(-1.0));
        assertFalse(ValidationUtils.isValidHarga(-100.0));
    }

    @Test
    @DisplayName("Validasi harga sangat kecil positif")
    void testValidasiHargaSangatKecil() {
        assertTrue(ValidationUtils.isValidHarga(0.001));
    }

    // ========== BOUNDARY VALUE ANALYSIS - isValidStok ==========

    @Test
    @DisplayName("Validasi stok nol - return true")
    void testValidasiStokNol() {
        assertTrue(ValidationUtils.isValidStok(0));
    }

    @Test
    @DisplayName("Validasi stok positif - return true")
    void testValidasiStokPositif() {
        assertTrue(ValidationUtils.isValidStok(1));
        assertTrue(ValidationUtils.isValidStok(100));
    }

    @Test
    @DisplayName("Validasi stok negatif - return false")
    void testValidasiStokNegatif() {
        assertFalse(ValidationUtils.isValidStok(-1));
        assertFalse(ValidationUtils.isValidStok(-100));
    }

    // ========== BOUNDARY VALUE ANALYSIS - isValidStokMinimum ==========

    @Test
    @DisplayName("Validasi stok minimum nol - return true")
    void testValidasiStokMinimumNol() {
        assertTrue(ValidationUtils.isValidStokMinimum(0));
    }

    @Test
    @DisplayName("Validasi stok minimum positif - return true")
    void testValidasiStokMinimumPositif() {
        assertTrue(ValidationUtils.isValidStokMinimum(5));
        assertTrue(ValidationUtils.isValidStokMinimum(10));
    }

    @Test
    @DisplayName("Validasi stok minimum negatif - return false")
    void testValidasiStokMinimumNegatif() {
        assertFalse(ValidationUtils.isValidStokMinimum(-1));
    }

    @Test
    @DisplayName("Validasi produk null - return false")
    void testValidasiProdukNull() {
        assertFalse(ValidationUtils.isValidProduk(null));
    }

    // ========== PATH COVERAGE - isValidKategori ==========

    @Test
    @DisplayName("Validasi kategori null - return false")
    void testValidasiKategoriNull() {
        assertFalse(ValidationUtils.isValidKategori(null));
    }

    @Test
    @DisplayName("Validasi kategori lengkap dan valid - return true")
    void testValidasiKategoriLengkapValid() {
        Kategori kategori = new Kategori("KAT001", "Elektronik", "Kategori untuk produk elektronik");

        assertTrue(ValidationUtils.isValidKategori(kategori));
    }

    @Test
    @DisplayName("Validasi kategori dengan kode invalid - return false")
    void testValidasiKategoriKodeInvalid() {
        Kategori kategori = new Kategori("K", "Elektronik", null);

        assertFalse(ValidationUtils.isValidKategori(kategori));
    }

    @Test
    @DisplayName("Validasi kategori dengan nama invalid - return false")
    void testValidasiKategoriNamaInvalid() {
        Kategori kategori = new Kategori("KAT001", "EL", null);

        assertFalse(ValidationUtils.isValidKategori(kategori));
    }

    @Test
    @DisplayName("Validasi kategori dengan deskripsi null - return true (opsional)")
    void testValidasiKategoriDeskripsiNull() {
        Kategori kategori = new Kategori("KAT001", "Elektronik", null);

        assertTrue(ValidationUtils.isValidKategori(kategori));
    }

    @Test
    @DisplayName("Validasi kategori dengan deskripsi kosong - return true")
    void testValidasiKategoriDeskripsiKosong() {
        Kategori kategori = new Kategori("KAT001", "Elektronik", "");

        assertTrue(ValidationUtils.isValidKategori(kategori));
    }

    @Test
    @DisplayName("Validasi kategori dengan deskripsi terlalu panjang - return false")
    void testValidasiKategoriDeskripsiTerlaluPanjang() {
        String deskripsiPanjang = "A".repeat(501);
        Kategori kategori = new Kategori("KAT001", "Elektronik", deskripsiPanjang);

        assertFalse(ValidationUtils.isValidKategori(kategori));
    }

    @Test
    @DisplayName("Validasi kategori dengan deskripsi tepat 500 karakter")
    void testValidasiKategoriDeskripsi500Karakter() {
        String deskripsi500 = "A".repeat(500);
        Kategori kategori = new Kategori("KAT001", "Elektronik", deskripsi500);

        assertTrue(ValidationUtils.isValidKategori(kategori));
    }

    // ========== BOUNDARY VALUE ANALYSIS - isValidPersentase ==========

    @Test
    @DisplayName("Validasi persentase 0 - return true")
    void testValidasiPersentaseNol() {
        assertTrue(ValidationUtils.isValidPersentase(0));
    }

    @Test
    @DisplayName("Validasi persentase 100 - return true")
    void testValidasiPersentaseSeratus() {
        assertTrue(ValidationUtils.isValidPersentase(100));
    }

    @Test
    @DisplayName("Validasi persentase antara 0-100 - return true")
    void testValidasiPersentaseValid() {
        assertTrue(ValidationUtils.isValidPersentase(50));
        assertTrue(ValidationUtils.isValidPersentase(25.5));
        assertTrue(ValidationUtils.isValidPersentase(99.99));
    }

    @Test
    @DisplayName("Validasi persentase negatif - return false")
    void testValidasiPersentaseNegatif() {
        assertFalse(ValidationUtils.isValidPersentase(-1));
        assertFalse(ValidationUtils.isValidPersentase(-0.01));
    }

    @Test
    @DisplayName("Validasi persentase lebih dari 100 - return false")
    void testValidasiPersentaseLebihDari100() {
        assertFalse(ValidationUtils.isValidPersentase(100.01));
        assertFalse(ValidationUtils.isValidPersentase(150));
    }

    // ========== BOUNDARY VALUE ANALYSIS - isValidKuantitas ==========

    @Test
    @DisplayName("Validasi kuantitas 1 - return true")
    void testValidasiKuantitasSatu() {
        assertTrue(ValidationUtils.isValidKuantitas(1));
    }

    @Test
    @DisplayName("Validasi kuantitas positif - return true")
    void testValidasiKuantitasPositif() {
        assertTrue(ValidationUtils.isValidKuantitas(10));
        assertTrue(ValidationUtils.isValidKuantitas(100));
    }

    @Test
    @DisplayName("Validasi kuantitas 0 - return false")
    void testValidasiKuantitasNol() {
        assertFalse(ValidationUtils.isValidKuantitas(0));
    }

    @Test
    @DisplayName("Validasi kuantitas negatif - return false")
    void testValidasiKuantitasNegatif() {
        assertFalse(ValidationUtils.isValidKuantitas(-1));
        assertFalse(ValidationUtils.isValidKuantitas(-10));
    }

    // ========== PARAMETERIZED TESTS ==========

    @ParameterizedTest
    @ValueSource(strings = {"PROD001", "ABC123", "XYZ999", "prod001", "abc123", "123456"})
    @DisplayName("Parameterized test - kode produk valid")
    void testKodeProdukValidBerbagai(String kode) {
        assertTrue(ValidationUtils.isValidKodeProduk(kode));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PR", "AB", "PROD-001", "PROD_001", "ABCDEFGHIJK"})
    @DisplayName("Parameterized test - kode produk invalid")
    void testKodeProdukInvalidBerbagai(String kode) {
        assertFalse(ValidationUtils.isValidKodeProduk(kode));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Laptop", "Mouse Wireless", "Keyboard Mechanical"})
    @DisplayName("Parameterized test - nama valid")
    void testNamaValidBerbagai(String nama) {
        assertTrue(ValidationUtils.isValidNama(nama));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"A", "AB", "   "})
    @DisplayName("Parameterized test - nama invalid")
    void testNamaInvalidBerbagai(String nama) {
        assertFalse(ValidationUtils.isValidNama(nama));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 100, 1000, 999999.99})
    @DisplayName("Parameterized test - harga valid")
    void testHargaValidBerbagai(double harga) {
        assertTrue(ValidationUtils.isValidHarga(harga));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -1, -100, -0.01})
    @DisplayName("Parameterized test - harga invalid")
    void testHargaInvalidBerbagai(double harga) {
        assertFalse(ValidationUtils.isValidHarga(harga));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 100, 1000})
    @DisplayName("Parameterized test - stok valid")
    void testStokValidBerbagai(int stok) {
        assertTrue(ValidationUtils.isValidStok(stok));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Parameterized test - stok invalid")
    void testStokInvalidBerbagai(int stok) {
        assertFalse(ValidationUtils.isValidStok(stok));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 25, 50, 75, 100})
    @DisplayName("Parameterized test - persentase valid")
    void testPersentaseValidBerbagai(double persentase) {
        assertTrue(ValidationUtils.isValidPersentase(persentase));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -0.01, 100.01, 150})
    @DisplayName("Parameterized test - persentase invalid")
    void testPersentaseInvalidBerbagai(double persentase) {
        assertFalse(ValidationUtils.isValidPersentase(persentase));
    }

    @Test
    void testConstructor() {
        // Hanya untuk menutup coverage constructor
        new ValidationUtils();
    }
}