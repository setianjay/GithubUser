package com.setianjay.githubuser.screens.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.setianjay.githubuser.databinding.FragmentFollowersBinding
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.network.resource.Resource
import com.setianjay.githubuser.screens.adapter.UserListAdapter
import com.setianjay.githubuser.utill.Constant
import com.setianjay.githubuser.utill.display
import com.setianjay.githubuser.viewmodel.GithubViewModel

class FollowersFragment private constructor() : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    private lateinit var userAdapter: UserListAdapter
    private var username: String? = null

    private val viewModel by viewModels<GithubViewModel>({
        requireActivity()
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setupObserver()
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        showFollowers()
    }

    /* function to initialize data */
    private fun initData() {
        username = arguments?.getString(ARG_USERNAME)
    }

    /* function to set up any observer in view model */
    private fun setupObserver() {
        // observe for followers of user and set with value based on condition
        viewModel.network.getFollowers().observe(viewLifecycleOwner) {
            when (it.statusType) {
                Resource.StatusType.LOADING -> {
                    showLoading(true)
                }
                Resource.StatusType.SUCCESS -> {
                    showLoading(false)
                    it.data?.let { followers -> userAdapter.setDataUser(followers) }
                }
                Resource.StatusType.ERROR -> {
                    showLoading(false)
                    it.message?.let { message -> getError(message) }
                }
            }
        }
    }

    /* function to set up recycle view */
    private fun setupRecycleView() {
        userAdapter = UserListAdapter(object : UserListAdapter.OnUserListAdapterListener {
            override fun onClick(data: UsersModel) {

            }
        })

        binding?.rvFollowers?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    /* function to show followers of user */
    private fun showFollowers() {
        username?.let { viewModel.network.userFollowers(it) }
    }

    /* function to show and not show progress bar for loading content */
    private fun showLoading(show: Boolean) {
        binding?.pbLoading?.display(show)
    }

    /* function to show and not show button refresh */
    private fun showBtnRefresh(show: Boolean) {
        binding?.btnRefresh?.display(show)
    }

    /* function to get error code */
    private fun getError(errorCode: Int) {
        when (errorCode) {
            Constant.ERROR.ERR_USERS_NOT_FOUND -> {
                binding?.tvNoFollowers?.visibility = View.VISIBLE
            }
            Constant.ERROR.ERR_API -> {
                showBtnRefresh(true)
                binding?.btnRefresh?.setOnClickListener {
                    showFollowers()
                    showBtnRefresh(false)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_USERNAME = "username"

        /* function to initialize this class */
        fun newInstance(username: String): Fragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }
}