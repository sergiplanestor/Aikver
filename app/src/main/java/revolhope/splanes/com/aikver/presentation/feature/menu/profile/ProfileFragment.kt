package revolhope.splanes.com.aikver.presentation.feature.menu.profile

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import revolhope.splanes.com.aikver.R
import revolhope.splanes.com.aikver.framework.app.observe
import revolhope.splanes.com.aikver.presentation.common.base.BaseActivity
import revolhope.splanes.com.aikver.presentation.common.base.BaseFragment
import revolhope.splanes.com.aikver.presentation.common.loadAvatar
import revolhope.splanes.com.aikver.presentation.common.loadCircular
import revolhope.splanes.com.aikver.presentation.common.visibility
import revolhope.splanes.com.aikver.presentation.common.widget.popup.Popup
import revolhope.splanes.com.aikver.presentation.feature.menu.profile.avatar.UserAvatarActivity
import revolhope.splanes.com.core.domain.model.User

class ProfileFragment : BaseFragment(), View.OnClickListener {

    private val viewModel: ProfileViewModel by viewModel()

    override fun initViews() {
        super.initViews()
        profileAvatarImageView.setOnClickListener(this)
    }

    override fun initObservers() {
        super.initObservers()
        observe(viewModel.user, ::bindViews)
    }

    private fun bindViews(user: User?) {
        if (user == null) {
            if (context != null) Popup.showError(context!!, childFragmentManager)
        } else {
            bindUserCardView(user)
            bindSelectedUserGroup(user)
        }
    }

    private fun bindUserCardView(user: User) {
        profileAvatarImageView.loadAvatar(user.avatar)
        //profileAvatarImageView.loadCircular("https://api.adorable.io/avatars/face/eyes9/${user.id}.png")
        profileUserNameTextView.text = user.username
    }

    private fun bindSelectedUserGroup(user: User) {
        val group = user.selectedUserGroup
        selectedGroupLayout.visibility(group != null)
        selectedGroupEmptyStateLayout.visibility(group == null)

        if (group != null) {
            selectedGroupImageView.loadCircular(group.icon)
            selectedGroupNameTextView.text = group.name
            selectedGroupMembersRecyclerView.layoutManager = LinearLayoutManager(context)
            selectedGroupMembersRecyclerView.adapter =
                UserGroupMembersAdapter(user.id, group.members)
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.fetchUser()
    }

    override fun onClick(view: View?) {
        (activity as BaseActivity?)?.navigateUp(UserAvatarActivity::class.java, isForResult = true)
    }

    override fun getLayoutResource(): Int = R.layout.fragment_profile
}

/*
    TODO: Avatar's api
    "https://api.adorable.io/avatars/285/${user.id}.png"
    "https://robohash.org/${user.id}.png?set=set4"
    "https://avatars.dicebear.com/v2/bottts/${user.id}.svg?options[mood][]=happy"


    TODO: Avatar's url
    deadpool -> https://www.pngkey.com/png/full/468-4685836_funny-avatar-png-graphic-transparent-library-dream-league.png
    homer -> https://revistadiners.com.co/wp-content/uploads/2016/03/homero_800x669.jpg
    sakura -> https://forosdz.com/imagenes/sakura-avatar-png.36745/
    breaking bad -> https://i3.sndcdn.com/avatars-000329792062-ffya5t-t500x500.jpg
    olaf -> https://www.stickpng.com/assets/images/589ee4e964b351149f22a871.png
    anonymous -> https://c7.uihere.com/files/574/309/291/anonymous-icon-v-for-vendetta-png-transparent-image.jpg
    star trek -> https://f1.pngfuel.com/png/589/296/843/facebook-logo-hat-star-trek-star-trek-the-role-playing-game-roleplaying-game-drawing-avatar-cone-png-clip-art.png
    groot -> https://cdn130.picsart.com/297590663053201.png?type=webp&to=min&r=640
*/