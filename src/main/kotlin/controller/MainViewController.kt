package controller

import Event.CloseDirChooserEvent
import com.google.common.eventbus.Subscribe
import com.google.common.io.Resources
import view.DirChooserView
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.ImageIcon
import javax.swing.JFrame

/**
 * Controlador de la vista principal
 */
class MainViewController {
    private val frame = JFrame("Kotlin")

    /**
     * Muestra un JFrame de selección de directorio
     */
    fun ShowDirChooser() {
        val panel = DirChooserView()
        frame.addWindowListener(
            object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent) {
                    System.exit(0)
                }
            }
        )
        val iconKotlin = Resources.getResource("img.png")
        val imageIcon = ImageIcon(iconKotlin).image
        frame.iconImage = imageIcon
        frame.contentPane.add(panel, "Center")
        frame.size =   Dimension(500, 200)
        frame.isVisible = true
    }

    /**
     * Recepciona el evento de final de procesamiento del directorio
     * y cierra JFrame
     * @param event
     */
    @Subscribe
    fun onEvent(event: CloseDirChooserEvent?) {
        frame.dispatchEvent(WindowEvent(frame, WindowEvent.WINDOW_CLOSING))
    }

    companion object {
        var instance: MainViewController? = null
            get() {
                if (field == null) {
                    field = MainViewController()
                }
                return field
            }
            private set
    }
}