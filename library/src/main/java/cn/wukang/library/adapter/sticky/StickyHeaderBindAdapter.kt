package cn.wukang.library.adapter.sticky

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import cn.wukang.library.adapter.base.BaseBindViewHolder
import cn.wukang.library.adapter.single.SingleBindAdapter
import cn.wukang.library.lib.stickyHeader.sticky.StickyRecyclerHeadersAdapter

/**
 * 粘性头部适配器(DataBinding模式)
 *
 * @param T  数据类型
 * @param SB 粘性头部布局绑定类
 * @param B  内容布局绑定类
 * @param headerLayoutId header需要的布局资源id
 * @param layoutId ontent需要的布局资源id
 * @param dataList 数据列表
 * @author wukang
 */
abstract class StickyHeaderBindAdapter<T, SB : ViewDataBinding, B : ViewDataBinding>(
        @LayoutRes private var headerLayoutId: Int, @LayoutRes layoutId: Int, dataList: List<T>
) : SingleBindAdapter<T, B>(layoutId, dataList), StickyRecyclerHeadersAdapter<BaseBindViewHolder<SB>> {
    /**
     * @return 设置item总个数（不允许设置无限轮播）
     */
    final override fun getItemCount(): Int = super.getDataSize()

    final override fun getHeaderId(position: Int): Long = getHeaderId(position, getDataItem(position))

    override fun onCreateHeaderViewHolder(parent: ViewGroup): BaseBindViewHolder<SB> = BaseBindViewHolder.get(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), headerLayoutId, parent, false),
            headerLayoutId, this)

    override fun onBindHeaderViewHolder(holder: BaseBindViewHolder<SB>, position: Int) {
        val binding: SB = holder.getBinding()
        convertHeader(position, getDataItem(position), binding)
        binding.executePendingBindings()
    }

    /**
     * 设置粘性头部高度，方便sticky header定位
     */
    abstract val headerHeight: Int

    /**
     * 获得 header id 。如果某几个条目有相同的header，其id 需相同。
     * 如某条目不需要header，则return < 0 即可。
     * 例：字符串可以用 String.charAt(0)
     *
     * @param position 当前item的position
     * @param data     position 对应的对象
     * @return header id [StickyRecyclerHeadersAdapter.getHeaderId]
     */
    abstract fun getHeaderId(position: Int, data: T?): Long

    /**
     * 填充粘性头部显示的内容
     *
     * @param position header 条目下标
     * @param data     header 对象数据封装
     * @param binding  [SB]
     */
    abstract fun convertHeader(position: Int, data: T?, binding: SB)
}