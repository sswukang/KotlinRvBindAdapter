package cn.wukang.library.listener

import android.support.annotation.LayoutRes
import android.view.View

/**
 * RecyclerView Item 添加监听接口
 *
 * @author wukang
 */
interface RecyclerClickListener {
    /**
     * item 单击事件
     */
    fun onItemClick(itemView: View, position: Int, @LayoutRes layoutId: Int)

    /**
     * item 长按事件
     */
    fun onItemLongClick(itemView: View, position: Int, @LayoutRes layoutId: Int): Boolean
}