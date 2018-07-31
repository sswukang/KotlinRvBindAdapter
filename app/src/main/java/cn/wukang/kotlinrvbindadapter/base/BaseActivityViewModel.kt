package cn.wukang.kotlinrvbindadapter.base

import android.databinding.ViewDataBinding

/**
 * Activity ViewModel 基类
 *
 * @author wukang
 */
open class BaseActivityViewModel<B : ViewDataBinding> {
    // Activity
    private lateinit var mActivity: BaseActivity<B, *>
    // 视图绑定对象
    private lateinit var mDataBinding: B

    fun <M : BaseActivityViewModel<B>> initViewModel(activity: BaseActivity<B, M>) {
        mActivity = activity
        mDataBinding = activity.getDataBinding()
    }

    open fun onCreate() {
        // do something...
    }

    open fun onDestroy() {
        // do something...
    }

    open fun getActivity(): BaseActivity<B, *> = mActivity

    fun getDataBinding(): B = mDataBinding
}