package com.anax.spwlib.base

import com.anax.spwlib.render.{Camera, Renderer}

import java.awt.{Color, Graphics2D}
import java.awt.image.BufferedImage

class CircleRenderer(
	                    subject: PhysicalObject with HasRadius,
	                    var fill: Boolean = false,
	                    var color: Color = Color.WHITE,
                    ) extends Renderer {
	
	override def renderOnImage(image: BufferedImage, g2d: Graphics2D, camera: Camera, layer: Int): BufferedImage = {
		val renderPos = camera.toScreenSpace(subject.getPosition)
		val renderX = renderPos.roundX()
		val renderY = renderPos.roundY()
		val renderRadius: Int = Math.round(camera.toScreenScale(subject.getRadius)).toInt
		
		g2d.setPaint(color)
		if(fill){
			g2d.fillOval(renderX - renderRadius, renderY - renderRadius, renderRadius*2, renderRadius*2)
		}else{
			g2d.drawOval(renderX - renderRadius, renderY - renderRadius, renderRadius * 2, renderRadius * 2)
		}
		
		image
	}
}
