package HexalFileNameManager.GUI.RenamerPanel;

/**
 * Interfaz con los metodos necesarios para renombrar archivos
 * 
 * @author David Giordana
 *
 */
public interface RenamerPanelInterface {
		
	/**
	 * Renombra el nombre a un archivo
	 * @param str Cadena a renombrar
	 * @param size Tamaño de la lista 
	 * @param index Indice de al lista
	 * @return Texto renombrado
	 */
	public abstract String rename(String str , int size , int index);
	
}
