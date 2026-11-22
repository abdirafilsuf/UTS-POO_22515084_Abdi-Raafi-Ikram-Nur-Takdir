package akademikMini;

import java.util.ArrayList;

public class DaftarMahasiswaTerurut {
    private static final int KAPASITAS_DEFAULT = 50;

    private ArrayList<Mahasiswa> data;
    private int kapasitas;

    public DaftarMahasiswaTerurut() {
        this(KAPASITAS_DEFAULT);
    }

    /**
     * Konstruktor berparameter.
     * Jika kapasitas tidak valid (<= 0), dipakai kapasitas default.
     */
    public DaftarMahasiswaTerurut(int kapasitas) {
        this.kapasitas = (kapasitas > 0) ? kapasitas : KAPASITAS_DEFAULT;
        this.data = new ArrayList<>();
    }

    /**
     * Pencarian biner berdasarkan NIM.
     *
     * @return indeks jika ditemukan; jika tidak, -(posisiSisip) - 1
     */
    private int cariIndeks(String nim) {
        int kiri = 0;
        int kanan = data.size() - 1;
        while (kiri <= kanan) {
            int tengah = (kiri + kanan) / 2;
            int banding = data.get(tengah).getNim().compareTo(nim);
            if (banding == 0) {
                return tengah;
            } else if (banding < 0) {
                kiri = tengah + 1;
            } else {
                kanan = tengah - 1;
            }
        }
        return -(kiri) - 1;
    }

    /**
     * Menyisipkan mahasiswa pada posisi yang menjaga urutan NIM.
     * Prasyarat: m tidak null, daftar belum penuh, NIM belum ada.
     *
     * @return true jika berhasil disisipkan
     */
    public boolean insert(Mahasiswa m) {
        if (m == null) {
            System.out.println("[ADT] Gagal: data mahasiswa null.");
            return false;
        }
        if (data.size() >= kapasitas) {
            System.out.println("[ADT] Gagal: daftar penuh (kapasitas " + kapasitas + ").");
            return false;
        }
        int hasil = cariIndeks(m.getNim());
        if (hasil >= 0) {
            System.out.println("[ADT] Gagal: NIM " + m.getNim() + " sudah ada di daftar.");
            return false;
        }
        int posisi = -(hasil + 1);
        data.add(posisi, m);
        return true;
    }

    /**
     * Menghapus mahasiswa berdasarkan NIM.
     *
     * @return true jika ditemukan dan dihapus
     */
    public boolean remove(String nim) {
        if (data.isEmpty()) {
            System.out.println("[ADT] Gagal: daftar kosong.");
            return false;
        }
        int idx = (nim == null) ? -1 : cariIndeks(nim.trim());
        if (idx < 0) {
            System.out.println("[ADT] Gagal: NIM " + nim + " tidak ditemukan.");
            return false;
        }
        data.remove(idx);
        return true;
    }

    /** @return mahasiswa dengan NIM tersebut, atau null jika tidak ditemukan/daftar kosong. */
    public Mahasiswa search(String nim) {
        if (data.isEmpty() || nim == null) {
            return null;
        }
        int idx = cariIndeks(nim.trim());
        return (idx >= 0) ? data.get(idx) : null;
    }

    /** @return mahasiswa pada indeks tertentu (mulai 0), atau null jika di luar jangkauan. */
    public Mahasiswa get(int index) {
        if (index < 0 || index >= data.size()) {
            return null;
        }
        return data.get(index);
    }

    /** Menampilkan isi daftar berurutan menurut NIM. */
    public void tampilkan() {
        if (data.isEmpty()) {
            System.out.println("(Daftar terurut kosong)");
            return;
        }
        System.out.println("Daftar mahasiswa terurut (" + data.size() + "/" + kapasitas + "):");
        for (int i = 0; i < data.size(); i++) {
            Mahasiswa m = data.get(i);
            System.out.println("  [" + i + "] " + m.getNim() + " - " + m.getNama() + " (" + m.getJurusan() + ")");
        }
    }

    /** @return jumlah elemen saat ini. */
    public int size() {
        return data.size();
    }
}
