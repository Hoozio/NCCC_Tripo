package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter Adapter;
    RecyclerView.LayoutManager layoutManager;
    EditText editText;
    Button add_button;
    CheckBox checkBox;
    ArrayList<MaterialItem> items;
    String materialAdd;

    private ArrayList<MaterialItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


        mContext = getApplicationContext();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        editText = (EditText) findViewById(R.id.material_edit);
        add_button = (Button) findViewById(R.id.material_button);


        items = new ArrayList();

        layoutManager = new LinearLayoutManager(this);


        Adapter = new MaterialAdapter(items, mContext);
        recyclerView.setAdapter(Adapter);

        recyclerView.setLayoutManager(layoutManager);

        for(int i = 0; ; i++) {
            if(getPreferences("item", i).equals("")){
                break;
            }
            String putitem = getPreferences("item", i);
            Log.e("^^^", putitem);
            items.add(new MaterialItem(putitem));
        }


        add_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                materialAdd = editText.getText().toString();
                editText.setText("");
                savePreferences(materialAdd, items.size());
                items.add(new MaterialItem(materialAdd));
                removeAllPreferences();
                for(int i=0; i<items.size(); i++) {
                    savePreferences(items.get(i).getCheckTitle(), i);
                }
                Adapter.notifyDataSetChanged();
            }
        });





    }



    private String getPreferences(String name, int size){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString(name+size, "");
    }

    // 값 저장하기
    private void savePreferences(String name, int size){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("item"+size, name);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    private void removePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("hi");
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    private void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}




