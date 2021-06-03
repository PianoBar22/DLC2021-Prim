package soporte;

import java.util.LinkedList;

public class UndirectedGraph<T> extends Graph<T> 
{
    /**
     * Crea un grafo no dirigido, con lista de vértices vacía, lista de arcos 
     * vacía y sin permitir arcos paralelos.
     */
    public UndirectedGraph() 
    {
    }
    
    /**
     * Crea un grafo no dirigido con lista de vértices vacía y lista de arcos 
     * vacía. El grafo permite arcos paralelos si el parámetro p es true, y no 
     * los permite si p es false.
     * @param p true: se permiten arcos paralelos.
     */
    public UndirectedGraph(boolean p)
    {
        super(p);
    }
            

    /**
     * Crea un grafo no dirigido cuya lista de vértices será <b>v</b> y cuya 
     * lista de arcos será <b>a</b>, sin permitir arcos paralelos. El método no 
     * controla si las listas de entrada contienen objetos válidos. Si alguna de 
     * las dos listas de entrada es null, la lista correspondiente se creará 
     * vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     */
    public UndirectedGraph(LinkedList< Node<T> > v, LinkedList< Arc<T> > a) 
    {
        super(v, a);
    }

    /**
     * Crea un grafo no dirigido cuya lista de vértices será <b>v</b> y cuya 
     * lista de arcos será <b>a</b>. El parámetro p indica si el grafo aceptará 
     * arcos paralelos (p = true) o no (p = false). El método no controla si las 
     * listas de entrada contienen objetos válidos. Si alguna de las dos listas 
     * de entrada es null, la lista correspondiente se creará vacía.
     * @param v la lista de vértices a almacenar en el grafo.
     * @param a la lista de arco a almacenar en el grafo.
     * @param p true: el grafo acepta arcos paralelos.
     */
    public UndirectedGraph(LinkedList< Node<T> > v, LinkedList< Arc<T> > a, boolean p) 
    {
        super(v, a, p);
    }
   
    /**
     * Crea un arco no dirigido con in como primer vértice y en como segundo 
     * vértice. El peso del arco será w. No comprueba si las referencias in y en
     * son null.
     * @param in el vértice inicial.
     * @param en el vértice final. 
     * @param w el peso del arco
     * @return el arco creado.
     */
    @Override
    public Arc<T> createArc(Node <T> in, Node <T> en, int w)
    {
        return new UndirectedArc(in, en, w);
    }
    
    /**
     * Busca un Arbol de Expansión Mínimo para el grafo, aplicando el algoritmo 
     * de Prim, y retorna el valor de la suma de los pesos de sus arcos. Se 
     * asume que los arcos pueden tener pesos negativos, cero o positivos 
     * indistintamente. El algoritmo utiliza un Heap para la extracción del arco 
     * de mínimo peso. Se asume que el grafo es conexo.
     * @return la suma de pesos del Arbol de Expansión Mínimo.
     */
    public long getMSTValue_Prim()
    {
        long suma = 0;
        
        // un subconjunto de vertices, con un solo vertice cualquiera... 
        LinkedList<Node<T>> x = new LinkedList<>();
        Node<T> s = vertices.getFirst();
        x.add(s);
        
        // un heap ascendente, con todos los arcos incidentes a ese primer unico nodo...
        Heap<Arc<T>> h = new Heap<>();
        LinkedList<Arc<T>> se = s.getArcs();
        for(Arc<T> e : se) { h.add(e); }
        
        // la lista de arcos que formaran el AEM, inicialmente vacía...
        LinkedList<Arc<T>> t = new LinkedList<>();
        
        // seguir hasta que x contenga todos los vértices del grafo original...
        while(x.size() != vertices.size())
        {      
            // tomar del heap el arco con menor costo...
            // ... pero controlar que x no contenga a ambos vértices... (el grafo puede tener arcos paralelos...)
            Arc mce;
            boolean ok;
            do
            {
                mce = (Arc<T>) h.remove();
                Node n1 = mce.getInit();
                Node n2 = mce.getEnd();
                ok = (x.contains(n1) && !x.contains(n2)) || (x.contains(n2) && !x.contains(n1));
            }
            while( ! h.isEmpty() && ! ok );
 
            // si el heap se vació sin darme un arco bueno, corto el proceso y retorno la suma como estaba...
            if( ! ok ) { break; }
            
            // añadir el arco al AEM...
            t.add(mce);
            
            // añadir el otro nodo incidente de ese arco al conjunto x...
            Node<T> y = mce.getInit();
            if(x.contains(y)) { y = mce.getEnd(); }
            x.add(y);
            
            // actualizar el heap, agregando los arcos que conecten al nodo "y" con {vertices - x}...
            LinkedList<Arc<T>> ye = y.getArcs();
            for(Arc<T> e : ye)
            {
                // para el arco "e", tomar el extremo que no es "y" ("y" ya está en x)... 
                Node<T> ny = e.getInit();
                if(ny.equals(y)) { ny = e.getEnd(); }
                
                // si ese extremo "ny" no está en x, entonces "e" es un arco de cruce y debe agregarse al heap "h"...
                if(! x.contains(ny)) { h.add(e); }
            }
            
            // finalmente, actualizar el valor de la suma de pesos y regresar al ciclo...
            suma += mce.getWeight();
        }
        
        // ... por fin, devolver la suma de pesos del AEM
        return suma;
    }
}

