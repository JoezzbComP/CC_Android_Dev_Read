package cc.makepower.dev_model_two

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cc.makepower.dev_model_two.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit var mainViewModel: MainViewModel

    override fun setLifecycleObserver(): LifecycleObserver? {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        return mainViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun afterOnCreateView(savedInstanceState: Bundle?) {

        /**
         * livedata监听数据  如果list变化 会及时通知到
         */
        mainViewModel.getData().observe(this, Observer {

            showToast(it[0])
        })

        btn_Test.setOnClickListener {
            mainViewModel.fetchRemoteData()
        }


    }

}
