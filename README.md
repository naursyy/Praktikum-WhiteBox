# White Box Testing Sistem Inventaris

Proyek ini merupakan implementasi **White Box Testing** dengan fokus pada **code coverage** menggunakan **JaCoCo** untuk sistem manajemen inventaris.

## Fitur Utama
- **Manajemen Produk** (CRUD dengan validasi stok)
- **Kalkulator Diskon** (berdasarkan kuantitas dan tipe pelanggan)
- **Validasi Data** (kode produk, nama, harga, stok)
- **Laporan Inventaris** (total nilai, total stok)

## Struktur Project
```text
whitebox-testing-lab/
├── src/
│   ├── main/java/com/praktikum/whitebox/
│   │   ├── model/
│   │   │   ├── Produk.java
│   │   │   └── Kategori.java
│   │   ├── repository/
│   │   │   └── RepositoryProduk.java
│   │   ├── service/
│   │   │   ├── ServiceInventaris.java
│   │   │   └── KalkulatorDiskon.java
│   │   └── util/
│   │       └── ValidationUtils.java
│   └── test/java/com/praktikum/whitebox/
│       ├── model/
│       │   ├── ProdukTest.java
│       │   └── KategoriTest.java
│       ├── service/
│       │   ├── ServiceInventarisTest.java
│       │   └── KalkulatorDiskonTest.java
│       └── util/
│           └── ValidationUtilsTest.java
└── pom.xml
```

## Tools dan Teknologi
- **Java 21+**
- **Maven** (manajemen dependensi & build)
- **JUnit 5.9.2** (unit testing)
- **Mockito 5.19.0** (mocking)
- **JaCoCo 0.8.12** (code coverage)

## Cara Menjalankan Unit Test
1. Pastikan **Java 21+** dan **Maven** sudah terinstal.
2. Clone repository:
```bash
git clone [URL_REPOSITORY]
cd whitebox-testing-lab
```
3. Install dependencies:
```bash
mvn clean install
```
4. Jalankan test dengan coverage:
```bash
mvn clean test
```
5. Generate report JaCoCo:
```bash
mvn jacoco:report
```
6. Buka laporan di browser:
```
target/site/jacoco/index.html
```

## Diskon Berdasarkan Kuantitas
| Kuantitas | Diskon |
|-----------|--------|
| 5-9       | 5%     |
| 10-49     | 10%    |
| 50-99     | 15%    |
| ≥100      | 20%    |

## Diskon Berdasarkan Tipe Pelanggan
| Tipe      | Diskon Tambahan |
|-----------|-----------------|
| PREMIUM   | 10%             |
| REGULER   | 5%              |
| BARU      | 2%              |

**Maksimal Diskon: 30%**

## Coverage Target
| Metrik           | Target  |
|------------------|---------|
| Line Coverage    | ≥ 90%   |
| Branch Coverage  | ≥ 85%   |
| Method Coverage  | 100%    |

## Teknik Testing
- **Boundary Value Analysis** → Testing nilai batas (4→5, 9→10, 49→50, 99→100)
- **Path Coverage** → Testing semua kemungkinan path
- **Parameterized Test** → Testing dengan multiple combinations
- **Edge Cases** → Null, empty, negative values
- **Mocking** → Isolasi dependencies dengan Mockito

## Test Cases
- ✅ Status stok (habis, menipis, aman)
- ✅ Operasi stok (tambah, kurangi) dengan validasi
- ✅ Kalkulasi diskon dengan boundary values
- ✅ Kombinasi diskon kuantitas + tipe pelanggan
- ✅ Validasi maksimal diskon 30%
- ✅ CRUD produk dengan mock repository
- ✅ Validasi data (kode, nama, harga, stok)

## Catatan
- Jalankan `mvn jacoco:check` untuk validasi coverage threshold
- Laporan coverage tersedia dalam format HTML dan XML
- Test coverage mencakup **line**, **branch**, dan **method coverage**
- Gunakan parameterized test untuk efisiensi testing

---
**Politeknik Negeri Cilacap** - Modul Praktikum 7: White Box Testing dengan JaCoCo
