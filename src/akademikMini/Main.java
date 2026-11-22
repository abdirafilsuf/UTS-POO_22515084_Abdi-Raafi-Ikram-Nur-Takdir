package akademikMini;

import java.util.Scanner;

public class Main {
    /** Kapasitas ADT; sengaja kecil agar kondisi "penuh" mudah diuji. Ubah sesuai kebutuhan. */
    private static final int KAPASITAS_ADT = 5;

    private Kampus kampus;
    private DaftarMahasiswaTerurut adt;
    private Scanner scanner;

    private Main() {
        this.kampus = new Kampus("Kampus Mini");
        this.adt = new DaftarMahasiswaTerurut(KAPASITAS_ADT);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main app = new Main();
        boolean jalan = true;
        while (jalan) {
            app.tampilkanMenu();
            int pilihan = app.bacaInt("Pilih menu: ");
            switch (pilihan) {
                case 1: app.menuMahasiswa(); break;
                case 2: app.menuMataKuliah(); break;
                case 3: app.menuBuatKrs(); break;
                case 4: app.menuCari(); break;
                case 5: app.menuAdt(); break;
                case 6: app.menuLaporan(); break;
                case 7:
                    System.out.println("Terima kasih. Program selesai.");
                    jalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia. Pilih angka 1 sampai 7.");
            }
        }
    }

    private void tampilkanMenu() {
        System.out.println();
        System.out.println("=========== AKADEMIK MINI KAMPUS ===========");
        System.out.println("1. Mahasiswa (tambah / tampilkan)");
        System.out.println("2. Mata kuliah (tambah / tampilkan)");
        System.out.println("3. Buat KRS");
        System.out.println("4. Cari data");
        System.out.println("5. Operasi ADT (daftar mahasiswa terurut)");
        System.out.println("6. Laporan ringkas");
        System.out.println("7. Keluar");
        System.out.println("============================================");
    }

    /** Membaca bilangan bulat; mengulang sampai input valid. */
    private int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            if (!scanner.hasNextLine()) {
                System.out.println("\nInput berakhir. Program selesai.");
                System.exit(0);
            }
            String baris = scanner.nextLine().trim();
            try {
                return Integer.parseInt(baris);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    /** Membaca teks; mengulang sampai tidak kosong. */
    private String bacaString(String pesan) {
        while (true) {
            System.out.print(pesan);
            if (!scanner.hasNextLine()) {
                System.out.println("\nInput berakhir. Program selesai.");
                System.exit(0);
            }
            String baris = scanner.nextLine().trim();
            if (!baris.isEmpty()) {
                return baris;
            }
            System.out.println("Input tidak boleh kosong. Coba lagi.");
        }
    }

    private void menuMahasiswa() {
        System.out.println("-- Menu Mahasiswa --");
        System.out.println("1. Tambah mahasiswa");
        System.out.println("2. Tampilkan semua mahasiswa");
        int pilih = bacaInt("Pilih: ");
        if (pilih == 1) {
            String nim = bacaString("NIM      : ");
            String nama = bacaString("Nama     : ");
            String jurusan = bacaString("Jurusan  : ");
            int semester = bacaInt("Semester : ");
            try {
                Mahasiswa m = new Mahasiswa(nim, nama, jurusan, semester);
                if (kampus.tambahMahasiswa(m)) {
                    System.out.println("Mahasiswa berhasil ditambahkan.");
                    if (adt.insert(m)) {
                        System.out.println("(Juga dimasukkan ke daftar terurut ADT.)");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[GAGAL] " + e.getMessage());
            }
        } else if (pilih == 2) {
            kampus.tampilkanSemuaMahasiswa();
        } else {
            System.out.println("Pilihan tidak tersedia.");
        }
    }

    private void menuMataKuliah() {
        System.out.println("-- Menu Mata Kuliah --");
        System.out.println("1. Tambah mata kuliah");
        System.out.println("2. Tampilkan semua mata kuliah");
        int pilih = bacaInt("Pilih: ");
        if (pilih == 1) {
            String kode = bacaString("Kode  : ");
            String nama = bacaString("Nama  : ");
            int sks = bacaInt("SKS   : ");
            int kuota = bacaInt("Kuota : ");
            try {
                MataKuliah mk = new MataKuliah(kode, nama, sks, kuota);
                if (kampus.tambahMataKuliah(mk)) {
                    System.out.println("Mata kuliah berhasil ditambahkan.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[GAGAL] " + e.getMessage());
            }
        } else if (pilih == 2) {
            kampus.tampilkanSemuaMataKuliah();
        } else {
            System.out.println("Pilihan tidak tersedia.");
        }
    }

    private void menuBuatKrs() {
        System.out.println("-- Buat KRS --");
        String nim = bacaString("NIM mahasiswa: ");
        KRS krs = kampus.buatKrs(nim);
        if (krs == null) {
            return;
        }
        System.out.println("KRS " + krs.getIdKrs() + " dibuat untuk " + krs.getMahasiswa().getNama() + ".");
        System.out.println("Ketik kode mata kuliah untuk ditambahkan, atau 0 untuk selesai.");
        kampus.tampilkanSemuaMataKuliah();
        while (true) {
            String kode = bacaString("Kode mata kuliah (0 = selesai): ");
            if (kode.equals("0")) {
                break;
            }
            MataKuliah mk = kampus.cariMataKuliah(kode);
            if (mk == null) {
                System.out.println("[GAGAL] Mata kuliah " + kode + " tidak ditemukan.");
                continue;
            }
            if (krs.tambahMataKuliah(mk)) {
                System.out.println("Berhasil menambah " + mk.getNama() + ". Total SKS: " + krs.getTotalSks());
            }
        }
        int setuju = bacaInt("Setujui KRS ini? (1 = ya, 0 = tidak): ");
        krs.ubahStatus(setuju == 1);
        System.out.println("--- Ringkasan KRS ---");
        krs.tampilkanInfo();
    }

    private void menuCari() {
        System.out.println("-- Cari Data --");
        System.out.println("1. Cari mahasiswa (NIM)");
        System.out.println("2. Cari mata kuliah (kode)");
        int pilih = bacaInt("Pilih: ");
        if (pilih == 1) {
            String nim = bacaString("NIM: ");
            Mahasiswa m = kampus.cariMahasiswa(nim);
            if (m == null) {
                System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
            } else {
                m.tampilkanInfo();
            }
        } else if (pilih == 2) {
            String kode = bacaString("Kode: ");
            MataKuliah mk = kampus.cariMataKuliah(kode);
            if (mk == null) {
                System.out.println("Mata kuliah dengan kode " + kode + " tidak ditemukan.");
            } else {
                mk.tampilkanInfo();
            }
        } else {
            System.out.println("Pilihan tidak tersedia.");
        }
    }

    private void menuAdt() {
        System.out.println("-- Operasi ADT: Daftar Mahasiswa Terurut --");
        System.out.println("1. insert (dari mahasiswa yang sudah terdaftar)");
        System.out.println("2. remove (berdasarkan NIM)");
        System.out.println("3. search (berdasarkan NIM)");
        System.out.println("4. get (berdasarkan indeks)");
        System.out.println("5. tampilkan");
        System.out.println("6. size");
        int pilih = bacaInt("Pilih: ");
        switch (pilih) {
            case 1: {
                String nim = bacaString("NIM mahasiswa: ");
                Mahasiswa m = kampus.cariMahasiswa(nim);
                if (m == null) {
                    System.out.println("[GAGAL] NIM " + nim + " belum terdaftar di kampus.");
                } else if (adt.insert(m)) {
                    System.out.println("Berhasil disisipkan ke daftar terurut.");
                }
                break;
            }
            case 2: {
                String nim = bacaString("NIM yang dihapus: ");
                if (adt.remove(nim)) {
                    System.out.println("Berhasil dihapus dari daftar terurut.");
                }
                break;
            }
            case 3: {
                String nim = bacaString("NIM yang dicari: ");
                Mahasiswa m = adt.search(nim);
                if (m == null) {
                    System.out.println("NIM " + nim + " tidak ditemukan di daftar terurut.");
                } else {
                    m.tampilkanInfo();
                }
                break;
            }
            case 4: {
                int idx = bacaInt("Indeks (mulai 0): ");
                Mahasiswa m = adt.get(idx);
                if (m == null) {
                    System.out.println("Indeks " + idx + " di luar jangkauan.");
                } else {
                    m.tampilkanInfo();
                }
                break;
            }
            case 5:
                adt.tampilkan();
                break;
            case 6:
                System.out.println("Jumlah elemen: " + adt.size());
                break;
            default:
                System.out.println("Pilihan tidak tersedia.");
        }
        if (pilih >= 1 && pilih <= 4) {
            System.out.println("--- Isi ADT saat ini ---");
            adt.tampilkan();
        }
    }

    private void menuLaporan() {
        kampus.tampilkanLaporan();
        System.out.println("Isi daftar terurut (ADT): " + adt.size() + " elemen");
    }
}
