package view

import Event.SelectedDirEvent
import com.google.common.eventbus.EventBus
import controller.DirProcessController
import org.springframework.beans.factory.annotation.Autowired
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class DirChooserView : JPanel(), ActionListener, DocumentListener {
    @Autowired
    private val eventbus = EventBus()
    private val tf: JTextField = JTextField()
    private val l: JLabel = JLabel()

    init {
        initView()
        eventbus.register(DirProcessController.instance)
    }

    /**
     * Inicializa la vista
     */
    private fun initView() {
        this.background = Color(235,244,251)
        this.border = BorderFactory.createTitledBorder("Selección de directorio con Kotlin")
        l.setText("<html>Introduzca la cadena de b&uacute;squeda y a continuaci&oacute;seleccione el directorio<html>")
        l.setBounds(10, 20, 430, 40)
        tf.setText("wax synthase")
        tf.setBounds(10, 60, 410, 20)
        tf.getDocument().addDocumentListener(this)
        val go = JButton("Seleccione el directorio")
        go.setBounds(220, 100, 200, 30)
        go.addActionListener(this)
        add(tf)
        add(l)
        add(go)
        setLayout(null)
        setVisible(true)
    }

    override fun actionPerformed(e: ActionEvent) {
        val chooser = JFileChooser()
        chooser.setCurrentDirectory(File("."))
        chooser.setDialogTitle("Seleccione el directorio a analizar")
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
        chooser.setAcceptAllFileFilterUsed(false)
        val selectedOption: Int = chooser.showOpenDialog(this)
        val searchString: String = tf.getText().trim { it <= ' ' }
        if (selectedOption == JFileChooser.APPROVE_OPTION && !searchString.isEmpty()) eventbus.post(
            SelectedDirEvent(
                chooser.getSelectedFile(),
                searchString
            )
        )
    }

    override fun insertUpdate(e: DocumentEvent) {
        showWarningIfNeedle()
    }

    override fun removeUpdate(e: DocumentEvent) {
        showWarningIfNeedle()
    }

    override fun changedUpdate(e: DocumentEvent) {
        showWarningIfNeedle()
    }

    /**
     * Muestra una advertencia en caso de que sea necesario
     */
    private fun showWarningIfNeedle() {
        if (tf.getText().trim { it <= ' ' }
                .isEmpty()) l.setText("<html>Introduzca la <strong><font color='red'>cadena de b&uacute;squeda</font><strong> y a continuaci&oacute;seleccione el directorio<html>") else l.setText(
            "<html>Introduzca la cadena de b&uacute;squeda y a continuaci&oacute;seleccione el directorio<html>"
        )
        this.setLayout(null)
    }
}