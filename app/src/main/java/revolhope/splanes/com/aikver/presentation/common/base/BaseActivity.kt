package revolhope.splanes.com.aikver.presentation.common.base

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.presentation.common.widget.AppLoader

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAV_LATERAL = "nav.lateral"
    }

// =================================================================================================
// Attributes
// =================================================================================================

    private var appLoader: AppLoader? = null
    private var isFirstOnResume = true

// =================================================================================================
// Lifecycle
// =================================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        appLoader = findViewById(R.id.appLoader)
        initializeViews()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstOnResume) {
            loadData()
            isFirstOnResume = false
        } else {
            reloadData()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overrideTransition()
    }

    fun finishPush() {
        finish()
        overrideTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.close, android.R.id.home -> {
                finish()
                overrideTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

// =================================================================================================
// Views
// =================================================================================================

    open fun initializeViews() {
        // Nothing to do here.
    }

    open fun loadData() {
        // Nothing to do here.
    }

    open fun reloadData() {
        // Nothing to do here.
    }

    fun showLoader() {
        appLoader?.play()
    }

    fun hideLoader() {
        runOnUiThread {
            appLoader?.stop()
        }
    }

// =================================================================================================
// Navigation
// =================================================================================================

    enum class NavigationTransition {
        BOTTOM_TO_TOP,
        RIGHT_TO_LEFT
    }

    fun <T> navigate(clazz: Class<T>, bundle: Bundle? = null) {
        navigate(null, clazz, bundle = bundle)
    }

    fun <T> navigateUp(clazz: Class<T>, bundle: Bundle? = null) {
        navigate(NavigationTransition.BOTTOM_TO_TOP, clazz, bundle = bundle)
    }

    fun <T> navigateUpFinishing(clazz: Class<T>) {
        navigateUp(clazz)
        finish()
    }

    fun <T> navigateLateral(clazz: Class<T>, isForResult: Boolean = false) {
        navigate(NavigationTransition.RIGHT_TO_LEFT, clazz, isForResult)
    }

    fun <T> navigateLateralFinishing(clazz: Class<T>) {
        navigateLateral(clazz)
        finish()
    }

    private fun <T> navigate(
        transition: NavigationTransition?,
        clazz: Class<T>,
        isForResult: Boolean = false,
        bundle: Bundle? = null
    ) {

        val intent = Intent(this, clazz)
        if (bundle != null) intent.putExtras(bundle)
        if (transition != null) {
            val animIn: Int
            val animOut: Int
            when(transition) {
                NavigationTransition.BOTTOM_TO_TOP -> {
                    animIn = R.anim.slide_in_up
                    animOut = R.anim.slide_out_up
                }
                NavigationTransition.RIGHT_TO_LEFT -> {
                    animIn = R.anim.enter
                    animOut = R.anim.exit
                    intent.putExtra(
                        EXTRA_NAV_LATERAL, true
                    )
                }
            }
            if (isForResult) startActivityForResult(intent, 0x234)
            else startActivity(intent)
            overridePendingTransition(animIn, animOut)
        }
    }

    private fun overrideTransition() {
        if (intent.getBooleanExtra(EXTRA_NAV_LATERAL, false)) {
            overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left)
        }
    }

// =================================================================================================
// Abstract methods
// =================================================================================================

    abstract fun getLayoutRes() : Int
}