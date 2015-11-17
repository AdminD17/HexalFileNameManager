package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.FilenameUtils;

import ExtraClass.GUI.JTextFieldHint;
import HexalFileNameManager.GUI.FileTable;
import HexalFileNameManager.GUI.RenamePanel;

/**
 * Panel para renombrar archivos.
 * El patron utilizado es la insercion de un sufijo
 * 
 * @author David Giordana
 *
 */
public class AddSuffixRenamer extends JPanel implements RenamerPanelInterface, DocumentListener, ChangeListener{

	private static final long serialVersionUID = -4156508698010972847L;

	/**
	 * ---- ATTRIBUTES
	 */

	//campo de entrada
	private JTextFieldHint input;	

	//check para control de extension
	private JCheckBox check;

	//tabla de archivos
	private FileTable table;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Constructor de la clase
	 */
	public AddSuffixRenamer() {
		super();
		//instancia los objetos de la clase
		this.table = FileTable.getInstence();
		check = new JCheckBox("Mantener la extension");
		input = new JTextFieldHint();
		
		//Setea los componentes de la clase
		check.setSelected(true);
		input.setHint("Sufijo: ");
		
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
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(check , gbc);
		this.add(panel , BorderLayout.NORTH);
		
		//agrega los listeners
		input.getDocument().addDocumentListener(this);
		check.addChangeListener(this);
	}

	@Override
	public String rename(String str , int size , int index){
			String temp;
			if(check.isSelected()){
				temp = FilenameUtils.getBaseName(str) + RenamePanel.extractString(input);
				temp += FilenameUtils.EXTENSION_SEPARATOR_STR;
				temp += FilenameUtils.getExtension(str);
			}
			else{
				temp = str + RenamePanel.extractString(input);
			}
			return temp;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		table.updateNewName();
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
