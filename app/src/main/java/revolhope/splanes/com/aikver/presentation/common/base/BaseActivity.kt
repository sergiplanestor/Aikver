package revolhope.splanes.com.aikver.presentation.common.base

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.widget.AppLoader
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.SnackBar
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel

abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAVIGATION_TRANSITION = "nav.transition"
    }

    enum class NavTransition {
        LATERAL,
        UP,
        MODAL,
        FADE
    }

// =================================================================================================
// Attributes
// =================================================================================================

    private var appLoader: AppLoader? = null
    private var isFirstOnResume = true
    private var onActivityResultMap: MutableMap<Int, (Intent?, Int, Int) -> Unit> = mutableMapOf()

// =================================================================================================
// Lifecycle
// =================================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        appLoader = findViewById(R.id.appLoader)
        initViews()
        initObservers()
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                overrideTransition()
                return true
            }
        }
        return item?.let { super.onOptionsItemSelected(item) } ?: false
    }

// =================================================================================================
// Views
// =================================================================================================

    open fun initViews() {
        /* Nothing to do here */
    }

    open fun initObservers() {
        getErrorLiveData()?.let {
            observe(it) { message ->
                SnackBar.show(
                    view = null,
                    model = SnackBarModel.Error(message)
                )
            }
        }
    }

    open fun loadData() {
        /* Nothing to do here */
    }

    open fun reloadData() {
        /* Nothing to do here */
    }

    open fun getErrorLiveData() : LiveData<String>? {
        return null
    }

    fun showLoader() = appLoader?.play()

    fun hideLoader() = runOnUiThread { appLoader?.stop() }

// =================================================================================================
// Navigation
// =================================================================================================

    override fun startActivity(intent: Intent?) {
        val anim = getNavAnimations(intent)
        super.startActivity(intent)
        overridePendingTransition(anim.first, anim.second)
    }

    fun startActivityForResult(
        intent: Intent?,
        requestCode: Int,
        onActivityResult: (data: Intent?, requestCode: Int, resultCode: Int) -> Unit
    ) {
        this.onActivityResultMap[requestCode] = onActivityResult
        val anim = getNavAnimations(intent)
        super.startActivityForResult(intent, requestCode)
        overridePendingTransition(anim.first, anim.second)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.onActivityResultMap[requestCode]?.invoke(data, requestCode, resultCode)
        this.onActivityResultMap.remove(requestCode)
    }

    private fun getNavAnimations(intent: Intent?, isStart: Boolean = true): Pair<Int, Int> {
        val bundle = intent?.extras
        return when (bundle?.getSerializable(EXTRA_NAVIGATION_TRANSITION) as NavTransition?) {
            NavTransition.LATERAL ->
                if (isStart) {
                    bundle?.putSerializable(EXTRA_NAVIGATION_TRANSITION, NavTransition.LATERAL)
                    Pair(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {
                    Pair(R.anim.slide_in_left, R.anim.slide_out_right)
                }
            NavTransition.UP -> Pair(R.anim.slide_in_up, R.anim.slide_out_up)
            NavTransition.MODAL ->
                if (isStart) {
                    bundle?.putSerializable(EXTRA_NAVIGATION_TRANSITION, NavTransition.MODAL)
                    Pair(R.anim.slide_in_up, android.R.anim.fade_out)
                } else {
                    Pair(android.R.anim.fade_in, R.anim.slide_out_down)
                }
            NavTransition.FADE ->
                if (isStart) {
                    bundle?.putSerializable(EXTRA_NAVIGATION_TRANSITION, NavTransition.FADE)
                    Pair(android.R.anim.fade_in, 0)
                } else {
                    Pair(0, android.R.anim.fade_out)
                }
            else -> Pair(0, 0)
        }
    }

    private fun overrideTransition() {
        val anim = getNavAnimations(intent, isStart = false)
        overridePendingTransition(anim.first, anim.second)
    }

// =================================================================================================
// Abstract methods
// =================================================================================================

    abstract fun getLayoutRes(): Int
}