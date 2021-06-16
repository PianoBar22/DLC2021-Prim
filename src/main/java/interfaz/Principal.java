package interfaz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import soporte.*;

/**
 * Una clase para contener un main de prueba para la implementación de grafos
 * por listas de adyacencia.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Marzo de 2014.
 */
public class Principal 
{
    public static void main(String args[]) throws IOException, InterruptedException
    {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

    	do
    	{
            System.out.println("-----------------------////////////-----------------------");
            System.out.println("--------------------MENU DE OPCIONES----------------------");
            System.out.println("-----------------------////////////-----------------------");
    	    System.out.println ("1. Ejemplo 1 - Grafo conexo");
    	    System.out.println ("2. Ejemplo 2 - Grafo no conexo - 2 componentes");
    	    System.out.println ("3. Ejemplo 4 - grafo desafio corte minimo");
    	    System.out.println ("4. Test Big O");
    	    System.out.println ("-1. Salir");
            System.out.println ("Ingrese opcion: ");
	    opcion = sc.nextInt();
            switch(opcion){
                case 1:
                    TestPrimUg1Conexo();
                    break;
                case 2:
                    TestPrimUg2NoConexo3Comp();
                    break;
                case 3:
                    TestPrimDesafioCorteMinimo();
                    break;
                case 4:
                    TestBigO();
                    break;
                    
            }
            
            System.out.println("-----------------------////////////-----------------------");
            System.out.println("----------------------FIN EJECUCION-----------------------");
            System.out.println("-----------------------////////////-----------------------");
            System.out.println ("\n\n");
        } while(opcion != -1);
        
    }

    private static void TestPrimUg1Conexo() {
        try {
            UndirectedGraph <String> ug1 = new UndirectedGraph<>(true);
            ug1.add("a");
            ug1.add("b");
            ug1.add("c");
            ug1.add("d");
            ug1.add("e");
            ug1.add("f");
            ug1.add("g");
            ug1.add("h");
            
            ug1.addArc("a", "b", 4);
            ug1.addArc("a", "d", 3);
            ug1.addArc("a", "e", 2);
            ug1.addArc("b", "c", 1);
            ug1.addArc("b", "d", 2);
            ug1.addArc("c", "d", 4);
            ug1.addArc("c", "f", 2);
            ug1.addArc("d", "e", 2);
            ug1.addArc("d", "f", 3);
            ug1.addArc("e", "f", 1);
            ug1.addArc("e", "g", 3);
            ug1.addArc("g", "h", 5);
            
            System.out.println("-----------Grafo Original (Conexo)-------------");
            System.out.println(ug1);
            System.out.println("-----------------------------------------------");
            System.out.println();
            
            UndirectedGraph <String> ugAEM = ug1.getMSTValue_Prim_NEW();
            long suma = 0;
            for (Arc<String> arco : ugAEM.getArcs()){
                suma += arco.getWeight();
            }
            System.out.println("-----------------Grafo AEM---------------------");
            System.out.println(ugAEM);
            System.out.println("-----------------------------------------------");
            System.out.println("Grafo 1: Valor del AEM (Prim - No contempla los no conexos): " + ug1.getMSTValue_Prim());
            System.out.println("Grafo 1: Valor del AEM (Prim Nuevo): " + suma);
            System.out.println("Grafo 1: Valor del AEM (Prim Nuevo V2): " + ug1.getMSTValue_Prim_NEW_V2());
            System.out.println(String.format("Grafo 1: Cantidad de Arcos minimos (Corte minimo): %s", ug1.corte_minimo()));
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void TestPrimUg2NoConexo3Comp() {
        UndirectedGraph <String> ug2 = new UndirectedGraph<>(true);
        
        ug2.add("1");
        ug2.add("2");
        ug2.add("3");
        ug2.add("4");
        ug2.add("5");
        ug2.add("6");
        ug2.add("7");
        ug2.add("8");
        ug2.add("9");
        ug2.add("10");
        ug2.add("11");
        ug2.add("12");
        ug2.add("13");
        ug2.add("14"); //nodo aislado sin arcos (componente conexa de un solo vertice)
        
        ug2.addArc("1", "2", 28);
        ug2.addArc("1", "6", 10);
        ug2.addArc("2", "7", 14);
        ug2.addArc("2", "3", 16);
        ug2.addArc("3", "4", 12);
        ug2.addArc("4", "7", 18);
        ug2.addArc("4", "5", 22);
        ug2.addArc("5", "7", 24);
        ug2.addArc("5", "6", 25);
        ug2.addArc("8", "9", 1);
        ug2.addArc("8", "10", 2);
        ug2.addArc("9", "10", 3);
        ug2.addArc("10", "11", 4);
        ug2.addArc("10", "12", 5);
        ug2.addArc("10", "13", 6);
        ug2.addArc("11", "12", 7);
        ug2.addArc("12", "13", 8);

        System.out.println("-----------Grafo Original (No Conexo - 2 Componentes)-------------");
        System.out.println(ug2);
        System.out.println("-----------------------------------------------");
        System.out.println();

        UndirectedGraph <String> ugAEM = ug2.getMSTValue_Prim_NEW();
        long suma = 0;
        for (Arc<String> arco : ugAEM.getArcs()){
            suma += arco.getWeight();
        }
        System.out.println("-----------------Grafo AEM---------------------");
        System.out.println(ugAEM);
        System.out.println("-----------------------------------------------");
        System.out.println();
        
        System.out.println("Grafo 2: Valor del AEM (Prim - No contempla los no conexos): " + ug2.getMSTValue_Prim());
        System.out.println("Grafo 2: Valor del AEM (Prim Nuevo): " + suma);
        System.out.println("Grafo 2: Valor del AEM (Prim Nuevo V2): " + ug2.getMSTValue_Prim_NEW_V2());
    }

    private static void TestPrimDesafioCorteMinimo(){
        UndirectedGraph <String> ug4 = new UndirectedGraph<>();
        File myObj = new File("graph.txt");
        
        try (Scanner myReader = new Scanner(myObj)) {
            while(myReader.hasNextLine()){
                Scanner line = new Scanner(myReader.nextLine());
                Iterator it = line.useDelimiter(" ");
                if(it.hasNext()){
                    String strNodo = (String) it.next();
                    ug4.add(strNodo);
                }
            }
            
            Random rnd = new Random();
            Scanner myReader2 = new Scanner(myObj);
            while(myReader2.hasNextLine()){
                Scanner line = new Scanner(myReader2.nextLine());
                Iterator it = line.useDelimiter(" ");
                if(it.hasNext()){
                    String strNodoInit = (String) it.next();
                    while(it.hasNext()){
                        String strNodoFin = (String) it.next();
                        if(Integer.parseInt(strNodoFin) > Integer.parseInt(strNodoInit)){
                            ug4.addArc(strNodoInit, strNodoFin, rnd.nextInt(100));
                        }
                    }
                }
            }
            
            UndirectedGraph <String> ugAEM = ug4.getMSTValue_Prim_NEW();
            long suma = 0;
            for (Arc<String> arco : ugAEM.getArcs()){
                suma += arco.getWeight();
            }
            
            System.out.println("-----------Grafo Original (Conexo - Desafio Corte minimo)-------------");
            System.out.println(ug4);
            System.out.println("-----------------------------------------------");
            System.out.println();
            
            System.out.println("-----------------Grafo AEM---------------------");
            System.out.println(ugAEM);
            System.out.println("-----------------------------------------------");

            System.out.println("Grafo 5: Valor del AEM (Prim - No contempla los no conexos): " + ug4.getMSTValue_Prim());
            System.out.println("Grafo 5: Valor del AEM (Prim Nuevo): " + suma);
            System.out.println("Grafo 5: Valor del AEM (Prim Nuevo V2): " + ug4.getMSTValue_Prim_NEW_V2());
            System.out.println(String.format("Grafo 5: Cantidad de Arcos minimos (Corte minimo): %s", ug4.corte_minimo()));
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void TestBigO() {
        LinkedList<Integer> lkList = new LinkedList<>();
        ArrayList<Integer> arrList = new ArrayList<>();
        HashSet<Integer> hashSet = new HashSet<>();
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        
        Random rnd = new Random();
        int buscar = 0;
        int min = 0;
        int max = 1000000;
        
        int idxBuscar = rnd.nextInt(max - min + 1) + min;
                
        for(int i = 0; i<max; i++){
            int valor = rnd.nextInt();
            if (i == idxBuscar){
                buscar = valor;
            }
            lkList.add(valor);
            arrList.add(valor);
            hashSet.add(valor);
            hashMap.put(valor, valor);
        }
        
        
        long start = System.nanoTime();
        lkList.contains(buscar);
        long end = System.nanoTime();
        long interval = end - start;
        System.out.println("LinkedList - Execution time in nanoseconds: " + interval);

        start = System.nanoTime();
        arrList.contains(buscar);
        end = System.nanoTime();
        interval = end - start;
        System.out.println("ArrayList - Execution time in nanoseconds: " + interval);
        
        start = System.nanoTime();
        hashSet.contains(buscar);
        end = System.nanoTime();
        interval = end - start;
        System.out.println("HashSet - Execution time in nanoseconds: " + interval);
        
        start = System.nanoTime();
        hashMap.containsKey(buscar);
        end = System.nanoTime();
        interval = end - start;
        System.out.println("HashMap(Keys) - Execution time in nanoseconds: " + interval);
        
        start = System.nanoTime();
        hashMap.containsValue(buscar);
        end = System.nanoTime();
        interval = end - start;
        System.out.println("HashMap(Value) - Execution time in nanoseconds: " + interval);
    }
}

