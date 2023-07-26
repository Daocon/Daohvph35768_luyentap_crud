package com.example.daohvph35768_luyentap_crud.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.daohvph35768_luyentap_crud.Fragment.KhoaFragment;
import com.example.daohvph35768_luyentap_crud.Fragment.LopFragment;

public class MyAdapterPager extends FragmentStateAdapter {
    int soLuongPage = 2;

    public MyAdapterPager(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new KhoaFragment();

            case 1:
                return new LopFragment();

            default:
                return new KhoaFragment();

        }
    }

    @Override
    public int getItemCount() {
        return soLuongPage;
    }
}
