package cc.makepower.dev_model_one

import android.content.Context
import makpower.cc.civillock.base.APresenter

/**
 * 负责请求，和数据有关的代码都卸载该类里面。
 */
class MainPresenter constructor(context: Context): APresenter<MainContract.View>(context =context ) ,MainContract{


    override fun testRequeset(parameter: String) {
        //网络请求
//        restDataSource?.getLockList(0, 0, null)
//            ?.subscribeOn(Schedulers.newThread())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe({
//                getView()?.testRequesetCallBack("请求成功")
//            }, {
//                getView()?.testRequesetCallBack("请求失败")
//            }, {
//            }, {
//                disposable = it
//            })
        getView()?.testRequesetCallBack("请求成功")

    }

}