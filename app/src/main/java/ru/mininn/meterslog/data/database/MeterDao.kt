package ru.mininn.meterslog.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ru.mininn.meterslog.data.model.Meter

@Dao
interface MeterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeter(meter: Meter)

    @Query("SELECT * FROM Meter")
    fun getMeters(): List<Meter>
}