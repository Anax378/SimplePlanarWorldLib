package com.anax.spwlib.example

import com.anax.spwlib.base.{BasicCircleObject, CameraPanAndZoomController}
import com.anax.spwlib.input.{Controller, InputState, InputSubscriber}
import com.anax.spwlib.math.Vec
import com.anax.spwlib.render.Camera
import com.anax.spwlib.thread.{RenderThread, ThreadManager, UpdateThread}
import com.anax.spwlib.window.Window
import com.anax.spwlib.world.World

import java.awt.Color
import java.awt.event.KeyEvent
import scala.util.Random

object Example1 {
	def main(args: Array[String]): Unit = {
		
		val w: Window = new Window(500, 500)
		val player: BasicCircleObject = new BasicCircleObject(Vec(250, 250), 5.0, color=Color.CYAN);
		val random: Random = new Random()
		
		val world: World = new World(){
			override def onTick(tick: Long): Unit = {
				super.onTick(tick)
				this.register(new BasicCircleObject(Vec(), 10.0){
					override def onTick(tick: Long): Unit = {
						super.onTick(tick)
						if(isColliding(player)){this.renderer.color = Color.RED}
						if(position.magnitude() > 1000){removeAfterTick = true}
					}
				}.withVelocity(Vec(random.nextDouble() - 0.5, random.nextDouble() - 0.5).normalize().recover()))
			}
		}
		val threadManager: ThreadManager = new ThreadManager(ThreadManager.TERMINATE_ON_EXIT)
		
		val renderThread = new RenderThread(world, w, threadManager)
		val updateThread = new UpdateThread(threadManager, w, world, world)
		
		
		world.register(player)
		
		val cameraController = new CameraPanAndZoomController(world.camera)
		world.registerController(cameraController)
		world.registerController(new Controller{
			override def handleInput(state: InputState, camera: Camera, tick: Long): Unit = {
				var v = Vec()
				if(state.keysDown(KeyEvent.VK_W)){v += Vec(0, -1)}
				if(state.keysDown(KeyEvent.VK_S)){v += Vec(0, 1)}
				if(state.keysDown(KeyEvent.VK_A)){v += Vec(-1, 0)}
				if(state.keysDown(KeyEvent.VK_D)){v += Vec(1, 0)}
				player.velocity = v.normalize().recover().scale(1.5)
			}
		})
		
		renderThread.start()
		updateThread.start()
		
	}
}
