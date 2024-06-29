package SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Lớp helper để quản lý cơ sở dữ liệu SQLite
public class Db_NhanVien_Helper extends SQLiteOpenHelper {
final public static String dbname = "dbnhanvien"; // Tên cơ sở dữ liệu
final public static int dbversion = 1; // Phiên bản cơ sở dữ liệu

// Constructor của lớp helper
public Db_NhanVien_Helper(@Nullable Context context) {
    super(context, dbname, null, dbversion);
}

// Phương thức được gọi khi cơ sở dữ liệu được tạo lần đầu tiên
@Override
public void onCreate(SQLiteDatabase db) {
    // Tạo bảng NHANVIEN
    String lenhsql = "create table NHANVIEN(MANV integer primary key not null, TENNV text, LUONG integer)";
    db.execSQL(lenhsql);

    // Thêm dữ liệu mẫu vào bảng NHANVIEN
    lenhsql = "insert into NHANVIEN values (101, 'Nguyễn Văn A', 1000)";
    db.execSQL(lenhsql);
    lenhsql = "insert into NHANVIEN values (102, 'Nguyễn Văn B', 2000)";
    db.execSQL(lenhsql);
    lenhsql = "insert into NHANVIEN values (103, 'Nguyễn Văn C', 3000)";
    db.execSQL(lenhsql);
}

// Phương thức được gọi khi phiên bản cơ sở dữ liệu được nâng cấp
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Xóa bảng NHANVIEN nếu nó tồn tại
    String lenhsql = "drop table if exists NHANVIEN";
    db.execSQL(lenhsql);

    // Tạo lại bảng NHANVIEN và thêm dữ liệu mẫu
    onCreate(db);
}
}