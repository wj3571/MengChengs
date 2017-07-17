package com.mengcheng.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mengcheng.R;

/**
 * Created by wangjia on 2017/7/16.
 */

public class WaitDealAdpater extends RecyclerView.Adapter<WaitDealAdpater.MyHolder> {

    Context context;

    public WaitDealAdpater(Context context) {
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*        return new MyHolder(LayoutInflater.from(
                context).inflate(R.layout.item_supervise, null));*/

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supervise, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
