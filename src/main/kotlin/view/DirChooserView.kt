package view

import Event.SelectedDirEvent
import com.google.common.eventbus.EventBus
import controller.DirProcessController
import org.springframework.beans.factory.annotation.Autowired
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel

class DirChooserView : JPanel(), ActionListener {
    @Autowired
    private val eventbus = EventBus()

    init {
        val go = JButton("Seleccione el directorio")
        go.addActionListener(this)
        add(go)
        eventbus.register(DirProcessController.instance)
    }

    override fun actionPerformed(e: ActionEvent) {
        val chooser = JFileChooser()
        chooser.setCurrentDirectory(File("."))
        chooser.setDialogTitle("Seleccione el directorio a analizar")
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
        chooser.setAcceptAllFileFilterUsed(false)
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) eventbus.post(SelectedDirEvent(chooser.getSelectedFile()))
    }

}