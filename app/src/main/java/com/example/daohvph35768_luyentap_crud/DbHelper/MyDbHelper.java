package com.example.daohvph35768_luyentap_crud.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    static String DB_NAME = "ontap_crud";
    static int DB_VERSION = 1;

    public MyDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_khoa = "CREATE TABLE tb_khoa ( id INTEGER PRIMARY KEY AUTOINCREMENT, ten_khoa TEXT (100) UNIQUE NOT NULL ); ";
        db.execSQL(sql_khoa);

        String sql_demo_khoa = "INSERT INTO tb_khoa (ten_khoa) VALUES ('Công nghệ thông tin'), (' Kinh tế')";
        db.execSQL(sql_demo_khoa);

        String sql_lop = "CREATE TABLE tb_lop ( id INTEGER PRIMARY KEY AUTOINCREMENT, ten_lop TEXT (100) UNIQUE NOT NULL, si_so INTEGER NOT NULL DEFAULT (0), id_khoa INTEGER REFERENCES tb_khoa (id) NOT NULL );";
        db.execSQL(sql_lop);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
