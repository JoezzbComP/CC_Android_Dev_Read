package cc.makepower.dev_model_one

import android.os.Bundle
import cc.makepower.dev_model_one.base.BaseActivity
import cc.makepower.dev_model_one.base.IBaseView
import kotlinx.android.synthetic.main.activity_main.*
import makpower.cc.civillock.base.APresenter

class MainActivity : BaseActivity(), MainContract.View {
    var mainPresenter: MainPresenter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun afterOnCreateView(savedInstanceState: Bundle?) {
        btn_Test.setOnClickListener{
            mainPresenter?.testRequeset("123")
        }
    }


    /**
     * 初始化presenter
     */
    override fun injectPresenter(): APresenter<IBaseView>? {
        if (mainPresenter == null) {
            mainPresenter = MainPresenter(this)
        }
        return mainPresenter as APresenter<IBaseView>
    }


    /**
     * 设置请求回调 ，告诉activity界面刷新
     */
    override fun testRequesetCallBack(resultData: String) {
       showToast(resultData)
    }


}
