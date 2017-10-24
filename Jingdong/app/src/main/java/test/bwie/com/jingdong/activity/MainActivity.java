package test.bwie.com.jingdong.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import test.bwie.com.jingdong.R;

public class MainActivity extends AppCompatActivity {

    private int i=3;
    private Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(i>1){
                i--;
                handler.postDelayed(runnable,1000);
            }else {
                handler.removeCallbacks(runnable);
                Intent intent=new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    private Button button;
    private ImageView image03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getGuideData();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void getGuideData() {
        //获取控件
        image03 = (ImageView) findViewById(R.id.image03);
        button = (Button) findViewById(R.id.but_jinru);
        handler.postDelayed(runnable,1000);
    }
}
