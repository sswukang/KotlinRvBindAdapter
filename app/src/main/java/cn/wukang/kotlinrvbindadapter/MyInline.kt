package cn.wukang.kotlinrvbindadapter


/**
 * Kotlin Inline
 *
 * @author wukang
 */
inline fun <T> T.use(block: (T) -> Unit) {
    block(this)
}