# **[NiceStateView](https://github.com/simplepeng/NiceStateView)**

![MIT](https://img.shields.io/badge/License-MIT-orange?style=flat-square)  ![Jcenter](https://img.shields.io/badge/Jcenter-1.0.0-brightgreen?style=flat-square)  ![Androidx](https://img.shields.io/badge/Androidx-Yes-blue?style=flat-square)  ![Api](https://img.shields.io/badge/Api-14+-blueviolet?style=flat-square)  ![Kotlin](https://img.shields.io/badge/Kotlin-Yes-ff6984?style=flat-square)

一个超赞的页面状态切换库（加载中，空布局，网络错误，重试）

| Loading | Empty | Error | Retry |
| ------- | ----- | ----- | ----- |
| ![](images/img_loading.png) | ![](images/img_empty.png) | ![](images/img_error.png) | ![](images/img_retry.png) |

## 导入依赖

```groovy
implementation 'me.simple:nice-state-view:x.y.z'
```

## 使用默认样式

```kotlin
private val niceStateView: NiceStateView by lazy {
        NiceStateView.builder()
            .registerLoading(NiceSampleLoadingView())
            .registerEmpty(NiceSampleEmptyView())
            .registerError(NiceSampleErrorView())
            .registerRetry(NiceSampleRetryView())
            .wrapContent(view_content)
    }
...
niceStateView.showLoading()
niceStateView.showEmpty()
niceStateView.showError()
niceStateView.showRetry()
niceStateView.showContent()
//设置点击事件
//showLoading()，showEmpty()，showError()，showRetry()都可以设置点击事件
niceStateView.showRetry().setOnViewClickListener(R.id.iv_retry) {
                niceStateView.showLoading()
                view_content.postDelayed({
                    niceStateView.showContent()
                }, 2000)
            }.setOnViewClickListener(R.id.view_retry) {
                toast("view_retry click")
            }
```

## 自定义样式

继承`IStateView`类，重写需要用到的方法。

```kotlin
class NiceSampleLoadingView : IStateView() {

    /**
     * 设置填充的布局文件
     */
    override fun setLayoutRes() = R.layout.sample_loading_view

    /**
     * 初始化一些耗时的动画资源
     */
    override fun onAttach(view: View) {
        super.onAttach(view)

    }

    /**
     * 释放一些耗时的动画资源，避免内存泄露
     */
    override fun onDetach(view: View) {
        super.onDetach(view)

    }

}
```

## 赞助

如果您觉得`SpideMan`帮助了您，可否扶贫一下作者，要是能`10.24`就太👍👍👍啦！

您的支持是作者继续努力创作的动力😁😁😁。

萌戳下方链接精准扶贫⤵️⤵️⤵️

**[扶贫方式](https://simplepeng.github.io/merge_pay_code/)**

