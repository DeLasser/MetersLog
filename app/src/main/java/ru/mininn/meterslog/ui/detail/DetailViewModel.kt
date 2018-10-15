package ru.mininn.meterslog.ui.detail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.mininn.meterslog.data.ble.MeterBleScanner
import ru.mininn.meterslog.data.model.Meter
import ru.mininn.meterslog.ui.BaseViewModel

class DetailViewModel(application: Application): BaseViewModel(application) {

    val meterLiveData = MutableLiveData<Meter>()

    private var disposable : Disposable? = null
    private val bleScanner = MeterBleScanner(application.applicationContext)

    fun setMeter(meter: Meter) {
        meterLiveData.postValue(meter)
        startScan(meter.deviceUId)
    }

    fun startScan(deviceUid: String) {
        disposable?.dispose()
        disposable = bleScanner
                .getMeterScanner()
                .filter{
                    meterLiveData.value?.let {item ->
                        it.deviceUId == deviceUid
                    } ?: kotlin.run {
                        return@run false
                    }
                }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    meterLiveData.postValue(it)
                }, {
                    messageLiveData.postValue(it.localizedMessage)
                    disposable?.dispose()
                })
    }

    fun stopScan() {
        disposable?.dispose()
    }
}