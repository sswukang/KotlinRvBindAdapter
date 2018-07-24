package cn.wukang.library.adapter.single

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.View
import cn.wukang.library.adapter.base.BaseBindAdapter
import cn.wukang.library.adapter.base.BaseBindViewHolder

/**
 * single item Adapter (DataBinding模式)
 *
 * @param T 数据类型
 * @param B 内容布局绑定类
 * @param layoutId adapter需要的布局资源id
 * @param dataList 数据列表
 * @author wukang
 */
abstract class SingleBindAdapter<T, B : ViewDataBinding>(layoutId: Int, dataList: List<T>)
    : BaseBindAdapter<T, B, BaseBindViewHolder<B>>(layoutId, dataList) {
    override fun convert(position: Int, data: T?, binding: B, holder: BaseBindViewHolder<B>) =
            convert(position, data, binding)

    override fun onItemClick(itemView: View, position: Int, @LayoutRes layoutId: Int) =
            onItemClick(itemView, position, getDataItem(position))

    override fun onItemLongClick(itemView: View, position: Int, @LayoutRes layoutId: Int): Boolean =
            onItemLongClick(itemView, position, getDataItem(position))

    /**
     * 实现该抽象方法，完成数据的绑定。
     *
     * @param position 当前item的position（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @param binding  [B]
     */
    abstract fun convert(position: Int, data: T?, binding: B)

    /**
     * item的单击事件
     *
     * @param itemView 点击的item [BaseBindViewHolder.itemView]
     * @param position 当前点击的position，采用[BaseBindViewHolder.getLayoutPosition]（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     */
    open fun onItemClick(itemView: View, position: Int, data: T?) {}

    /**
     * item的长按事件
     *
     * @param itemView 点击的item [BaseBindViewHolder.itemView]
     * @param position 当前点击的position，采用[BaseBindViewHolder.getLayoutPosition]（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @return 长按事件是否被消费
     */
    open fun onItemLongClick(itemView: View, position: Int, data: T?): Boolean = false
}