package com.khve.feature_profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.feature_auth.domain.entity.User
import com.khve.feature_auth.domain.entity.UserRole
import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_meta.domain.entity.MetaCardItem
import com.khve.feature_meta.domain.entity.MetaCardListState
import com.khve.ui.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: FragmentUserProfileBinding? = null
    private val binding: FragmentUserProfileBinding
        get() = _binding ?: throw NullPointerException("FragmentUserProfileBinding == null")

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserProfile()
    }

    private fun showUserProfile() {
        val userUid = arguments?.getString(USER_UID)
        observeUserState()
        observeUserMetaCardListState()
        if (userUid == null) {
            viewModel.findCurrentUserByUid()
        } else {
            viewModel.findAnotherUserByUid(userUid)
        }
    }

    private fun observeUserMetaCardListState() {
        viewModel.getUserMetaCardList(null)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.metaCardListState.collect {
                    when (it) {
                        MetaCardListState.Initial -> {}
                        is MetaCardListState.Error -> {}
                        MetaCardListState.Initial -> {}
                        is MetaCardListState.MetaCardList -> {
                            binding.tvKarma.text = countKarma(it.metaCardList).toString()
                        }
                        MetaCardListState.Progress -> {}
                    }
                }
            }
        }
    }

    private fun countKarma(metaCardList: List<MetaCardItem>): Int {
        return metaCardList.flatMap { it.likes }.count() - metaCardList.flatMap { it.dislikes }.count()
    }

    private fun observeUserState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.userState.collect {
                    when (it) {
                        UserState.Initial -> {}
                        is UserState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UserState.User -> {
                            setupViews(it.user)
                        }
                        UserState.NotAuthorized -> {}
                    }
                }
            }
        }
    }

    private fun setupViews(user: User) {
        with(binding) {
            tvUsername.text = user.username
            tvRole.text = user.role.title
            setupVerifyEmailButton(user)
        }
    }

    private fun setupVerifyEmailButton(user: User) {
        with(binding.btnVerifyEmail) {
            visibility = if (user.role == UserRole.NOT_VERIFIED) View.VISIBLE else View.GONE
            if (user.role == UserRole.NOT_VERIFIED) {
                setOnClickListener {
                    isClickable = false
                    isEnabled = false
                    sendEmailVerification()
                }
            }
        }
    }

    private fun sendEmailVerification() {
        viewModel.sendVerificationEmail()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.notification.collect { message ->
                    if (message != null) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        private const val USER_UID = "user_uid"
        const val BACKSTACK_NAME = "user_profile_fragment"

        @JvmStatic
        fun newInstance(userUid: String?) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_UID, userUid)
                }
            }

    }
}