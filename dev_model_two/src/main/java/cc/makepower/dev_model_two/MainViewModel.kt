package cc.makepower.dev_model_two

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import cc.makepower.dev_model_two.base.BaseViewModel

class MainViewModel constructor(application: Application) : BaseViewModel(application = application), LifecycleObserver {


    var mutableLiveDataList: MutableLiveData<List<String>>? = null//需要监听的 数据源  只要数据变更，activity就能监听到


    fun getData(): MutableLiveData<List<String>> {
        if (mutableLiveDataList == null) {
            mutableLiveDataList = MutableLiveData()
        }
        return mutableLiveDataList!!
    }


    /**
     * 模拟从远程获取数据
     */
    fun fetchRemoteData() {
//        restDataSource?.getLockList(0, 0, null)
//            ?.subscribeOn(Schedulers.newThread())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe({
//                //                mutableLiveDataList?.postValue(it)
////                saveData2LocalDB(it)//
//            }, {
//                //                mutableLiveDataList?.postValue(data)
//            }, {
//            }, {
//                disposable = it
//
//            })

        Log.d("MainViewModel", if(restDataSource==null) "error" else "success")
        var data = listOf("测试的数据")
        mutableLiveDataList?.value = data//将mutableLiveDataList的值修改了 ，对应activity就能知道
        saveData2LocalDB(data)//
    }

    //保存数据到本地数据库
    fun saveData2LocalDB(data: List<String>) {
        Log.d("MainViewModel", "saveData2LocalDB Success")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onConnectLifecycle() {
        Log.d("MainViewModel", "onConnectLifecycle")

    }


}