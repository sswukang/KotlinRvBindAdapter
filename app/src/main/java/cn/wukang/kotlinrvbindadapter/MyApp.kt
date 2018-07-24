package cn.wukang.kotlinrvbindadapter

import android.app.Application
import cn.wukang.kotlinrvbindadapter.manager.CountryManager

/**
 * My Application
 *
 * @author wukang
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        CountryManager.init(this)
    }
}