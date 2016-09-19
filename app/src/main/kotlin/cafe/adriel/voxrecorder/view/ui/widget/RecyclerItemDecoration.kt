package cafe.adriel.voxrecorder.view.ui.widget

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.util.drawable

class RecyclerItemDecoration : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val divider = drawable(R.drawable.list_item_divider)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0..parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

}