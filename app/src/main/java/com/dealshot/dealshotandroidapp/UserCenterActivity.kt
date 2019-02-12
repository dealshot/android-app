package com.dealshot.dealshotandroidapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dealshot.dealshotandroidapp.ui.adapter.UserCenterFragmentAdapter
import com.dealshot.dealshotandroidapp.ui.menu.AppMenuHandler
import kotlinx.android.synthetic.main.activity_user_center.*

class UserCenterActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_center)
    container.adapter = UserCenterFragmentAdapter(supportFragmentManager, this)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean =
    AppMenuHandler.handleMenuItem(this, item)
}
