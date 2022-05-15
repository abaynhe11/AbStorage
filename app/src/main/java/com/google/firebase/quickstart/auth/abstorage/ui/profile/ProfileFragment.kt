package com.google.firebase.quickstart.auth.abstorage.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.quickstart.auth.abstorage.MainActivity
import com.google.firebase.quickstart.auth.abstorage.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.profile_preference_key),
            Context.MODE_PRIVATE
        )

        txtName.setText(
            sharedPref.getString(
                getString(R.string.profile_name_key),
                getString(R.string.profile_name_default)
            )
        )

        btnSave.setOnClickListener {
            with(sharedPref.edit()) {
                putString(
                    getString(R.string.profile_name_key),
                    txtName.text.toString()
                )
                apply()
            }

            (activity as MainActivity).hideKeyboard()
            Snackbar
                .make(
                    view,
                    getString(R.string.profile_success),
                    Snackbar.LENGTH_SHORT
                )
                .show()
        }
    }
}