package ru.mininn.meterslog.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ru.mininn.meterslog.data.model.MeterModel
import ru.mininn.meterslog.data.model.UserMeter
import ru.mininn.meterslog.data.model.UserMeterInfo

@Dao
interface MeterDao {

    @Insert
    fun insertMeter(meter: MeterModel)

    @Insert
    fun insertMeter(userMeterData: UserMeterInfo)

    @Query("SELECT UserMeterInfo.deviceUId, UserMeterInfo.dataType, UserMeterInfo.description FROM UserMeterInfo")
    fun getUserMeters() : LiveData<List<UserMeter>>
}