package com.setianjay.githubuser.screens.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.setianjay.githubuser.databinding.FragmentFollowingBinding
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.network.resource.Resource
import com.setianjay.githubuser.screens.adapter.UserListAdapter
import com.setianjay.githubuser.utill.Constant
import com.setianjay.githubuser.viewmodel.GithubViewModel

class FollowingFragment private constructor() : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserListAdapter
    private var username: String? = null

    private lateinit var viewModel: GithubViewModel

    companion object{
        private const val ARG_USERNAME = "username"

        /* function to initialize this class */
        fun newInstance(username: String): Fragment{
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViewModel()
        setupObserver()
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        showFollowing()
    }

    /* function to initialize data */
    private fun initData(){
        username = arguments?.getString(ARG_USERNAME)
    }

    /* function to initialize view model */
    private fun initViewModel(){
        viewModel = ViewModelProvider(requireActivity()).get(GithubViewModel::class.java)
    }

    /* function to set up any observer in view model */
    private fun setupObserver(){
        // observe for following of user and set with value based on condition
        viewModel.getFollowing().observe(viewLifecycleOwner){
            when(it.statusType){
                Resource.StatusType.LOADING -> {
                    showLoading(true)
                }
                Resource.StatusType.SUCCESS -> {
                    showLoading(false)
                    it.data?.let { following -> userAdapter.setDataUser(following) }
                }
                Resource.StatusType.ERROR -> {
                    showLoading(false)
                    it.message?.let { message -> getError(message) }
                }
            }
        }
    }

    /* function to set up recycle view */
    private fun setupRecycleView(){
        userAdapter = UserListAdapter(object: UserListAdapter.OnUserListAdapterListener{
            override fun onClick(data: UsersModel) {

            }
        })

        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    /* function to show following of user */
    private fun showFollowing(){
        username?.let { viewModel.userFollowing(it) }
    }

    /* function to show progress bar for loading content */
    private fun showLoading(show: Boolean){
        binding.pbLoading.visibility = if(show) View.VISIBLE else View.GONE
    }

    /* function to show button refresh */
    private fun showBtnRefresh(show: Boolean){
        binding.btnRefresh.visibility = if (show) View.VISIBLE else View.GONE
    }

    /* function to get error code */
    private fun getError(errorCode: Int){
        when(errorCode){
            Constant.ERROR.ERR_USERS_NOT_FOUND -> {
                binding.tvNoFollowers.visibility = View.VISIBLE
            }
            Constant.ERROR.ERR_API -> {
                showBtnRefresh(true)
                binding.btnRefresh.setOnClickListener {
                    showFollowing()
                    showBtnRefresh(false)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}