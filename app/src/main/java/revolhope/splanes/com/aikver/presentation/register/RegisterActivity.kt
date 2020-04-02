package revolhope.splanes.com.aikver.presentation.register

import android.os.Build
import android.text.Layout
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_register.*
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.widget.popup.Popup
import revolhope.splanes.com.aikver.presentation.dashboard.DashboardActivity

class RegisterActivity : BaseActivity<RegisterViewModel>(), View.OnClickListener {


    override fun initializeViews() {
        super.initializeViews()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            usernameDescriptionTextView.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
            groupDescriptionTextView.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        }

        submitButton.setOnClickListener(this)
    }


// =================================================================================================
// View.OnClickListener impl
// =================================================================================================

    override fun onClick(v: View?) {
        showLoader()
        getViewModel().register(usernameInputEditText.text?.toString()).observe(this, Observer {
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