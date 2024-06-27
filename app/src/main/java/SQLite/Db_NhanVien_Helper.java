package SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db_NhanVien_Helper extends SQLiteOpenHelper {
    final public static String dbname = "dbnhanvien";
    final public static int dbversion = 1;


    public Db_NhanVien_Helper(@Nullable Context context) {
        super(context, dbname, null, dbversion);
    }

    // Tạo dữ liệu trong SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {
        String lenhsql = "create table NHANVIEN(MANV integer primary key not null, TENNV text, LUONG integer)";
        db.execSQL(lenhsql);

        //thêm - tạo dữ liệu mặc định ( dữ liệu mẫu )
        lenhsql = "insert into NHANVIEN values (101, 'Nguyễn Văn A', 1000)";
        db.execSQL(lenhsql);
        lenhsql = "insert into NHANVIEN values (102, 'Nguyễn Văn B', 2000)";
        db.execSQL(lenhsql);
        lenhsql = "insert into NHANVIEN values (103, 'Nguyễn Văn C', 3000)";
        db.execSQL(lenhsql);
    }

    // Cập nhật dữ liệu trong SQLite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // xóa bảng NHANVIEN
        String lenhsql = "drop table if exists NHANVIEN";
        db.execSQL(lenhsql);
        onCreate(db);
    }
}

