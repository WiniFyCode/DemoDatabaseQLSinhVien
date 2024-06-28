package ChucNang;

import java.util.Comparator;

import model.SINHVIEN;

public class SinhVienComparator implements Comparator<SINHVIEN> {
    @Override
    public int compare(SINHVIEN sv1, SINHVIEN sv2) {
        // So sánh MSSV
        int result = sv1.MSSV.compareTo(sv2.MSSV);
        if (result != 0) {
            return result;
        }

        // Nếu MSSV giống nhau, so sánh tên sinh viên
        return sv1.TENSV.compareTo(sv2.TENSV);
    }
}