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
 * El patron utilizado es reemplazo de patrones
 * 
 * @author David Giordana
 *
 */
public class FindReplaceRenamer extends RenamerAbstractPanel {

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
	 * @param table tabla de archivos
	 */
	public FindReplaceRenamer(FileTable table) {
		super();
		//instancia los objetos de la calse
		this.table = table;
		pattern = new JTextFieldHint();
		pattern.setHint("Buscar: ");
		replacement = new JTextFieldHint();
		replacement.setHint("Reemplazo: ");
		extCheck = new JCheckBox("Conservar Extensión");
		extCheck.setSelected(true);
		caseCheck = new JCheckBox("Ignorar Capitalizacion");
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
		extCheck.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				rename();
			}
		});
		caseCheck.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				rename();
			}
		});
		pattern.getDocument().addDocumentListener(new DocumentListener(){

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
		replacement.getDocument().addDocumentListener(new DocumentListener(){

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

}
