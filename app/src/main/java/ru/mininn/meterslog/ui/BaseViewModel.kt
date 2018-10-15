package ru.mininn.meterslog.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

open class BaseViewModel(application: Application): AndroidViewModel(application) {
    val messageLiveData = MutableLiveData<String>()
}