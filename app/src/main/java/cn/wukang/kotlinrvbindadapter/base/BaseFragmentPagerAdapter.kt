package cn.wukang.kotlinrvbindadapter.base

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.jetbrains.annotations.NotNull

/**
 * FragmentPager 通用适配器
 * @param fragList 碎片集合
 * @param fragTags 碎片tag集合
 *
 * @author wukang
 */
open class BaseFragmentPagerAdapter<F : BaseFragment<*, *>>(
        fm: FragmentManager, private var fragList: List<F>,
        private var fragTags: List<CharSequence>? = null) : FragmentPagerAdapter(fm) {
    init {
        if (fragTags != null) {
            if (fragList.size != fragTags!!.size)
                throw IllegalArgumentException("Fragment list size must be the same with tag list size.")
        }
    }

    override fun getItem(position: Int): F = fragList[position]

    override fun getCount(): Int = fragList.size

    override fun getPageTitle(position: Int): CharSequence = fragTags?.get(position) ?: ""
}