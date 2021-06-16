package soporte;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;

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
    public UndirectedGraph(ArrayList< Node<T> > v, ArrayList< Arc<T> > a) 
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
    public UndirectedGraph(ArrayList< Node<T> > v, ArrayList< Arc<T> > a, boolean p) 
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
        LinkedList<Node<T>> procesados = new LinkedList<>();
        Node<T> nodo = vertices.get(0);
        procesados.add(nodo);
        
        // un heap ascendente, con todos los arcos incidentes a ese primer unico nodo...
        Heap<Arc<T>> heapArcos = new Heap<>();
        ArrayList<Arc<T>> se = nodo.getArcs();
        se.forEach(e -> {
            heapArcos.add(e);
        });
        
        // la lista de arcos que formaran el AEM, inicialmente vacía...
        LinkedList<Arc<T>> arcosAEM = new LinkedList<>();
        
        // seguir hasta que x contenga todos los vértices del grafo original...
        while(procesados.size() != vertices.size())
        {      
            // tomar del heap el arco con menor costo...
            // ... pero controlar que x no contenga a ambos vértices... (el grafo puede tener arcos paralelos...)
            Arc mce;
            boolean ok;
            do
            {
                mce = (Arc<T>) heapArcos.remove();
                Node n1 = mce.getInit();
                Node n2 = mce.getEnd();
                
                ok = (procesados.contains(n1) && !procesados.contains(n2)) || 
                        (procesados.contains(n2) && !procesados.contains(n1));
            }
            while( ! heapArcos.isEmpty() && ! ok );
 
            // si el heap se vació sin darme un arco bueno, corto el proceso y retorno la suma como estaba...
            if( ! ok ) { break; }
            
            // añadir el arco al AEM...
            arcosAEM.add(mce);
            
            // añadir el otro nodo incidente de ese arco al conjunto x...
            Node<T> y = mce.getInit();
            if(procesados.contains(y)) { y = mce.getEnd(); }
            procesados.add(y);
            
            // actualizar el heap, agregando los arcos que conecten al nodo "y" con {vertices - x}...
            ArrayList<Arc<T>> ye = y.getArcs();
            for(Arc<T> e : ye)
            {
                // para el arco "e", tomar el extremo que no es "y" ("y" ya está en x)... 
                Node<T> ny = e.getInit();
                if(ny.equals(y)) { ny = e.getEnd(); }
                
                // si ese extremo "ny" no está en x, entonces "e" es un arco de cruce y debe agregarse al heap "h"...
                if(! procesados.contains(ny)) { heapArcos.add(e); }
            }
            
            // finalmente, actualizar el valor de la suma de pesos y regresar al ciclo...
            suma += mce.getWeight();
        }
        
        // ... por fin, devolver la suma de pesos del AEM
        return suma;
    }
    
    public UndirectedGraph<T> getMSTValue_Prim_NEW(){
        long suma = 0;
        // un subconjunto de vertices, con un solo vertice cualquiera... 
        // En cuanto a tiempos de busqueda la estructura mas optima es
        // el HashMap, luego tambien podria ser un HashSet ya que ambos
        // tienen O(1) en la busqueda pero comparandolos el HashMap es 
        // mas rapido
        HashMap<Node<T>, Boolean> procesados = new HashMap<>();
        
        // Es el grafo que formaran el AEM, inicialmente con los nodos
        // sin arcos asignados...
        UndirectedGraph<T> grafoAEM = new UndirectedGraph<>();
        //creo un nuevo nodo para que no arrastre los arcos de cada nodo
        this.vertices.forEach(nodo -> grafoAEM.add(new Node(nodo.getValue())));

        // un heap ascendente, con todos los arcos incidentes a ese primer unico nodo...
        Heap<Arc<T>> heapArcos = new Heap<>();

        //Recorro todos los nodos para poder acceder a cada uno 
        //de los nodos en caso de que el grafo sea no conexo
        //con esto me aseguro que todos los nodos fueran procesados
        for(Node nodo : vertices){
            //esto va a devolver true en el caso de que procesados este
            //vacio y cuando queda algun nodo no conexo
            if(!procesados.containsKey(nodo)){
                // agrego el nodo de la lista
                procesados.put(nodo, true);

                ArrayList<Arc<T>> se = nodo.getArcs();
                se.forEach(e -> {
                    heapArcos.add(e);
                });

                // seguir hasta que x contenga todos los vértices del grafo original...
                //!heapArcos.isEmpty() es por el caso en que sea un grafo de un solo nodo
                //por lo cual no tiene arcos
                while(procesados.size() != vertices.size() && !heapArcos.isEmpty())
                {      
                    // tomar del heap el arco con menor costo...
                    // ... pero controlar que x no contenga a ambos vértices... (el grafo puede tener arcos paralelos...)
                    Arc mce;
                    boolean ok;
                    //utilizo estas variables booleanas para tener ya cargado
                    //si los nodos del arco que se analiza estan procesados
                    // y utilizarlos en el momento que haga falta sin tener que 
                    // volver a busacarlos
                    boolean containInit;
                    boolean containEnd;
                    
                    do
                    {
                        mce = (Arc<T>) heapArcos.remove();
                        Node n1 = mce.getInit();
                        Node n2 = mce.getEnd();
                        
                        containInit = procesados.containsKey(n1);
                        containEnd = procesados.containsKey(n2);
                        
                        ok = (containInit && !containEnd) || 
                                (containEnd && !containInit);
                    }
                    while( ! heapArcos.isEmpty() && ! ok );

                    // si el heap se vació sin darme un arco bueno, corto el proceso y retorno la suma como estaba...
                    // y va a continuar recorriendo los nodos por si hay alguna otra componente conexa que no se haya
                    // recorrido
                    if( ! ok ) { break; }

                    // añadir el arco al AEM...
                    //el add de los arcos crea un arco nuevo
                    //con los nodos que pertenecen al grafoAEM
                    grafoAEM.add(mce);

                    // añadir el otro nodo incidente de ese arco al conjunto x...
                    Node<T> y = mce.getInit();
                    
                    if(containInit) { y = mce.getEnd(); }
                    procesados.put(y, true);

                    // actualizar el heap, agregando los arcos que conecten al nodo "y" con {vertices - x}...
                    ArrayList<Arc<T>> ye = y.getArcs();
                    for(Arc<T> e : ye)
                    {
                        // para el arco "e", tomar el extremo que no es "y" ("y" ya está en x)... 
                        Node<T> ny = e.getInit();
                        if(ny.equals(y)) { ny = e.getEnd(); }

                        // si ese extremo "ny" no está en x, entonces "e" es un arco de cruce y debe agregarse al heap "h"...
                        // el hecho de controlar que no esta en x y no insertarlo directamente es que el contains es de orden
                        // constante y el add del heap tiene orden logaritmico, entonces es mas barato controlar que insertarlo
                        // y de esta manera mejoramos la performance
                        if(! procesados.containsKey(ny)) { heapArcos.add(e); }
                    }

                    // finalmente, actualizar el valor de la suma de pesos y regresar al ciclo...
                    suma += mce.getWeight();
                }
            }
        }
        
        
        // ... por fin, devolver la suma de pesos del AEM para todas las componentes conexas
        // del grafo
        return grafoAEM;
    }
    
    public long getMSTValue_Prim_NEW_V2(){
        // Utiliza la estructura Union-Find para encontrar las componentes conexas
        // y las cantidades de nodos de cada componente
        // se ejecuta el algoritmo de prim tantas veces como grupos haya empezando
        // por cada lider del grupo
        
        //pareciera ser una buena opcion ya que usa union-find pero comparando tiempos
        //de ejecucion es mas lento que la version V1
        //Habria que hacer una pruba con una gran cantidad de nodos, arcos y componentes conexas
        long suma = 0;
        //O(log(n))
        Heap<Arc<T>> heapArcosOrd = this.getArcsOrder();
        
        //O(n) -- recorre todos los nodos para inicializar el union-find
        UnionFind<T> uf = new UnionFind<>(this.vertices);
        
        while(!heapArcosOrd.isEmpty()){
            Arc arc = heapArcosOrd.remove();
            uf.union(arc.getInit(), arc.getEnd());
        }
        
        //seria la misma logica que el algoritmo de prim de la V1
        //con la diferencia de que se corta cuando se procesaron la cantidad de
        //nodos es la que nos devuelve el UnionFind(objeto uf)
        for(int i = 0 ; i < this.vertices.size() ; i++){
            //obtengo el valor del item en la posicion del nodo
            //para saber si es lider o no
            int item = uf.getItem(i);
            
            if(item < 0){ //si es lider tiene la cantidad de items del grupo en negativo
                // un subconjunto de vertices, con un solo vertice cualquiera... 
                // En cuanto a tiempos de busqueda la estructura mas optima es
                // el HashMap, luego tambien podria ser un HashSet ya que ambos
                // tienen O(1) en la busqueda pero comparandolos el HashMap es 
                // mas rapido
                HashMap<Node<T>, Boolean> procesados = new HashMap<>();

                // la lista de arcos que formaran el AEM, inicialmente vacía...
                ArrayList <Arc<T>> arcosAEM = new ArrayList<>();

                // un heap ascendente, con todos los arcos incidentes a ese primer unico nodo...
                Heap<Arc<T>> heapArcos = new Heap<>();
                
                Node nodo = this.vertices.get(i);
                // agrego el nodo de la lista
                procesados.put(nodo, true);

                ArrayList<Arc<T>> se = nodo.getArcs();
                se.forEach(e -> {
                    heapArcos.add(e);
                });

                // seguir hasta que procesados contenga todos los vértices de la componente conexa...
                while(procesados.size() != Math.abs(item))
                {      
                    // tomar del heap el arco con menor costo...
                    // ... pero controlar que x no contenga a ambos vértices... (el grafo puede tener arcos paralelos...)
                    Arc mce;
                    boolean ok;
                    //utilizo estas variables booleanas para tener ya cargado
                    //si los nodos del arco que se analiza estan procesados
                    // y utilizarlos en el momento que haga falta sin tener que 
                    // volver a busacarlos
                    boolean containInit;
                    boolean containEnd;
                    
                    do
                    {
                        mce = (Arc<T>) heapArcos.remove();
                        Node n1 = mce.getInit();
                        Node n2 = mce.getEnd();
                        
                        containInit = procesados.containsKey(n1);
                        containEnd = procesados.containsKey(n2);
                        
                        ok = (containInit && !containEnd) || 
                                (containEnd && !containInit);
                    }
                    while( ! heapArcos.isEmpty() && ! ok );

                    // si el heap se vació sin darme un arco bueno, corto el proceso y retorno la suma como estaba...
                    if( ! ok ) { break; }

                    // añadir el arco al AEM...
                    arcosAEM.add(mce);

                    // añadir el otro nodo incidente de ese arco al conjunto x...
                    Node<T> y = mce.getInit();
                    
                    if(containInit) { y = mce.getEnd(); }
                    procesados.put(y, true);

                    // actualizar el heap, agregando los arcos que conecten al nodo "y" con {vertices - x}...
                    ArrayList<Arc<T>> ye = y.getArcs();
                    for(Arc<T> e : ye)
                    {
                        // para el arco "e", tomar el extremo que no es "y" ("y" ya está en x)... 
                        Node<T> ny = e.getInit();
                        if(ny.equals(y)) { ny = e.getEnd(); }

                        // si ese extremo "ny" no está en x, entonces "e" es un arco de cruce y debe agregarse al heap "h"...
                        // el hecho de controlar que no esta en x y no insertarlo directamente es que el contains es de orden
                        // constante y el add del heap tiene orden logaritmico, entonces es mas barato controlar que insertarlo
                        // y de esta manera mejoramos la performance
                        if(! procesados.containsKey(ny)) { heapArcos.add(e); }
                    }

                    // finalmente, actualizar el valor de la suma de pesos y regresar al ciclo...
                    suma += mce.getWeight();
                }
            }
        }
        
        return suma;
    }
    
    public long corte_minimo() throws CloneNotSupportedException{
        UndirectedGraph<String> G = (UndirectedGraph<String>) this.clone();
        G.allow_parallel_arcs = true;
        
        while(G.countNodes() > 2){
            Arc<String> arc = G.getRandomArc();
            Node<String> nodeCompress = new Node<>(arc.getInit().getValue() + "-" + arc.getEnd().getValue());
            
            G.reasignarNodo(arc.getInit(), nodeCompress);
            G.reasignarNodo(arc.getEnd(), nodeCompress);
            G.add(nodeCompress);
            G.eliminarAutoCiclos();
        }
        
        return G.countEdges();
    }

    private void reasignarNodo(Node<T> oldNode, Node<T> newNode) {
        for(Arc<T> arc : oldNode.getArcs()){
            if(arc.getInit().equals(oldNode)){
                arc.setInit(newNode);
            }
            else
            {
                arc.setEnd(newNode);
            }
            newNode.getArcs().add(arc);
        }
        this.vertices.remove(oldNode);
    }

    private void eliminarAutoCiclos() {
        ArrayList<Arc<T>> toRemove = new ArrayList<>();
        for(Arc<T> arc : this.getArcs()){
            if(arc.getInit().equals(arc.getEnd())){
                toRemove.add(arc);
            }
        }
        
        for(Arc<T> arc : toRemove){
            this.getArcs().remove(arc);
            arc.getInit().getArcs().remove(arc);
            arc.getEnd().getArcs().remove(arc);
        }
    }
}

