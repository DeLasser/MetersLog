package ru.mininn.meterslog.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.mininn.meterslog.data.ble.MeterBleScanner
import ru.mininn.meterslog.data.database.MeterDatabase

class MainViewModel(application: Application) : AndroidViewModel(application){
    val statusLiveData = MutableLiveData<Boolean>()
    val metersLiveData by lazy {
        MeterDatabase.databaseBuilder(application.applicationContext).allowMainThreadQueries().build().getMeterDao().getUserMeters()}
    private  var scanDisposable: Disposable? = null
    private val bleScanner = MeterBleScanner(application.applicationContext)

    init {
        statusLiveData.value = false
    }

    fun startScan() {
        scanDisposable = bleScanner
                .getMeterScanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                },{

                })
    }

    fun stopScan() {
        scanDisposable?.dispose()
    }
}