package com.mengcheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.base.BasePresenterFragment;
import com.core.utils.persistence.PopupWindowUtils;
import com.core.utils.persistence.SwipeRecyclerViewWithStatusView;
import com.jakewharton.rxbinding2.view.RxView;
import com.mengcheng.R;
import com.mengcheng.adpater.WaitDealAdpater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dulei on 2017/7/13.
 */

public class WaitDealFragment extends BasePresenterFragment {
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_back)
    RelativeLayout topBack;
    @BindView(R.id.recycle_swipe)
    SwipeRecyclerViewWithStatusView recycleSwipe;
    @BindView(R.id.text_swipe)
    TextView textSwipe;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, view);
        topTitle.setText("待办");
        topBack.setVisibility(View.INVISIBLE);

        recycleSwipe.setLayoutManager(new LinearLayoutManager(getActivity()));
        WaitDealAdpater waitDealAdpater = new WaitDealAdpater(getActivity());
        recycleSwipe.setAdapter(waitDealAdpater);
        recycleSwipe.getRecyclerView().setBackgroundResource(R.color.Line);

        recycleSwipe.setOnRefreshListener(() -> {
            //刷新数据
            recycleSwipe.finishLoading();
            recycleSwipe.finishRefresh();
        });

        recycleSwipe.showLoading();
        textSwipe.setOnClickListener(o->{
            PopupWindowUtils.popupWindowListsShow(getActivity(),"",o);
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
