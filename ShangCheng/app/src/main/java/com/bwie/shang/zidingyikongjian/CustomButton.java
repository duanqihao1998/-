package com.bwie.shang.zidingyikongjian;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.shang.R;

/**
 * Created by Love_you on 2017/9/4 0004.
 */
public class CustomButton extends LinearLayout {

    private TextView textCount;
    private TextView textName;

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //导入布局
        LayoutInflater.from(context).inflate(R.layout.zidingyijilu,this,true);
        textCount = (TextView) findViewById(R.id.textCount);
        textName = (TextView) findViewById(R.id.textName);
    }
    public void setTextCount(String Count){
        textCount.setText(Count);
    }
    public void setTextName(String Name){
        textName.setText(Name);
    }
}
