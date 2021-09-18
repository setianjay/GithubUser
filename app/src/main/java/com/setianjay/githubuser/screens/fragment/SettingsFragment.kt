package com.setianjay.githubuser.screens.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.FragmentSettingsBinding
import com.setianjay.githubuser.viewmodel.GithubViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!

    private lateinit var viewModel: GithubViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        setTitle()
    }

    /* function to set of title for current fragment and send the value to HomeActivity */
    private fun setTitle(){
        viewModel.setTitle(getString(R.string.settings))
    }

    /* function to initialize all listener in this layout */
    private fun initListener(){
        // listener for change language
        binding.containerLanguage.setOnClickListener {
            Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS).also {
                startActivity(it)
            }
        }
    }

    /* function to initialize view model */
    private fun initViewModel(){
        viewModel = ViewModelProvider(requireActivity()).get(GithubViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}