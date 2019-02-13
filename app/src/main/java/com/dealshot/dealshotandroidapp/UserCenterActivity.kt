package com.dealshot.dealshotandroidapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.ui.adapter.UserCenterFragmentAdapter
import kotlinx.android.synthetic.main.activity_user_center.*

class UserCenterActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_center)
    container.adapter = UserCenterFragmentAdapter(supportFragmentManager, this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    menu.findItem(R.id.action_switch).title = getString(R.string.plaza_tag)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_switch -> {
        finish()
        startActivity(Intent(this, PlazaActivity::class.java))
        return true
      }
      R.id.action_sign_out -> {
        AuthController.signOut()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
        true
      }
      else -> false
    }
  }
}
