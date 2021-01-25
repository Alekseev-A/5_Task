package com.example.weatherrx.ui.cities.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class ItemTouchHelperCallback(
    private val onItemMove: (from: Int, to: Int) -> Unit,
    private val onItemDismiss: (Int) -> Unit,
    private val onDrag: (Boolean) -> Unit,
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.DOWN or ItemTouchHelper.UP,
    ItemTouchHelper.END
) {

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            onDrag(false)
        } else if (actionState == ItemTouchHelper.DOWN) {
            onDrag(true)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemDismiss(viewHolder.adapterPosition)
    }
}