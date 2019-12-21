package cc.makepower.dev_model_one.request

import android.content.Context
import cc.makepower.dev_model_one.bean.ResultBean
import cc.makepower.dev_model_two.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import makpower.cc.civillock.retrofit.HttpResultInterceptorFunc
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestDataSource private constructor(context: Context) {


    private var restApi: RestApi
    private var gsonBuilder: Gson

    init {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)//设置超时时间
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain?): okhttp3.Response? {
                    var original = chain?.request()
                    //请求头里添加部分参数  服务器可以从请求头中拿到想到的数据
                    var requestBuilder = original?.newBuilder()?.header("device", "android")
                        ?.header("version", BuildConfig.VERSION_NAME)
                    return chain?.proceed(requestBuilder?.build())
                }
//
            })
//            .cookieJar(CookiesManager(context))//cookies持久化()..需要cookie可加


        if (BuildConfig.DEBUG) {
//            val loggerInterceptor = HttpLoggerInterceptor()
//            builder.addInterceptor(loggerInterceptor)//日志输出
        }
        val client = builder.build()


        gsonBuilder = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .setVersion(1.0)
            .create()


        restApi = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl("http://172.16.7.218:8084/civillock/")
//                .baseUrl("http://172.16.4.117:8084/civillock/")
            .baseUrl("http://172.28.88.136:8084/civillock/")
            .build()
            .create(RestApi::class.java)
    }


    //静态方法
    companion object {
        @Volatile
        private var INSTANCE: RestDataSource? = null

        fun getInstance(con: Context): RestDataSource {
            if (INSTANCE == null) {
                synchronized(RestDataSource::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = RestDataSource(con)
                    }
                }
            }
            return INSTANCE!!
        }
    }


    fun registerUser(account: String, password: String): Observable<ResultBean<String>> {
        return restApi.registerUser(account, password)
            .throttleFirst(5, TimeUnit.SECONDS)
            .map(HttpResultInterceptorFunc())
    }


    fun getLockList(
        pageSize: Int,
        pageIndex: Int,
        bean: String?
    ): Observable<ResultBean<String>> {
        var maps = mutableMapOf<String, Any>()
        maps.put("pageSize", pageSize)
        maps.put("pageIndex", pageIndex)

        return restApi.getLockList(maps)
            .throttleFirst(5, TimeUnit.SECONDS)
            .map(HttpResultInterceptorFunc())
    }


}