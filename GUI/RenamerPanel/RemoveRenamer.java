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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FilenameUtils;

import HexalFileNameManager.GUI.FileTable;

/**
 * Panel para renombrar archivos.
 * El patron utilizado es la eliminacion de partes del nombre
 * 
 * @author David Giordana
 *
 */
public class RemoveRenamer extends RenamerAbstractPanel implements ChangeListener , ActionListener , ItemListener{

	private static final long serialVersionUID = -2782592197603201862L;

	/**
	 * ---- ATTRIBUTES
	 */

	//lista de puntos de vista
	private final String[] VIEW_POINT = {
			"Contar desde el principio" ,
			"Contar desde el final"
	};

	//cantidad de caracteres a eliminar
	private JSpinner removeCharacters;

	//indice de partida del conteo
	private JSpinner startIndex;

	//lista desplegables de puntos de vista
	private JComboBox<String> viewPoint;

	//tabla de archivos
	private FileTable table;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Constructor de la clase
	 */
	public RemoveRenamer() {
		super();
		//instancia los componentes
		removeCharacters = new JSpinner(new SpinnerNumberModel(0,0,100,1));
		startIndex = new JSpinner(new SpinnerNumberModel(0,0,100,1));
		viewPoint = new JComboBox<String>(VIEW_POINT);
		this.table = FileTable.getInstence();

		//agrega los componentes al panel
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" Eliminar ") , gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(removeCharacters , gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" caracteres ") , gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" Iniciar desde el índice ") , gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(startIndex , gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(viewPoint , gbc);
		this.add(panel , BorderLayout.NORTH);

		//agrega los listeners
		removeCharacters.addChangeListener(this);
		startIndex.addChangeListener(this);
		viewPoint.addActionListener(this);
		viewPoint.addItemListener(this);
	}

	@Override
	public void rename() {
		ArrayList<String> oldList = table.getOldNameList();
		ArrayList<String> newList = new ArrayList<String>();
		int cantElim = (int) removeCharacters.getValue();
		int begIndex = (int) startIndex.getValue();
		int vp = viewPoint.getSelectedIndex();
		for(String str : oldList){
			String name = FilenameUtils.getBaseName(str);
			String temporal = "";
			if(vp == 0){
				if(begIndex >= name.length()){
					begIndex = name.length() - 1;
				}
				temporal = name.substring(0, begIndex);
				begIndex += cantElim;
				if(begIndex >= name.length()){
					begIndex = name.length() ;
				}
				temporal += name.substring(begIndex, name.length());
			}
			else{
				if(begIndex >= name.length()){
					begIndex = name.length();
				}
				if(begIndex - cantElim > 0){
					temporal += name.substring(0, begIndex - cantElim);
				}
				temporal += name.substring(begIndex, name.length());
			}
			if(temporal.length() == 0){
				temporal = name;
			}
			temporal += FilenameUtils.EXTENSION_SEPARATOR_STR;
			temporal += FilenameUtils.getExtension(str);
			newList.add(temporal);
		}
		table.setNewNameList(newList);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		rename();
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
