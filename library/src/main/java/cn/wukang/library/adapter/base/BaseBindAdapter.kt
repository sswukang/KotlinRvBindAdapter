package cn.wukang.library.adapter.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.wukang.library.listener.RecyclerClickListener

/**
 * RecyclerView基础Adapter。(DataBinding模式)
 *
 * @param T 数据类型
 * @param B 内容布局绑定类
 * @param H ViewHold类
 * @param layoutId adapter需要的布局资源id
 * @param dataList 数据列表
 * @author wukang
 */
abstract class BaseBindAdapter<T, B : ViewDataBinding, H : BaseBindViewHolder<B>>(
        @LayoutRes private var layoutId: Int, private var dataList: List<T>
) : RecyclerView.Adapter<H>(), RecyclerClickListener {
    private lateinit var context: Context

    init {
        this.setHasStableIds(true)
    }

    fun getDataList(): List<T> = dataList

    fun setDataList(dataList: List<T>) {
        this.dataList = dataList
    }

    /**
     * @return 获得item数据总个数
     */
    protected fun getDataSize(): Int = getDataList().size

    /**
     * @param position item下标
     * @return 获得item数据封装
     */
    protected fun getDataItem(position: Int): T? {
        var index: Int = position
        val data: List<T> = getDataList()
        if (data.isNotEmpty()) {
            if (index >= data.size) {
                index %= data.size
            }
            return data[index]
        }
        return null
    }

    // 设置ID，保证item操作不错乱
    override fun getItemId(position: Int): Long = getDataItem(position)?.hashCode()?.toLong()
            ?: super.getItemId(position)

    /**
     * @return 设置item总个数（一般为数据总个数，设置成[Integer.MAX_VALUE]可实现无限轮播）
     */
    override fun getItemCount(): Int = getDataSize()

    /**
     * 利用getItemViewType传递layout id
     *
     * @param position 当前行数
     * @return layout id
     */
    @LayoutRes
    override fun getItemViewType(position: Int): Int = layoutId

    // 创建hold
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int): H = BaseBindViewHolder.get(DataBindingUtil
            .inflate<B>(LayoutInflater.from(parent.context), viewType, parent, false), viewType, this) as H

    // 绑定hold
    override fun onBindViewHolder(holder: H, position: Int) {
        context = holder.getContext()
        val binding: B = holder.getBinding()
        convert(position, getDataItem(position), binding, holder)
        binding.executePendingBindings()
    }

    /**
     * 实现该抽象方法，完成数据的绑定。
     *
     * @param position 当前item的position（无限轮播时会超过数据总个数）
     * @param data     position 对应的对象（无限轮播时为对数据总个数取余后对应的对象）
     * @param binding  [B]
     * @param holder   [H]
     */
    abstract fun convert(position: Int, data: T?, binding: B, holder: H)

    /**
     * 单击事件
     *
     * @param itemView 点击的item [BaseBindViewHolder.itemView]
     * @param position 当前点击的position，采用[BaseBindViewHolder.getLayoutPosition]（无限轮播时会超过数据总个数）
     * @param layoutId item布局id[BaseBindViewHolder.getLayoutId]
     */
    override fun onItemClick(itemView: View, position: Int, @LayoutRes layoutId: Int) {}

    /**
     * 长按事件
     *
     * @param itemView 点击的item [BaseBindViewHolder.itemView]
     * @param position 当前点击的position，采用[BaseBindViewHolder.getLayoutPosition]（无限轮播时会超过数据总个数）
     * @param layoutId item布局id[BaseBindViewHolder.getLayoutId]
     * @return 是否消费事件
     */
    override fun onItemLongClick(itemView: View, position: Int, @LayoutRes layoutId: Int): Boolean = false

    /**
     * 得到ItemView依赖的Context
     *
     * @return [View.getContext]
     */
    fun getContext(): Context = context
}