package revolhope.splanes.com.aikver.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.SnackBar
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel

abstract class BaseFragment : Fragment() {

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
        // Nothing to do here
    }

    open fun initObservers() {
        getErrorLiveData()?.let {
            observe(it) { message ->
                SnackBar.show(
                    view = view,
                    model = SnackBarModel.Error(message)
                )
            }
        }
    }

    open fun loadData() {
        // Nothing to do here
    }

    open fun getErrorLiveData() : LiveData<String>? {
        return null
    }

    fun showLoader() = (activity as BaseActivity?)?.showLoader()

    fun hideLoader() = with(activity as BaseActivity?) { this?.runOnUiThread { hideLoader() } }

    abstract fun getLayoutResource(): Int
}