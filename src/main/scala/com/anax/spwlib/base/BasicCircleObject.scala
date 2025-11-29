package com.anax.spwlib.base

import com.anax.spwlib.math.Vec
import com.anax.spwlib.render.{Renderable, Renderer}
import com.anax.spwlib.world.Tickable

import java.awt.Color

class BasicCircleObject(@volatile var position: Vec, var radius: Double, color: Color = Color.WHITE, var fill: Boolean = false) extends PhysicalObject with HasRadius with Renderable with Tickable{
	val renderer: CircleRenderer = new CircleRenderer(this, fill, color)
	@volatile
	var velocity: Vec = Vec()
	
	def withVelocity(velocity: Vec): BasicCircleObject = {
		this.velocity = velocity
		this
	}
	
	override def getPosition: Vec = position
	override def setPosition(position: Vec): Unit = {
		this.position = position
	}
	
	def isColliding(other: PhysicalObject with HasRadius): Boolean = this.position.vecTo(other.getPosition).magnitude() <= radius+other.getRadius
	
	override def isInside(position: Vec): Boolean = position.vecTo(this.position).magnitude() <= radius
	
	override def getRadius: Double = radius
	
	override def getRenderer(): Renderer = renderer
	
	override def onTick(tick: Long): Unit = {
		this.position += velocity
	}
}
