package com.setianjay.githubuser.screens.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.setianjay.githubuser.R
import com.setianjay.githubuser.database.presistence.entity.User
import com.setianjay.githubuser.databinding.FragmentFavoriteBinding
import com.setianjay.githubuser.screens.adapter.UserFavoriteAdapter
import com.setianjay.githubuser.screens.common.dialogs.DialogsNavigators
import com.setianjay.githubuser.utill.display
import com.setianjay.githubuser.viewmodel.GithubViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    private val dialogsNavigator by lazy { DialogsNavigators(requireContext()) }

    private lateinit var userFavoriteAdapter: UserFavoriteAdapter

    private val viewModel by viewModels<GithubViewModel>({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        setTitle()
        showUsersFavorite()
    }

    /* function to set of title for current fragment and send the value to HomeActivity */
    private fun setTitle() {
        viewModel.app.setTitle(getString(R.string.favorite_user))
    }

    /* function to check and display whether the user's favorite exists */
    private fun showUsersFavorite() {
        CoroutineScope(Dispatchers.Main).launch {
            showLoading(true)
            delay(1000L)
            viewModel.db.getUserFavorite().observe(viewLifecycleOwner) { usersFavorite ->
                if (usersFavorite.isNotEmpty()) { // if user favorite exists
                    showLoading(false)
                    showImgInformation(false)
                    showRecycleView(true)
                    userFavoriteAdapter.setDataUser(usersFavorite)
                } else { // if user favorite doesn't exists
                    showLoading(false)
                    showImgInformation(true)
                    showRecycleView(false)
                }
            }
        }

    }

    /* function to set up recycle view */
    private fun setupRecycleView() {
        userFavoriteAdapter =
            UserFavoriteAdapter(object : UserFavoriteAdapter.OnUserFavoriteAdapterListener {
                override fun onClick(user: User) {
                    val bundle = Bundle()
                    bundle.putString(UserProfileFragment.EXTRA_PROFILE, user.username)
                    findNavController().navigate(
                        R.id.action_favoriteFragment_to_userProfileFragment,
                        bundle
                    )
                }

                override fun onLongClick(user: User) {
                    dialogsNavigator.dialogWarning(user.username,
                        object : DialogsNavigators.IOnDialogsNavigator {
                            override fun positiveButton(dialog: Dialog) {
                                viewModel.db.deleteUserFavorite(user)
                                dialog.dismiss()
                            }

                            override fun negativeButton(dialog: Dialog) {
                                dialog.dismiss()
                            }
                        })
                }
            })

        binding?.rvFavorite?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userFavoriteAdapter
            setHasFixedSize(true)
        }
    }

    /* function for show and not show progress bar for loading content */
    private fun showLoading(show: Boolean) {
        binding?.pbLoading?.display(show)
    }

    /* function for show and not show image information */
    private fun showImgInformation(show: Boolean) {
        if (show) {
            binding?.apply {
                ivInformation.display(show)
                tvTitle.display(show)
                tvMessage.display(show)
            }
        } else {
            binding?.apply {
                ivInformation.display(show)
                tvTitle.display(show)
                tvMessage.display(show)
            }
        }
    }

    /* function for show and not show recycle view */
    private fun showRecycleView(show: Boolean){
        binding?.rvFavorite?.display(show)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}