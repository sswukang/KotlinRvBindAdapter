package cn.wukang.library.adapter.itemtouch

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.View
import cn.wukang.library.adapter.base.BaseBindViewHolder
import cn.wukang.library.listener.RecyclerClickListener

/**
 * KotlinRvBindAdapter
 *
 * @author wukang
 */
open class ItemTouchBindViewHolder<B : ViewDataBinding>(
        binding: B, @LayoutRes layoutId: Int, clickListener: RecyclerClickListener,
        private var listener: ItemViewStateChangeListener
) : BaseBindViewHolder<B>(binding, layoutId, clickListener), ItemTouchCallBack.OnStateChangedListener {
    companion object {
        /**
         * 创建 ItemTouchViewHolder 的方法
         *
         * @param binding       [B]
         * @param layoutId      该条目的layout id，常用于多条目的区分
         * @param clickListener [RecyclerClickListener]
         * @param listener      [ItemViewStateChangeListener]
         * @return [ItemTouchBindViewHolder]
         */
        fun <B : ViewDataBinding> get(
                binding: B, @LayoutRes layoutId: Int, clickListener: RecyclerClickListener,
                listener: ItemViewStateChangeListener
        ): ItemTouchBindViewHolder<B> = ItemTouchBindViewHolder(binding, layoutId, clickListener, listener)
    }

    override fun onItemPressed() = listener.onItemPressed(itemView)

    override fun onItemClear() = listener.onItemClear(itemView)

    /**
     * 拖拽状态改变
     */
    interface ItemViewStateChangeListener {
        fun onItemPressed(itemView: View)

        fun onItemClear(itemView: View)
    }
}