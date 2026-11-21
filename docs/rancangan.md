# Class Mahasiswa

Atribut:

- nim : String
- nama : String
- jurusan : String
- semester : int
- aktif : boolean

Method:

+ Mahasiswa() (konstruktor default)
+ Mahasiswa(nim : String, nama : String, jurusan : String, semester : int) (konstruktor berparameter)
+ getNim() : String dan + setNim(nim : String) : void
+ getNama() : String dan + setNama(nama : String) : void
+ getJurusan() : String dan + setJurusan(jurusan : String) : void
+ getSemester() : int dan + setSemester(semester : int) : void
+ isAktif() : boolean dan + setAktif(aktif : boolean) : void
+ tampilkanInfo() : void
+ naikSemester() : void
+ ubahStatus(aktif : boolean) : void

Validasi setter: nim dan nama tidak boleh kosong, semester antara 1 sampai 14.

# Class MataKuliah

Atribut:

- kode : String
- nama : String
- sks : int
- kuota : int
- terisi : int

Method:

+ MataKuliah()
+ MataKuliah(kode : String, nama : String, sks : int, kuota : int)
  getter dan setter untuk kode, nama, sks, dan kuota; getter untuk terisi
+ isPenuh() : boolean
+ sisaKuota() : int
+ tambahPeserta() : boolean
+ kurangiPeserta() : void
+ tampilkanInfo() : void

Validasi setter: sks antara 1 sampai 6, kuota lebih dari 0, terisi tidak boleh melebihi kuota.

# Class KRS

Atribut:

- MAKS_SKS : int = 24 (static final)
- idKrs : String
- mahasiswa : Mahasiswa
- daftarMataKuliah : ArrayList<MataKuliah>
- totalSks : int
- disetujui : boolean

Method:

+ KRS()
+ KRS(idKrs : String, mahasiswa : Mahasiswa)
+ getIdKrs() : String dan + setIdKrs(idKrs : String) : void
+ getMahasiswa() : Mahasiswa
+ getTotalSks() : int
+ isDisetujui() : boolean
+ tambahMataKuliah(mk : MataKuliah) : boolean
+ hapusMataKuliah(kode : String) : boolean
+ hitungTotalSks() : int
+ ubahStatus(disetujui : boolean) : void
+ tampilkanInfo() : void

Aturan bisnis pada tambahMataKuliah:

Tolak bila total SKS ditambah SKS mata kuliah baru melebihi 24.
Tolak bila kuota mata kuliah sudah penuh.
Tolak bila mata kuliah sudah ada di KRS yang sama.
Bila lolos, panggil mk.tambahPeserta() lalu tambahkan ke daftar.

# Class Kampus

Atribut:

- namaKampus : String
- daftarMahasiswa : ArrayList<Mahasiswa>
- daftarMataKuliah : ArrayList<MataKuliah>
- daftarKrs : ArrayList<KRS>

Method:

+ Kampus()
+ Kampus(namaKampus : String)
+ getNamaKampus() : String dan + setNamaKampus(namaKampus : String) : void
+ tambahMahasiswa(m : Mahasiswa) : boolean (tolak NIM duplikat)
+ tambahMataKuliah(mk : MataKuliah) : boolean (tolak kode duplikat)
+ buatKrs(nim : String) : KRS
+ cariMahasiswa(nim : String) : Mahasiswa
+ cariMataKuliah(kode : String) : MataKuliah
+ tampilkanLaporan() : void

# Class ADT 
Atribut:

- data : ArrayList<Mahasiswa>
- kapasitas : int

Method:

+ DaftarMahasiswaTerurut() dan + DaftarMahasiswaTerurut(kapasitas : int)
+ insert(m : Mahasiswa) : boolean (posisi tetap terurut)
+ remove(nim : String) : boolean
+ search(nim : String) : Mahasiswa
+ get(index : int) : Mahasiswa
+ tampilkan() : void
+ size() : int

# Class Main

Atribut:

- kampus : Kampus
- adt : (sesuai varian yang dipilih)
- scanner : Scanner

Method:

+ main(args : String[]) : void (static)
- tampilkanMenu() : void
- bacaInt(pesan : String) : int
- bacaString(pesan : String) : String
- menuMahasiswa() : void
- menuMataKuliah() : void
- menuBuatKrs() : void
- menuCari() : void
- menuAdt() : void
- menuLaporan() : void