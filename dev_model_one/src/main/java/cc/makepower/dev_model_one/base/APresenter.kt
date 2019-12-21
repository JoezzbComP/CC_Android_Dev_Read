package makpower.cc.civillock.base

import android.content.Context
import cc.makepower.dev_model_one.request.RestDataSource
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

/**
author : atex
 *     e-mail : xxx@xx
 *     time   : 2018/11/29
 *     desc   :
 *     version: 1.0
 */
abstract class APresenter<T> : LifecyclePresenter<T> {

    private var mView: T? = null
     var disposable: Disposable? = null
    private var context: Context
    var restDataSource: RestDataSource


    constructor(context: Context) {
        this.context = context
        restDataSource = RestDataSource.getInstance(context)
    }


    fun showError(e: Throwable): String {
        e.printStackTrace()

        if (e is RuntimeException) {

            return "运行异常" + e.message
        }
        if (e is ConnectException) {
            return "网络连接异常"
        }
        if (e is SocketTimeoutException) {
            return "网络连接超时"
        }
        return if (e is HttpException && e.response().code() != HttpURLConnection.HTTP_OK) {
            "http异常,错误码:" + e.response().code()
        } else "未知错误:" + e.toString()

    }

    override fun attachView(t: T) {
        this.mView = t
    }

    override fun detachView() {

    }

    fun unSubscribe() {
        disposable?.dispose()
    }

    fun getView(): T? {
        return this!!.mView
    }


}