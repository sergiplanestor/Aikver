package revolhope.splanes.com.aikver.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutResource(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        loadData()
    }

    open fun initViews() {

    }

    open fun initObservers() {

    }

    open fun loadData() {

    }

    fun showLoader() = (activity as BaseActivity?)?.showLoader()

    fun hideLoader() = with(activity as BaseActivity?) { this?.runOnUiThread { hideLoader() } }

    abstract fun getLayoutResource() : Int
}