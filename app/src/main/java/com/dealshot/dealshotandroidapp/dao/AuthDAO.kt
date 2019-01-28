package com.dealshot.dealshotandroidapp.dao

import com.google.firebase.auth.FirebaseUser

// TODO: Integrate with auth.
object AuthDAO {
  private var user: FirebaseUser? = null

  fun registerUser(newUser: FirebaseUser?) {
    user = newUser
  }

  fun currentOwnerId() = user!!.email
}