package com.example.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapter.SinhVienAdapter;
import ChucNang.SinhVienComparator;
import SQLite.QLSinhVien_OpenHelper;
import model.SINHVIEN;

// Khai báo lớp MainActivity kế thừa từ AppCompatActivity
public class MainActivity extends AppCompatActivity {
    // Khai báo các biến thành viên
    QLSinhVien_OpenHelper dbHelper; // Helper để tương tác với cơ sở dữ liệu
    ListView listView; // ListView để hiển thị danh sách sinh viên
    SinhVienAdapter adapter; // Adapter để quản lý dữ liệu cho ListView
    List<SINHVIEN> sinhVienList; // Danh sách các đối tượng SINHVIEN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Đặt layout cho Activity

        // Khởi tạo các thành phần
        dbHelper = new QLSinhVien_OpenHelper(this); // Khởi tạo helper cơ sở dữ liệu
        listView = findViewById(R.id.lvSinhVien); // Lấy tham chiếu đến ListView
        sinhVienList = new ArrayList<>(); // Khởi tạo danh sách sinh viên
        adapter = new SinhVienAdapter(this, R.layout.item_sinhvien, sinhVienList); // Khởi tạo adapter
        listView.setAdapter(adapter); // Đặt adapter cho ListView

        // Thêm các đối tượng SINHVIEN vào danh sách ( bộ dữ liệu mẫu, nên xóa đi )
        sinhVienList.add(new SINHVIEN("123", "Nguyễn Thanh Huy", true));
        // Nếu sinhVien.GIOITINH là true, hiển thị hình ảnh nam (R.drawable.male)
        // Nếu sinhVien.GIOITINH là false, hiển thị hình ảnh nữ (R.drawable.female)

        loadData(); // Tải dữ liệu từ cơ sở dữ liệu và hiển thị lên ListView

        // Lấy các nút từ layout
        Button btnThem = findViewById(R.id.btnThem);
        Button btnSua = findViewById(R.id.btnSua);
        Button btnXoa = findViewById(R.id.btnXoa);

        // Lấy các CheckBox từ layout
        CheckBox cbNam = findViewById(R.id.cbNam);
        CheckBox cbNu = findViewById(R.id.cbNu);

        // Đăng ký sự kiện click cho nút Thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSinhVien(); // Thêm sinh viên mới vào cơ sở dữ liệu
            }
        });

        // Đăng ký sự kiện click cho nút Sửa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaSinhVien(); // Sửa thông tin sinh viên trong cơ sở dữ liệu
            }
        });

        // Đăng ký sự kiện click cho nút Xóa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaSinhVien(); // Xóa sinh viên khỏi cơ sở dữ liệu
            }
        });

        // Đăng ký sự kiện thay đổi trạng thái cho CheckBox Nam
        cbNam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbNu.setEnabled(!isChecked); // Vô hiệu hóa CheckBox Nữ khi CheckBox Nam được chọn
            }
        });

        // Đăng ký sự kiện thay đổi trạng thái cho CheckBox Nữ
        cbNu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbNam.setEnabled(!isChecked); // Vô hiệu hóa CheckBox Nam khi CheckBox Nữ được chọn
            }
        });

        // Đăng ký sự kiện click cho ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            SINHVIEN sinhVien = sinhVienList.get(position); // Lấy sinh viên tại vị trí được click

            // Lấy các trường nhập liệu
            TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
            TextInputEditText edtTENSV = findViewById(R.id.edtTENSV);

            // Gán dữ liệu lên các trường nhập liệu ( gắn dữ liệu ở listview lên edit và checkbox )
            edtMSSV.setText(sinhVien.MSSV);
            edtTENSV.setText(sinhVien.TENSV);
            if (sinhVien.GIOITINH) {
                cbNam.setChecked(true);
                cbNu.setChecked(false);
            } else {
                cbNam.setChecked(false);
                cbNu.setChecked(true);
            }
        });
    }


    // Tải dữ liệu từ cơ sở dữ liệu và hiển thị lên ListView
    private void loadData() {
        sinhVienList.clear(); // Xóa tất cả các phần tử khỏi danh sách sinhVienList để chuẩn bị cho việc tải dữ liệu mới.
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Mở cơ sở dữ liệu ở chế độ đọc

        // Truy vấn tất cả các sinh viên từ bảng SINHVIEN
        Cursor cursor = db.query(QLSinhVien_OpenHelper.TABLE_SINHVIEN, null,null, null, null, null, null);
        while (cursor.moveToNext()) {
            // Lấy dữ liệu từ Cursor

            // Cách 1
//            String mssv = cursor.getString(cursor.getColumnIndexOrThrow(QLSinhVien_OpenHelper.COLUMN_MSSV));
//            String tensv = cursor.getString(cursor.getColumnIndexOrThrow(QLSinhVien_OpenHelper.COLUMN_TENSV));
//            boolean gioitinh = cursor.getInt(cursor.getColumnIndexOrThrow(QLSinhVien_OpenHelper.COLUMN_GIOITINH)) == 1;
//
//            // Thêm sinh viên vào danh sách
//            sinhVienList.add(new SINHVIEN(mssv, tensv, gioitinh));

            // Cách 2
            sinhVienList.add(new SINHVIEN(cursor.getString(0), cursor.getString(1), cursor.getInt(2) == 1));
        }

        // Sắp xếp danh sách sinh viên theo MSSV
        Collections.sort(sinhVienList, new SinhVienComparator());

        cursor.close(); // Đóng Cursor
        db.close(); // Đóng cơ sở dữ liệu
        adapter.notifyDataSetChanged(); // Cập nhật ListView
    }

    // Thêm sinh viên mới vào cơ sở dữ liệu
    private void themSinhVien() {
        // Lấy dữ liệu từ các trường nhập liệu
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        TextInputEditText edtTENSV = findViewById(R.id.edtTENSV);
        CheckBox cbNam = findViewById(R.id.cbNam);
        CheckBox cbNu = findViewById(R.id.cbNu);

        String mssv = edtMSSV.getText().toString();
        String tensv = edtTENSV.getText().toString();
        boolean gioitinh = cbNam.isChecked();

        // Kiểm tra xem các trường nhập liệu có trống không
        if (mssv.isEmpty() || tensv.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mở cơ sở dữ liệu ở chế độ ghi
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Tạo một ContentValues để lưu trữ dữ liệu sinh viên
        ContentValues values = new ContentValues();
        values.put(QLSinhVien_OpenHelper.COLUMN_MSSV, mssv);
        values.put(QLSinhVien_OpenHelper.COLUMN_TENSV, tensv);
        values.put(QLSinhVien_OpenHelper.COLUMN_GIOITINH, gioitinh ? 1 : 0);
        // Nếu GIOTTINH là true, đặt giá trị 1, ngược lại đặt giá trị 0

        // Thêm sinh viên vào cơ sở dữ liệu
        long result = db.insert(QLSinhVien_OpenHelper.TABLE_SINHVIEN, null, values);
        db.close(); // Đóng cơ sở dữ liệu

        // Kiểm tra kết quả thêm sinh viên
        if (result > 0) {
            Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
            loadData(); // Tải lại dữ liệu và cập nhật ListView
            clearInputFields(); // Xóa các trường nhập liệu
        } else {
            Toast.makeText(this, "Thêm sinh viên thất bại", Toast.LENGTH_SHORT).show();
        }

        // Kích hoạt lại các CheckBox
        cbNam.setEnabled(true);
        cbNu.setEnabled(true);
    }

    // Sửa thông tin sinh viên trong cơ sở dữ liệu
    private void suaSinhVien() {
        // Lấy dữ liệu từ các trường nhập liệu
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        TextInputEditText edtTENSV = findViewById(R.id.edtTENSV);
        CheckBox cbNam = findViewById(R.id.cbNam);

        String mssv = edtMSSV.getText().toString();
        String tensv = edtTENSV.getText().toString();
        boolean gioitinh = cbNam.isChecked();

        // Kiểm tra xem các trường nhập liệu có trống không
        if (mssv.isEmpty() || tensv.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mở cơ sở dữ liệu ở chế độ ghi
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Tạo một ContentValues để lưu trữ dữ liệu sinh viên
        ContentValues values = new ContentValues();
        values.put(QLSinhVien_OpenHelper.COLUMN_TENSV, tensv);
        values.put(QLSinhVien_OpenHelper.COLUMN_GIOITINH, gioitinh ? 1 : 0);

        // Cập nhật thông tin sinh viêntrong cơ sở dữ liệu
        int result = db.update(QLSinhVien_OpenHelper.TABLE_SINHVIEN, values, QLSinhVien_OpenHelper.COLUMN_MSSV + "= ?", new String[]{mssv});
        db.close(); // Đóng cơ sở dữ liệu// Kiểm tra kết quả sửa sinh viên
        if (result > 0) {
            Toast.makeText(this, "Sửa sinh viên thành công", Toast.LENGTH_SHORT).show();
            loadData(); // Tải lại dữ liệu và cập nhật ListView
            clearInputFields(); // Xóa các trường nhập liệu
        } else {
            Toast.makeText(this, "Sửa sinh viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // Xóa sinh viên khỏi cơ sở dữ liệu
    private void xoaSinhVien() {// Lấy MSSV từ trường nhập liệu
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        String mssv = edtMSSV.getText().toString();

        // Kiểm tra xem MSSV có trống không
        if (mssv.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập MSSV để xóa", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mở cơ sở dữ liệu ở chế độ ghi
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Xóa sinh viên khỏi cơ sở dữ liệu
        int result = db.delete(QLSinhVien_OpenHelper.TABLE_SINHVIEN, QLSinhVien_OpenHelper.COLUMN_MSSV + " = ?", new String[]{mssv});
        db.close(); // Đóng cơ sở dữ liệu

        // Kiểm tra kết quả xóa sinh viên
        if (result > 0) {
            Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show();
            loadData(); // Tải lại dữ liệu và cập nhật ListView
            clearInputFields(); // Xóa các trường nhập liệu
        } else {
            Toast.makeText(this, "Xóa sinh viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // Xóa các trường nhập liệu và reset các CheckBox
    private void clearInputFields() {
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        TextInputEditText edtTENSV = findViewById(R.id.edtTENSV);
        CheckBox cbNam = findViewById(R.id.cbNam);
        CheckBox cbNu = findViewById(R.id.cbNu);

        edtMSSV.setText(""); // Xóa nội dung của trường MSSV
        edtTENSV.setText(""); // Xóa nội dung của trường TENSV
        cbNam.setChecked(false); // Bỏ chọn CheckBox Nam
        cbNu.setChecked(false); // Bỏ chọn CheckBox Nữ
        cbNam.setEnabled(true); // Kích hoạt CheckBox Nam
        cbNu.setEnabled(true); // Kích hoạt CheckBox Nữ
    }

}