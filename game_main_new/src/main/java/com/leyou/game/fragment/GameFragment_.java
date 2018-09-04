package com.leyou.game.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyou.game.R;
import com.leyou.game.activity.game.CategoryGameActivity;
import com.leyou.game.activity.game.GameSearchActivity;
import com.leyou.game.adapter.game.BannerViewHolder;
import com.leyou.game.adapter.game.GameAdapter;
import com.leyou.game.adapter.game.GameNewAdapter;
import com.leyou.game.base.BaseFragment;
import com.leyou.game.bean.game.GameBean;
import com.leyou.game.ipresenter.game.IGameFragment;
import com.leyou.game.presenter.game.GameFragmentPresenter;
import com.leyou.game.util.HttpUtil;
import com.leyou.game.util.LogUtil;
import com.leyou.game.util.ToastUtils;
import com.leyou.game.widget.dialog.LoadingProgressDialog;
import com.leyou.game.widget.dialog.NoNetWorkDialog;
import com.umeng.analytics.MobclickAgent;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description : 新版游戏板块页面
 *
 * @author : rocky
 * @Create Time : 2017/10/18 上午12:02
 * @Modified By: rocky
 * @Modified Time : 2017/10/18 上午12:02
 */
public class GameFragment_ extends BaseFragment implements IGameFragment, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GameFragment_";
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.ll_game_search)
    LinearLayout llGameSearch;
    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.card_new)
    CardView cardViewNewGame;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.game_recycler_new)
    RecyclerView gameRecyclerNew;
    @BindView(R.id.game_recycler)
    RecyclerView recycler;
    Unbinder unbinder;
    private GameFragmentPresenter presenter;
    private GameNewAdapter adapter;
    private List<GameBean> list = new ArrayList<>();
    private List<GameBean> listGames = new ArrayList<>();
    private GameAdapter gameAdapter;
    private boolean isLoading = false;
    private LoadingProgressDialog loadingProgressDialog;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && null != presenter) {
            if (!HttpUtil.isNetworkConnected(context)) {
                showNoNetWorkDialog();
            } else {
                onRefresh();
            }
        }
    }

    public GameFragment_() {
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_game_fragment_;
    }

    @Override
    protected void initView(View rootView, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, rootView);

        //设置下拉刷新背景颜色
        swipeRefresh.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.white_1));
        //设置刷新进度颜色
        swipeRefresh.setColorSchemeResources(R.color.blue_44, R.color.blue_42, R.color.purple_62, R.color.purple_74);
        swipeRefresh.setOnRefreshListener(this);

        //添加适配器
        adapter = new GameNewAdapter(getContext(), list);
        gameRecyclerNew.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        gameRecyclerNew.setAdapter(adapter);

        //添加适配器
        gameAdapter = new GameAdapter(getContext(), listGames);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(gameAdapter);
        recycler.setNestedScrollingEnabled(false);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == gameAdapter.getItemCount()) {
                    if (!isLoading) {
                        isLoading = true;
                        presenter.loadMoreGameList();
                    }
                }
            }
        });
    }

    @Override
    protected void initPresenter() {
        presenter = new GameFragmentPresenter(context, this);
    }

    @OnClick({R.id.ll_game_search, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_game_search:
                startActivity(new Intent(context, GameSearchActivity.class));
                break;
            case R.id.tv_more:
                startActivity(new Intent(context, CategoryGameActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setBannerList(List<GameBean> data) {
        if (null != data) {
            banner.setPages(data, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
            banner.start();
        }
    }

    @Override
    public void showDataNewList(List<GameBean> dataList) {
        swipeRefresh.setRefreshing(false);
        if (null != dataList && dataList.size() > 0) {
            cardViewNewGame.setVisibility(View.VISIBLE);
            adapter.setAdapterData(dataList);
        } else {
            cardViewNewGame.setVisibility(View.GONE);
        }
    }

    @Override
    public void showDataList(List<GameBean> dataList) {
        isLoading = false;
        swipeRefresh.setRefreshing(false);
        gameAdapter.setAdapterData(dataList);
        gameAdapter.setLoadAllData(false);
    }

    @Override
    public void showDataLoadMoreList(List<GameBean> gameBeanList) {
        isLoading = false;
        swipeRefresh.setRefreshing(false);
        gameAdapter.loadMoreData(gameBeanList);
    }

    @Override
    public void setDataLoadAll(boolean isLoadAll) {
        gameAdapter.setLoadAllData(isLoadAll);
    }

    @Override
    public void showNoNetWorkDialog() {
        new NoNetWorkDialog(context, false).show();
    }

    @Override
    public void onRefresh() {
        presenter.getBannerData();
        presenter.getNewUpData();
        presenter.getGame();
    }

    @Override
    public void showLoading() {
        loadingProgressDialog = new LoadingProgressDialog(context, false);
        loadingProgressDialog.show();
    }

    @Override
    public void changeLoadingDes(String des) {
        if (null != loadingProgressDialog) {
            loadingProgressDialog.setLoadingText(des);
        }
    }

    @Override
    public void dismissedLoading() {
        if (null != loadingProgressDialog) {
            loadingProgressDialog.dismiss();
        }
    }

    @Override
    public void showMessageToast(final String msg) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToastShort(msg);
            }
        });
    }
}
