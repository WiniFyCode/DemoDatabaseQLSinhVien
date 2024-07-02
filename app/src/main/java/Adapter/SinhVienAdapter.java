package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demodatabase.R;

import java.util.List;

import model.SINHVIEN;

// Adapter để hiển thị danh sách sinh viên trong ListView
public class SinhVienAdapter extends ArrayAdapter<SINHVIEN> {

    // Constructor của adapter
    public SinhVienAdapter(@NonNull Context context, int resource, @NonNull List<SINHVIEN> objects) {
        super(context, resource, objects);
    }

    // Phương thức được gọi để tạoView cho mỗi mục trong ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView; // Tái sử dụng View nếu có thể
        if (view == null) {
            // Tạo View mới từ layout item_sinhvien.xml
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_sinhvien, parent, false);
        }

        // Lấy đối tượng SINHVIEN tại vị trí position
        SINHVIEN sinhVien = getItem(position);
        if (sinhVien != null) {
            // Lấy các thành phần View từ layout item_sinhvien.xml
            TextView tvMasv = view.findViewById(R.id.tvMasv);
            TextView tvTensv = view.findViewById(R.id.tvTensv);
            ImageView imgGioitinh = view.findViewById(R.id.imgGioiTinh);

            // Hiển thị MSSV và tên sinh viên
            tvMasv.setText(sinhVien.MSSV);
            tvTensv.setText(sinhVien.TENSV);

            // Cách 1 để hiển thị hình ảnh giới tính
            imgGioitinh.setImageResource(sinhVien.GIOITINH ? R.drawable.male : R.drawable.female);

            // Cách 2 để hiển thị hình ảnh giới tính
            // if (sinhVien.GIOITINH) {
            //     imgGioitinh.setImageResource(R.drawable.male);
            // } else {
            //     imgGioitinh.setImageResource(R.drawable.female);
            // }

            // Nếu sinhVien.GIOITINH là true, hiển thị hình ảnh nam (R.drawable.male)
            // Nếu sinhVien.GIOITINH là false, hiển thị hình ảnh nữ (R.drawable.female)

        }

        return view; // Trả về View đã được tạo hoặc tái sử dụng
    }
}