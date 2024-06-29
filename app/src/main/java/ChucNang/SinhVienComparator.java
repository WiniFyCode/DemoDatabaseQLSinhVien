package ChucNang;

import java.util.Comparator;

import model.SINHVIEN;

// Comparator để so sánh các đối tượng SINHVIEN
public class SinhVienComparator implements Comparator<SINHVIEN> {
    @Override// Phương thức so sánh hai đối tượng SINHVIEN
    public int compare(SINHVIEN sv1, SINHVIEN sv2) {
        // So sánh MSSV của hai sinh viên
        int result = sv1.MSSV.compareTo(sv2.MSSV);
        if (result != 0) {
            return result; // Trả về kết quả so sánh MSSV nếu khác nhau
        }

        // Nếu MSSV giống nhau, so sánh tên sinh viên
        return sv1.TENSV.compareTo(sv2.TENSV); //Trả về kết quả so sánh tên sinh viên
    }
}