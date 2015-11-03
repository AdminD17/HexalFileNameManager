package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ExtraClass.GUI.JTextFieldHint;
import HexalFileNameManager.GUI.FileTable;

/**
 * Panel para renombrar archivos.
 * El patron utilizado es la insercion de un prefijo
 * 
 * @author David Giordana
 *
 */
public class AddPrefixRenamer extends RenamerAbstractPanel implements DocumentListener{

	private static final long serialVersionUID = 8151074486846839862L;

	/**
	 * ---- ATTRIBUTES
	 */

	//campo de entrada
	private JTextFieldHint input;	

	//tabla de archivos
	private FileTable table;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Constructor de la clase
	 */
	public AddPrefixRenamer() {
		super();
		//instancia los objetos de la clase
		this.table = FileTable.getInstence();
		input = new JTextFieldHint();
		input.setHint("Prefijo: ");
		
		//agrega los componentes al panel
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(input , gbc);
		this.add(panel , BorderLayout.NORTH);
		
		//agrega los listeners
		input.getDocument().addDocumentListener(this);
	}

	@Override
	public void rename() {
		ArrayList<String> oldList = table.getOldNameList();
		ArrayList<String> newList = new ArrayList<String>();
		for(String str : oldList){
			newList.add(input.getContent() + str);
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
