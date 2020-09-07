package revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_content_details.addButton
import kotlinx.android.synthetic.main.activity_content_details.rootLayout
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.exec
import revolhope.splanes.com.aikver.framework.app.findByTag
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.fragment.slave.ContentDetailsSlaveFragment
import revolhope.splanes.com.core.domain.model.content.Content
import revolhope.splanes.com.core.domain.model.content.ContentDetails

class ContentDetailsActivity : BaseActivity() {

    private var addButtonState: Int = STATE_ADD
    var contentDetails: ContentDetails? = null

    companion object {
        private const val EXTRA_CONTENT = "ContentDetailsActivity.extra.content"
        private const val STATE_ADD = 0
        private const val STATE_CLOSE = 1

        fun start(baseActivity: BaseActivity?, content: Content) {
            baseActivity?.startActivity(
                Intent(baseActivity, ContentDetailsActivity::class.java).apply {
                    putExtras(
                        bundleOf(
                            EXTRA_NAVIGATION_TRANSITION to NavTransition.MODAL,
                            EXTRA_CONTENT to content
                        )
                    )
                }
            )
        }
    }

    override fun initViews() {

        addButton.setOnClickListener {

            if (addButtonState == STATE_ADD) {
                supportFragmentManager.exec { transaction ->
                    transaction
                        .add(
                            R.id.container,
                            ContentDetailsSlaveFragment.newInstance(
                                x = addButton.x + addButton.width / 2,
                                y = addButton.y + addButton.height / 2,
                                width = rootLayout.width,
                                height = rootLayout.height
                            ),
                            ContentDetailsSlaveFragment::javaClass.name
                        )
                        .addToBackStack(ContentDetailsSlaveFragment::javaClass.name)
                }
                changeFabState(STATE_CLOSE)

            } else {
                (supportFragmentManager.findByTag(ContentDetailsSlaveFragment::javaClass.name)
                        as? ContentDetailsSlaveFragment)?.run {
                    playAnimationOut {
                        supportFragmentManager.popBackStackImmediate(
                            ContentDetailsSlaveFragment::javaClass.name,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    }
                }
                changeFabState(STATE_ADD)
            }
        }
    }

    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getContent()?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    fun getContent(): Content? =
        intent.extras?.getSerializable(EXTRA_CONTENT) as Content?

    override fun onBackPressed() {
        if (addButtonState == STATE_CLOSE) addButton.callOnClick() else super.onBackPressed()
    }

    private fun changeFabState(newState: Int) {
        addButton.backgroundTintList = ColorStateList.valueOf(
            getColor(
                if (newState != STATE_ADD) android.R.color.white else R.color.colorAccent
            )
        )
        addButton.setImageResource(
            if (newState != STATE_ADD) R.drawable.ic_close else R.drawable.ic_add
        )
        addButtonState = newState
    }

    override fun getLayoutRes(): Int = R.layout.activity_content_details
}