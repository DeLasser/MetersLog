package ru.mininn.meterslog.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_meter_log.view.*
import ru.mininn.meterslog.data.model.MeterModel

class MeterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: MeterModel) {
        itemView.text.text = item.toString()
    }

}