package cn.wukang.library.adapter.itemtouch

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.wukang.library.adapter.base.BaseBindAdapter
import java.util.*

/**
 * ItemTouch Adapter Adapter (DataBinding模式)
 *
 * @param T 数据类型
 * @param B 内容布局绑定类
 * @param layoutId adapter需要的布局资源id
 * @param dataList 数据列表
 * @param pressedColor item按下时的颜色
 * @param clearColor item清除时的颜色
 *
 * @author wukang
 */
abstract class ItemTouchBindAdapter<T, B : ViewDataBinding>(
        @LayoutRes layoutId: Int, dataList: List<T>,
        @ColorInt private var pressedColor: Int = Color.GRAY,
        @ColorInt private var clearColor: Int = Color.TRANSPARENT
) : BaseBindAdapter<T, B, ItemTouchBindViewHolder<B>>(layoutId, dataList),
        ItemTouchCallBack.OnMoveSwipeListener, ItemTouchBindViewHolder.ItemViewStateChangeListener {
    /**
     * @return 设置item总个数（不允许设置无限轮播）
     */
    final override fun getItemCount(): Int = getDataSize()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTouchBindViewHolder<B> =
            ItemTouchBindViewHolder.get(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    viewType, parent, false), viewType, this, this)

    final override fun convert(position: Int, data: T?, binding: B, holder: ItemTouchBindViewHolder<B>) = convert(position, data, binding)

    final override fun onItemClick(itemView: View, position: Int, @LayoutRes layoutId: Int) = onItemClick(itemView, position, getDataItem(position))

    final override fun onItemLongClick(itemView: View, position: Int, @LayoutRes layoutId: Int): Boolean = onItemLongClick(itemView, position, getDataItem(position))

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        //交换数据源位置
        Collections.swap(getDataList(), fromPosition, toPosition)
        //交换列表中数据位置
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemSwipe(position: Int) {
        val data: MutableList<T> = getDataList().toMutableList()
        //删除数据源中对应数据
        data.removeAt(position)
        //删除列表中对应位置
        notifyItemRemoved(position)
    }

    /**
     * 实现该抽象方法，完成数据的绑定。
     *
     * @param position 当前item的position
     * @param data     position 对应的对象
     * @param binding  [B]
     */
    abstract fun convert(position: Int, data: T?, binding: B)

    /**
     * item的单击事件
     *
     * @param itemView 点击的item [ItemTouchBindViewHolder.itemView]
     * @param position 当前点击的position，采用[ItemTouchBindViewHolder.getLayoutPosition]
     * @param data     position 对应的对象
     */
    open fun onItemClick(itemView: View, position: Int, data: T?) {}

    /**
     * item的长按事件
     *
     * @param itemView 点击的item [ItemTouchBindViewHolder.itemView]
     * @param position 当前点击的position，采用[ItemTouchBindViewHolder.getLayoutPosition]
     * @param data     position 对应的对象
     * @return 长按事件是否被消费
     */
    open fun onItemLongClick(itemView: View, position: Int, data: T?): Boolean = false

    override fun onItemPressed(itemView: View) = itemView.setBackgroundColor(pressedColor)

    override fun onItemClear(itemView: View) = itemView.setBackgroundColor(clearColor)
}