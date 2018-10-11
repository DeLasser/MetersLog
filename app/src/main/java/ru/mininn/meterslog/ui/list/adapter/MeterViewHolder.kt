package ru.mininn.meterslog.ui.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_meter.view.*
import ru.mininn.meterslog.R
import ru.mininn.meterslog.data.model.Meter
import java.util.*

class MeterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: Meter) {
        bindType(item)
        bindValuew(item)
        bindBattary(item)
        bindTemp(item)

    }

    private fun bindTemp(item: Meter) {
        itemView.temp.text = "${item.temperatureValue}"
    }

    private fun bindBattary(item: Meter) {
        itemView.battary.text = "${item.batteryValue}"
    }


    private fun bindValuew(item: Meter) {
        itemView.value.text = String.format(Locale.getDefault(),
                itemView.resources.getString(R.string.meter_reading_gas),
                Math.floor(item.deviceValue!!))
    }

    private fun bindType(meterData: Meter) {
        val resModelText: Int
        val resModelDiameter: Int
        val model: String
        when (meterData.deviceType) {
            GAS -> {
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
            WATER -> {
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

        itemView.type.text = itemView.resources.getString(
                R.string.meter_model_and_sernum,
                model,
                meterData.deviceSerNum)
    }

    companion object {
        const val GAS = 1
        const val WATER = 2
    }

}