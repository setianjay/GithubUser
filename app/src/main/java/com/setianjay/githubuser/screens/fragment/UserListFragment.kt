package com.setianjay.githubuser.screens.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.FragmentUserListBinding
import com.setianjay.githubuser.model.user.UserManager
import com.setianjay.githubuser.model.user.UserModel
import com.setianjay.githubuser.screens.activity.HomeActivity
import com.setianjay.githubuser.screens.adapter.UserListAdapter

class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userData: List<UserModel>
    private lateinit var userAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        showUser(userData)
        setTitle()
    }

    private fun setupRecycleView(){
        userData = UserManager(resources).dataUser()
        userAdapter = UserListAdapter(object: UserListAdapter.OnUserListAdapterListener{
            override fun onClick(data: UserModel) {
                val bundle = Bundle()
                bundle.putParcelable(UserProfileFragment.EXTRA_PROFILE, data)
                findNavController().navigate(R.id.action_userListFragment_to_userProfileFragment,bundle)
            }

        })

        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    private fun showUser(userData: List<UserModel>) {
        userAdapter.setDataUser(userData)
    }

    private fun setTitle(){
        (requireActivity() as HomeActivity).setTitle(getString(R.string.home))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}