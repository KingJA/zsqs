package com.kingja.zsqs.ui.placement.list;

import android.support.annotation.NonNull;

import com.kingja.zsqs.net.api.LoadSirObserver;
import com.kingja.zsqs.net.api.LoadSirVisibleObserver;
import com.kingja.zsqs.net.api.UserApi;
import com.kingja.zsqs.net.entiy.PlacementItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Description：TODO
 * Create Time：2016/10/10 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlacementListPresenter implements PlacementListContract.Presenter {
    private UserApi mApi;
    private PlacementListContract.View mView;

    @Inject
    public PlacementListPresenter(UserApi mApi) {
        this.mApi = mApi;
    }

    @Override
    public void getPlacementList(String projectId) {
        mApi.getApiService().getPlacementList( projectId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe
                (new LoadSirVisibleObserver<List<PlacementItem>>(mView) {
                    @Override
                    protected void onSuccess(List<PlacementItem> placementItemList) {
                        mView.onGetPlacementListSuccess(placementItemList);
                    }
                });
    }

    @Override
    public void attachView(@NonNull PlacementListContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

}