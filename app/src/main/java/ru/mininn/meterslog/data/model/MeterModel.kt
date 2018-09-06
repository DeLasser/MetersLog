package ru.mininn.meterslog.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
class MeterModel() {
    @PrimaryKey(autoGenerate = true)
    var  id: Int = 0
    var isEncrypted: Boolean = false
    var packetCounter: Int? = null
    var packetVersion: Int? = null
    var deviceType: Int = 0
    var deviceModel: Int = 0
    var deviceSerNum: Int = 0
    var timestamp: Long = 0
    var deviceValue: Double? = null
    var deviceSecondValue: Double? = null
    var batteryValue: Double? = null
    var temperatureValue: Double? = null
    var lat: Double? = null
    var lng: Double? = null
    var accuracy: Double? = null
    @NotNull
    var isBackground: Boolean = false
    var packageData: String = ""
    var deviceUId : String= ""

    constructor(isEncrypted: Boolean,
                packetCounter: Int,
                packetVersion: Int,
                deviceType: Int,
                deviceModel: Int,
                deviceSerNum: Int,
                timestamp: Long,
                deviceValue: Double,
                deviceSecondValue: Double,
                batteryValue: Double,
                temperatureValue: Double,
                lat: Double,
                lng: Double,
                accuracy: Double,
                packageData: String,
                isBackground: Boolean) : this() {
        this.isEncrypted = isEncrypted
        this.packetCounter = packetCounter
        this.packetVersion = packetVersion
        this.deviceType = deviceType
        this.deviceModel = deviceModel
        this.deviceSerNum = deviceSerNum
        this.timestamp = timestamp
        this.deviceValue = deviceValue
        this.deviceSecondValue = deviceSecondValue
        this.batteryValue = batteryValue
        this.temperatureValue = temperatureValue
        this.lat = lat
        this.lng = lng
        this.accuracy = accuracy
        this.packageData = packageData
        this.isBackground = isBackground
        this.deviceUId = generateDeviceUId()
    }

    override fun toString(): String {
        return if (isEncrypted) {
            "isEncrypted: $isEncrypted \n" +
                    "deviceType: $deviceType \n" +
                    "deviceModel: $deviceModel \n" +
                    "deviceSerNum: $deviceSerNum \n" +
                    "timestamp: $timestamp  = ${Date(timestamp)} \n" +
                    "packageData: $packageData"
        } else {
            "isEncrypted: $isEncrypted \n" +
                    "packetCounter: $packetCounter \n" +
                    "packetVersion: $packetVersion \n" +
                    "deviceType: $deviceType \n" +
                    "deviceModel: $deviceModel \n" +
                    "deviceSerNum: $deviceSerNum \n" +
                    "timestamp: $timestamp  = ${Date(timestamp)} \n" +
                    "deviceValue: $deviceValue\n" +
                    "deviceSecondValue: $deviceSecondValue\n" +
                    "batteryValue: $batteryValue\n" +
                    "temperatureValue: $temperatureValue\n" +
                    "packageData: $packageData"

        }
    }

    private fun generateDeviceUId(): String = "$deviceType-$deviceModel-$deviceSerNum${if (isEncrypted) {
        "-Encrypted"
    } else {
        ""
    }}"
}