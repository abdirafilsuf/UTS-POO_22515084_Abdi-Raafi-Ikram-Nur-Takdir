package akademikMini;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String jurusan;
    private int semester;
    private boolean aktif;

    /** Konstruktor default: mengisi nilai awal yang aman. */
    public Mahasiswa() {
        this.nim = "-";
        this.nama = "-";
        this.jurusan = "-";
        this.semester = 1;
        this.aktif = true;
    }

    /**
     * Konstruktor berparameter (overloading).
     * Memakai setter agar validasi ikut berjalan.
     *
     * @throws IllegalArgumentException jika ada nilai yang tidak valid
     */
    public Mahasiswa(String nim, String nama, String jurusan, int semester) {
        this.setNim(nim);
        this.setNama(nama);
        this.setJurusan(jurusan);
        this.setSemester(semester);
        this.aktif = true;
    }

    // ---------- Getter dan Setter (dengan validasi) ----------

    public String getNim() {
        return nim;
    }

    /** Validasi: NIM tidak boleh kosong. */
    public void setNim(String nim) {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM tidak boleh kosong.");
        }
        this.nim = nim.trim();
    }

    public String getNama() {
        return nama;
    }

    /** Validasi: nama tidak boleh kosong. */
    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public String getJurusan() {
        return jurusan;
    }

    /** Validasi: jurusan tidak boleh kosong. */
    public void setJurusan(String jurusan) {
        if (jurusan == null || jurusan.trim().isEmpty()) {
            throw new IllegalArgumentException("Jurusan tidak boleh kosong.");
        }
        this.jurusan = jurusan.trim();
    }

    public int getSemester() {
        return semester;
    }

    /** Validasi: semester harus 1 sampai 14. */
    public void setSemester(int semester) {
        if (semester < 1 || semester > 14) {
            throw new IllegalArgumentException("Semester harus antara 1 sampai 14.");
        }
        this.semester = semester;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    // ---------- Method perilaku ----------

    /** Menampilkan informasi mahasiswa ke console. */
    public void tampilkanInfo() {
        System.out.println("NIM      : " + nim);
        System.out.println("Nama     : " + nama);
        System.out.println("Jurusan  : " + jurusan);
        System.out.println("Semester : " + semester);
        System.out.println("Status   : " + (aktif ? "Aktif" : "Tidak aktif"));
    }

    /** Menaikkan semester satu tingkat (maksimal semester 14). */
    public void naikSemester() {
        if (this.semester >= 14) {
            System.out.println("Semester sudah mencapai batas maksimal (14).");
            return;
        }
        this.semester++;
    }

    /** Mengubah status keaktifan mahasiswa. */
    public void ubahStatus(boolean aktif) {
        this.aktif = aktif;
    }
}
