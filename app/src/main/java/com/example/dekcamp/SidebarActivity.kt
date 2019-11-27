package com.example.dekcamp

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.dekcamp.data.User
import com.example.dekcamp.data.Util
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class SidebarActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sidebar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        Util.currentUser.observe(this, Observer<User> {
            val headerView = navView.getHeaderView(0)
            val imgHead = headerView.findViewById<ImageView>(R.id.imageView)
            val nameText = headerView.findViewById<TextView>(R.id.name_textView)
            val emailText = headerView.findViewById<TextView>(R.id.email_textView)

            Log.i("currentUser", "$it")

            if (it.image == "") {
//            imgHead.setImageResource(R.drawable.shrimp)
            }
            else {
                val err: Drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_error_black_24dp)!!
                DrawableCompat.setTint(
                    DrawableCompat.wrap(err),
                    ContextCompat.getColor(applicationContext, R.color.colorAccent)
                )
                Picasso.with(applicationContext).load(it.image).transform(CropCircleTransformation()).error(err).into(imgHead)
            }

            nameText.text = "${it.firstname} ${it.lastname}"
            emailText.text = it.email

        })



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.sidebar, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
