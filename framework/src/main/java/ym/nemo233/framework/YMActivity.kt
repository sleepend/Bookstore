package ym.nemo233.framework

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * 框架基础类
 */
abstract class YMActivity : AppCompatActivity(), YMBaseInterface {

    companion object {
        var isMiUi = false

        /*
         * 静态域，获取系统版本是否基于MIUI
         */
        init {
            @SuppressLint("PrivateApi")
            val sysClass = Class.forName("android.os.SystemProperties")
            val getStringMethod = sysClass.getDeclaredMethod("get", String::class.java)
            val version = getStringMethod.invoke(sysClass, "ro.miui.ui.version.name") as String
            isMiUi = version >= "V6" && Build.VERSION.SDK_INT < 24
        }
    }

    override fun initializeBefore() = Unit

    override fun bindEvent() = Unit

    override fun firstRequest() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBefore()
        setStatusBarDarkMode()
        setContentView(getLayoutId())
        initView()
        bindEvent()
        firstRequest()
    }

    @SuppressLint("InlinedApi")
    private fun setStatusBarDarkMode() {
        when (getStatusBarLightMode()) {
            1 -> setMIUIStatusBarDarkMode()
            2 -> setMeiZuDarkMode(window, true)
            3 -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun getStatusBarLightMode(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            when {
                isMiUi -> 1
                setMeiZuDarkMode(window, true) -> 2
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    3
                }
                else -> 0
            }
        } else 0
    }


    /**
     * 设置小米黑色状态栏字体
     */
    @SuppressLint("PrivateApi")
    private fun setMIUIStatusBarDarkMode() {
        if (isMiUi) {
            try {
                val clazz: Class<Window> = window.javaClass
                val lp = Class.forName("android.view.MiuiWindowManager${'$'}LayoutParams")
                val field = lp.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                val darkModeFlag = field.getInt(lp)
                val extraFlagField =
                    clazz.getMethod("setExtraFlags", Int::class.java, Int::class.java)
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 设置魅族手机状态栏图标颜色风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun setMeiZuDarkMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= 24) return false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag =
                    WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meiZuFlag =
                    WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meiZuFlag.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meiZuFlag.getInt(lp)
                value = if (dark) value or bit else value and bit.inv()
                meiZuFlag.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return result
    }
}