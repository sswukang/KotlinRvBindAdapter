package cn.wukang.library.adapter.multi

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.View
import cn.wukang.library.adapter.base.BaseBindAdapter
import cn.wukang.library.adapter.base.BaseBindViewHolder

/**
 * multi Item Adapter。(DataBinding模式)
 *
 * @param T 数据类型
 * @param dataList 数据列表
 * @author wukang
 */
abstract class MultiBindAdapter<T>(dataList: List<T>)
    : BaseBindAdapter<T, ViewDataBinding, BaseBindViewHolder<ViewDataBinding>>(-1, dataList) {
    /**
     * 利用getItemViewType传递layout id
     *
     * @param position 当前item的position（无限轮播时会超过数据总个数）
     * @return layout id
     */
    @LayoutRes
    override fun getItemViewType(position: Int): Int = getItemLayoutId(position, getDataItem(position))

    override fun convert(position: Int, data: T?, binding: ViewDataBinding, holder: BaseBindViewHolder<ViewDataBinding>) =
            convert(position, data, binding, holder.getLayoutId())

    override fun onItemClick(itemView: View, position: Int, @LayoutRes layoutId: Int) =
            onItemClick(itemView, position, getDataItem(position), layoutId)

    override fun onItemLongClick(itemView: View, position: Int, @LayoutRes layoutId: Int): Boolean =
            onItemLongClick(itemView, position, getDataItem(position), layoutId)

    /**
     * 实现该抽象方法，得到单个item的layout id。
     *
     * @param position 当前item的position（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @return layout id
     */
    abstract fun getItemLayoutId(position: Int, data: T?): Int

    /**
     * 实现该抽象方法，完成数据的填充。
     *
     * @param position 当前item的position（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @param binding  [ViewDataBinding]
     * @param layoutId 布局id (用于区别不同item)
     */
    abstract fun convert(position: Int, data: T?, binding: ViewDataBinding, @LayoutRes layoutId: Int)

    /**
     * item的单击事件
     *
     * @param itemView 触发点击事件的View
     * @param position 当前点击的position，采用[BaseBindViewHolder.getLayoutPosition]（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @param layoutId 布局id (用于区别不同item)
     */
    open fun onItemClick(itemView: View, position: Int, data: T?, @LayoutRes layoutId: Int) {}

    /**
     * item的长按事件
     *
     * @param itemView 触发点击事件的View
     * @param position 当前点击的position，采用[BaseBindViewHolder.getLayoutPosition]（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @param layoutId 布局id (用于区别不同item)
     * @return 长按事件是否被消费
     */
    open fun onItemLongClick(itemView: View, position: Int, data: T?, @LayoutRes layoutId: Int): Boolean = false

    /**
     * 得到每个item的ViewDataBinding(注：一定要是item xml对应的ViewDataBinding)
     *
     * @param binding [ViewDataBinding]
     * @param B       [B]
     * @return [B]
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <B : ViewDataBinding> getItemBinding(binding: ViewDataBinding): B = binding as B
}