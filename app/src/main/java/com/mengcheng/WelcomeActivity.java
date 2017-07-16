package com.mengcheng;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.core.base.BasePresenterActivity;
import com.core.utils.persistence.FastData;
import com.mengcheng.activity.HomeActivity;
import com.mengcheng.activity.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/*wangjia
* */
public class WelcomeActivity extends BasePresenterActivity {


    private int recLen = 3;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        timer.schedule(task, 1000, 1000);       // timeTask
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    if (recLen < 0) {
                        timer.cancel();
                        /*登录成功免登录
                        * */
                        if(FastData.getToken()!=null&&!TextUtils.isEmpty(FastData.getToken())){
                        HomeActivity.open(WelcomeActivity.this);
                        }else{//登录页面
                            LoginActivity.open(WelcomeActivity.this);
                        }
                        finish();
                    }
                }
            });
        }
    };
}
