package com.example.daohvph35768_luyentap_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daohvph35768_luyentap_crud.Adapter.KhoaAdapter;
import com.example.daohvph35768_luyentap_crud.DAO.KhoaDAO;
import com.example.daohvph35768_luyentap_crud.DTO.KhoaDTO;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class KhoaActivity extends AppCompatActivity {
    TextInputLayout ed_tenkhoa;
    Button btn_del, btn_add, btn_update;
    ListView lv_khoa;

    KhoaAdapter khoaAdapter;
    KhoaDTO objCurrentKhoa;
    KhoaDAO khoaDAO;
    List<KhoaDTO> list_khoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoa);

        ed_tenkhoa = findViewById(R.id.ed_tenkhoa);
        btn_add = findViewById(R.id.btn_add);
        btn_del = findViewById(R.id.btn_del);
        btn_update = findViewById(R.id.btn_update);
        lv_khoa = findViewById(R.id.lv_khoa);

        khoaDAO = new KhoaDAO(this);
        list_khoa = khoaDAO.getAll();

        khoaAdapter = new KhoaAdapter(list_khoa,this);

        lv_khoa.setAdapter(khoaAdapter);
        //nut them moi
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_khoa = ed_tenkhoa.getEditText().getText().toString();
                KhoaDTO khoaDTO = new KhoaDTO(ten_khoa);
                int id_moi = khoaDAO.AddRow(khoaDTO);
                if (id_moi > 0 ){
                    list_khoa.clear();
                    list_khoa.addAll( khoaDAO.getAll());
                    khoaAdapter.notifyDataSetChanged();
                    Toast.makeText(KhoaActivity.this, "THêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(KhoaActivity.this, "THêm ko thành công", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(KhoaActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(KhoaActivity.this, "Cập nhật ko thành công", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(KhoaActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    ed_tenkhoa.getEditText().setText("");
                    objCurrentKhoa = null;
                }else {
                    Toast.makeText(KhoaActivity.this, "Xóa ko thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}