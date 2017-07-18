package com.exam.administrator.nccc_trip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2017-07-04.
 */

public class RegionFragment extends Fragment {
    public RegionFragment(){
    }
    ImageButton im;
    ImageButton im2;
    ImageButton im3;
    ImageButton im4;
    ImageButton im5;
    ImageButton im6;
    ImageButton im7;
    ImageButton im8;
    ImageButton im9;
    ImageButton im10;
    ImageButton im11;
    ImageButton im12;
    int sigungu;
    RegionOnCLickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_region, container, false);
        im = (ImageButton) view.findViewById(R.id.danyang);
        im2 = (ImageButton) view.findViewById(R.id.jecheon);
        im3 = (ImageButton) view.findViewById(R.id.chungju);
        im4 = (ImageButton) view.findViewById(R.id.eumseong);
        im5 = (ImageButton) view.findViewById(R.id.goesan);
        im6 = (ImageButton) view.findViewById(R.id.jincheon);
        im7 = (ImageButton) view.findViewById(R.id.jeungpyeong);
        im8 = (ImageButton) view.findViewById(R.id.cheongwon);
        im9 = (ImageButton) view.findViewById(R.id.cheongju);
        im10 = (ImageButton) view.findViewById(R.id.boeun);
        im11 = (ImageButton) view.findViewById(R.id.ohkcheon);
        im12 = (ImageButton) view.findViewById(R.id.yeongdong);

        listener = new RegionOnCLickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.danyang:
                        Log.e("dddd", "단양 클릭");
                        sigungu = 2;
                        break;
                    case R.id.jecheon:
                        sigungu = 7;
                        Log.e("dddd", "제천 클릭");
                        break;
                    case R.id.chungju:
                        sigungu = 11;
                        Log.e("dddd", "충주 클릭");
                        break;
                    case R.id.eumseong:
                        Log.e("dddd", "음성 클릭");
                        sigungu = 6;
                        break;
                    case R.id.goesan:
                        Log.e("dddd", "괴산 클릭");
                        sigungu = 1;
                        break;
                    case R.id.jincheon:
                        sigungu = 8;
                        Log.e("dddd", "진천 클릭");
                        break;
                    case R.id.jeungpyeong:
                        sigungu = 12;
                        Log.e("dddd", "증평 클릭");
                        break;
                    case R.id.cheongwon:
                        sigungu = 9;
                        Log.e("dddd", "청원 클릭");
                        break;
                    case R.id.cheongju:
                        sigungu = 10;
                        Log.e("dddd", "청주 클릭");
                        break;
                    case R.id.boeun:
                        Log.e("dddd", "보은 클릭");
                        sigungu = 3;
                        break;
                    case R.id.ohkcheon:
                        sigungu = 5;
                        Log.e("dddd", "옥천 클릭");
                        break;
                    case R.id.yeongdong:
                        Log.e("dddd", "영동 클릭");
                        sigungu = 4;
                        break;
                }
                MainActivity main = (MainActivity)getActivity();
                main.setSigungu(sigungu);
                main.shiftFragment(1,sigungu);
            }
        };
        im.setOnClickListener(listener);
        im2.setOnClickListener(listener);
        im3.setOnClickListener(listener);
        im4.setOnClickListener(listener);
        im5.setOnClickListener(listener);
        im6.setOnClickListener(listener);
        im7.setOnClickListener(listener);
        im8.setOnClickListener(listener);
        im9.setOnClickListener(listener);
        im10.setOnClickListener(listener);
        im11.setOnClickListener(listener);
        im12.setOnClickListener(listener);
        return view;
    }
}
