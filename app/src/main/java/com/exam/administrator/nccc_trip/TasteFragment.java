package com.exam.administrator.nccc_trip;

import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TasteFragment extends Fragment {


    public TasteFragment(){
    }

    private List<Item> ItemList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TasteItem> items;
    private String name;
    private String adress;
    private Bitmap bmimg;
    private String cate;

    int page = 1;
    int radius = 20000;
    String apikey = "a0822a15d67056c8c62651bc3ebb13c2";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_taste, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.taste_recycler_view);

        GpsInfo gps = new GpsInfo(getActivity());
        double lat = gps.getLatitude();
        double lon = gps.getLongitude();

        ItemList = new ArrayList<>();
        items = new ArrayList();


        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new TasteAdapter(items, view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        final GestureDetector gestureDetector = new GestureDetector(view.getContext(),new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });


        String query = "중식";
        OldSearcher searcher = new OldSearcher();

        searcher.searchKeyword(getActivity().getApplicationContext(), query, lat, lon, radius, page, 2, apikey, new OnFinishSearchListener() {
            @Override
            public void onSuccess(List<Item> itemList) {
                showResult(itemList);

            }

            @Override
            public void onFail() {

            }
        });


        return view;
    }



    private void showResult(List<Item> itemList){

        for(int i = 0; i<itemList.size(); i++){

            Item item = itemList.get(i);
            cate = String.valueOf(item.category);
            name = String.valueOf(item.title);
            adress = String.valueOf(item.address);
            try{
                URL url = new URL(String.valueOf(item.imageUrl));
                HttpURLConnection imgConn = (HttpURLConnection) url.openConnection();
                imgConn.setDoInput(true);
                imgConn.connect();
                InputStream is = imgConn.getInputStream();
                bmimg = BitmapFactory.decodeStream(is);
                items.add(new TasteItem(name, adress, bmimg));


                imgConn.disconnect();
            }
            catch (Exception eex){
                BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                bmimg = drawable.getBitmap();
                items.add(new TasteItem(name, adress, bmimg));



            }
            Log.e("ddd", cate);
            Log.e("^^^^^", name);
            String[] arr = cate.split(" > ");
            Log.e("ddd", arr[1]);
            Log.e("ddd", adress);

            Log.e("ddfsd", "dddd" + item.latitude);
            Log.e("ddfsd", "dddd" + item.longitude);
            ItemList.add(itemList.get(i));
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }

}

/*
    public void MatrixTime(int delayTime){
        long saveTime = System.currentTimeMillis();
        long currTime = 0;
        while( currTime - saveTime < delayTime){
            currTime = System.currentTimeMillis();
        }
    }
*/