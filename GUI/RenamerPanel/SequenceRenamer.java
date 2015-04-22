package HexalFileNameManager.GUI.RenamerPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
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

/**
 * Panel para renombrar archivos.
 * El patron utilizado es la la incorporacion de secuencias
 * 
 * @author David Giordana
 *
 */
public class SequenceRenamer extends RenamerAbstractPanel {

	private static final long serialVersionUID = 792376107200566704L;

	/**
	 * ---- ATTRIBUTES
	 */

	//opciones de secuencias
	private final String[] SEQUENCE_OPTIONS = {
			"1, 2, 3 ..." //,
			//"A , B , C ..." ,
			//"a , b , c ..."
	};

	//numero de arranque del patron
	private JSpinner startIn;

	//incremento de paso
	private JSpinner incr;

	//patron de secuencia
	private JComboBox<String> pattern;

	//prefijo
	private JTextFieldHint prefix;

	//sufijo
	private JTextFieldHint suffix;

	//caja de control de extension
	private JCheckBox keepExt;

	//tabla de archivos
	private FileTable table;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Constructor de la clase
	 * @param table tabla de archivos
	 */
	public SequenceRenamer(FileTable table) {
		super();
		//instancia los objetos de la clase
		this.table = table;
		pattern = new JComboBox<String>(SEQUENCE_OPTIONS);	
		startIn = new JSpinner(new SpinnerNumberModel(0,0,100,1));
		incr = new JSpinner(new SpinnerNumberModel(1,1,100,1));
		prefix = new JTextFieldHint();
		prefix.setHint("Prefijo: ");
		suffix = new JTextFieldHint();
		suffix.setHint("Sufijo: ");
		keepExt = new JCheckBox("Mantener Extension ");
		keepExt.setSelected(true);
		//agrega los objetos al panel
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(pattern , gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" Iniciar en ") , gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(startIn , gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(new JLabel(" Incrementar ") , gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(incr , gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(prefix , gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(suffix , gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		panel.add(keepExt , gbc);
		this.add(panel , BorderLayout.NORTH);
		//agrega los listeners
		pattern.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				rename();
			}
		});
		pattern.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				rename();
			}
		});
		startIn.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				rename();
			}
		});
		incr.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				rename();
			}
		});
		prefix.getDocument().addDocumentListener(new DocumentListener(){
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
		suffix.getDocument().addDocumentListener(new DocumentListener(){
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
		int patternSelected = pattern.getSelectedIndex();
		for(int i = 0 ; i < oldList.size() ; i++){
			String ext = "";
			//controla la extension
			if(keepExt.isSelected()){
				String temp = FilenameUtils.getExtension(oldList.get(i));
				if(temp.length() > 0){
					ext += FilenameUtils.EXTENSION_SEPARATOR_STR;
					ext += temp;
				}
			}
			if(patternSelected == 0){
				int incr = (int) this.incr.getValue();
				int initialVal = (int) this.startIn.getValue();
				int maxValue = (oldList.size() - 1)*incr +initialVal;
				String temp = seqArabic(i , ext , Integer.toString(maxValue).length());
				newList.add(temp);
			}
		}
		table.setNewNameList(newList);
	}

	/**
	 * 
	 * @param index
	 * @param extension
	 * @param digits
	 * @return
	 */
	private String seqArabic(int index , String extension , int digits){
		String ret = prefix.getContent();
		int num = (index*(int) this.incr.getValue()) + (int) this.startIn.getValue();
		ret += String.format("%" + digits +"d" , num);
		ret += suffix.getContent();
		ret += extension;
		return ret;
	}

}
