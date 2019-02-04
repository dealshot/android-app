package com.dealshot.dealshotandroidapp.dao

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

object AuthController {
  private val auth = FirebaseAuth.getInstance()

  private val providers = arrayListOf(
    AuthUI.IdpConfig.EmailBuilder().build(),
    AuthUI.IdpConfig.PhoneBuilder().build(),
    AuthUI.IdpConfig.GoogleBuilder().build()
  )

  fun createSignInIntentBuilder() =
    AuthUI.getInstance()
      .createSignInIntentBuilder()
      .setIsSmartLockEnabled(false)
      .setAvailableProviders(providers)

  fun signOut() = auth.signOut()

  fun currentUID(): String = auth.currentUser!!.uid

  fun hasUser() = auth.currentUser != null

  fun addAuthStateListener(listener: (FirebaseAuth) -> Unit) {
    auth.addAuthStateListener(listener)
  }
}
