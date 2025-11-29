package com.anax.spwlib.math

import scala.annotation.targetName

case class Vec(val x: Double = 0.0, val y: Double = 0.0) {
	def add(other: Vec): Vec = Vec(x+other.x, y+other.y)
	@targetName("plus") def +(other: Vec): Vec = add(other)
	def subtract(other: Vec): Vec = Vec(x-other.x, y-other.y)
	@targetName("minus") def -(other: Vec): Vec = subtract(other)
	def scale(factor: Double): Vec = Vec(x*factor, y*factor)
	def scaleTo(length: Double): Vec = normalize().scale(length)
	@targetName("multiply") def *(factor: Double): Vec = scale(factor)
	@targetName("divide") def /(factor: Double): Vec = scale(1.0/factor)
	def dot(other: Vec): Double = other.x*x + other.y*y
	@targetName("product") def *(other: Vec): Double = dot(other)
	def magnitude(): Double = Math.sqrt(dot(this))
	def normalize(): Vec = scale(1.0/magnitude())
	def vecTo(other: Vec): Vec = other.subtract(this)
	def recover(): Vec = Vec(if x.isNaN then 0.0 else x, if y.isNaN then 0.0 else y)
	def cap(max: Double) = if magnitude() <= max then this else scaleTo(max)
	def rotate(angle: Double): Vec = {
		val sin = Math.sin(angle)
		val cos = Math.cos(angle)
		Vec(x*cos - y*sin, x*sin + y*cos)
	}
	def roundX(): Int = Math.round(x).toInt
	def roundY(): Int = Math.round(y).toInt
}
