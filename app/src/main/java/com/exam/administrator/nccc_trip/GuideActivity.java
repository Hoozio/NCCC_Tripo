package com.exam.administrator.nccc_trip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class GuideActivity extends AppCompatActivity  {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;


    HashMap<String, List<String>> childList;

    public ArrayList<String> parentList;
    public List<String> test;
    public ArrayList<String> location1;
    public ArrayList<String> location2;
    public ArrayList<String> location3;
    public ArrayList<String> location4;
    public ArrayList<String> location5;
    public ArrayList<String> location6;
    public ArrayList<String> location7;

    Toolbar toolbar;
    TextView subject;
    ImageView back;
    ImageView searcher;
    NavigationOnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

// Adding Toolbar to the activity
        toolbar = (Toolbar) findViewById(R.id.etc_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);

        subject = (TextView)findViewById(R.id.etc_subject);
        back = (ImageView)findViewById(R.id.etc_back);
        searcher = (ImageView)findViewById(R.id.etc_searcher);
        listener = new NavigationOnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.etc_back:
                        finish();
                        break;
                    case R.id.etc_searcher:
                        Intent i = new Intent(GuideActivity.this, SearchActivity.class);
                        startActivity(i);
                        break;
                }
            }
        };
        subject.setText("관광안내소");
        back.setOnClickListener(listener);
        searcher.setOnClickListener(listener);


        Workbook workbook = null;
        Sheet sheet = null;

        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("guidefile.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);

            int RowStart = 0;int ColumnStart = 0;

            int RowEnd = sheet.getColumn(2).length - 1;
            int ColumnEnd = sheet.getRow(2).length - 1;

            Log.i("getColumn(2).length",""+sheet.getColumn(2).length);
            Log.i("getColumn(2).getRow",""+sheet.getRow(2).length);


            for( int nRow = RowStart; nRow <= RowEnd; nRow++ )
            {
                for( int nColumn = ColumnStart; nColumn <= ColumnEnd ; nColumn++)
                {
                    String excelload = sheet.getCell(ColumnStart, nRow).getContents();
                    Log.i("#####",""+excelload);
                }
            }
            parentList = new ArrayList<String>();

            for(int i=1; i<RowEnd+1; i++){

                if(sheet.getColumn(2).length-1 ==i) {
                    parentList.add(sheet.getCell(3, i).getContents().toString());
                }
                if(sheet.getCell(3,i).getContents().toString().equals(sheet.getCell(3,i+1).getContents().toString()))
                    Log.i("aaaaaaa","aaaaaaaaaaa");
                else {
                    if(i<RowEnd)
                        parentList.add(sheet.getCell(3, i).getContents().toString());
                }
            }


            Log.i("♥♥♥♥","♥♥♥"+parentList.get(0));
//            for(int i=0; i<RowEnd; i++){
//                for(;;) {
//                    Log.i("★★★★","★★★★");
//
//                    if (parentList.get(i).equals(parentList.get(j))) {
//                        Log.i("★★★★","test"+sheet.getCell(0, j).getContents().toString());
//                        test.add(sheet.getCell(0, j).getContents().toString());
//                        j++;
//                    }else{
//                        j=i;
//                        childList.put(parentList.get(i),test);
//                        test.clear();
//                        break;
//                    }
//                }
//            }
            childList = new HashMap<String,List<String>>();
            location1 = new ArrayList<String>();
            location2 = new ArrayList<String>();
            location3 = new ArrayList<String>();
            location4 = new ArrayList<String>();
            location5 = new ArrayList<String>();
            location6 = new ArrayList<String>();
            location7 = new ArrayList<String>();
            test = new ArrayList<String>();
//
//            int z=0;
//            int j=1;
//            String tmp=null;


            for(int i=1; i<RowEnd+1; i++)
            {
                switch (sheet.getCell(3,i).getContents().toString()){
                    case "괴산군":
                        location1.add(sheet.getCell(0,i).getContents().toString()+"/"+ sheet.getCell(15,i).getContents().toString() +"/"+sheet.getCell(16,i).getContents().toString());
                        break;
                    case "단양군":
                        location2.add(sheet.getCell(0,i).getContents().toString()+"/"+ sheet.getCell(15,i).getContents().toString() +"/"+sheet.getCell(16,i).getContents().toString());
                        break;
                    case "보은군":
                        location3.add(sheet.getCell(0,i).getContents().toString()+"/"+ sheet.getCell(15,i).getContents().toString() +"/"+sheet.getCell(16,i).getContents().toString());
                        break;
                    case "영동군":
                        location4.add(sheet.getCell(0,i).getContents().toString()+"/"+ sheet.getCell(15,i).getContents().toString() +"/"+sheet.getCell(16,i).getContents().toString());
                        break;
                    case "제천시":
                        location5.add(sheet.getCell(0,i).getContents().toString()+"/"+ sheet.getCell(15,i).getContents().toString() +"/"+sheet.getCell(16,i).getContents().toString());
                        break;
                    case "청주시":
                        location6.add(sheet.getCell(0,i).getContents().toString()+"/"+ sheet.getCell(15,i).getContents().toString() +"/"+sheet.getCell(16,i).getContents().toString());
                        break;
                    case "충주시":
                        location7.add(sheet.getCell(0,i).getContents().toString()+"/"+ sheet.getCell(15,i).getContents().toString() +"/"+sheet.getCell(16,i).getContents().toString());
                        break;

                    default:
                        Log.i("nonono","nonono");
                }

            }
            childList.put(parentList.get(0),location1);
            childList.put(parentList.get(1),location2);
            childList.put(parentList.get(2),location3);
            childList.put(parentList.get(3),location4);
            childList.put(parentList.get(4),location5);
            childList.put(parentList.get(5),location6);
            childList.put(parentList.get(6),location7);






//
//            for(int i=1; i<RowEnd+1; i++)
//            {
//                if (sheet.getCell(3,i).getContents().toString().equals(sheet.getCell(3,j).getContents().toString())) {
//                    Log.i("★★★★","test"+sheet.getCell(0,i).getContents().toString());
//                    test.add(sheet.getCell(0,i).getContents().toString());
//
//                }else{
//                    Log.i("*********","test"+parentList.get(z));
//                    for(int a=0; a<test.size(); a++){
//                        Log.i("여기는 test 값::","test"+ test.get(a));
//                    }
//                    childList.put(parentList.get(z),test);
//                    Log.i("여기는 childList 값::",""+childList.keySet());
//                    Log.i("여기는 childList 값::",""+childList.values());
//
//                    test.clear();
//                    z++;
//                    j=i;
//                    i--;
//                }
//
//            }






//
//            String a;
//            String b;
//            a=sheet.getCell(4,3).getContents();
//            Log.i("★★★★",""+a.toString());
//            b=sheet.getColumn(4).toString();
//            Log.i("★★★★",""+b.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }


        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        expandableListAdapter = new CustomExpandableListAdapter(this, parentList, childList);

        expandableListView.setAdapter(expandableListAdapter);

        //그룹 열릴 경우
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
        //그룹이 닫힐경우
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });
        //그룹 클릭했을 때
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });


        //차일드 클릭했을 때
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });
    }

}