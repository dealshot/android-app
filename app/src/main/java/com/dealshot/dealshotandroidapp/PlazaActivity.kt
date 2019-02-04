package com.dealshot.dealshotandroidapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.fragment.PlazaFragment
import com.dealshot.dealshotandroidapp.fragment.UserCenterSectionFragment

class PlazaActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plaza)

    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, PlazaFragment.newInstance())
      .commit()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    menu.findItem(R.id.action_switch_segment).title = getString(R.string.user_center_tag)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_switch_segment -> {
        startActivity(Intent(this, UserCenterActivity::class.java))
        finish()
        return true
      }
      R.id.action_sign_out -> {
        AuthController.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        return true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
