package HexalFileNameManager;

import HexalFileNameManager.GUI.FileRenamer;

/**
 * Clase encargada de lanzar la aplicación
 * 
 * @author David Giordana
 *
 */
public class Main {

	/**
	 * Metodo para ejecutar el programa
	 * @param args argumentos pasados en la terminal
	 */
	public static void main(String[] args) {
		FileRenamer fr = new FileRenamer();
		fr.setVisible(true);
	}

}
