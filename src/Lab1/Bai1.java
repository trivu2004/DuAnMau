/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Lab1;

import DAO.NhanVienDAO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import helper.DialogHelper;
import helper.ShareHelper;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import model.NhanVien;
import org.apache.log4j.PropertyConfigurator;
import until.Log;

public class Bai1 extends javax.swing.JFrame {

    String path = "E:\\DuAnMau\\src\\image\\fpt.png";
    ImageIcon imageIcon = new ImageIcon(path);

    NhanVienDAO dao = new NhanVienDAO();
    String User, MaNV;
    boolean vaiTro;

    void kk() {
        PropertyConfigurator.configure("E:\\DuAnMau\\src\\Log\\log4j.properties");
        setTitle("Hệ Thống Quản Lý Đào Tạo");
        setIconImage(imageIcon.getImage());
        setLocationRelativeTo(null);
//        qrcode();
        new Timer(1000, new ActionListener() {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent ae) {
                lblTime.setText(timeFormat.format(new Date()));
            }
        }).start();
        this.openChao();
        this.openDangNhap();
        List<NhanVien> list = dao.vaiTro(ShareHelper.USER + "");
        for (NhanVien nhanVien : list) {
            vaiTro = nhanVien.isVaiTro();
            User = nhanVien.getHoTen();
            MaNV = nhanVien.getMaNV();
            if (nhanVien.isVaiTro()) {
                lblVaiTro.setText("Vai trò: Trưởng phòng | " + " " + "Người dùng: " + nhanVien.getMaNV());
                Log.logger.info("Vai trò: Trưởng phòng | " + " " + "Người dùng: " + nhanVien.getMaNV());
            } else {
                lblVaiTro.setText("Vai trò: Nhân viên | " + " " + "Người dùng: " + nhanVien.getMaNV());
                Log.logger.info("Vai trò: Nhân viên | " + " " + "Người dùng: " + nhanVien.getMaNV());
                mnuChuyenDe.setEnabled(vaiTro);
            }

        }

    }

    public Bai1() {
        initComponents();
        kk();
    }
    
    void openChao() {
        new ChaoJDialog(this, true).setVisible(true);
    }

    void openDangNhap() {
        new DangNhap1(this, true).setVisible(true);
    }

    void logoff() {
        ShareHelper.logoff();
        this.openDangNhap();
    }

    void exit() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn kết thúc?")) {
            System.exit(0);
        }
    }

    void exit1() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn kết thúc?")) {
            this.dispose();
//            this.openDangNhap();
            new Bai1().setVisible(true);
        }
    }

    void openThongKe(int index) {
        if (ShareHelper.authenticated()) {
            new ThongKeJFrame(index).setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openNhanVien() {
        if (ShareHelper.authenticated()) {
            new QuanLyNhanVien().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openKhoaHoc() {
        if (ShareHelper.authenticated()) {
            new QuanLyKhoaHoc().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openChuyenDe() {
        if (ShareHelper.authenticated()) {
            new QuanLyChuyenDe().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openNguoiHoc() {
        if (ShareHelper.authenticated()) {
            new QuanLyNguoiHoc().setVisible(true);
        } else {
            DialogHelper.alert(this, "Vui lòng đăng nhập!");
        }
    }

    void openAbout() {
        new GioiThieuJDialog(this, true).setVisible(true);
    }

    void openDoiMatKhau() {
        new DoiMatKhauJFrame().setVisible(true);
    }

//    void qrcode() {
//        String data = "Ha noi mua thu";
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
//
//        // Write to file image
//        String outputFile = "/path/to/output/image.png";
//        Path path = FileSystems.getDefault().getPath(outputFile);
//        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
//
//    }
    void openWebsite() {
        try {
            Desktop.getDesktop().browse(new File("E:\\DuAnMau\\src\\helper\\demo.html").toURI());
        } catch (IOException ex) {
            DialogHelper.alert(this, "Không tìm thấy file hướng dẫn!");
            Log.logger.error(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator9 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tbaCongCu = new javax.swing.JToolBar();
        btnDangXuat = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnKetThuc = new javax.swing.JButton();
        btnChuyenDe = new javax.swing.JButton();
        btnNguoiHoc = new javax.swing.JButton();
        btnKhoaHoc = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        btnHuongDan = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        lblVaiTro = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        mnuHeThong = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        mnuQuanLy = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        btnNhanVien = new javax.swing.JMenuItem();
        mnuThongKe = new javax.swing.JMenu();
        mnuTungNam = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuDIemKhoa = new javax.swing.JMenuItem();
        mnuKhoaHoc = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnuChuyenDe = new javax.swing.JMenuItem();
        mnuTroGiup = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N

        tbaCongCu.setRollover(true);

        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Exit.png"))); // NOI18N
        btnDangXuat.setText("Đăng Xuất");
        btnDangXuat.setFocusable(false);
        btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setIconTextGap(7);
        btnDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnDangXuat);
        tbaCongCu.add(jSeparator7);

        btnKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Stop.png"))); // NOI18N
        btnKetThuc.setText("Kết Thúc");
        btnKetThuc.setFocusable(false);
        btnKetThuc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKetThuc.setIconTextGap(7);
        btnKetThuc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetThucActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnKetThuc);

        btnChuyenDe.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lists.png"))); // NOI18N
        btnChuyenDe.setText("Chuyên Đề");
        btnChuyenDe.setFocusable(false);
        btnChuyenDe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChuyenDe.setIconTextGap(7);
        btnChuyenDe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenDeActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnChuyenDe);

        btnNguoiHoc.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnNguoiHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Conference.png"))); // NOI18N
        btnNguoiHoc.setText("Người Học");
        btnNguoiHoc.setFocusable(false);
        btnNguoiHoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNguoiHoc.setIconTextGap(7);
        btnNguoiHoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNguoiHocActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnNguoiHoc);

        btnKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Certificate.png"))); // NOI18N
        btnKhoaHoc.setText("Khóa Học");
        btnKhoaHoc.setFocusable(false);
        btnKhoaHoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKhoaHoc.setIconTextGap(7);
        btnKhoaHoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoaHocActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnKhoaHoc);
        tbaCongCu.add(jSeparator8);

        btnHuongDan.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnHuongDan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Globe.png"))); // NOI18N
        btnHuongDan.setText("Hướng Dẫn");
        btnHuongDan.setFocusable(false);
        btnHuongDan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHuongDan.setIconTextGap(7);
        btnHuongDan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHuongDan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuongDanActionPerformed(evt);
            }
        });
        tbaCongCu.add(btnHuongDan);
        tbaCongCu.add(jSeparator10);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Icons8-Windows-8-Ecommerce-Bill.32.png"))); // NOI18N
        jButton1.setText("Mã QR Code:");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        tbaCongCu.add(jButton1);
        tbaCongCu.add(jSeparator12);

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblVaiTro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fpt.png"))); // NOI18N
        lblVaiTro.setText("Vai Trò: ");
        tbaCongCu.add(lblVaiTro);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbaCongCu, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tbaCongCu, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(21, 21, 21))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Problem.png"))); // NOI18N
        jLabel2.setText("Hệ Thống Quản Lý Đào Tạo");

        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        lblTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Alarm.png"))); // NOI18N
        lblTime.setText("02:30:54 PM");

        mnuHeThong.setText("Hệ Thống");
        mnuHeThong.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Key.png"))); // NOI18N
        jMenuItem1.setText("Đăng Nhập");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnuHeThong.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Exit.png"))); // NOI18N
        jMenuItem2.setText("Đăng Xuất");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnuHeThong.add(jMenuItem2);
        mnuHeThong.add(jSeparator1);

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        jMenuItem3.setText("Đổi Mật Khẩu");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        mnuHeThong.add(jMenuItem3);
        mnuHeThong.add(jSeparator2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Stop.png"))); // NOI18N
        jMenuItem4.setText("Kết Thúc");
        mnuHeThong.add(jMenuItem4);

        jMenuBar2.add(mnuHeThong);

        mnuQuanLy.setText("Quản Lý");
        mnuQuanLy.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Conference.png"))); // NOI18N
        jMenuItem5.setText("Người Học");
        mnuQuanLy.add(jMenuItem5);

        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lists.png"))); // NOI18N
        jMenuItem6.setText("Chuyên Đề");
        mnuQuanLy.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Certificate.png"))); // NOI18N
        jMenuItem7.setText("Khóa Học");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        mnuQuanLy.add(jMenuItem7);

        btnNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/User group.png"))); // NOI18N
        btnNhanVien.setText("Nhân Viên");
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });
        mnuQuanLy.add(btnNhanVien);

        jMenuBar2.add(mnuQuanLy);

        mnuThongKe.setText("Thống Kê");
        mnuThongKe.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N

        mnuTungNam.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnuTungNam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Clien list.png"))); // NOI18N
        mnuTungNam.setText("Người Học Từng Năm");
        mnuTungNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTungNamActionPerformed(evt);
            }
        });
        mnuThongKe.add(mnuTungNam);
        mnuThongKe.add(jSeparator3);

        mnuDIemKhoa.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnuDIemKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Card file.png"))); // NOI18N
        mnuDIemKhoa.setText("Bảng Điểm Khóa");
        mnuDIemKhoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDIemKhoaActionPerformed(evt);
            }
        });
        mnuThongKe.add(mnuDIemKhoa);

        mnuKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnuKhoaHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Bar chart.png"))); // NOI18N
        mnuKhoaHoc.setText("Điểm Từng Khóa Học");
        mnuKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuKhoaHocActionPerformed(evt);
            }
        });
        mnuThongKe.add(mnuKhoaHoc);
        mnuThongKe.add(jSeparator4);

        mnuChuyenDe.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnuChuyenDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Dollar.png"))); // NOI18N
        mnuChuyenDe.setText("Doanh Thu Từng Chuyên Đề");
        mnuChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuChuyenDeActionPerformed(evt);
            }
        });
        mnuThongKe.add(mnuChuyenDe);

        jMenuBar2.add(mnuThongKe);

        mnuTroGiup.setText("Trợ Giúp");
        mnuTroGiup.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Globe.png"))); // NOI18N
        jMenuItem13.setText("Hướng Dẫn Sử Dụng");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        mnuTroGiup.add(jMenuItem13);
        mnuTroGiup.add(jSeparator5);

        jMenuItem14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Home.png"))); // NOI18N
        jMenuItem14.setText("Giới Thiệu Sản Phẩm");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        mnuTroGiup.add(jMenuItem14);

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Problem.png"))); // NOI18N
        jMenuItem8.setText("Phản Hồi Yêu Cầu");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        mnuTroGiup.add(jMenuItem8);

        jMenuBar2.add(mnuTroGiup);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime)
                .addGap(63, 63, 63))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblTime))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        openDangNhap();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        openDoiMatKhau();

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        openAbout();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void mnuDIemKhoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDIemKhoaActionPerformed
        // TODO add your handling code here:
        openThongKe(1);
    }//GEN-LAST:event_mnuDIemKhoaActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        // TODO add your handling code here:
        openNhanVien();
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        exit1();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetThucActionPerformed
        // TODO add your handling code here:
        exit();
    }//GEN-LAST:event_btnKetThucActionPerformed

    private void btnNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNguoiHocActionPerformed
        // TODO add your handling code here:
        openNguoiHoc();
    }//GEN-LAST:event_btnNguoiHocActionPerformed

    private void btnChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenDeActionPerformed
        // TODO add your handling code here:
        openChuyenDe();
    }//GEN-LAST:event_btnChuyenDeActionPerformed

    private void btnKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoaHocActionPerformed

        openKhoaHoc();
    }//GEN-LAST:event_btnKhoaHocActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        logoff();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnuTungNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTungNamActionPerformed
        // TODO add your handling code here:
        openThongKe(0);
    }//GEN-LAST:event_mnuTungNamActionPerformed

    private void mnuKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuKhoaHocActionPerformed
        // TODO add your handling code here:
        openThongKe(2);
    }//GEN-LAST:event_mnuKhoaHocActionPerformed

    private void mnuChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuChuyenDeActionPerformed
        // TODO add your handling code here:
        openThongKe(3);
    }//GEN-LAST:event_mnuChuyenDeActionPerformed

    private void btnHuongDanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuongDanActionPerformed
        // TODO add your handling code here:
        openWebsite();
    }//GEN-LAST:event_btnHuongDanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new QRCode().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        new GMail.Gmail().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Bai1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bai1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bai1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bai1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bai1().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChuyenDe;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnKetThuc;
    private javax.swing.JButton btnKhoaHoc;
    private javax.swing.JButton btnNguoiHoc;
    private javax.swing.JMenuItem btnNhanVien;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JMenuItem mnuChuyenDe;
    private javax.swing.JMenuItem mnuDIemKhoa;
    private javax.swing.JMenu mnuHeThong;
    private javax.swing.JMenuItem mnuKhoaHoc;
    private javax.swing.JMenu mnuQuanLy;
    private javax.swing.JMenu mnuThongKe;
    private javax.swing.JMenu mnuTroGiup;
    private javax.swing.JMenuItem mnuTungNam;
    private javax.swing.JToolBar tbaCongCu;
    // End of variables declaration//GEN-END:variables
}
