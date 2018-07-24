package cn.wukang.kotlinrvbindadapter.module.viewmodel

import cn.wukang.kotlinrvbindadapter.databinding.FragmentStickyBinding
import cn.wukang.kotlinrvbindadapter.manager.CountryManager
import cn.wukang.kotlinrvbindadapter.model.Country

/**
 * Sticky Fragment ViewModel
 *
 * @author wukang
 */
class StickyViewModel : SortBaseFragmentViewModel<FragmentStickyBinding>() {
    override fun asc(): List<Country> = CountryManager.getInitialsMap().stickySort { it.keys.sorted() }

    override fun desc(): List<Country> = CountryManager.getInitialsMap().stickySort { it.keys.sortedDescending() }

    override fun shuffle(): List<Country> = CountryManager.getInitialsMap().stickySort { it.keys.shuffled() }

    // Sticky 排序的通用方法
    private fun Map<String, List<Country>>.stickySort(block: (Map<String, List<Country>>) -> List<String>): List<Country> {
        // 创建一个空list
        var data: List<Country> = listOf()
        // 根据首字母的顺序，添加对应的数据列表
        block(this).forEach { data += this.getOrElse(it) { listOf() }.sortedBy { it.countryNameEn } }
        return data
    }
}