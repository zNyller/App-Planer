package com.nyller.android.mach3.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.nyller.android.mach3.activities.LoginActivity
import com.nyller.android.mach3.databinding.FragmentUserBinding
import com.nyller.android.mach3.utils.toast

class ConfigFragment : Fragment() {
    private lateinit var _binding : FragmentUserBinding
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater)

        return _binding.root
    }

    override fun onStart() {
        super.onStart()

        _binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            context?.let { it1 -> "Log-out efetuado!".toast(it1) }
        }

    }

}