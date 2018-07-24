package cn.wukang.kotlinrvbindadapter.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Activity 基类
 *
 * @author wukang
 */
abstract class BaseActivity<B : ViewDataBinding, M : BaseActivityViewModel<B>> : AppCompatActivity() {
    // 视图绑定对象
    private lateinit var mDataBinding: B
    // ViewModel模型对象
    private lateinit var mViewModel: M

    /**
     * @return 设置视图id
     */
    abstract fun getLayoutId(): Int

    /**
     * @return 设置绑定id
     */
    abstract fun getVariableId(): Int

    /**
     * 初始化视图
     */
    abstract fun initView()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 得到DataBinding
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        // 得到ViewModel
        val genType: Type = javaClass.genericSuperclass
        val params: Array<Type> = (genType as ParameterizedType).actualTypeArguments
        val bizClass: Class<M> = params[1] as Class<M> // 泛型位置
        try {
            mViewModel = bizClass.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // 绑定视图与ViewModel
        mDataBinding.setVariable(getVariableId(), mViewModel)
        // 初始化ViewModel
        mViewModel.initViewModel(this)
        // 初始化视图
        initView()
        mViewModel.onCreate()
    }

    override fun onDestroy() {
        mViewModel.onDestroy()
        super.onDestroy()
    }

    fun getDataBinding(): B = mDataBinding

    fun getViewModel(): M = mViewModel

    fun getContext(): Context = this

    fun getActivity(): BaseActivity<B, M> = this
}