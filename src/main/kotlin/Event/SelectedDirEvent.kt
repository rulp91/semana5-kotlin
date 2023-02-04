package Event

import java.io.File

/**
 * Evento de selección de directorio
 */
class SelectedDirEvent(val file: File, val searchString: String)