package com.example.daohvph35768_luyentap_crud.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daohvph35768_luyentap_crud.DTO.LopDTO;
import com.example.daohvph35768_luyentap_crud.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class LopDAO {
    MyDbHelper myDbHelper;
    SQLiteDatabase db;

    public LopDAO(Context context){
        myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
    }
    public int AddRow(LopDTO objLop){
        ContentValues values = new ContentValues();
        values.put("ten_lop", objLop.getTen_lop());
        values.put("si_so", objLop.getSi_so());
        values.put("id_khoa",objLop.getId_khoa());
        return (int) db.insert("tb_lop", null, values);
    }

    public int UpdateRow (LopDTO objLop){
        ContentValues values = new ContentValues();
        values.put("ten_lop", objLop.getTen_lop());
        values.put("si_so", objLop.getSi_so());
        values.put("id_khoa",objLop.getId_khoa());
        //điều kiện cập nhật
        String[] dieukien = new String[]{ String.valueOf(objLop.getId())};

        return (int) db.update("tb_lop", values, "id=?", dieukien);
    }
    public int DeleteRow (LopDTO objLop){
        //điều kiện xóa
        String[] dieukien = new String[]{ String.valueOf(objLop.getId())};
        return (int) db.delete("tb_lop", "id=?", dieukien);
    }
    public List<LopDTO> getAll(){
        List<LopDTO> listLop = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT tb_lop.id, ten_lop, ten_khoa, si_so, id_khoa FROM tb_lop INNER JOIN tb_khoa ON tb_khoa.id = tb_lop.id_khoa;",null);

        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id_lop = c.getInt(0);
                String ten_lop = c.getString(1);
                String ten_khoa = c.getString(2);
                int si_so = c.getInt(3);
                int id_khoa = c.getInt(4);

                LopDTO objLop = new LopDTO(id_lop,ten_lop,si_so,id_khoa,ten_khoa);
                listLop.add(objLop);

                c.moveToNext();
            }
        }


        return listLop;
    }
}
