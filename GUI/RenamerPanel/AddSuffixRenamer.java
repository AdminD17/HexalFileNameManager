package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.FilenameUtils;

import ExtraClass.GUI.JTextFieldHint;
import HexalFileNameManager.GUI.FileTable;

/**
 * Panel para renombrar archivos.
 * El patron utilizado es la insercion de un sufijo
 * 
 * @author David Giordana
 *
 */
public class AddSuffixRenamer extends RenamerAbstractPanel {

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
	 * @param table tabla de archivos
	 */
	public AddSuffixRenamer(FileTable table) {
		super();
		//instancia los objetos de la clase
		this.table = table;
		check = new JCheckBox("Mantener la extension");
		check.setSelected(true);
		input = new JTextFieldHint();
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
		input.getDocument().addDocumentListener(new DocumentListener(){

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

		});
		check.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				rename();
			}

		});
	}

	@Override
	public void rename() {
		ArrayList<String> oldList = table.getOldNameList();
		ArrayList<String> newList = new ArrayList<String>();
		for(String str : oldList){
			String temp;
			if(check.isSelected()){
				temp = FilenameUtils.getBaseName(str) + input.getContent();
				temp += FilenameUtils.EXTENSION_SEPARATOR_STR;
				temp += FilenameUtils.getExtension(str);
			}
			else{
				temp = str + input.getContent();
			}
			newList.add(temp);
		}
		table.setNewNameList(newList);
	}

}
