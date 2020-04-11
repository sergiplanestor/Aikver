package revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar

import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user_avatar.avatarPreview
import kotlinx.android.synthetic.main.activity_user_avatar.closeButton
import kotlinx.android.synthetic.main.activity_user_avatar.colorRecyclerView
import kotlinx.android.synthetic.main.activity_user_avatar.doneButton
import kotlinx.android.synthetic.main.activity_user_avatar.eyesSpinner
import kotlinx.android.synthetic.main.activity_user_avatar.mouthSpinner
import kotlinx.android.synthetic.main.activity_user_avatar.noseSpinner
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.widget.popup.Popup
import revolhope.splanes.com.core.domain.model.UserAvatar
import revolhope.splanes.com.core.domain.model.UserAvatarTypes

class UserAvatarActivity : BaseActivity(), AdapterView.OnItemSelectedListener {

    private val viewModel: UserAvatarViewModel by viewModel()
    private val mapEyes: MutableMap<String, String> = mutableMapOf()
    private val mapNose: MutableMap<String, String> = mutableMapOf()
    private val mapMouth: MutableMap<String, String> = mutableMapOf()
    private lateinit var userAvatar: UserAvatar

    override fun initViews() {
        super.initViews()

        doneButton.setOnClickListener { onDoneClick() }
        closeButton.setOnClickListener { onCloseClick() }
    }

    override fun initObservers() {
        super.initObservers()

        observe(viewModel.user) { user ->
            user?.let {
                viewModel.fetchAvatarTypes()
                userAvatar = it.avatar.copy()
                avatarPreview.loadAvatar(userAvatar)
            }
        }

        observe(viewModel.avatarTypes) {
            if (it != null) {
                mapAvatarTypes(it)
                bindViews()
            } else {
                Popup.showError(this, supportFragmentManager)
            }
        }

        observe(viewModel.insertAvatarResult) {
            hideLoader()
            if (it) {
                onBackPressed()
            }
            else {
                Popup.showError(
                    this,
                    supportFragmentManager,
                    View.OnClickListener {
                        onBackPressed()
                    }
                )
            }
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchUser()
    }

    private fun bindViews() {

        eyesSpinner.adapter = ArrayAdapter(
            this,
            R.layout.component_app_spinner_item,
            mapEyes.keys.toList()
        ).apply { setDropDownViewResource(R.layout.component_app_spinner_dropdown_item) }

        noseSpinner.adapter = ArrayAdapter(
            this,
            R.layout.component_app_spinner_item,
            mapNose.keys.toList()
        ).apply { setDropDownViewResource(R.layout.component_app_spinner_dropdown_item) }

        mouthSpinner.adapter = ArrayAdapter(
            this,
            R.layout.component_app_spinner_item,
            mapMouth.keys.toList()
        ).apply { setDropDownViewResource(R.layout.component_app_spinner_dropdown_item) }

        colorRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        eyesSpinner.setSelection(getIndexOf(mapEyes, userAvatar.eyes), true)
        noseSpinner.setSelection(getIndexOf(mapNose, userAvatar.nose), true)
        mouthSpinner.setSelection(getIndexOf(mapMouth, userAvatar.mouth), true)
        colorRecyclerView.adapter = UserAvatarColorAdapter(userAvatar.color, ::onColorSelected)

        eyesSpinner.onItemSelectedListener = this
        noseSpinner.onItemSelectedListener = this
        mouthSpinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { /* Nothing to do */ }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent) {
            eyesSpinner -> userAvatar.eyes = mapEyes["Tipo ${position + 1}"] ?: ""
            noseSpinner -> userAvatar.nose = mapNose["Tipo ${position + 1}"] ?: ""
            mouthSpinner -> userAvatar.mouth = mapMouth["Tipo ${position + 1}"] ?: ""
        }
        avatarPreview.loadAvatar(userAvatar)
    }

    private fun mapAvatarTypes(avatarTypes: UserAvatarTypes) {
        avatarTypes.eyes.forEachIndexed { index, item ->
            mapEyes["Tipo ${index + 1}"] = item
        }

        avatarTypes.nose.forEachIndexed { index, item ->
            mapNose["Tipo ${index + 1}"] = item
        }

        avatarTypes.mouth.forEachIndexed { index, item ->
            mapMouth["Tipo ${index + 1}"] = item
        }
    }

    private fun getIndexOf(map: Map<String, String>, value: String) : Int =
        map.keys.find { map[it] == value }?.let { map.keys.indexOf(it) } ?: 0

    private fun onColorSelected(color: String) {
        userAvatar.color = color
        avatarPreview.loadAvatar(userAvatar)
    }

    private fun onDoneClick() {
        showLoader()
        viewModel.insertAvatar(userAvatar)
    }

    private fun onCloseClick() {
        setResult(Activity.RESULT_CANCELED)
        onBackPressed()
    }

    override fun getLayoutRes(): Int = R.layout.activity_user_avatar
}