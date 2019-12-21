package makpower.cc.civillock.retrofit

import cc.makepower.dev_model_one.bean.ResultBean
import io.reactivex.functions.Function


/**
author : atex
 *     e-mail : xxx@xx
 *     time   : 2018/11/29
 *     desc   :
 *     version: 1.0
 */
class HttpResultInterceptorFunc<T> :Function<ResultBean<T>,ResultBean<T>>{
    override fun apply(t: ResultBean<T>): ResultBean<T> {
        //过滤器  请求结果过滤
        if (t.code==200){
            return t
        }else{
            throw Exception()
        }
    }

}
