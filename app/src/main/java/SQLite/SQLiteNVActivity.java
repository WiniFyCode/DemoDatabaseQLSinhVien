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

public class SQLiteNVActivity extends AppCompatActivity {

    Db_NhanVien_Helper dpNhanVienHelper;
    SQLiteDatabase database;
    ListView lvNhanvien;

    ArrayList<String> dulieu = new ArrayList<>();@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite_nvactivity);

        lvNhanvien = findViewById(R.id.listNhanVien);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dulieu);
        lvNhanvien.setAdapter(adapter);

        dpNhanVienHelper = new Db_NhanVien_Helper(this);
        database = dpNhanVienHelper.getWritableDatabase();

        //insert
        //insert
        ContentValues values = new ContentValues();
        values.put("MANV", 106); // Thay 105 bằng giá trị MANV mong muốn
        values.put("TENNV", "Nguyễn Văn F");
        values.put("LUONG", 6000);

        if (database.insert("NHANVIEN", null, values) > 0) {
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Không thêm được", Toast.LENGTH_LONG).show();
        }

        // ĐỌC DỮ LIỆU
        Cursor cursor = database.query("NHANVIEN", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String tam = cursor.getString(0) + " - " + cursor.getString(cursor.getColumnIndexOrThrow("TENNV")) + " - " + cursor.getString(cursor.getColumnIndexOrThrow("LUONG"));
            dulieu.add(tam);
        }
        cursor.close(); // Đóng cursor
        adapter.notifyDataSetChanged();

        database.close(); // Đóng database
    }
}