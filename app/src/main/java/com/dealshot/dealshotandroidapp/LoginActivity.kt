package com.dealshot.dealshotandroidapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dealshot.dealshotandroidapp.dao.AuthController

/** A login screen that offers login via email/password. */
class LoginActivity : AppCompatActivity() {
  companion object {
    private const val RC_SIGN_IN = 100
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    createSignInIntent()
  }

  private fun createSignInIntent() {
    startActivityForResult(
      AuthController
        .createSignInIntentBuilder()
        .build(),
      RC_SIGN_IN
    )
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == RC_SIGN_IN) {
      if (resultCode == Activity.RESULT_OK) {
        finish()
        startActivity(Intent(this, PlazaActivity::class.java))
      } else {
        Toast.makeText(
          this,
          getString(R.string.cannot_login_warning),
          Toast.LENGTH_SHORT
        ).show()
      }
    }
  }
}
