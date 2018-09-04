package ru.mininn.meterslog.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.mininn.meterslog.data.ble.MeterBleScanner
import ru.mininn.meterslog.data.model.Meter

class MainViewModel(application: Application) : AndroidViewModel(application){
    val statusLiveData = MutableLiveData<Boolean>()
    val metersLiveData = MutableLiveData<ArrayList<Meter>>()
    private  var scanDisposable: Disposable? = null
    private val bleScanner = MeterBleScanner(application.applicationContext)

    init {
        statusLiveData.value = false
        metersLiveData.value = ArrayList()
    }

    fun startScan() {
        metersLiveData.value?.clear()
        scanDisposable = bleScanner
                .getMeterScanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    metersLiveData.value?.add(it)
                    metersLiveData.value = metersLiveData.value
                },{

                })
    }

    fun stopScan() {
        scanDisposable?.dispose()
    }
}