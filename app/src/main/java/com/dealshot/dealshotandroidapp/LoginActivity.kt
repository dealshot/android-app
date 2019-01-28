package com.dealshot.dealshotandroidapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dealshot.dealshotandroidapp.dao.AuthDAO
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

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
    createSignInIntent()
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
        AuthDAO.registerUser(FirebaseAuth.getInstance().currentUser)
        startActivity(Intent(this, PlazaActivity::class.java))
        finish()
      } else {
        // TODO: if cannot log in.
        Log.d("app-login", "Cannot login.")
      }
    }
  }
}
