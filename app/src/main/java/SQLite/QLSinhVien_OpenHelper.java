package SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Lớp helper đểquản lý cơ sở dữ liệu SQLite cho ứng dụng quản lý sinh viên
public class QLSinhVien_OpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLSinhVien.db"; // Tên cơ sở dữ liệu
    private static final int DATABASE_VERSION = 2; // Phiên bản cơ sở dữ liệu

    // Tên bảng và cột cho bảng KHOA
    public static final String TABLE_KHOA = "KHOA";
    public static final String COLUMN_MAKHOA = "MAKHOA";
    public static final String COLUMN_TENKHOA = "TENKHOA";

    // Tên bảng và cột cho bảng SINHVIEN
    public static final String TABLE_SINHVIEN = "SINHVIEN";
    public static final String COLUMN_MSSV = "MSSV";
    public static final String COLUMN_TENSV = "TENSV";
    public static final String COLUMN_GIOITINH = "GIOITINH";
    public static final String COLUMN_MAKHOA_SV = "MAKHOA"; // Đổi tên để tránh trùng lặp

    // Câu lệnh SQL để tạo bảng KHOA
    private static final String CREATE_TABLE_KHOA =
            "CREATE TABLE " + TABLE_KHOA + " (" +
                    COLUMN_MAKHOA + " TEXT PRIMARY KEY, " +
                    COLUMN_TENKHOA + " TEXT)";

    // Câu lệnh SQL để tạo bảng SINHVIEN với khóa ngoại tham chiếu đến bảng KHOA
    private static final String CREATE_TABLE_SINHVIEN =
            "CREATE TABLE " + TABLE_SINHVIEN + " (" +
                    COLUMN_MSSV + " TEXT PRIMARY KEY, " +
                    COLUMN_TENSV + " TEXT, " +
                    COLUMN_GIOITINH + " INTEGER, " +
                    COLUMN_MAKHOA_SV + " TEXT, " +
                    "FOREIGN KEY(" + COLUMN_MAKHOA_SV + ") REFERENCES " + TABLE_KHOA + "(" + COLUMN_MAKHOA + "))";

    // Constructor của lớp helper
    public QLSinhVien_OpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức được gọi khi cơ sở dữ liệu được tạo lần đầu tiên
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Kiểm tra xem bảng KHOA đã tồn tại chưa
        if (!tableExists(db, TABLE_KHOA)) {
            db.execSQL(CREATE_TABLE_KHOA); // Tạo bảng KHOA nếu chưa tồn tại
        }

        // Kiểm tra xem bảng SINHVIEN đã tồn tại chưa
        if (!tableExists(db, TABLE_SINHVIEN)) {
            db.execSQL(CREATE_TABLE_SINHVIEN); // Tạo bảng SINHVIEN nếu chưa tồn tại
        }
    }

    // Phương thức kiểm tra xem bảng đã tồn tại chưa
    private boolean tableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Phương thức được gọi khi phiên bản cơ sở dữ liệu được nâng cấp
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng SINHVIEN và KHOA nếu chúng tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHOA);

        // Tạo lại các bảng
        onCreate(db);
    }
}