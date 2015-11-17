package HexalFileNameManager.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel con la lista de paneles de renombre
 * 
 * @author David Giordana
 *
 */
public class RenamesListPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5209315166440599684L;
	
	//Boton agregar panel de renombre
	private JButton add;
	
	//Lista de paneles de renombre
	private ArrayList<RenamePanel> list;
	
	//panel con paneles de renombres
	private JPanel panel;
	
	//Contiene la instancia de la clase
	private static RenamesListPanel ins;
	
	/**
	 * CONTRUCTOR
	 */
	
	/**
	 * Retorna una instancia unica de la clase
	 * @return Instancia de la clase
	 */
	public static RenamesListPanel getInstance(){
		if(ins == null)
			ins = new RenamesListPanel();
		return ins;
	}
	
	/**
	 * Constructor de la clase
	 */
	private RenamesListPanel(){
		//Instancia los componentes de la clase
		panel = new JPanel();
		BoxLayout bl = new BoxLayout(panel , BoxLayout.Y_AXIS);
		add = new JButton("+");
		list = new ArrayList<RenamePanel>();
		
		//Setea los componentes de la clase
		panel.setLayout(bl);
		this.setLayout(new BorderLayout());
		add.setToolTipText("Agrega panel de renombre");
		add.addActionListener(this);
		
		//Agrega los componentes al panel
		this.add(panel, BorderLayout.CENTER);
		this.add(add, BorderLayout.SOUTH);
	}

	/**
	 * Renombra el nombre a un archivo
	 * @param str Cadena a renombrar
	 * @param size Tamaño de la lista 
	 * @param index Indice de al lista
	 * @return Texto renombrado
	 */
	public String rename(String str , int size , int index){
		String temp = str;
		for(RenamePanel rp : list){
			temp = rp.rename(temp, size, index);
		}
		return temp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		createFrame();
	}
	
	/**
	 * Panel que almacena un panel de renombre y funciona como ventana
	 * 
	 * @author David Giordana
	 *
	 */
	private class RenameFrame extends JPanel implements ActionListener{

		private static final long serialVersionUID = -2874011726209882607L;

		//Boton de cerrar
		private JButton close;
		
		//Panel de nenombre
		private RenamePanel renamePanel;
		
		/**
		 * Constructor del panel
		 * @param rp Panel de renombre
		 */
		public RenameFrame(RenamePanel rp){
			//Instancia los componentes
			JPanel temp = new JPanel(new FlowLayout(SwingConstants.RIGHT));
			close = new JButton("X");
			renamePanel = rp;
			
			//Setea los parametros de la clase
			this.setLayout(new BorderLayout());
			close.addActionListener(this);
			close.setToolTipText("Quita el panel de renombre");
			
			//Agrega los componetens al panel
			temp.add(close);
			this.add(temp, BorderLayout.NORTH);
			this.add(renamePanel, BorderLayout.CENTER);
		}
		
		/**
		 * Retorna el panel de renombre
		 * @return
		 */
		public RenamePanel getRenamePanel(){
			return renamePanel;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			closeFrame(this);
		}
		
	}
	
	/**
	 * Crea un frame
	 */
	private void createFrame(){
		RenamePanel t = new RenamePanel();
		panel.add(new RenameFrame(t));
		list.add(t);
		panel.revalidate();
	}
	
	/**
	 * Cierra un frame
	 * @param frame frame a cerrar
	 */
	private void closeFrame(RenameFrame frame){
		frame.setVisible(false);
		list.remove(frame.getRenamePanel());
		panel.remove(frame);
	}
	
}
