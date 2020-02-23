package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.UndoManager;

public class FramePrincipal extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//Creo las variables que voy a utilizar en la aplicacion
	JPanel panelMenu;
	JMenuBar barraMenu;
	JMenu menu1,menu2,menu3,menu4;
	JMenuItem mItColor,mItSalir,mItNuevo,mItAbrir,mItGuardar, mItNegrita, mItSubrayado, mItCursiva,mItAlinIzq,mItAlinDer,mItAlinCent,mItAlinJust;
	JMenuItem mItNegritaPop,mItSubrayadoPop, mItCursivaPop,mItAlinIzqPop,mItAlinDerPop,mItAlinCentPop,mItAlinJustPop;
	JMenuItem mItCopiar,mItPegar,mItCortar,mItRehacer,mItDeshacer,mItInsertImg;
	JMenuItem mItCopiarPop,mItPegarPop,mItCortarPop,mItInsertarImgPop;
	JTextPane panelTexto;
	JToolBar barraHerramientas;
	JToggleButton btNegrita,btCursiva,btSubrayado,btAlinIzq,btAlinDer,btAlinCent,btAlinJust,btEspanyol,btIngles;
	JButton btGuardar,btSalir,btAbrirFichero,btCopiar,btPegar,btCortar,btRehacer,btDeshacer,btInsertarImg;
	JScrollPane scroll;
	Font fuente;
	JLabel labelFuente, labelTamanyo;
	JComboBox<String> comboBoxFuentes;
	JComboBox<Integer>comboBoxTamanyo;
	ButtonGroup grupoParrafo,grupoIdioma;
	JOptionPane vSalir;
	UndoManager managerRedoUndo;

	public FramePrincipal() {
		
		//En el constructor, llamo al método que inicia los componentes y los configura
		initComponents();
		
	}
	
	private void initComponents() {
		
		try {
		ImageIcon iconoApp = new ImageIcon("icons/iconoApp.png"); 
		this.setIconImage(iconoApp.getImage());// Definimos un icono para el editor
		}catch(Exception ex) {System.out.println("Error al cargar el icono de la aplicación");}
		this.setTitle("Editor de texto"); // El titulo del frame
		this.setResizable(true);  //Configuramos el frame para que pueda cambiar su tamaño
		
		//----------Inicializamos las variables----------//
		
		panelTexto = new JTextPane();
		managerRedoUndo = new UndoManager(); 
		scroll = new JScrollPane(panelTexto);
		scroll.setAutoscrolls(true);
		fuente = new Font("Arial", Font.PLAIN, 12);
		
		panelTexto.setFont(fuente); //Definimos la fuente por defecto
		
		//ButtonGroups para que no se queden seleccionandos más de uno.
		grupoParrafo = new ButtonGroup();
		grupoIdioma = new ButtonGroup();
		//Label
		labelFuente = new JLabel();
		labelTamanyo = new JLabel();
		//PanelMenu donde se encuentra nuestro Toolbar y nuestro JMenuBar
		panelMenu = new JPanel(new BorderLayout());
		panelMenu.setBackground(Color.GRAY);
		//Toolbar
		barraHerramientas = new JToolBar();
		//ComboBox
		comboBoxFuentes = new JComboBox<String>();
		comboBoxTamanyo = new JComboBox<Integer>();
		
		//Añadimos las fuentes al comboBox
		String[] nombreFuentes=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for(String fuente:nombreFuentes) {
        	comboBoxFuentes.addItem(fuente);    	
        }
  
        comboBoxFuentes.setSelectedIndex(3);//Seleccionamos por defecto la posicion 3(Arial) para que tenga coherencia la interfaz al iniciar
        comboBoxFuentes.setMaximumSize(comboBoxFuentes.getPreferredSize()); // Y ajustamos su posicion
        
        //Añadimos los tamaños al comboBoc
        for(Integer i=10;i<96;i++) {
        	comboBoxTamanyo.addItem(i);
        	if(i>=24) { //Estos if son para incrementar la i, y que no haya demasiadas opciones en el comboBox
        		i+=1;
        		if(i>=40) {
            		i+=3;
            		if(i>=64) {
                		i+=5;
                	}
            	}
        	}
        }	
        
        comboBoxTamanyo.setSelectedIndex(2); //Seleccionamos la posicion 2 (12) para que tenga coherencia al iniciar
        comboBoxTamanyo.setMaximumSize(comboBoxTamanyo.getPreferredSize()); //Y ajustamos su posicion
		
		//Botones
        try {
		btGuardar = new JButton(new ImageIcon("icons/save.png"));
		btNegrita= new JToggleButton(new ImageIcon("icons/bold.png"));
		btCursiva = new JToggleButton(new ImageIcon("icons/italic.png"));
		btSubrayado = new JToggleButton(new ImageIcon("icons/underline.png"));
		btSalir = new JButton(new ImageIcon("icons/close.png"));
		btAbrirFichero = new JButton(new ImageIcon("icons/open.png"));
		btAlinCent = new JToggleButton(new ImageIcon("icons/text.png"));
		btAlinDer = new JToggleButton(new ImageIcon("icons/right-align.png"));
		btAlinJust = new JToggleButton(new ImageIcon("icons/align.png"));
		btAlinIzq = new JToggleButton(new ImageIcon("icons/left-align.png"));
		btEspanyol = new JToggleButton(new ImageIcon("icons/spain.png"));
		btIngles = new JToggleButton(new ImageIcon("icons/uk.png"));
		btCopiar = new JButton(new ImageIcon("icons/copy.png"));
		btPegar = new JButton(new ImageIcon("icons/paste.png"));
		btCortar = new JButton(new ImageIcon("icons/scissors.png"));
		btRehacer = new JButton(new ImageIcon("icons/redo.png"));
		btDeshacer = new JButton(new ImageIcon("icons/undo.png"));
		btInsertarImg = new JButton(new ImageIcon("icons/insert.png"));
        }catch(Exception ex) {System.out.println("Error al cargar imágenes de botones");}
        
		btEspanyol.setSelected(true);//Seleccionamos por defecto el boton del idioma español para que tenga coherencia al iniciar
		
		//Menu
		barraMenu=new JMenuBar();
		menu1 = new JMenu();
		menu2 = new JMenu();
		menu3 = new JMenu();
		menu4 = new JMenu();
		//MenuItems
		try {
		mItAbrir = new JMenuItem(new ImageIcon("icons/mail.png"));
		mItGuardar= new JMenuItem(new ImageIcon("icons/file-and-folder.png"));
		mItNuevo= new JMenuItem(new ImageIcon("icons/document.png"));
		mItSalir = new JMenuItem(new ImageIcon("icons/close.png"));
		mItNegrita = new JMenuItem(new ImageIcon("icons/bold.png"));
		mItCursiva = new JMenuItem(new ImageIcon("icons/italic.png"));
		mItSubrayado = new JMenuItem(new ImageIcon("icons/underline.png"));
		mItColor = new JMenuItem(new ImageIcon("icons/paint.png"));
		mItAlinCent = new JMenuItem(new ImageIcon("icons/text.png"));
		mItAlinIzq = new JMenuItem(new ImageIcon("icons/left-align.png"));
		mItAlinDer = new JMenuItem(new ImageIcon("icons/right-align.png"));
		mItAlinJust = new JMenuItem(new ImageIcon("icons/align.png"));
		mItCopiar = new JMenuItem(new ImageIcon("icons/copy.png"));
		mItPegar = new JMenuItem(new ImageIcon("icons/paste.png"));
		mItCortar = new JMenuItem(new ImageIcon("icons/scissors.png"));
		mItRehacer = new JMenuItem(new ImageIcon("icons/redo.png"));
		mItDeshacer = new JMenuItem(new ImageIcon("icons/undo.png"));
		mItInsertImg = new JMenuItem(new ImageIcon("icons/insert.png"));
		}catch(Exception ex) {System.out.println("Error al cargar imágenes de items del menú");}
		
		barraHerramientas.setFloatable(false);//Configuramos el toolbar para que no se pueda arrastrar
		barraMenu.setVisible(true); //Y por ultimo, que sea visible
		
		//----------Añadimos los componentes al frame----------//
		
		grupoIdioma.add(btEspanyol);
		grupoIdioma.add(btIngles);
		grupoParrafo.add(btAlinCent);
		grupoParrafo.add(btAlinDer);
		grupoParrafo.add(btAlinIzq);
		grupoParrafo.add(btAlinJust);
		menu1.add(mItNuevo);
		menu1.add(mItAbrir);
		menu1.add(mItGuardar);
		menu1.addSeparator();
		menu1.add(mItSalir);
		menu2.add(mItCopiar);
		menu2.add(mItCortar);
		menu2.add(mItPegar);
		menu2.add(mItInsertImg);
		menu2.addSeparator();
		menu2.add(mItRehacer);
		menu2.add(mItDeshacer);
		menu3.add(mItNegrita);
		menu3.add(mItCursiva);
		menu3.add(mItSubrayado);
		menu3.add(mItColor);
		menu4.add(mItAlinIzq);
		menu4.add(mItAlinCent);
		menu4.add(mItAlinDer);
		menu4.add(mItAlinJust);
		barraHerramientas.add(btAbrirFichero);
		barraHerramientas.add(btGuardar);
		barraHerramientas.add(btSalir);
		barraHerramientas.addSeparator();
		barraHerramientas.add(btCopiar);
		barraHerramientas.add(btCortar);
		barraHerramientas.add(btPegar);
		barraHerramientas.add(btDeshacer);
		barraHerramientas.add(btRehacer);
		barraHerramientas.add(btInsertarImg);
		barraHerramientas.addSeparator();
		barraHerramientas.add(labelFuente);
		barraHerramientas.add(comboBoxFuentes);
		barraHerramientas.addSeparator();
		barraHerramientas.add(labelTamanyo);
		barraHerramientas.add(comboBoxTamanyo);
		barraHerramientas.addSeparator();
		barraHerramientas.add(btNegrita);
		barraHerramientas.add(btCursiva);
		barraHerramientas.add(btSubrayado);
		barraHerramientas.addSeparator();
		barraHerramientas.add(btAlinIzq);
		barraHerramientas.add(btAlinCent);
		barraHerramientas.add(btAlinDer);
		barraHerramientas.add(btAlinJust);
		barraHerramientas.addSeparator();
		barraHerramientas.add(btEspanyol);
		barraHerramientas.add(btIngles);
		barraMenu.add(menu1);
		barraMenu.add(menu2);
		barraMenu.add(menu3);
		barraMenu.add(menu4);
		
		panelMenu.add(barraMenu,BorderLayout.NORTH); //Añadimos nuestro MenuBar a nuestro panel y lo posicionamos en el norte del Layout del panel
		panelMenu.add(barraHerramientas,BorderLayout.SOUTH); //Añadimos nuestro Toolbar a nuestro panel y lo posicionamos en el sur del Layout del panel
		
		this.add(panelMenu,BorderLayout.NORTH);//Añadimos nuestro PanelMenu a nuestro frame y lo posicionamos en el norte del Layout del frame
		this.add(scroll,BorderLayout.CENTER);//Añadimos nuestro JTextPane a nuestro frame y lo posicionamos en el centro del Layout del frame
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//Ajustamos el tamaño de la ventana al iniciar para que coincida con la resolución de la pantalla
		menuContextual(); //Añadimos el menú contextual
		idiomaPorDefecto(); // Y el idioma por defecto
		
		//----------Action Listeners y métodos----------//

		btNegrita.addActionListener(Listener());
		mItNegrita.addActionListener(Listener());
		mItNegritaPop.addActionListener(Listener());
		btSubrayado.addActionListener(Listener());
		mItSubrayado.addActionListener(Listener());
		mItSubrayadoPop.addActionListener(Listener());
		btCursiva.addActionListener(Listener());
		mItCursiva.addActionListener(Listener());
		mItCursivaPop.addActionListener(Listener());
		btAlinIzq.addActionListener(Listener());
		mItAlinIzq.addActionListener(Listener());
		mItAlinIzqPop.addActionListener(Listener());
		btAlinCent.addActionListener(Listener());
		mItAlinCent.addActionListener(Listener());
		mItAlinCentPop.addActionListener(Listener());
		btAlinDer.addActionListener(Listener());
		mItAlinDer.addActionListener(Listener());
		mItAlinDerPop.addActionListener(Listener());
		btAlinJust.addActionListener(Listener());
		mItAlinJust.addActionListener(Listener());
		mItAlinJustPop.addActionListener(Listener());
		btCopiar.addActionListener(Listener());
		mItCopiar.addActionListener(Listener());
		mItCopiarPop.addActionListener(Listener());
		btPegar.addActionListener(Listener());
		mItPegar.addActionListener(Listener());
		mItPegarPop.addActionListener(Listener());
		btCortar.addActionListener(Listener());
		mItCortar.addActionListener(Listener());
		mItCortarPop.addActionListener(Listener());
		btInsertarImg.addActionListener(Listener());
		mItInsertarImgPop.addActionListener(Listener());
		mItInsertImg.addActionListener(Listener());
		btGuardar.addActionListener(Listener());
		mItGuardar.addActionListener(Listener());
		btAbrirFichero.addActionListener(Listener());
		mItAbrir.addActionListener(Listener());
		btRehacer.addActionListener(Listener());
		mItRehacer.addActionListener(Listener());
		btDeshacer.addActionListener(Listener());
		mItRehacer.addActionListener(Listener());
		mItNuevo.addActionListener(Listener());
		mItColor.addActionListener(Listener());
		btEspanyol.addActionListener(Listener());
		btIngles.addActionListener(Listener());
		btSalir.addActionListener(Listener());
		mItSalir.addActionListener(Listener());
		
		panelTexto.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				managerRedoUndo.addEdit(e.getEdit());
			}
		}); //Este listener es para la mejora de deshacer y rehacer
		
		comboBoxFuentes.addActionListener(new ActionListener() {//Listener del comboBox de fuentes
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = comboBoxFuentes.getSelectedItem().toString();
				new StyledEditorKit.FontFamilyAction("fuente", String.valueOf(nombre)).actionPerformed(e);
				panelTexto.grabFocus();
			}
		});
		
		comboBoxTamanyo.addActionListener(new ActionListener() {//Listener del comboBox de tamaño
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer n = Integer.parseInt(comboBoxTamanyo.getSelectedItem().toString());
				new StyledEditorKit.FontSizeAction("tamanyo", Integer.valueOf(n)).actionPerformed(e);
				panelTexto.grabFocus();
			}
		});
		
		panelTexto.addMouseListener(new ListenerRaton()); //Añadimos el Listener para la coherencia de la interfaz al utilizar el ratón
		panelTexto.addKeyListener(new ListenerTeclado());//Añadimos el Listener para la coherencia de la interfaz al utilizar el teclado
		
		atajosDeTeclado(); //Llamamos al método que cofigura los atajos de teclado;
	
	}
	
	private void atajosDeTeclado() { //Método que configura los atajos de teclado
		
		mItNuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItNegrita.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItSubrayado.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItCursiva.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinIzq.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinCent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinDer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinJust.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItRehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItDeshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}
	
	private class ListenerRaton extends MouseAdapter{ //Clase interna que llama a los métodos de cohererncia del ratón 

		@Override
		public void mouseClicked(MouseEvent e) {
			controlMouse();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			controlMouse();			
		}
		
	}
	
	private class ListenerTeclado extends KeyAdapter{ //Clase interna que llama a los métodos de cohererncia del teclado
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			controlTeclado();
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			
			controlTeclado();
		}
	}
	
	private void controlTeclado() { //Método para la coherencia al utilizar el teclado
		
		int posicionMouse = panelTexto.getCaretPosition();//Guardamos la posición del ratón
		AttributeSet estiloCaracter = panelTexto.getStyledDocument().getCharacterElement(posicionMouse-1).getAttributes();//Guardamos el alineado del caracter anterior
		//Realizamos todas las comprobaciones y configuramos todos los elementos para que tenga coherencia la interfaz
		if(panelTexto.getStyledDocument().getFont(estiloCaracter).isBold()==true){
			btNegrita.setSelected(true); 
			mItNegrita.setBackground(Color.GRAY);
			mItNegritaPop.setBackground(Color.GRAY);
		}
		else {
			btNegrita.setSelected(false); 
			mItNegrita.setBackground(Color.decode("#EEEEEE"));
			mItNegritaPop.setBackground(Color.decode("#EEEEEE"));
		}

		if(panelTexto.getStyledDocument().getFont(estiloCaracter).isItalic()==true){
			btCursiva.setSelected(true); 
			mItCursiva.setBackground(Color.GRAY);
			mItCursivaPop.setBackground(Color.GRAY);
		}
		else {
			btCursiva.setSelected(false); 
			mItCursiva.setBackground(Color.decode("#EEEEEE"));
			mItCursivaPop.setBackground(Color.decode("#EEEEEE"));
		}
		
		if(estiloCaracter.containsAttribute(StyleConstants.Underline, true)){
			btSubrayado.setSelected(true); 
			mItSubrayado.setBackground(Color.GRAY);
			mItSubrayadoPop.setBackground(Color.GRAY);
		}
		else {
			btSubrayado.setSelected(false); 
			mItSubrayado.setBackground(Color.decode("#EEEEEE"));
			mItSubrayadoPop.setBackground(Color.decode("#EEEEEE"));
		}
		
		if(estiloCaracter.containsAttribute(StyleConstants.ALIGN_LEFT, true)){
			btAlinIzq.setSelected(true); 
			mItAlinIzq.setBackground(Color.GRAY);
			mItAlinIzqPop.setBackground(Color.GRAY);
			mItAlinCent.setBackground(Color.decode("#EEEEEE"));
            mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinDer.setBackground(Color.decode("#EEEEEE"));
            mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinJust.setBackground(Color.decode("#EEEEEE"));
            mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));
			
		}
		else {
			btAlinIzq.setSelected(false);
			mItAlinIzq.setBackground(Color.decode("#EEEEEE"));
			mItAlinIzqPop.setBackground(Color.decode("#EEEEEE"));
		}
		
		if(estiloCaracter.containsAttribute(StyleConstants.ALIGN_RIGHT, true)){
			System.out.println("ALINEADO DERECHA");
			btAlinDer.setSelected(true); 
			mItAlinDer.setBackground(Color.GRAY);
			mItAlinDerPop.setBackground(Color.GRAY);
			mItAlinCent.setBackground(Color.decode("#EEEEEE"));
            mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinIzq.setBackground(Color.decode("#EEEEEE"));
            mItAlinIzqPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinJust.setBackground(Color.decode("#EEEEEE"));
            mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));
		}
		else {
			btAlinDer.setSelected(false);
			mItAlinDer.setBackground(Color.decode("#EEEEEE"));
			mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));
		}
		
		if(estiloCaracter.containsAttribute(StyleConstants.ALIGN_CENTER, true)){
			btAlinCent.setSelected(true); 
			mItAlinCent.setBackground(Color.GRAY);
			mItAlinCentPop.setBackground(Color.GRAY);
			mItAlinJust.setBackground(Color.decode("#EEEEEE"));
            mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinDer.setBackground(Color.decode("#EEEEEE"));
            mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinJust.setBackground(Color.decode("#EEEEEE"));
            mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));
		}
		else {
			btAlinCent.setSelected(false);
			mItAlinCent.setBackground(Color.decode("#EEEEEE"));
			mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));
		}
		if(estiloCaracter.containsAttribute(StyleConstants.ALIGN_JUSTIFIED, true)){
			btAlinJust.setSelected(true); 
			mItAlinJust.setBackground(Color.GRAY);
			mItAlinJustPop.setBackground(Color.GRAY);
			mItAlinCent.setBackground(Color.decode("#EEEEEE"));
            mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinDer.setBackground(Color.decode("#EEEEEE"));
            mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));
            mItAlinIzq.setBackground(Color.decode("#EEEEEE"));
            mItAlinIzqPop.setBackground(Color.decode("#EEEEEE"));
		}
		else {
			btAlinJust.setSelected(false);
			mItAlinJust.setBackground(Color.decode("#EEEEEE"));
			mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));
		}
		
		
	}
	//Variables para controlar el estado de los componentes referentes al párrafo
	boolean isAlinDer = false; 
    boolean isAlinCent = false;
    boolean isAlinIzq =false;
    boolean isJustificado = false;
    
	private void controlMouse() {//Método para la coherencia al utilizar el ratón
		
		int posicionMouse = panelTexto.getCaretPosition(); //Guardamos la posición del ratón
		AttributeSet estiloCaracter = panelTexto.getStyledDocument().getCharacterElement(posicionMouse -1).getAttributes(); //Guardamos el estilo del caracter anterior
		String fuenteCaracter = panelTexto.getStyledDocument().getFont(estiloCaracter).getFamily();//Guardamos la fuente del caracter
		int tamanyoCaracter = panelTexto.getStyledDocument().getFont(estiloCaracter).getSize();//Guardamos el tamaño del caracter
		//Realizamos todas las comprobaciones y configuramos todos los elementos para que tenga coherencia la interfaz
		if(panelTexto.getStyledDocument().getFont(estiloCaracter).isBold()==true){
			btNegrita.setSelected(true); 
			mItNegrita.setBackground(Color.GRAY);
			mItNegritaPop.setBackground(Color.GRAY);
		}
		else {
			btNegrita.setSelected(false); 
			mItNegrita.setBackground(Color.decode("#EEEEEE"));
			mItNegritaPop.setBackground(Color.decode("#EEEEEE"));
		}

		if(panelTexto.getStyledDocument().getFont(estiloCaracter).isItalic()==true){
			btCursiva.setSelected(true); 
			mItCursiva.setBackground(Color.GRAY);
			mItCursivaPop.setBackground(Color.GRAY);
		}
		else {
			btCursiva.setSelected(false); 
			mItCursiva.setBackground(Color.decode("#EEEEEE"));
			mItCursivaPop.setBackground(Color.decode("#EEEEEE"));
		}
		
		if(estiloCaracter.containsAttribute(StyleConstants.Underline, true)){
			btSubrayado.setSelected(true); 
			mItSubrayado.setBackground(Color.GRAY);
			mItSubrayadoPop.setBackground(Color.GRAY);
		}
		else {
			btSubrayado.setSelected(false); 
			mItSubrayado.setBackground(Color.decode("#EEEEEE"));
			mItSubrayadoPop.setBackground(Color.decode("#EEEEEE"));
		}
		

		//Llamamos al método que controla el comboBox para la coherencia de la interfaz
		controlComboBox(fuenteCaracter, tamanyoCaracter);
		
	}
	
	private void controlComboBox(String fuente, Integer tamanyo) { //Método que controla la coherencia de los comboBox
		for (int i = 0; i < comboBoxFuentes.getItemCount(); i++) {
			if(fuente.equals(comboBoxFuentes.getItemAt(i))) {
				comboBoxFuentes.setSelectedIndex(i);	
			}
		}
		
		for (int i = 0; i < comboBoxTamanyo.getItemCount(); i++) {
			
			if(tamanyo.equals(comboBoxTamanyo.getItemAt(i))) {
				comboBoxTamanyo.setSelectedIndex(i);
			}
		}
	}
	
	private ActionListener Listener()  { //Listener que engloba la mayoría de las acciones realizadas por el usuario
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource()==btNegrita || e.getSource() == mItNegrita || e.getSource() == mItNegritaPop){
					panelTexto.grabFocus();
					new StyledEditorKit.BoldAction().actionPerformed(e);
				}
				
				else if(e.getSource()==btSubrayado || e.getSource()==mItSubrayado || e.getSource()==mItSubrayadoPop) {
					new StyledEditorKit.UnderlineAction().actionPerformed(e);
					panelTexto.grabFocus();
				}
				
				else if(e.getSource()==btCursiva || e.getSource()==mItCursiva || e.getSource()==mItCursivaPop){
					new StyledEditorKit.ItalicAction().actionPerformed(e);
					panelTexto.grabFocus();
				}
				
				else if(e.getSource()==btAlinIzq || e.getSource()==mItAlinIzq || e.getSource()==mItAlinIzqPop) {
					new StyledEditorKit.AlignmentAction("Parrafo", StyleConstants.ALIGN_LEFT).actionPerformed(e);
					panelTexto.grabFocus();
					    if(!isAlinIzq) {
						btAlinIzq.setSelected(true);
					    mItAlinIzq.setBackground(Color.GRAY);
					    mItAlinIzqPop.setBackground(Color.GRAY);
					    mItAlinDer.setBackground(Color.decode("#EEEEEE"));
					    mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));isAlinDer=false;
					    mItAlinCent.setBackground(Color.decode("#EEEEEE"));
					    mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));isAlinCent = false;
					    mItAlinJust.setBackground(Color.decode("#EEEEEE"));
					    mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));isJustificado=false;
					    isAlinIzq=true;
					    }else {
					    mItAlinIzq.setBackground(Color.decode("#EEEEEE"));
					    mItAlinIzqPop.setBackground(Color.decode("#EEEEEE"));
					    isAlinIzq=false;
					    }
				}
				
				else if(e.getSource()==btAlinCent || e.getSource()==mItAlinCent || e.getSource()==mItAlinCentPop) {
					new StyledEditorKit.AlignmentAction("Parrafo", StyleConstants.ALIGN_CENTER).actionPerformed(e);
					panelTexto.grabFocus();
					
					if(!isAlinCent) {
						btAlinCent.setSelected(true);
					    mItAlinCent.setBackground(Color.GRAY);
					    mItAlinCentPop.setBackground(Color.GRAY);
					    mItAlinDer.setBackground(Color.decode("#EEEEEE"));
					    mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));isAlinDer = false;
					    mItAlinIzq.setBackground(Color.decode("#EEEEEE"));
					    mItAlinIzqPop.setBackground(Color.decode("#EEEEEE"));isAlinIzq = false;
					    mItAlinJust.setBackground(Color.decode("#EEEEEE"));
					    mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));isJustificado = false;
					    isAlinCent = true;
					    }else {
					    mItAlinCent.setBackground(Color.decode("#EEEEEE"));
					    mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));
					    isAlinCent=false;
					    }
				}
				
				else if(e.getSource()==btAlinDer || e.getSource()==mItAlinDer || e.getSource()==mItAlinDerPop) {
					new StyledEditorKit.AlignmentAction("Parrafo", StyleConstants.ALIGN_RIGHT).actionPerformed(e);
					panelTexto.grabFocus();
					
					if(!isAlinDer) {
						btAlinDer.setSelected(true);
					    mItAlinDer.setBackground(Color.GRAY);
					    mItAlinDerPop.setBackground(Color.GRAY);
					    mItAlinIzq.setBackground(Color.decode("#EEEEEE"));
					    mItAlinIzqPop.setBackground(Color.decode("#EEEEEE"));isAlinIzq=false;
					    mItAlinCent.setBackground(Color.decode("#EEEEEE"));
					    mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));isAlinCent = false;
					    mItAlinJust.setBackground(Color.decode("#EEEEEE"));
					    mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));isJustificado=false;
					    isAlinDer=true;
					    }else {
					    mItAlinDer.setBackground(Color.decode("#EEEEEE"));
					    mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));
					    isAlinDer=false;
					    }
				}
				
				else if(e.getSource()==btAlinJust || e.getSource()==mItAlinJust || e.getSource()==mItAlinJustPop) {
					new StyledEditorKit.AlignmentAction("Parrafo", StyleConstants.ALIGN_JUSTIFIED).actionPerformed(e);
					panelTexto.grabFocus();
					
					if(!isJustificado) {
						btAlinJust.setSelected(true);
					    mItAlinJust.setBackground(Color.GRAY);
					    mItAlinJustPop.setBackground(Color.GRAY);
					    mItAlinDer.setBackground(Color.decode("#EEEEEE"));
					    mItAlinDerPop.setBackground(Color.decode("#EEEEEE"));isAlinDer=false;
					    mItAlinCent.setBackground(Color.decode("#EEEEEE"));
					    mItAlinCentPop.setBackground(Color.decode("#EEEEEE"));isAlinCent = false;
					    mItAlinIzq.setBackground(Color.decode("#EEEEEE"));
					    mItAlinIzqPop.setBackground(Color.decode("#EEEEEE"));isAlinIzq=false;
					    isJustificado=true;
					    }else {
					    mItAlinJust.setBackground(Color.decode("#EEEEEE"));
					    mItAlinJustPop.setBackground(Color.decode("#EEEEEE"));
					    isJustificado=false;
					    }
				}
				else if(e.getSource()==btCopiar || e.getSource()==mItCopiarPop || e.getSource()==mItCopiar) {
					new DefaultEditorKit.CopyAction().actionPerformed(e);
				}
				else if(e.getSource()==btCortar || e.getSource()==mItCortarPop || e.getSource()==mItCortar) {
					new DefaultEditorKit.CutAction().actionPerformed(e);
				}
				else if(e.getSource()==btPegar || e.getSource()==mItPegarPop || e.getSource()==mItPegar) {
					new DefaultEditorKit.PasteAction().actionPerformed(e);
				}
				else if(e.getSource()==btInsertarImg || e.getSource()==mItInsertImg || e.getSource()==mItInsertarImgPop) {
					insertarImagen();
				}
				else if(e.getSource()==btGuardar|| e.getSource()==mItGuardar){
					guardarArchivo();
				}
				else if(e.getSource()==btAbrirFichero|| e.getSource()==mItAbrir){
					abrirArchivo();
				}
				
				else if(e.getSource()==btDeshacer || e.getSource()==mItDeshacer) {
					managerRedoUndo.canUndo();
					managerRedoUndo.undo();
				}
				else if(e.getSource()==btRehacer|| e.getSource()==mItRehacer) {
					managerRedoUndo.canRedo();
					managerRedoUndo.redo();
				}
				
				else if(e.getSource()==mItNuevo) {
					new FramePrincipal().setVisible(true) ;
				}
				else if(e.getSource()==mItColor) {
	                JColorChooser colores = new JColorChooser(); 
	                Color color = colores.showDialog(null, "Seleccione un color", Color.black);
	                new StyledEditorKit.ForegroundAction("Color", color).actionPerformed(e);
	            }
				else if(e.getSource()==btEspanyol) {
					idiomaPorDefecto();
				}
				else if(e.getSource()==btIngles) {
					idiomaIngles();
				}
				else if(e.getSource()==btSalir || e.getSource()==mItSalir ) {
					String [] opciones = {"Guardar","No guardar","Cancelar"};
					int jOptionPaneSalir = JOptionPane.showOptionDialog(null,"¿Desea guardar antes de \n salir de la aplicación?","",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,opciones,opciones[0]);
					if(jOptionPaneSalir == 0) {
						guardarArchivo();
						System.exit(0);
					} else if(jOptionPaneSalir == 1) {
						System.exit(0);
					}
					
				}
			}
		};
		
	}
	
	private void insertarImagen() { //Método para insertar imágenes
		JFileChooser fileChooser = new JFileChooser();
        int controlFileChooser = fileChooser.showOpenDialog(this);
        if (controlFileChooser == JFileChooser.APPROVE_OPTION) {
          File imagenSeleccionada = fileChooser.getSelectedFile();
          panelTexto.insertIcon(new ImageIcon(imagenSeleccionada.getAbsolutePath()));
        }
	}
	
	private String abrirArchivo() { //Método para abrir un archivo
		String aux="";   
		String texto="";
		  try
		  {
		   JFileChooser file=new JFileChooser();
		   file.showOpenDialog(this);
		   File abre=file.getSelectedFile();
		   
		   if(abre!=null)
		   {     
		      FileReader archivos = new FileReader(abre);
		      BufferedReader lee = new BufferedReader(archivos);
		      while((aux=lee.readLine()) != null){texto += aux + "\n";}
		         lee.close();
		    }    
		   }catch(IOException ex){
		     JOptionPane.showMessageDialog(null,ex+"" +
		           "\nNo se ha encontrado el archivo",
		                 "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
		    }
		  return texto;
	}
	
	private void guardarArchivo() { //Método para guardar un archivo (y sus estilos).
		
		JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String rutaFichero = fileChooser.getSelectedFile().getAbsolutePath();
            StyledDocument styledDocument = (StyledDocument) panelTexto.getDocument();
            RTFEditorKit kit = new RTFEditorKit();
            BufferedOutputStream bufferedOutputStream;
           try {
               bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(rutaFichero));
               kit.write(bufferedOutputStream, styledDocument, styledDocument.getStartPosition().getOffset(), styledDocument.getLength());
               bufferedOutputStream.flush();
               bufferedOutputStream.close();
           }catch (IOException | BadLocationException e) {e.printStackTrace();}
        }
	}    
    private void menuContextual() { //Método que crea el menú contextual
    	
    	JPopupMenu ventanaEmergente; //Definimos un PopUpMenu
    	try {
    	mItNegritaPop = new JMenuItem("Negrita",new ImageIcon("icons/bold.png"));
    	mItCursivaPop = new JMenuItem("Cursiva",new ImageIcon("icons/italic.png"));
    	mItSubrayadoPop = new JMenuItem("Subrayado",new ImageIcon("icons/underline.png"));
    	mItAlinCentPop = new JMenuItem("Centrado",new ImageIcon("icons/text.png"));
    	mItAlinIzqPop = new JMenuItem("Izquierda",new ImageIcon("icons/left-align.png"));
    	mItAlinDerPop = new JMenuItem("Derecha",new ImageIcon("icons/right-align.png"));
    	mItAlinJustPop = new JMenuItem("Justifado",new ImageIcon("icons/align.png"));
    	mItCopiarPop = new JMenuItem("Copiar", new ImageIcon("icons/copy.png"));
    	mItPegarPop = new JMenuItem("Pegar",new ImageIcon("icons/paste.png"));
    	mItCortarPop = new JMenuItem("Cortar",new ImageIcon("icons/scissors.png"));
    	mItInsertarImgPop = new JMenuItem("Insertar imagen",new ImageIcon("icons/insert.png"));
    	}catch(Exception ex) {System.out.println("Error al cargar imágenes del menú contextual");}
    	
    	//Configuramos los atajos de teclado, ya que son otros items y no valdrían con los del menu
    	mItNegritaPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItSubrayadoPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItCursivaPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItCopiarPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItPegarPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItCortarPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinIzqPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinCentPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinDerPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mItAlinJustPop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		//Inicializamos el popUoMenu y le añadimos los items
    	ventanaEmergente = new JPopupMenu();
    	ventanaEmergente.add(mItCopiarPop);
    	ventanaEmergente.add(mItCortarPop);
    	ventanaEmergente.add(mItPegarPop);
    	ventanaEmergente.add(mItInsertarImgPop);
    	ventanaEmergente.addSeparator();
    	ventanaEmergente.add(mItNegritaPop);
    	ventanaEmergente.add(mItSubrayadoPop);
    	ventanaEmergente.add(mItCursivaPop);
    	ventanaEmergente.addSeparator();
    	ventanaEmergente.add(mItAlinIzqPop);
    	ventanaEmergente.add(mItAlinCentPop);
    	ventanaEmergente.add(mItAlinDerPop);
    	ventanaEmergente.add(mItAlinJustPop);
    	
    	//Añadimos un MouseListener para que al clicar con el botón derecho del ratón,se muestre el menú contextual
    	panelTexto.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			if(e.getButton() == MouseEvent.BUTTON3) {
    				ventanaEmergente.show(panelTexto, e.getX(), e.getY());
    			}	
    		}  	
		});
    	panelTexto.add(ventanaEmergente); //Lo añadimos a nuestro JTextPane
    }
    //Método para configurar el idioma por defecto
    private void idiomaPorDefecto() { 
    	//Recuperamos el valor de nuestras etiquetas definidas en los archivos ".properties" y configuramos el idioma de nuestros componentes
    	menu1.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_menu_archivo"));
    	menu2.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_menu_editar"));
    	menu3.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_menu_estilos"));
    	menu4.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_menu_parrafo"));
    	mItAbrir.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_abrir"));
    	mItGuardar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_guardar"));
    	mItNuevo.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_nuevo"));
    	mItCopiar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_copiar"));
    	mItCortar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_cortar"));
    	mItPegar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_pegar"));
    	mItRehacer.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_rehacer"));
    	mItDeshacer.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_deshacer"));
    	mItInsertImg.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_insertar_imagen"));
    	mItNegrita.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_negrita"));
    	mItCursiva.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_cursiva"));
    	mItSubrayado.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_subrayado"));
    	mItColor.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_color"));
    	mItAlinIzq.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_izquierda"));
    	mItAlinCent.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_centrado"));
    	mItAlinDer.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_derecha"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_justificado"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_fuente"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_tamanyo"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_idioma_espanyol"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_idioma_ingles"));
    	mItSalir.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_salir"));
    	btAbrirFichero.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_abrir"));
    	btGuardar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_guardar"));
    	btSalir.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_salir"));
    	btCopiar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_copiar"));
    	btCortar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_cortar"));
    	btPegar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_pegar"));
    	btDeshacer.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_deshacer"));
    	btRehacer.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_rehacer"));
    	btInsertarImg.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_insertar_imagen"));
    	btNegrita.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_negrita"));
    	btCursiva.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_cursiva"));
    	btSubrayado.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_subrayado"));
    	btAlinIzq.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_izquierda"));
    	btAlinCent.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_centrado"));
    	btAlinDer.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_derecha"));
    	btAlinJust.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_justificado"));
    	btEspanyol.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_idioma_espanyol"));
    	btIngles.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_idioma_ingles"));
    	labelFuente.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_fuente"));
    	labelTamanyo.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_tamanyo"));
    	mItAlinCentPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_centrado"));
    	mItAlinDerPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_derecha"));
    	mItAlinIzqPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_izquierda"));
    	mItAlinJustPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_alineado_justificado"));
    	mItCopiarPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_copiar"));
    	mItCortarPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_cortar"));
    	mItCursivaPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_cursiva"));
    	mItInsertarImgPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_insertar_imagen"));
    	mItNegritaPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_negrita"));
    	mItPegarPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_pegar"));
    	mItSubrayadoPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_es").getString("etiqueta_item_subrayado"));
    }
    //Método para configurar el idioma inglés
    private void idiomaIngles() {
    	//Recuperamos el valor de nuestras etiquetas definidas en los archivos ".properties" y configuramos el idioma de nuestros componentes
    	menu1.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_menu_archivo"));
    	menu2.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_menu_editar"));
    	menu3.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_menu_estilos"));
    	menu4.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_menu_parrafo"));
    	mItAbrir.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_abrir"));
    	mItGuardar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_guardar"));
    	mItNuevo.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_nuevo"));
    	mItCopiar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_copiar"));
    	mItCortar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_cortar"));
    	mItPegar.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_pegar"));
    	mItRehacer.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_rehacer"));
    	mItDeshacer.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_deshacer"));
    	mItInsertImg.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_insertar_imagen"));
    	mItNegrita.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_negrita"));
    	mItCursiva.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_cursiva"));
    	mItSubrayado.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_subrayado"));
    	mItColor.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_color"));
    	mItAlinIzq.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_izquierda"));
    	mItAlinCent.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_centrado"));
    	mItAlinDer.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_derecha"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_justificado"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_fuente"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_tamanyo"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_idioma_espanyol"));
    	mItAlinJust.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_idioma_ingles"));
    	mItSalir.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_salir"));
    	btAbrirFichero.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_abrir"));
    	btGuardar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_guardar"));
    	btSalir.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_salir"));
    	btCopiar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_copiar"));
    	btCortar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_cortar"));
    	btPegar.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_pegar"));
    	btDeshacer.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_deshacer"));
    	btRehacer.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_rehacer"));
    	btInsertarImg.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_insertar_imagen"));
    	btNegrita.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_negrita"));
    	btCursiva.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_cursiva"));
    	btSubrayado.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_subrayado"));
    	btAlinIzq.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_izquierda"));
    	btAlinCent.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_centrado"));
    	btAlinDer.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_derecha"));
    	btAlinJust.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_justificado"));
    	btEspanyol.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_idioma_espanyol"));
    	btIngles.setToolTipText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_idioma_ingles"));
    	labelFuente.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_fuente"));
    	labelTamanyo.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_tamanyo"));
    	mItAlinCentPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_centrado"));
    	mItAlinDerPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_derecha"));
    	mItAlinIzqPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_izquierda"));
    	mItAlinJustPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_alineado_justificado"));
    	mItCopiarPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_copiar"));
    	mItCortarPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_cortar"));
    	mItCursivaPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_cursiva"));
    	mItInsertarImgPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_insertar_imagen"));
    	mItNegritaPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_negrita"));
    	mItPegarPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_pegar"));
    	mItSubrayadoPop.setText(ResourceBundle.getBundle("idiomas.Etiquetas_en").getString("etiqueta_item_subrayado"));
    }
}
