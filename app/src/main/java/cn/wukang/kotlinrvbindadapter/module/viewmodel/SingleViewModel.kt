package cn.wukang.kotlinrvbindadapter.module.viewmodel

import cn.wukang.kotlinrvbindadapter.databinding.FragmentSingleBinding
import cn.wukang.kotlinrvbindadapter.manager.CountryManager
import cn.wukang.kotlinrvbindadapter.model.Country

/**
 * Single Fragment ViewModel
 *
 * @author wukang
 */
class SingleViewModel : SortBaseFragmentViewModel<FragmentSingleBinding>() {
    override fun asc(): List<Country> = CountryManager.getCountryList().sortedBy { it.countryCode }

    override fun desc(): List<Country> = CountryManager.getCountryList().sortedByDescending { it.countryCode }

    override fun shuffle(): List<Country> = CountryManager.getCountryList().shuffled()
}