package revolhope.splanes.com.aikver.presentation.feature.onboarding.register

import android.view.View
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.activity_register.groupDescriptionTextView
import kotlinx.android.synthetic.main.activity_register.groupInputEditText
import kotlinx.android.synthetic.main.activity_register.submitButton
import kotlinx.android.synthetic.main.activity_register.usernameDescriptionTextView
import kotlinx.android.synthetic.main.activity_register.usernameInputEditText
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.feature.onboarding.OnBoardingActivity


class RegisterFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: RegisterViewModel by viewModel()

    override fun initViews() {
        with(activity as OnBoardingActivity?) {
            this?.changeTitle(getString(R.string.register))
            this?.setBackVisibility(show = true)
        }
        usernameDescriptionTextView.justify()
        groupDescriptionTextView.justify()
        submitButton.setOnClickListener(this)
    }

    override fun initObservers() {
        super.initObservers()

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
                if (it != null) this?.navToDashBoard()
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

    override fun getErrorLiveData(): LiveData<String>? = viewModel.errorState

    override fun getLayoutResource(): Int = R.layout.fragment_register
}