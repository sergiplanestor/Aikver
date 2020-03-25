package bemobile.splanes.com.aikver.presentation.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.lifecycle.Observer
import bemobile.splanes.com.aikver.R
import bemobile.splanes.com.aikver.presentation.common.base.BaseActivity
import bemobile.splanes.com.aikver.presentation.dashboard.DashboardActivity
import bemobile.splanes.com.aikver.presentation.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun loadData() {
        super.loadData()
        if (!lottieAnimationView.isAnimating) {
            startAnimation()
        }
        getViewModel().getUser().observe(this, Observer { user ->
            if (user == null) {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            } else {
                descriptionTextView.text = getString(R.string.splash_text_connecting)
                getViewModel().doLogin(user).observe(this, Observer { success ->
                    if (success) {

                        navigateUpFinishing(DashboardActivity::class.java)

                    } else {
                        runOnUiThread {
                            lottieAnimationView.setAnimation(R.raw.anim_error_connection)
                            lottieAnimationView.playAnimation()
                            descriptionTextView.text = getString(R.string.splash_text_error)
                        }
                    }
                })
            }
        })
    }

    private fun startAnimation() {

        val propertyName = "alpha"
        val values = arrayOf(0f, 1f)

        val anim1 =
            ObjectAnimator.ofFloat(titleTextView, propertyName, values[0], values[1]).apply {
                duration = 1000
            }
        val anim2 =
            ObjectAnimator.ofFloat(lottieAnimationView, propertyName, values[0], values[1]).apply {
                duration = 4000
            }

        AnimatorSet().apply {
            /*duration = 4000*/
            playTogether(anim1, anim2)
            start()
        }

        lottieAnimationView.playAnimation()
    }

// =================================================================================================
// BaseActivity abstract impl
// =================================================================================================

    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java
}