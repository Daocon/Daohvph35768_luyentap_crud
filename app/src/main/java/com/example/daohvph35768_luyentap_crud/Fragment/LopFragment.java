package com.example.daohvph35768_luyentap_crud.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daohvph35768_luyentap_crud.Adapter.KhoaAdapter;
import com.example.daohvph35768_luyentap_crud.Adapter.LopAdapter;
import com.example.daohvph35768_luyentap_crud.DAO.KhoaDAO;
import com.example.daohvph35768_luyentap_crud.DAO.LopDAO;
import com.example.daohvph35768_luyentap_crud.DTO.KhoaDTO;
import com.example.daohvph35768_luyentap_crud.DTO.LopDTO;
import com.example.daohvph35768_luyentap_crud.LopActivity;
import com.example.daohvph35768_luyentap_crud.R;

import java.util.List;


public class LopFragment extends Fragment {

    RecyclerView rc_lop;
    LopAdapter lopAdapter;
    LopDAO lopDAO;
    List<LopDTO> listLop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_lop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc_lop = view.findViewById(R.id.rc_lop);

        lopDAO = new LopDAO(getContext());
        listLop = lopDAO.getAll();
        lopAdapter = new LopAdapter(getContext(), listLop);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc_lop.setLayoutManager(linearLayoutManager);


        rc_lop.setAdapter( lopAdapter);

        Button btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDiaLogAddLop();
            }
        });
    }
    void ShowDiaLogAddLop(){
        //định nghĩ dialog add lop
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
        KhoaDAO khoaDAO = new KhoaDAO(getContext());
        List<KhoaDTO> listKhoa = khoaDAO.getAll();
        KhoaAdapter khoaAdapter = new KhoaAdapter(listKhoa, getContext());

        spin_khoa.setAdapter(khoaAdapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_lop = ed_ten_lop.getText().toString();
                String si_so_str = ed_si_so.getText().toString();
                int id_khoa = (int) spin_khoa.getSelectedItemId();

                if (ten_lop.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên lớp học", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (si_so_str.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập số lượng sinh viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                int si_so = Integer.parseInt(si_so_str);
                if (si_so <= 0) {
                    Toast.makeText(getContext(), "Số lượng sinh viên không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (spin_khoa.getSelectedItemId() == -1) {
                    Toast.makeText(getContext(), "Vui lòng chọn khoa", Toast.LENGTH_SHORT).show();
                    return;
                }


                LopDAO dao = new LopDAO(getContext());
                LopDTO objLop = new LopDTO(ten_lop,si_so,id_khoa);

                int id = dao.AddRow(objLop);
                if (id > 0){
                    //load lại rc
                    listLop.clear();
                    listLop.addAll(dao.getAll());
                    lopAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "THêm thàn công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Lỗi thêm thành công", Toast.LENGTH_SHORT).show();
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