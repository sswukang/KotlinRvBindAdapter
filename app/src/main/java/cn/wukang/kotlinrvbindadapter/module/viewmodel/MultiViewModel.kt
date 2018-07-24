package cn.wukang.kotlinrvbindadapter.module.viewmodel

import cn.wukang.kotlinrvbindadapter.databinding.FragmentMultiBinding
import cn.wukang.kotlinrvbindadapter.manager.CountryManager
import cn.wukang.kotlinrvbindadapter.model.Country
import java.util.*

/**
 * Multi Fragment ViewModel
 *
 * @author wukang
 */
class MultiViewModel : SortBaseFragmentViewModel<FragmentMultiBinding>() {
    override fun asc(): List<Country> = CountryManager.getCountryList().sortedBy { it.countryId }
            .repeat(Random().nextInt(5), Country().apply {
                countryNameEn = "Recycler View Multi Adapter Item."
            })

    override fun desc(): List<Country> = CountryManager.getCountryList().sortedByDescending { it.countryId }
            .repeat(Random().nextInt(5), Country().apply {
                countryNameEn = "Recycler View Multi Adapter Item."
            })

    override fun shuffle(): List<Country> = CountryManager.getCountryList().shuffled()
            .repeat(Random().nextInt(5), Country().apply {
                countryNameEn = "Recycler View Multi Adapter Item."
            })

    private fun List<Country>.repeat(times: Int, element: Country): List<Country> {
        var data: List<Country> = this
        for (i: Int in 0..times) {
            data = listOf(element) + data
        }
        return data
    }
}