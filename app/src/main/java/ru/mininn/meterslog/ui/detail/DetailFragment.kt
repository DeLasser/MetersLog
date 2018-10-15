package ru.mininn.meterslog.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.view.*
import ru.mininn.meterslog.R
import ru.mininn.meterslog.data.model.Meter
import ru.mininn.meterslog.ui.list.adapter.MeterViewHolder
import java.util.*

class DetailFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(arguments!!["meter"] as Meter)
    }

    private fun bindView(item: Meter) {
        if (view != null) {
            bindType(item, view!!)
            view?.device_uid?.text = item.deviceUId
            view?.packet_counter?.text = item.packetCounter.toString()
            view?.packet_version?.text = item.packetVersion.toString()

            view?.device_type?.text = item.deviceType.toString()
            view?.device_model?.text = item.deviceModel.toString()
            view?.device_sernum?.text = item.deviceSerNum.toString()
            view?.device_value?.text = item.deviceValue.toString()

            view?.timestamp?.text = Date(item.timestamp).toString()
            view?.battery_value?.text = item.batteryValue.toString()
            view?.temperature_value?.text = item.temperatureValue.toString()
            view?.package_data?.text = item.packageData
        }
    }

    private fun bindType(meterData: Meter, itemView: View) {
        val resModelText: Int
        val resModelDiameter: Int
        val model: String
        when (meterData.deviceType) {
            MeterViewHolder.GAS -> {
                when {
                    meterData.deviceModel < 15 -> {
                        resModelText = R.string.dev_gas_name
                        resModelDiameter = R.array.gas_kind_names
                    }
                    meterData.deviceModel < 30 -> {
                        resModelText = R.string.dev_gas_dl_name
                        resModelDiameter = R.array.gas_kind_names
                    }
                    meterData.deviceModel < 45 -> {
                        resModelText = R.string.dev_gas_ultra_name
                        resModelDiameter = R.array.gas_ultra_kind_name
                    }
                    else -> {
                        resModelText = R.string.empty
                        resModelDiameter = R.array.empty_kind_names
                    }
                }

            }
            MeterViewHolder.WATER -> {
                resModelText = R.string.dev_water_name

                resModelDiameter = R.array.water_kind_names
            }
            else -> {
                resModelText = R.string.empty

                resModelDiameter = R.array.empty_kind_names
            }
        }
        model = itemView.resources.getString(R.string.dev_name,
                itemView.resources.getString(resModelText),
                try {
                    itemView.resources.getStringArray(resModelDiameter)[meterData.deviceModel % 15 - 1]
                } catch (e: ArrayIndexOutOfBoundsException) {
                    itemView.resources.getString(R.string.empty)
                })

        itemView.device_name.text = itemView.resources.getString(
                R.string.meter_model_and_sernum,
                model,
                meterData.deviceSerNum)
    }
}