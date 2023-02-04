package controller

import Event.CloseDirChooserEvent
import Event.SelectedDirEvent
import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import org.springframework.beans.factory.annotation.Autowired
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files

/**
 * Controlador singleton de procesado de directorios
 */
class DirProcessController private constructor() {
    @Autowired
    private val eventbus = EventBus()
    private var searchString: String? = null

    init {
        eventbus.register(MainViewController.instance)
    }

    /**
     * Recepciona el evento de selección de un directorio
     * y lanza su procesamiento
     * @param event
     */
    @Subscribe
    fun onEvent(event: SelectedDirEvent) {
        try {
            searchString = event.searchString
            loopIterativelyDir(event.file)
            eventbus.post(CloseDirChooserEvent())
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Recorre de forma iterativa el arbol de directorios y busca las coincidencias
     * @param file
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun loopIterativelyDir(file: File) {
        if (file.isDirectory) {
            val files = file.listFiles()
            for (currentFile in files) {
                if (currentFile.isDirectory)
                    loopIterativelyDir(currentFile)
                if (isTextFile(currentFile))
                    buscar(currentFile, searchString)
            }
        }
        if (isTextFile(file))
            buscar(file, searchString)
    }

    /**
     * Función demandada por el ejercicio
     * @param file
     * @param searchString
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun buscar(file: File, searchString: String?) {

        var dirIsPrinted = false
        BufferedReader(FileReader(file)).use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                if (line?.contains(searchString!!) == true) {
                    if (!dirIsPrinted) {
                        println(file.absolutePath)
                        dirIsPrinted = true
                    }
                    println("\t" + line)
                }
            }
        }
    }

    /**
     * Retorna si el fichero es un fichero de texto
     * @param file
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun isTextFile(file: File): Boolean {
        val mimeType = Files.probeContentType(file.absoluteFile.toPath())
        return "text/plain".equals(mimeType, ignoreCase = true)
    }

    companion object {
        var instance: DirProcessController? = null
            /**
             * Singleton del controlador
             * @return
             */
            get() {
                if (field == null) field = DirProcessController()
                return field
            }
            private set
    }
}