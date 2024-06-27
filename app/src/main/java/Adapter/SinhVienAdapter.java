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

public class SinhVienAdapter extends ArrayAdapter<SINHVIEN> {

    public SinhVienAdapter(@NonNull Context context, int resource, @NonNull List<SINHVIEN> objects) {super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_sinhvien, parent, false);
        }

        SINHVIEN sinhVien = getItem(position);
        if (sinhVien != null) {
            TextView tvMasv = view.findViewById(R.id.tvMasv);
            TextView tvTensv = view.findViewById(R.id.tvTensv);
            ImageView imgGioitinh = view.findViewById(R.id.imgGioiTinh);

            tvMasv.setText(sinhVien.MSSV);
            tvTensv.setText(sinhVien.TENSV);

            // Set hình ảnh cho ImageView dựa trên giới tính (sinhVien.GIOITINH)
            imgGioitinh.setImageResource(sinhVien.GIOITINH ? R.drawable.male : R.drawable.female);

            // Set hình ảnh cho ImageView dựa trên giới tính (sinhVien.GIOITINH) cách 2
//            if (sinhVien.GIOITINH) {
//                imgGioitinh.setImageResource(R.drawable.male); // Thay R.drawable.male bằng ID của hình ảnh nam
//            } else {
//                imgGioitinh.setImageResource(R.drawable.female); // Thay R.drawable.female bằng ID của hình ảnh nữ
//            }
        }
        return view;
    }
}