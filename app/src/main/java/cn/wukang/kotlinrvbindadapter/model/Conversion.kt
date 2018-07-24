package cn.wukang.kotlinrvbindadapter.model

/**
 * BindingConversion 辅助对象，避免影响其它DataBinding数据。
 *
 * @author wukang
 */
data class Conversion<T>(var data: T)