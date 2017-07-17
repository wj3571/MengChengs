package com.core.utils.persistence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.mengcheng.R;

import java.util.List;

/**
 * Created by wangjia on 2017/7/16.
 */

public class PopupWindowUtils {

    public static  void popupWindowListsShow(Context context, String list, View view) {
// 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.popupwindows, null);
        PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, 200, true);

        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(view);
    }

}
