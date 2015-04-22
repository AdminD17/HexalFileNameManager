package HexalFileNameManager.GUI;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ExtraClass.GUI.Layouts.PannedLayout1;

/**
 * Ventana del renombrador de archivos
 * 
 * @author David Giordana
 *
 */
public class FileRenamer extends JFrame{

	private static final long serialVersionUID = 2666013758471421947L;

	/**
	 * ---- ATTRIBUTES
	 */
	
	//tabla de archivos
	private FileTable table;
	
	//selector de modos de renombre
	private FileChangeSelector mode;
	
	//botonera
	private JPanel buttons;
	
	//boton convertir
	private JButton convert;
	
	/**
	 * ---- CONSTRUCTOR
	 */
	
	/**
	 * Constructor de la clase
	 */
	public FileRenamer(){
		table = new FileTable();
		mode = new FileChangeSelector(table);
		table.setFileChangeSelector(mode);
		buttons = new JPanel(new BorderLayout());
		convert = new JButton("Convertir");
		//crea la botonera
		buttons.add(convert, BorderLayout.EAST);
		convert.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				table.renameFiles();
				table.clear();
			}
		});
		//agrega los componentes a la ventana
		PannedLayout1 lay = new PannedLayout1();
		//lay.setConstraint(PannedLayout1.RIGHT_BAR, 250);
		this.getContentPane().setLayout(lay);
		this.getContentPane().add(buttons, PannedLayout1.TOP);
		this.getContentPane().add(table, PannedLayout1.CENTER);
		this.getContentPane().add(mode, PannedLayout1.RIGHT_BAR);
		//setea los parametros de la ventana
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
	}
	
	
}
