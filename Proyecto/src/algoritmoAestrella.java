import java.util.ArrayList;
//import javafx.util.Pair;  
//import javafx.util.Map;  
//import javafx.util.HashMap;  


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

    //private Map estaciones= new HashMap();




    //Trasbordos: Belecour - Charpennes (2680 ), Saxo Gamebta - Hotel de ville (1710 )

    public static  void main(  String[]args){
        System.out.println("Dummy");    
    }



    public void inicializa(){
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

    private ArrayList estacionesPosibles(int estacion){ 

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

    public int calculaTiempo(ArrayList camino){

        //trasbordoMasCercano: dado un camino de estaciones en forma de ArrayList devuelve el tiempo que tarda en recorrerlo (hay que tener en cuenta horarios)

        return 0;
    }

    private ArrayList Aestrella(int inicio, int destino, int horaIni){

        return null;
    }


}