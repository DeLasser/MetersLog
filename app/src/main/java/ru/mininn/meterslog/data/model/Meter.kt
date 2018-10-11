package ru.mininn.meterslog.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.util.*

@Entity(foreignKeys = arrayOf(ForeignKey(entity = UserMeter::class,
        parentColumns = arrayOf("meterUId"),
        childColumns = arrayOf("deviceUId"))))
class Meter(): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var isEncrypted: Boolean = false
    var deviceUId: String = ""
    var packetCounter: Int = 0
    var packetVersion: Int = 0
    var deviceType: Int = 0
    var deviceModel: Int = 0
    var deviceSerNum: Int = 0
    var timestamp: Long = 0
    var deviceValue: Double? = null
    var deviceSecondValue: Double? = null
    var batteryValue: Int = 0
    var temperatureValue: Double = 0.toDouble()
    var lat: Double = 0.toDouble()
    var lng: Double = 0.toDouble()
    var accuracy: Double = 0.toDouble()
    var packageData: String = ""
    var isBackground: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        isEncrypted = parcel.readByte() != 0.toByte()
        deviceUId = parcel.readString()
        packetCounter = parcel.readInt()
        packetVersion = parcel.readInt()
        deviceType = parcel.readInt()
        deviceModel = parcel.readInt()
        deviceSerNum = parcel.readInt()
        timestamp = parcel.readLong()
        deviceValue = parcel.readValue(Double::class.java.classLoader) as? Double
        deviceSecondValue = parcel.readValue(Double::class.java.classLoader) as? Double
        batteryValue = parcel.readInt()
        temperatureValue = parcel.readDouble()
        lat = parcel.readDouble()
        lng = parcel.readDouble()
        accuracy = parcel.readDouble()
        packageData = parcel.readString()
        isBackground = parcel.readByte() != 0.toByte()
    }

    constructor(isEncrypted: Boolean,
                packetCounter: Int,
                packetVersion: Int,
                deviceType: Int,
                deviceModel: Int,
                deviceSernum: Int,
                timestamp: Long,
                deviceValue: Double?,
                deviceSecondValue: Double?,
                batteryValue: Int,
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
        this.deviceSerNum = deviceSernum
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
        this.deviceUId = generateDeviceUid()
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

    private fun generateDeviceUid(): String = "$deviceType-$deviceModel-$deviceSerNum${if (isEncrypted) {
        "-Encrypted"
    } else {
        ""
    }}"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (isEncrypted) 1 else 0)
        parcel.writeString(deviceUId)
        parcel.writeInt(packetCounter)
        parcel.writeInt(packetVersion)
        parcel.writeInt(deviceType)
        parcel.writeInt(deviceModel)
        parcel.writeInt(deviceSerNum)
        parcel.writeLong(timestamp)
        parcel.writeValue(deviceValue)
        parcel.writeValue(deviceSecondValue)
        parcel.writeInt(batteryValue)
        parcel.writeDouble(temperatureValue)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeDouble(accuracy)
        parcel.writeString(packageData)
        parcel.writeByte(if (isBackground) 1 else 0)
    }

    fun update(item: Meter) {
        this.isEncrypted = item.isEncrypted
        this.packetCounter = item.packetCounter
        this.packetVersion = item.packetVersion
        this.deviceType = item.deviceType
        this.deviceModel = item.deviceModel
        this.deviceSerNum = item.deviceSerNum
        this.timestamp = item.timestamp
        this.deviceValue = item.deviceValue
        this.deviceSecondValue = item.deviceSecondValue
        this.batteryValue = item.batteryValue
        this.temperatureValue = item.temperatureValue
        this.lat = item.lat
        this.lng = item.lng
        this.accuracy = item.accuracy
        this.packageData = item.packageData
        this.isBackground = item.isBackground
        this.deviceUId = item.generateDeviceUid()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Meter> {
        override fun createFromParcel(parcel: Parcel): Meter {
            return Meter(parcel)
        }

        override fun newArray(size: Int): Array<Meter?> {
            return arrayOfNulls(size)
        }
    }
}