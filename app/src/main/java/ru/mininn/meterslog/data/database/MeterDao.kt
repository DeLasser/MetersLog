package ru.mininn.meterslog.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import ru.mininn.meterslog.data.model.MeterModel
import ru.mininn.meterslog.data.model.UserMeter
import ru.mininn.meterslog.data.model.UserMeterInfo

@Dao
interface MeterDao {

    @Insert
    fun insertMeter(meter: MeterModel) : Long

    @Insert
    fun insertMeter(userMeterData: UserMeterInfo) : Long

//    @Query("SELECT UserMeterInfo.meterUId, UserMeterInfo.dataType, UserMeterInfo.description FROM UserMeterInfo INNER JOIN MeterModel ON UserMeterInfo.meterUId = MeterModel.deviceUId WHERE MeterModel.timestamp IN (SELECT MAX(timestamp) FROM MeterModel GROUP BY deviceUId)")
//    fun getUserMeters() : LiveData<List<UserMeter>>

    @Query("SELECT * FROM MeterModel, UserMeterInfo WHERE MeterModel.timestamp IN(SELECT MAX(timestamp) FROM MeterModel GROUP BY deviceUId) AND MeterModel.deviceUId = UserMeterInfo.meterUId ")
    fun getUserMeters() : List<UserMeter>

    @Query("SELECT * FROM UserMeterInfo")
    fun getUserInfoLiveData() : LiveData<List<UserMeterInfo>>

}

