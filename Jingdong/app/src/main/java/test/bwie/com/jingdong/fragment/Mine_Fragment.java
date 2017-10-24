package test.bwie.com.jingdong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.activity.Denglu_Activity;
import test.bwie.com.jingdong.activity.MainActivity;
import test.bwie.com.jingdong.activity.Zhuce_Activity;
import test.bwie.com.jingdong.util.SharedUtil;

/**
 * Created by Love_you on 2017/9/28 0028.
 */
public class Mine_Fragment extends Fragment {

    private TextView user_name;
    private boolean config;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wode, null);
        user_name = view.findViewById(R.id.user_name);
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Denglu_Activity.class);
                startActivity(intent);
            }
        });
        //获取布局控件
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedUtil instances = SharedUtil.getInstances();
        config = (boolean) instances.getValueByKey(getActivity(), "config", false);
        if(config){
            String username = (String) instances.getValueByKey(getActivity(), "username", "");
            user_name.setText(username);
        }
    }
}
