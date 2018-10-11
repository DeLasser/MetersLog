package ru.mininn.meterslog.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import ru.mininn.meterslog.R
import ru.mininn.meterslog.data.model.Meter
import ru.mininn.meterslog.ui.detail.DetailFragment
import ru.mininn.meterslog.ui.list.adapter.MeterAdapter
import ru.mininn.meterslog.ui.list.adapter.OnRecyclerItemClickAction

class ListFragment: Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var meterAdapter: MeterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initDataUpdate()
    }

    private fun initView(view: View) {
        recyclerView = view.recycler_view
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        meterAdapter = MeterAdapter(viewModel.metersLiveData.value!!)
        meterAdapter.setOnItemClickAction(object : OnRecyclerItemClickAction<Meter> {
            override fun execute(item: Meter) {
                startDetailFragment(item)
            }

        })
        recyclerView.adapter = meterAdapter
        if(viewModel.statusLiveData.value!!) {
            view.button.setBackgroundColor(this.resources.getColor(R.color.stopScanColor))
            view.button.setText(R.string.stop_scan)
        } else {
            view.button.setBackgroundColor(this.resources.getColor(R.color.startScanColor))
            view.button.setText(R.string.start_scan)
        }
        view.button.setOnClickListener {
            viewModel.statusLiveData.value = !viewModel.statusLiveData.value!!
        }
    }

    private fun initDataUpdate() {
        viewModel.subscribeForUpdates(this, Observer {
            meterAdapter.setData(it!!)
        })

        viewModel.statusLiveData.observe(this, Observer {
            if (it!!) {
                view?.button?.setBackgroundColor(this.resources.getColor(R.color.stopScanColor))
                view?.button?.setText(R.string.stop_scan)
                viewModel.startScan()
            } else {
                this.button.setBackgroundColor(this.resources.getColor(R.color.startScanColor))
                this.button.setText(R.string.start_scan)
                viewModel.stopScan()
            }
        })
    }

    private fun startDetailFragment(item: Meter) {
        val bundle = Bundle()
        val fragment = DetailFragment()
        bundle.putParcelable("meter", item)
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.container, fragment)
                ?.addToBackStack("")
                ?.commit()
    }
}