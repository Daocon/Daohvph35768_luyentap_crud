package com.example.daohvph35768_luyentap_crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daohvph35768_luyentap_crud.Adapter.KhoaAdapter;
import com.example.daohvph35768_luyentap_crud.Adapter.LopAdapter;
import com.example.daohvph35768_luyentap_crud.DAO.KhoaDAO;
import com.example.daohvph35768_luyentap_crud.DAO.LopDAO;
import com.example.daohvph35768_luyentap_crud.DTO.KhoaDTO;
import com.example.daohvph35768_luyentap_crud.DTO.LopDTO;

import java.util.List;

public class LopActivity extends AppCompatActivity {

    RecyclerView rc_lop;
    LopAdapter lopAdapter;
    LopDAO lopDAO;
    List<LopDTO> listLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop);
        rc_lop = findViewById(R.id.rc_lop);

        lopDAO = new LopDAO(this);
        listLop = lopDAO.getAll();
        lopAdapter = new LopAdapter(this, listLop);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_lop.setLayoutManager(linearLayoutManager);


        rc_lop.setAdapter( lopAdapter);

        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDiaLogAddLop();
            }
        });
    }
    void ShowDiaLogAddLop(){
        //định nghĩ dialog add lop
        AlertDialog.Builder builder = new AlertDialog.Builder(LopActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog_add_lop,null);
        builder.setView(v);

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        // tương tác view
        EditText ed_ten_lop = v.findViewById(R.id.ed_ten_lop);
        EditText ed_si_so = v.findViewById(R.id.ed_si_so);
        Spinner spin_khoa = v.findViewById(R.id.spin_khoa);
        Button btn_save = v.findViewById(R.id.btn_save);
        Button btn_cancel = v.findViewById(R.id.btn_cancel);

        // đưa dữ liệu lên spin để chọn
        KhoaDAO khoaDAO = new KhoaDAO(LopActivity.this);
        List<KhoaDTO> listKhoa = khoaDAO.getAll();
        KhoaAdapter khoaAdapter = new KhoaAdapter(listKhoa, LopActivity.this);

        spin_khoa.setAdapter(khoaAdapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_lop = ed_ten_lop.getText().toString();
                int si_so = Integer.parseInt(ed_si_so.getText().toString());
                int id_khoa = (int) spin_khoa.getSelectedItemId();

                LopDAO dao = new LopDAO(LopActivity.this);
                LopDTO objLop = new LopDTO(ten_lop,si_so,id_khoa);

                int id = dao.AddRow(objLop);
                if (id > 0){
                    //load lại rc
                    listLop.clear();
                    listLop.addAll(dao.getAll());
                    lopAdapter.notifyDataSetChanged();
                    Toast.makeText(LopActivity.this, "THêm thàn công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(LopActivity.this, "Lỗi thêm thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}