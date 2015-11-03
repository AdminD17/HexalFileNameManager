package HexalFileNameManager.GUI.RenamerPanel;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.FilenameUtils;

import ExtraClass.GUI.JTextFieldHint;
import HexalFileNameManager.GUI.FileTable;

/**
 * Panel para renombrar archivos
 * El patron utilizado es el reemplazo de la extension
 * 
 * @author David Giordana
 *
 */
public class ChangeExtensionRenamer extends RenamerAbstractPanel implements DocumentListener{

	private static final long serialVersionUID = -6997063912831288142L;

	/**
	 * ---- ATTRIBUTES
	 */
	
	//tabla de archivos
	private FileTable table;
	
	//componentes
	private JTextFieldHint field;
	private JCheckBox check;
	
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
		check = new JCheckBox("Cambiar Extensiones Existentes Solamente");
		
		//setea componentes
		field.setHint("Extension: ");
		field.getDocument().addDocumentListener(this);
		//agrega los componentes al panel
		JPanel components = new JPanel();
		components.setLayout(new BoxLayout(components , BoxLayout.Y_AXIS));
		components.add(field);
		components.add(check);
		this.add(components);
	}

	@Override
	public void rename() {
		if(field.getContent().length() == 0){
			table.setNewNameList(table.getOldNameList());
			return;
		}
		ArrayList<String> oldList = table.getOldNameList();
		ArrayList<String> newList = new ArrayList<String>();
		String ext = field.getContent();
		if(ext.charAt(0) == FilenameUtils.EXTENSION_SEPARATOR){
			ext = ext.substring(0, ext.length());
		}
		for(String str : oldList){
			if(check.isSelected() && FilenameUtils.getExtension(str).length() == 0){
				continue;
			}
			newList.add(FilenameUtils.getBaseName(str) + FilenameUtils.EXTENSION_SEPARATOR + ext);
		}
		table.setNewNameList(newList);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		rename();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		rename();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}

}
