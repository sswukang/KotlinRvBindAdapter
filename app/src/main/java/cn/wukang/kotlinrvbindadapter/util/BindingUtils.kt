package cn.wukang.kotlinrvbindadapter.util

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.annotation.ColorInt
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import cn.wukang.kotlinrvbindadapter.model.Conversion
import cn.wukang.library.adapter.sticky.StickyHeaderBindAdapter
import cn.wukang.library.lib.side.SideAndStickyHeaderRecyclerView
import cn.wukang.library.lib.stickyHeader.sticky.StickyRecyclerHeadersDecoration

/**
 * DataBinding adapter
 *
 * @author wukang
 */
object BindingUtils {
    /**
     * 方便xml得到BindingConversion的辅助对象[<]
     */
    @JvmStatic
    fun <T> getConversion(data: T): Conversion<T> = Conversion(data)

    /**
     * 为展示的区号数据增加“+”号
     */
    @JvmStatic
    fun getShowCode(countryCode: Int): String = "+$countryCode"

    /**
     * 组合中英文名展示数据
     */
    @JvmStatic
    fun getShowName(nameCn: String, nameEn: String): String = "$nameCn($nameEn)"


    /* ----------------------------- BindingConversion ----------------------------- */
    /**
     * 使xml里的[Conversion]数据转换成[ColorDrawable]
     */
    @BindingConversion
    @JvmStatic
    fun convertToBgColor(countryId: Conversion<Int>): ColorDrawable = ColorDrawable(Color.parseColor(if (countryId.data % 2 == 0) "#f4237a" else "#1296db"))

    /**
     * 使xml里的[Conversion]数据转换成[ColorInt]
     */
    @BindingConversion
    @ColorInt
    @JvmStatic
    fun convertToTextColor(countryId: Conversion<Int>): Int = Color.parseColor(if (countryId.data % 2 == 0) "#f4237a" else "#1296db")


    /* ----------------------------- BindingAdapter ----------------------------- */
    /**
     * 数据绑定方式执行[ViewPager.setCurrentItem]
     */
    @BindingAdapter("currentItem")
    @JvmStatic
    fun setCurrentItem(viewPager: ViewPager, index: Int) = viewPager.setCurrentItem(index, false)

    /**
     * 数据绑定方式执行[ViewPager.setAdapter]
     */
    @BindingAdapter("adapter")
    @JvmStatic
    fun setAdapter(viewPager: ViewPager, pagerAdapter: FragmentPagerAdapter) = with(viewPager) { adapter = pagerAdapter }

    /**
     * 数据绑定方式执行[RecyclerView.setLayoutManager]
     */
    @BindingAdapter("layoutManager")
    @JvmStatic
    fun setLayoutManager(view: RecyclerView, manager: RecyclerView.LayoutManager) = with(view) { view.layoutManager = manager }

    /**
     * 数据绑定方式执行[RecyclerView.setAdapter]
     */
    @BindingAdapter("adapter")
    @JvmStatic
    fun <VH : RecyclerView.ViewHolder> setAdapter(view: RecyclerView, rvAdapter: RecyclerView.Adapter<VH>) = with(view) { adapter = rvAdapter }

    /**
     * 数据绑定方式执行[RecyclerView.addItemDecoration]
     */
    @BindingAdapter("itemDecoration")
    @JvmStatic
    fun setItemDecoration(view: RecyclerView, decoration: RecyclerView.ItemDecoration) = view.addItemDecoration(decoration)

    /**
     * 数据绑定方式执行[RecyclerView.addItemDecoration]
     * （注：参数为[<]）
     */
    @BindingAdapter("stickyDecoration")
    @JvmStatic
    fun <VH : RecyclerView.ViewHolder> setStickyDecoration(view: RecyclerView, decoration: StickyRecyclerHeadersDecoration<VH>) = view.addItemDecoration(decoration)

    /**
     * 数据绑定方式执行[SideAndStickyHeaderRecyclerView.setAdapter]
     */
    @BindingAdapter("adapter")
    @JvmStatic
    fun <T, SB : ViewDataBinding, B : ViewDataBinding> setAdapter(view: SideAndStickyHeaderRecyclerView, adapter: StickyHeaderBindAdapter<T, SB, B>) = view.setAdapter(adapter)

    /**
     * 是否跟随移动
     */
    @BindingAdapter("linkageMove")
    @JvmStatic
    fun setLinkageMove(view: SideAndStickyHeaderRecyclerView, linkageMove: Boolean) = view.linkageMove(linkageMove)

    /**
     * 数据绑定方式执行[SideAndStickyHeaderRecyclerView.addItemDecoration]
     */
    @BindingAdapter("itemDecoration")
    @JvmStatic
    fun setItemDecoration(view: SideAndStickyHeaderRecyclerView, decoration: RecyclerView.ItemDecoration) = view.addItemDecoration(decoration)
}