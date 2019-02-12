package com.dealshot.dealshotandroidapp.ui.menu

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.dealshot.dealshotandroidapp.LoginActivity
import com.dealshot.dealshotandroidapp.R
import com.dealshot.dealshotandroidapp.dao.AuthController

object AppMenuHandler {
  fun handleMenuItem(activity: AppCompatActivity, item: MenuItem): Boolean =
    when (item.itemId) {
      R.id.action_sign_out -> {
        AuthController.signOut()
        activity.finish()
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        true
      }
      else -> false
    }
}