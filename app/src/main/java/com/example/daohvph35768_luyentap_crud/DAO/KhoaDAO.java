package com.example.daohvph35768_luyentap_crud.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.daohvph35768_luyentap_crud.DTO.KhoaDTO;
import com.example.daohvph35768_luyentap_crud.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class KhoaDAO {
    MyDbHelper myDbHelper;
    SQLiteDatabase db;

    public KhoaDAO (Context context){
        myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
    }

    public int AddRow(KhoaDTO objKhoa){
        ContentValues values = new ContentValues();
        values.put("ten_khoa",objKhoa.getTen_khoa());
        return (int) db.insert("tb_khoa", null, values);
    }

    public int UpdateRow (KhoaDTO objKhoa){
        ContentValues values = new ContentValues();
        values.put("ten_khoa",objKhoa.getTen_khoa());
        //điều kiện cập nhật
        String[] dieukien = new String[]{ String.valueOf(objKhoa.getId())};

        return (int) db.update("tb_khoa", values, "id=?", dieukien);
    }
    public int DeleteRow (KhoaDTO objKhoa){
        //điều kiện xóa
        String[] dieukien = new String[]{ String.valueOf(objKhoa.getId())};
        return (int) db.delete("tb_khoa", "id=?", dieukien);
    }

    public List<KhoaDTO> getAll(){
        List<KhoaDTO> list_khoa = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM tb_khoa", null);

        if (c != null && c.getCount()>0){
            c.moveToFirst();
            while (!c.isAfterLast()){
                //Thứ tự ột 0: id, 1: ten_khoa
                int id_khoa = c.getInt(0);
                String ten_khoa = c.getString(1);

                KhoaDTO khoaDTO = new KhoaDTO(id_khoa,ten_khoa);
                list_khoa.add(khoaDTO);

                c.moveToNext();
            }
        }
        return list_khoa;
    }

}
