package ru.mininn.meterslog.ui

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.mininn.meterslog.R
import ru.mininn.meterslog.ui.adapter.MeterAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var meterAdapter: MeterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reqestPermissions()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initView()
        initDataUpdate()
    }

    private fun initView() {
        recyclerView = this.recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        meterAdapter = MeterAdapter(viewModel.metersLiveData.value!!)
        recyclerView.adapter = meterAdapter
        if(viewModel.statusLiveData.value!!) {
            this.button.setBackgroundColor(this.resources.getColor(R.color.stopScanColor))
            this.button.setText(R.string.stop_scan)
        } else {
            this.button.setBackgroundColor(this.resources.getColor(R.color.startScanColor))
            this.button.setText(R.string.start_scan)
        }
        this.button.setOnClickListener {
            viewModel.statusLiveData.value = !viewModel.statusLiveData.value!!
        }
    }

    private fun initDataUpdate() {
        viewModel.metersLiveData.observe(this, Observer {
            meterAdapter.setData(it!!)
        })

        viewModel.statusLiveData.observe(this, Observer {
            if (it!!) {
                this.button.setBackgroundColor(this.resources.getColor(R.color.stopScanColor))
                this.button.setText(R.string.stop_scan)
                viewModel.startScan()
            } else {
                this.button.setBackgroundColor(this.resources.getColor(R.color.startScanColor))
                this.button.setText(R.string.start_scan)
                viewModel.stopScan()
            }
        })
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

}
