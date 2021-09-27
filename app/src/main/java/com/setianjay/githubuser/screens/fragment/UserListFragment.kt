package com.setianjay.githubuser.screens.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.FragmentUserListBinding
import com.setianjay.githubuser.model.user.UsersModel
import com.setianjay.githubuser.network.resource.Resource
import com.setianjay.githubuser.screens.adapter.UserListAdapter
import com.setianjay.githubuser.screens.common.dialogs.DialogsNavigators
import com.setianjay.githubuser.utill.Constant
import com.setianjay.githubuser.utill.hideKeyboard
import com.setianjay.githubuser.viewmodel.GithubViewModel

class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val dialogsNavigators by lazy { DialogsNavigators(requireContext()) }

    private lateinit var userAdapter: UserListAdapter

    private val viewModel by viewModels<GithubViewModel>({
        requireActivity()
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setupObserver()
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        setTitle()
        showImgInformation(true)
    }

    /* function to set of title for current fragment and send the value to HomeActivity */
    private fun setTitle() {
        viewModel.app.setTitle(getString(R.string.home))
    }

    /* function to initialize all listener in this layout */
    private fun initListener() {
        // listener for searching with edit text
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val username = binding.etSearch.text.toString().trim()
                if (username.isEmpty()) { // if username empty, send notification error
                    binding.etSearch.error = getString(R.string.field_required)
                } else { // if username not empty, call the search Github API
                    viewModel.network.searchUsers(username)
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    /* function to set up any observer in view model */
    private fun setupObserver() {
        // observe for users data and set with value based on condition
        viewModel.network.getUsers().observe(viewLifecycleOwner) {
            when (it.statusType) {
                Resource.StatusType.LOADING -> {
                    context?.hideKeyboard(requireView())
                    showLoading(true)
                    showImgInformation(false)
                    showRecycleView(false)
                }
                Resource.StatusType.SUCCESS -> {
                    showLoading(false)
                    showImgInformation(false)
                    showRecycleView(true)
                    it.data?.let { users -> userAdapter.setDataUser(users) }
                }
                else -> {
                    context?.hideKeyboard(requireView())
                    showLoading(false)
                    showRecycleView(false)
                    it.message?.let { message -> getError(message) }
                }
            }
        }
    }

    /* function to set up recycle view */
    private fun setupRecycleView() {
        userAdapter = UserListAdapter(object : UserListAdapter.OnUserListAdapterListener {
            override fun onClick(data: UsersModel) {
                val bundle = Bundle()
                bundle.putString(UserProfileFragment.EXTRA_PROFILE, data.username)
                findNavController().navigate(
                    R.id.action_userListFragment_to_userProfileFragment,
                    bundle
                )
            }

        })

        binding.rvUserList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    /* function for show and not show image information, IMG_SEARCH_INFORMATION is default. */
    private fun showImgInformation(
        show: Boolean,
        typeInfo: Int = Constant.INFO.IMG_SEARCH_INFORMATION
    ) {
        when (typeInfo) {
            Constant.INFO.IMG_SEARCH_INFORMATION -> {
                if (show) {
                    binding.apply {
                        ivInformation.visibility = View.VISIBLE
                        ivInformation.setImageResource(R.drawable.ic_search_information)

                        tvTitle.visibility = View.VISIBLE
                        tvTitle.text = getString(R.string.title_search)


                        tvMessage.visibility = View.VISIBLE
                        tvMessage.text = getString(R.string.message_search)
                    }
                } else {
                    binding.apply {
                        ivInformation.visibility = View.GONE
                        tvTitle.visibility = View.GONE
                        tvMessage.visibility = View.GONE
                    }
                }
            }
            Constant.INFO.IMG_NOT_FOUND_INFORMATION -> {
                if (show) {
                    binding.apply {
                        ivInformation.visibility = View.VISIBLE
                        ivInformation.setImageResource(R.drawable.ic_oops)

                        tvTitle.visibility = View.VISIBLE
                        tvTitle.text = getString(R.string.title_not_found)


                        tvMessage.visibility = View.VISIBLE
                        tvMessage.text = getString(R.string.message_not_found)
                    }
                } else {
                    binding.apply {
                        ivInformation.visibility = View.GONE
                        tvTitle.visibility = View.GONE
                        tvMessage.visibility = View.GONE
                    }
                }
            }
        }
    }

    /* function to show and not show progress bar for loading content */
    private fun showLoading(show: Boolean) {
        binding.pbLoading.visibility = if (show) View.VISIBLE else View.GONE
    }

    /* function to show and not show recycle view */
    private fun showRecycleView(show: Boolean) {
        binding.rvUserList.visibility = if (show) View.VISIBLE else View.GONE
    }

    /* function to get error code */
    private fun getError(errorCode: Int) {
        when (errorCode) {
            Constant.ERROR.ERR_API -> {
                // show dialog server error
                dialogsNavigators.dialogServerError(object: DialogsNavigators.IOnDialogsNavigator{

                    override fun positiveButton(dialog: Dialog) {
                        showImgInformation(true)
                        dialog.dismiss()
                    }

                    override fun negativeButton(dialog: Dialog) {
                        showImgInformation(true)
                        dialog.dismiss()
                    }

                })
            }
            Constant.ERROR.ERR_USERS_NOT_FOUND -> {
                // show img information for user not found
                showImgInformation(true, Constant.INFO.IMG_NOT_FOUND_INFORMATION)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}