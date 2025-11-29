package com.anax.spwlib.thread

import com.anax.spwlib.render.{RenderProvider, RenderSubscriber, Renderable}

import java.awt.{Color, Graphics2D}
import java.awt.image.BufferedImage

class RenderThread(provider: RenderProvider, subscriber: RenderSubscriber, manager: ThreadManager) extends Thread {
	
	override def run(): Unit = {
		manager.register()
		var (width, height) = subscriber.getDimensions()
		
		var image: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
		var buffer: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
		
		while(manager.running){
			val (w, h) = subscriber.getDimensions()
			if((width, height) != (w, h)){
				width = w
				height = h
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
				buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
			}
			
			val temp = image
			image = buffer
			buffer = temp
			
			val g2d: Graphics2D = image.createGraphics()
			g2d.setPaint(Color.BLACK)
			g2d.fillRect(0, 0, width, height)
			subscriber.provideImage(provider.renderOnImage(image, g2d))
			g2d.dispose()
			
		}
		manager.deregister()
	}
}
