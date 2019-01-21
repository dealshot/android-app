package com.dealshot.dealshotandroidapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class MainActivity : AppCompatActivity() {

  companion object {
    private const val RC_SIGN_IN = 100
  }

  private val providers = arrayListOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.PhoneBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    join_app_button.setOnClickListener { createSignInIntent() }
  }

  private fun createSignInIntent() {
    startActivityForResult(
      AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build(),
      RC_SIGN_IN
    )
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == RC_SIGN_IN) {
      if (resultCode == Activity.RESULT_OK) {
        val user = FirebaseAuth.getInstance().currentUser
      } else {
        // TODO (@xinx): if cannot log in.
      }
    }
  }
}
