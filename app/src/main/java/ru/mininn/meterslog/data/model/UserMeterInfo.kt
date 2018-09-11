package ru.mininn.meterslog.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class UserMeterInfo() {
    @PrimaryKey
    var meterUId: String = ""
    var dataType: Int = 0
    var description: String = ""

    constructor(meterUId: String ,
                dataType: Int ,
                description: String): this(){
        this.meterUId = meterUId
        this.dataType = dataType
        this.description = description

    }

}