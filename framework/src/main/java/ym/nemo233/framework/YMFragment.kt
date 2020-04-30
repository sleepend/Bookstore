package ym.nemo233.framework

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Fragment 基类
 */
abstract class YMFragment : Fragment(), YMBaseInterface {

    lateinit var mActivity: FragmentActivity
    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mActivity = activity!!
        mContext = activity!!
        initializeBefore()
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        bindEvent()
    }

    override fun initializeBefore() = Unit
    override fun bindEvent() = Unit
    override fun firstRequest() = Unit

    override fun onStart() {
        super.onStart()
        firstRequest()
    }

    fun hideSortKeyboard(view: View) {
        (mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).let {
            it.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}