package com.mengcheng.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.core.base.BasePresenterActivity;
import com.core.utils.persistence.FastData;
import com.core.utils.persistence.ToastUtil;
import com.mengcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
/*wangjia
* */
public class LoginActivity extends BasePresenterActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.userPassword)
    EditText userPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    public static void open(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        insert();
    }
    public void insert(){
        btnLogin.setOnClickListener(o->{
            String name = username.getText().toString().trim().toString();
            String password = userPassword.getText().toString().trim().toString();
            inHerd(name,password);
        });
    }
    public  void inHerd(String name,String psw){
        if(name!=null&& !TextUtils.isEmpty(name)){
            if(psw!=null&&!TextUtils.isEmpty(psw)){
                /*网络请求
                * */
               // FastData.setToken("login...");
                HomeActivity.open(LoginActivity.this);
                finish();
            }else{
                ToastUtil.show("密码为空！！！");
            }
        }else{
            ToastUtil.show("用户名为空！！！");
        }

    }


}
