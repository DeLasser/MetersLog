package ru.mininn.meterslog.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.mininn.meterslog.data.ble.MeterBleScanner
import ru.mininn.meterslog.data.database.MeterDatabase
import ru.mininn.meterslog.data.model.UserMeter
import ru.mininn.meterslog.data.model.UserMeterInfo

class MainViewModel(application: Application) : AndroidViewModel(application) {


    val statusLiveData = MutableLiveData<Boolean>()
    val metersLiveData = MutableLiveData<List<UserMeter>>()
    val database by lazy {
        MeterDatabase.databaseBuilder(application.applicationContext).allowMainThreadQueries().build()
    }
    val userMetersObservable by lazy {
        database.getMeterDao().getUserInfoLiveData()
    }

    val userMeterInfoObserver = Observer<List<UserMeterInfo>>{
        Log.d("asdasd", "obsever")
        metersLiveData.value = database.getMeterDao().getUserMeters()
        Log.d("asdasd", "obsever")
    }

    private var scanDisposable: Disposable? = null
    private val bleScanner = MeterBleScanner(application.applicationContext)

    init {
        statusLiveData.value = false
        metersLiveData.value = database.getMeterDao().getUserMeters()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("asdasd", "onCleared")
    }

    fun subscribeForUpdates(lifecycleOwner: LifecycleOwner, observer: Observer<List<UserMeter>>) {
        database.getMeterDao().getUserInfoLiveData().observe(lifecycleOwner, Observer {
            metersLiveData.value = database.getMeterDao().getUserMeters()
        })
        metersLiveData.observe(lifecycleOwner, observer)
    }

    fun startScan() {
        scanDisposable = bleScanner
                .getMeterScanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {

                })
    }

    fun stopScan() {
        scanDisposable?.dispose()
    }
}