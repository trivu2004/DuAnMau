package Lab1;

import DAO.NhanVienDAO;
import helper.DialogHelper;
import helper.ShareHelper;
import java.awt.Color;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.scene.Scene;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import model.NhanVien;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import until.Exe;
import until.Log;

public class QuanLyNhanVien extends javax.swing.JFrame {

    List<NhanVienDAO> list = new ArrayList<>();

    ArrayList<NhanVien> arr = new ArrayList<>();
    int current = 0;
    String url = "jdbc:mysql://localhost:3306/Polypro";
    String user = "root";
    String password = "123456";
    private DefaultTableModel tableModel;
    String path = "E:\\DuAnMau\\src\\image\\fpt.png";
    ImageIcon imageIcon = new ImageIcon(path);
    Timer timer = new Timer(1, e -> updateClock());
    String User, MaNV, MatKhau;
    boolean vaiTro;

    public QuanLyNhanVien() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(imageIcon.getImage());
        timer.start();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Bạn có muốn đóng chương trình không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    setVisible(false);
//System.exit(0);
                } else {
                    setDefaultCloseOperation(0);
                }
            }
        }
        );
        List<NhanVien> list = dao.vaiTro(ShareHelper.USER + "");
        for (NhanVien nhanVien : list) {
            vaiTro = nhanVien.isVaiTro();
            User = nhanVien.getHoTen();
            MaNV = nhanVien.getMaNV();
            MatKhau = nhanVien.getMatKhau();

        }
        rdoNhanVien.setSelected(!vaiTro);
        txtHoTen.setText(User);
        txtMaNV.setText(MaNV);
        txtMatKhau.setText(MatKhau);
        txtXacNhanMatKhau.setText(MatKhau);

    }
    int index = 0;
    NhanVienDAO dao = new NhanVienDAO();

    void load() {
        int stt = 1;
        DefaultTableModel model = (DefaultTableModel) tblSinhVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.select();
            for (NhanVien nv : list) {
                Object[] row = {
                    stt,
                    nv.getMaNV(),
                    nv.getMatKhau(),
                    nv.getHoTen(),
                    nv.isVaiTro() ? "Trưởng phòng" : "Nhân viên"
                };
                model.addRow(row);
                stt++;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        NhanVien model = getModel();
        String confirm = new String(txtXacNhanMatKhau.getPassword());
        if (confirm.equals(model.getMatKhau())) {
            try {
                dao.insert(model);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm mới thất bại!");
            }
        } else {
            DialogHelper.alert(this, "Xác nhận mật khẩu không đúng!");
        }
    }

    void update() {
        NhanVien model = getModel();
        String confirm = new String(txtXacNhanMatKhau.getPassword());
        if (!confirm.equals(model.getMatKhau())) {
            DialogHelper.alert(this, "Xác nhận mật khẩu không đúng!");
        } else {
            try {

                dao.update(model);
                this.load();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            String manv = txtMaNV.getText();
            try {
                dao.delete(manv);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void edit() {
        try {
            String manv = (String) tblSinhVien.getValueAt(this.index, 1);
            NhanVien model = dao.findById(manv);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void clear() {
        this.setModel(new NhanVien());
        this.setStatus(true);
    }

    void setModel(NhanVien model) {
        txtMaNV.setText(model.getMaNV());
        txtHoTen.setText(model.getHoTen());
        txtMatKhau.setText(model.getMatKhau());
        txtXacNhanMatKhau.setText(model.getMatKhau());
        rdoTruongPhong.setSelected(model.isVaiTro());
        rdoNhanVien.setSelected(!model.isVaiTro());
    }

    NhanVien getModel() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNV.getText());
        model.setHoTen(txtHoTen.getText());
        model.setMatKhau(new String(txtMatKhau.getPassword()));
        model.setVaiTro(rdoTruongPhong.isSelected());
        return model;
    }

    void setStatus(boolean insertable) {
        txtMaNV.setEditable(insertable);
        btnInsert.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblSinhVien.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
    }

    public static final String EMAIL_VERIFICATION = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";

    public static final String ID_PATTERN = "(?i)nv\\d{3}";
    public static final String SDT_PATTEN = "0[0-9]{9}";

    public void updateClock() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        String formattedTime = timeFormat.format(currentDate);
        lblTime.setText(formattedTime + " \n " + formattedDate);
    }
    int rownum = 0;
    Row row;
    Cell cell;
//
//    public static Workbook printBangDiemKhoaHocToExcel(javax.swing.JTable tblBangDiem,javax.swing.JComboBox<String> cbKhoaHoc,ThongKeDAO tkdao) throws 
//            FileNotFoundException,IOException
     void printExe(){
        
        try {
            HSSFWorkbook fWorkbook = new HSSFWorkbook();
            HSSFSheet sheet = fWorkbook.createSheet("Danh sách");

            row = sheet.createRow(rownum);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("MaNV");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Mật Khẩu");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Họ Tên");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Vai Trò");

            for (int i = 0; i < arr.size(); i++) {
//                model.NhanVien nhanVien = arr.get(i);
                rownum++;
                row = sheet.createRow(rownum);

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(arr.get(i).getMaNV());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(arr.get(i).getMatKhau());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(arr.get(i).getHoTen());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(arr.get(i).isVaiTro());

            }
            File f = new File("D://danhsach.xlsx");
            try {
                FileOutputStream fis = new FileOutputStream(f);
                fWorkbook.write(fis);
                fis.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "In thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi mở file");
            e.printStackTrace();
        }
     }

    void printExcel(JTable tbl) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue != JFileChooser.APPROVE_OPTION) {
            return; // Người dùng không chọn tệp để lưu
        }

        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath() + ".xls";

        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));
            WritableSheet sheet = workbook.createSheet("Dữ liệu từ JTable", 0);

            DefaultTableModel model = (DefaultTableModel) tbl.getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();

            // Đưa tên cột vào dòng đầu tiên của tệp Excel
            for (int colIdx = 0; colIdx < columnCount; colIdx++) {
                Label label = new Label(colIdx, 0, model.getColumnName(colIdx)); // Lấy tên cột từ JTable
                sheet.addCell(label);
            }

            // Duyệt qua JTable và đổ dữ liệu vào tệp Excel từ dòng thứ hai
            for (int rowIdx = 0; rowIdx < rowCount; rowIdx++) {
                for (int colIdx = 0; colIdx < columnCount; colIdx++) {
                    Label label = new Label(colIdx, rowIdx + 1, model.getValueAt(rowIdx, colIdx).toString()); // Dòng thứ hai trở đi
                    sheet.addCell(label);
                }
            }

            workbook.write();
            workbook.close();

            int option = JOptionPane.showConfirmDialog(null, "Tệp đã được lưu thành công. Bạn có muốn mở tệp không?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                Desktop.getDesktop().open(new File(filePath)); // Mở tệp nếu người dùng chọn "Có"
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Không thể in file Excel!");
        }
    }

    public boolean Check() {
        if (txtMaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhập mã nhân viên!");
            txtMaNV.requestFocus();
            return false;
        }
        if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhập họ tên nhân viên!");
            txtHoTen.requestFocus();
            return false;
        }
        if (txtMatKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhập mật khẩu của nhân viên!");
            txtMatKhau.requestFocus();
            return false;
        }
        if (txtXacNhanMatKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu của nhân viên!");
            txtXacNhanMatKhau.requestFocus();
            return false;
        }

        if (!txtMatKhau.getText().equals(txtXacNhanMatKhau.getText())) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không giống nhau. Vui lòng thử lại!");
            txtMatKhau.requestFocus();
            return false;
        }
        Matcher matcherId = Pattern.compile(ID_PATTERN).matcher(txtMaNV.getText());
        if (!matcherId.matches()) {
            JOptionPane.showMessageDialog(null, "Mã sinh viên không chính xác\nMã sinh viên gồm sv + 3 số phía sau\nVí dụ: nv001");
            return false;
        }

        return true;

    }

    void check() {
        if (txtMaNV.getText().equals(MaNV) & rdoTruongPhong.isSelected()) {
            //Đăng nhập bằng tài khoản trưởng phòng, mở cột của mình

            txtMaNV.setEnabled(false);
        } else if (txtMaNV.getText().equals(MaNV) & rdoNhanVien.isSelected()) {
            //Đăng nhập bằng tài khoản nhân viên, mở cột của mình

            txtMaNV.setEnabled(false);
        } else if (!txtMaNV.getText().equals(MaNV) & rdoNhanVien.isSelected() & vaiTro) {
            //Đăng nhập bằng tài khoản trưởng phòng, mở cột của nhân viên

            txtMaNV.setEnabled(false);
        } else if (!txtMaNV.getText().equals(MaNV) & rdoTruongPhong.isSelected()) {
            //Đăng nhập bằng tài khoản trưởng phòng, mở cột của trưởng phòng khác

            txtMaNV.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        } else if (!txtMaNV.getText().equals(MaNV) & rdoTruongPhong.isSelected() & !vaiTro) {
            //Đăng nhập bằng tài khoản  nhân viên, mở cột của trưởng phòng 

            txtMaNV.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        } else if (!txtMaNV.getText().equals(MaNV) & rdoNhanVien.isSelected() & !vaiTro) {
            //Đăng nhập bằng tài khoản  nhân viên, mở cột của nhân viên, khác

            txtMaNV.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    public void kk() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtMatKhau.setText("");
        txtXacNhanMatKhau.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtXacNhanMatKhau = new javax.swing.JPasswordField();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        lblTime = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSinhVien = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản Lý Nhân Viên");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(204, 255, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jTabbedPane1.setForeground(new java.awt.Color(255, 51, 51));
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jTabbedPane1.setName(""); // NOI18N

        jLayeredPane1.setBackground(new java.awt.Color(204, 255, 255));
        jLayeredPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel1.setText("Mã Nhân Viên:");

        txtMaNV.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel2.setText("Họ Và Tên:");

        txtHoTen.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel3.setText("Mật Khẩu:");

        txtMatKhau.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        jLabel4.setText("Xác Nhận Mật Khẩu:");

        txtXacNhanMatKhau.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        btnInsert.setBackground(new java.awt.Color(204, 255, 255));
        btnInsert.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(255, 0, 0));
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Luu1.png"))); // NOI18N
        btnInsert.setText("Save");
        btnInsert.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51), 2));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(204, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 0, 51));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 255, 255));
        btnDelete.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 0, 51));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51), 2));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(204, 255, 255));
        btnNew.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 0, 0));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Them1.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51), 2));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(204, 255, 255));
        btnFirst.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/len.png"))); // NOI18N
        btnFirst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51), 2));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(204, 255, 255));
        btnNext.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cuoi.png"))); // NOI18N
        btnNext.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51), 2));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(204, 255, 255));
        btnLast.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xuong.png"))); // NOI18N
        btnLast.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51), 2));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(204, 255, 255));
        btnPrev.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/dau.png"))); // NOI18N
        btnPrev.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 51, 51));
        lblTime.setText("10:22 AM");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel5.setText("Vai Trò: ");

        buttonGroup1.add(rdoTruongPhong);
        rdoTruongPhong.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        rdoTruongPhong.setSelected(true);
        rdoTruongPhong.setText("Trưởng Phòng");

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        rdoNhanVien.setText("Nhân Viên");

        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtMaNV, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtHoTen, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtMatKhau, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtXacNhanMatKhau, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnInsert, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnUpdate, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnDelete, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnNew, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnFirst, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnNext, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnLast, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnPrev, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblTime, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(rdoTruongPhong, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(rdoNhanVien, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                            .addComponent(txtMatKhau)
                            .addComponent(txtXacNhanMatKhau)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(txtHoTen))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTime)
                        .addGap(121, 121, 121))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(rdoTruongPhong)
                                .addGap(56, 56, 56)
                                .addComponent(rdoNhanVien))
                            .addComponent(jLabel5)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1))
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addGap(28, 28, 28)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtXacNhanMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTruongPhong)
                    .addComponent(rdoNhanVien))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cập Nhật", jLayeredPane1);

        jLayeredPane2.setBackground(new java.awt.Color(204, 255, 255));
        jLayeredPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        tblSinhVien.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        tblSinhVien.setForeground(new java.awt.Color(0, 51, 51));
        tblSinhVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Mật Khẩu", "Họ Và Tên", "Vai Trò"
            }
        ));
        tblSinhVien.setEnabled(false);
        tblSinhVien.setOpaque(false);
        tblSinhVien.setRowHeight(40);
        tblSinhVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSinhVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSinhVien);

        jButton1.setText("Exe");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLayeredPane2.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(72, 72, 72))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("Danh Sách", jLayeredPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Cap Nhat");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
//        new QuanLy().setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void tblSinhVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSinhVienMouseClicked
        if (evt.getClickCount() == 2) {
            this.index = tblSinhVien.rowAtPoint(evt.getPoint());
            String manvtam = (String) tblSinhVien.getValueAt(index, 1);
            if (vaiTro) {
                if (manvtam.equals(MaNV) | tblSinhVien.getValueAt(index, 4).equals("Nhân viên")) {
                    if (this.index >= 0) {
                        this.edit();
                        jTabbedPane1.setSelectedIndex(0);
                    }
                } else {
                    DialogHelper.alert(this, "Không thể chọn trưởng phòng khác");
                }
            } else if (!vaiTro & manvtam.equals(MaNV)) {
                if (this.index >= 0) {
                    this.edit();
                    jTabbedPane1.setSelectedIndex(0);
                }
            } else {
                DialogHelper.alert(this, "Không thể chọn ");
            }

        }
    }//GEN-LAST:event_tblSinhVienMouseClicked

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        this.index--;
        this.edit();
        check();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.index = tblSinhVien.getRowCount() - 1;
        this.edit();
        check();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.index++;
        this.edit();
        check();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.index = 0;
        this.edit();
        check();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clear();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        if (Check()) {
            insert();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.load();
        this.setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
//        printExe();
        printExcel(tblSinhVien);
//        try {
//            XSSFWorkbook fWorkbook = new XSSFWorkbook();
//            XSSFSheet sheet = fWorkbook.createSheet("Danh sách");
//            XSSFRow row = null;
//            Cell cell = null;
//            row = sheet.createRow(3);
//            cell = row.createCell(0, CellType.STRING);
//            cell.setCellValue("STT");
//
//            cell = row.createCell(1, CellType.STRING);
//            cell.setCellValue("MaNV");
//
//            cell = row.createCell(2, CellType.STRING);
//            cell.setCellValue("Mật Khẩu");
//
//            cell = row.createCell(3, CellType.STRING);
//            cell.setCellValue("Họ Tên");
//
//            cell = row.createCell(4, CellType.STRING);
//            cell.setCellValue("Vai Trò");
//
//            for (int i = 0; i < arr.size(); i++) { lấy tất cả danh sách trong đó
//                model.NhanVien nhanVien = arr.get(i);
//                row = sheet.createRow(4 + i);
//
//                cell = row.createCell(0, CellType.NUMERIC);
//                cell.setCellValue(i + 1);
//
//                cell = row.createCell(1, CellType.STRING);
//                cell.setCellValue(arr.get(i).getMaNV());
//
//                cell = row.createCell(2, CellType.STRING);
//                cell.setCellValue(arr.get(i).getMatKhau());
//
//                cell = row.createCell(3, CellType.STRING);
//                cell.setCellValue(arr.get(i).getHoTen());
//
//                cell = row.createCell(4, CellType.STRING);
//                cell.setCellValue(arr.get(i).isVaiTro());
//
//            }
//            File f = new File("D://danhsach.xlsx");
//            try {
//                FileOutputStream fis = new FileOutputStream(f);
//                fWorkbook.write(fis);
//                fis.close();
//            } catch (FileNotFoundException ex) {
//                ex.printStackTrace();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            JOptionPane.showMessageDialog(this, "In thành công!");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Lỗi mở file");
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTime;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JTable tblSinhVien;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtXacNhanMatKhau;
    // End of variables declaration//GEN-END:variables
}
