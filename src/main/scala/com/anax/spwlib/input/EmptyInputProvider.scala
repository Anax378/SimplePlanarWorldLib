package com.anax.spwlib.input

object EmptyInputProvider extends InputProvider{
	private val emptyInputState = new InputState()
	override def getInputState: InputState = emptyInputState
}
