package bemobile.splanes.com.aikver.presentation.register

import android.view.View
import androidx.lifecycle.Observer
import bemobile.splanes.com.aikver.R
import bemobile.splanes.com.aikver.presentation.common.base.BaseActivity
import bemobile.splanes.com.aikver.presentation.common.widget.popup.Popup
import bemobile.splanes.com.aikver.presentation.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterViewModel>(), View.OnClickListener {


    override fun initializeViews() {
        super.initializeViews()
        submitButton.setOnClickListener(this)
    }


// =================================================================================================
// View.OnClickListener impl
// =================================================================================================

    override fun onClick(v: View?) {
        showLoader()
        getViewModel().register(inputEditText.text?.toString()).observe(this, Observer {
            hideLoader()
            if (it) {
                navigateUp(DashboardActivity::class.java)
            } else {
                Popup.showError(this, supportFragmentManager, View.OnClickListener {  })
            }
        })

    }

// =================================================================================================
// BaseActivity abstract impl
// =================================================================================================

    override fun getLayoutRes(): Int = R.layout.activity_register

    override fun getViewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java
}