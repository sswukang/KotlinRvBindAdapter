package cn.wukang.kotlinrvbindadapter.module.view

import android.annotation.SuppressLint
import android.support.v7.view.menu.MenuAdapter
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.ListPopupWindow
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import cn.wukang.kotlinrvbindadapter.BR
import cn.wukang.kotlinrvbindadapter.R
import cn.wukang.kotlinrvbindadapter.base.BaseActivity
import cn.wukang.kotlinrvbindadapter.base.BaseFragmentAdapter
import cn.wukang.kotlinrvbindadapter.databinding.ActivityMainBinding
import cn.wukang.kotlinrvbindadapter.module.viewmodel.MainViewModel

/**
 * 主界面
 *
 * @author sswukang
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    // 左pop
    private lateinit var leftMenuPop: ListPopupWindow
    // 右pop
    private lateinit var rightMenuPop: ListPopupWindow
    // FragmentAdapter
    private lateinit var fragmentAdapter: BaseFragmentAdapter<SortBaseFragment<*, *>>

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getVariableId(): Int = BR.main

    override fun initView() {
        // 初始化ActionBar
        setSupportActionBar(getDataBinding().topToolbar)
        // 初始化PopupWindow
        initLeftMenuPop()
        initRightMenuPop()
        // 初始化ViewPager
        fragmentAdapter = BaseFragmentAdapter(supportFragmentManager, listOf(SingleFragment(),
                MultiFragment(), StickyFragment(), SideStickyFragment()))
        getDataBinding().adapter = fragmentAdapter
    }

    @SuppressLint("RestrictedApi")
    private fun initLeftMenuPop() {
        val menuBuilder: MenuBuilder = MenuBuilder(getContext()).apply {
            setOptionalIconsVisible(true)
            add(R.string.main_single).setIcon(R.drawable.ic_main_single)
            add(R.string.main_multi).setIcon(R.drawable.ic_main_multi)
            add(R.string.main_sticky).setIcon(R.drawable.ic_main_sticky)
            add(R.string.main_sticky_side).setIcon(R.drawable.ic_main_sticky_side)
        }
        leftMenuPop = ListPopupWindow(getContext()).apply {
            width = resources.displayMetrics.widthPixels / 2
            height = ListPopupWindow.WRAP_CONTENT
            anchorView = getDataBinding().topToolbar
            isModal = true//设置是否是模式
            setAdapter(MenuAdapter(menuBuilder, layoutInflater, true))
            setDropDownGravity(Gravity.START)
            setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
                getDataBinding().currentItem = position
                dismiss()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun initRightMenuPop() {
        val menuBuilder: MenuBuilder = MenuBuilder(getContext()).apply {
            setOptionalIconsVisible(true)
            add(R.string.main_asc).setIcon(R.drawable.ic_main_asc)
            add(R.string.main_desc).setIcon(R.drawable.ic_main_desc)
            add(R.string.main_shuffle).setIcon(R.drawable.ic_main_shuffle)
        }
        rightMenuPop = ListPopupWindow(getContext()).apply {
            width = resources.displayMetrics.widthPixels / 2
            height = ListPopupWindow.WRAP_CONTENT
            anchorView = getDataBinding().topToolbar
            isModal = true//设置是否是模式
            setAdapter(MenuAdapter(menuBuilder, layoutInflater, true))
            setDropDownGravity(Gravity.END)
            setOnItemClickListener { _: AdapterView<*>, _: View, position: Int, _: Long ->
                when (position) {
                // 调用排序方法
                    0 -> fragmentAdapter.getItem(getDataBinding().mainViewPager.currentItem).asc()
                    1 -> fragmentAdapter.getItem(getDataBinding().mainViewPager.currentItem).desc()
                    2 -> fragmentAdapter.getItem(getDataBinding().mainViewPager.currentItem).shuffle()
                }
                dismiss()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            leftMenuPop.show()
            true
        }
        R.id.main_pop -> {
            rightMenuPop.show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
