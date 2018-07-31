package cn.wukang.kotlinrvbindadapter.module.view

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.wukang.kotlinrvbindadapter.BR
import cn.wukang.kotlinrvbindadapter.R
import cn.wukang.kotlinrvbindadapter.databinding.FragmentMultiBinding
import cn.wukang.kotlinrvbindadapter.databinding.RvMultiContentBinding
import cn.wukang.kotlinrvbindadapter.databinding.RvMultiTitleBinding
import cn.wukang.kotlinrvbindadapter.manager.CountryManager
import cn.wukang.kotlinrvbindadapter.model.Country
import cn.wukang.kotlinrvbindadapter.module.viewmodel.MultiViewModel
import cn.wukang.library.adapter.multi.MultiBindAdapter

/**
 * Multi Fragment
 *
 * @author wukang
 */
class MultiFragment : SortBaseFragment<FragmentMultiBinding, MultiViewModel>() {
    private lateinit var adapter: MultiBindAdapter<Country>

    override fun getLayoutId(): Int = R.layout.fragment_multi

    override fun getVariableId(): Int = BR.multi

    override fun initView() {
        // 模拟添加一些数据
        val list: List<Country> = listOf(Country().apply {
            countryNameEn = "Recycler View Multi Adapter Item."
        }) + CountryManager.getCountryList()
        // Adapter数据绑定
        adapter = object : MultiBindAdapter<Country>(list) {
            // 得到每个类型item的布局id
            override fun getItemLayoutId(position: Int, data: Country?): Int =
                    if (data?.countryId ?: 0 <= 0) R.layout.rv_multi_title else R.layout.rv_multi_content

            // adapter数据绑定
            override fun convert(position: Int, data: Country?, binding: ViewDataBinding, @LayoutRes layoutId: Int) {
                when (layoutId) {
                    R.layout.rv_multi_title -> (getItemBinding(binding) as RvMultiTitleBinding).country = data
                    R.layout.rv_multi_content -> (getItemBinding(binding) as RvMultiContentBinding).country = data
                }
            }

            override fun onItemClick(itemView: View, position: Int, data: Country?, @LayoutRes layoutId: Int) {
                when (layoutId) {
                    R.layout.rv_multi_title -> Snackbar.make(itemView, "MultiAdapter Title Item.", Snackbar.LENGTH_SHORT).show()
                    R.layout.rv_multi_content -> {
                        if (null == data) return
                        Snackbar.make(itemView, data.toString(), Snackbar.LENGTH_SHORT).show()
                        // 点击改变opToolbar内容
                        getMainActivity().getViewModel().nameCn.set(data.countryNameCn)
                        getMainActivity().getViewModel().nameEn.set(data.countryNameEn)
                    }
                }
            }
        }
        with(getDataBinding()) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MultiFragment.adapter
        }
    }

    override fun updateAdapter(dataList: List<Country>) {
        adapter.apply {
            setDataList(dataList)
            notifyDataSetChanged()
        }
    }
}