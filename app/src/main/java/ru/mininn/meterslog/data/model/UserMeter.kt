package ru.mininn.meterslog.data.model

import android.arch.persistence.room.Relation

class UserMeter() {
    var deviceUId: String = ""
    var dataType: Int = 0
    var description: String = ""
    @Relation(parentColumn = "deviceUId", entityColumn = "deviceUId")
    var meterData : List<MeterModel> = ArrayList<MeterModel>()
}