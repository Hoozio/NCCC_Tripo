package com.exam.administrator.nccc_trip;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 2017-07-18.
 */

public class CalendarDialog extends Dialog {
    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mContent;

    private View.OnClickListener ReturnListener;
    private View.OnClickListener ScheduleListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;

        Window window = this.getWindow();
        window.setAttributes(lpWindow);


        setContentView(R.layout.calendar_dialog);

        mTitleView = (TextView) findViewById(R.id.txt_title);
        mContentView = (TextView) findViewById(R.id.txt_content);
        mRightButton = (Button) findViewById(R.id.btn_return);
        mLeftButton = (Button) findViewById(R.id.btn_detail);

        // 제목과 내용을 생성자에서 셋팅한다.
        mTitleView.setText(mTitle);
        mContentView.setText(mContent);

        // 클릭 이벤트 셋팅

        mRightButton.setOnClickListener(ScheduleListener);
        mLeftButton.setOnClickListener(ReturnListener);


    }


    public CalendarDialog(Context context, String title,
                        String content, View.OnClickListener leftListener,
                        View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.ScheduleListener = leftListener;
        this.ReturnListener = rightListener;
    }



}




