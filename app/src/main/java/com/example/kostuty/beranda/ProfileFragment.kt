package com.example.kostuty.beranda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kostuty.Helper.Constant
import com.example.kostuty.Helper.SharePrefHelper
import com.example.kostuty.Kost.DaftarKostActivity
import com.example.kostuty.Kost.InfoKostPenjual
import com.example.kostuty.UpdateProfileActivity
import com.example.kostuty.User.WelcomeActivity
import com.example.kostuty.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    lateinit var sharePref: SharePrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        sharePref = activity?.let { SharePrefHelper(it) }!!
        var id = sharePref.getString(Constant.PREF_ID)
        Log.e("ID_USER  ", id.toString())

        binding.profileNama.text = sharePref.getString(Constant.PREF_NAMA)
        binding.profileTelepon.text = sharePref.getString(Constant.PREF_TELEPON)
        binding.profileEmail.text = sharePref.getString(Constant.PREF_EMAIL)

        binding.imgEdit.isVisible = true
        binding.imgEdit.isEnabled = true
        binding.imgEdit.setOnClickListener{
            startActivity(Intent(activity, UpdateProfileActivity::class.java))
        }
        binding.crdDaftarkost.setOnClickListener({
            startActivity(Intent(activity, DaftarKostActivity::class.java))
        })

        binding.daftarkost.setOnClickListener {
            sharePref.put(Constant.PREF_ID_KOST,"0")
            startActivity(Intent(activity,InfoKostPenjual::class.java))
        }

        binding.txtLogout.setOnClickListener {
            var intent = Intent(activity,WelcomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        return binding.root
    }
}