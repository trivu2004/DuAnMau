/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package until;

import DAO.NhanVienDAO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import helper.ShareHelper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.NhanVien;

/**
 *
 * @author USER
 */
public class qrcode {

    public static void main(String[] args) {
        String User = null, MaNV = null;
        boolean vaiTro = false;
        NhanVienDAO dao = new NhanVienDAO();
        List<NhanVien> list = dao.vaiTro(ShareHelper.USER + "");
        for (NhanVien nhanVien : list) {
            vaiTro = nhanVien.isVaiTro();
            User = nhanVien.getHoTen();
            MaNV = nhanVien.getMaNV();
        }
        String data = MaNV + "" + User + " "+ vaiTro; // Dữ liệu bạn muốn chứa trong mã QR code
        String filePath = "E:\\DuAnMau\\src\\image\\qrcode.png"; // Đường dẫn đến tệp hình ảnh QR code

        int width = 300; // Chiều rộng của mã QR code
        int height = 300; // Chiều cao của mã QR code

        // Đối tượng Map chứa các thông số cấu hình cho mã QR code
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // Độ sửa lỗi cao
        hints.put(EncodeHintType.MARGIN, 2); // Độ rộng viền

        try {
            // Tạo đối tượng BitMatrix từ dữ liệu và thông số cấu hình
            BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);
            // Tạo hình ảnh từ BitMatrix và lưu vào tệp
            File outputFile = new File(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", outputFile.toPath());
            System.out.println("Mã QR code đã được tạo và lưu vào " + filePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

    }
}
