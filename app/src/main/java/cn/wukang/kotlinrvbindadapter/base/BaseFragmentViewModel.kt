package cn.wukang.kotlinrvbindadapter.base

import android.databinding.ViewDataBinding

/**
 * Fragment ViewModel 基类
 *
 * @author wukang
 */
open class BaseFragmentViewModel<B : ViewDataBinding> {
    // fragment
    private lateinit var mFragment: BaseFragment<B, *>
    // 视图绑定对象
    private lateinit var mDataBinding: B

    fun <M : BaseFragmentViewModel<B>> initViewModel(fragment: BaseFragment<B, M>) {
        mFragment = fragment
        mDataBinding = fragment.getDataBinding()
    }

    fun onCreate() {
        // do something...
    }

    fun onDestroy() {
        // do something...
    }

    open fun getFragment(): BaseFragment<B, *> = mFragment

    fun getDataBinding(): B = mDataBinding
}