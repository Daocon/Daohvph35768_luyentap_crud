package com.example.daohvph35768_luyentap_crud.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daohvph35768_luyentap_crud.Adapter.MyAdapterPager;
import com.example.daohvph35768_luyentap_crud.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class collectionFragmentPagerDemo extends Fragment {
    MyAdapterPager myAdapterPager ;
    ViewPager2 pager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_collection_frag_viewpager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //tạo adaper cho viewpager
        myAdapterPager = new MyAdapterPager(this);

        pager2 = view.findViewById(R.id.page_demo);

        pager2.setAdapter(myAdapterPager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout2);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, pager2, true,
                true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // thiết laapj cấu hình cho tab ở đây
                //tab.setText("Tab thứ: "+ position);
                switch (position){
                    case 0:
                        tab.setText("Khoa"+ position);
                        break;
                    case 1:
                        tab.setText("Lop"+ position);
                        break;
                    case 2:
                        tab.setText("Khoa"+ position);
                        break;
                }
            }
        });
        // attach vào tab layout
        mediator.attach();
    }
}