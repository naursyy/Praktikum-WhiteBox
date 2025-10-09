package com.praktikum.whitebox.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Cases untuk Class Kategori
 * Target: 100% Method Coverage, Line Coverage ≥90%, Branch Coverage ≥85%
 */
public class KategoriTest {

    private Kategori kategori1;
    private Kategori kategori2;

    @BeforeEach
    void setUp() {
        kategori1 = new Kategori("KAT001", "Elektronik", "Produk elektronik");
        kategori2 = new Kategori("KAT002", "Fashion", "Produk fashion");
    }

    // ========== TEST CONSTRUCTOR ==========

    @Test
    @DisplayName("Test constructor tanpa parameter")
    void testConstructorTanpaParameter() {
        Kategori kategori = new Kategori();
        assertNotNull(kategori);
    }

    @Test
    @DisplayName("Test constructor dengan 3 parameter")
    void testConstructorDengan3Parameter() {
        Kategori kategori = new Kategori("KAT003", "Makanan", "Produk makanan");

        assertEquals("KAT003", kategori.getKode());
        assertEquals("Makanan", kategori.getNama());
        assertEquals("Produk makanan", kategori.getDeskripsi());
        assertTrue(kategori.isAktif());
    }

    // ========== TEST GETTERS DAN SETTERS ==========

    @Test
    @DisplayName("Test setKode dan getKode")
    void testSetKodeGetKode() {
        kategori1.setKode("KAT999");
        assertEquals("KAT999", kategori1.getKode());
    }

    @Test
    @DisplayName("Test setNama dan getNama")
    void testSetNamaGetNama() {
        kategori1.setNama("Olahraga");
        assertEquals("Olahraga", kategori1.getNama());
    }

    @Test
    @DisplayName("Test setDeskripsi dan getDeskripsi")
    void testSetDeskripsiGetDeskripsi() {
        kategori1.setDeskripsi("Deskripsi baru");
        assertEquals("Deskripsi baru", kategori1.getDeskripsi());
    }

    @Test
    @DisplayName("Test setAktif dan isAktif")
    void testSetAktifIsAktif() {
        assertTrue(kategori1.isAktif());

        kategori1.setAktif(false);
        assertFalse(kategori1.isAktif());

        kategori1.setAktif(true);
        assertTrue(kategori1.isAktif());
    }

    // ========== TEST EQUALS ==========

    @Test
    @DisplayName("Test equals dengan objek yang sama")
    void testEqualsObjekSama() {
        assertTrue(kategori1.equals(kategori1));
    }

    @Test
    @DisplayName("Test equals dengan null")
    void testEqualsNull() {
        assertFalse(kategori1.equals(null));
    }

    @Test
    @DisplayName("Test equals dengan class berbeda")
    void testEqualsClassBerbeda() {
        assertFalse(kategori1.equals("String"));
        assertFalse(kategori1.equals(Integer.valueOf(123)));
    }

    @Test
    @DisplayName("Test equals dengan kode yang sama")
    void testEqualsKodeSama() {
        Kategori kategori3 = new Kategori("KAT001", "Nama Berbeda", "Deskripsi Berbeda");
        assertTrue(kategori1.equals(kategori3));
    }

    @Test
    @DisplayName("Test equals dengan kode berbeda")
    void testEqualsKodeBerbeda() {
        assertFalse(kategori1.equals(kategori2));
    }

    // ========== TEST HASHCODE ==========

    @Test
    @DisplayName("Test hashCode konsisten")
    void testHashCodeKonsisten() {
        int hash1 = kategori1.hashCode();
        int hash2 = kategori1.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Test hashCode untuk objek yang equals")
    void testHashCodeUntukObjekEquals() {
        Kategori kategori3 = new Kategori("KAT001", "Nama Berbeda", "Deskripsi Berbeda");
        assertEquals(kategori1.hashCode(), kategori3.hashCode());
    }

    @Test
    @DisplayName("Test hashCode untuk objek yang berbeda")
    void testHashCodeUntukObjekBerbeda() {
        assertNotEquals(kategori1.hashCode(), kategori2.hashCode());
    }

    // ========== TEST TOSTRING ==========

    @Test
    @DisplayName("Test toString berisi semua field")
    void testToStringBerisiSemuaField() {
        String result = kategori1.toString();

        assertTrue(result.contains("Kategori"));
        assertTrue(result.contains("kode"));
        assertTrue(result.contains("KAT001"));
        assertTrue(result.contains("nama"));
        assertTrue(result.contains("Elektronik"));
        assertTrue(result.contains("deskripsi"));
        assertTrue(result.contains("Produk elektronik"));
        assertTrue(result.contains("aktif"));
    }

    @Test
    @DisplayName("Test toString dengan deskripsi null")
    void testToStringDeskripsiNull() {
        Kategori kategori = new Kategori("KAT004", "Test", null);
        String result = kategori.toString();

        assertNotNull(result);
        assertTrue(result.contains("KAT004"));
    }

    // ========== TEST EDGE CASES ==========

    @Test
    @DisplayName("Test kategori dengan nilai null pada setter")
    void testSetterDenganNull() {
        kategori1.setKode(null);
        assertNull(kategori1.getKode());

        kategori1.setNama(null);
        assertNull(kategori1.getNama());

        kategori1.setDeskripsi(null);
        assertNull(kategori1.getDeskripsi());
    }

    @Test
    @DisplayName("Test kategori dengan string kosong")
    void testKategoriDenganStringKosong() {
        Kategori kategori = new Kategori("", "", "");

        assertEquals("", kategori.getKode());
        assertEquals("", kategori.getNama());
        assertEquals("", kategori.getDeskripsi());
    }

    @Test
    @DisplayName("Test multiple setter calls pada objek yang sama")
    void testMultipleSetterCalls() {
        kategori1.setKode("NEW001");
        kategori1.setKode("NEW002");
        kategori1.setKode("NEW003");

        assertEquals("NEW003", kategori1.getKode());
    }
}