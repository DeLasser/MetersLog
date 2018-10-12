package ru.mininn.meterslog.ui.list.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mininn.meterslog.R
import ru.mininn.meterslog.data.model.Meter

class MeterAdapter(var meters: List<Meter>): RecyclerView.Adapter<MeterViewHolder>() {
    private var action: OnRecyclerItemClickAction<Meter>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeterViewHolder {
        return MeterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_meter, parent, false))
    }

    override fun getItemCount(): Int {
        return meters.size
    }

    override fun onBindViewHolder(holder: MeterViewHolder, position: Int) {
        holder.bind(meters[position])
        holder.itemView.setOnClickListener {
            action?.execute(meters[position])
        }
    }

    @SuppressLint("CheckResult")
    fun setData(meters: List<Meter>) {
        var size = this.meters.size
        Observable.create<Int> {observable ->
            if (size <= meters.size) {
                observable.onNext(size)
            } else {
                this.meters.forEachIndexed { index, meter ->
                    meters.forEach {
                        if (meter.deviceUId == it.deviceUId) {
                            observable.onNext(index)
                        }
                    }
                }
            }
            observable.onComplete()
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    this.meters = meters
                    if( size <= meters.size) {
                        notifyItemChanged(it)
                    } else {
                        notifyDataSetChanged()
                    }
                }
    }

    fun setOnItemClickAction(action: OnRecyclerItemClickAction<Meter>) {
        this.action = action
    }
}