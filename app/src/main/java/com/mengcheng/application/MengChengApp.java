package com.mengcheng.application;

import android.app.Application;

import com.core.utils.persistence.FastData;
import com.core.utils.persistence.Remember;

/**
 * Created by wangjia on 2017/7/13.
 */

public class MengChengApp extends Application{
    private static MengChengApp instanse;
    @Override
    public void onCreate() {
        super.onCreate();
        instanse=this;
        FastData.getInstance();
    }

    public static MengChengApp getInstanse() {
        return instanse;
    }

}

