package org.ieselcaminas.valentin.managesextinguisher.ComponentsTabPager

import android.view.View
import androidx.viewpager.widget.ViewPager

class ZoomPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.getWidth()
        val pageHeight = view.getHeight()

        if (position < -1) { // [-Infinito,-1)

            // Esta página desaparece de la pantalla hacia la izquierda.
            view.setAlpha(0F)

        } else if (position <= 1) { // [-1,1]

            // Modifica la transición deslizante encogiendo la página
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val horzMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2)
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2)
            }
            // Devuelve la página a su tamaño normal
            view.setScaleX(scaleFactor)
            view.setScaleY(scaleFactor)
            // Aparece la página con su tamaño normal
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA))

        } else { // (1,+Infinity]
            // Esta página desaparece de la pantalla hacia la derecha.
            view.setAlpha(0F)
        }
    }

    companion object {
        private val MIN_SCALE = 0.85f
        private val MIN_ALPHA = 0.5f
    }
}