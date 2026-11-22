package akademikMini;

public class MataKuliah {
    private String kode;
    private String nama;
    private int sks;
    private int kuota;
    private int terisi;

    /** Konstruktor default. */
    public MataKuliah() {
        this.kode = "-";
        this.nama = "-";
        this.sks = 1;
        this.kuota = 1;
        this.terisi = 0;
    }

    /**
     * Konstruktor berparameter (overloading).
     *
     * @throws IllegalArgumentException jika ada nilai yang tidak valid
     */
    public MataKuliah(String kode, String nama, int sks, int kuota) {
        this.setKode(kode);
        this.setNama(nama);
        this.setSks(sks);
        this.setKuota(kuota);
        this.terisi = 0;
    }

    // ---------- Getter dan Setter (dengan validasi) ----------

    public String getKode() {
        return kode;
    }

    /** Validasi: kode tidak boleh kosong. */
    public void setKode(String kode) {
        if (kode == null || kode.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode mata kuliah tidak boleh kosong.");
        }
        this.kode = kode.trim();
    }

    public String getNama() {
        return nama;
    }

    /** Validasi: nama tidak boleh kosong. */
    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama mata kuliah tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public int getSks() {
        return sks;
    }

    /** Validasi: SKS harus 1 sampai 6. */
    public void setSks(int sks) {
        if (sks < 1 || sks > 6) {
            throw new IllegalArgumentException("SKS harus antara 1 sampai 6.");
        }
        this.sks = sks;
    }

    public int getKuota() {
        return kuota;
    }

    /** Validasi: kuota harus lebih dari 0 dan tidak boleh di bawah jumlah peserta saat ini. */
    public void setKuota(int kuota) {
        if (kuota <= 0) {
            throw new IllegalArgumentException("Kuota harus lebih dari 0.");
        }
        if (kuota < this.terisi) {
            throw new IllegalArgumentException(
                    "Kuota tidak boleh lebih kecil dari jumlah peserta saat ini (" + this.terisi + ").");
        }
        this.kuota = kuota;
    }

    /** Tidak ada setter terisi agar nilainya hanya berubah lewat tambah/kurangiPeserta. */
    public int getTerisi() {
        return terisi;
    }

    // ---------- Method perilaku ----------

    /** @return true jika kuota sudah penuh. */
    public boolean isPenuh() {
        return terisi >= kuota;
    }

    /** @return jumlah kursi yang masih tersedia. */
    public int sisaKuota() {
        return kuota - terisi;
    }

    /**
     * Menambah satu peserta.
     *
     * @return true jika berhasil, false jika kuota sudah penuh
     */
    public boolean tambahPeserta() {
        if (isPenuh()) {
            return false;
        }
        this.terisi++;
        return true;
    }

    /** Mengurangi satu peserta (tidak akan kurang dari 0). */
    public void kurangiPeserta() {
        if (this.terisi > 0) {
            this.terisi--;
        }
    }

    /** Menampilkan informasi mata kuliah ke console. */
    public void tampilkanInfo() {
        System.out.println("Kode   : " + kode);
        System.out.println("Nama   : " + nama);
        System.out.println("SKS    : " + sks);
        System.out.println("Kuota  : " + terisi + "/" + kuota
                + (isPenuh() ? " (PENUH)" : " (sisa " + sisaKuota() + ")"));
    }
}

