package revolhope.splanes.com.aikver.presentation.feature.onboarding.register

import android.os.Build
import android.text.Layout
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.feature.onboarding.OnBoardingActivity


class RegisterFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: RegisterViewModel by viewModel()

    override fun initViews() {
        with(activity as OnBoardingActivity?) {
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
            if (it) viewModel.getUser()
            else {
                hideLoader()
                (activity as OnBoardingActivity?)?.showError()
            }
        }
        observe(viewModel.user) {
            hideLoader()
            with(activity as OnBoardingActivity?) {
                if (it != null) this?.navToDashBoard(it)
                else this?.showError()
            }
        }
    }

    override fun onClick(view: View?) {
        showLoader()
        viewModel.register(
            usernameInputEditText.text?.toString(),
            groupInputEditText.text?.toString()
        )
    }

    override fun getLayoutResource(): Int = R.layout.fragment_register
}