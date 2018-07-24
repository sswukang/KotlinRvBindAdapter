package cn.wukang.kotlinrvbindadapter.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Fragment 基类
 *
 * @author wukang
 */
abstract class BaseFragment<B : ViewDataBinding, M : BaseFragmentViewModel<B>> : Fragment() {
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 得到DataBinding
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        // 得到ViewModel
        val genType: Type = javaClass.genericSuperclass
        val params: Array<Type> = (genType as ParameterizedType).actualTypeArguments
        val bizClass: Class<M> = params[1] as Class<M>
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
        return mDataBinding.root
    }

    override fun onDestroyView() {
        mViewModel.onDestroy()
        super.onDestroyView()
    }

    fun getDataBinding(): B = mDataBinding

    fun getViewModel(): M = mViewModel
}