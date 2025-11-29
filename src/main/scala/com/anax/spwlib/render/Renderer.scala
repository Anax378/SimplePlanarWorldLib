package com.anax.spwlib.render

import java.awt.Graphics2D
import java.awt.image.BufferedImage

trait Renderer(){
	def renderOnImage(
		                 image: BufferedImage,
		                 g2d: Graphics2D,
		                 camera: Camera,
		                 layer: Int,
	                 ): BufferedImage
	
	def renderOnImage(image: BufferedImage, camera: Camera, layer: Int): BufferedImage = {
		renderOnImage(image, image.createGraphics(), camera, layer)
	}
}
