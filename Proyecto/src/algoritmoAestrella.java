import java.util.ArrayList;
import java.util.Comparator;
//import javafx.util.Pair;  
//import javafx.util.Map;  
//import javafx.util.HashMap;  
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/* FUNCIONAMIENTO A*
 * Vamos a tener todas las estaciones asociadas a un int (podremos sacar luego su nombre de un hashmap)
 * 
 * 
 * Para los tiempos es en segundos todo. Las 0:00:00 es 0 y las 23:59:59 es 86399 (tener cuidado con viajes que empiezan tarde)
 * TODO Horarios de todas las estaciones pasados a este formato 
 * 
 * estacionesPosibles: dada una estacion devuelve a que estaciones están adyacentes a ella
 * distEur: dadas dos estaciones devuelve la distancia euristica. Esto lo hace calculando el trasbordo más cercano
 * y luego sabiendo la distancia entre las estaciones de trasbordo que es fija 
 * trasbordoMasCercano: dada una estación devuelve la estacion trasbordo más cercana
 * calculaTiempo: dado un camino de estaciones en forma de ArrayList devuelve el tiempo que tarda en recorrerlo (hay que tener en cuenta horarios)
 * 
 * AEstrella: dadas dos estaciones devuelve el camino más corto entre ellas.
 * Va avanzando con estacionesPosibles y cuando encuentra una bifurcación 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */




public class algoritmoAestrella{

    private ArrayList tiempos = new ArrayList<>();
    private ArrayList tiemposA = new ArrayList<>(); //Empieza en Perrache, acaba en Vaulx-en-Velin La Soie
    private ArrayList tiemposB = new ArrayList<>();
    private ArrayList tiemposC = new ArrayList<>();
    private ArrayList tiemposD = new ArrayList<>();
    private Map<Integer, String> codEstacion = new HashMap<Integer, String>(); 
    private Map<Integer, ArrayList<Integer>> conexiones = new HashMap<>(); 
    //private Map estaciones= new HashMap();




    //Trasbordos: Belecour - Charpennes (2680 ), Saxo Gamebta - Hotel de ville (1710 )

    public static  void main(  String[]args){
        System.out.println("Dummy");    
        
    }



    public void inicializa(){

        //Hashmap estaciones
        
        codEstacion.put(1, "Cuire");
        codEstacion.put(2, "Hénon"); 
        // TODO Así con todas

        //Hashmap conexiones
        
        ArrayList<Integer> cuire = new ArrayList<>();
        cuire.add(2); //Cuire solo conecta con Hénon
        conexiones.put( 1, cuire); 
        ArrayList<Integer> henon = new ArrayList<>();
        henon.add(1); henon.add(3);//Hénon conecta con Cuire y Croix-Rousse
        conexiones.put( 2, henon); 
        // TODO Así con todas



/*  
        //Tiempos A
        tiemposA.add(new Pair (0, 0)); //Perrache
        tiemposA.add(new Pair (1, 400)); //Ampère-Victor Hugo
        tiemposA.add(new Pair (2, 660)); //Bellecour
        tiemposA.add(new Pair (1, 620)); //Cordeliers
        tiemposA.add(new Pair (2, 460)); //Hôtel de Ville L. Pradel
        tiemposA.add(new Pair (1, 630)); //Foch
        tiemposA.add(new Pair (2, 680)); //Masséna
        tiemposA.add(new Pair (2, 780)); //Charpennes
        tiemposA.add(new Pair (2, 820)); //République Villeurbanne
        tiemposA.add(new Pair (2, 700)); //Gratte Ciel
        tiemposA.add(new Pair (1, 600)); //Flachet
        tiemposA.add(new Pair (2, 800)); //Cusset
        tiemposA.add(new Pair (1, 650)); //Laurent Bonnevay
        tiemposA.add(new Pair (2, 1100)); //Vaulx-en-Velin La Soie
 */
    }


    private int funcionObjetivo(int distanciaEur, int coste, int horaActual){



        return 0;
    }

    private int calculaCoste(int origen, int destino, int horaActual){



        return 0;
    }

    private ArrayList<Integer> estacionesPosibles(int estacion){ 

        //estacionesPosibles: dada una estacion devuelve a que estaciones están adyacentes a ella
        return null; 
    }

    private int distEur(int orig, int dest){

        //distEur: dadas dos estaciones devuelve la distancia euristica. Esto lo hace calculando el trasbordo más cercano
        //y luego sabiendo la distancia entre las estaciones de trasbordo que es fija 

        int orig1 = trasbordoMasCercano(orig);
        int dest1 = trasbordoMasCercano(dest);




        return 0;
    }

    private int trasbordoMasCercano(int estacion){

        //trasbordoMasCercano: dada una estación devuelve la estacion trasbordo más cercana

        return 0;
    }

    public int calculaTiempo(ArrayList<Integer> camino){

        //trasbordoMasCercano: dado un camino de estaciones en forma de ArrayList devuelve el tiempo que tarda en recorrerlo (hay que tener en cuenta horarios)

        return 0;
    }

    private ArrayList<Integer> Aestrella(int inicio, int destino, int horaIni){

        PriorityQueue<Estacion> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.getCost() + node.getHeuristic()));
        Set<Integer> closedSet = new HashSet<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();
        Map<Integer, Integer> costSoFar = new HashMap<>();
        Iterator<Integer> it;

        openSet.add(new Estacion(inicio, 0, distEur(inicio, destino)));

        cameFrom.put(inicio, null);
        costSoFar.put(inicio, 0);

        while (!openSet.isEmpty()) {
            Estacion posActual = openSet.poll();

            if (posActual.getPosition() == destino) {
                return reconstructPath(cameFrom, posActual.getPosition());
            }

            closedSet.add(posActual.getPosition());

            //No lo entiendo bien
            it = conexiones.get(posActual.getPosition()).iterator(); 

            while(it.hasNext()) {
                int neighbor = it.next();
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int newCost = costSoFar.get(posActual.getPosition()) + 1; //Por qué +1

                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    int priority = newCost + distEur(neighbor, destino); //Ojito
                    openSet.add(new Estacion(neighbor, newCost, priority));
                    cameFrom.put(neighbor, posActual.getPosition());
                }
            }
        }

        // No se encontró un camino (no debería pasar nunca)
        return null;
    }



/* 
     int posActual = inicio;
        ArrayList<Integer> res = new ArrayList<>();
        ArrayList<Integer> listaAbierta = new ArrayList<>();
        ArrayList<Integer> listaCerrada = new ArrayList<>();
        ArrayList<Integer> puedoLlegar = new ArrayList<>();
        Iterator<Integer> it = conexiones.get( (Integer) posActual).iterator(); 
        Map<Integer, Integer> vengoDe = new HashMap<>();
        while (it.hasNext()){
            listaAbierta.add(it.next());
        }
        listaCerrada.add(inicio);


        while (!listaAbierta.isEmpty()) {
            
            puedoLlegar = conexiones.get( (Integer) posActual); 






            //Calcular posición actual nueva
            Iterator<Integer> it2 = listaAbierta.iterator();
            int horaActual = horaIni; //TODO
            int costeMenor = 99999999;
            int posActualPrima = posActual;
            while (it2.hasNext()) {
                int posibleSiguiente = (int) it2.next();
                vengoDe.put(posibleSiguiente, posActualPrima); //Ojo aqui
                
                int costeAso = calculaCoste( posibleSiguiente , destino, horaActual);
                if (costeAso < costeMenor){
                    costeMenor = costeAso;
                    posActual = posibleSiguiente; //Me he movido
                    listaAbierta.remove(posActual);
                    listaCerrada.add(posActual);
                }
                 

            }

            

            
            if (puedoLlegar.contains(destino)){
                res.add(destino);
                return res;
            }

            closedSet.add(current.getPosition());

            for (int neighbor : graph.getOrDefault(current.getPosition(), Collections.emptyList())) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int newCost = costSoFar.get(current.getPosition()) + 1;

                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    int priority = newCost + heuristic(neighbor, goal);
                    openSet.add(new Node(neighbor, newCost, priority));
                    cameFrom.put(neighbor, current.getPosition());
                }
            }
        }

        Iterator it = puedoLlegar.iterator();

        //Ha habido un error, siempre se puede hacer un camino
        return null;
*/


    private ArrayList<Integer> reconstructPath(Map<Integer, Integer> cameFrom, int current) {
        ArrayList<Integer> path = new ArrayList<>();
        while (current != -1) {
            path.add(current);
            current = cameFrom.get(current);
        }
        //Collections.reverse(path);
        return path;
    }

    private static class Estacion {
        private final int posicion;
        private final int coste;
        private final int eur;

        public Estacion(int position, int cost, int heuristic) {
            this.posicion = position;
            this.coste = cost;
            this.eur = heuristic;
        }

        public int getPosition() {
            return posicion;
        }

        public int getCost() {
            return coste;
        }

        public int getHeuristic() {
            return eur;
        }
    }

/*import java.util.*;

class AStarAlgorithm {
    private Map<Integer, List<Integer>> graph;

    public AStarAlgorithm(Map<Integer, List<Integer>> graph) {
        this.graph = graph;
    }

    public List<Integer> findPath(int start, int goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.getCost() + node.getHeuristic()));
        Set<Integer> closedSet = new HashSet<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();
        Map<Integer, Integer> costSoFar = new HashMap<>();

        openSet.add(new Node(start, 0, heuristic(start, goal)));

        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.getPosition() == goal) {
                return reconstructPath(cameFrom, current.getPosition());
            }

            closedSet.add(current.getPosition());

            for (int neighbor : graph.getOrDefault(current.getPosition(), Collections.emptyList())) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int newCost = costSoFar.get(current.getPosition()) + 1;

                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    int priority = newCost + heuristic(neighbor, goal);
                    openSet.add(new Node(neighbor, newCost, priority));
                    cameFrom.put(neighbor, current.getPosition());
                }
            }
        }

        // No se encontró un camino (no debería pasar nunca)
        return null;
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> cameFrom, int current) {
        List<Integer> path = new ArrayList<>();
        while (current != -1) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private int distEur(int orig, int dest){

        //distEur: dadas dos estaciones devuelve la distancia euristica. Esto lo hace calculando el trasbordo más cercano
        //y luego sabiendo la distancia entre las estaciones de trasbordo que es fija 

        int orig1 = trasbordoMasCercano(orig);
        int dest1 = trasbordoMasCercano(dest);




        return 0;
    }

    
}
 * 
 * 
 * 
 */



}