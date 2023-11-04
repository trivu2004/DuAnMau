/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package until;

import DAO.ThongKeDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import jxl.Workbook;
import model.KhoaHoc;
import model.NhanVien;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import Lab1.*;

/**
 *
 * @author USER
 */
public class Exe {

    ArrayList<NhanVien> arr = new ArrayList<>();
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

           
}
