package com.example.daohvph35768_luyentap_crud.DTO;

public class KhoaDTO {
    int id;
    String ten_khoa;


    public KhoaDTO() {
    }

    public KhoaDTO(String ten_khoa) {
        this.ten_khoa = ten_khoa;
    }

    public KhoaDTO(int id, String ten_khoa) {
        this.id = id;
        this.ten_khoa = ten_khoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_khoa() {
        return ten_khoa;
    }

    public void setTen_khoa(String ten_khoa) {
        this.ten_khoa = ten_khoa;
    }
}
