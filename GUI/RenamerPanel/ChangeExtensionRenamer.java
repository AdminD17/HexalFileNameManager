package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.FilenameUtils;

import ExtraClass.GUI.JTextFieldHint;
import HexalFileNameManager.GUI.FileTable;
import HexalFileNameManager.GUI.RenamePanel;

/**
 * Panel para renombrar archivos
 * El patron utilizado es el reemplazo de la extension
 * 
 * @author David Giordana
 *
 */
public class ChangeExtensionRenamer extends JPanel implements RenamerPanelInterface, DocumentListener{

	private static final long serialVersionUID = -6997063912831288142L;

	/**
	 * ---- ATTRIBUTES
	 */

	//tabla de archivos
	private FileTable table;

	//componentes
	private JTextFieldHint field;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Constructor de la clase
	 */
	public ChangeExtensionRenamer() {
		super();
		//instancia componentes
		this.table = FileTable.getInstence();
		field = new JTextFieldHint();

		//setea componentes
		this.setLayout(new BorderLayout());
		field.setHint("Extension: ");
		field.getDocument().addDocumentListener(this);
		
		//agrega los componentes al panel
		this.add(field , BorderLayout.NORTH);
	}

	@Override
	public String rename(String str , int size , int index){
		String in = RenamePanel.extractString(field);
		if(in.length() == 0){
			return str;
		}
		if(in.charAt(0) == FilenameUtils.EXTENSION_SEPARATOR){
			in = in.substring(0, in.length());
		}
		return FilenameUtils.getBaseName(str) + FilenameUtils.EXTENSION_SEPARATOR + in;


	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		table.updateNewName();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		table.updateNewName();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}

}
