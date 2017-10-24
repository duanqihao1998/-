package com.bwie.shang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwie.shang.R;

/**
 * Created by Love_you on 2017/8/31 0031.
 */
public class WeiFragment extends Fragment{

    private ImageView imageweisou;
    private ImageView imageguan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weitao, null);
        imageweisou = (ImageView) view.findViewById(R.id.Imageweissou);
        imageguan = (ImageView) view.findViewById(R.id.woguanzhu);
//        TextView textView = new TextView(getActivity());
//        textView.setText("我的微淘");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
