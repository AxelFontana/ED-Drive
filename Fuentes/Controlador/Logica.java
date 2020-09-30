package Controlador;

import java.util.regex.*;
import TDAArbol.*;
import TDALista.*;
import TDAPila.*;
import TDACola.*;
import TDADiccionario.*;
import TDAMapeo.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Clase logica de la aplicacion "ED-Drive" realizada por alumnos del Departamento de Cs. e Ing. de la Computacion, UNS.
 * 
 * @author Fontana, Axel R.
 * @author Franciscutti, Joaquin
 * @author Jerez, Martin
 */
public class Logica{
	Tree<Pair<String, PositionList<String>>> arbol;
	
	/**
	 * Dada la ruta de acceso a un archivo de texto A, 
	 * parsea el contenido de A creando una jerarquia de tipo arbol que representa el sistema de archivos virtual.
	 * @param ruta Ruta de acceso a un archivo de texto A al que desea representar en la aplicacion. 
	 * @throws InvalidFileException Se lanza si el archivo no cumple el formato xml indicado.
	 */
	public void generarJerarquia(String ruta) throws InvalidFileException{
		
		String archivo = aplanarArchivo(ruta);
		Queue<String> cola = new ColaCircular<String>();
		arbol = new Arbol<Pair<String,PositionList<String>>>();
		
		llenar_cola(cola,archivo);
		if( !esValida(cola))
			throw new InvalidFileException("El archivo no cumple el formato indicado.");
		
		while(!cola.isEmpty()) {
			try {
				cola.dequeue();
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private String aplanarArchivo(String ruta) {
		File archivo;
		FileReader fr;
		BufferedReader br;
		archivo=new File(ruta);	
		String s="";
		
		try {
			fr=new FileReader(archivo);
			br=new BufferedReader(fr);
			String linea=br.readLine();
			String guardar1;
			
			while(linea!=null) {
				
				guardar1=linea.replaceAll("\t","");
				s=s+guardar1;
				
				linea=br.readLine();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	private void llenar_cola(Queue<String> cola, String archivo) {
		int i = 0;
		String token = "";
		while(i < archivo.length()) {
			char c = archivo.charAt(i);
			if (c == '>') {
				token+=c;
				cola.enqueue(token);
				token = "";
			}
			else
				if (c == '<' ) {
					if (i>0) {
						if (archivo.charAt(i-1) != '>') {
							cola.enqueue(token);
							token = ""+c;					
						}
						else
							token+=c;
					}else
						token+=c;
				}
				else
					token+=c;
			i++;
		}
	}
	
	
	private boolean esApertura(String s) {
		boolean es = false;
		if (s.length()>2)
			if(s.charAt(0) == '<' && s.charAt(1) != '/')
				es = true;
		return es;
	}
	
	private boolean corresponden(String a, String c) {
		String sub_a = a.substring(1, a.length()-1);
		String sub_c = c.substring(2, c.length()-1);
		if (sub_a.equals(sub_c))
			return true;
		else
			return false;
	}
	
	private boolean esValida(Queue<String> cola) {
		boolean es_valido = true;
		Stack<String> pila = new PilaArreglo<String>();
		Position<Pair<String,PositionList<String>>> actual = null;
		Position<Pair<String,PositionList<String>>> padre = null;

		while (!cola.isEmpty() && es_valido) {
			try {
				String token2 = cola.dequeue();
				if (esApertura(token2)) {
					pila.push(token2);
					//hacemos un switch para diferentes situaciones
					switch(token2) {
						case "<nombre>":{
							Pair<String,PositionList<String>> entrada = 
									new Pair<String,PositionList<String>>(cola.dequeue(),new ListaDobleEnlace<String>());
							if (padre == null) { //caso raiz
								try {
									arbol.createRoot(entrada);
									actual = arbol.root();		
								} catch (EmptyTreeException | InvalidOperationException e) {
									e.printStackTrace();
								}
							}
							else {
								try {
									actual = arbol.addLastChild(padre, entrada);
								} catch (InvalidPositionException e) {
									e.printStackTrace();
								}
							}
							break;
						}
						case "<archivo>":{
							actual.element().getValue().addLast(cola.dequeue());
							break;
						}
						case "<lista_sub_carpetas>":{
							padre = actual;
							break;
						}
					}
				}
				else {
					if (token2.contains("</")) {
						if (!pila.isEmpty() && corresponden(pila.top(),token2))
							if (!pila.isEmpty())
								pila.pop();
						else
							es_valido = false;
					}
				}
			} catch (EmptyQueueException | EmptyStackException e) {
				e.printStackTrace();
			}
		}
		if (!pila.isEmpty() || !es_valido) {
			es_valido = false;
			arbol = null;
		}
		
		return es_valido;

	}
		
	//------FIN GENERAR-------
	/**
	 * Consulta si la ruta pasada por parametro existe dentro del sistema virtual de archivos.
	 * @param ruta Ruta del sistema virtual de archivos a checkear.
	 * @return Si existe entonces retorna la posicion del ultimo directorio de la ruta pasada por parametro, sino retorna null.
	 */
	private  Position<Pair<String, PositionList<String>>> chequearRuta(String ruta) {
		String separador = Pattern.quote("\\");
		String[] r = ruta.split(separador);
		int i = 0;
		boolean esValido = true;
		boolean estaHijo;
		Position<Pair<String, PositionList<String>>> pos = null;
		try {
			pos = (arbol.root() == null) ? null : arbol.root();
			Position<Pair<String, PositionList<String>>> posHijo = null;
			Iterator<Position<Pair<String, PositionList<String>>>> it;
			boolean termino = true;
			if (r[i].equals(pos.element().getKey())) {
				while (i < r.length && esValido && termino) {
					it = arbol.children(pos).iterator();
					estaHijo = false;
					if (i < r.length - 1) {
						i++;
						while (!estaHijo && it.hasNext()) {
							posHijo = it.next();
							if (posHijo.element().getKey().equals(r[i])) {
								estaHijo = true;
								pos = posHijo;
							}
						}
						if (!estaHijo) {
							esValido = false;
							pos = null;
						}
					} else {
						i = r.length;
					}
				}
			} else {
				pos = null;
			}
		} catch (EmptyTreeException | InvalidPositionException e) {

			e.printStackTrace();
		}
		return pos;
	}
	
	
	/**
	 * Dada la ruta completa dentro del sistema virtual de archivos de un
	 * directorio D, y el nombre de un archivo A, agrega el archivo A al directorio D.
	 * @param ruta Directorio al cual desea agregar el nuevo archivo.
	 * @param nombre Nombre del archivo a agregar.
	 * @throws InvalidPathException Se lanza cuando la ruta que se ingreso no es valida.
	 */
	public void agregarArchivo(String ruta, String nombre) throws InvalidPathException {
		Position<Pair<String, PositionList<String>>> directorio = chequearRuta(ruta);
		if(directorio == null)
			throw new InvalidPathException("La ruta es invalida.");
		
		directorio.element().getValue().addLast(nombre);
	}
	
	/**
	 * Dada la ruta completa dentro del sistema virtual de archivos de un archivo A,
	 *  elimina el archivo A del directorio que lo contiene. En caso de que A no exista,
	 *  la jerarquia no se ve modificada.
	 * @param ruta Ruta completa dentro del sistema virtual de archivos de un archivo A.
	 * @throws InvalidPathException Se lanza cuando la ruta que ingreso no es valida.
	 * @throws InvalidFileException Se lanza si el archivo que indico no existe en el directorio dado.
	 */
		
	public void EliminarArchivo(String ruta)throws InvalidPathException, InvalidFileException {
		String separador = Pattern.quote("\\");//Se agarra la ruta pasada por parametro
		String[] arreglo = ruta.split(separador);
		String archivo = arreglo[arreglo.length - 1];//Se extrae el nombre del archivo a eliminar
		String rutaDirectorio = "";
		for (int i = 0; i < arreglo.length - 1; i++) {//Y se recrea la ruta hacia el archivo
			if (i == arreglo.length - 2)
				rutaDirectorio += arreglo[i];
			else
				rutaDirectorio += arreglo[i] + "\\";
		}
		
		Position<Pair<String, PositionList<String>>> directorio = chequearRuta(rutaDirectorio);
		if(directorio == null)
			throw new InvalidPathException("La ruta es invalida.");
		
		Iterator<Position<String>> it = directorio.element().getValue().positions().iterator();
		Position<String> pos = null; boolean esta=false;
		while (it.hasNext() && !esta) {
			pos = it.next();
			if (pos.element().equals(archivo))
				try {
					esta=true;
					directorio.element().getValue().remove(pos);
				} catch (InvalidPositionException e) {
					System.out.println(e.getMessage());
				}
		}
		if(!esta)
			throw new InvalidFileException("El archivo indicado no existe.");
	}
	
	/**
	 * Dada la ruta completa dentro del sistema virtual de archivos de un directorio D1,
	 *  y el nombre de un directorio D2, agrega el directorio D2 dentro del directorio D1.
	 * @param ruta Ruta completa dentro del sistema virtual de archivos de un directorio D1.
	 * @param dir Nombre de un directorio D2.
	 * @throws InvalidPathException Se lanza cuando la ruta que ingreso no es valida.
	 */
	
	public void AgregarDirectorio(String ruta, String dir)throws InvalidPathException {
		Position<Pair<String, PositionList<String>>> directorio = chequearRuta(ruta);
		if (directorio == null)
			throw new InvalidPathException("La ruta es invalida");
		PositionList<String> lista = new ListaDobleEnlace<String>();
		Pair<String, PositionList<String>> nuevo = new Pair<String, PositionList<String>>(dir, lista);

		try {
			arbol.addLastChild(directorio, nuevo);
		} catch (InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Se encarga de eliminar de la jerarquia todos los sub-Directorios y archivos
	 * del directorio pasado por parametro.
	 * @param par Posicion del directorio del cual se desea eliminar sus sub-directorios y archivos.
	 */
	private void EliminarAux(Position<Pair<String, PositionList<String>>> par) {
		try {
			for (Position<Pair<String, PositionList<String>>> ph : arbol.children(par)) {
				EliminarAux(ph);
				if (arbol.isExternal(ph)) {
					arbol.removeNode(ph);
				}
			}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dada la ruta completa dentro del sistema virtual de archivos de un directorio D,
	 *  elimina el directorio D y todo archivo y sub-directorio con o sin archivos que contenga.
	 * @param ruta Ruta completa dentro del sistema virtual de archivos de un directorio D.
	 * @throws InvalidPathException Se lanza cuando la ruta que ingreso no es valida.
	 */
	
	public void EliminarDirectorio(String ruta) throws InvalidPathException {
		Position<Pair<String, PositionList<String>>> directorio = chequearRuta(ruta);
		if (directorio == null) 
			throw new InvalidPathException("Ruta invalida");

		EliminarAux(directorio);
		try {
			arbol.removeNode(directorio);
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Dada la ruta completa dentro del sistema virtual de archivos de dos directorios D1 y D2, 
	 * elimina el directorio D1 del directorio que actualmente lo contiene y lo mueve dentro del directorio D2.
	 * @param ruta1 Ruta completa dentro del sistema virtual de archivos de un directorio D1.
	 * @param ruta2 Ruta completa dentro del sistema virtual de archivos de un directorio D2.
	 * @throws InvalidPathException Se lanza cuando las rutas ingresadas son invalidas o cuando la ruta1 pertenece al directorio raiz.
	 */
	
	public void MoverDirectorio(String ruta1, String ruta2) throws InvalidPathException{
		Position<Pair<String, PositionList<String>>> d1 = chequearRuta(ruta1);
		Position<Pair<String, PositionList<String>>> d2 = chequearRuta(ruta2);
		try {
			if (d1 != null && d2 != null && d1 != arbol.root()) {
				
				Position<Pair<String,PositionList<String>>>pos=arbol.addLastChild(d2,d1.element());
				arbol.removeNode(d1);							
				EliminarYCrear(d1,pos);		
			}
			else if(d1 == arbol.root()) {
				throw new InvalidPathException("No se puede mover el Directorio raiz.");
			}
			else if(d1 == null && d2 == null){
				throw new InvalidPathException("Ruta 1 y ruta 2 invalidas.");
			}
			else if(d1 == null){
				throw new InvalidPathException("Ruta 1 invalida.");
			}
			else if(d2 == null){
				throw new InvalidPathException("Ruta 2 invalida.");
			}

		} catch (InvalidPositionException | EmptyTreeException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Se encargan de mover los archivos y sub-directorios del directorio D1 y
	 *  al directorio D2.
	 * @param par1 Directorio D1 a eliminar y recrear en D2.
	 * @param par2 Directorio D2 en el que se recreara el directorio D1.
	 */
	private void EliminarYCrear(Position<Pair<String, PositionList<String>>> par1, Position<Pair<String, PositionList<String>>> par2) {
		try {
			for (Position<Pair<String, PositionList<String>>> ph : arbol.children(par1)) {
				Position<Pair<String,PositionList<String>>>pn=ph;
				Position<Pair<String,PositionList<String>>>pos=arbol.addLastChild(par2,pn.element());
				
				EliminarYCrear(ph,pos);
				if (arbol.isExternal(ph)) {
					arbol.removeNode(ph);
				}
			}
			
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Consulta la cantidad de directorios y archivos que posee el sistema virtual de archivos. 
	 * @return Un Pair<Integer,Integer> el cual posee la cant. de directorios y la cant. de archivos respectivamente.
	 */
	
	public Pair<Integer, Integer> CantidadDirectoriosArchivos() {
		int directorios = 0;
		int archivos = 0;
		Iterable<Position<Pair<String, PositionList<String>>>> it = arbol.positions();
		for (Position<Pair<String, PositionList<String>>> p : it) {
			directorios++;
			archivos += p.element().getValue().size();
		}
		Pair<Integer, Integer> salida = new Pair<Integer, Integer>(directorios, archivos);
		return salida;
	}
	
	/**
	 * Genera una lista de strings tal que, lista los directorios y archivos del sistema de archivos por niveles.
	 * @return Una lista de Strings que lista los directorios y archivos del sistema de archivos por niveles.
	 */
	public PositionList<String> ListadoPorNiveles() {
		PositionList<String> salida = new ListaDobleEnlace<String>();
		Queue<Position<Pair<String, PositionList<String>>>> cola1 = new ColaCircular<Position<Pair<String, PositionList<String>>>>();
		Queue<String> cola2 = new ColaCircular<String>();
		salida.addLast("<");
		try {
			cola1.enqueue(arbol.root());
			cola1.enqueue(null);
			while (!cola1.isEmpty()) {
				Position<Pair<String, PositionList<String>>> v = cola1.dequeue();
				if (v != null) {
					salida.addLast(v.element().getKey());// Lista el rotulo de la carpeta
					salida.addLast(", ");
					for (Position<Pair<String, PositionList<String>>> p : arbol.children(v))
						cola1.enqueue(p);
					Iterator<String> archivos = v.element().getValue().iterator();
					String act;

					while (archivos.hasNext()) {
						act = archivos.next();
						cola2.enqueue(act);
					}

				} else {
					salida.addLast("\\ ");
					if (!cola1.isEmpty())
						cola1.enqueue(null);
					while (!cola2.isEmpty()) {
						String w = cola2.dequeue();
						if (w != null)
							salida.addLast(w + ", ");
					}
				}

			}

			
		} catch (EmptyTreeException | EmptyQueueException | InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		salida.addLast(">");
		return salida;
	}
	
	/**
	 * Genera un diccionario que modela como claves las extensiones de los archivos,
	 *  y como valores los nombres de los archivos correspondientes a dicha extensión.
	 *  @return Un diccionario que modela como claves las extensiones de los archivos, y como valores los nombres de los archivos correspondientes a dicha extensión.
	 */
	
	public  Dictionary<String, String> ListadoPorExtension() {
		Dictionary<String, String> dicc = new DiccionarioHashAbierto<String, String>();
		Iterable<Position<Pair<String, PositionList<String>>>> posiciones = arbol.positions();
		try {
			for (Position<Pair<String, PositionList<String>>> p : posiciones) {
				PositionList<String> archivos = p.element().getValue();
				Iterator<String> it = archivos.iterator();
				while (it.hasNext()) {
					String separador = Pattern.quote(".");
					String[] partes = it.next().split(separador);
					dicc.insert(partes[1], partes[0]);
				}
			}
		} catch (InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
		return dicc;
	}
	
	
	
	/**
	 * Genera un mapeo que modela como claves las rutas completas de cada uno de los directorios, 
	 * y como valores la profundidad que dichos directorios poseen en la jeraquia.
	 * @return Un mapeo que modela como claves las rutas completas de cada uno de los directorios, y como valores la profundidad que dichos directorios.
	 */
	public Map<String, Integer> ListadoPorProfundidad() {
		Map<String, Integer> map = new mapeoConHashCerrado<String, Integer>();
		Iterable<Position<Pair<String, PositionList<String>>>> posiciones = arbol.positions();
		for (Position<Pair<String, PositionList<String>>> p : posiciones) {
			try {
				map.put(rutaCompleta(p), profundidad(p));
			} catch (InvalidKeyException e) {
				System.out.println(e.getMessage());
			}

		}

		return map;
	}
	
	private int profundidad(Position<Pair<String, PositionList<String>>> p) {
		int salida = 0;
		try {
			if (p == arbol.root())
				salida = 0;
			else {
				salida = 1 + profundidad(arbol.parent(p));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return salida;
	}

	private String rutaCompleta(Position<Pair<String, PositionList<String>>> directorio) {

		return rutaCompletaAux(directorio);
	}

	private String rutaCompletaAux(Position<Pair<String, PositionList<String>>> directorio) {
		String salida = directorio.element().getKey();
		try {
			if (directorio != arbol.root()) {

				salida = rutaCompletaAux(arbol.parent(directorio)) + "\\" + salida;

			}
		} catch (EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage());
		}

		return salida;

	}
	
}
	
	

	
	
	
