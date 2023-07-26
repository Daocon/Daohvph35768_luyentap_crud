package com.example.daohvph35768_luyentap_crud.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daohvph35768_luyentap_crud.DAO.KhoaDAO;
import com.example.daohvph35768_luyentap_crud.DAO.LopDAO;
import com.example.daohvph35768_luyentap_crud.DTO.KhoaDTO;
import com.example.daohvph35768_luyentap_crud.DTO.LopDTO;
import com.example.daohvph35768_luyentap_crud.LopActivity;
import com.example.daohvph35768_luyentap_crud.R;

import java.util.List;

public class LopAdapter extends RecyclerView.Adapter<LopAdapter.ViewHolderLop> {

    Context context;
    List<LopDTO> list_lop;
    //tạo contructor truyền vào context và dữ liệu cho adapter

    public LopAdapter(Context context, List<LopDTO> list_lop) {
        this.context = context;
        this.list_lop = list_lop;
    }

    @NonNull
    @Override
    public ViewHolderLop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context) .getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_row_lop,parent,false);

        ViewHolderLop holderLop = new ViewHolderLop(v);
        return holderLop;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLop holder, int position) {
        LopDTO objLop = list_lop.get(position);

        //gắn dữ liệu
        holder.tv_id.setText(objLop.getId() + "");
        holder.tv_ten_lop.setText(objLop.getTen_lop());
        holder.tv_ten_khoa.setText(objLop.getTen_khoa());

        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDiaLogEdit(objLop);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogDelete(objLop);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_lop.size();
    }

    public static class ViewHolderLop extends RecyclerView.ViewHolder{
        TextView tv_id,tv_ten_lop, tv_ten_khoa;
        Button btn_update, btn_delete;


        public ViewHolderLop(@NonNull View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.tv_id_lop);
            tv_ten_lop = itemView.findViewById(R.id.tv_ten_lop);
            tv_ten_khoa = itemView.findViewById(R.id.tv_ten_khoa);

            btn_update = itemView.findViewById(R.id.btn_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

    void ShowDiaLogEdit(LopDTO objLop){
        //định nghĩ dialog add lop
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog_edit_lop,null);
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
        KhoaDAO khoaDAO = new KhoaDAO(context);
        List<KhoaDTO> listKhoa = khoaDAO.getAll();
        KhoaAdapter khoaAdapter = new KhoaAdapter(listKhoa, context);

        spin_khoa.setAdapter(khoaAdapter);
        // hiển thị dữ liệu cũ
        ed_ten_lop.setText(objLop.getTen_lop());
        ed_si_so.setText(objLop.getSi_so()+ "");
        //set spinner hiển thị qkhoa chọn sẵn
        for (int i = 0; i < listKhoa.size(); i++){
            if (listKhoa.get(i).getId() == objLop.getId_khoa()){
                spin_khoa.setSelection(i);
            }
        }
        //
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_lop = ed_ten_lop.getText().toString();
                int si_so = Integer.parseInt(ed_si_so.getText().toString());
                int id_khoa = (int) spin_khoa.getSelectedItemId();

                LopDAO dao = new LopDAO(context);
                LopDTO objLop_moi = new LopDTO(ten_lop,si_so,id_khoa);
                objLop_moi.setId(objLop.getId());

                int kq = dao.UpdateRow(objLop_moi);
                if (kq > 0){
                    //load lại rc
                    list_lop.clear();
                    list_lop.addAll(dao.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Cập nhât thàn công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
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
    void ShowDialogDelete(LopDTO objLop){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("THông báo xóa");
        builder.setMessage("Bạn có đồng ý xóa lớp: "+ objLop.getTen_lop());
        builder.setPositiveButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //thực hiện xóa
                LopDAO dao = new LopDAO(context);
                int kq = dao.DeleteRow(objLop);
                if (kq > 0){
                    //load lại rc
                    list_lop.clear();
                    list_lop.addAll(dao.getAll());
                    notifyDataSetChanged();
                    Toast.makeText(context, "xóa thàn công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "XÓa ko thàn công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        (builder.create()).show();
    }
}
