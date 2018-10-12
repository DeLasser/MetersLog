package ru.mininn.meterslog.data.ble

import android.content.Context
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.Observable
import ru.mininn.meterslog.data.model.Meter
import ru.mininn.util.MeterParser
import java.util.*

class MeterBleScanner(context: Context) {
    private val scanSettings by lazy { ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build() }
    private val scanFilter by lazy { ScanFilter.empty() }

    private var bleClient: RxBleClient = RxBleClient.create(context)
    private var deviceScanner: Observable<ScanResult> = bleClient.scanBleDevices(scanSettings, scanFilter)



    fun getMeterScanner(): Observable<Meter> {
        return deviceScanner
                .filter {
                    it.bleDevice.macAddress.substring(0, 2) == "B0" || it.bleDevice.macAddress.substring(0, 2) == "B1"
                }
                .map {
                    return@map if (it.bleDevice.macAddress.substring(0, 2) == "B0") {
                        MeterParser.parseToMeterData(it.scanRecord.bytes, Date().time)
                    } else {
                        MeterParser.parseToMeterDataEncrypted(it.bleDevice.macAddress, it.scanRecord.bytes, Date().time)
                    }
                }.filter {
                    !it.isEncrypted
                }

    }

}