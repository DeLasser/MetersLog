package ru.mininn.meterslog.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.mininn.meterslog.data.model.Meter

@Database(entities = [Meter::class], version = 1)
abstract class MeterDatabase : RoomDatabase() {
    companion object {
        fun databaseBuilder(context: Context): Builder<MeterDatabase> {
            return Room
                    .databaseBuilder(context, MeterDatabase::class.java, "database.db")
        }
    }

    abstract fun getMeterDao(): MeterDao
}