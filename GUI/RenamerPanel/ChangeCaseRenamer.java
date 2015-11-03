package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.io.FilenameUtils;

import HexalFileNameManager.GUI.FileTable;

/**
 * Panel para renombrar archivos.
 * El patron utilizado es el cambio de capitalizacion
 * 
 * @author David Giordana
 *
 */
public class ChangeCaseRenamer extends RenamerAbstractPanel implements ActionListener, ItemListener{

	private static final long serialVersionUID = 3480338868936959200L;

	/**
	 * ---- ATTRIBUTES
	 */

	//patrones de renombre para nombre
	private final String [] namePatterns = {
			"Preservar" ,
			"Cambiar a Mayúsculas" ,
			"Cambiar a Minúsculas" ,
			"Solo Primera Letra" ,
			"Capitalizar"
	};

	//patrones de renombre para extensiones
	private final String [] extensionPatterns = {
			"Preservar" ,
			"Cambiar a Mayúscula" ,
			"Cambiar a Minúscula"
	};

	//tabla de archivos
	private FileTable table;

	//lista desplegable con las opciones de renombre para nombre
	private JComboBox<String> nameOptions;

	//lista desplegabla con las opciones de renombre para extension
	private JComboBox<String> extensionOptions;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Constructor de la clase
	 */
	public ChangeCaseRenamer() {
		super();
		//instancia los componentes
		this.table = FileTable.getInstence();
		this.nameOptions = new JComboBox<String>(namePatterns);
		this.extensionOptions = new JComboBox<String>(extensionPatterns);
		
		//agrega los componentes al panel
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" Nombre: " , SwingConstants.LEFT) , gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(nameOptions , gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" Extensión: " , SwingConstants.LEFT) , gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(extensionOptions , gbc);
		this.add(panel , BorderLayout.NORTH);
		
		//agrega los listeners
		nameOptions.addActionListener(this);
		nameOptions.addItemListener(this);
		extensionOptions.addActionListener(this);
		extensionOptions.addItemListener(this);
	}

	/**
	 * ---- METHODS
	 */

	@Override
	public void rename() {
		ArrayList<String> oldList = table.getOldNameList();
		ArrayList<String> newList = new ArrayList<String>();
		for(String str : oldList){
			String temp = "";
			temp += renameName(FilenameUtils.getBaseName(str));
			temp += FilenameUtils.EXTENSION_SEPARATOR_STR;
			temp += renameExtension(FilenameUtils.getExtension(str));
			newList.add(temp);
		}
		table.setNewNameList(newList);
	}

	/**
	 * Renombra el nombre de un archivo segun lo seteado
	 * @param name nombre del archivo
	 * @return nombre renombrado
	 */
	private String renameName(String name){
		int selected = nameOptions.getSelectedIndex();
		if(selected == 0){
			return name;
		}
		else if(selected == 1){
			return name.toUpperCase();
		}
		else if(selected == 2){
			return name.toLowerCase();
		}
		else if(selected == 3){
			if(name.length() == 0){
				return "";
			}
			else if(name.length() == 1){
				return name.toUpperCase();
			}
			else{
				return Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length());
			}
		}
		else if(selected == 4){
			String[] splitted = name.split("\\s");
			String ret = "";
			for(String str : splitted){
				char[] temp = str.toCharArray();
				temp[0] = Character.toUpperCase(temp[0]);
				ret += new String(temp);
			}
			return ret;
		}
		else{
			return null;
		}
	}

	/**
	 * Renombra una extension segun lo seteado
	 * @param extension estension a renombrar
	 * @return extension renombrada
	 */
	private String renameExtension(String extension){
		int selected = extensionOptions.getSelectedIndex();
		if(selected == 0){
			return extension;
		}
		else if(selected == 1){
			return extension.toUpperCase();
		}
		else if(selected == 2){
			return extension.toLowerCase();
		}
		else{
			return null;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		rename();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		rename();
	}


}
