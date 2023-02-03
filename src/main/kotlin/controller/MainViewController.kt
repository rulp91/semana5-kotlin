package controller

import Event.CloseDirChooserEvent
import com.google.common.eventbus.Subscribe
import view.DirChooserView
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.util.*
import javax.swing.JFrame

/**
 * Controlador de la vista principal
 */
class MainViewController {
    private val frame = JFrame("")

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
        frame.contentPane.add(panel, "Center")
        frame.size =   Dimension(400, 200)
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