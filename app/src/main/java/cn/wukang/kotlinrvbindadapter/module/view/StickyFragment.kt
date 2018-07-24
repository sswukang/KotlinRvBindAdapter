package cn.wukang.kotlinrvbindadapter.module.view

import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.wukang.kotlinrvbindadapter.BR
import cn.wukang.kotlinrvbindadapter.R
import cn.wukang.kotlinrvbindadapter.databinding.FragmentStickyBinding
import cn.wukang.kotlinrvbindadapter.databinding.RvStickyContentBinding
import cn.wukang.kotlinrvbindadapter.databinding.RvStickyTitleBinding
import cn.wukang.kotlinrvbindadapter.manager.CountryManager
import cn.wukang.kotlinrvbindadapter.model.Country
import cn.wukang.kotlinrvbindadapter.module.viewmodel.StickyViewModel
import cn.wukang.library.adapter.sticky.StickyHeaderBindAdapter
import cn.wukang.library.lib.stickyHeader.sticky.StickyRecyclerHeadersDecoration

/**
 * Sticky Fragment
 *
 * @author wukang
 */
class StickyFragment : SortBaseFragment<FragmentStickyBinding, StickyViewModel>() {
    private lateinit var adapter: StickyHeaderBindAdapter<Country, RvStickyTitleBinding, RvStickyContentBinding>

    override fun getLayoutId(): Int = R.layout.fragment_sticky

    override fun getVariableId(): Int = BR.sticky

    override fun initView() {
        // Adapter数据绑定
        adapter = object : StickyHeaderBindAdapter<Country, RvStickyTitleBinding, RvStickyContentBinding>(
                R.layout.rv_sticky_title, R.layout.rv_sticky_content, CountryManager.getCountryList()) {
            override val headerHeight: Int = resources.getDimensionPixelSize(R.dimen.main_sticky_header_height)

            override fun getHeaderId(position: Int, data: Country?): Long = data?.countryNameEn?.get(0)?.toLong()
                    ?: getItemId(position)

            override fun convertHeader(position: Int, data: Country?, binding: RvStickyTitleBinding) {
                // 粘性头部数据绑定
                binding.country = data
            }

            override fun convert(position: Int, data: Country?, binding: RvStickyContentBinding) {
                // 内容数据绑定
                binding.country = data
            }

            override fun onItemClick(itemView: View, position: Int, data: Country?) {
                if (null == data) return
                Snackbar.make(itemView, data.toString(), Snackbar.LENGTH_SHORT).show()
                // 点击改变opToolbar内容
                getMainActivity().getViewModel().nameCn.set(data.countryNameCn)
                getMainActivity().getViewModel().nameEn.set(data.countryNameEn)
            }
        }

        with(getDataBinding()) {
            getDataBinding().layoutManager = LinearLayoutManager(context)
            getDataBinding().itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            getDataBinding().stickyDecoration = StickyRecyclerHeadersDecoration(this@StickyFragment.adapter) // 必须添加
            getDataBinding().adapter = this@StickyFragment.adapter
        }

        // 初始化排序一次
        asc()
    }

    override fun updateAdapter(dataList: List<Country>) {
        adapter.apply {
            setDataList(dataList)
            notifyDataSetChanged()
        }
    }
}