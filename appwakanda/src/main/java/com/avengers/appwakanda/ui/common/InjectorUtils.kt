/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.avengers.appwakanda.ui.common

import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepository
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX
import com.avengers.appwakanda.webapi.Api

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    fun getNewDetailRepository(): NewsDetailRepositoryX {
        return NewsDetailRepositoryX.getInstance(
                Api.getSmartApi(),
                RoomHelper.getWakandaDb().newsDetailDao(),
                WakandaModule.appExecutors)
    }

    /*   private fun getPlantRepository(context: Context): PlantRepository {
           return PlantRepository.getInstance(AppDatabase.getInstance(context).plantDao())
       }

       private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
           return GardenPlantingRepository.getInstance(
                   AppDatabase.getInstance(context).gardenPlantingDao())
       }

       fun provideGardenPlantingListViewModelFactory(
           context: Context
       ): GardenPlantingListViewModelFactory {
           val repository = getGardenPlantingRepository(context)
           return GardenPlantingListViewModelFactory(repository)
       }

       fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
           val repository = getPlantRepository(context)
           return PlantListViewModelFactory(repository)
       }

       fun providePlantDetailViewModelFactory(
           context: Context,
           plantId: String
       ): PlantDetailViewModelFactory {
           return PlantDetailViewModelFactory(getPlantRepository(context),
                   getGardenPlantingRepository(context), plantId)
       }*/
}
