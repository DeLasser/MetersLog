package ru.mininn.meterslog.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class UserMeterInfo(@PrimaryKey
                    var deviceUId: String ,
                    var dataType: Int ,
                    var description: String)