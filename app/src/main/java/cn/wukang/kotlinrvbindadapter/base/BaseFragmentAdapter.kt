package cn.wukang.kotlinrvbindadapter.base

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.jetbrains.annotations.NotNull

/**
 * FragmentPager 通用适配器
 *
 * @author wukang
 */
open class BaseFragmentAdapter<F : BaseFragment<*, *>> : FragmentPagerAdapter {
    private lateinit var fragList: List<F> // 碎片集合
    private var fragTags: List<CharSequence>? = null // 碎片tag集合

    private constructor(fm: FragmentManager) : super(fm)

    constructor (fm: FragmentManager, @NotNull fragList: List<F>) : this(fm) {
        this.fragList = fragList
    }

    constructor (fm: FragmentManager, fragList: List<F>, fragTags: List<CharSequence>) : this(fm, fragList) {
        this.fragTags = fragTags

        if (fragList.size != fragTags.size)
            throw IllegalArgumentException("Fragment list size must be the same with tag list size.")
    }

    override fun getItem(position: Int): F = fragList[position]

    override fun getCount(): Int = fragList.size

    override fun getPageTitle(position: Int): CharSequence = fragTags?.get(position) ?: ""
}