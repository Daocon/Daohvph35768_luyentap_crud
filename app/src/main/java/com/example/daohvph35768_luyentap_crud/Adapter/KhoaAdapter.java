package com.example.daohvph35768_luyentap_crud.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daohvph35768_luyentap_crud.DTO.KhoaDTO;
import com.example.daohvph35768_luyentap_crud.R;

import java.util.List;

public class KhoaAdapter extends BaseAdapter {

    List<KhoaDTO> list_khoa;
    Context context;

    public KhoaAdapter(List<KhoaDTO> list_khoa, Context context) {
        this.list_khoa = list_khoa;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_khoa.size();
    }

    @Override
    public Object getItem(int position) {
        return list_khoa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list_khoa.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        if (convertView == null) {
            row = View.inflate(context, R.layout.layout_row_khoa, null);
        } else
            row = convertView;

        //lấy dữ liệu
        KhoaDTO objKhoa = list_khoa.get(position);
        //ánh xạ
        TextView tv_id = row.findViewById(R.id.tv_id);
        TextView tv_ten_khoa = row.findViewById(R.id.tv_ten_khoa);

        //gán dữ liệu

        tv_id.setText(objKhoa.getId() + "");
        tv_ten_khoa.setText(objKhoa.getTen_khoa());
        return row;
    }
}