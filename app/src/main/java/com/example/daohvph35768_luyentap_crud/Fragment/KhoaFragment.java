package com.example.daohvph35768_luyentap_crud.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daohvph35768_luyentap_crud.Adapter.KhoaAdapter;
import com.example.daohvph35768_luyentap_crud.DAO.KhoaDAO;
import com.example.daohvph35768_luyentap_crud.DTO.KhoaDTO;
import com.example.daohvph35768_luyentap_crud.KhoaActivity;
import com.example.daohvph35768_luyentap_crud.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class KhoaFragment extends Fragment {

    TextInputLayout ed_tenkhoa;
    Button btn_del, btn_add, btn_update;
    ListView lv_khoa;

    KhoaAdapter khoaAdapter;
    KhoaDTO objCurrentKhoa;
    KhoaDAO khoaDAO;
    List<KhoaDTO> list_khoa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_khoa , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_tenkhoa = view.findViewById(R.id.ed_tenkhoa);
        btn_add = view.findViewById(R.id.btn_add);
        btn_del = view.findViewById(R.id.btn_del);
        btn_update = view.findViewById(R.id.btn_update);
        lv_khoa = view.findViewById(R.id.lv_khoa);

        khoaDAO = new KhoaDAO(getContext());
        list_khoa = khoaDAO.getAll();

        khoaAdapter = new KhoaAdapter(list_khoa,getContext());

        lv_khoa.setAdapter(khoaAdapter);
        //nut them moi
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_khoa = ed_tenkhoa.getEditText().getText().toString();
                if (ten_khoa.isEmpty()) {
                    ed_tenkhoa.setError("Vui lòng nhập tên khoa");
                    return;
                } else {
                    ed_tenkhoa.setError(null);
                }
                KhoaDTO khoaDTO = new KhoaDTO(ten_khoa);
                int id_moi = khoaDAO.AddRow(khoaDTO);
                if (id_moi > 0 ){
                    list_khoa.clear();
                    list_khoa.addAll( khoaDAO.getAll());
                    khoaAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "THêm thành công", Toast.LENGTH_SHORT).show();
                    ed_tenkhoa.getEditText().setText("");
                    objCurrentKhoa = null;
                }else {
                    Toast.makeText(getContext(), "THêm ko thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lv_khoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                objCurrentKhoa = list_khoa.get(position);
                ed_tenkhoa.getEditText().setText(objCurrentKhoa.getTen_khoa());
                return true;
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_khoa = ed_tenkhoa.getEditText().getText().toString();
                objCurrentKhoa.setTen_khoa( ten_khoa);

                int kq = khoaDAO.UpdateRow(objCurrentKhoa);
                if (kq == 1 ){
                    list_khoa.clear();
                    list_khoa.addAll( khoaDAO.getAll());
                    khoaAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Cập nhật ko thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kq = khoaDAO.DeleteRow(objCurrentKhoa);
                if (kq == 1 ){
                    list_khoa.clear();
                    list_khoa.addAll( khoaDAO.getAll());
                    khoaAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    ed_tenkhoa.getEditText().setText("");
                    objCurrentKhoa = null;
                }else {
                    Toast.makeText(getContext(), "Xóa ko thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}