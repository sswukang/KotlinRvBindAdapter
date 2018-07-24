package cn.wukang.kotlinrvbindadapter.module.viewmodel

import android.databinding.ViewDataBinding
import cn.wukang.kotlinrvbindadapter.base.BaseFragmentViewModel
import cn.wukang.kotlinrvbindadapter.model.Country
import cn.wukang.kotlinrvbindadapter.module.view.SortBaseFragment

/**
 * Sort Fragment ViewModel 基类
 *
 * @author wukang
 */
abstract class SortBaseFragmentViewModel<B : ViewDataBinding> : BaseFragmentViewModel<B>() {
    // 正序
    abstract fun asc(): List<Country>

    // 倒序
    abstract fun desc(): List<Country>

    // 乱序
    abstract fun shuffle(): List<Country>

    override fun getFragment(): SortBaseFragment<B, *> = super.getFragment() as SortBaseFragment
}