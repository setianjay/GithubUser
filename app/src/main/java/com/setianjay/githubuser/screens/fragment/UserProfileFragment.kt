package com.setianjay.githubuser.screens.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.FragmentUserProfileBinding
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.network.resource.Resource
import com.setianjay.githubuser.screens.common.dialogs.DialogsNavigators
import com.setianjay.githubuser.screens.common.tablayout.ProfileTabLayout
import com.setianjay.githubuser.viewmodel.GithubViewModel

class UserProfileFragment : Fragment() {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private var data: UsersModel? = null
    private val dialogsNavigators by lazy { DialogsNavigators(requireContext()) }

    private lateinit var viewModel: GithubViewModel

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
        initViewModel()
        initData()
        setupObserver()
        setupTabLayout()
    }

    override fun onStart() {
        super.onStart()
        setTitle()
        showDetails()
    }

    /* function to set of title for current fragment and send the value to HomeActivity */
    private fun setTitle() {
        viewModel.setTitle(getString(R.string.profile))
    }

    /* function to initialize data */
    private fun initData() {
        val bundle = arguments
        if (bundle != null) {
            data = bundle.getParcelable(EXTRA_PROFILE)
        }
    }

    /* function to show details of user*/
    private fun showDetails() {
        val username = data?.username
        if (username != null) viewModel.userDetails(username)
    }

    /* function to initialize view model */
    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(GithubViewModel::class.java)
    }

    /* function to set up any observer in view model */
    private fun setupObserver() {
        // observe for user details data and set with value based on condition
        viewModel.getUserDetails().observe(viewLifecycleOwner) {
            when (it.statusType) {
                Resource.StatusType.LOADING -> {
                    showLoading(true)
                    showContentDetail(false)
                }
                Resource.StatusType.SUCCESS -> {
                    showLoading(false)
                    showContentDetail(true)
                    Glide.with(binding.ivProfile.context)
                        .load(it.data?.avatar)
                        .into(binding.ivProfile)

                    binding.apply {
                        tvRepository.text = it.data?.totalRepository.toString()
                        tvFollowers.text = it.data?.totalFollowers.toString()
                        tvFollowing.text = it.data?.totalFollowing.toString()
                        tvName.text = it.data?.name
                        tvUsername.text = it.data?.username ?: getString(R.string.no_data)
                        tvCompany.text = it.data?.company ?: getString(R.string.no_data)
                        tvLocation.text = it.data?.location ?: getString(R.string.no_data)
                    }
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
        val tabAdapter = ProfileTabLayout(childFragmentManager, lifecycle)
        binding.vwPager.adapter = tabAdapter

        val titlesTabLayout = listOf(getString(R.string.followers), getString(R.string.following))
        TabLayoutMediator(binding.tbLayout, binding.vwPager) { tab, position ->
            tab.text = titlesTabLayout[position]
        }.attach()
    }

    /* function to show progress bar for loading content */
    private fun showLoading(show: Boolean) {
        binding.pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    /* function to show the detail profile content */
    private fun showContentDetail(show: Boolean) {
        binding.containerProfile.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}