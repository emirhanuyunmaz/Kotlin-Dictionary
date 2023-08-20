package com.emirhanuyunmaz.kotlindictionary

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

open class SwipeController(val context:Context) :ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteCalor=ContextCompat.getColor(context, androidx.appcompat.R.color.error_color_material_dark)
    private val updateColor=ContextCompat.getColor(context, androidx.appcompat.R.color.material_blue_grey_900)
    private val deleteIcon=R.drawable.delete_icon
    private val updateIcon=R.drawable.upload_icon

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        recyclerView.addOnScrollListener(object: OnScrollListener() {

        })

        RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
            .addSwipeRightLabel("")
            .addSwipeRightBackgroundColor(updateColor)
            .addSwipeRightActionIcon(updateIcon)
            .addSwipeLeftLabel("")
            .addSwipeLeftBackgroundColor(deleteCalor)
            .addSwipeLeftActionIcon(deleteIcon)
            .create()
            .decorate()


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}