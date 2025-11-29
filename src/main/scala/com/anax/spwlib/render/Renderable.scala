package com.anax.spwlib.render

import java.awt.Graphics2D
import java.awt.image.BufferedImage

trait Renderable {
	
	def getRenderer(): Renderer
	
	def renderOnImage(image: BufferedImage, g2d: Graphics2D, camera: Camera, layer: Int): BufferedImage = 
		getRenderer().renderOnImage(image, g2d, camera, layer)
	def renderOnImage(image: BufferedImage, camera: Camera, layer: Int): BufferedImage = getRenderer().renderOnImage(image, camera, layer)
		
}
