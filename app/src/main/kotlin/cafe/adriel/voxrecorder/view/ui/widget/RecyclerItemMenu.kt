package cafe.adriel.voxrecorder.view.ui.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import cafe.adriel.voxrecorder.App
import cafe.adriel.voxrecorder.util.Util
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import org.zakariya.flyoutmenu.FlyoutMenuView

class RecyclerItemMenu {

    class MenuItem(id: Int, val icon: GoogleMaterial.Icon): FlyoutMenuView.MenuItem(id) {

        override fun onDraw(canvas: Canvas?, bounds: RectF?, degreeSelected: Float) {
            val bitmap = IconicsDrawable(App.Companion.instance, icon)
                    .color(if(Util.isDarkTheme()) Color.WHITE else Color.BLACK)
                    .sizeDp(20)
                    .toBitmap()
            canvas!!.drawBitmap(bitmap, bounds!!.centerX() / 2, bounds.centerY() / 2, null)
        }

    }

    class ButtonRenderer(): FlyoutMenuView.ButtonRenderer() {

        override fun onDrawButtonContent(canvas: Canvas?, buttonBounds: RectF?, buttonColor: Int, alpha: Float) {
            val bitmap = IconicsDrawable(App.Companion.instance, GoogleMaterial.Icon.gmd_more_vert)
                    .color(if(Util.isDarkTheme()) Color.WHITE else Color.BLACK)
                    .alpha(128)
                    .sizeDp(18)
                    .toBitmap()
            val center = ((canvas!!.width - bitmap.width) / 2).toFloat()
            canvas.drawBitmap(bitmap, center, center, null)
        }

        override fun onDrawButtonBase(canvas: Canvas?, buttonBounds: RectF?, buttonColor: Int, alpha: Float) {
            // Do nothing
        }

    }

}