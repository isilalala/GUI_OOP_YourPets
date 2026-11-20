# GUI_OOP_YourPets
# Your Peliharaan — Java Swing GUI Application

Aplikasi desktop berbasis **Java Swing** untuk mencatat data hewan peliharaan (`Kucing`, `Anjing`, dan `Burung`) serta melakukan pengecekan kondisi fisik/kesehatan dasar secara interaktif. Data peliharaan disimpan dan dimuat otomatis ke file lokal `peliharaan.txt`.

## Fitur Utama
- **Manajemen Data Peliharaan (CRUD):** Tambah data peliharaan baru, edit data yang sudah ada, hapus data, dan tampilkan daftar peliharaan di *sidebar*.
- **Checklist Kesehatan 5 Aspek:** Penilaian kondisi (`Normal` / `Ada masalah`) yang otomatis menghitung status (`Sehat`, `Perlu perhatian`, `Periksa ke dokter`, atau `Belum dicek`) beserta saran singkat dan status vaksinasi.
- **Penyesuaian Kriteria per Jenis Hewan:** `Burung` memiliki item pemeriksaan khusus (seperti *Paruh*, *Kaki dan Cakar*, *Pernapasan*), sedangkan `Kucing` dan `Anjing` mengimplementasikan interface `Berlari`.
- **Penyimpanan Lokal:** Otomatis menyimpan perubahan ke `peliharaan.txt` dan memuatnya kembali saat aplikasi dibuka.

## 5 GUI Components / Parts dalam Kode

1. **`JTextField` (`fieldNama`) & `JComboBox` (`comboJenis`)**
   - Terletak di `PanelForm` untuk menginput nama peliharaan dan memilih jenis hewan (`Kucing`, `Anjing`, atau `Burung`).
2. **`JRadioButton` & `ButtonGroup` (`radioJantan`/`radioBetina` serta `normal`/`masalah`)**
   - Digunakan di `PanelForm` untuk memilih jenis kelamin dan di `PanelCek` untuk memilih kondisi `Normal` atau `Ada masalah` pada 5 indikator kesehatan.
3. **`JSlider` (`sliderUmur`) & `JCheckBox` (`checkVaksin`)**
   - Terletak di `PanelForm` (dengan `ChangeListener` pada `JSlider`) untuk mengatur umur peliharaan (`0`–`20` tahun) dan menandai status vaksinasi (`Sudah divaksin`).
4. **`JList` (`daftar`) & `JScrollPane`**
   - `JList` menampilkan daftar objek `Hewan` pada *sidebar* kiri (`Main`) dan memicu perpindahan tampilan saat item dipilih; `JScrollPane` membungkus daftar peliharaan dan panel checklist (`PanelCek`) agar bisa di-*scroll*.
5. **`JPanel` dengan `CardLayout` (`isi`), `JButton`, & `JLabel`**
   - `CardLayout` pada panel `isi` mengatur perpindahan halaman antara form input/edit (`"form"`) dan halaman pengecekan kesehatan (`"cek"`), dilengkapi `JButton` untuk aksi (`+ Tambah Peliharaan`, `Simpan dan Cek Kesehatan`, `Simpan Hasil Cek`, `Edit Data`, `Hapus`) serta `JLabel` untuk menampilkan info, status warna, dan saran.

## Screenshot Aplikasi

<img width="1171" height="803" alt="Screenshot 2026-11-20 143351" src="https://github.com/user-attachments/assets/6487ad96-b4ca-4021-80bf-713a96041ad9" />

## Cara Menjalankan
1. Compile file Java:
   ```bash
   javac Main.java
