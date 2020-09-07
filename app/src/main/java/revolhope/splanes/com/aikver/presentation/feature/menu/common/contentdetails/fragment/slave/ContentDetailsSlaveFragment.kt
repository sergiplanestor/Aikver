package revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.fragment.slave

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.fragment_content_details_slave.addButton
import kotlinx.android.synthetic.main.fragment_content_details_slave.commentInputEditText
import kotlinx.android.synthetic.main.fragment_content_details_slave.infoTextView
import kotlinx.android.synthetic.main.fragment_content_details_slave.networkSelector
import kotlinx.android.synthetic.main.fragment_content_details_slave.punctuationGroup
import kotlinx.android.synthetic.main.fragment_content_details_slave.punctuationView
import kotlinx.android.synthetic.main.fragment_content_details_slave.root
import kotlinx.android.synthetic.main.fragment_content_details_slave.rootLayout
import kotlinx.android.synthetic.main.fragment_content_details_slave.contentSeenSwitcher
import kotlinx.android.synthetic.main.fragment_content_details_slave.recommendToGroup
import kotlinx.android.synthetic.main.fragment_content_details_slave.recommendToUserPicked
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.AnimUtils
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.invisible
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.SnackBar
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel
import revolhope.splanes.com.aikver.presentation.common.widget.switcher.SwitcherView
import revolhope.splanes.com.aikver.presentation.feature.menu.common.contentdetails.ContentDetailsActivity

class ContentDetailsSlaveFragment : BaseFragment() {

    val viewModel: ContentDetailsSlaveViewModel by viewModel()

    companion object {

        private const val ARG_X = "ContentDetailsSlaveFragment.arg.X"
        private const val ARG_Y = "ContentDetailsSlaveFragment.arg.Y"
        private const val ARG_WIDTH = "ContentDetailsSlaveFragment.arg.WIDTH"
        private const val ARG_HEIGHT = "ContentDetailsSlaveFragment.arg.HEIGHT"

        fun newInstance(
            x: Float,
            y: Float,
            width: Int,
            height: Int
        ): ContentDetailsSlaveFragment {
            return ContentDetailsSlaveFragment()
                .apply {
                    arguments = bundleOf(
                        ARG_X to x,
                        ARG_Y to y,
                        ARG_WIDTH to width,
                        ARG_HEIGHT to height
                    )
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).also {
            playAnimationIn(it!!)
        }
    }

    override fun initViews() {
        infoTextView.justify()
        contentSeenSwitcher.setOnCheckedChangeListener { option ->
            punctuationGroup.visibility(option == SwitcherView.Option.LEFT)
            TransitionManager.beginDelayedTransition(rootLayout)
        }
        punctuationView.whiteMode()
        addButton.setOnClickListener { addSerie() }
        viewModel.fetchUser()
    }

    override fun initObservers() {
        super.initObservers()

        observe(viewModel.user) {
            it?.let { user ->
                user.selectedUserGroup?.let { group ->

                    recommendToGroup.visibility(show = group.members.size != 1)

                    recommendToUserPicked.fragmentManager = childFragmentManager
                    recommendToUserPicked.userId = user.id
                    recommendToUserPicked.group = group

                } ?: recommendToUserPicked.invisible()
            } ?: recommendToUserPicked.invisible()
        }
        observe(viewModel.addContentResult) {
            SnackBar.show(
                root,
                if (it) SnackBarModel.Success(
                    message = getString(R.string.new_content_added),
                    onClick = { (activity as? BaseActivity)?.finish() },
                    onDismiss = { (activity as? BaseActivity)?.finish() }
                )
                else SnackBarModel.Error(getString(R.string.error_short))
            )
        }
    }

    private fun playAnimationIn(view: View) {
        AnimUtils.playCircularRevealIn(
            view = view,
            model = AnimUtils.AnimCircularRevealModel(
                centerX = arguments?.getFloat(ARG_X)?.toInt() ?: 0,
                centerY = arguments?.getFloat(ARG_Y)?.toInt() ?: 0,
                width = arguments?.getInt(ARG_WIDTH) ?: 0,
                height = arguments?.getInt(ARG_HEIGHT) ?: 0
            ),
            colors = resources.getColor(
                android.R.color.white,
                null
            ) to resources.getColor(R.color.colorPrimaryDark, null)
        )
    }

    fun playAnimationOut(onFinish: () -> Unit) {
        AnimUtils.playCircularRevealOut(
            view = requireView(),
            model = AnimUtils.AnimCircularRevealModel(
                centerX = arguments?.getFloat(ARG_X)?.toInt() ?: 0,
                centerY = arguments?.getFloat(ARG_Y)?.toInt() ?: 0,
                width = arguments?.getInt(ARG_WIDTH) ?: 0,
                height = arguments?.getInt(ARG_HEIGHT) ?: 0
            ),
            colors = resources.getColor(
                R.color.colorPrimaryDark,
                null
            ) to resources.getColor(android.R.color.white, null),
            onFinish = onFinish
        )
    }

    private fun addSerie() {
        (activity as? ContentDetailsActivity)?.contentDetails?.let {
            viewModel.addContent(
                ContentCustomInfoUiModel(
                    content = it,
                    haveSeen = contentSeenSwitcher.getOptionSelected() == SwitcherView.Option.LEFT,
                    score = punctuationView.getScore() ?: -1,
                    network = networkSelector.getSelected(),
                    comments = commentInputEditText.text?.toString() ?: "",
                    recommendedTo = recommendToUserPicked.pickedUsers()
                )
            )
        }
    }

    override fun getErrorLiveData(): LiveData<String>? = viewModel.errorState

    override fun getLayoutResource(): Int = R.layout.fragment_content_details_slave
}