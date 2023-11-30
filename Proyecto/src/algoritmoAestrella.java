import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/* FUNCIONAMIENTO A*
 * Vamos a tener todas las estaciones asociadas a un int (podremos sacar luego su nombre de un hashmap)
 * 
 * 
 * Para los tiempos es en segundos todo. Las 0:00:00 es 0 y las 23:59:59 es 86399 (tener cuidado con viajes que empiezan tarde)
 * TODO Horarios de todas las estaciones pasados a este formato 
 * TODO los costes en segundos también
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

    private int tiempoAdy(int estacion1, int estacion2, int horaActual){ 
        //Da el tiempo que se tarda de ir a una estacion a otra siendo estas adyacentes.
        //Tiene en cuenta tiempo de trasbordo
        int res = 0;  
       

        return res;
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


    //Devuelve el camino de estaciones. La última posición del array no es una estación, es el coste 
    private ArrayList<Integer> Aestrella(int inicio, int destino, int horaIni){

        Set<Estacion> listaAbierta = new HashSet<>();
        Set<Integer> listaCerrada = new HashSet<>();
        Map<Integer, Integer> vieneDe = new HashMap<>();
        Map<Integer, Integer> costePorAhora = new HashMap<>();
        Iterator<Integer> it;
        //Coste está en SEGUNDOS
        listaAbierta.add(new Estacion(inicio, 0, distEur(inicio, destino), horaIni));

        vieneDe.put(inicio, -1);
        costePorAhora.put(inicio, 0);
        Estacion posActual;
        while (!listaAbierta.isEmpty()) {

            //Calcular posición actual nueva
            Iterator<Estacion> it2 = listaAbierta.iterator();
            int costeMenor = 99999999;
            Estacion posibleSiguiente = null;
           // int posActualPrima = posActual;
            while (it2.hasNext()) {
                Estacion siguiente =  it2.next();

                if (siguiente.getFun() < costeMenor){
                    costeMenor = siguiente.getFun() ;
                    posibleSiguiente = siguiente; //Me he movido
                    
                }
                 

            }
            posActual = posibleSiguiente;
            listaAbierta.remove(posActual);


            if (posActual.getPos() == destino) {
                return recorrido(vieneDe, destino, costePorAhora.get(destino));
            }

            listaCerrada.add(posActual.getPos());

            
            it = conexiones.get(posActual.getPos()).iterator(); 

            while(it.hasNext()) {
                int conexion = it.next();
                if (!listaCerrada.contains(conexion)) {

                    int tiempoTardo = tiempoAdy(posActual.getPos(), conexion, posActual.getHoraLlego());

                    int nuevoCoste = costePorAhora.get(posActual.getPos()) + tiempoTardo; 

                    if (!costePorAhora.containsKey(conexion) || nuevoCoste < costePorAhora.get(conexion)) {
                        costePorAhora.put(conexion, nuevoCoste);
                        int funcion = nuevoCoste + distEur(conexion, destino)/7;  //Ojito 2 min -- 840 m 
                        int horaNueva = posActual.getHoraLlego() + tiempoTardo;
                        listaAbierta.add(new Estacion(conexion, nuevoCoste, funcion, horaNueva));
                        vieneDe.put(conexion, posActual.getPos());
                    }
                }

                
            }
        }

        // No se encontró un camino (no debería pasar nunca)
        return null;
    }

    private ArrayList<Integer> recorrido(Map<Integer, Integer> vieneDe, int actual, int costeTotal) {
        ArrayList<Integer> caminoRe = new ArrayList<>();
        while (actual != -1) {
            caminoRe.add(actual);
            actual = vieneDe.get(actual);
        }
        //Están en orden al revés
        ArrayList<Integer> res = new ArrayList<Integer>();
        for (int i = caminoRe.size() - 1; i >= 0; i--) {

            res.add(caminoRe.get(i));
        }

        res.add(costeTotal); //Añado al final el coste

        return res;
    }

    private static class Estacion {
        private final int posicion;
        private final int coste;
        private final int funcion;
        private final int horaLlego;

        public Estacion(int posicion, int coste, int funcion, int horaLlego) {
            this.posicion = posicion;
            this.coste = coste;
            this.funcion = funcion;
            this.horaLlego = horaLlego;
        }

        public int getPos() {
            return posicion;
        }

        public int getCoste() {
            return coste;
        }

        public int getFun() {
            return funcion;
        }

        public int getHoraLlego() {
            return horaLlego;
        }
    }





}