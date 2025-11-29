package com.anax.spwlib.render

import com.anax.spwlib.math.Vec

class Camera(var position: Vec, var scale: Double) {
	
	def toScreenSpace(point: Vec): Vec = point.subtract(position).scale(scale)
	def toWorldSpace(point: Vec): Vec = position.add(point.scale(1.0/scale))
	
	def toScreenScale(vec: Vec): Vec = vec.scale(scale)
	def toWorldScale(vec: Vec): Vec = vec.scale(1.0/scale)
	
	def toScreenScale(num: Double): Double = num * scale
	def toWorldScale(num: Double): Double = num / scale
	
}
