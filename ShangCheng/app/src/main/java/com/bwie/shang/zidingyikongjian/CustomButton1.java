package com.bwie.shang.zidingyikongjian;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.shang.R;

/**
 * Created by Love_you on 2017/9/7 0007.
 */
public class CustomButton1 extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public CustomButton1(Context context) {
        super(context);
    }

    public CustomButton1(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.zidingyishangdian,this,true);
        imageView = (ImageView) findViewById(R.id.imagetop);
        textView = (TextView) findViewById(R.id.imagename);
    }
    public void setImageView(int resId){
        imageView.setImageResource(resId);
    }
    public void setTextView(String textViewname){
        textView.setText(textViewname);
    }

}
