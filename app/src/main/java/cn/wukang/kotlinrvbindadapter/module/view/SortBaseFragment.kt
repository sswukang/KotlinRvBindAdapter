package cn.wukang.kotlinrvbindadapter.module.view

import android.databinding.ViewDataBinding
import cn.wukang.kotlinrvbindadapter.base.BaseFragment
import cn.wukang.kotlinrvbindadapter.model.Country
import cn.wukang.kotlinrvbindadapter.module.viewmodel.SortBaseFragmentViewModel
import cn.wukang.kotlinrvbindadapter.use

/**
 * Sort Fragment 基类
 *
 * @author wukang
 */
abstract class SortBaseFragment<B : ViewDataBinding, M : SortBaseFragmentViewModel<B>> : BaseFragment<B, M>() {
    // 正序并刷新
    fun asc() = getViewModel().asc().use { updateAdapter(it) }

    // 倒序并刷新
    fun desc() = getViewModel().desc().use { updateAdapter(it) }

    // 乱序并刷新
    fun shuffle() = getViewModel().shuffle().use { updateAdapter(it) }

    protected abstract fun updateAdapter(dataList: List<Country>)

    protected fun getMainActivity(): MainActivity = requireActivity() as MainActivity
}