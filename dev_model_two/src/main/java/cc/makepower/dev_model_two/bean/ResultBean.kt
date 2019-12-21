package cc.makepower.dev_model_one.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
author : atex
 *     e-mail : xxx@xx
 *     time   : 2018/11/29
 *     desc   :
 *     version: 1.0
 */
data class ResultBean<T>(
        @Expose
        @SerializedName("resultCode")
        var code: Int
        ,
        @Expose
        @SerializedName("resultMessage")
        var message: String
        ,
        @Expose
        @SerializedName("data")
        var data: T)
