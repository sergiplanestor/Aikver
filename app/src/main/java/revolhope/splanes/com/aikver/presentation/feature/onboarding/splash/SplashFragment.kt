package revolhope.splanes.com.aikver.presentation.feature.onboarding.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.feature.onboarding.OnBoardingActivity

class SplashFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: SplashViewModel by viewModel()

    override fun initViews() {
        if (!lottieAnimationView.isAnimating) {
            startAnimation()
        }
        descriptionTextView.justify()
        with(activity as OnBoardingActivity?) {
            this?.changeTitle(getString(R.string.app_name))
            this?.setBackVisibility(false)
        }
        buttonLogin.setOnClickListener(this)
        buttonRegister.setOnClickListener(this)
    }

    override fun initObservers() {
        super.initObservers()

        observe(viewModel.userLogin) { userLogin ->
            if (userLogin == null) {
                descriptionTextView.text = getString(R.string.register_user_not_registered)
            } else {
                descriptionTextView.text = getString(R.string.splash_text_connecting)
                viewModel.doLogin(userLogin)
            }
        }
        observe(viewModel.userLoginResult) { success ->
            if (success) {
                (activity as OnBoardingActivity?)?.navToDashBoard()
            } else {
                activity?.runOnUiThread {
                    lottieAnimationView.setAnimation(R.raw.anim_error_connection)
                    lottieAnimationView.playAnimation()
                    descriptionTextView.text = getString(R.string.splash_text_error)
                }
            }
        }
    }

    override fun loadData() = viewModel.getUserLogin()

    private fun startAnimation() {

        val propertyName = "alpha"
        val values = arrayOf(0f, 1f)

        val anim1 =
            ObjectAnimator.ofFloat(descriptionTextView, propertyName, values[0], values[1]).apply {
                duration = 1000
            }
        val anim2 =
            ObjectAnimator.ofFloat(lottieAnimationView, propertyName, values[0], values[1]).apply {
                duration = 2000
            }

        AnimatorSet().apply {
            playTogether(anim1, anim2)
            start()
        }

        lottieAnimationView.playAnimation()
    }

    override fun onClick(view: View?) {
        (activity as OnBoardingActivity?)?.navigate(
            when (view?.id) {
                R.id.buttonLogin -> R.id.action_splash_to_login
                else  /* R.id.buttonRegister */ -> R.id.action_splash_to_register
            }
        )
    }

    override fun getErrorLiveData(): LiveData<String>? = viewModel.errorState

    override fun getLayoutResource(): Int = R.layout.fragment_splash
}