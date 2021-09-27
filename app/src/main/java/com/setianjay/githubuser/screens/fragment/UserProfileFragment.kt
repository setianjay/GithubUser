package com.setianjay.githubuser.screens.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.setianjay.githubuser.R
import com.setianjay.githubuser.database.presistence.entity.User
import com.setianjay.githubuser.databinding.FragmentUserProfileBinding
import com.setianjay.githubuser.network.resource.Resource
import com.setianjay.githubuser.screens.common.dialogs.DialogsNavigators
import com.setianjay.githubuser.screens.common.tablayout.ProfileTabLayout
import com.setianjay.githubuser.utill.*
import com.setianjay.githubuser.viewmodel.GithubViewModel

class UserProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val dialogsNavigators by lazy { DialogsNavigators(requireContext()) }

    private var isUserExists = false

    private lateinit var type: String
    private lateinit var username: String
    private lateinit var userImg: String

    private val viewModel by viewModels<GithubViewModel>({
        requireActivity()
    })

    companion object {
        const val EXTRA_PROFILE = "extra_profile"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
        setupObserver()
        setupTabLayout()
    }

    override fun onStart() {
        super.onStart()
        checkUser()
        setTitle()
        showDetails()
    }

    /* function to initialize data */
    private fun initData() {
        val bundle = arguments
        if (bundle != null) {
            val data = bundle.getString(EXTRA_PROFILE)
            data?.let { username = it }
        }
    }

    /* function to handle listener for each view in this layout */
    private fun initListener() {
        binding.fabProfile.setOnClickListener(this)
    }

    /* function to check user is exist or not exist in local persistence */
    private fun checkUser() {
        // observe for the result of specific user
        viewModel.db.checkUserExists(username).observe(viewLifecycleOwner) { user ->
            // if user is not null (user is exist in local persistence)
            if (user != null) {
                setSrcFab(R.drawable.ic_heart_red)
                isUserExists = true
            }
        }
    }

    /* function to set of title for current fragment and send the value to HomeActivity */
    private fun setTitle() {
        viewModel.app.setTitle(getString(R.string.profile))
    }

    /* function to show details of user */
    private fun showDetails() {
       viewModel.network.userDetails(username)
    }

    /* function to set up any observer in view model */
    private fun setupObserver() {
        // observe for details of user and set with value based on condition
        viewModel.network.getUserDetails().observe(viewLifecycleOwner) {
            when (it.statusType) {
                Resource.StatusType.LOADING -> {
                    showLoading(true)
                    showContentDetail(false)
                }
                Resource.StatusType.SUCCESS -> {
                    showLoading(false)
                    showContentDetail(true)
                    it.data?.avatar?.let { src -> binding.ivProfile.load(src) }

                    binding.apply {
                        tvRepository.text = it.data?.totalRepository.toString()
                        tvFollowers.text = it.data?.totalFollowers.toString()
                        tvFollowing.text = it.data?.totalFollowing.toString()
                        tvName.text = it.data?.name
                        tvUsername.text = it.data?.username ?: getString(R.string.no_data)
                        tvCompany.text = it.data?.company ?: getString(R.string.no_data)
                        tvLocation.text = it.data?.location ?: getString(R.string.no_data)
                    }
                    type = it.data?.type.toString()
                    userImg = it.data?.avatar.toString()
                }
                Resource.StatusType.ERROR -> {
                    showLoading(false)
                    showContentDetail(false)
                    dialogsNavigators.dialogServerError(object :
                        DialogsNavigators.IOnDialogsNavigator {

                        override fun positiveButton(dialog: Dialog) {
                            showDetails()
                            dialog.dismiss()
                        }


                        override fun negativeButton(dialog: Dialog) {
                            dialog.dismiss()
                            findNavController().navigate(R.id.action_userProfileFragment_to_userListFragment)
                        }
                    })
                }
            }
        }
    }

    /* function to setup tab layout and viewpager */
    private fun setupTabLayout() {
        val tabAdapter = ProfileTabLayout(childFragmentManager, lifecycle, username)
        binding.vwPager.adapter = tabAdapter

        val titlesTabLayout = listOf(getString(R.string.followers), getString(R.string.following))
        TabLayoutMediator(binding.tbLayout, binding.vwPager) { tab, position ->
            tab.text = titlesTabLayout[position]
        }.attach()
    }

    /* function to show and not show progress bar for loading content */
    private fun showLoading(show: Boolean) {
        binding.pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    /* function to show and not show the detail profile content */
    private fun showContentDetail(show: Boolean) {
        binding.containerProfile.visibility = if (show) View.VISIBLE else View.GONE
    }

    /* function to set image source for floating action button */
    private fun setSrcFab(drawable: Int){
        binding.fabProfile.src(resources, drawable)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.fab_profile) {
            val user = User(username, userImg, type)
            if (isUserExists) { // if isUserExists is true (user is exists in local persistence)
                viewModel.db.deleteUserFavorite(user)
                setSrcFab(R.drawable.ic_heart)
                isUserExists = false
                AppUtil.showToast(requireContext(), getString(R.string.favorites_remove, username))
            } else { // if isUserExists is false (user don't have exists in local persistence)
                viewModel.db.addUserFavorite(user)
                setSrcFab(R.drawable.ic_heart_red)
                isUserExists = true
                AppUtil.showToast(requireContext(), getString(R.string.favorites_add, username))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}