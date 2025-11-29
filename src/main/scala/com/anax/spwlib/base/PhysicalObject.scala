package com.anax.spwlib.base

import com.anax.spwlib.math.Vec

trait PhysicalObject {
	
	def getPosition: Vec
	def setPosition(position: Vec): Unit
	def isInside(position: Vec): Boolean
	
}
