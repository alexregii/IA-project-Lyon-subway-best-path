
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
 * Para los tiempos es en segundos todo. Las 0:00 es 0 y las 23:59 es  (tener cuidado con viajes que empiezan tarde)
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

    //private ArrayList tiempos = new ArrayList<>();

    private static ArrayList<int[]> tiemposA = new ArrayList<int[]>(); //Empieza en Perrache, acaba en Vaulx-en-Velin La Soie
    private static ArrayList<int[]> tiemposB = new ArrayList<>();
    private static ArrayList<int[]> tiemposC = new ArrayList<>();
    private static ArrayList<int[]> tiemposD = new ArrayList<>();

    private static Map<Integer, String> codEstacion = new HashMap<Integer, String>(); 
    private static Map<Integer, ArrayList<Integer>> conexiones = new HashMap<>(); 
    //private Map estaciones= new HashMap();
    private static ArrayList<Integer> estacionesLineaA;
    private static ArrayList<Integer> estacionesLineaB;
    private static ArrayList<Integer> estacionesLineaC;
    private static ArrayList<Integer> estacionesLineaD;

    private static ArrayList<Integer> horarioPerrache;
    private static ArrayList<Integer> horarioHoteldeVilleLPradel;
    private static ArrayList<Integer> horarioGaredeVaise;
    private static ArrayList<Integer> horaioGaredOullins;



    //Trasbordos: Belecour - Charpennes (2680 ), Saxo Gamebta - Hotel de ville (1710 )


    private int tiempoAdy(int estacion1, int estacion2, int horaActual){ 
        //Da el tiempo que se tarda de ir a una estacion a otra siendo estas adyacentes.
        //Tiene en cuenta tiempo de trasbordo
        ArrayList<Integer> horarioOrig;
        int retraso = 0;
        int res = 0; 
/* 
        if (estacionesLineaA.contains(estacion1) && estacionesLineaA.contains(estacion2)){



            int convLinea = conversionALinea(orig, "A");
            horarioOrig = horariosPerrache.get(convLinea);
            
            while(){
                if(horarioOrig.contains(horaActual+retraso)){
                
                    return retraso + tiemposA.get(convLinea)[0];
                
                 }else retraso++;
            }
            
        
        }*/
         return res;
    }


    private int conversionALinea(int estacion, String linea){ 

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
        return 0;
    }


    private int distEur(int orig, int dest){

        //distEur: dadas dos estaciones devuelve la distancia euristica. Esto lo hace calculando el trasbordo más cercano
        //y luego sabiendo la distancia entre las estaciones de trasbordo que es fija 

        //Si están en la misma linea, es la distancia
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



        //si están en distintas lineas es esto

        int orig1 = trasbordoMasCercano(orig);

        int dif1 = distEur(orig, orig1);

        int dest1 = trasbordoMasCercano(dest);

        int dif2 = distEur(dest, dest1);

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

    private int trasbordoMasCercano(int estacion){


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


    private int[] exploraCamino(int estacion, int anterior, int profundidad){

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

        

        //return res; 

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
        codEstacion.put(10, "Saxe Gambetta"); //Trasbordo
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

        
        estacionesLineaC = new ArrayList<Integer>(); estacionesLineaC.add(1); estacionesLineaC.add(2);
        estacionesLineaC.add(3); estacionesLineaC.add(4); estacionesLineaC.add(5);
        //TODO el resto
        estacionesLineaB = new ArrayList<Integer>(); estacionesLineaB.add(6); estacionesLineaB.add(7);
        estacionesLineaB.add(8); estacionesLineaB.add(9);estacionesLineaB.add(10); estacionesLineaB.add(11);
        estacionesLineaB.add(12); estacionesLineaB.add(13);estacionesLineaB.add(14); estacionesLineaB.add(15);
        estacionesLineaA = new ArrayList<Integer>(); estacionesLineaA.add(16); estacionesLineaA.add(17);
        estacionesLineaA.add(18);estacionesLineaA.add(19);estacionesLineaA.add(20);estacionesLineaA.add(21);
        estacionesLineaA.add(6);estacionesLineaA.add(22);estacionesLineaA.add(23);estacionesLineaA.add(5);
        estacionesLineaA.add(24);estacionesLineaA.add(25);estacionesLineaA.add(26);estacionesLineaA.add(27);
        estacionesLineaD = new ArrayList<Integer>(); estacionesLineaD.add(28);estacionesLineaD.add(29);
        estacionesLineaD.add(30);estacionesLineaD.add(31);estacionesLineaD.add(32);estacionesLineaD.add(33);
        estacionesLineaD.add(34);estacionesLineaD.add(35);estacionesLineaD.add(10);estacionesLineaD.add(36);
        estacionesLineaD.add(25);estacionesLineaD.add(37);estacionesLineaD.add(38);estacionesLineaD.add(39);
        estacionesLineaD.add(40);
        

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

        ArrayList<Integer> Masséna = new ArrayList<>();
        Masséna.add(6); Masséna.add(23);//Masséna conecta con LaurentBonnevay y con Foch
        conexiones.put(22, Masséna);

        ArrayList<Integer> Foch = new ArrayList<>();
        Foch.add(22); Foch.add(5);//Foch conecta con Masséna y HoteldeVilleLPradel
        conexiones.put( 23, Foch);

         ArrayList<Integer> Cordeliers = new ArrayList<>();
        Cordeliers.add(5); Cordeliers.add(25); //Cordeliers conecta con HoteldeVilleLPradel y Bellecour
        conexiones.put( 24, Cordeliers);

        ArrayList<Integer> Bellecour = new ArrayList<>();
        Bellecour.add(24); Bellecour.add(26); //Bellecour conecta con Cordeliers y AmpèreVictorHugo
        conexiones.put( 25, Bellecour);

        ArrayList<Integer> AmpèreVictorHugo = new ArrayList<>();
        AmpèreVictorHugo.add(25); AmpèreVictorHugo.add(27); //AmpèreVictorHugo conecta con Bellecour y Perrache
        conexiones.put( 26, AmpèreVictorHugo);

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

        ArrayList<Integer> JeanMacé = new ArrayList<>();
        JeanMacé.add(10); JeanMacé.add(12);//JeanMacé conecta con SaxeGambetta y Hôtel de PlaceJeanJaurès
        conexiones.put( 11, JeanMacé);

        ArrayList<Integer>  PlaceJeanJaurès = new ArrayList<>();
        PlaceJeanJaurès.add(11);PlaceJeanJaurès.add(13); //PlaceJeanJaurès conecta con JeanMacé y Debourg
        conexiones.put( 12, PlaceJeanJaurès);

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
        
        ArrayList<Integer> GaredeVénissieux = new ArrayList<>();
        GaredeVénissieux.add(29); //GaredeVénissieux  conecta con Parilly
        conexiones.put( 28, GaredeVénissieux);

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

        ArrayList<Integer>  MonplaisirLumière = new ArrayList<>();
        MonplaisirLumière.add(32);MonplaisirLumière.add(34); //MonplaisirLumière conecta con GrangeBlanche y SansSouci
        conexiones.put( 33, MonplaisirLumière);

        ArrayList<Integer> SansSouci = new ArrayList<>();
        SansSouci.add(33); SansSouci.add(35);//SansSouci conecta con MonplaisirLumière y Garibaldi
        conexiones.put( 34, SansSouci);

        ArrayList<Integer> Garibaldi = new ArrayList<>();
        Garibaldi.add(34); Garibaldi.add(10);//Garibaldi conecta con SansSouci y con Saxe Gambetta
        conexiones.put(35, Garibaldi);

        ArrayList<Integer> GuillotièreGabrielPéri = new ArrayList<>();
        GuillotièreGabrielPéri.add(10);GuillotièreGabrielPéri.add(25);//GuillotièreGabrielPéri conecta con Saxe Gambetta y Bellecour
        conexiones.put( 36, GuillotièreGabrielPéri);

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
       
        
		
		//Burrada para que funcione por ahora y poder probar
        horarioPerrache.add(8);
        horarioPerrache.add(20);
        horarioPerrache.add(301);
        horarioPerrache.add(313);
        horarioPerrache.add(323);
        horarioPerrache.add(333);
        horarioPerrache.add(342);
        horarioPerrache.add(351);
        horarioPerrache.add(357);
        horarioPerrache.add(362);
        horarioPerrache.add(368);
        horarioPerrache.add(373);
        horarioPerrache.add(379);
        horarioPerrache.add(384);
        horarioPerrache.add(390);
        horarioPerrache.add(395);
        horarioPerrache.add(400);
        horarioPerrache.add(405);
        horarioPerrache.add(408);
        horarioPerrache.add(410);
        horarioPerrache.add(413);
        horarioPerrache.add(416);
        horarioPerrache.add(419);
        horarioPerrache.add(422);
        horarioPerrache.add(425);
        horarioPerrache.add(428);
        horarioPerrache.add(431);
        horarioPerrache.add(434);
        horarioPerrache.add(437);
        horarioPerrache.add(440);
        horarioPerrache.add(443);
        horarioPerrache.add(446);
        horarioPerrache.add(449);
        horarioPerrache.add(452);
        horarioPerrache.add(454);
        horarioPerrache.add(457);
        horarioPerrache.add(460);
        horarioPerrache.add(463);
        horarioPerrache.add(466);
        horarioPerrache.add(469);
        horarioPerrache.add(472);
        horarioPerrache.add(476);
        horarioPerrache.add(479);
        horarioPerrache.add(482);
        horarioPerrache.add(484);
        horarioPerrache.add(487);
        horarioPerrache.add(490);
        horarioPerrache.add(493);
        horarioPerrache.add(496);
        horarioPerrache.add(499);
        horarioPerrache.add(502);
        horarioPerrache.add(505);
        horarioPerrache.add(508);
        horarioPerrache.add(511);
        horarioPerrache.add(514);
        horarioPerrache.add(517);
        horarioPerrache.add(520);
        horarioPerrache.add(523);
        horarioPerrache.add(526);
        horarioPerrache.add(529);
        horarioPerrache.add(532);
        horarioPerrache.add(535);
        horarioPerrache.add(538);
        horarioPerrache.add(541);
        horarioPerrache.add(544);
        horarioPerrache.add(547);
        horarioPerrache.add(550);
        horarioPerrache.add(553);
        horarioPerrache.add(556);
        horarioPerrache.add(559);
        horarioPerrache.add(562);
        horarioPerrache.add(567);
        horarioPerrache.add(573);
        horarioPerrache.add(578);
        horarioPerrache.add(583);
        horarioPerrache.add(589);
        horarioPerrache.add(594);
        horarioPerrache.add(599);
        horarioPerrache.add(605);
        horarioPerrache.add(609);
        horarioPerrache.add(615);
        horarioPerrache.add(620);
        horarioPerrache.add(625);
        horarioPerrache.add(631);
        horarioPerrache.add(636);
        horarioPerrache.add(642);
        horarioPerrache.add(647);
        horarioPerrache.add(652);
        horarioPerrache.add(657);
        horarioPerrache.add(662);
        horarioPerrache.add(667);
        horarioPerrache.add(673);
        horarioPerrache.add(678);
        horarioPerrache.add(683);
        horarioPerrache.add(687);
        horarioPerrache.add(691);
        horarioPerrache.add(695);
        horarioPerrache.add(699);
        horarioPerrache.add(703);
        horarioPerrache.add(707);
        horarioPerrache.add(711);
        horarioPerrache.add(714);
        horarioPerrache.add(718);
        horarioPerrache.add(722);
        horarioPerrache.add(726);
        horarioPerrache.add(730);
        horarioPerrache.add(734);
        horarioPerrache.add(738);
        horarioPerrache.add(742);
        horarioPerrache.add(746);
        horarioPerrache.add(750);
        horarioPerrache.add(754);
        horarioPerrache.add(758);
        horarioPerrache.add(762);
        horarioPerrache.add(766);
        horarioPerrache.add(770);
        horarioPerrache.add(774);
        horarioPerrache.add(778);
        horarioPerrache.add(782);
        horarioPerrache.add(786);
        horarioPerrache.add(790);
        horarioPerrache.add(794);
        horarioPerrache.add(798);
        horarioPerrache.add(802);
        horarioPerrache.add(806);
        horarioPerrache.add(810);
        horarioPerrache.add(813);
        horarioPerrache.add(817);
        horarioPerrache.add(821);
        horarioPerrache.add(824);
        horarioPerrache.add(828);
        horarioPerrache.add(832);
        horarioPerrache.add(836);
        horarioPerrache.add(839);
        horarioPerrache.add(843);
        horarioPerrache.add(847);
        horarioPerrache.add(850);
        horarioPerrache.add(854);
        horarioPerrache.add(858);
        horarioPerrache.add(861);
        horarioPerrache.add(865);
        horarioPerrache.add(869);
        horarioPerrache.add(872);
        horarioPerrache.add(876);
        horarioPerrache.add(880);
        horarioPerrache.add(883);
        horarioPerrache.add(887);
        horarioPerrache.add(891);
        horarioPerrache.add(894);
        horarioPerrache.add(898);
        horarioPerrache.add(902);
        horarioPerrache.add(906);
        horarioPerrache.add(909);
        horarioPerrache.add(913);
        horarioPerrache.add(917);
        horarioPerrache.add(920);
        horarioPerrache.add(924);
        horarioPerrache.add(928);
        horarioPerrache.add(931);
        horarioPerrache.add(935);
        horarioPerrache.add(939);
        horarioPerrache.add(943);
        horarioPerrache.add(947);
        horarioPerrache.add(950);
        horarioPerrache.add(953);
        horarioPerrache.add(958);
        horarioPerrache.add(961);
        horarioPerrache.add(965);
        horarioPerrache.add(968);
        horarioPerrache.add(972);
        horarioPerrache.add(976);
        horarioPerrache.add(980);
        horarioPerrache.add(983);
        horarioPerrache.add(986);
        horarioPerrache.add(989);
        horarioPerrache.add(992);
        horarioPerrache.add(995);
        horarioPerrache.add(998);
        horarioPerrache.add(1001);
        horarioPerrache.add(1004);
        horarioPerrache.add(1007);
        horarioPerrache.add(1010);
        horarioPerrache.add(1013);
        horarioPerrache.add(1016);
        horarioPerrache.add(1019);
        horarioPerrache.add(1022);
        horarioPerrache.add(1025);
        horarioPerrache.add(1028);
        horarioPerrache.add(1031);
        horarioPerrache.add(1034);
        horarioPerrache.add(1037);
        horarioPerrache.add(1040);
        horarioPerrache.add(1043);
        horarioPerrache.add(1046);
        horarioPerrache.add(1049);
        horarioPerrache.add(1051);
        horarioPerrache.add(1054);
        horarioPerrache.add(1057);
        horarioPerrache.add(1060);
        horarioPerrache.add(1063);
        horarioPerrache.add(1066);
        horarioPerrache.add(1069);
        horarioPerrache.add(1072);
        horarioPerrache.add(1075);
        horarioPerrache.add(1078);
        horarioPerrache.add(1081);
        horarioPerrache.add(1084);
        horarioPerrache.add(1087);
        horarioPerrache.add(1090);
        horarioPerrache.add(1093);
        horarioPerrache.add(1096);
        horarioPerrache.add(1099);
        horarioPerrache.add(1102);
        horarioPerrache.add(1105);
        horarioPerrache.add(1108);
        horarioPerrache.add(1111);
        horarioPerrache.add(1114);
        horarioPerrache.add(1117);
        horarioPerrache.add(1120);
        horarioPerrache.add(1123);
        horarioPerrache.add(1126);
        horarioPerrache.add(1129);
        horarioPerrache.add(1132);
        horarioPerrache.add(1135);
        horarioPerrache.add(1138);
        horarioPerrache.add(1141);
        horarioPerrache.add(1144);
        horarioPerrache.add(1147);
        horarioPerrache.add(1150);
        horarioPerrache.add(1153);
        horarioPerrache.add(1156);
        horarioPerrache.add(1159);
        horarioPerrache.add(1162);
        horarioPerrache.add(1166);
        horarioPerrache.add(1169);
        horarioPerrache.add(1172);
        horarioPerrache.add(1177);
        horarioPerrache.add(1180);
        horarioPerrache.add(1183);
        horarioPerrache.add(1186);
        horarioPerrache.add(1189);
        horarioPerrache.add(1192);
        horarioPerrache.add(1195);
        horarioPerrache.add(1199);
        horarioPerrache.add(1203);
        horarioPerrache.add(1206);
        horarioPerrache.add(1210);
        horarioPerrache.add(1214);
        horarioPerrache.add(1217);
        horarioPerrache.add(1221);
        horarioPerrache.add(1226);
        horarioPerrache.add(1231);
        horarioPerrache.add(1236);
        horarioPerrache.add(1242);
        horarioPerrache.add(1247);
        horarioPerrache.add(1252);
        horarioPerrache.add(1258);
        horarioPerrache.add(1264);
        horarioPerrache.add(1270);
        horarioPerrache.add(1275);
        horarioPerrache.add(1283);
        horarioPerrache.add(1291);
        horarioPerrache.add(1299);
        horarioPerrache.add(1307);
        horarioPerrache.add(1314);
        horarioPerrache.add(1322);
        horarioPerrache.add(1330);
        horarioPerrache.add(1338);
        horarioPerrache.add(1346);
        horarioPerrache.add(1353);
        horarioPerrache.add(1361);
        horarioPerrache.add(1369);
        horarioPerrache.add(1377);
        horarioPerrache.add(1385);
        horarioPerrache.add(1393);
        horarioPerrache.add(1400);
        horarioPerrache.add(1408);
        horarioPerrache.add(1416);
        horarioPerrache.add(1424);
        horarioPerrache.add(1432);
        horarioPerrache.add(1439);
        horaioGaredOullins.add(3);
        horaioGaredOullins.add(7);
        horaioGaredOullins.add(11);
        horaioGaredOullins.add(293);
        horaioGaredOullins.add(296);
        horaioGaredOullins.add(421);
        horaioGaredOullins.add(423);
        horaioGaredOullins.add(425);
        horaioGaredOullins.add(427);
        horaioGaredOullins.add(430);
        horaioGaredOullins.add(432);
        horaioGaredOullins.add(434);
        horaioGaredOullins.add(436);
        horaioGaredOullins.add(438);
        horaioGaredOullins.add(440);
        horaioGaredOullins.add(442);
        horaioGaredOullins.add(444);
        horaioGaredOullins.add(446);
        horaioGaredOullins.add(448);
        horaioGaredOullins.add(450);
        horaioGaredOullins.add(452);
        horaioGaredOullins.add(455);
        horaioGaredOullins.add(457);
        horaioGaredOullins.add(459);
        horaioGaredOullins.add(461);
        horaioGaredOullins.add(463);
        horaioGaredOullins.add(465);
        horaioGaredOullins.add(467);
        horaioGaredOullins.add(469);
        horaioGaredOullins.add(471);
        horaioGaredOullins.add(473);
        horaioGaredOullins.add(475);
        horaioGaredOullins.add(477);
        horaioGaredOullins.add(901);
        horaioGaredOullins.add(903);
        horaioGaredOullins.add(906);
        horaioGaredOullins.add(908);
        horaioGaredOullins.add(911);
        horaioGaredOullins.add(914);
        horaioGaredOullins.add(916);
        horaioGaredOullins.add(919);
        horaioGaredOullins.add(921);
        horaioGaredOullins.add(924);
        horaioGaredOullins.add(927);
        horaioGaredOullins.add(929);
        horaioGaredOullins.add(932);
        horaioGaredOullins.add(934);
        horaioGaredOullins.add(937);
        horaioGaredOullins.add(940);
        horaioGaredOullins.add(942);
        horaioGaredOullins.add(944);
        horaioGaredOullins.add(946);
        horaioGaredOullins.add(948);
        horaioGaredOullins.add(950);
        horaioGaredOullins.add(952);
        horaioGaredOullins.add(954);
        horaioGaredOullins.add(956);
        horaioGaredOullins.add(959);
        horaioGaredOullins.add(961);
        horaioGaredOullins.add(963);
        horaioGaredOullins.add(965);
        horaioGaredOullins.add(967);
        horaioGaredOullins.add(969);
        horaioGaredOullins.add(971);
        horaioGaredOullins.add(973);
        horaioGaredOullins.add(975);
        horaioGaredOullins.add(977);
        horaioGaredOullins.add(979);
        horaioGaredOullins.add(981);
        horaioGaredOullins.add(984);
        horaioGaredOullins.add(986);
        horaioGaredOullins.add(988);
        horaioGaredOullins.add(990);
        horaioGaredOullins.add(992);
        horaioGaredOullins.add(994);
        horaioGaredOullins.add(996);
        horaioGaredOullins.add(998);
        horaioGaredOullins.add(1000);
        horaioGaredOullins.add(1002);
        horaioGaredOullins.add(1004);
        horaioGaredOullins.add(1006);
        horaioGaredOullins.add(1009);
        horaioGaredOullins.add(1011);
        horaioGaredOullins.add(1013);
        horaioGaredOullins.add(1015);
        horaioGaredOullins.add(1017);
        horaioGaredOullins.add(1019);
        horaioGaredOullins.add(1021);
        horaioGaredOullins.add(1023);
        horaioGaredOullins.add(1025);
        horaioGaredOullins.add(1027);
        horaioGaredOullins.add(1029);
        horaioGaredOullins.add(1031);
        horaioGaredOullins.add(1033);
        horaioGaredOullins.add(1036);
        horaioGaredOullins.add(1038);
        horaioGaredOullins.add(1040);
        horaioGaredOullins.add(1042);
        horaioGaredOullins.add(1044);
        horaioGaredOullins.add(1046);
        horaioGaredOullins.add(1048);
        horaioGaredOullins.add(1050);
        horaioGaredOullins.add(1052);
        horaioGaredOullins.add(1054);
        horaioGaredOullins.add(1056);
        horaioGaredOullins.add(1058);
        horaioGaredOullins.add(1061);
        horaioGaredOullins.add(1063);
        horaioGaredOullins.add(1065);
        horaioGaredOullins.add(1067);
        horaioGaredOullins.add(1069);
        horaioGaredOullins.add(1071);
        horaioGaredOullins.add(1073);
        horaioGaredOullins.add(1075);
        horaioGaredOullins.add(1077);
        horaioGaredOullins.add(1079);
        horaioGaredOullins.add(1081);
        horaioGaredOullins.add(1083);
        horaioGaredOullins.add(1086);
        horaioGaredOullins.add(1088);
        horaioGaredOullins.add(1090);
        horaioGaredOullins.add(1092);
        horaioGaredOullins.add(1094);
        horaioGaredOullins.add(1096);
        horaioGaredOullins.add(1098);
        horaioGaredOullins.add(1100);
        horaioGaredOullins.add(1102);
        horaioGaredOullins.add(1104);
        horaioGaredOullins.add(1106);
        horaioGaredOullins.add(1108);
        horaioGaredOullins.add(1111);
        horaioGaredOullins.add(1113);
        horaioGaredOullins.add(1115);
        horaioGaredOullins.add(1117);
        horaioGaredOullins.add(1119);
        horaioGaredOullins.add(1121);
        horaioGaredOullins.add(1123);
        horaioGaredOullins.add(1125);
        horaioGaredOullins.add(1127);
        horaioGaredOullins.add(1129);
        horaioGaredOullins.add(1131);
        horaioGaredOullins.add(1133);
        horaioGaredOullins.add(1135);
        horaioGaredOullins.add(1138);
        horaioGaredOullins.add(1202);
        horaioGaredOullins.add(1206);
        horaioGaredOullins.add(1208);
        horaioGaredOullins.add(1211);
        horaioGaredOullins.add(1213);
        horaioGaredOullins.add(1217);
        horaioGaredOullins.add(1219);
        horaioGaredOullins.add(1221);
        horaioGaredOullins.add(1225);
        horaioGaredOullins.add(1230);
        horaioGaredOullins.add(1233);
        horaioGaredOullins.add(1236);
        horaioGaredOullins.add(1239);
        horaioGaredOullins.add(1242);
        horaioGaredOullins.add(1245);
        horaioGaredOullins.add(1248);
        horaioGaredOullins.add(1251);
        horaioGaredOullins.add(1254);
        horaioGaredOullins.add(1257);
        horaioGaredOullins.add(1383);
        horaioGaredOullins.add(1387);
        horaioGaredOullins.add(1391);
        horaioGaredOullins.add(1395);
        horaioGaredOullins.add(1399);
        horaioGaredOullins.add(1403);
        horaioGaredOullins.add(1407);
        horaioGaredOullins.add(1411);
        horaioGaredOullins.add(1415);
        horaioGaredOullins.add(1419);
        horaioGaredOullins.add(1423);
        horaioGaredOullins.add(1427);
        horaioGaredOullins.add(1431);
        horaioGaredOullins.add(1435);
        horaioGaredOullins.add(1439);
        horarioHoteldeVilleLPradel.add(3);
        horarioHoteldeVilleLPradel.add(13);
        horarioHoteldeVilleLPradel.add(25);
        horarioHoteldeVilleLPradel.add(366);
        horarioHoteldeVilleLPradel.add(377);
        horarioHoteldeVilleLPradel.add(388);
        horarioHoteldeVilleLPradel.add(396);
        horarioHoteldeVilleLPradel.add(402);
        horarioHoteldeVilleLPradel.add(407);
        horarioHoteldeVilleLPradel.add(413);
        horarioHoteldeVilleLPradel.add(418);
        horarioHoteldeVilleLPradel.add(424);
        horarioHoteldeVilleLPradel.add(429);
        horarioHoteldeVilleLPradel.add(435);
        horarioHoteldeVilleLPradel.add(440);
        horarioHoteldeVilleLPradel.add(446);
        horarioHoteldeVilleLPradel.add(451);
        horarioHoteldeVilleLPradel.add(457);
        horarioHoteldeVilleLPradel.add(462);
        horarioHoteldeVilleLPradel.add(468);
        horarioHoteldeVilleLPradel.add(473);
        horarioHoteldeVilleLPradel.add(479);
        horarioHoteldeVilleLPradel.add(484);
        horarioHoteldeVilleLPradel.add(490);
        horarioHoteldeVilleLPradel.add(495);
        horarioHoteldeVilleLPradel.add(501);
        horarioHoteldeVilleLPradel.add(506);
        horarioHoteldeVilleLPradel.add(512);
        horarioHoteldeVilleLPradel.add(517);
        horarioHoteldeVilleLPradel.add(523);
        horarioHoteldeVilleLPradel.add(528);
        horarioHoteldeVilleLPradel.add(534);
        horarioHoteldeVilleLPradel.add(539);
        horarioHoteldeVilleLPradel.add(545);
        horarioHoteldeVilleLPradel.add(550);
        horarioHoteldeVilleLPradel.add(556);
        horarioHoteldeVilleLPradel.add(561);
        horarioHoteldeVilleLPradel.add(567);
        horarioHoteldeVilleLPradel.add(572);
        horarioHoteldeVilleLPradel.add(578);
        horarioHoteldeVilleLPradel.add(586);
        horarioHoteldeVilleLPradel.add(593);
        horarioHoteldeVilleLPradel.add(601);
        horarioHoteldeVilleLPradel.add(608);
        horarioHoteldeVilleLPradel.add(616);
        horarioHoteldeVilleLPradel.add(624);
        horarioHoteldeVilleLPradel.add(631);
        horarioHoteldeVilleLPradel.add(639);
        horarioHoteldeVilleLPradel.add(646);
        horarioHoteldeVilleLPradel.add(654);
        horarioHoteldeVilleLPradel.add(661);
        horarioHoteldeVilleLPradel.add(669);
        horarioHoteldeVilleLPradel.add(676);
        horarioHoteldeVilleLPradel.add(684);
        horarioHoteldeVilleLPradel.add(691);
        horarioHoteldeVilleLPradel.add(699);
        horarioHoteldeVilleLPradel.add(706);
        horarioHoteldeVilleLPradel.add(714);
        horarioHoteldeVilleLPradel.add(721);
        horarioHoteldeVilleLPradel.add(729);
        horarioHoteldeVilleLPradel.add(736);
        horarioHoteldeVilleLPradel.add(744);
        horarioHoteldeVilleLPradel.add(751);
        horarioHoteldeVilleLPradel.add(759);
        horarioHoteldeVilleLPradel.add(766);
        horarioHoteldeVilleLPradel.add(774);
        horarioHoteldeVilleLPradel.add(781);
        horarioHoteldeVilleLPradel.add(789);
        horarioHoteldeVilleLPradel.add(796);
        horarioHoteldeVilleLPradel.add(804);
        horarioHoteldeVilleLPradel.add(811);
        horarioHoteldeVilleLPradel.add(819);
        horarioHoteldeVilleLPradel.add(826);
        horarioHoteldeVilleLPradel.add(834);
        horarioHoteldeVilleLPradel.add(841);
        horarioHoteldeVilleLPradel.add(849);
        horarioHoteldeVilleLPradel.add(856);
        horarioHoteldeVilleLPradel.add(864);
        horarioHoteldeVilleLPradel.add(871);
        horarioHoteldeVilleLPradel.add(879);
        horarioHoteldeVilleLPradel.add(886);
        horarioHoteldeVilleLPradel.add(894);
        horarioHoteldeVilleLPradel.add(901);
        horarioHoteldeVilleLPradel.add(909);
        horarioHoteldeVilleLPradel.add(916);
        horarioHoteldeVilleLPradel.add(924);
        horarioHoteldeVilleLPradel.add(931);
        horarioHoteldeVilleLPradel.add(939);
        horarioHoteldeVilleLPradel.add(946);
        horarioHoteldeVilleLPradel.add(953);
        horarioHoteldeVilleLPradel.add(1021);
        horarioHoteldeVilleLPradel.add(1027);
        horarioHoteldeVilleLPradel.add(1032);
        horarioHoteldeVilleLPradel.add(1038);
        horarioHoteldeVilleLPradel.add(1043);
        horarioHoteldeVilleLPradel.add(1049);
        horarioHoteldeVilleLPradel.add(1054);
        horarioHoteldeVilleLPradel.add(1060);
        horarioHoteldeVilleLPradel.add(1065);
        horarioHoteldeVilleLPradel.add(1071);
        horarioHoteldeVilleLPradel.add(1076);
        horarioHoteldeVilleLPradel.add(1082);
        horarioHoteldeVilleLPradel.add(1087);
        horarioHoteldeVilleLPradel.add(1093);
        horarioHoteldeVilleLPradel.add(1098);
        horarioHoteldeVilleLPradel.add(1104);
        horarioHoteldeVilleLPradel.add(1109);
        horarioHoteldeVilleLPradel.add(1115);
        horarioHoteldeVilleLPradel.add(1120);
        horarioHoteldeVilleLPradel.add(1126);
        horarioHoteldeVilleLPradel.add(1131);
        horarioHoteldeVilleLPradel.add(1137);
        horarioHoteldeVilleLPradel.add(1142);
        horarioHoteldeVilleLPradel.add(1148);
        horarioHoteldeVilleLPradel.add(1153);
        horarioHoteldeVilleLPradel.add(1159);
        horarioHoteldeVilleLPradel.add(1164);
        horarioHoteldeVilleLPradel.add(1170);
        horarioHoteldeVilleLPradel.add(1175);
        horarioHoteldeVilleLPradel.add(1181);
        horarioHoteldeVilleLPradel.add(1188);
        horarioHoteldeVilleLPradel.add(1196);
        horarioHoteldeVilleLPradel.add(1203);
        horarioHoteldeVilleLPradel.add(1211);
        horarioHoteldeVilleLPradel.add(1218);
        horarioHoteldeVilleLPradel.add(1226);
        horarioHoteldeVilleLPradel.add(1233);
        horarioHoteldeVilleLPradel.add(1241);
        horarioHoteldeVilleLPradel.add(1248);
        horarioHoteldeVilleLPradel.add(1256);
        horarioHoteldeVilleLPradel.add(1263);
        horarioHoteldeVilleLPradel.add(1271);
        horarioHoteldeVilleLPradel.add(1278);
        horarioHoteldeVilleLPradel.add(1286);
        horarioHoteldeVilleLPradel.add(1293);
        horarioHoteldeVilleLPradel.add(1301);
        horarioHoteldeVilleLPradel.add(1308);
        horarioHoteldeVilleLPradel.add(1316);
        horarioHoteldeVilleLPradel.add(1323);
        horarioHoteldeVilleLPradel.add(1331);
        horarioHoteldeVilleLPradel.add(1338);
        horarioHoteldeVilleLPradel.add(1346);
        horarioHoteldeVilleLPradel.add(1353);
        horarioHoteldeVilleLPradel.add(1361);
        horarioHoteldeVilleLPradel.add(1368);
        horarioHoteldeVilleLPradel.add(1376);
        horarioHoteldeVilleLPradel.add(1383);
        horarioHoteldeVilleLPradel.add(1391);
        horarioHoteldeVilleLPradel.add(1398);
        horarioHoteldeVilleLPradel.add(1406);
        horarioHoteldeVilleLPradel.add(1413);
        horarioHoteldeVilleLPradel.add(1421);
        horarioHoteldeVilleLPradel.add(1428);
        horarioHoteldeVilleLPradel.add(1436);
        horarioGaredeVaise.add(3);
        horarioGaredeVaise.add(8);
        horarioGaredeVaise.add(14);
        horarioGaredeVaise.add(20);
        horarioGaredeVaise.add(301);
        horarioGaredeVaise.add(306);
        horarioGaredeVaise.add(311);
        horarioGaredeVaise.add(316);
        horarioGaredeVaise.add(322);
        horarioGaredeVaise.add(327);
        horarioGaredeVaise.add(332);
        horarioGaredeVaise.add(338);
        horarioGaredeVaise.add(343);
        horarioGaredeVaise.add(348);
        horarioGaredeVaise.add(353);
        horarioGaredeVaise.add(359);
        horarioGaredeVaise.add(364);
        horarioGaredeVaise.add(369);
        horarioGaredeVaise.add(374);
        horarioGaredeVaise.add(380);
        horarioGaredeVaise.add(385);
        horarioGaredeVaise.add(389);
        horarioGaredeVaise.add(392);
        horarioGaredeVaise.add(395);
        horarioGaredeVaise.add(399);
        horarioGaredeVaise.add(402);
        horarioGaredeVaise.add(404);
        horarioGaredeVaise.add(406);
        horarioGaredeVaise.add(409);
        horarioGaredeVaise.add(411);
        horarioGaredeVaise.add(413);
        horarioGaredeVaise.add(415);
        horarioGaredeVaise.add(417);
        horarioGaredeVaise.add(541);
        horarioGaredeVaise.add(543);
        horarioGaredeVaise.add(545);
        horarioGaredeVaise.add(547);
        horarioGaredeVaise.add(549);
        horarioGaredeVaise.add(551);
        horarioGaredeVaise.add(553);
        horarioGaredeVaise.add(556);
        horarioGaredeVaise.add(558);
        horarioGaredeVaise.add(560);
        horarioGaredeVaise.add(562);
        horarioGaredeVaise.add(564);
        horarioGaredeVaise.add(567);
        horarioGaredeVaise.add(570);
        horarioGaredeVaise.add(572);
        horarioGaredeVaise.add(575);
        horarioGaredeVaise.add(578);
        horarioGaredeVaise.add(581);
        horarioGaredeVaise.add(584);
        horarioGaredeVaise.add(587);
        horarioGaredeVaise.add(590);
        horarioGaredeVaise.add(593);
        horarioGaredeVaise.add(596);
        horarioGaredeVaise.add(599);
        horarioGaredeVaise.add(602);
        horarioGaredeVaise.add(605);
        horarioGaredeVaise.add(608);
        horarioGaredeVaise.add(610);
        horarioGaredeVaise.add(613);
        horarioGaredeVaise.add(616);
        horarioGaredeVaise.add(619);
        horarioGaredeVaise.add(622);
        horarioGaredeVaise.add(625);
        horarioGaredeVaise.add(628);
        horarioGaredeVaise.add(631);
        horarioGaredeVaise.add(634);
        horarioGaredeVaise.add(637);
        horarioGaredeVaise.add(640);
        horarioGaredeVaise.add(643);
        horarioGaredeVaise.add(646);
        horarioGaredeVaise.add(648);
        horarioGaredeVaise.add(651);
        horarioGaredeVaise.add(654);
        horarioGaredeVaise.add(657);
        horarioGaredeVaise.add(1021);
        horarioGaredeVaise.add(1023);
        horarioGaredeVaise.add(1025);
        horarioGaredeVaise.add(1026);
        horarioGaredeVaise.add(1028);
        horarioGaredeVaise.add(1030);
        horarioGaredeVaise.add(1032);
        horarioGaredeVaise.add(1034);
        horarioGaredeVaise.add(1036);
        horarioGaredeVaise.add(1038);
        horarioGaredeVaise.add(1040);
        horarioGaredeVaise.add(1042);
        horarioGaredeVaise.add(1043);
        horarioGaredeVaise.add(1045);
        horarioGaredeVaise.add(1047);
        horarioGaredeVaise.add(1049);
        horarioGaredeVaise.add(1051);
        horarioGaredeVaise.add(1053);
        horarioGaredeVaise.add(1054);
        horarioGaredeVaise.add(1056);
        horarioGaredeVaise.add(1058);
        horarioGaredeVaise.add(1060);
        horarioGaredeVaise.add(1062);
        horarioGaredeVaise.add(1063);
        horarioGaredeVaise.add(1065);
        horarioGaredeVaise.add(1067);
        horarioGaredeVaise.add(1069);
        horarioGaredeVaise.add(1071);
        horarioGaredeVaise.add(1073);
        horarioGaredeVaise.add(1074);
        horarioGaredeVaise.add(1076);
        horarioGaredeVaise.add(1078);
        horarioGaredeVaise.add(1264);
        horarioGaredeVaise.add(1269);
        horarioGaredeVaise.add(1273);
        horarioGaredeVaise.add(1278);
        horarioGaredeVaise.add(1283);
        horarioGaredeVaise.add(1288);
        horarioGaredeVaise.add(1293);
        horarioGaredeVaise.add(1297);
        horarioGaredeVaise.add(1302);
        horarioGaredeVaise.add(1307);
        horarioGaredeVaise.add(1312);
        horarioGaredeVaise.add(1317);
        horarioGaredeVaise.add(1321);
        horarioGaredeVaise.add(1327);
        horarioGaredeVaise.add(1332);
        horarioGaredeVaise.add(1338);
        horarioGaredeVaise.add(1343);
        horarioGaredeVaise.add(1349);
        horarioGaredeVaise.add(1354);
        horarioGaredeVaise.add(1360);
        horarioGaredeVaise.add(1365);
        horarioGaredeVaise.add(1371);
        horarioGaredeVaise.add(1376);
        horarioGaredeVaise.add(1382);
        horarioGaredeVaise.add(1387);
        horarioGaredeVaise.add(1393);
        horarioGaredeVaise.add(1398);
        horarioGaredeVaise.add(1404);
        horarioGaredeVaise.add(1409);
        horarioGaredeVaise.add(1415);
        horarioGaredeVaise.add(1420);
        horarioGaredeVaise.add(1426);
        horarioGaredeVaise.add(1431);
        horarioGaredeVaise.add(1437);

		
	}




}