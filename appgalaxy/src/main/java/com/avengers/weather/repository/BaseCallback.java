package com.avengers.weather.repository;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.Nullable;

import com.avengers.weather.bean.CityWeatherBean;
import com.avengers.zombiebase.aacbase.IReqParam;
import com.avengers.zombiebase.aacbase.NetworkState;
import com.avengers.zombiebase.aacbase.Repository;
import com.avengers.zombiebase.ui.LaeView;
import com.bty.retrofit.net.callAdapter.LifeCallAdapterFactory;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by duo.chen on 2018/7/24
 */
public class BaseCallback<T extends CityWeatherBean> extends LifeCallAdapterFactory.LifeCallback<T> {

    private WeakReference<Repository<? extends IReqParam, T>> repositoryRef;

    @Nullable
    private WeakReference<LaeView> activityRef;

    public BaseCallback(@Nullable LifecycleOwner lifecycleOwner, LaeView laeView, Repository<? extends IReqParam, T> repository) {
        super(lifecycleOwner);

        this.activityRef = new WeakReference<>(laeView);
        this.repositoryRef = new WeakReference<Repository<? extends IReqParam, T>>(repository);

        if (activityRef.get() != null) {
            activityRef.get().showLoadView();
        }
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if ("200".equals(response.body().getStatus())) {
            if (activityRef != null) {
                activityRef.get().showContentView();
            }
            if (repositoryRef.get() != null) {
                repositoryRef.get().saveData(response.body());
                repositoryRef.get().getNetWorkState().postValue(NetworkState.Companion.getLOADED());
            }
        } else {
            if (activityRef != null) {
                activityRef.get().showErrorView(response.body().getMessage());
            }
            if (repositoryRef.get() != null) {
                repositoryRef.get().getNetWorkState().postValue(NetworkState.Companion.error("failed" + response.body().getMessage()));
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (activityRef != null) {
            activityRef.get().showErrorView(t.getMessage());
        }
        if (repositoryRef.get() != null) {
            repositoryRef.get().getNetWorkState().postValue(NetworkState.Companion.error("failed"));
        }
    }

}
