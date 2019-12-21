package cc.makepower.dev_model_two.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import cc.makepower.dev_model_one.request.RestDataSource
import io.reactivex.disposables.Disposable

open class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {


    var disposable: Disposable? = null
    var restDataSource: RestDataSource = RestDataSource.getInstance(application.applicationContext)


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDisConnectLifecycle() {
        if (disposable != null) {
            disposable?.dispose()//请求取消
        }
        Log.d("MainViewModel", "onDisConnectLifecycle")
    }
}