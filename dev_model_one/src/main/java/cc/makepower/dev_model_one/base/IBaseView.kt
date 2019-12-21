package cc.makepower.dev_model_one.base

interface IBaseView {
    fun showToast(msg:String)
    fun showToast(res:Int)
    fun showProgress(msg: String)
    fun showProgress(msg: Int)
    fun hideProgress(result: Boolean)
}