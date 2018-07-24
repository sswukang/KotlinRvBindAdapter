package cn.wukang.library.adapter.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.wukang.library.listener.DebouncingOnClickListener
import cn.wukang.library.listener.RecyclerClickListener

/**
 * 自定义 RecyclerView 的 ViewHolder (DataBinding模式)
 *
 * @author wukang
 */
open class BaseBindViewHolder<B : ViewDataBinding>(
        private var binding: B, @LayoutRes private var layoutId: Int, listener: RecyclerClickListener
) : RecyclerView.ViewHolder(binding.root) {
    init {
        //添加监听事件
        itemView.setOnClickListener(object : DebouncingOnClickListener() {
            override fun doClick(v: View) {
                listener.onItemClick(v, layoutPosition, layoutId)
            }
        })
        itemView.setOnLongClickListener { v: View -> listener.onItemLongClick(v, layoutPosition, layoutId) }
    }

    companion object {
        /**
         * 自定义ViewHolder创建方法
         *
         * @param binding  [B]
         * @param layoutId 该条目的layout id，可用于多条目的区分
         * @param listener 条目的监听
         * @return [BaseBindViewHolder]
         */
        fun <B : ViewDataBinding> get(binding: B, @LayoutRes layoutId: Int, listener: RecyclerClickListener)
                : BaseBindViewHolder<B> = BaseBindViewHolder(binding, layoutId, listener)
    }

    /**
     * @return [B]
     */
    fun getBinding(): B = binding

    /**
     * 获得context，建议布局里使用用此方法得到context。
     *
     * @return [Context]
     */
    fun getContext(): Context = itemView.context

    /**
     * 获得item布局资源id（可用于multi adapter里区别不同item）
     *
     * @return item view res id
     */
    @LayoutRes
    fun getLayoutId(): Int = layoutId
}