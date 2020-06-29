package revolhope.splanes.com.aikver.presentation.feature.menu.common.content

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_serie_details_slave.addButton
import kotlinx.android.synthetic.main.fragment_serie_details_slave.commentInputEditText
import kotlinx.android.synthetic.main.fragment_serie_details_slave.infoTextView
import kotlinx.android.synthetic.main.fragment_serie_details_slave.networkSelector
import kotlinx.android.synthetic.main.fragment_serie_details_slave.punctuationGroup
import kotlinx.android.synthetic.main.fragment_serie_details_slave.punctuationView
import kotlinx.android.synthetic.main.fragment_serie_details_slave.root
import kotlinx.android.synthetic.main.fragment_serie_details_slave.rootLayout
import kotlinx.android.synthetic.main.fragment_serie_details_slave.serieSeenSwitcher
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.AnimUtils
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.justify
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.SnackBar
import revolhope.splanes.com.aikver.presentation.common.widget.snakbar.model.SnackBarModel

class SerieDetailsSlaveFragment : BaseFragment() {

    val viewModel: SerieDetailsSlaveViewModel by viewModel()

    companion object {

        private const val ARG_X = "SerieDetailsSlaveFragment.arg.X"
        private const val ARG_Y = "SerieDetailsSlaveFragment.arg.Y"
        private const val ARG_WIDTH = "SerieDetailsSlaveFragment.arg.WIDTH"
        private const val ARG_HEIGHT = "SerieDetailsSlaveFragment.arg.HEIGHT"

        fun newInstance(
            x: Float,
            y: Float,
            width: Int,
            height: Int
        ): SerieDetailsSlaveFragment {
            return SerieDetailsSlaveFragment().apply {
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
        serieSeenSwitcher.setOnCheckedChangeListener { _, checked ->
            punctuationGroup.visibility(checked)
            TransitionManager.beginDelayedTransition(rootLayout)
        }
        punctuationView.whiteMode()
        addButton.setOnClickListener { addSerie() }
    }

    override fun initObservers() {
        observe(viewModel.addSerieResult) {
            SnackBar.show(
                root,
                if (it) SnackBarModel.Success("T_Serie añadida") { activity?.finish() }
                else SnackBarModel.Error("T_Ooops.. Ha habido un error")
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
        (activity as? SerieDetailsActivity)?.getContent()?.let {
            viewModel.addSerie(
                SerieCustomInfoUiModel(
                    it,
                    serieSeenSwitcher.isChecked,
                    punctuationView.getScore(),
                    networkSelector.getSelected(),
                    commentInputEditText.text?.toString() ?: ""
                )
            )
        }
    }

    override fun getLayoutResource(): Int = R.layout.fragment_serie_details_slave
}