package com.dealshot.dealshotandroidapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser

class PlazaActivity : AppCompatActivity() {
  companion object {
    const val ARGS_USER = "PLAZA_USER"
  }

  private var user: FirebaseUser? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    user = savedInstanceState!!.getParcelable(ARGS_USER)
  }

}