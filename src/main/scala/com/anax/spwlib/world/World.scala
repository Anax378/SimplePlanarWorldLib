package com.anax.spwlib.world

import com.anax.spwlib.input.{Controller, InputState, InputSubscriber}
import com.anax.spwlib.math.Vec
import com.anax.spwlib.render.{Camera, RenderProvider, Renderable, Renderer}

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.renderable.RenderableImage
import java.util
import scala.collection.mutable

class World extends RenderProvider with InputSubscriber with Tickable {
	var layers: Int = 2
	
	val tickable: mutable.HashSet[Tickable] = new mutable.HashSet[Tickable]()
	val renderable: mutable.HashSet[Renderable] = new mutable.HashSet[Renderable]()
	val controllers: mutable.HashSet[Controller] = new mutable.HashSet[Controller]()
	
	val camera: Camera = new Camera(Vec(0, 0), 1)
	
	def withLayers(layers: Int): World = {
		this.layers = layers
		this
	}
	
	def register(o: Object): Unit = {
		val m = Option(o)
		m.collect({case t: Tickable => t}).foreach(t => registerTickable(t))
		m.collect({case r: Renderable => r}).foreach(r => registerRenderable(r))
		m.collect({case i: Controller => i}).foreach(i => registerController(i))
	}
	
	def registerAll(obs: IterableOnce[Object]): Unit = {
		obs.iterator.foreach(o => register(o))
	}
	
	def registerTickable(t: Tickable): Unit = {
		Option(t).foreach(t => tickable.add(t))
	}
	
	def registerRenderable(r: Renderable): Unit = {
		Option(r).foreach(r => renderable.add(r))
	}
	
	def registerController(i: Controller): Unit = {
		Option(i).foreach(i => controllers.add(i))
	}
	
	def unregister(o: Object): Unit = {
		val m = Option(o)
		m.collect({case t: Tickable => t}).foreach(t => tickable.remove(t))
		m.collect({case r: Renderable => r}).foreach(r => renderable.remove(r))
		m.collect({case i: Controller => i}).foreach(i => controllers.remove(i))
	}
	
	override def renderOnImage(image: BufferedImage, g2d: Graphics2D): BufferedImage = {
		(0 until layers).foreach(l =>
			renderable.foreach(r => {
				r.renderOnImage(image, g2d, camera, l)
			})
		)
		image
	}
	
	override def handleInput(state: InputState, tick: Long): Unit = {
		controllers.foreach(i => i.handleInput(state, camera, tick))
	}
	override def onTick(tick: Long): Unit = {
		val toRemove = new util.LinkedList[Tickable]()
		tickable.foreach(t => {
			t.onTick(tick)
			if(t.removeAfterTick){toRemove.add(t)}
		})
		toRemove.forEach(t => unregister(t))
	}
}
