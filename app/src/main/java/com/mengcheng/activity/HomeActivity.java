package com.mengcheng.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.core.base.BasePresenterActivity;
import com.core.base.eventbus.IEventBus;
import com.core.utils.persistence.FastData;
import com.core.utils.persistence.ToastUtil;
import com.mengcheng.R;
import com.mengcheng.event.HomeTabChangeEvent;
import com.mengcheng.fragment.QuotaFragment;
import com.mengcheng.fragment.SuperviseDealFragment;
import com.mengcheng.fragment.WaitDealFragment;
import com.mengcheng.fragment.AlreadyDealFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/*wangjia
* */
public class HomeActivity extends BasePresenterActivity implements View.OnClickListener ,IEventBus {

    public  static void open(Context context){
        context.startActivity(new Intent(context,HomeActivity.class));
    }
    @BindViews({R.id.ll_homepage, R.id.ll_shop, R.id.ll_find, R.id.ll_my})
    public List<LinearLayout> mTabs;
    private Fragment lastFragment;

    private long backTime = 2000;
    private long curTime;
    private static final String HOME_PAGE = "homePageFragment";
    private static final String SHOP = "homeShopFragment";
    private static final String DISCOVERY = "homeDiscoveryFragment";
    private static final String MINE = "homeMineFragment";
    private int lastSelectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        for (int i = 0; i < mTabs.size(); i++) {
            mTabs.get(i).setTag(R.string.tag_index, i);
            mTabs.get(i).setOnClickListener(this);
        }

        lastFragment = new WaitDealFragment();
        changeTab(0);

//        Log.d(TAG, DeviceUtil.getDeviceInfo());
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("lastTabIndex") && mTabs != null) {
                lastSelectedIndex = savedInstanceState.getInt("lastTabIndex");
                mTabs.get(0).setSelected(false);
                changeTab(lastSelectedIndex);
            }
        }
        FastData.setIsLogin(false);
        if(FastData.getLogin()){
            findViewById(R.id.ll_find).setVisibility(View.GONE);
        }else{
            findViewById(R.id.ll_find).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        try {
            int index = (int) v.getTag(R.string.tag_index);
            if (lastSelectedIndex != index) {
                changeTab(index);
            }
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }

    private void changeTab(int index) {
        Fragment fragment = null;
        switch (index) {
            case 0:
                fragment = getSupportFragmentManager().findFragmentByTag(HOME_PAGE);
                if (fragment == null) {
                    fragment = new WaitDealFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container, fragment, HOME_PAGE)
                            .commit();
                }
                break;

            case 1:
                fragment = getSupportFragmentManager().findFragmentByTag(SHOP);
                if (fragment == null) {
                    fragment = new AlreadyDealFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container, fragment, SHOP)
                            .commit();
                }
                break;

            case 2:
                fragment = getSupportFragmentManager().findFragmentByTag(DISCOVERY);
                if (fragment == null) {
                    fragment = new SuperviseDealFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container, fragment, DISCOVERY)
                            .commit();
                }
                break;

            case 3:
                fragment = getSupportFragmentManager().findFragmentByTag(MINE);
                if (fragment == null) {
                    fragment = new QuotaFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container, fragment, MINE)
                            .commit();
                }
                break;

            default:
                return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .hide(lastFragment)
                .show(fragment)
                .commit();
        lastFragment = fragment;
        mTabs.get(lastSelectedIndex).setSelected(false);
        mTabs.get(index).setSelected(true);
        lastSelectedIndex = index;
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - curTime > backTime) {
            ToastUtil.show(HomeActivity.this, R.string.exit_app);
            curTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("lastTabIndex", lastSelectedIndex);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onChangeTabEvent(HomeTabChangeEvent event) {
        addDisposable(
                Maybe.just(event)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(e -> {
                            if (e.index < mTabs.size()) {
                                changeTab(e.index);
                            }
                        }, throwable -> {
                            Log.e(TAG, "error", throwable);
                        })
        );
    }

}
