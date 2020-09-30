package TDAArbol;

import java.util.Iterator;

import TDALista.BoundaryViolationException;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDobleEnlace;
import TDALista.Nodo;
import TDALista.Position;
import TDALista.PositionList;

/**
 * Implementacion del TDAArbol

 *
 */
public class Arbol<E> implements Tree<E>{
	protected TNodo<E> root;
	protected int size;
	
	/**
	 * Constructor del arbol, inicializa con tamaño 0 y sin referencia a raiz
	 */
	public Arbol() {
		root=null;
		size=0;
	}
	
	/**
	 * Consulta la cantidad de nodos en el �rbol.
	 * @return Cantidad de nodos en el �rbol.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Consulta si el �rbol est� vac�o.
	 * @return Verdadero si el �rbol est� vac�o, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	/**
	 * Devuelve un iterador de los elementos almacenados en el �rbol en preorden.
	 * @return Iterador de los elementos almacenados en el �rbol.
	 */
	public Iterator<E> iterator() {
		PositionList<E> l = new ListaDobleEnlace<E>();
		for( Position<E> p : positions() ) {
			l.addLast( p.element() );
		}
		return l.iterator();
	}
	
	/**
	 * Devuelve una colecci�n iterable de las posiciones de los nodos del �rbol.
	 * @return Colecci�n iterable de las posiciones de los nodos del �rbol.
	 */
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> l = new ListaDobleEnlace<Position<E>>();
		if( size!=0 )
			preOrden( l, root );
		
		return l;
	}
	
	/**
	 * Se encarga de recorrer y agregar los nodos de un arbol con raiz r, en preorden,  a una lista l pasada por parametro
	 * @param l Coleccion vacia que va a ser llenada con la posicion de los nodos
	 * @param r Representa el cursor de la raíz del subárbol listado actualmente
	 */
	private void preOrden(PositionList<Position<E>> l, TNodo<E> r) {
		l.addLast( r );
		for( TNodo<E> h : r.getHijos()) {
			preOrden( l, h );
		}	
	}
	
	/**
	 * Reemplaza el elemento almacenado en la posici�n dada por el elemento pasado por par�metro. Devuelve el elemento reemplazado.
	 * @param v Posici�n de un nodo.
	 * @param e Elemento a reemplazar en la posici�n pasada por par�metro.
	 * @return Elemento reemplazado.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
	 */
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> nodo= checkPosition(v);
		E item= nodo.element();
		nodo.setElemento(e);
		
		return item;
	}
	
	/**
	 * Devuelve la posici�n de la ra�z del �rbol.
	 * @return Posici�n de la ra�z del �rbol.
	 * @throws EmptyTreeException si el �rbol est� vac�o.
	 */
	public Position<E> root() throws EmptyTreeException {
		if(size==0) {
			throw new EmptyTreeException("Empty tree.");
		}
		return root;
	}
	
	/**
	 * Devuelve la posici�n del nodo padre del nodo correspondiente a una posici�n dada.
	 * @param v Posici�n de un nodo.
	 * @return Posici�n del nodo padre del nodo correspondiente a la posici�n dada.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
	 * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde a la ra�z del �rbol.
	 */
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> nodo= checkPosition(v);
		if(nodo==root) 
			throw new BoundaryViolationException("Parent: raiz del arbol.");
		return nodo.getPadre();
	}
	
	/**
	 * Devuelve una colecci�n iterable de los hijos del nodo correspondiente a una posici�n dada.
	 * @param v Posici�n de un nodo.
	 * @return Colecci�n iterable de los hijos del nodo correspondiente a la posici�n pasada por par�metro.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
	 */
	public Iterable<Position<E>> children( Position<E> v ) throws InvalidPositionException {
			TNodo<E> p = checkPosition(v);
			PositionList<Position<E>> lista =new ListaDobleEnlace<Position<E>>();//la lista doble enlace era de tipo TNodo<E>
			for( TNodo<E> n : p.getHijos() ) {
				lista.addLast(n);
			}
			return lista;
	}
	
	/**
	 * Consulta si una posici�n corresponde a un nodo interno.
	 * @param v Posici�n de un nodo.
	 * @return Verdadero si la posici�n pasada por par�metro corresponde a un nodo interno, falso en caso contrario.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
	 */
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo= checkPosition(v);
		return !nodo.getHijos().isEmpty();
	}
	
	/**
	 * Consulta si una posici�n dada corresponde a un nodo externo.
	 * @param v Posici�n de un nodo.
	 * @return Verdadero si la posici�n pasada por par�metro corresponde a un nodo externo, falso en caso contrario.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
	 */
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo= checkPosition(v);
		return nodo.getHijos().isEmpty();
	}
	
	/**
	 * Consulta si una posici�n dada corresponde a la ra�z del �rbol.
	 * @param v Posici�n de un nodo.
	 * @return Verdadero, si la posici�n pasada por par�metro corresponde a la ra�z del �rbol,falso en caso contrario.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida.
	 */
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo= checkPosition(v);
		return nodo==root;
	}
	
	/**
	 * Crea un nodo con r�tulo e como ra�z del �rbol.
	 * @param E R�tulo que se asignar� a la ra�z del �rbol.
	 * @throws InvalidOperationException si el �rbol ya tiene un nodo ra�z.
	 */
	public void createRoot(E e) throws InvalidOperationException {
		if(root!=null) {
			throw new InvalidOperationException("The tree already has a root.");
		}
		root= new TNodo<E>(e);
		size=1;
	}
	
	/**
	 * Agrega un nodo con r�tulo e como primer hijo de un nodo dado.
	 * @param e R�tulo del nuevo nodo.
	 * @param padre Posici�n del nodo padre.
	 * @return La posici�n del nuevo nodo creado.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o el �rbol est� vac�o.
	 */
	public Position<E> addFirstChild(Position<E> p, E e) throws	InvalidPositionException {
		TNodo<E> padre= checkPosition(p);
		if(size==0) {
			throw new InvalidPositionException("Empty tree.");
		}
		
		TNodo<E> nodo= new TNodo<E>(e, padre);
		padre.getHijos().addFirst(nodo);
		size++;
		return nodo;
	}
	
	/**
	 * Agrega un nodo con r�tulo e como �timo hijo de un nodo dado.
	 * @param e R�tulo del nuevo nodo.
	 * @param p Posici�n del nodo padre.
	 * @return La posici�n del nuevo nodo creado.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o el �rbol est� vac�o.
	 */
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		if(size==0) 
			throw new InvalidPositionException("Empty tree.");
		
		TNodo<E> padre= checkPosition(p);
		TNodo<E> nodo=new TNodo<E>(e,padre);
		padre.getHijos().addLast(nodo);
		size++;
		return nodo;
	}
	
	/**
	 * Agrega un nodo con r�tulo e como hijo de un nodo padre dado. El nuevo nodo se agregar� delante de otro nodo tambi�n dado.
	 * @param e R�tulo del nuevo nodo.
	 * @param p Posici�n del nodo padre.
	 * @param rb Posici�n del nodo que ser� el hermano derecho del nuevo nodo.
	 * @return La posici�n del nuevo nodo creado.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida, o el �rbol est� vac�o, o la posici�n rb no corresponde a un nodo hijo del nodo referenciado por p.
	 */
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		if(size<2)
			throw new InvalidPositionException("Empty tree.");
		
		TNodo<E> padre= checkPosition(p);
		TNodo<E> hermanoDerecho= checkPosition(rb);
		TNodo<E> nodo= new TNodo<E>(e,padre);
		
		try {
			PositionList<TNodo<E>> hijosPadre= padre.getHijos();
			boolean encontre=false;
			Position<TNodo<E>> pp= hijosPadre.first();
			
			while(pp!=null && !encontre) {
				if(pp.element()==hermanoDerecho)
					encontre=true;
				else 
					pp=(pp==hijosPadre.last()) ? null : hijosPadre.next(pp);
			}
			
			if(!encontre)
				throw new InvalidPositionException("p no es el padre de rb");
			hijosPadre.addBefore(pp, nodo);
			size++;
			
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException f) {
			throw new InvalidPositionException("...");
		}
		return nodo;
	}
	
	/**
	 * Agrega un nodo con r�tulo e como hijo de un nodo padre dado. El nuevo nodo se agregar� a continuaci�n de otro nodo tambi�n dado.
	 * @param e R�tulo del nuevo nodo.
	 * @param p Posici�n del nodo padre.
	 * @param lb Posici�n del nodo que ser� el hermano izquierdo del nuevo nodo.
	 * @return La posici�n del nuevo nodo creado.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida, o el �rbol est� vac�o, o la posici�n lb no corresponde a un nodo hijo del nodo referenciado por p.
	 */
	public Position<E> addAfter (Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		if(size<2)
			throw new InvalidPositionException("Empty tree.");
		
		TNodo<E> padre= checkPosition(p);
		TNodo<E> hermanoIzq= checkPosition(lb);
		TNodo<E> nodo= new TNodo<E>(e,padre);
		
		try {
			PositionList<TNodo<E>> hijosPadre= padre.getHijos();
			boolean encontre=false;
			Position<TNodo<E>> pp= hijosPadre.first();
			
			while(pp!=null && !encontre) {
				if(pp.element()==hermanoIzq)
					encontre=true;
				else 
					pp=(pp==hijosPadre.last()) ? null : hijosPadre.next(pp);
			}
			
			if(!encontre)
				throw new InvalidPositionException("p no es el padre de rb");
			hijosPadre.addAfter(pp, nodo);
			size++;
			
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException f) {
			throw new InvalidPositionException("...");
		}
		return nodo;
	}
	
	/**
	 * Elimina el nodo referenciado por una posici�n dada, si se trata de un nodo externo. 
	 * @param n Posici�n del nodo a eliminar.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o no corresponde a un nodo externo, o el �rbol est� vac�o.
	 */
	public void removeExternalNode (Position<E> p) throws InvalidPositionException{
		if(isInternal(p)){
			throw new InvalidPositionException("");
		}
		try {
			TNodo<E> nodo = checkPosition(p);
			if(size==1) {
				root=null;
			}
			else {
				TNodo<E> padre=nodo.getPadre();
				PositionList<TNodo<E>> hermanos=padre.getHijos();
				boolean es=false; Position<TNodo<E>> pos=hermanos.first();
				while (!es) {
					if(pos.element()==nodo || pos==hermanos.last()) {
						es=true;
					}
					else {
						pos=hermanos.next(pos);
					}
				}
				hermanos.remove(pos);	
			}
		}
		catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			throw new InvalidPositionException("");
		}
	
		size--;
	}
		
	/**
	 * Elimina el nodo referenciado por una posici�n dada, si se trata de un nodo interno. Los hijos del nodo eliminado lo reemplazan en el mismo orden en el que aparecen. 
	 * Si el nodo a eliminar es la ra�z del �rbol,  �nicamente podr� ser eliminado si tiene un solo hijo, el cual lo reemplazar�.
	 * @param n Posici�n del nodo a eliminar.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o no corresponde a un nodo interno o corresponde a la ra�z (con m�s de un hijo), o el �rbol est� vac�o.
	 */
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if(isExternal(p))
			throw new InvalidPositionException("El nodo es externo");
		
		TNodo<E> nodo = checkPosition(p);
		try {
			if(nodo==root) {
				if(nodo.getHijos().size()>1) {
					throw new InvalidPositionException("");
				}
				if(nodo.getHijos().size()==1) {
					root=nodo.getHijos().remove(nodo.getHijos().first());
					root.setPadre(null);
				}
			}
			else {
				TNodo<E> padre=nodo.getPadre();
				PositionList<TNodo<E>> hermanos=padre.getHijos();
				PositionList<TNodo<E>> hijos=nodo.getHijos();
				boolean es=false; Position<TNodo<E>> pos=hermanos.first();
				while (!es) {
					if(pos.element()==nodo || pos==hermanos.last()) {
						es=true;
					}
					else {
						pos=hermanos.next(pos);
					}
				}
				for(TNodo<E> hijo:hijos) {
					hermanos.addBefore(pos, hijo);
					hijo.setPadre(padre);
				}
				hermanos.remove(pos);	
			}
		}
		catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			throw new InvalidPositionException("");
		}
		
		size--;
	}
	
	/**
	 * Elimina el nodo referenciado por una posici�n dada. Si se trata de un nodo interno. Los hijos del nodo eliminado lo reemplazan en el mismo orden en el que aparecen. 
	 * Si el nodo a eliminar es la ra�z del �rbol,  �nicamente podr� ser eliminado si tiene un solo hijo, el cual lo reemplazar�.
	 * @param n Posici�n del nodo a eliminar.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o corresponde a la ra�z (con m�s de un hijo), o el �rbol est� vac�o.
	 */
	public void removeNode(Position<E> p) throws InvalidPositionException
	{
		TNodo<E> pos = checkPosition(p);
		
		if (isInternal(pos))
			removeInternalNode(pos);
		else
			removeExternalNode(pos);
	}
	
	/**
	 * Consulta que la posicion pasada por parametro sea valida y retorna el nodo asociado a la misma.
	 * @param v Posicion de un nodo.
	 * @return El nodo asociado a la posicion pasada por parametro.
	 * @throws InvalidPositionException si la posicion pasada por parametro es invalida.
	 */
	private TNodo<E> checkPosition(Position<E> v) throws InvalidPositionException {
		try {
			if(v==null) {
				throw new InvalidPositionException("Invalid position.");
			}
			return (TNodo<E>) v;
		} catch(ClassCastException e) {
			throw new InvalidPositionException( "...." );
		}	
	}
}
