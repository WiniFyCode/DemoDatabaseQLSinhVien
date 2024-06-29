package SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demodatabase.R;

import java.util.ArrayList;

// Activity để hiển thị danh sách nhân viên từ cơ sở dữ liệu SQLite
public class SQLiteNVActivity extends AppCompatActivity {

    Db_NhanVien_Helper dpNhanVienHelper; // Helper để quản lý cơ sở dữ liệu
    SQLiteDatabase database; // Đối tượng SQLiteDatabase để tương tác với cơ sở dữ liệu
    ListView lvNhanvien; // ListViewđể hiển thị danh sách nhân viên

    ArrayList<String> dulieu = new ArrayList<>(); // Danh sách chuỗi để lưu trữ dữ liệu nhân viên

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Kích hoạt chế độ hiển thị toàn màn hình
        setContentView(R.layout.activity_sqlite_nvactivity); // Đặt layout cho Activity

        lvNhanvien = findViewById(R.id.listNhanVien); // Lấy ListView từ layout

        // Tạo ArrayAdapter để hiển thị dữ liệu trong ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dulieu);
        lvNhanvien.setAdapter(adapter);

        // Khởi tạo helper và mở cơ sở dữ liệu ở chế độ ghi
        dpNhanVienHelper = new Db_NhanVien_Helper(this);
        database = dpNhanVienHelper.getWritableDatabase();

        // Thêm một nhân viên mới vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("MANV", 106);
        values.put("TENNV", "Nguyễn Văn F");
        values.put("LUONG", 6000);

        // Kiểm tra kết quả thêm nhân viên
        if (database.insert("NHANVIEN", null, values) > 0) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Không thêm được", Toast.LENGTH_LONG).show();
        }

        // Đọc dữ liệu từ cơ sở dữ liệu
            Cursor cursor = database.query("NHANVIEN", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                // Lấy thông tin nhân viên và thêm vào danh sách dulieu
            String tam = cursor.getString(0) + " - " + cursor.getString(cursor.getColumnIndexOrThrow("TENNV")) + " - " + cursor.getString(cursor.getColumnIndexOrThrow("LUONG"));
            dulieu.add(tam);
        }
        cursor.close(); // Đóng cursor
        adapter.notifyDataSetChanged(); // Cập nhật ListView

        database.close(); // Đóng cơ sở dữ liệu
    }
}