package ru.mininn.meterslog.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.util.Log
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.mininn.meterslog.data.ble.MeterBleScanner
import ru.mininn.meterslog.data.model.Meter
import ru.mininn.meterslog.ui.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {


    val statusLiveData = MutableLiveData<Boolean>()
    val metersLiveData = MutableLiveData<List<Meter>>()
    private var scanDisposable: Disposable? = null
    private val bleScanner = MeterBleScanner(application.applicationContext)

    init {
        statusLiveData.value = false
        metersLiveData.value = ArrayList()
    }

    override fun onCleared() {
        super.onCleared()
        stopScan()
    }

    fun subscribeForUpdates(lifecycleOwner: LifecycleOwner, observer: Observer<List<Meter>>) {
        metersLiveData.observe(lifecycleOwner, observer)
    }

    fun changeScanStatus() {
        statusLiveData.value?.let {
            statusLiveData.value = !it
            Log.d("asd", "${it}")
            if (it) {
                stopScan()
            } else {
                startScan()
            }
        }
    }

    private fun startScan() {
        statusLiveData.value = true
        scanDisposable?.dispose()
        scanDisposable = bleScanner
                .getMeterScanner()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    val result = ArrayList(metersLiveData.value)
                    kotlin.run {
                        result.forEach {item ->
                            if (it.deviceUId == item.deviceUId){
                                item.update(it)
                                return@run
                            }
                        }
                        result.add(it)
                    }
                    metersLiveData.postValue(result)
                }, {
                    Log.d("asdError", it.localizedMessage)
                    it.printStackTrace()
                    messageLiveData.postValue(it.localizedMessage)
                    stopScan()
                })
    }

    private fun stopScan() {
        statusLiveData.postValue(false)
        scanDisposable?.dispose()
    }
}