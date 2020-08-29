package revolhope.splanes.com.aikver.presentation.feature.onboarding.login

import android.view.View
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.fragment_login.*
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.feature.onboarding.OnBoardingActivity

class LoginFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModel()

    override fun initViews() {
        with(activity as OnBoardingActivity?) {
            this?.changeTitle(getString(R.string.login))
            this?.setBackVisibility(show = true)
        }
        submitButton.setOnClickListener(this)
    }

    override fun initObservers() {
        super.initObservers()

        observe(viewModel.userLoginResult) {
            hideLoader()
            with(activity as OnBoardingActivity?) {
                if (it) this?.navToDashBoard()
                else this?.showError()
            }
        }
    }

    override fun onClick(view: View?) {
        showLoader()
        viewModel.doLogin(usernameInputEditText.text?.toString() ?: "")
    }

    override fun getErrorLiveData(): LiveData<String>? = viewModel.errorState

    override fun getLayoutResource(): Int = R.layout.fragment_login
}