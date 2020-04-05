package revolhope.splanes.com.aikver.presentation.init.register

import android.os.Build
import android.text.Layout
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.init.InitialActivity


class RegisterFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: RegisterViewModel by viewModel()

    override fun initViews() {
        with(activity as InitialActivity?) {
            this?.changeTitle(getString(R.string.register))
            this?.setBackVisibility(show = true)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            usernameDescriptionTextView.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
            groupDescriptionTextView.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        }
        submitButton.setOnClickListener(this)
    }

    override fun initObservers() {
        observe(viewModel.registerResult) {
            hideLoader()
            if (it) {
                // navigateUp(DashboardActivity::class.java)
            } else {
                // Popup.showError(context, supportFragmentManager, View.OnClickListener {  })
            }
        }
    }

    override fun onClick(view: View?) {
        showLoader()
        viewModel.register(usernameInputEditText.text?.toString())
    }

    override fun getLayoutResource(): Int = R.layout.fragment_register
}