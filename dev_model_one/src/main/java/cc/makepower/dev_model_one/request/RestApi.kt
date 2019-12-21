package cc.makepower.dev_model_one.request

import cc.makepower.dev_model_one.bean.ResultBean
import io.reactivex.Observable
import retrofit2.http.*

interface RestApi {
    @FormUrlEncoded
    @POST("v1/user/register")
    fun registerUser(
        @Field("account") user: String,
        @Field("password") pwd: String
    ): Observable<ResultBean<String>>

    @GET("v1/lock/lockList")
    fun getLockList(@QueryMap parm: MutableMap<String, Any>): Observable<ResultBean<String>>
}