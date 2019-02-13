package com.dealshot.dealshotandroidapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.dealshot.dealshotandroidapp.ui.adapter.PlazaErrandAdapter
import com.dealshot.dealshotandroidapp.dao.AuthController
import com.dealshot.dealshotandroidapp.ui.adapter.PlazaErrandAdapter
import kotlinx.android.synthetic.main.activity_plaza.*

class PlazaActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plaza)
    plaza_errand_view.adapter = PlazaErrandAdapter()
    plaza_errand_view.layoutManager = LinearLayoutManager(this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    menu.findItem(R.id.action_switch).title = getString(R.string.user_center_tag)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_switch -> {
        finish()
        startActivity(Intent(this, UserCenterActivity::class.java))
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
