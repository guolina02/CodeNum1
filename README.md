# CodeNum1
第一行代码2部分练习

chapter4 简易新闻应用
Fragment
Qualifiers(限定符)
    size: small normal large xlarge 480x360
    resolution: ldpi(<120dpi)
                mdpi(120dpi-160dpi)
                hdpi(160dpi-240dpi)
                xhdpi(240dpi-320dpi)
                xxhdpi(320dpi-480dpi)
    orientation: land port
    smallest-width: sw600dp(width>600dp) sw720dp
    width: w600dp
    height: h720dp
    language: en fr
    combine: drawable-en-port-xhdpi layout-w600dp-land values-fr-480x360

chapter5 强制下线功能
Broadcast, ActivityController
静态注册的Receiver不能启动AlertDialog，（因为它的Context是ReceiverRestrictedContext），所以使用动态注册。
而弹框只是需要当前页面显示就行了，其他页面不需要接收这个广播，所以注册注销广播的操作放在了onResume和onPause中，一般都是在onCreate和onDestroy中的。

chapter6 LitePal

chapter7 通讯录，跨进程访问数据
ContentProvider, Permission动态申请

chapter11 地图，定位

chapter12 design包
DrawerLayout
NavigationView
FloatingActionButton
RecyclerView
SwipeRefreshLayout
Toolbar
CoordinatorLayout
AppBarLayout
Snackbar

chapter13 LogUtils

chapter14 天气应用
DB
Gson
OkHttp3
Glide等