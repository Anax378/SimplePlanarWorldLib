package com.anax.spwlib.render

import java.awt.image.BufferedImage

trait RenderSubscriber {
	def provideImage(image: BufferedImage): Unit
	def getDimensions(): (Int, Int)
}
