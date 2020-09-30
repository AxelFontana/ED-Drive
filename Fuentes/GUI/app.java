package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import Controlador.*;
import TDAMapeo.*;
import TDADiccionario.*;
import TDALista.*;

/**
 * GUI de la aplicacion "ED-Drive" realizada por alumnos del Departamento de Cs. e Ing. de la Computacion, UNS.
 * 
 * @author Fontana, Axel R.
 * @author Franciscutti, Joaquin
 * @author Jerez Martin
 */
public class app {

	private JFrame frmEddrive;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JScrollPane scrollPane ;
	private JScrollPane scrollPane_1;
	private JButton btnGenerarJerarquia;
	private JButton btnAgregarArchivo;
	private JButton btnEliminarArchivo;
	private JButton btnAgregarDirectorio;
	private JButton btnEliminarDirectorio;
	private JButton btnMoverDirectorio;
	private JButton btnCantidadDeDirectorios;
	private Logica logica; 
	private JComboBox<String> cMostrar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app window = new app();
					window.frmEddrive.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public app() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEddrive = new JFrame();
		frmEddrive.setTitle("ED-Drive");
		frmEddrive.setBounds(200, 100, 900, 460);
		frmEddrive.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frmEddrive.getContentPane().setLayout(null);
		
	    btnGenerarJerarquia = new JButton("Generar Jerarquia");
	    btnGenerarJerarquia.setToolTipText("Genera la estructura interna del arbol");
		btnGenerarJerarquia.setBounds(23, 13, 145, 23);
		btnGenerarJerarquia.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg)  {
				logica=new Logica();
				String Ubicacion=JOptionPane.showInputDialog("Inserte Ubicacion Archivo");
				
				try {
					logica.generarJerarquia(Ubicacion);
				}
				catch( InvalidFileException  e) {
					JOptionPane.showMessageDialog(null,( (Throwable) e).getMessage());
				}
				
				actualizarPantalla();
				
				cMostrar.setEnabled(true);
				cMostrar.setToolTipText("Seleccione como desea mostrar el Sistema de Archivos");
				btnGenerarJerarquia.setEnabled(false);
				btnAgregarArchivo.setEnabled(true);
				btnAgregarArchivo.setToolTipText("Inserte la ruta del directorio y el archivo a agregarle");
				btnEliminarArchivo.setEnabled(true);
				btnEliminarArchivo.setToolTipText("Inserte la ruta del archivo a eliminar");
				btnAgregarDirectorio.setEnabled(true);
				btnAgregarDirectorio.setToolTipText("Inserta el directorio D2 en el directorio D1");
				btnEliminarDirectorio.setEnabled(true);
				btnEliminarDirectorio.setToolTipText("Inserta la ruta a un directorio D, este metodo elimina todo Archivo y subCarpeta");
				btnMoverDirectorio.setEnabled(true);
				btnMoverDirectorio.setToolTipText("Inserte las rutas de dos directorios y elimina el directorio D1 y lo mueve al directorio D2");
				btnCantidadDeDirectorios.setEnabled(true);
				btnCantidadDeDirectorios.setToolTipText("Consulta la cantidad de directorios y archivos que posee el sistema.");
			}
		});
		
		frmEddrive.getContentPane().add(btnGenerarJerarquia);
		
		btnAgregarArchivo = new JButton("Agregar Archivo");
		btnAgregarArchivo.setToolTipText("Deshabilitado porque la estructura no esta creada");
		btnAgregarArchivo.setEnabled(false);
		btnAgregarArchivo.setBounds(23, 46, 145, 23);
		btnAgregarArchivo.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String ruta=JOptionPane.showInputDialog("Ingrese la ruta al directorio");
				String archivo=JOptionPane.showInputDialog("Ingrese el archivo");
				try {
					logica.agregarArchivo(ruta, archivo);
				} catch (InvalidPathException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
				
				actualizarPantalla();
				
			}
		});
		
		
		frmEddrive.getContentPane().add(btnAgregarArchivo);
				
		btnEliminarArchivo = new JButton("Eliminar Archivo");		
		btnEliminarArchivo.setToolTipText("Deshabilitado porque la estructura no esta creada");
		btnEliminarArchivo.setEnabled(false);
		btnEliminarArchivo.setBounds(23, 80, 145, 23);
		btnEliminarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				String rutaAeliminar=JOptionPane.showInputDialog("Ingrese la ruta al archivo a eliminar");
				
				try {
					logica.EliminarArchivo(rutaAeliminar);
				} catch (InvalidPathException | InvalidFileException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
				
				actualizarPantalla(); 
					
			}
		});
		
		frmEddrive.getContentPane().add(btnEliminarArchivo);
		
		btnAgregarDirectorio = new JButton("Agregar Directorio");
		btnAgregarDirectorio.setToolTipText("Deshabilitado porque la estructura no esta creada");
		btnAgregarDirectorio.setEnabled(false);
		btnAgregarDirectorio.setBounds(23, 114, 145, 23);
		btnAgregarDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				String RutaDirectorioD1= JOptionPane.showInputDialog("Ingrese la ruta al directorio D1");
				String NombreCarpeta=JOptionPane.showInputDialog("Ingrese el nombre del nuevo directorio D2");
				try {
					logica.AgregarDirectorio(RutaDirectorioD1,NombreCarpeta);
				} catch (InvalidPathException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
				
				actualizarPantalla();
			}
		});
		
		
		frmEddrive.getContentPane().add(btnAgregarDirectorio);
		
		
		btnEliminarDirectorio = new JButton("Eliminar Directorio");
		btnEliminarDirectorio.setToolTipText("Deshabilitado porque la estructura no esta creada");
		btnEliminarDirectorio.setEnabled(false);
		btnEliminarDirectorio.setBounds(23, 148, 145, 23);
		
		btnEliminarDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String RutaAeliminar=JOptionPane.showInputDialog("Ingrese la ruta al directorio a eliminar");
				
				try {
					logica.EliminarDirectorio(RutaAeliminar);
				} catch (InvalidPathException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
				
				actualizarPantalla(); 
			}
		
		});
		
		
		frmEddrive.getContentPane().add(btnEliminarDirectorio);
		
		
	    btnMoverDirectorio = new JButton("Mover Directorio");
	    btnMoverDirectorio.setToolTipText("Deshabilitado porque la estructura no esta creada");
	    btnMoverDirectorio.setEnabled(false);
		btnMoverDirectorio.setBounds(23, 182, 145, 23);
		btnMoverDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String d1=JOptionPane.showInputDialog("Ingrese la ruta al directorio D1");
				String d2=JOptionPane.showInputDialog("Ingrese la ruta al nuevo directorio que contendra a D1");
				
				try {
					logica.MoverDirectorio(d1,d2);
				} catch(InvalidPathException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
				
				actualizarPantalla();
				
			}
		});
		
		frmEddrive.getContentPane().add(btnMoverDirectorio);
		
		btnCantidadDeDirectorios = new JButton("Cantidad de Directorios y Archivos");
		btnCantidadDeDirectorios.setToolTipText("Deshabilitado porque la estructura no esta creada");
		btnCantidadDeDirectorios.setEnabled(false);
		btnCantidadDeDirectorios.setBounds(23, 216, 234, 23);
		btnCantidadDeDirectorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				Pair<Integer,Integer> par=logica.CantidadDirectoriosArchivos();		
				JOptionPane.showMessageDialog(null," La cantidad de directorios es :"+par.getKey()+"\n La cantidad de archivos es :"+par.getValue());
			}			
		});
		frmEddrive.getContentPane().add(btnCantidadDeDirectorios);
		
		cMostrar = new JComboBox<String>();
		cMostrar.setToolTipText("Deshabilitado por que no hay ningun archivo");
		cMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s=(String) cMostrar.getSelectedItem();
				if(s.equals("Listado por extension")) {
					Dictionary<String,String>diccionario=logica.ListadoPorExtension();
					String sl="";
					for(Entry<String, String> e :diccionario.entries()) {
						sl+="Extension : ."+e.getKey()+" ; Nombre :"+e.getValue()+"\n";
					}
					textArea.setText(sl);
					
				}
				else if(s.equals("Listado por profundidad")) {
					Map<String,Integer>map=logica.ListadoPorProfundidad();
					String smap="";
					for(Entry<String,Integer>e:map.entries()) {
						smap+="Ruta completa de directorio : "+e.getKey()+ " Profundidad : "+e.getValue()+"\n ";
					}
					textArea.setText(smap);
				}
				else {
					if(s.equals("Listado por niveles")) {
						PositionList<String>lista=logica.ListadoPorNiveles();
						String st="";
						for(String l:lista) {
							st+=l;
						}
						textArea.setText(st); 
					}
				}
				
				
			}
		});
		cMostrar.setBounds(23, 250, 234, 23);
		cMostrar.setEnabled(false);
		frmEddrive.getContentPane().add(cMostrar);
		cMostrar.addItem("Tipo de muestra");
		cMostrar.addItem("Listado por extension");
		cMostrar.addItem("Listado por profundidad");
		cMostrar.addItem("Listado por niveles");
		
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(287, 13, 495, 158); 
		frmEddrive.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setSize(495,158);
		scrollPane.setRowHeaderView(textArea);
		textArea.setLineWrap(true);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(287, 205, 495,158);
		frmEddrive.getContentPane().add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		textArea_1.setSize(495,158);
		scrollPane_1.setRowHeaderView(textArea_1);
		textArea_1.setEditable(false);
		textArea_1.setLineWrap(true);
	}
	
	private void actualizarPantalla() {
		PositionList<String>lista=logica.ListadoPorNiveles();
		String s="";
		for(String l:lista) {
			s+=l;
		}
		textArea_1.setText(s);
	}
}
