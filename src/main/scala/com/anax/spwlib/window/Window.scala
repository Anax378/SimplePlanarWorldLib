package com.anax.spwlib.window

import com.anax.spwlib.input.{InputProvider, InputState}
import com.anax.spwlib.render.RenderSubscriber

import java.awt.AWTEvent
import java.awt.event.*
import java.awt.image.BufferedImage
import java.util.concurrent.ConcurrentHashMap
import javax.swing.*

class Window(val width: Int, val height: Int) extends InputProvider with RenderSubscriber {
	
	val inputState: InputState = new InputState()
	val label: JLabel = new JLabel()
	val panel: JPanel = new JPanel()
	val frame: JFrame = new JFrame()
	panel.add(label)
	frame.add(panel)
	
	frame.addKeyListener(new KeyAdapter {
		override def keyPressed(e: KeyEvent): Unit = {
			inputState.keysDown(e.getKeyCode) =  true
			inputState.unprocessedKeyPresses(e.getKeyCode) =  true
			super.keyPressed(e);
		}
	 
		override def keyReleased(e: KeyEvent): Unit = {
			inputState.keysDown(e.getKeyCode) = false
			super.keyReleased(e)
		}
	})
	
	label.addMouseListener(new MouseAdapter {
		override def mouseEntered(e: MouseEvent): Unit = {
			inputState.isMouseOnWindow = true
		}
		
		override def mouseExited(e: MouseEvent): Unit = {
			inputState.isMouseOnWindow = false
		}
		
		override def mousePressed(e: MouseEvent): Unit = {
			println(e.getButton)
			inputState.mouseButtonsDown(e.getButton) = true
			inputState.unprocessedMouseButtonPresses(e.getButton) = true
			super.mousePressed(e)
		}
	 
		override def mouseReleased(e: MouseEvent): Unit = {
			inputState.mouseButtonsDown(e.getButton) = false
			super.mouseReleased(e)
		}
	})
	
	label.addMouseWheelListener(new MouseAdapter {
		override def mouseWheelMoved(e: MouseWheelEvent): Unit = {
			inputState.unprocessedMouseScroll += e.getUnitsToScroll
		}
	})
	
	
	label.addMouseMotionListener(new MouseAdapter {
	
		override def mouseMoved(e: MouseEvent): Unit = {
			val dx = e.getX - inputState.lastMouseX
			val dy = e.getY - inputState.lastMouseY
			inputState.lastMouseX = e.getX
			inputState.lastMouseY = e.getY
			
			inputState.unprocessedMouseMovementX += dx
			inputState.unprocessedMouseMovementY += dy
		}
		
		override def mouseDragged(e: MouseEvent): Unit = {
			val dx = e.getX - inputState.lastMouseX
			val dy = e.getY - inputState.lastMouseY
			inputState.lastMouseX = e.getX
			inputState.lastMouseY = e.getY
			
			inputState.unprocessedMouseMovementX += dx
			inputState.unprocessedMouseMovementY += dy
		}
	})
	
	frame.addFocusListener(new FocusAdapter {
		override def focusGained(e: FocusEvent): Unit = {
			inputState.hasFocus = true
		}
		
		override def focusLost(e: FocusEvent): Unit = {
			inputState.hasFocus = false
		}
	})
	

	frame.setSize(width, height)
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
	frame.setResizable(true)
	frame.setVisible(true)
	
	inputState.hasFocus = frame.hasFocus

	override def provideImage(image: BufferedImage): Unit = {
		label.setIcon(new ImageIcon(image))
		SwingUtilities.updateComponentTreeUI(frame)
	}
	
	override def getInputState: InputState = inputState
	override def getDimensions(): (Int, Int) = (frame.getWidth, frame.getHeight)
	
}