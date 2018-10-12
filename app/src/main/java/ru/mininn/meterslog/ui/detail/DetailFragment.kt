package ru.mininn.meterslog.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.view.*
import ru.mininn.meterslog.R
import ru.mininn.meterslog.data.model.Meter

class DetailFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(arguments!!["meter"] as Meter)
    }

    private fun bindView(item: Meter) {
        view?.device_uid?.text = item.deviceUId
        view?.packet_counter?.text = item.packetCounter.toString()
        view?.packet_version?.text = item.packetVersion.toString()

        view?.device_type?.text = item.deviceType.toString()
        view?.device_model?.text = item.deviceModel.toString()
        view?.device_sernum?.text = item.deviceSerNum.toString()
        view?.device_value?.text = item.deviceValue.toString()

        view?.timestamp?.text = item.timestamp.toString()
        view?.battery_value?.text = item.batteryValue.toString()
        view?.temperature_value?.text = item.temperatureValue.toString()
        view?.package_data?.text = item.packageData
    }
}