package cc.makepower.dev_model_one

import cc.makepower.dev_model_one.base.IBaseView

/**
 * 确认请求和请求回调
 */
interface MainContract {

    fun testRequeset( parameter:String)
    interface View :IBaseView{

        fun  testRequesetCallBack(resultData:String)
    }
}