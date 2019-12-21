package makpower.cc.civillock.base

/**
author : atex
 *     e-mail : xxx@xx
 *     time   : 2018/11/29
 *     desc   :
 *     version: 1.0
 */
interface LifecyclePresenter<T>{
    fun  attachView(t:T)

    fun  detachView()
}