package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 2017-07-13.
 */

public class MaterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<MaterialItem> mItems;


    private int lastPosition = -1;




    public MaterialAdapter(ArrayList items, Context mContext) {
        mItems = items;
        context = mContext;

    }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material, parent, false);
        holder = new DeleteItemVH(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DeleteItemVH){
            final DeleteItemVH deleteItemVH = (DeleteItemVH)holder;

            deleteItemVH.checkBox.setText(mItems.get(position).getCheckTitle());
            deleteItemVH.deleteButton.setTag(holder);
            deleteItemVH.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MaterialItem putitem;
                    for(int i = 0; ; i++) {
                        if(getPreferences("item",false,i).getCheckTitle().equals("item")){
                            break;
                        }
                        putitem = getPreferences("item", false, i);
                        mItems.get(i).setCheckPosition(isChecked);
                        savePreferences(putitem.getCheckTitle(), mItems.get(i).getCheckPosition(), i);
                    }
                    removeAllPreferences();
                    for(int i =0; i<mItems.size(); i++){
                        savePreferences(mItems.get(i).getCheckTitle(), mItems.get(i).getCheckPosition(), i);
                    }
                }
            });
            deleteItemVH.deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int holderPosition = ((DeleteItemVH)v.getTag()).getAdapterPosition();
                    Log.e("*****", ""+holderPosition);
                    mItems.remove(holderPosition);
                    removePreferences(holderPosition);
                    notifyItemRemoved(holderPosition);
                    notifyItemChanged(holderPosition,  mItems.size());
                    removeAllPreferences();
                    for(int i = 0; i<mItems.size(); i++) {
                        savePreferences(mItems.get(i).getCheckTitle(), mItems.get(i).getCheckPosition(), i);
                    }


                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }



    public class DeleteItemVH extends RecyclerView.ViewHolder{
        public CheckBox checkBox;
        public Button deleteButton;

        public DeleteItemVH(View view){
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.material_check);
            deleteButton = (Button) view.findViewById(R.id.material_bbutton);
        }
    }

    private MaterialItem getPreferences(String name, boolean check, int size){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return new MaterialItem(pref.getString("item"+size, name), pref.getBoolean("check"+size, check));
    }

    // 값 저장하기
    private void savePreferences(String name, boolean check, int size){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("item"+size, name);
        editor.putBoolean("check"+size, check);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    private void removePreferences(int size){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("item"+size);
        editor.remove("check"+size);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    private void removeAllPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}



