package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;
    EditText editText;
    Button add_button;

    NavigationOnClickListener listener;
    Toolbar toolbar;
    TextView subject;
    ImageView back;
    ImageView searcher;

    ArrayList<MaterialItem> items;
    String materialAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


        mContext = getApplicationContext();

        // Adding Toolbar to the activity
        toolbar = (Toolbar) findViewById(R.id.etc_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        editText = (EditText) findViewById(R.id.material_edit);
        add_button = (Button) findViewById(R.id.material_button);


        items = new ArrayList();

        layoutManager = new LinearLayoutManager(this);


        Adapter = new MaterialAdapter(items, mContext);
        recyclerView.setAdapter(Adapter);

        subject = (TextView)findViewById(R.id.etc_subject);
        back = (ImageView)findViewById(R.id.etc_back);
        searcher = (ImageView)findViewById(R.id.etc_searcher);

        recyclerView.setLayoutManager(layoutManager);

        for(int i = 0; ; i++) {
            if(getPreferences("item",false,i).getCheckTitle().equals("item")){
                break;
            }
            MaterialItem putitem = getPreferences("item", false, i);
            items.add(new MaterialItem(putitem.getCheckTitle(), putitem.getCheckPosition()));
        }

        listener = new NavigationOnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.etc_back:
                        finish();
                        break;
                    case R.id.etc_searcher:
                        Intent i = new Intent(InventoryActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };
        subject.setText("준비물");
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);


        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(editText.getText().toString().equals("")){
                    AlertDialog.Builder ab = new AlertDialog.Builder(InventoryActivity.this);
                    ab.setMessage("준비물을 입력해주세요.");
                    ab.setPositiveButton("확인", null);
                    ab.show();
                    return;
                }
                materialAdd = editText.getText().toString();
                editText.setText("");
                savePreferences(materialAdd, false, items.size());
                items.add(new MaterialItem(materialAdd, false));
                removeAllPreferences();
                for(int i=0; i<items.size(); i++) {
                    savePreferences(items.get(i).getCheckTitle(), items.get(i).getCheckPosition(), i);
                }
                Adapter.notifyDataSetChanged();
            }
        });





    }

    private MaterialItem getPreferences(String name, boolean check, int size){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return new MaterialItem(pref.getString("item"+size, name), pref.getBoolean("check"+size, check));
    }

    // 값 저장하기
    private void savePreferences(String name, boolean check, int size){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("item"+size, name);
        editor.putBoolean("check"+size, check);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    private void removePreferences(int size){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("item"+size);
        editor.remove("check"+size);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    private void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }



//    private String getPreferences(String name, int size){
//        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        return pref.getString(name+size, "");
//    }
//
//    // 값 저장하기
//    private void savePreferences(String name, int size){
//        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("item"+size, name);
//        editor.commit();
//    }
//
//    // 값(Key Data) 삭제하기
//    private void removePreferences(){
//        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.remove("hi");
//        editor.commit();
//    }
//
//    // 값(ALL Data) 삭제하기
//    private void removeAllPreferences(){
//        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.clear();
//        editor.commit();
//    }

}




