package HexalFileNameManager.GUI.RenamerPanel;

import javax.swing.JPanel;

/**
 * Clase abstracta que extiende de un @See JPanel que contiene los
 * metodos y atributos necesarios para renombrar archivos
 * 
 * @author David Giordana
 *
 */
public abstract class RenamerAbstractPanel extends JPanel {

	private static final long serialVersionUID = 2337765671710528056L;
	
	/**
	 * ---- ABSTRACT METHODS
	 */
	
	/**
	 * renombra los archivos de una tabla
	 */
	public abstract void rename();
	
}
