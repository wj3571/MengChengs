package com.mengcheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.core.base.BasePresenterFragment;
import com.mengcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dulei on 2017/7/13.
 */

public class QuotaFragment extends BasePresenterFragment {
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_back)
    RelativeLayout topBack;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        bind = ButterKnife.bind(this, view);
        topTitle.setText("指标");
        topBack.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
