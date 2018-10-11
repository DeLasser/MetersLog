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

class MainViewModel(application: Application) : AndroidViewModel(application) {


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
        Log.d("asdasd", "onCleared")
    }

    fun subscribeForUpdates(lifecycleOwner: LifecycleOwner, observer: Observer<List<Meter>>) {
        metersLiveData.observe(lifecycleOwner, observer)
    }

    fun startScan() {
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

                })
    }

    fun stopScan() {
        scanDisposable?.dispose()
    }
}