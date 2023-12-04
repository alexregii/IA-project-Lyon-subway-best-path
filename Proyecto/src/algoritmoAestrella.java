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
        //LINEA C
        codEstacion.put(1, "Cuire");
        codEstacion.put(2, "Hénon");
        codEstacion.put(3, "Croix-Rousse");
        codEstacion.put(4, "Croix Paquet");
        codEstacion.put(5, "Hôtel de Ville L. Pradel");

        //LINEA B
        codEstacion.put(6, "Charpennes");
        codEstacion.put(7, "Brotteaux ");
        codEstacion.put(8, "Gare Part-Dieu V. Merle");
        codEstacion.put(9, "Place Guichard");
        codEstacion.put(10, "Saxe Gambetta");
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
        codEstacion.put(25, "Bellecour ");
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

        

       
        

        //Hashmap conexiones
        
        //LINEA C
        ArrayList<Integer> cuire = new ArrayList<>();
        cuire.add(2); //Cuire solo conecta con Hénon
        conexiones.put( 1, cuire);

        ArrayList<Integer> henon = new ArrayList<>();
        henon.add(1); henon.add(3);//Hénon conecta con Cuire y Croix-Rousse
        conexiones.put( 2, henon);

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
        conexiones.put( 19, CroixPaquet);

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
        /*
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
        codEstacion.put(40, "Gare de Vaise "); */

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
        conexiones.put( 36, GaredOullins);

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