package cn.wukang.library.adapter.itemtouch

import android.graphics.Canvas
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * 拖拽侧滑CallBack
 *
 * @author wukang
 */
open class ItemTouchCallBack(private var listener: OnMoveSwipeListener) : ItemTouchHelper.Callback() {
    companion object {
        private const val ALPHA_FULL = 1.0f
    }

    // 设置拖动方向和侧滑方向
    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int = when (recyclerView) {
        null -> -1
        is GridLayoutManager -> {  // GridLayout样式
            val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // 拖动方向:上下左右
            val swipeFlags = 0 // 不支持侧滑
            ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }
        is LinearLayoutManager -> { // linearLayout样式
            val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN // 拖动方向:上下
            val swipeFlags: Int = ItemTouchHelper.START or ItemTouchHelper.END // 侧滑方向:左右
            ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }
        else -> -1
    }


    // 拖动时调用
    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean = when {
        null == viewHolder || null == target -> false // null不可以拖拽
        viewHolder.itemViewType != target.itemViewType -> false // 两个item不是一个类型, 不可以拖拽
        else -> listener.onItemMove(viewHolder.adapterPosition, target.adapterPosition) // 回调adapter中的onMove
    }


    // 侧滑时调用
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        // 回调adapter中的onSwipe
        if (null != viewHolder) listener.onItemSwipe(viewHolder.adapterPosition)
    }

    // 拖拽或侧滑某一项
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        // 不是空闲状态(正在拖拽或侧滑)
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            // 判断viewHolder是否实现了OnStateChangedListener
            if (viewHolder is OnStateChangedListener) {
                viewHolder.onItemPressed()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    // 拖拽侧滑完清除状态
    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is OnStateChangedListener) {
            viewHolder.onItemClear()
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) { // 正在侧滑, 根据位移修改item透明度
            val alpha: Float = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    //拖拽或侧滑回调
    interface OnMoveSwipeListener {
        fun onItemMove(fromPosition: Int, toPosition: Int): Boolean  // 某一项从fromPosition拖到toPosition

        fun onItemSwipe(position: Int)  // 侧滑某一项
    }

    interface OnStateChangedListener {
        fun onItemPressed()  // 长按选中某一项

        fun onItemClear()
    }
}