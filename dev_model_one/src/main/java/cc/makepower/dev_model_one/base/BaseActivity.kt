package cc.makepower.dev_model_one.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import makpower.cc.civillock.base.APresenter

abstract class BaseActivity :AppCompatActivity() ,IBaseView{


    var aPresenter: APresenter<IBaseView>? = null
    abstract fun getLayoutId(): Int
    private fun beforeOnCreateView() {}
    abstract fun afterOnCreateView(savedInstanceState: Bundle?)

    lateinit var unbinder: Unbinder
    abstract fun injectPresenter(): APresenter<IBaseView>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        beforeOnCreateView()
        setContentView(getLayoutId())
//        AppManager.getInstance().addActivity(this)
//        EventBus.getDefault().register(this)
        aPresenter = injectPresenter()//RestDataSource是单例的  执行过一次后 所有的请求都可以调用
        if (aPresenter != null) {
            aPresenter!!.attachView(this)
        }
        //注册ButterKnife
        unbinder = ButterKnife.bind(this)
        afterOnCreateView(savedInstanceState)
    }

    override fun onDestroy() {
//        AppManager.getInstance().removeActivity(this)
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this)
//        }
        if (aPresenter != null) {
            aPresenter?.unSubscribe()
        }
        unbinder!!.unbind()
        super.onDestroy()
    }

    override fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(res: Int) {
        Toast.makeText(applicationContext, res, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(msg: Int) {

    }

    override fun showProgress(msg: String) {

    }

    override fun hideProgress(result: Boolean) {

    }

    /**
     * 沉浸式状态栏
     */
    fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            val localLayoutParams = window.attributes
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags)
        }
        //修改字体颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    open fun onGetMessage(message: MessageWrap) {
//
//    }

}