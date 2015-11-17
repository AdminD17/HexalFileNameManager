package HexalFileNameManager.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ExtraClass.GUI.Layouts.PannedLayout1;

/**
 * Ventana del renombrador de archivos
 * 
 * @author David Giordana
 *
 */
public class FileRenamer extends JFrame implements ActionListener{

	private static final long serialVersionUID = 2666013758471421947L;

	/**
	 * ---- ATTRIBUTES
	 */

	//tabla de archivos
	private FileTable table;
	
	//Panel de renombres
	private RenamesListPanel renamePanel;

	//botonera
	private JPanel buttons;

	//boton convertir
	private JButton convert;
	
	//Boton Limpiar
	private JButton clear;

	/**
	 * ---- CONSTRUCTOR
	 */

	/**
	 * Constructor de la clase
	 */
	public FileRenamer(){
		//Instancia los componetnes de la clase
		table = FileTable.getInstence();
		renamePanel = RenamesListPanel.getInstance();
		buttons = new JPanel(new FlowLayout(SwingConstants.RIGHT));
		convert = new JButton("Convertir");
		clear = new JButton("Limpiar");
		
		//crea la botonera
		buttons.add(clear);
		buttons.add(convert);
		
		//Agrega los listeners
		convert.addActionListener(this);
		clear.addActionListener(this);
		
		//agrega los componentes a la ventana
		PannedLayout1 lay = new PannedLayout1();
		lay.setConstraint(PannedLayout1.RIGHT_BAR, 300);
		
		this.getContentPane().setLayout(lay);
		this.getContentPane().add(buttons, PannedLayout1.TOP);
		this.getContentPane().add(table, PannedLayout1.CENTER);
		this.getContentPane().add(renamePanel, PannedLayout1.RIGHT_BAR);
		
		//setea los parametros de la ventana
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(800, 600));
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(convert)){
			table.renameFiles();
			table.clear();
		}
		if(e.getSource().equals(clear)){
			table.clear();
		}
	}


}
