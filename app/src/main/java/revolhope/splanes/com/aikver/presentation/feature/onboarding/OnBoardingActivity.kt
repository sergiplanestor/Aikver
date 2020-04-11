package revolhope.splanes.com.aikver.presentation.feature.onboarding

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_onboarding.back
import kotlinx.android.synthetic.main.activity_onboarding.rootLayout
import kotlinx.android.synthetic.main.activity_onboarding.titleTextView
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.popup.Popup
import revolhope.splanes.com.aikver.presentation.feature.menu.MenuActivity

class OnBoardingActivity : BaseActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun initViews() {
        super.initViews()
        rootLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        back.setOnClickListener { onBackClick() }
    }

    fun changeTitle(title: String) { titleTextView.text = title }

    fun setBackVisibility(show: Boolean) = back.visibility(show)

    fun navigate(action: Int, args: Bundle? = null) =
        navController.navigate(action, args, NavOptions.Builder().run {
            setEnterAnim(R.anim.enter)
            setExitAnim(R.anim.exit)
            setPopEnterAnim(R.anim.slide_left_to_right)
            setPopExitAnim(R.anim.slide_right_to_left)
            build()
        })

    fun navToDashBoard() {
        navigateUpFinishing(MenuActivity::class.java)
    }

    fun showError() {
        Popup.showError(this, supportFragmentManager, View.OnClickListener {  })
    }

    private fun onBackClick() { onBackPressed() }

    override fun getLayoutRes(): Int = R.layout.activity_onboarding
}