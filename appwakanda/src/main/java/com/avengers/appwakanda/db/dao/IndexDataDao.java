package com.avengers.appwakanda.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.avengers.appwakanda.ui.indexmain.vm.ContextItemBean;

import java.util.List;

@Dao
public interface IndexDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfo(ContextItemBean contextItemBean);

    @Query("SELECT * FROM ContextItemBean")
    LiveData<List<ContextItemBean>> quryInfos();

    @Query("SELECT COUNT(*) FROM ContextItemBean ")
    int hasData();

}
