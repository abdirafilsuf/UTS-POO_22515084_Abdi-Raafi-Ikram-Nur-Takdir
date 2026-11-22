package akademikMini;

import java.util.ArrayList;

public class Kampus {
    private String namaKampus;
    private ArrayList<Mahasiswa> daftarMahasiswa;
    private ArrayList<MataKuliah> daftarMataKuliah;
    private ArrayList<KRS> daftarKrs;

    /** Konstruktor default. */
    public Kampus() {
        this.namaKampus = "Kampus";
        this.daftarMahasiswa = new ArrayList<>();
        this.daftarMataKuliah = new ArrayList<>();
        this.daftarKrs = new ArrayList<>();
    }

    /** Konstruktor berparameter (overloading). */
    public Kampus(String namaKampus) {
        this.setNamaKampus(namaKampus);
        this.daftarMahasiswa = new ArrayList<>();
        this.daftarMataKuliah = new ArrayList<>();
        this.daftarKrs = new ArrayList<>();
    }

    public String getNamaKampus() {
        return namaKampus;
    }

    /** Validasi: nama kampus tidak boleh kosong. */
    public void setNamaKampus(String namaKampus) {
        if (namaKampus == null || namaKampus.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kampus tidak boleh kosong.");
        }
        this.namaKampus = namaKampus.trim();
    }

    /**
     * Menambah mahasiswa baru. NIM harus unik.
     *
     * @return true jika berhasil, false jika null atau NIM sudah terdaftar
     */
    public boolean tambahMahasiswa(Mahasiswa m) {
        if (m == null) {
            System.out.println("[GAGAL] Data mahasiswa tidak valid.");
            return false;
        }
        if (cariMahasiswa(m.getNim()) != null) {
            System.out.println("[GAGAL] NIM " + m.getNim() + " sudah terdaftar.");
            return false;
        }
        daftarMahasiswa.add(m);
        return true;
    }

    /**
     * Menambah mata kuliah baru. Kode harus unik.
     *
     * @return true jika berhasil, false jika null atau kode sudah terdaftar
     */
    public boolean tambahMataKuliah(MataKuliah mk) {
        if (mk == null) {
            System.out.println("[GAGAL] Data mata kuliah tidak valid.");
            return false;
        }
        if (cariMataKuliah(mk.getKode()) != null) {
            System.out.println("[GAGAL] Kode mata kuliah " + mk.getKode() + " sudah terdaftar.");
            return false;
        }
        daftarMataKuliah.add(mk);
        return true;
    }

    /**
     * Membuat KRS kosong untuk mahasiswa dengan NIM tertentu dan mencatatnya.
     *
     * @return objek KRS baru, atau null jika mahasiswa tidak ditemukan
     */
    public KRS buatKrs(String nim) {
        Mahasiswa m = cariMahasiswa(nim);
        if (m == null) {
            System.out.println("[GAGAL] Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
            return null;
        }
        String id = String.format("KRS-%03d", daftarKrs.size() + 1);
        KRS krs = new KRS(id, m);
        daftarKrs.add(krs);
        return krs;
    }

    /** @return mahasiswa dengan NIM tersebut, atau null jika tidak ditemukan. */
    public Mahasiswa cariMahasiswa(String nim) {
        if (nim == null) {
            return null;
        }
        for (Mahasiswa m : daftarMahasiswa) {
            if (m.getNim().equalsIgnoreCase(nim.trim())) {
                return m;
            }
        }
        return null;
    }

    /** @return mata kuliah dengan kode tersebut, atau null jika tidak ditemukan. */
    public MataKuliah cariMataKuliah(String kode) {
        if (kode == null) {
            return null;
        }
        for (MataKuliah mk : daftarMataKuliah) {
            if (mk.getKode().equalsIgnoreCase(kode.trim())) {
                return mk;
            }
        }
        return null;
    }

    /** Tambahan (tidak ada di diagram awal): menampilkan seluruh mahasiswa untuk menu 1. */
    public void tampilkanSemuaMahasiswa() {
        if (daftarMahasiswa.isEmpty()) {
            System.out.println("(Belum ada data mahasiswa)");
            return;
        }
        int no = 1;
        for (Mahasiswa m : daftarMahasiswa) {
            System.out.println("--- Mahasiswa " + no++ + " ---");
            m.tampilkanInfo();
        }
    }

    /** Tambahan (tidak ada di diagram awal): menampilkan seluruh mata kuliah untuk menu 2. */
    public void tampilkanSemuaMataKuliah() {
        if (daftarMataKuliah.isEmpty()) {
            System.out.println("(Belum ada data mata kuliah)");
            return;
        }
        int no = 1;
        for (MataKuliah mk : daftarMataKuliah) {
            System.out.println("--- Mata kuliah " + no++ + " ---");
            mk.tampilkanInfo();
        }
    }

    /** Menampilkan laporan ringkas seluruh data kampus. */
    public void tampilkanLaporan() {
        int totalSksSemua = 0;
        int krsDisetujui = 0;
        for (KRS k : daftarKrs) {
            totalSksSemua += k.getTotalSks();
            if (k.isDisetujui()) {
                krsDisetujui++;
            }
        }
        System.out.println("===== LAPORAN " + namaKampus.toUpperCase() + " =====");
        System.out.println("Jumlah mahasiswa     : " + daftarMahasiswa.size());
        System.out.println("Jumlah mata kuliah   : " + daftarMataKuliah.size());
        System.out.println("Jumlah KRS           : " + daftarKrs.size() + " (disetujui: " + krsDisetujui + ")");
        System.out.println("Total SKS seluruh KRS: " + totalSksSemua);
        if (!daftarMataKuliah.isEmpty()) {
            System.out.println("Kuota mata kuliah:");
            for (MataKuliah mk : daftarMataKuliah) {
                System.out.println("  " + mk.getKode() + " - " + mk.getNama() + ": "
                        + mk.getTerisi() + "/" + mk.getKuota());
            }
        }
        if (!daftarKrs.isEmpty()) {
            System.out.println("Daftar KRS:");
            for (KRS k : daftarKrs) {
                System.out.println("  " + k.getIdKrs() + " | " + k.getMahasiswa().getNama()
                        + " | " + k.getTotalSks() + " SKS");
            }
        }
    }
}

