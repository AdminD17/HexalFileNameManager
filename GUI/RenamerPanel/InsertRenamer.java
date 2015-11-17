package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
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
 * El patron utilizado es la insercion de contenido al nombre del archivo
 * 
 * @author David Giordana
 *
 */
public class InsertRenamer extends JPanel implements RenamerPanelInterface, ActionListener, ItemListener, ChangeListener, DocumentListener{

	private static final long serialVersionUID = -5822764386585784323L;

	/**
	 * ---- ATTRIBUTES
	 */

	//lista de puntos de vista
	private final String[] VIEW_POINT = {
			"Contar desde el principio" ,
			"Contar desde el final"
	};

	//texto a insertar
	private JTextFieldHint insert;

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
	public InsertRenamer() {
		super();
		//instancia los objetos de la calse
		this.table = FileTable.getInstence();
		viewPoint = new JComboBox<String>(VIEW_POINT);
		startIndex = new JSpinner(new SpinnerNumberModel(0,0,100,1));
		insert = new JTextFieldHint();
		insert.setHint("Insertar: ");
		insert.setColumns(20);

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
		gbc.fill= GridBagConstraints.EAST;
		panel.add(insert , gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" en el índice ") , gbc);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(startIndex , gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(viewPoint , gbc);		
		this.add(panel , BorderLayout.NORTH);

		//agrega los listeners
		viewPoint.addActionListener(this);
		viewPoint.addItemListener(this);
		startIndex.addChangeListener(this);
		insert.getDocument().addDocumentListener(this);
	}

	@Override
	public String rename(String str , int size , int index){
		int begIndex = (int) startIndex.getValue();
		int vp = viewPoint.getSelectedIndex();
		String name = FilenameUtils.getBaseName(str);
		String temporal = "";
		if(vp == 0){
			if(begIndex >= name.length()){
				begIndex = name.length();
			}
			temporal += name.substring(0, begIndex);
			temporal += RenamePanel.extractString(insert);
			temporal += name.substring(begIndex, name.length());
		}
		else{
			if(begIndex >= name.length()){
				begIndex = 0;
			}
			else{
				begIndex = name.length() - begIndex;
			}
			temporal += name.substring(0, begIndex);
			temporal += insert.getContent();
			temporal += name.substring(begIndex, name.length());
		}
		temporal += FilenameUtils.EXTENSION_SEPARATOR_STR;
		temporal += FilenameUtils.getExtension(str);
		return temporal;
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		table.updateNewName();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
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
