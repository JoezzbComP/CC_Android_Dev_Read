## CC_Android_Dev_Read

android开发架构技术栈

#dev_model_one 
`结构分明，每一层都有自己要做的事情，便于合作开发，有人负责逻辑处理，有人负责页面刷新，等`
+ 1， retrofit: 负责网络请求，将所有的请求都交给RestDataSource对象，所有的网络请求由它完成 
+ 2， mvp:代码结构，activity负责接收数据和刷新界面，presenter负责处理数据（获取，存储）。contract作为中心件，作为两者的桥梁回调数据给actiivty
+ 3， rxjava2:让请求更格式化，所有的请求返回都是一个Observable对象。可以定义请求的线程，以及数据回调给activity需要用什么线程
+ 4，HttpResultInterceptorFunc.class 负责解析掉第一层的json。需要和后台定义好第一层的json格式，如果返回的code为错误码，return异常，rxjava接收到异常不会进行下一步的解析

    (随机更新)
>> #####1,Room:作为本地数据存储数据，需要注意版本的更新，每次当前版本和上一个版本的数据库表结构有改动，需要编写对应的sql兼容迁移；可像 RestDataSource对象封装在一个对象中，所有的数据库存储由该类负责
>> #####2,EventBus:跨组件通信，只要在对应的地方注册eventbus。可以使用eventbus进行数据传输
>> #####3,AppManager:一个avtivity管理类，作用在注销登陆需要删除已启动的activity

***

#dev_model_two
`可以像mvp模式一样使用，针对各种App开发，比mvp代码量更少，google android jectpack 开发环境。更新多,目前用的只是冰山一角`
+ android jectpack
    + Architecture Compinents（架构组件）
        + Data Bingding（数据绑定）
        + Room（数据库）
        + WorkManager（后台任务管家）
        + - [x] Lifecycle（生命周期）
        + Navigation（导航）
        + Paging（分页）
        + Data Binding（数据绑定）
        + - [x] LiveData（底层数据通知更改视图）
        + - [x] ViewModel（以注重生命周期的方式管理界面的相关数据）
    + Foundation（基础）
         + - [x] AppCompat（向后兼容）
         + - [x] Android KTX（编写更加简洁的Kotlin代码）
         + Multidex （多处理dex的问题）
         + Test（测试）
    + UI(视觉交互)
         + Animation & transitions（动画和过渡）
         + Auto（Auto组件）
         + Emoji（标签）
         + Fragment（Fragment）
         + - [x] Layout（布局）
         + Palette（调色板）
         + TV（TV）
         + Wear OS by Google（穿戴设备）
    + Behavior（行为）
         +  Download manager（下载给管理器）
         +  Media & playback（媒体和播放）
         + Notifications（通知）
         + Permissions（权限）
         + Preferences（偏好设置）
         + Sharing（共享）
         + Slices（切片）

+ 1，retrofit: 负责网络请求，将所有的请求都交给RestDataSource对象，所有的网络请求由它完成
+ 2，ViewModel: 替代MVP架构中的P层
+ 3，Lifecycle: 可以将activity的生命周期交给viewmodel及其他中间件使用。做出对应的操作
+ 4，LiveData:  将数据源放在LiveData中，该对象可以交由其他组件监听，如果数据源发生改变，则监听对象可直接收到到数据更新的信息。

* *** A，所有ViewModel需继承BaseViewModel,BaseViewModel中有RestDataSource对象可以直接访问远程数据。如果有本地数据，也将对应的本地数据存储对象放在BaseViewModel中，方便调用 ***
* *** B，BaseViewModel中有lifecycle对应的组件，监听了对应的avtivity/fragment对象的生命周期，如果activity销毁，对应的请求也需要销毁，避免内存泄漏； ***
