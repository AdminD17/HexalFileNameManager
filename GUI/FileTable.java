package HexalFileNameManager.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.iharder.dnd.FileDrop;
import net.iharder.dnd.FileDrop.Listener;



/**
 * Clase que funciona como tabla de archivos
 *
 * @author David Giordana
 *
 */
public class FileTable extends JPanel implements Listener{

	private static final long serialVersionUID = 9002333815314926106L;

	/**
	 * ---- CONSTANTS
	 */

	//indice de la columna denombre originañ
	public static final int OLD_NAME_INDEX = 0;

	//indice de la columna de nuevo nombre
	public static final int NEW_NAME_INDEX = 1;

	//indice de la columna de origen
	public static final int SOURCE_INDEX = 2;

	//columnas de la tabla
	public static final String[] COLUMNS = {
		"Nombre Original" ,
		"Nuevo Nombre" ,
		"Origen"
	};

	/**
	 * ---- ATTRIBUTES
	 */

	//tabla de archivos
	private JTable table;

	//modelo de tabla
	private DefaultTableModel model;

	//scroll para la tabla
	private JScrollPane scroll;
	
	//setea el panel de patrones
	private FileChangeSelector patternSelector;

	//constraint para el layot
	private GridBagConstraints gbc;
	
	//API Drag an drop
	@SuppressWarnings("unused")
	private FileDrop dnd;
	
	//Contiene una instancia de la clase
	private static FileTable ins;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Retorna una instancia unica de la clase
	 * @return Instancia de la clase
	 */
	public static FileTable getInstence(){
		if(ins == null)
			ins = new FileTable();
		return ins;
	}
	
	/**
	 * Cosntructor de la clase
	 */
	private FileTable(){
		//instancia los objetos
		gbc = new GridBagConstraints();
		model = new DefaultTableModel();
		table = new JTable(model);
		scroll = new JScrollPane(table);		
		dnd = new FileDrop(this, this);
		
		//agrega los componentes al panel
		this.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill= GridBagConstraints.BOTH;
		this.add(scroll , gbc);
		
		//setea la tabla
		model.setColumnIdentifiers(COLUMNS);
	}

	/**
	 * setea el panel selector de patrones de renombre
	 * @param fcs selector de patrones de renombre
	 */
	public void setFileChangeSelector(FileChangeSelector fcs){
		this.patternSelector = fcs;
	}
		
	/**
	 * renombra los archivos segun la tabla
	 */
	public void renameFiles(){
		for(int i = 0 ; i < table.getRowCount() ; i++){
			String oldName = (String) model.getValueAt(i, OLD_NAME_INDEX);
			String newName = (String) model.getValueAt(i, NEW_NAME_INDEX);
			String parent = (String) model.getValueAt(i, SOURCE_INDEX);
			File file = new File(parent + oldName);
			file.renameTo(new File(parent + newName));
		}
	}

	/**
	 * limpia el contenido de la lista
	 */
	public void clear(){
		while(model.getRowCount() != 0){
			model.removeRow(0);
		}
	}

	/**
	 * Retorna la tcolumna de nombres de archivo en forma de lista
	 * @return lista con el contenido de la columna
	 */
	public ArrayList<String> getOldNameList(){
		ArrayList<String> ret = new ArrayList<String>();
		for(int i = 0 ; i < model.getRowCount() ; i++){
			ret.add((String)model.getValueAt(i, OLD_NAME_INDEX));
		}
		return ret;
	}

	/**
	 * setea la columna de nuevo nombre
	 * @param list lista con el contenido de la columna
	 */
	public void setNewNameList(ArrayList<String> list){
		for(int i = 0 ; i < list.size() ; i++){
			model.setValueAt(list.get(i), i, NEW_NAME_INDEX);
		}
	}

	@Override
	public void filesDropped(File[] arg0) {
		for (File file : arg0) {
			String [] rowData = new String[3];
			rowData[OLD_NAME_INDEX] = file.getName();
			rowData[NEW_NAME_INDEX] = file.getName();
			rowData[SOURCE_INDEX] = file.getParent();
			if(!rowData[SOURCE_INDEX].endsWith(File.separator)){
				rowData[SOURCE_INDEX] += File.separator;
			}
			model.addRow(rowData);						
		}
		if(patternSelector != null){
			patternSelector.update();
		}
	}

}
