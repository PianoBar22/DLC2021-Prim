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
    	    System.out.println ("0. Ejemplo 1 - Propuesto implementado");
    	    System.out.println ("1. Ejemplo 2");
    	    System.out.println ("2. Ejemplo 3");
    	    System.out.println ("3. Ejemplo 4 - no conexo (Ej1 + Ej2)");
    	    System.out.println ("4. Ejemplo 5 - grafo desafio corte minimo");
    	    System.out.println ("5. Test Big O");
    	    System.out.println ("-1. Salir");
            System.out.println ("Ingrese opcion: ");
	    opcion = sc.nextInt();
            switch(opcion){
                case 0:
                    TestPrimUg1();
                    break;
                case 1:
                    TestPrimUg2();
                    break;
                case 2:
                    TestPrimUg3();
                    break;
                case 3:
                    TestPrimUg4();
                    break;
                case 4:
                    TestPrimUg5();
                    break;
                case 5:
                    TestBigO();
                    break;
                    
            }
            
            System.out.println("-----------------------////////////-----------------------");
            System.out.println("----------------------FIN EJECUCION-----------------------");
            System.out.println("-----------------------////////////-----------------------");
            System.out.println ("\n\n");
        } while(opcion != -1);
        
    }

    private static void TestPrimUg1() {
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
        ug1.addArc("c", "f", 2); // probar con este arco paralelo... ok!!
        ug1.addArc("d", "e", 2);
        ug1.addArc("d", "f", 3);
        ug1.addArc("e", "f", 1);
        ug1.addArc("g", "h", 2); // grafo no conexo...

        System.out.println("Grafo 1 (no dirigido - sin arcos paralelos: ");
        System.out.println(ug1);
        System.out.println();
        
        System.out.println("Grafo 1: Valor del AEM (Prim): " + ug1.getMSTValue_Prim());
        System.out.println("Grafo 1: Valor del AEM (Prim Nuevo): " + ug1.getMSTValue_Prim_NEW());
        System.out.println("Grafo 1: Valor del AEM (Prim Nuevo V2): " + ug1.getMSTValue_Prim_NEW_V2());
    }

    private static void TestPrimUg2() {
        UndirectedGraph <String> ug2 = new UndirectedGraph<>(true);
        
        ug2.add("1");
        ug2.add("2");
        ug2.add("3");
        ug2.add("4");
        ug2.add("5");
        ug2.add("6");
        ug2.add("7");
        
        ug2.addArc("1", "2", 28);
        ug2.addArc("1", "6", 10);
        ug2.addArc("2", "7", 14);
        ug2.addArc("2", "3", 16);
        ug2.addArc("3", "4", 12);
        ug2.addArc("4", "7", 18);
        ug2.addArc("4", "5", 22);
        ug2.addArc("5", "7", 24);
        ug2.addArc("5", "6", 25);

        System.out.println("Grafo 2: Valor del AEM (Prim): " + ug2.getMSTValue_Prim());
        System.out.println("Grafo 2: Valor del AEM (Prim Nuevo): " + ug2.getMSTValue_Prim_NEW());
        System.out.println("Grafo 2: Valor del AEM (Prim Nuevo V2): " + ug2.getMSTValue_Prim_NEW_V2());
    }

    private static void TestPrimUg3() {
        UndirectedGraph <String> ug3 = new UndirectedGraph<>(true);
        ug3.add("a");
        ug3.add("b");
        ug3.add("c");
        ug3.add("d");
        ug3.add("e");
        ug3.add("f");
        ug3.add("g");
        
        ug3.addArc("a", "b", 1);
        ug3.addArc("a", "c", 5);
        ug3.addArc("b", "c", 4);
        ug3.addArc("b", "d", 8);
        ug3.addArc("c", "d", 6);
        ug3.addArc("c", "f", 2);
        ug3.addArc("d", "e", 11);
        ug3.addArc("d", "f", 9);
        ug3.addArc("e", "f", 3);
        ug3.addArc("e", "g", 10);
        ug3.addArc("f", "g", 12);
        
        System.out.println("Grafo 3: Valor del AEM (Prim): " + ug3.getMSTValue_Prim());
        System.out.println("Grafo 3: Valor del AEM (Prim Nuevo): " + ug3.getMSTValue_Prim_NEW());
        System.out.println("Grafo 3: Valor del AEM (Prim Nuevo V2): " + ug3.getMSTValue_Prim_NEW_V2());
    }

    private static void TestPrimUg4() {
        UndirectedGraph <String> ug4 = new UndirectedGraph<>(true);
        
        ug4.add("1");
        ug4.add("2");
        ug4.add("3");
        ug4.add("4");
        ug4.add("5");
        ug4.add("6");
        ug4.add("7");
        ug4.add("a");
        ug4.add("b");
        ug4.add("c");
        ug4.add("d");
        ug4.add("e");
        ug4.add("f");
        ug4.add("g");
        
        ug4.addArc("1", "2", 28);
        ug4.addArc("1", "6", 10);
        ug4.addArc("2", "7", 14);
        ug4.addArc("2", "3", 16);
        ug4.addArc("3", "4", 12);
        ug4.addArc("4", "7", 18);
        ug4.addArc("4", "5", 22);
        ug4.addArc("5", "7", 24);
        ug4.addArc("5", "6", 25);
        ug4.addArc("a", "b", 1);
        ug4.addArc("a", "c", 5);
        ug4.addArc("b", "c", 4);
        ug4.addArc("b", "d", 8);
        ug4.addArc("c", "d", 6);
        ug4.addArc("c", "f", 2);
        ug4.addArc("d", "e", 11);
        ug4.addArc("d", "f", 9);
        ug4.addArc("e", "f", 3);
        ug4.addArc("e", "g", 10);
        ug4.addArc("f", "g", 12);

        long start;
        start = System.nanoTime();
            System.out.println("Grafo 4: Valor del AEM (Prim): " + ug4.getMSTValue_Prim());
            System.out.println("Grafo 4: Tiempo en nanosegundos (Prim): " + (System.nanoTime() - start));
            
            start = System.nanoTime();
            System.out.println("Grafo 4: Valor del AEM (Prim Nuevo): " + ug4.getMSTValue_Prim_NEW());
            System.out.println("Grafo 4: Tiempo en nanosegundos (Prim Nuevo): " + (System.nanoTime() - start));
            
            start = System.nanoTime();
            System.out.println("Grafo 4: Valor del AEM (Prim Nuevo V2): " + ug4.getMSTValue_Prim_NEW_V2());
            System.out.println("Grafo 4: Tiempo en nanosegundos (Prim Nuevo V2): " + (System.nanoTime() - start));
    }

    private static void TestPrimUg5(){
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
            
            long start;
            
            System.out.println("Grafo no dirigido (desafio): ");
            System.out.println(ug4);
            
            start = System.nanoTime();
            System.out.println("Grafo 5: Valor del AEM (Prim): " + ug4.getMSTValue_Prim());
            System.out.println("Grafo 5: Tiempo en nanosegundos (Prim): " + (System.nanoTime() - start));
            
            start = System.nanoTime();
            System.out.println("Grafo 5: Valor del AEM (Prim Nuevo): " + ug4.getMSTValue_Prim_NEW());
            System.out.println("Grafo 5: Tiempo en nanosegundos (Prim Nuevo): " + (System.nanoTime() - start));
            
            start = System.nanoTime();
            System.out.println("Grafo 5: Valor del AEM (Prim Nuevo V2): " + ug4.getMSTValue_Prim_NEW_V2());
            System.out.println("Grafo 5: Tiempo en nanosegundos (Prim Nuevo V2): " + (System.nanoTime() - start));

            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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

