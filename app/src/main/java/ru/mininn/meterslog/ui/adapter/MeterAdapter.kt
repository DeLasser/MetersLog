package ru.mininn.meterslog.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.mininn.meterslog.R
import ru.mininn.meterslog.data.model.Meter

class MeterAdapter(var meters: List<Meter>): RecyclerView.Adapter<MeterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeterViewHolder {
        return MeterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_meter_log, parent, false))
    }

    override fun getItemCount(): Int {
        return meters.size
    }

    override fun onBindViewHolder(holder: MeterViewHolder, position: Int) {
        holder.bind(meters[position])
    }

    fun setData(meters: List<Meter>) {
        this.meters = meters
        notifyDataSetChanged()
    }
}