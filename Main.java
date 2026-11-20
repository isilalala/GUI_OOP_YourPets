import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

interface Berlari {
    String lari();
}

abstract class Hewan {
    protected String nama, kelamin;
    protected int umur;
    protected boolean vaksin;
    protected boolean[] cek = {true, true, true, true, true};
    protected boolean sudahDicek = false;

    public Hewan(String nama, String kelamin, int umur, boolean vaksin) {
        this.nama = nama;
        this.kelamin = kelamin;
        this.umur = umur;
        this.vaksin = vaksin;
    }

    public static Hewan buat(String jenis, String nama, String kelamin, int umur, boolean vaksin) {
        if (jenis.equals("Kucing")) {
            return new Kucing(nama, kelamin, umur, vaksin);
        } else if (jenis.equals("Anjing")) {
            return new Anjing(nama, kelamin, umur, vaksin);
        } else {
            return new Burung(nama, kelamin, umur, vaksin);
        }
    }

    public String jenis() {
        return getClass().getSimpleName();
    }

    public String[] judul() {
        return new String[]{"Mata dan Hidung", "Telinga", "Kulit dan Bulu", "Mulut dan Gigi", "Gerak dan Tubuh"};
    }

    public String[] isi() {
        return new String[]{
            "Mata cerah, tidak berair atau merah. Hidung lembap, bebas luka atau kerak.",
            "Bagian dalam bersih, tidak berbau menyengat, merah muda pucat.",
            "Tidak ada benjolan, ruam, koreng, atau kutu. Bulu halus dan mengilap.",
            "Gigi relatif bersih, gusi tidak pucat atau berdarah, napas tidak terlalu bau.",
            "Perhatikan apakah bergerak normal dan tidak kesakitan saat disentuh."
        };
    }

    public String kategori() {
        if (umur < 2) {
            return "Anak";
        } else if (umur < 8) {
            return "Dewasa";
        } else {
            return "Tua";
        }
    }

    public String status() {
        int skor = 0;
        for (boolean b : cek) {
            if (b) {
                skor++;
            }
        }
        if (!sudahDicek) {
            return "Belum dicek";
        } else if (skor == 5) {
            return "Sehat";
        } else if (skor >= 3) {
            return "Perlu perhatian";
        } else {
            return "Periksa ke dokter";
        }
    }

    public String saran() {
        String v = vaksin ? "Sudah divaksin" : "Belum divaksin";
        switch (status()) {
            case "Sehat":
                return v + " dan kondisi fisik terlihat baik.";
            case "Perlu perhatian":
                return v + ", tapi ada tanda yang perlu dipantau.";
            case "Periksa ke dokter":
                return v + ", tapi vaksin tidak menjamin sehat. Segera ke dokter hewan.";
            default:
                return "Jawab 5 pertanyaan lalu simpan hasilnya.";
        }
    }

    public String toString() {
        return nama + " - " + jenis() + " - " + status();
    }
}

class Kucing extends Hewan implements Berlari {
    public Kucing(String nama, String kelamin, int umur, boolean vaksin) {
        super(nama, kelamin, umur, vaksin);
    }

    public String lari() {
        return nama + " berlari dengan lincah";
    }
}

class Anjing extends Hewan implements Berlari {
    public Anjing(String nama, String kelamin, int umur, boolean vaksin) {
        super(nama, kelamin, umur, vaksin);
    }

    public String lari() {
        return nama + " berlari kencang";
    }
}

class Burung extends Hewan {
    public Burung(String nama, String kelamin, int umur, boolean vaksin) {
        super(nama, kelamin, umur, vaksin);
    }

    public String[] judul() {
        return new String[]{"Mata dan Hidung", "Bulu dan Kulit", "Paruh", "Kaki dan Cakar", "Pernapasan"};
    }

    public String[] isi() {
        return new String[]{
            "Mata cerah, bersih, tidak menyipit atau berair. Hidung kering dan bersih.",
            "Bulu rapi dan berkilau. Cek sela bulu dari kutu, luka, atau kulit kering.",
            "Tidak retak, mengelupas parah, atau tumbuh terlalu panjang.",
            "Cengkeraman kuat. Tanpa bengkak, luka, koreng, atau kuku terlalu panjang.",
            "Napas tenang tanpa bunyi klik, mengi, atau ekor naik-turun berlebihan."
        };
    }
}

class PanelForm extends JPanel {
    JTextField fieldNama = new JTextField();
    JComboBox<String> comboJenis = new JComboBox<>(new String[]{"Kucing", "Anjing", "Burung"});
    JRadioButton radioJantan = new JRadioButton("Jantan", true);
    JRadioButton radioBetina = new JRadioButton("Betina");
    JSlider sliderUmur = new JSlider(0, 20, 1);
    JLabel labelUmur = new JLabel("Umur: 1 tahun");
    JCheckBox checkVaksin = new JCheckBox("Sudah divaksin");
    JButton tombol = new JButton("Simpan dan Cek Kesehatan");
    TitledBorder judulForm = new TitledBorder("Tambah Peliharaan");

    PanelForm() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        ButtonGroup grup = new ButtonGroup();
        grup.add(radioJantan);
        grup.add(radioBetina);
        JPanel kelamin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kelamin.add(radioJantan);
        kelamin.add(radioBetina);

        sliderUmur.setMajorTickSpacing(5);
        sliderUmur.setPaintTicks(true);
        sliderUmur.setPaintLabels(true);
        sliderUmur.addChangeListener(e -> labelUmur.setText("Umur: " + sliderUmur.getValue() + " tahun"));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 15));
        form.setBorder(judulForm);
        form.add(new JLabel("Nama:"));
        form.add(fieldNama);
        form.add(new JLabel("Jenis:"));
        form.add(comboJenis);
        form.add(new JLabel("Kelamin:"));
        form.add(kelamin);
        form.add(labelUmur);
        form.add(sliderUmur);
        form.add(new JLabel("Kesehatan:"));
        form.add(checkVaksin);

        JPanel bawah = new JPanel(new FlowLayout());
        bawah.add(tombol);

        JPanel atas = new JPanel(new BorderLayout());
        atas.add(form, BorderLayout.CENTER);
        atas.add(bawah, BorderLayout.SOUTH);
        add(atas, BorderLayout.NORTH);
    }

    void isi(Hewan h) {
        fieldNama.setText(h.nama);
        comboJenis.setSelectedItem(h.jenis());
        radioJantan.setSelected(h.kelamin.equals("Jantan"));
        radioBetina.setSelected(h.kelamin.equals("Betina"));
        sliderUmur.setValue(h.umur);
        checkVaksin.setSelected(h.vaksin);
        judulForm.setTitle("Edit Peliharaan");
        tombol.setText("Simpan Perubahan");
        repaint();
    }

    void reset() {
        judulForm.setTitle("Tambah Peliharaan");
        tombol.setText("Simpan dan Cek Kesehatan");
        repaint();
        fieldNama.setText("");
        comboJenis.setSelectedIndex(0);
        radioJantan.setSelected(true);
        sliderUmur.setValue(1);
        checkVaksin.setSelected(false);
    }
}

class PanelCek extends JPanel {
    TitledBorder[] border = new TitledBorder[5];
    JLabel[] desk = new JLabel[5];
    JRadioButton[] normal = new JRadioButton[5];
    JRadioButton[] masalah = new JRadioButton[5];
    JLabel labelNama = new JLabel();
    JLabel labelInfo = new JLabel();
    JLabel labelStatus = new JLabel();
    JLabel labelSaran = new JLabel();
    JButton tombolSimpan = new JButton("Simpan Hasil Cek");
    JButton tombolEdit = new JButton("Edit Data");
    JButton tombolHapus = new JButton("Hapus");
    Hewan hewan;

    PanelCek() {
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        labelNama.setFont(new Font("SansSerif", Font.BOLD, 22));
        labelStatus.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.add(labelNama);
        header.add(labelInfo);

        JPanel daftar = new JPanel();
        daftar.setLayout(new BoxLayout(daftar, BoxLayout.Y_AXIS));
        for (int i = 0; i < 5; i++) {
            desk[i] = new JLabel("");
            border[i] = new TitledBorder("");
            normal[i] = new JRadioButton("Normal", true);
            masalah[i] = new JRadioButton("Ada masalah");
            ButtonGroup grup = new ButtonGroup();
            grup.add(normal[i]);
            grup.add(masalah[i]);

            JPanel pilihan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pilihan.add(normal[i]);
            pilihan.add(masalah[i]);

            JPanel baris = new JPanel(new GridLayout(2, 1));
            baris.setBorder(border[i]);
            baris.add(desk[i]);
            baris.add(pilihan);
            daftar.add(baris);
        }

        JPanel tombol = new JPanel(new FlowLayout());
        tombol.add(tombolEdit);
        tombol.add(tombolHapus);
        tombol.add(tombolSimpan);

        JPanel hasil = new JPanel(new GridLayout(2, 1));
        hasil.add(labelStatus);
        hasil.add(labelSaran);

        JPanel bawah = new JPanel(new BorderLayout());
        bawah.add(hasil, BorderLayout.CENTER);
        bawah.add(tombol, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(daftar), BorderLayout.CENTER);
        add(bawah, BorderLayout.SOUTH);
    }

    void tampilkan(Hewan h) {
        hewan = h;
        labelNama.setText(h.nama);
        labelInfo.setText(h.jenis() + " - " + h.kelamin + " - " + h.umur + " tahun ("
                + h.kategori() + ") - " + (h.vaksin ? "Sudah divaksin" : "Belum divaksin"));

        String[] judulH = h.judul();
        String[] isiH = h.isi();
        for (int i = 0; i < 5; i++) {
            border[i].setTitle((i + 1) + ". " + judulH[i]);
            desk[i].setText(isiH[i]);
        }
        if (h instanceof Berlari) {
            desk[4].setText(((Berlari) h).lari() + ". Cek apakah tidak pincang dan tidak kesakitan.");
        }

        for (int i = 0; i < 5; i++) {
            if (!h.sudahDicek || h.cek[i]) {
                normal[i].setSelected(true);
            } else {
                masalah[i].setSelected(true);
            }
        }
        perbarui();
        repaint();
    }

    void simpanHasil() {
        for (int i = 0; i < 5; i++) {
            hewan.cek[i] = normal[i].isSelected();
        }
        hewan.sudahDicek = true;
        perbarui();
    }

    void perbarui() {
        String s = hewan.status();
        Color warna;
        if (s.equals("Sehat")) {
            warna = new Color(22, 163, 74);
        } else if (s.equals("Perlu perhatian")) {
            warna = new Color(217, 119, 6);
        } else if (s.equals("Periksa ke dokter")) {
            warna = new Color(220, 38, 38);
        } else {
            warna = Color.GRAY;
        }
        labelStatus.setText("Status: " + s);
        labelStatus.setForeground(warna);
        labelSaran.setText(hewan.saran());
    }
}

public class Main extends JFrame {
    DefaultListModel<Hewan> model = new DefaultListModel<>();
    JList<Hewan> daftar = new JList<>(model);
    CardLayout kartu = new CardLayout();
    JPanel isi = new JPanel(kartu);
    PanelForm form = new PanelForm();
    PanelCek cek = new PanelCek();
    Hewan sedangDiedit = null;

    public Main() {
        setTitle("Your Peliharaan");
        setSize(950, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JButton tombolTambah = new JButton("+ Tambah Peliharaan");
        JLabel judul = new JLabel("Peliharaanku");
        judul.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel atas = new JPanel(new GridLayout(2, 1, 0, 10));
        atas.add(judul);
        atas.add(tombolTambah);

        daftar.setFixedCellHeight(36);
        daftar.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel sidebar = new JPanel(new BorderLayout(0, 10));
        sidebar.setBorder(new EmptyBorder(15, 15, 15, 15));
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.add(atas, BorderLayout.NORTH);
        sidebar.add(new JScrollPane(daftar), BorderLayout.CENTER);

        isi.add(form, "form");
        isi.add(cek, "cek");

        add(sidebar, BorderLayout.WEST);
        add(isi, BorderLayout.CENTER);

        tombolTambah.addActionListener(e -> bukaForm());
        form.tombol.addActionListener(e -> tambah());
        form.fieldNama.addActionListener(e -> tambah());
        cek.tombolSimpan.addActionListener(e -> simpanCek());
        cek.tombolHapus.addActionListener(e -> hapus());
        cek.tombolEdit.addActionListener(e -> mulaiEdit());
        daftar.addListSelectionListener(e -> {
            Hewan h = daftar.getSelectedValue();
            if (!e.getValueIsAdjusting() && h != null) {
                cek.tampilkan(h);
                kartu.show(isi, "cek");
            }
        });

        muatFile();
    }

    private void bukaForm() {
        sedangDiedit = null;
        form.reset();
        daftar.clearSelection();
        kartu.show(isi, "form");
    }

    private void tambah() {
        String nama = form.fieldNama.getText().trim().replace("|", "");
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong");
            return;
        }
        String kelamin = form.radioJantan.isSelected() ? "Jantan" : "Betina";
        Hewan h = Hewan.buat((String) form.comboJenis.getSelectedItem(), nama, kelamin,
                form.sliderUmur.getValue(), form.checkVaksin.isSelected());
        if (sedangDiedit != null) {
            int index = model.indexOf(sedangDiedit);
            h.cek = sedangDiedit.cek.clone();
            h.sudahDicek = sedangDiedit.sudahDicek;
            model.set(index, h);
            sedangDiedit = null;
            simpanFile();
            form.reset();
            daftar.setSelectedIndex(index);
            cek.tampilkan(h);
            kartu.show(isi, "cek");
        } else {
            model.addElement(h);
            simpanFile();
            form.reset();
            daftar.setSelectedValue(h, true);
        }
    }

    private void mulaiEdit() {
        Hewan h = daftar.getSelectedValue();
        if (h == null) {
            return;
        }
        sedangDiedit = h;
        form.isi(h);
        kartu.show(isi, "form");
    }

    private void simpanCek() {
        cek.simpanHasil();
        simpanFile();
        daftar.repaint();
    }

    private void hapus() {
        Hewan h = daftar.getSelectedValue();
        if (h == null) {
            return;
        }
        int p = JOptionPane.showConfirmDialog(this, "Hapus " + h.nama + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (p == JOptionPane.YES_OPTION) {
            model.removeElement(h);
            simpanFile();
            bukaForm();
        }
    }

    private void simpanFile() {
        List<String> baris = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            Hewan h = model.get(i);
            String c = "";
            for (boolean b : h.cek) {
                c += b ? "1" : "0";
            }
            baris.add(h.jenis() + "|" + h.nama + "|" + h.kelamin + "|" + h.umur + "|"
                    + h.vaksin + "|" + h.sudahDicek + "|" + c);
        }
        try {
            Files.write(Paths.get("peliharaan.txt"), baris);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void muatFile() {
        try {
            Path f = Paths.get("peliharaan.txt");
            if (!Files.exists(f)) {
                return;
            }
            for (String s : Files.readAllLines(f)) {
                String[] p = s.split("\\|");
                Hewan h = Hewan.buat(p[0], p[1], p[2], Integer.parseInt(p[3]), Boolean.parseBoolean(p[4]));
                for (int i = 0; i < 5; i++) {
                    h.cek[i] = p[6].charAt(i) == '1';
                }
                h.sudahDicek = Boolean.parseBoolean(p[5]);
                model.addElement(h);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
