package ru.mininn.meterslog.ui.list.adapter

interface OnRecyclerItemClickAction<T> {

    fun execute(item: T)
}