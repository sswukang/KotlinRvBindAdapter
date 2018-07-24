package cn.wukang.kotlinrvbindadapter.module.view

import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import cn.wukang.kotlinrvbindadapter.BR
import cn.wukang.kotlinrvbindadapter.R
import cn.wukang.kotlinrvbindadapter.databinding.FragmentSingleBinding
import cn.wukang.kotlinrvbindadapter.databinding.RvSingleItemBinding
import cn.wukang.kotlinrvbindadapter.manager.CountryManager
import cn.wukang.kotlinrvbindadapter.model.Country
import cn.wukang.kotlinrvbindadapter.module.viewmodel.SingleViewModel
import cn.wukang.library.adapter.single.SingleBindAdapter

/**
 * Single Fragment
 *
 * @author wukang
 */
class SingleFragment : SortBaseFragment<FragmentSingleBinding, SingleViewModel>() {
    private lateinit var adapter: SingleBindAdapter<Country, RvSingleItemBinding>

    override fun getLayoutId(): Int = R.layout.fragment_single

    override fun getVariableId(): Int = BR.single

    override fun initView() {
        // Adapter数据绑定
        adapter = object : SingleBindAdapter<Country, RvSingleItemBinding>(R.layout.rv_single_item,
                CountryManager.getCountryList()) {
            override fun convert(position: Int, data: Country?, binding: RvSingleItemBinding) {
                // adapter数据绑定
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
            layoutManager = LinearLayoutManager(context)
            getDataBinding().adapter = this@SingleFragment.adapter
        }
    }

    override fun updateAdapter(dataList: List<Country>) {
        adapter.apply {
            setDataList(dataList)
            notifyDataSetChanged()
        }
    }
}