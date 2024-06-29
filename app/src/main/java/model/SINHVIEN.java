package model;

// Lớp đại diện cho một sinh viên
public class SINHVIEN {
    public String MSSV; // Mã số sinh viên
    public String TENSV; // Tên sinh viên
    public boolean GIOITINH; //Giới tính (true: Nam, false: Nữ)

    // Constructor để khởi tạo một đối tượng SINHVIEN
    public SINHVIEN(String MSSV, String TENSV, boolean GIOITINH) {
        this.MSSV = MSSV; // Gán giá trị MSSV
        this.TENSV = TENSV; // Gán giá trị TENSV
        this.GIOITINH = GIOITINH; // Gán giá trị GIOITINH
    }
}