package io.github.viniciusalvesmello.findviewbyidtoviewbinding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _counter: MutableLiveData<Int> = MutableLiveData()
    val counter: LiveData<Int>
        get() = _counter

    fun incCounter() {
        _counter.postValue(_counter.value?.inc() ?: 1)
    }
}
