package ru.mininn.meterslog.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import ru.mininn.meterslog.R
import ru.mininn.meterslog.ui.list.ListFragment

class MainActivity : AppCompatActivity() {
    private val fragmentManager = this.supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reqestPermissions()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, ListFragment())
//                    .addToBackStack("list")
                    .commit()
        }
    }

    private fun reqestPermissions () {
        if (!checkLocationPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            }
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        }
    }

}
