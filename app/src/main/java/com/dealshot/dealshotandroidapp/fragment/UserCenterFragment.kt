package com.dealshot.dealshotandroidapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dealshot.dealshotandroidapp.R
import com.google.firebase.auth.FirebaseUser

class UserCenterFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_user_center, container, false)
    bindView(view)
    return view
  }

  private fun bindView(view: View?) {

  }

  companion object {
    @JvmStatic
    fun newInstance() = PlazaFragment()
  }
}