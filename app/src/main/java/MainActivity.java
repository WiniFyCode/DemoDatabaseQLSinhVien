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

import com.example.demodatabase.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapter.SinhVienAdapter;
import ChucNang.SinhVienComparator;
import SQLite.QLSinhVien_OpenHelper;
import model.SINHVIEN;

public class MainActivity extends AppCompatActivity {

    QLSinhVien_OpenHelper dbHelper;
    ListView listView;
    SinhVienAdapter adapter;
    List<SINHVIEN> sinhVienList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new QLSinhVien_OpenHelper(this);
        listView = findViewById(R.id.lvSinhVien); // Thay thế bằng ID của ListView trong activity_main.xml
        sinhVienList = new ArrayList<>();
        adapter = new SinhVienAdapter(this, R.layout.item_sinhvien, sinhVienList);
        listView.setAdapter(adapter);

        loadData();

        Button btnThem = findViewById(R.id.btnThem);
        Button btnSua = findViewById(R.id.btnSua);
        Button btnXoa = findViewById(R.id.btnXoa);

        // Đăng ký sự kiện cho các nút
        CheckBox cbNam = findViewById(R.id.cbNam); // Thay thế bằng ID của CheckBox Nam
        CheckBox cbNu = findViewById(R.id.cbNu); // Thay thế bằng ID của CheckBox Nữ

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSinhVien();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suaSinhVien();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoaSinhVien();
            }
        });


        cbNam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbNu.setEnabled(!isChecked); // Vô hiệu hóa cbNu khi cbNam được chọn, và kích hoạt lại khi cbNam bị bỏ chọn
            }
        });

        cbNu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbNam.setEnabled(!isChecked); // Vô hiệu hóa cbNam khi cbNu được chọn, và kích hoạt lại khi cbNu bị bỏ chọn
            }
        });
    }

    private void loadData() {
        sinhVienList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(QLSinhVien_OpenHelper.TABLE_SINHVIEN, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String mssv = cursor.getString(cursor.getColumnIndexOrThrow(QLSinhVien_OpenHelper.COLUMN_MSSV));
            String tensv = cursor.getString(cursor.getColumnIndexOrThrow(QLSinhVien_OpenHelper.COLUMN_TENSV));
            boolean gioitinh = cursor.getInt(cursor.getColumnIndexOrThrow(QLSinhVien_OpenHelper.COLUMN_GIOITINH)) == 1;
            sinhVienList.add(new SINHVIEN(mssv, tensv, gioitinh));
        }
        //Comparator
        Collections.sort(sinhVienList, new SinhVienComparator());

        cursor.close();
        db.close();
        adapter.notifyDataSetChanged();
    }

    private void themSinhVien() {
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        TextInputEditText edtTENSV = findViewById(R.id.edtTENSV);

        CheckBox cbNam = findViewById(R.id.cbNam);
        CheckBox cbNu = findViewById(R.id.cbNu);

        String mssv = edtMSSV.getText().toString();
        String tensv = edtTENSV.getText().toString();

        boolean gioitinh = cbNam.isChecked();

        if (mssv.isEmpty() || tensv.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QLSinhVien_OpenHelper.COLUMN_MSSV, mssv);
        values.put(QLSinhVien_OpenHelper.COLUMN_TENSV, tensv);
        values.put(QLSinhVien_OpenHelper.COLUMN_GIOITINH, gioitinh ? 1 : 0);

        long result = db.insert(QLSinhVien_OpenHelper.TABLE_SINHVIEN, null, values);
        db.close();

        if (result > 0) {
            Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
            loadData();
            clearInputFields();
        } else {
            Toast.makeText(this, "Thêm sinh viên thất bại", Toast.LENGTH_SHORT).show();
        }

        // Kích hoạt lại các CheckBox
        cbNam.setEnabled(true);
        cbNu.setEnabled(true);

        // Cập nhật ListView
    }

    private void suaSinhVien() {
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        TextInputEditText edtTENSV = findViewById(R.id.edtTENSV);
        CheckBox cbNam = findViewById(R.id.cbNam);

        String mssv = edtMSSV.getText().toString();
        String tensv = edtTENSV.getText().toString();
        boolean gioitinh = cbNam.isChecked();

        if (mssv.isEmpty() || tensv.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QLSinhVien_OpenHelper.COLUMN_TENSV, tensv);
        values.put(QLSinhVien_OpenHelper.COLUMN_GIOITINH, gioitinh ? 1 : 0);

        int result = db.update(QLSinhVien_OpenHelper.TABLE_SINHVIEN, values, QLSinhVien_OpenHelper.COLUMN_MSSV + "= ?", new String[]{mssv});
        db.close();

        if (result > 0) {
            Toast.makeText(this, "Sửa sinh viên thành công", Toast.LENGTH_SHORT).show();
            loadData();
            clearInputFields();
        } else {
            Toast.makeText(this, "Sửa sinh viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void xoaSinhVien() {
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        String mssv = edtMSSV.getText().toString();

        if (mssv.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập MSSV để xóa", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(QLSinhVien_OpenHelper.TABLE_SINHVIEN, QLSinhVien_OpenHelper.COLUMN_MSSV + " = ?", new String[]{mssv});
        db.close();

        if (result > 0) {
            Toast.makeText(this, "Xóa sinh viên thành công", Toast.LENGTH_SHORT).show();
            loadData();
            clearInputFields();
        } else {
            Toast.makeText(this, "Xóa sinh viên thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputFields() {
        TextInputEditText edtMSSV = findViewById(R.id.edtMSSV);
        TextInputEditText edtTENSV = findViewById(R.id.edtTENSV);
        CheckBox cbNam = findViewById(R.id.cbNam);
        CheckBox cbNu = findViewById(R.id.cbNu); // Thêm cbNu vào đây

        edtMSSV.setText("");
        edtTENSV.setText("");
        cbNam.setChecked(false); // Reset cbNam về trạng thái chưa được chọn
        cbNu.setChecked(false); // Reset cbNu về trạng thái chưa được chọn
        cbNam.setEnabled(true); // Kích hoạt lại cbNam
        cbNu.setEnabled(true); // Kích hoạt lại cbNu
    }

}