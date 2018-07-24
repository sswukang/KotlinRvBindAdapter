package cn.wukang.kotlinrvbindadapter.module.viewmodel

import cn.wukang.kotlinrvbindadapter.databinding.FragmentSideStickyBinding
import cn.wukang.kotlinrvbindadapter.manager.CountryManager
import cn.wukang.kotlinrvbindadapter.model.Country
import cn.wukang.library.lib.side.SideBar

/**
 * Side Sticky Fragment ViewModel
 *
 * @author wukang
 */
class SideStickyViewModel : SortBaseFragmentViewModel<FragmentSideStickyBinding>() {
    override fun asc(): List<Country> = CountryManager.getInitialsMap().sideStickySort { it.keys.sorted() }

    override fun desc(): List<Country> = CountryManager.getInitialsMap().sideStickySort { it.keys.sortedDescending() }

    override fun shuffle(): List<Country> = CountryManager.getInitialsMap().sideStickySort { it.keys.shuffled() }

    // Side Sticky 排序的通用方法
    private fun Map<String, List<Country>>.sideStickySort(block: (Map<String, List<Country>>) -> List<String>)
            : List<Country> = sideStickyOtherSet(block(this), this)

    // Side Sticky 排序的其他设置
    private fun sideStickyOtherSet(keys: List<String>, initials: Map<String, List<Country>>): List<Country> {
        // 创建一个空list
        var data: List<Country> = listOf()
        // 根据首字母的顺序，添加对应的数据列表
        keys.forEach { data += initials.getOrElse(it) { listOf() }.sortedBy { it.countryNameEn } }
        // rv设置
        with(getDataBinding().sideStickyRv) {
            setIndexItems(keys)
            setOnSelectIndexItemListener(object : SideBar.OnSelectIndexItemListener {
                override fun onSelectIndexItem(index: String) {
                    val position: Int = data.indexOfFirst { index == it.countryNameEn.take(1) }
                    if (0 <= position && position < data.size) {
                        this@with.moveToPosition(position)
                    }
                }
            })
        }
        return data
    }
}