package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * El patron utilizado es reemplazo de patrones
 * 
 * @author David Giordana
 *
 */
public class FindReplaceRenamer extends JPanel implements RenamerPanelInterface, DocumentListener, ActionListener, ChangeListener{

	private static final long serialVersionUID = 5215673525341635563L;

	/**
	 * ---- ATTRIBUTES
	 */

	//tabla de archivos
	private FileTable table;

	//campo de busqueda
	private JTextFieldHint pattern;

	//campo de reemplazo
	private JTextFieldHint replacement;

	//contrala la extension
	private JCheckBox extCheck;

	//controla la capitalizacion
	private JCheckBox caseCheck;

	//controla la capitalizacion


	/**
	 * ---- CONSTRUCTORS
	 */	

	/**
	 * Cosntructor de la clase
	 */
	public FindReplaceRenamer() {
		super();
		//instancia los objetos de la calse
		table = FileTable.getInstence();
		pattern = new JTextFieldHint();
		replacement = new JTextFieldHint();
		extCheck = new JCheckBox("Conservar Extensión");
		caseCheck = new JCheckBox("Ignorar Capitalizacion");

		//Setea los componentes de la clase
		pattern.setHint("Buscar: ");
		replacement.setHint("Reemplazo: ");
		extCheck.setSelected(true);
		caseCheck.setSelected(true);

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
		panel.add(pattern , gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(caseCheck , gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(replacement , gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(extCheck , gbc);
		this.add(panel , BorderLayout.NORTH);

		//agrega los listeners
		extCheck.addChangeListener(this);
		caseCheck.addChangeListener(this);
		pattern.getDocument().addDocumentListener(this);
		replacement.getDocument().addDocumentListener(this);
	}
	
	@Override
	public String rename(String str , int size , int index){
		String temp = str;
		String name = FilenameUtils.getBaseName(str);
		String extension = FilenameUtils.getExtension(str);
		String Spattern = RenamePanel.extractString(pattern);
		String Rreplacement = RenamePanel.extractString(replacement);

		//Evita trabajar el caso inutil
		if(Spattern.isEmpty()){
			return str;
		}

		if(extension.isEmpty() || !extCheck.isSelected()){
			extension = "";
		}
		else if(!extension.isEmpty()){
			temp = name;
			extension = FilenameUtils.EXTENSION_SEPARATOR_STR + extension;
		}
		else{
			temp = name;
			extension = "";
		}

		if(caseCheck.isSelected()){
			temp = temp.replaceAll("(?i)" + Spattern, Rreplacement);
		}
		else{
			temp = temp.replaceAll(Spattern, Rreplacement);
		}

		return temp + extension;
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		table.updateNewName();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
