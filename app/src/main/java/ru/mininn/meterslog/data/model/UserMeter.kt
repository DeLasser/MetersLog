package ru.mininn.meterslog.data.model

import android.arch.persistence.room.Embedded

class UserMeter() {
    @Embedded
    var userMeterInfo : UserMeterInfo = UserMeterInfo()
    @Embedded
    var meterData: MeterModel = MeterModel()
}