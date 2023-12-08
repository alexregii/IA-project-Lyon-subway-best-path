
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* FUNCIONAMIENTO A*
 * Vamos a tener todas las estaciones asociadas a un int (podremos sacar luego su nombre de un hashmap)
 * 
 * 
 * Para los tiempos es en minutos todo. Las 0:00 es 0 y las 23:59 es 1439 (tener cuidado con viajes que empiezan tarde) (se soluciona con el módulo)
 * estacionesPosibles: dada una estacion devuelve a que estaciones están adyacentes a ella
 * distEur: dadas dos estaciones devuelve la distancia euristica. Esto lo hace calculando el trasbordo más cercano en caso de que estén en líneas distintas
 * y luego sabiendo la distancia entre las estaciones de trasbordo que es fija. Si están 
 * 
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

    //private ArrayList tiempos = new ArrayList<>();

    private static ArrayList<int[]> tiemposA = new ArrayList<int[]>(); //Empieza en Perrache, acaba en Vaulx-en-Velin La Soie
    private static ArrayList<int[]> tiemposB = new ArrayList<int[]>();
    private static ArrayList<int[]> tiemposC = new ArrayList<int[]>();
    private static ArrayList<int[]> tiemposD = new ArrayList<int[]>();

    private static Map<Integer, String> codEstacion = new HashMap<Integer, String>(); 
    private static Map<Integer, ArrayList<Integer>> conexiones = new HashMap<>(); 
    //private Map estaciones= new HashMap();
    private static ArrayList<Integer> estacionesLineaA;
    private static ArrayList<Integer> estacionesLineaB;
    private static ArrayList<Integer> estacionesLineaC;
    private static ArrayList<Integer> estacionesLineaD;

    private static  ArrayList<Integer> horarioPerrache = new ArrayList<Integer>();
    private static  ArrayList<Integer> horarioHoteldeVilleLPradel = new ArrayList<Integer>();
    private static  ArrayList<Integer> horarioGaredeVaise = new ArrayList<Integer>();
    private static  ArrayList<Integer> horarioGaredOullins = new ArrayList<Integer>();


    private static ArrayList<ArrayList<Integer>> horariosLineaA = new  ArrayList<ArrayList<Integer>>();
    private static ArrayList<ArrayList<Integer>> horariosLineaB = new  ArrayList<ArrayList<Integer>>();
    private static ArrayList<ArrayList<Integer>> horariosLineaC = new  ArrayList<ArrayList<Integer>>();
    private static ArrayList<ArrayList<Integer>> horariosLineaD = new  ArrayList<ArrayList<Integer>>();


    private static int tiempoAdy(int estacion1, int estacion2, int horaActual){ 
        //Da el tiempo que se tarda de ir a una estacion a otra siendo estas adyacentes.
        //Partes de la estacion1
        //Tiene en cuenta los horarios

        int retraso = 0;

        if (estacionesLineaA.contains(estacion1) && estacionesLineaA.contains(estacion2)){

            int convLinea1 = conversionALinea(estacion1, "A");
            int convLinea2 = conversionALinea(estacion2, "A");

            int estacionEfectiva = Math.max(convLinea1, convLinea2);
            
            // 5  Hotel de ville
            //6 Charpennes
            //10 saxo gambetta
            //25 Bellecour
            
            if(estacion1 != 5 & estacion1 != 6 && estacion1 != 25){
                return tiemposA.get(estacionEfectiva)[0]; //ya estás subido al tren, va a salir a la hora
            }
            
            while(retraso < 100000){ //debería pararse antes
                if(horariosLineaA.get(estacion1 != 5 ? (estacion1 == 6 ? 2: 0) :1).contains(horaActual+retraso)){
                    return tiemposA.get(estacionEfectiva)[0]+retraso;
                
                 }else retraso++;
            }

        }

        if (estacionesLineaB.contains(estacion1) && estacionesLineaB.contains(estacion2)){

            int convLinea1 = conversionALinea(estacion1, "B");
            int convLinea2 = conversionALinea(estacion2, "B");

            int estacionEfectiva = Math.max(convLinea1, convLinea2); //Para que sea más fácil calcular los tiempos con la estructura de datos
            //Se supone que se tarda lo mismo en ir de A a B que de B a A
          
            
            if(estacion1 != 6 || estacion1 != 10 ){
                return tiemposB.get(estacionEfectiva)[0]; //ya estás subido al tren, va a salir a la hora
            }

            while(retraso < 100000){ //debería pararse antes
                if(horariosLineaB.get(estacion1 == 6 ? 1 : 0).contains(horaActual+retraso)){
                
                    return tiemposB.get(estacionEfectiva)[0]+retraso;
                
                 }else retraso++;
            }

        }

        if (estacionesLineaC.contains(estacion1) && estacionesLineaC.contains(estacion2)){

            int convLinea1 = conversionALinea(estacion1, "C");
            int convLinea2 = conversionALinea(estacion2, "C");

            int estacionEfectiva = Math.max(convLinea1, convLinea2);
          
            if(estacion1 != 5){
                return tiemposC.get(estacionEfectiva)[0]; //ya estás subido al tren, va a salir a la hora
            }
            
            while(retraso < 100000){ //debería pararse antes
                if(horariosLineaC.get(0).contains(horaActual+retraso)){
                
                    return tiemposC.get(estacionEfectiva)[0]+retraso;
                
                 }else retraso++;
            }

        }

        if (estacionesLineaD.contains(estacion1) && estacionesLineaD.contains(estacion2)){

            int convLinea1 = conversionALinea(estacion1, "D");
            int convLinea2 = conversionALinea(estacion2, "D");

            int estacionEfectiva = Math.max(convLinea1, convLinea2);
            
            if( estacion1 != 10 && estacion1 != 25){
                return tiemposA.get(estacionEfectiva)[0]; //ya estás subido al tren, va a salir a la hora
            }
            
            while(retraso < 100000){ //debería pararse antes
                if(horariosLineaD.get(estacion1 == 25 ? 0 : 1).contains(horaActual+retraso)){
                
                    return tiemposD.get(estacionEfectiva)[0]+retraso;
                
                 }else retraso++;
            }

        }


        //No se debería ejecutar
         return 0;
    }

    //Dada un código de estación y una línea da la posición relativa de la estación con respecto de la línea 
    private static int conversionALinea(int estacion, String linea){ 

        if(linea.equals("A")){
            return estacionesLineaA.indexOf(estacion);
        }
        if(linea.equals("B")){
            return estacionesLineaB.indexOf(estacion);
        }
        if(linea.equals("C")){
            return estacionesLineaC.indexOf(estacion);
        }
        if(linea.equals("D")){
            return estacionesLineaD.indexOf(estacion);
        }
       
        //No se debería ejecutar
        return -99999;
    }


    private static int distHeur(int orig, int dest){

        //distEur: dadas dos estaciones devuelve la distancia euristica. Esto lo hace calculando el trasbordo más cercano
        //y luego sabiendo la distancia entre las estaciones de trasbordo que es fija 

        //Si están en la misma linea, es la distancia entre ellas que se puede calcular directamente respecto a la estructura de datos
        if (estacionesLineaA.contains(orig) && estacionesLineaA.contains(dest)){
            return Math.abs(tiemposA.get(conversionALinea(orig, "A") )[1] - tiemposA.get(conversionALinea(dest, "A") )[1]  );
            
        }
        if (estacionesLineaB.contains(orig) && estacionesLineaB.contains(dest)){
            return Math.abs(tiemposB.get(conversionALinea(orig, "B") )[1] - tiemposB.get(conversionALinea(dest, "B") )[1]  );
            
        }
        if (estacionesLineaC.contains(orig) && estacionesLineaC.contains(dest)){
            return Math.abs(tiemposC.get(conversionALinea(orig, "C") )[1] - tiemposC.get(conversionALinea(dest, "C") )[1]  );
            
        }
        if (estacionesLineaD.contains(orig) && estacionesLineaD.contains(dest)){
            return Math.abs(tiemposD.get(conversionALinea(orig, "D") )[1] - tiemposD.get(conversionALinea(dest, "D") )[1]  );
            
        }



        //si están en distintas lineas se calcula la distancia a la estacion trasbordo más cercana
        //Y luego la distancia entre las estaciones trasbordo

        int orig1 = trasbordoMasCercano(orig);

        int dif1 = distHeur(orig, orig1);

        int dest1 = trasbordoMasCercano(dest);

        int dif2 = distHeur(dest, dest1);

        // 5  Hotel de ville
        //6 Charpennes
        //10 saxo gambetta
        //25 Bellecour
        
        /*Belecour - Charpennes (2680 ), 
        Saxo Gamebta - Hotel de ville (1710 )
        Saxo Gamebta - Charpennes (2230 )
        Belecour -  Hotel de ville 1100
        Belecour  - Saxo Gamebta 1100
        Hotel de ville  - Charpennes 2000 */

        if(orig1 == dest1){
            return dif1+dif2;
        }

        if(orig1 == 5 && dest1 == 6|| orig1 == 6 && dest1 == 5 ){
            return 2000+dif1+dif2;

        }
        if(orig1 == 5 && dest1 == 10|| orig1 == 10 && dest1 == 5 ){
            return 1710+dif1+dif2;

        }
        if(orig1 == 5 && dest1 == 25|| orig1 == 25 && dest1 == 5 ){
            return 1100+dif1+dif2;

        }
        if(orig1 == 6 && dest1 == 10|| orig1 == 10 && dest1 == 6 ){
            return 2230+dif1+dif2;

        }
        if(orig1 == 6 && dest1 == 25|| orig1 == 25 && dest1 == 6 ){
            return 2680+dif1+dif2;

        }
        if(orig1 == 10 && dest1 == 25|| orig1 == 25 && dest1 == 10 ){
            return 1100+dif1+dif2;

        }

        //No debería ejecutarse
        return 0;
    }

    private static int trasbordoMasCercano(int estacion){


        //trasbordoMasCercano: dada una estación devuelve la estacion trasbordo más cercana

        //Si son las propias estaciones ya está
        if (estacion == 6 || estacion == 25 || estacion == 10 || estacion == 5){
            return estacion;
        }

        ArrayList<Integer> posiblesCaminos = conexiones.get(estacion);


        if (posiblesCaminos.size() == 1){ //Está en un extremo

            return exploraCamino(posiblesCaminos.get(0), estacion,1)[0];

        }else{ //Es una de las del medio, tiene 2 conecxiones

            int [] camino1 = exploraCamino(posiblesCaminos.get(0), estacion,1);
            int [] camino2 = exploraCamino(posiblesCaminos.get(1), estacion,1);

            //Devuelve el que tenga menos profundidad
            return Math.min(camino1[2], camino2[2]) == camino1[2] ? camino1[0]: camino2[0];
        }

    }

    // Sirve para recorrer líneas en una dirección hasta encontrar un trasbordo
    //La profundidad marca cuantas estaciones te tienes que mover hasta encontrarlo
    private static int[] exploraCamino(int estacion, int anterior, int profundidad){

        int res[] = {estacion,anterior, profundidad};
        int dummy[] = {0,0,9999}; //sirve para marcar extremos

        if (estacion == 6 || estacion == 25 || estacion == 10 || estacion == 5){
            return res;
        }else{
            ArrayList<Integer> posiblesCaminos = conexiones.get(estacion);
            if (posiblesCaminos.size() == 1){ //no puedo llegar, es un extremo
                return dummy;
                
            }else{
                int siguiente = posiblesCaminos.get(0) == anterior ? posiblesCaminos.get(1): posiblesCaminos.get(0);
                return exploraCamino(siguiente, estacion, profundidad+1);
            }
            
            
        }


    }


    //Devuelve el camino de estaciones óptimo con el algortimo A *. La última posición del array no es una estación, es el coste (minutos)
    private static ArrayList<Integer> Aestrella(int inicio, int destino, int horaIni){

        Set<Estacion> listaAbierta = new HashSet<>();
        Set<Integer> listaCerrada = new HashSet<>();
        Map<Integer, Integer> vieneDe = new HashMap<>();
        Map<Integer, Integer> costePorAhora = new HashMap<>();
        Iterator<Integer> it;
        
        listaAbierta.add(new Estacion(inicio, distHeur(inicio, destino), horaIni));

        vieneDe.put(inicio, -1);
        costePorAhora.put(inicio, 0);
        Estacion posActual;
        while (!listaAbierta.isEmpty()) {

            //Calcular posición actual nueva
            Iterator<Estacion> it2 = listaAbierta.iterator();
            int costeMenor = 99999999;
            Estacion posibleSiguiente = null;
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
                        int funcion = nuevoCoste + distHeur(conexion, destino)/420;  //Ojito 1 min -- 420 m 
                        int horaNueva = (posActual.getHoraLlego() + tiempoTardo )  % 1440; //evitar problemas tiempo
                        listaAbierta.add(new Estacion(conexion, funcion, horaNueva));
                        vieneDe.put(conexion, posActual.getPos());
                    }
                }

                
            }
        }

        // No se encontró un camino (no debería pasar nunca)
        return null;
    }


    //Genera el camino sabiendo las direcciones y las estaciones que están en el recorrido. La última posición del array no es una estación, es el coste (minutos)
    private static ArrayList<Integer> recorrido(Map<Integer, Integer> vieneDe, int actual, int costeTotal) {
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
        private final int funcion;
        private final int horaLlego;

        public Estacion(int posicion, int funcion, int horaLlego) {
            this.posicion = posicion;
            this.funcion = funcion;
            this.horaLlego = horaLlego;
        }

        public int getPos() {
            return posicion;
        }

        public int getFun() {
            return funcion;
        }

        public int getHoraLlego() {
            return horaLlego;
        }
    }


    //Dado un horario te da el horario desfasado un tiempo llamado suma
    private static ArrayList<Integer> sumaHorario(ArrayList<Integer> horario, int suma){
        ArrayList<Integer> res = new ArrayList<Integer>();

        Iterator<Integer> it = horario.iterator();

        while (it.hasNext()) {

            int siguiente = it.next();
            res.add(siguiente+suma % 1440); //para evitar problemas con horas que 
            //superan las 23:59 (1439 en minutos)
        }

        return res;
    }   




    public static void inicializa(){

        //Hashmap estaciones
        //LINEA C
        codEstacion.put(1, "Cuire");
        codEstacion.put(2, "Hénon");
        codEstacion.put(3, "Croix-Rousse");
        codEstacion.put(4, "Croix Paquet");
        codEstacion.put(5, "Hôtel de Ville L. Pradel"); //Trasbordo

        //LINEA B
        codEstacion.put(6, "Charpennes"); //Trasbordo
        codEstacion.put(7, "Brotteaux ");
        codEstacion.put(8, "Gare Part-Dieu V. Merle");
        codEstacion.put(9, "Place Guichard");
        codEstacion.put(10, "Saxe Gambetta");//Trasbordo
        codEstacion.put(11, "Jean Macé");
        codEstacion.put(12, " Place Jean Jaurès");
        codEstacion.put(13, "Debourg");
        codEstacion.put(14, "Stade de Gerland ");
        codEstacion.put(15, "Gare d’Oullins");
        

         //LINEA A
        codEstacion.put(16, "Vaulx-en-Velin La Soie");
        codEstacion.put(17, "Laurent Bonnevay");
        codEstacion.put(18, "Cusset ");
        codEstacion.put(19, "Flachet ");
        codEstacion.put(20, "Gratte Ciel");
        codEstacion.put(21, "République Villeurbanne");
        //Charpennes ya esta por linea B
        codEstacion.put(22, "Masséna ");
        codEstacion.put(23, "Foch ");
        //Hôtel de Ville L. Prade ya esta por linea C
        codEstacion.put(24, "Cordeliers ");
        codEstacion.put(25, "Bellecour "); //Trasbordo
        codEstacion.put(26, " Ampère-Victor Hugo");
        codEstacion.put(27, "Perrache ");

        //LINEA D
        codEstacion.put(28, "Gare de Vénissieux");
        codEstacion.put(29, "Parilly ");
        codEstacion.put(30, "Mermoz - Pinel ");
        codEstacion.put(31, "Laennec");
        codEstacion.put(32, "Grange Blanche");
        codEstacion.put(33, "Monplaisir Lumière");
        codEstacion.put(34, "Sans Souci ");
        codEstacion.put(35, "Garibaldi ");
        //Saxe Gambetta ya esta por la linea B
        codEstacion.put(36, "Guillotière Gabriel Péri ");
        //Bellecour ya esta por la linea A
        codEstacion.put(37, " Vieux Lyon");
        codEstacion.put(38, " Gorge de Loup");
        codEstacion.put(39, "Valmy ");
        codEstacion.put(40, "Gare de Vaise ");

        
        estacionesLineaC = new ArrayList<Integer>(); estacionesLineaC.add(5); estacionesLineaC.add(4);
        estacionesLineaC.add(3); estacionesLineaC.add(2); estacionesLineaC.add(1);

        estacionesLineaB = new ArrayList<Integer>(); estacionesLineaB.add(15); estacionesLineaB.add(14); 
        estacionesLineaB.add(13); estacionesLineaB.add(12);  estacionesLineaB.add(11); 
        estacionesLineaB.add(10);  estacionesLineaB.add(9); estacionesLineaB.add(8);  
        estacionesLineaB.add(7); estacionesLineaB.add(6); 
        
        

        estacionesLineaA = new ArrayList<Integer>(); estacionesLineaA.add(27);estacionesLineaA.add(26);estacionesLineaA.add(25);
        estacionesLineaA.add(24);estacionesLineaA.add(5);estacionesLineaA.add(23);estacionesLineaA.add(22);
        estacionesLineaA.add(6);estacionesLineaA.add(21);estacionesLineaA.add(20);estacionesLineaA.add(19);
        estacionesLineaA.add(18);estacionesLineaA.add(17);estacionesLineaA.add(16); 


        estacionesLineaD = new ArrayList<Integer>(); estacionesLineaD.add(40);estacionesLineaD.add(39);estacionesLineaD.add(38);
        estacionesLineaD.add(37);estacionesLineaD.add(25);estacionesLineaD.add(36);estacionesLineaD.add(10);
        estacionesLineaD.add(35); estacionesLineaD.add(34);estacionesLineaD.add(33);
        estacionesLineaD.add(32);estacionesLineaD.add(31);estacionesLineaD.add(30);
        estacionesLineaD.add(29); estacionesLineaD.add(28);
        


        //Hashmap conexiones
        
        //LINEA C
        ArrayList<Integer> Cuire = new ArrayList<>();
        Cuire.add(2); //Cuire solo conecta con Hénon
        conexiones.put( 1, Cuire);

        ArrayList<Integer> Henon = new ArrayList<>();
        Henon.add(1); Henon.add(3);//Hénon conecta con Cuire y Croix-Rousse
        conexiones.put( 2, Henon);

        ArrayList<Integer> CroixRousse = new ArrayList<>();
        CroixRousse.add(2); CroixRousse.add(4);//Croix-Rousse conecta con henon y con Croix-paquet
        conexiones.put(3, CroixRousse);

        ArrayList<Integer> CroixPaquet = new ArrayList<>();
        CroixPaquet.add(3); CroixPaquet.add(5);//CroixPaquet conecta con Croix-Rousse y Hôtel de Ville L. Pradel
        conexiones.put( 4, CroixPaquet);

         ArrayList<Integer> HoteldeVilleLPradel = new ArrayList<>();
        HoteldeVilleLPradel.add(4); HoteldeVilleLPradel.add(23); HoteldeVilleLPradel.add(24);//HoteldeVilleLPradel conecta con foch, croix paquet y cordellers
        conexiones.put( 5, HoteldeVilleLPradel);  

        //LINEA A
        ArrayList<Integer> VaulxenVelinLaSoie = new ArrayList<>();
        VaulxenVelinLaSoie.add(17); //VaulxenVelinLaSoie solo conecta con LaurentBonnevay
        conexiones.put( 16, VaulxenVelinLaSoie);

        ArrayList<Integer> LaurentBonnevay = new ArrayList<>();
        LaurentBonnevay.add(16); LaurentBonnevay.add(18);//LaurentBonnevay conecta con VaulxenVelinLaSoie y Cusset
        conexiones.put( 17, LaurentBonnevay);

        ArrayList<Integer> Cusset = new ArrayList<>();
        Cusset.add(17); Cusset.add(19);//Cusset conecta con LaurentBonnevay y con Flachet
        conexiones.put(18, Cusset);

        ArrayList<Integer> Flachet = new ArrayList<>();
        Flachet.add(18); Flachet.add(20);//Flachet conecta con Cusset y Hôtel de GratteCiel
        conexiones.put( 19, Flachet);

         ArrayList<Integer> GratteCiel = new ArrayList<>();
        GratteCiel.add(19); GratteCiel.add(21); //GratteCiel conecta con Flachet y République Villeurbanne
        conexiones.put( 20, GratteCiel);

         ArrayList<Integer> RepubliqueVilleurbanne = new ArrayList<>();
        RepubliqueVilleurbanne.add(20);RepubliqueVilleurbanne.add(6); //RépubliqueVilleurbanne conecta con GratteCiel y Charpennes
        conexiones.put( 21, RepubliqueVilleurbanne);

        ArrayList<Integer> Charpennes = new ArrayList<>();
        Charpennes.add(21); Charpennes.add(22);Charpennes.add(7);//Charpennes conecta con RepubliqueVilleurbanne, Masséna y con Brotteaux
        conexiones.put( 6, Charpennes);

        ArrayList<Integer> Massena = new ArrayList<>();
        Massena.add(6); Massena.add(23);//Masséna conecta con LaurentBonnevay y con Foch
        conexiones.put(22, Massena);

        ArrayList<Integer> Foch = new ArrayList<>();
        Foch.add(22); Foch.add(5);//Foch conecta con Masséna y HoteldeVilleLPradel
        conexiones.put( 23, Foch);

         ArrayList<Integer> Cordeliers = new ArrayList<>();
        Cordeliers.add(5); Cordeliers.add(25); //Cordeliers conecta con HoteldeVilleLPradel y Bellecour
        conexiones.put( 24, Cordeliers);

        ArrayList<Integer> Bellecour = new ArrayList<>();
        Bellecour.add(24); Bellecour.add(26); //Bellecour conecta con Cordeliers y AmpèreVictorHugo. 
        Bellecour.add(36); Bellecour.add(37); //También con Guillotière Gabriel Péri y Vieux Lyon
        conexiones.put( 25, Bellecour);

        ArrayList<Integer> AmpereVictorHugo = new ArrayList<>();
        AmpereVictorHugo.add(25); AmpereVictorHugo.add(27); //AmpèreVictorHugo conecta con Bellecour y Perrache
        conexiones.put( 26, AmpereVictorHugo);

        ArrayList<Integer> Perrache = new ArrayList<>();
        Perrache.add(26);//GratteCiel conecta con AmpèreVictorHugo 
        conexiones.put( 27, Perrache);

        //LINEA B

        ArrayList<Integer> Brotteaux = new ArrayList<>();
        Brotteaux.add(6);Brotteaux.add(8); //Brotteaux  conecta con Charpennes y GarePartDieuVMerle
        conexiones.put( 7, Brotteaux);

        ArrayList<Integer> GarePartDieuVMerle = new ArrayList<>();
        GarePartDieuVMerle.add(7); GarePartDieuVMerle.add(9);//GarePartDieuVMerle conecta con Brotteaux y PlaceGuichard
        conexiones.put( 8, GarePartDieuVMerle);

        ArrayList<Integer> PlaceGuichard = new ArrayList<>();
        PlaceGuichard.add(8); PlaceGuichard.add(10);//PlaceGuichard conecta con GarePartDieuVMerle y con SaxeGambetta
        conexiones.put(9, PlaceGuichard);

        ArrayList<Integer> SaxeGambetta = new ArrayList<>();
        SaxeGambetta.add(9); SaxeGambetta.add(11);SaxeGambetta.add(35); SaxeGambetta.add(36);//SaxeGambetta conecta con PlaceGuichard, Hôtel de JeanMacé Garibaldi y Guillotière Gabriel Péri
        conexiones.put( 10, SaxeGambetta);

        ArrayList<Integer> JeanMace = new ArrayList<>();
        JeanMace.add(10); JeanMace.add(12);//JeanMacé conecta con SaxeGambetta y Hôtel de PlaceJeanJaurès
        conexiones.put( 11, JeanMace);

        ArrayList<Integer>  PlaceJeanJaures = new ArrayList<>();
        PlaceJeanJaures.add(11);PlaceJeanJaures.add(13); //PlaceJeanJaurès conecta con JeanMacé y Debourg
        conexiones.put( 12, PlaceJeanJaures);

        ArrayList<Integer> Debourg = new ArrayList<>();
        Debourg.add(12); Debourg.add(14);//Debourg conecta con PlaceJeanJaurès y StadedeGerland
        conexiones.put( 13, Debourg);

        ArrayList<Integer> StadedeGerland = new ArrayList<>();
        StadedeGerland.add(13); StadedeGerland.add(15);//StadedeGerland conecta con Debourg y con GaredOullins
        conexiones.put(14, StadedeGerland);

        ArrayList<Integer> GaredOullins = new ArrayList<>();
        GaredOullins.add(14);//SaxeGambetta conecta con StadedeGerland
        conexiones.put( 15, GaredOullins);
        
        //LINEA D
        
        ArrayList<Integer> GaredeVenissieux = new ArrayList<>();
        GaredeVenissieux.add(29); //GaredeVénissieux  conecta con Parilly
        conexiones.put( 28, GaredeVenissieux);

        ArrayList<Integer> Parilly = new ArrayList<>();
        Parilly.add(28); Parilly.add(30);//Parilly conecta con GaredeVénissieux y MermozPinel
        conexiones.put( 29, Parilly);

        ArrayList<Integer> MermozPinel  = new ArrayList<>();
        MermozPinel.add(29); MermozPinel.add(31);//MermozPinel conecta con GarePartDieuVMerle y con Laennec
        conexiones.put(30, MermozPinel);

        ArrayList<Integer> Laennec = new ArrayList<>();
        Laennec.add(30); Laennec.add(32); //Laennec conecta con MermozPinel y GrangeBlanche.
        conexiones.put(31, Laennec);

        ArrayList<Integer> GrangeBlanche = new ArrayList<>();
        GrangeBlanche.add(31); GrangeBlanche.add(33);//GrangeBlanche conecta con Laennec y Hôtel de MonplaisirLumière
        conexiones.put( 32, GrangeBlanche);

        ArrayList<Integer>  MonplaisirLumiere = new ArrayList<>();
        MonplaisirLumiere.add(32);MonplaisirLumiere.add(34); //MonplaisirLumière conecta con GrangeBlanche y SansSouci
        conexiones.put( 33, MonplaisirLumiere);

        ArrayList<Integer> SansSouci = new ArrayList<>();
        SansSouci.add(33); SansSouci.add(35);//SansSouci conecta con MonplaisirLumière y Garibaldi
        conexiones.put( 34, SansSouci);

        ArrayList<Integer> Garibaldi = new ArrayList<>();
        Garibaldi.add(34); Garibaldi.add(10);//Garibaldi conecta con SansSouci y con Saxe Gambetta
        conexiones.put(35, Garibaldi);

        ArrayList<Integer> GuillotiereGabrielPeri = new ArrayList<>();
        GuillotiereGabrielPeri.add(10);GuillotiereGabrielPeri.add(25);//GuillotièreGabrielPéri conecta con Saxe Gambetta y Bellecour
        conexiones.put( 36, GuillotiereGabrielPeri);

         ArrayList<Integer>  VieuxLyon = new ArrayList<>();
        VieuxLyon.add(25);VieuxLyon.add(38); //VieuxLyon conecta con Bellecour y GorgedeLoup
        conexiones.put( 37, VieuxLyon);

        ArrayList<Integer> GorgedeLoup = new ArrayList<>();
        GorgedeLoup.add(37); GorgedeLoup.add(39);//GorgedeLoup conecta con VieuxLyon y Valmy
        conexiones.put( 38, GorgedeLoup);

        ArrayList<Integer> Valmy = new ArrayList<>();
        Valmy.add(38); Valmy.add(40);//Valmy conecta con GorgedeLoup y con GaredeVaise
        conexiones.put(39, Valmy);

        ArrayList<Integer> GaredeVaise = new ArrayList<>();
        GaredeVaise.add(39);//GaredeVaise conecta con Valmy y Bellecour
        conexiones.put( 40, GaredeVaise);

        


        //Tiempos A
        tiemposA.add(new int[] {0, 0}); //Perrache
        tiemposA.add(new int[]  {1, 400}); //Ampère-Victor Hugo
        tiemposA.add(new int[]  {2, 1060 }); //Bellecour
        tiemposA.add(new int[]  {1, 1680 }); //Cordeliers
        tiemposA.add(new int[]  {2, 2140 }); //Hôtel de Ville L. Pradel
        tiemposA.add(new int[]  {1, 2770 }); //Foch
        tiemposA.add(new int[]  {2, 3450 }); //Masséna
        tiemposA.add(new int[]  {2, 4230 }); //Charpennes
        tiemposA.add(new int[]  {2, 5050 }); //République Villeurbanne
        tiemposA.add(new int[]  {2, 5750 }); //Gratte Ciel
        tiemposA.add(new int[]  {1, 6350 }); //Flachet
        tiemposA.add(new int[]  {2, 7150 }); //Cusset
        tiemposA.add(new int[]  {1, 7800 }); //Laurent Bonnevay
        tiemposA.add(new int[]  {2, 8900}); //Vaulx-en-Velin La Soie

        //Tiempos B
        tiemposB.add(new int[] {0, 0}); //Gare d’Oullins
        tiemposB.add(new int[]  {2, 1700}); //Stade de Gerland
        tiemposB.add(new int[]  {2, 2240 }); //Debourg
        tiemposB.add(new int[]  {1, 3020 }); //Place Jean Jaurès
        tiemposB.add(new int[]  {2, 3970 }); //Jean Macé
        tiemposB.add(new int[]  {2, 4930 }); //Saxe Gambetta
        tiemposB.add(new int[]  {1, 5540 }); //Place Guichard
        tiemposB.add(new int[]  {2, 6360 }); //Gare Part-Dieu V. Merle
        tiemposB.add(new int[]  {2, 6960 }); //Brotteaux
        tiemposB.add(new int[]  {2, 7460 }); //Charpennes

        //Tiempos C
        tiemposC.add(new int[] {0, 0}); //Hôtel de Ville L. Pradel
        tiemposC.add(new int[]  {2, 400}); //Croix Paquet
        tiemposC.add(new int[]  {2, 900}); //Croix-Rousse
        tiemposC.add(new int[]  {2, 1550}); //Hénon
        tiemposC.add(new int[]  {2, 2440}); //Cuire

        //Tiempos D
        tiemposD.add(new int[] {0, 0}); //Gare de Vaise
        tiemposD.add(new int[] {2, 670}); //Valmy
        tiemposD.add(new int[] {2, 1640}); //Gorge de Loup
        tiemposD.add(new int[] {2, 3380}); //Vieux Lyon
        tiemposD.add(new int[] {2, 4042}); //Bellecour
        tiemposD.add(new int[] {2, 4742}); //Guillotière Gabriel Péri
        tiemposD.add(new int[] {1, 5142}); //Saxe-Gambetta
        tiemposD.add(new int[] {1, 5742}); //Garibaldi
        tiemposD.add(new int[] {2, 6642}); //Sans Souci
        tiemposD.add(new int[] {2, 7242}); //Monplaisir Lumière
        tiemposD.add(new int[] {2, 8412}); //Grange Blanche
        tiemposD.add(new int[] {2, 10042}); //Laennec
        tiemposD.add(new int[] {2, 10872}); //Mermoz - Pinel
        tiemposD.add(new int[] {2, 12122}); //Parilly
        tiemposD.add(new int[] {2, 13712}); //Gare de Vénissieux
       
        
		
		//horarios

            
        try (BufferedReader br = new BufferedReader(new FileReader("proyecto/src/auxiliar/horario.txt"))) {
            
            String line=br.readLine();
            String horario[]=line.split(";");
           
            for(int i = 0; i<horario.length;i++){
            horarioPerrache.add(Integer.parseInt(horario[i]));
            }
            
            line=br.readLine();
            horario=line.split(";");
            for(int i = 0; i<horario.length;i++){
            horarioGaredOullins.add(Integer.parseInt(horario[i]));
            }
            line=br.readLine();

            horario=line.split(";");
            for(int i = 0; i<horario.length;i++){
            horarioHoteldeVilleLPradel.add(Integer.parseInt(horario[i]));
            }
            line=br.readLine();
            
            horario=line.split(";");
            for(int i = 0; i<horario.length;i++){
            horarioGaredeVaise.add(Integer.parseInt(horario[i]));
            }
            br.close();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }

        //Teniendo esos 4 se calculan los otros a partir del tiempo entre estaciones

        // 5  Hotel de ville
        //6 Charpennes
        //10 saxo gambetta
        //25 Bellecour
        

        //Linea A
        //horariosLineaA.add(horarioPerrache);
        horariosLineaA.add(sumaHorario(horarioPerrache, 3)); //Bellecour
        horariosLineaA.add(sumaHorario(horarioPerrache, 6)); //Hotelle
        horariosLineaA.add(sumaHorario(horarioPerrache, 11)); //Charpennes

        //Linea B
        //horariosLineaB.add(horarioGaredOullins);
        horariosLineaB.add(sumaHorario(horarioGaredOullins,9)); //Saxe gambetta
        horariosLineaB.add(sumaHorario(horarioGaredOullins,16)); //Charpennes


        //Linea C
        horariosLineaC.add(horarioHoteldeVilleLPradel);
        //y ya

        //Linea D
        //horariosLineaD.add(horarioGaredeVaise);
        horariosLineaD.add(sumaHorario(horarioGaredeVaise, 8)); //Bellecour
        horariosLineaD.add(sumaHorario(horarioGaredeVaise, 11)); // Saxe Gambetta


	}

    public static void main(String[] args) {
        inicializa();
        /* 
        System.out.println(" ----------------------------- \nLINEA A \n-----------------------------");
        for (int i = 0; i < horarioPerrache.size(); i++) {
            if (i == horarioPerrache.size()-1)
                System.out.printf("%02d:%02d\n", horarioPerrache.get(i)/60, horarioPerrache.get(i)%60);
            else
            System.out.printf("%02d:%02d, ", horarioPerrache.get(i)/60, horarioPerrache.get(i)%60);
        }
        System.out.println(" -----------------------------\nLINEA B \n-----------------------------");
        for (int i = 0; i < horarioGaredOullins.size(); i++) {
            if (i == horarioGaredOullins.size()-1)
                System.out.printf("%02d:%02d\n", horarioGaredOullins.get(i)/60, horarioGaredOullins.get(i)%60);
            else
            System.out.printf("%02d:%02d, ", horarioGaredOullins.get(i)/60, horarioGaredOullins.get(i)%60);
        }
        System.out.println("-----------------------------\nLINEA C \n-----------------------------");
        for (int i = 0; i < horarioHoteldeVilleLPradel.size(); i++) {
            if (i == horarioHoteldeVilleLPradel.size()-1)
                System.out.printf("%02d:%02d\n", horarioHoteldeVilleLPradel.get(i)/60,horarioHoteldeVilleLPradel.get(i)%60);
            else
            System.out.printf("%02d:%02d, ", horarioHoteldeVilleLPradel.get(i)/60, horarioHoteldeVilleLPradel.get(i)%60);
        }
        System.out.println("-----------------------------\nLINEA D \n-----------------------------");
        for (int i = 0; i < horarioGaredeVaise.size(); i++) {
            if (i == horarioGaredeVaise.size()-1)
                System.out.printf("%02d:%02d\n", horarioGaredeVaise.get(i)/60, horarioGaredeVaise.get(i)%60);
            else
                System.out.printf("%02d:%02d, ", horarioGaredeVaise.get(i)/60, horarioGaredeVaise.get(i)%60);
        }
        */
        System.out.println(Aestrella(22,35,23*60+59));

        
    }
}