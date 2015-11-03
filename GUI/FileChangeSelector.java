package HexalFileNameManager.GUI;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import HexalFileNameManager.GUI.RenamerPanel.AddPrefixRenamer;
import HexalFileNameManager.GUI.RenamerPanel.AddSuffixRenamer;
import HexalFileNameManager.GUI.RenamerPanel.ChangeCaseRenamer;
import HexalFileNameManager.GUI.RenamerPanel.ChangeExtensionRenamer;
import HexalFileNameManager.GUI.RenamerPanel.FindReplaceRenamer;
import HexalFileNameManager.GUI.RenamerPanel.InsertRenamer;
import HexalFileNameManager.GUI.RenamerPanel.RemoveRenamer;
import HexalFileNameManager.GUI.RenamerPanel.SequenceRenamer;

/**
 * Panel para la seleccion del modo de renombre
 * 
 * @author David Giordana
 *
 */
public class FileChangeSelector extends JPanel implements ItemListener , ActionListener{

	private static final long serialVersionUID = 8443560997673751813L;

	/**
	 * ---- CONSTANTS
	 */

	//arreglo con las opciones de nerombre
	private final String[] RENAME_MODE = {
			"Encontrar Y Reemplazar" ,
			"Secuencia" ,
			"Agregar Prefijo" ,
			"Agregar Sufijo" ,
			"Insertar" ,
			"Remover" ,
			"Cambiar Capitalizacion" ,
			"Cambiar Extension"
	};

	/**
	 * ---- ATTRIBUTES
	 */

	//lista desplegables con las opciones de renombre
	private JComboBox<String> options;

	//panel con los paneles de renombre
	private JPanel renamePanel;

	//layout del panel de renombre
	private CardLayout cl;

	//constraint para el layout del panel
	private GridBagConstraints gbc;

	//panel de renombre 1
	private FindReplaceRenamer rp1;

	//panel de renombre 2
	private SequenceRenamer rp2;

	//panel de renombre 3
	private AddPrefixRenamer rp3;

	//panel de renombre 4
	private AddSuffixRenamer rp4;

	//panel de renombre 5
	private InsertRenamer rp5;

	//panel de renombre 6
	private RemoveRenamer rp6;

	//panel de renombre 7
	private ChangeCaseRenamer rp7;

	//panel de renombre 8
	private ChangeExtensionRenamer rp8;
	
	//Contiene la instancia de la clase
	private static FileChangeSelector ins;
	
	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Retorna una instancia unica de la clase
	 * @return Instancia de la clase
	 */
	public static FileChangeSelector getInstence(){
		if(ins == null)
			ins = new FileChangeSelector();
		return ins;
	}
	
	/**
	 * Constructor de la clase
	 * @param table tabla de archivos
	 */
	private FileChangeSelector(){		
		//instancia los componentes
		options = new JComboBox<String>(RENAME_MODE);
		cl = new CardLayout();
		renamePanel = new JPanel(cl);
		gbc = new GridBagConstraints();
		rp1 = new FindReplaceRenamer();
		rp2 = new SequenceRenamer();
		rp3 = new AddPrefixRenamer();
		rp4 = new AddSuffixRenamer();
		rp5 = new InsertRenamer();
		rp6 = new RemoveRenamer();
		rp7 = new ChangeCaseRenamer();
		rp8 = new ChangeExtensionRenamer();
		
		//agrega los componentes al panel de renombre
		renamePanel.add(rp1, RENAME_MODE[0]);
		renamePanel.add(rp2, RENAME_MODE[1]);
		renamePanel.add(rp3, RENAME_MODE[2]);
		renamePanel.add(rp4, RENAME_MODE[3]);
		renamePanel.add(rp5, RENAME_MODE[4]);
		renamePanel.add(rp6, RENAME_MODE[5]);
		renamePanel.add(rp7, RENAME_MODE[6]);
		renamePanel.add(rp8, RENAME_MODE[7]);
		
		//agreg los componentes al panel
		this.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		this.add(options , gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.BOTH;
		this.add(renamePanel , gbc);
		
		//setea los componentes
		options.addActionListener(this);
		options.addItemListener(this);
		cl.show(renamePanel, (String)options.getSelectedItem());
	}
	
	/**
	 * ---- LISTENER METHODS
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(options)){
			update();
		}		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(options)){
			update();
		}
	}
	
	/**
	 * ---- METHODS
	 */

	/**
	 * actualiza el panel de renombre
	 */
	public void update(){
		int selected = options.getSelectedIndex();
		cl.show(renamePanel, (String)options.getSelectedItem());
		if(selected == 0){
			rp1.rename();
		}
		else if(selected == 1){
			rp2.rename();
		}
		else if(selected == 2){
			rp3.rename();
		}
		else if(selected == 3){
			rp4.rename();
		}
		else if(selected == 4){
			rp5.rename();
		}
		else if(selected == 5){
			rp6.rename();
		}
		else if(selected == 6){
			rp7.rename();
		}
		else if(selected == 7){
			rp8.rename();
		}
	}

}
