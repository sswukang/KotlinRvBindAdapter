package cn.wukang.kotlinrvbindadapter.module.viewmodel

import android.databinding.ObservableField
import cn.wukang.kotlinrvbindadapter.base.BaseActivityViewModel
import cn.wukang.kotlinrvbindadapter.databinding.ActivityMainBinding

/**
 * Main Activity ViewModel
 *
 * @author wukang
 */
class MainViewModel : BaseActivityViewModel<ActivityMainBinding>() {
    // 中文名
    var nameCn = ObservableField<String>()
    // 英文名
    var nameEn = ObservableField<String>()
}