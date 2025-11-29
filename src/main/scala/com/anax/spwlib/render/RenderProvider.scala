package com.anax.spwlib.render

import java.awt.Graphics2D
import java.awt.image.BufferedImage

trait RenderProvider {
	def renderOnImage(image: BufferedImage, g2d: Graphics2D): BufferedImage
}
