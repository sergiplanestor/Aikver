package revolhope.splanes.com.aikver.presentation.detailsserie
/*

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.FragmentManager
import revolhope.splanes.com.aikver.R
import bemobile.splanes.com.core.domain.Serie
import revolhope.splanes.com.aikver.framework.helper.SharedPreferencesHelper
import revolhope.splanes.com.aikver.presentation.common.widget.AppLoader
import revolhope.splanes.com.aikver.presentation.common.widget.ResultView
import revolhope.splanes.com.aikver.presentation.common.widget.ScoreView
import revolhope.splanes.com.aikver.presentation.common.widget.popup.Popup
import revolhope.splanes.com.aikver.presentation.common.widget.popup.PopupModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailsSerieBottomSheet(private val serie: bemobile.splanes.com.core.domain.Serie): BottomSheetDialogFragment() {

    private val sharedPreferencesHelper: SharedPreferencesHelper by inject()
    private val viewModel: DetailsSerieViewModel by viewModel()
    private lateinit var resultView: ResultView
    private lateinit var appLoader: AppLoader
    private lateinit var contentLayout: ConstraintLayout
    private lateinit var rootLayout: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details_serie, container)
        val user = sharedPreferencesHelper.getString(SharedPreferencesHelper.PREF_USR)

        rootLayout = view.findViewById(R.id.rootLayout)
        contentLayout = view.findViewById(R.id.contentLayout)
        appLoader = view.findViewById(R.id.appLoader)
        resultView = view.findViewById(R.id.resultView)

        view.findViewById<TextView>(R.id.serieTitleTextView).text = serie.title
        view.findViewById<TextView>(R.id.scoreTextView).text = serie.score.toString()

        if (serie.category != null)
            view.findViewById<TextView>(R.id.categoryTextView).text = serie.category
        else view.findViewById<LinearLayout>(R.id.categoryLayout).visibility = View.GONE

        if (serie.platform != null)
            view.findViewById<ImageView>(R.id.platformImageView).background =
                resources.getDrawable(serie.platform.imageRes, null)
        else view.findViewById<LinearLayout>(R.id.platformLayout).visibility = View.GONE

        if (haveUserScored(user))
            view.findViewById<Group>(R.id.scoreGroup).visibility = View.GONE
        else {
            with(view.findViewById<ScoreView>(R.id.scoreView)) {
                hideNumeric()
                setDark()
                isCentered(true)
                updateScore(serie.score)
                isEditable(!haveUserScored(user))
            }
            view.findViewById<MaterialButton>(R.id.uploadScoreButton).setOnClickListener {
                onUpdateClick(serie.apply {
                    addScore(view.findViewById<ScoreView>(R.id.scoreView).getScore(), user)
                })
            }
        }

        if (serie.numScorers == 0)
            view.findViewById<TextView>(R.id.numScorersTextView).visibility = View.GONE
        else
            view.findViewById<TextView>(R.id.numScorersTextView).text =
                getString(R.string.template_scorers, serie.numScorers)

        view.findViewById<TextView>(R.id.creatorTextView).text =
            getString(R.string.template_user_creator, serie.userCreator, getDate(serie.dateCreation))

        if (serie.userCreator == user) {
            with(view.findViewById<ImageView>(R.id.deleteButton)) {
                visibility = View.VISIBLE
                setOnClickListener{ onDeleteClick(serie) }
            }
        }

        return view
    }

    private fun haveUserScored(user: String?): Boolean =
        user != null && serie.userScorers != null && serie.userScorers!!.contains(user)


    private fun getDate(millis: Long): String =
        SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).format(millis)

    private fun onUpdateClick(serie: bemobile.splanes.com.core.domain.Serie) {
        if (activity != null) {
            showLoader()
            viewModel.updateSerie(serie).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                activity?.runOnUiThread {
                    showResult(it)
                }
            })
        }
    }

    private fun onDeleteClick(serie: bemobile.splanes.com.core.domain.Serie)     {
        if (activity != null) {
            Popup.show(childFragmentManager, PopupModel(
                title = getString(R.string.delete_confirmation_title),
                message = getString(R.string.delete_confirmation_message),
                buttonPositive = getString(R.string.delete_confirmation_ok),
                buttonNegative = getString(R.string.delete_confirmation_cancel),
                buttonPositiveListener = View.OnClickListener {
                    if (activity != null) {
                        showLoader()
                        viewModel.deleteSerie(serie).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                            activity?.runOnUiThread {
                                if (it) this.dismiss()
                                else Popup.showError(activity!!, childFragmentManager, View.OnClickListener { })
                            }
                        })
                    }
                },
                isCancelable = true
            ))
        }
    }

    private fun showLoader() {
        appLoader.visibility = View.VISIBLE
        contentLayout.visibility = View.INVISIBLE
        appLoader.play()
        TransitionManager.beginDelayedTransition(rootLayout)
    }

    private fun showResult(success: Boolean) {
        resultView.initialize(success)
        appLoader.visibility = View.GONE
        resultView.visibility = View.VISIBLE
        resultView.play()
        TransitionManager.beginDelayedTransition(rootLayout)
    }

    fun show(fm: FragmentManager) {
        show(fm, this::javaClass.name)
    }
}*/
