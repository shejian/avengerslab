package com.avengers.weather.repository;

import com.avengers.weather.bean.CityWeatherBean;
import com.avengers.zombiebase.aacbase.IReqParam;
import com.avengers.zombiebase.aacbase.NetworkState;
import com.avengers.zombiebase.aacbase.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by duo.chen on 2018/7/24
 */
public class BaseCallback<T extends CityWeatherBean> implements Callback<T> {

    private Repository<? extends IReqParam, T> repository;

    public BaseCallback(Repository<? extends IReqParam, T> repository) {
        this.repository = repository;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if ("200".equals(response.body().getStatus())) {
            repository.saveData(response.body());
            repository.getNetWorkState().postValue(NetworkState.Companion.getLOADED());
        } else {
            repository.getNetWorkState().postValue(NetworkState.Companion.error("failed" + response.body().getMessage()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        repository.getNetWorkState().postValue(NetworkState.Companion.error("failed"));
    }

}
