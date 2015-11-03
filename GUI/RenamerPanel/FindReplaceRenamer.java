package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * El patron utilizado es reemplazo de patrones
 * 
 * @author David Giordana
 *
 */
public class FindReplaceRenamer extends RenamerAbstractPanel implements DocumentListener, ActionListener, ChangeListener{

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
		this.table = FileTable.getInstence();
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
	public void rename() {
		ArrayList<String> oldList = table.getOldNameList();
		ArrayList<String> newList = new ArrayList<String>();
		for(String str : oldList){
			String temporal = "";
			if(extCheck.isSelected()){
				temporal = FilenameUtils.getBaseName(str);
				if(caseCheck.isSelected()){
					temporal = temporal.replaceAll("(?i)" + pattern.getContent(), replacement.getContent());
				}
				else{
					temporal = temporal.replaceAll(pattern.getContent(), replacement.getContent());
				}
				temporal += FilenameUtils.EXTENSION_SEPARATOR_STR;
				temporal += FilenameUtils.getExtension(str);
			}
			else{
				temporal = str;
				if(caseCheck.isSelected()){
					temporal = temporal.replaceAll("(?i)" + pattern.getContent(), replacement.getContent());
				}
				else{
					temporal = temporal.replaceAll(pattern.getContent(), replacement.getContent());
				}
			}
			newList.add(temporal);
		}
		table.setNewNameList(newList);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		rename();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		rename();
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
