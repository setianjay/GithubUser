package com.setianjay.githubuser.screens.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.setianjay.githubuser.databinding.FragmentUserListBinding
import com.setianjay.githubuser.model.user.UserManager
import com.setianjay.githubuser.model.user.UserModel
import com.setianjay.githubuser.screens.adapter.UserListAdapter

class UserListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding

    private lateinit var userData: List<UserModel>
    private lateinit var userAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    override fun onStart() {
        super.onStart()
        showUser(userData)
    }

    private fun initRecycleView(){
        userData = UserManager(resources).dataUser()
        userAdapter = UserListAdapter()

        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    private fun showUser(userData: List<UserModel>) {
        userAdapter.setDataUser(userData)
    }
}