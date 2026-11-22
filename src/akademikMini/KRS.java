package akademikMini;

import java.util.ArrayList;

public class KRS {
    private static final int MAKS_SKS = 24;

    private String idKrs;
    private Mahasiswa mahasiswa;
    private ArrayList<MataKuliah> daftarMataKuliah;
    private int totalSks;
    private boolean disetujui;

    /** Konstruktor default. */
    public KRS() {
        this.idKrs = "-";
        this.mahasiswa = null;
        this.daftarMataKuliah = new ArrayList<>();
        this.totalSks = 0;
        this.disetujui = false;
    }

    /**
     * Konstruktor berparameter (overloading).
     *
     * @throws IllegalArgumentException jika id kosong atau mahasiswa null
     */
    public KRS(String idKrs, Mahasiswa mahasiswa) {
        this.setIdKrs(idKrs);
        if (mahasiswa == null) {
            throw new IllegalArgumentException("Mahasiswa pada KRS tidak boleh null.");
        }
        this.mahasiswa = mahasiswa;
        this.daftarMataKuliah = new ArrayList<>();
        this.totalSks = 0;
        this.disetujui = false;
    }

    // ---------- Getter dan Setter ----------

    public String getIdKrs() {
        return idKrs;
    }

    /** Validasi: id KRS tidak boleh kosong. */
    public void setIdKrs(String idKrs) {
        if (idKrs == null || idKrs.trim().isEmpty()) {
            throw new IllegalArgumentException("ID KRS tidak boleh kosong.");
        }
        this.idKrs = idKrs.trim();
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public int getTotalSks() {
        return totalSks;
    }

    public boolean isDisetujui() {
        return disetujui;
    }

    // ---------- Method perilaku dan aturan bisnis ----------

    /**
     * Menambahkan mata kuliah ke KRS dengan menjalankan aturan bisnis:
     * (1) tidak boleh duplikat, (2) total SKS maksimal 24, (3) kuota belum penuh.
     * Pelanggaran aturan menampilkan pesan error dan mengembalikan false (tidak crash).
     *
     * @return true jika berhasil ditambahkan
     */
    public boolean tambahMataKuliah(MataKuliah mk) {
        if (mk == null) {
            System.out.println("[GAGAL] Mata kuliah tidak valid.");
            return false;
        }
        for (MataKuliah m : daftarMataKuliah) {
            if (m.getKode().equalsIgnoreCase(mk.getKode())) {
                System.out.println("[GAGAL] Mata kuliah " + mk.getKode() + " sudah ada di KRS ini.");
                return false;
            }
        }
        if (this.totalSks + mk.getSks() > MAKS_SKS) {
            System.out.println("[GAGAL] Total SKS akan menjadi " + (this.totalSks + mk.getSks())
                    + ", melebihi batas maksimal " + MAKS_SKS + " SKS.");
            return false;
        }
        if (mk.isPenuh()) {
            System.out.println("[GAGAL] Kuota mata kuliah " + mk.getKode() + " sudah penuh.");
            return false;
        }
        mk.tambahPeserta();
        daftarMataKuliah.add(mk);
        hitungTotalSks();
        return true;
    }

    /**
     * Menghapus mata kuliah dari KRS berdasarkan kode dan mengembalikan kursinya.
     *
     * @return true jika ditemukan dan dihapus
     */
    public boolean hapusMataKuliah(String kode) {
        for (int i = 0; i < daftarMataKuliah.size(); i++) {
            MataKuliah m = daftarMataKuliah.get(i);
            if (m.getKode().equalsIgnoreCase(kode)) {
                m.kurangiPeserta();
                daftarMataKuliah.remove(i);
                hitungTotalSks();
                return true;
            }
        }
        System.out.println("[GAGAL] Mata kuliah " + kode + " tidak ada di KRS ini.");
        return false;
    }

    /** Menghitung ulang total SKS dari seluruh mata kuliah di KRS. */
    public int hitungTotalSks() {
        int total = 0;
        for (MataKuliah m : daftarMataKuliah) {
            total += m.getSks();
        }
        this.totalSks = total;
        return total;
    }

    /** Mengubah status persetujuan KRS. */
    public void ubahStatus(boolean disetujui) {
        this.disetujui = disetujui;
    }

    /** Menampilkan informasi KRS ke console. */
    public void tampilkanInfo() {
        System.out.println("ID KRS    : " + idKrs);
        System.out.println("Mahasiswa : " + (mahasiswa == null ? "-" : mahasiswa.getNim() + " - " + mahasiswa.getNama()));
        System.out.println("Total SKS : " + totalSks + " / " + MAKS_SKS);
        System.out.println("Status    : " + (disetujui ? "Disetujui" : "Belum disetujui"));
        if (daftarMataKuliah.isEmpty()) {
            System.out.println("Mata kuliah: (kosong)");
        } else {
            System.out.println("Mata kuliah:");
            int no = 1;
            for (MataKuliah m : daftarMataKuliah) {
                System.out.println("  " + no++ + ". " + m.getKode() + " - " + m.getNama() + " (" + m.getSks() + " SKS)");
            }
        }
    }
}
