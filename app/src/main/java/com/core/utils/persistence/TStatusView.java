package com.core.utils.persistence;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jakewharton.rxbinding2.view.RxView;
import com.mengcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jerry.views.statusview.StatusView;
import retrofit2.HttpException;

/**
 * 天天爱车加载占位组件
 * <p>
 * Created by JieGuo on 2017/4/5.
 */

public class TStatusView extends StatusView {

    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;
    @BindView(R.id.iv_error)
    ImageView ivError;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    public TStatusView(@NonNull Context context) {
        super(context);
    }

    public TStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TStatusView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public TStatusView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        ButterKnife.bind(this);
        if (!isInEditMode()) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
        tvTitle.setText("正在努力加载……");
        RxView.clicks(tvTitle)
                .subscribe(o -> {
                    if (onRefreshListener != null) {
                        showLoading();
                        onRefreshListener.onRefresh();
                    }
                }, throwable -> {
                    Log.e("Error", "error", new Exception(throwable));
                });
        setOnClickListener(v -> {

        });


        RxView.clicks(tvError).subscribe(o -> {

            if (onRefreshListener != null) {
                onRefreshListener.onRefresh();
            }
        }, throwable -> {
            Log.e("Error", "error", new Exception(throwable));
        });
    }

    @Override
    protected int getContentResource() {
        return R.layout.view_tticar_statusview2;
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    @Override
    public void showError(Throwable throwable) {
        super.showError(throwable);
        if (throwable instanceof HttpException) {
            if (((HttpException) throwable).code() == 504) {

                showEmpty(R.drawable.ic_status_view_not_network, "没有网络连接, 点击重试");
            } else {
                showEmpty(R.drawable.ic_status_view_empty_data, "请求出错，请检查网络后，点击重试");
            }
        } else {

            showEmpty(R.drawable.ic_status_view_empty_data, "加载失败! 点击重试");
        }
    }

    public void showNetworkError() {
        showEmpty(R.drawable.ic_status_view_not_network, "没有网络连接");
    }

    @Override
    public void showLoading() {
        super.showLoading();
        ivLoading.setVisibility(VISIBLE);
        tvTitle.setVisibility(VISIBLE);
        tvError.setVisibility(GONE);
        ivError.setVisibility(GONE);
        // ivLoading.setImageResource(R.drawable.loading_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
//        animationDrawable.start();
        tvTitle.setText("正在加载……");
    }

    public void showEmpty(String text) {
        setAlpha(1);
        showEmpty(R.drawable.ic_status_view_empty_data, text);
    }

    public void showEmpty(int resource, String text) {
        setVisibility(VISIBLE);
        ivLoading.setVisibility(GONE);
        setAlpha(1);
        ivError.setVisibility(VISIBLE);
        tvTitle.setVisibility(GONE);
        tvError.setVisibility(VISIBLE);
        Glide.with(getContext())
                .load(resource)
                .fitCenter()
                .into(ivError);
        if (TextUtils.isEmpty(text)) {
            tvError.setText("空空如也!");
        } else {
            tvError.setText(text);
        }
    }

    public boolean isLoading() {
        return getVisibility() == VISIBLE;
    }
}
