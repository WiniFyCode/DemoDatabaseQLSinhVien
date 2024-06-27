package ChucNang;

import java.util.Comparator;

import model.SINHVIEN;

public class SinhVienComparator implements Comparator<SINHVIEN> {
    @Override
    public int compare(SINHVIEN sv1, SINHVIEN sv2) {
        // Trải chuỗi MSSV thành số nguyên để so sánh
        int mssv1 = Integer.parseInt(sv1.MSSV.substring(2));
        int mssv2 = Integer.parseInt(sv2.MSSV.substring(2));
        return Integer.compare(mssv1, mssv2);
    }}