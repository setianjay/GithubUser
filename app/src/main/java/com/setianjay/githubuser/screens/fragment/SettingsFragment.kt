package com.setianjay.githubuser.screens.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.setianjay.githubuser.R
import com.setianjay.githubuser.databinding.FragmentSettingsBinding
import com.setianjay.githubuser.viewmodel.GithubViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<GithubViewModel>({
        requireActivity()
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        setTitle()
    }

    /* function to set of title for current fragment and send the value to HomeActivity */
    private fun setTitle() {
        viewModel.app.setTitle(getString(R.string.settings))
    }

    /* function to initialize all listener in this layout */
    private fun initListener() {
        // listener for change language
        binding?.containerLanguage?.setOnClickListener {
            Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS).also {
                startActivity(it)
            }
        }

        // listener for change theme
        binding?.swTheme?.setOnCheckedChangeListener { _, checked ->
            viewModel.app.setTheme(checked)
        }
    }

    /* function to set up any observer in view model */
    private fun setupObserver(){
        // observe theme of the application, is it dark mode or light mode
        viewModel.app.getTheme().observe(viewLifecycleOwner){ isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.swTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.swTheme?.isChecked = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}