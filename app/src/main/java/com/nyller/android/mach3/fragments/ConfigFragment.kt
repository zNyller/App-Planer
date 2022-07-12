package com.nyller.android.mach3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nyller.android.mach3.databinding.FragmentUserBinding

class ConfigFragment : Fragment() {
    private lateinit var _binding : FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater)
        return _binding.root
    }

}