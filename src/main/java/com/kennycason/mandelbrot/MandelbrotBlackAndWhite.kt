package com.kennycason.mandelbrot

import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

/**
 * Created by kenny on 11/3/16.
 */

fun main(args: Array<String>) {
    ManderboltBlackAndWhite().run()
}

class ManderboltBlackAndWhite {
    val max = 100
    val width = 640
    val height = 480
    val canvas: BufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

    fun run() {
        val frame = JFrame()
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.setSize(width, height + 18)
        frame.setVisible(true)

        val panel = object: JPanel() {
            override fun paintComponent(graphics: Graphics) {
                super.paintComponent(graphics)

                graphics.drawImage(canvas, 0, 0, width, height, null)
            }

        }
        frame.add(panel)
        panel.revalidate()

        (0 .. max).forEach { max ->
            drawManderbolt(max)
            Thread.sleep(100)
            panel.repaint()
            println(max)
            ImageIO.write(canvas, "png", File("output/iteration_$max.png"))
        }
    }


    // x_k+1 = x_k^2 - y_k^2 + Re c
    // y+k+1 = 2 x_k * y_k + Im c
    private fun drawManderbolt(max: Int) {
        for (row in 0..height - 1) {
            for (col in 0..width - 1) {
                val c_re = (col - width / 2.0) * 4.0 / width
                val c_im = (row - height / 2.0) * 4.0 / height
                var x = 0.0
                var y = 0.0
                var iteration = 0
                while (x * x + y * y <= 4 && iteration < max) {
                    val x_new = x * x - y * y + c_re
                    y = 2.0 * x * y + c_im
                    x = x_new
                    iteration++
                }
                if (iteration < max) {
                    canvas.setRGB(col, row, Color.WHITE.rgb)
                } else {
                    canvas.setRGB(col, row, Color.BLACK.rgb)
                }
            }
        }
    }
}