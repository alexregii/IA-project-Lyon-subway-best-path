

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.border.Border;

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

    static Map<Integer, String> codEstacion = new HashMap<Integer, String>(); 
    private static Map<Integer, ArrayList<Integer>> conexiones = new HashMap<>(); 
    //private Map estaciones= new HashMap();
    static ArrayList<Integer> estacionesLineaA;
    static ArrayList<Integer> estacionesLineaB;
    static ArrayList<Integer> estacionesLineaC;
    static ArrayList<Integer> estacionesLineaD;

    private static  ArrayList<Integer> horarioPerrache = new ArrayList<Integer>();
    private static  ArrayList<Integer> horarioHoteldeVilleLPradel = new ArrayList<Integer>();
    private static  ArrayList<Integer> horarioGaredeVaise = new ArrayList<Integer>();
    private static  ArrayList<Integer> horarioGaredOullins = new ArrayList<Integer>();


    private static ArrayList<ArrayList<Integer>> horariosLineaA = new  ArrayList<ArrayList<Integer>>();
    private static ArrayList<ArrayList<Integer>> horariosLineaB = new  ArrayList<ArrayList<Integer>>();
    private static ArrayList<ArrayList<Integer>> horariosLineaC = new  ArrayList<ArrayList<Integer>>();
    private static ArrayList<ArrayList<Integer>> horariosLineaD = new  ArrayList<ArrayList<Integer>>();

    private static Integer horarioP[]={8,20,301,313,323,333,342,351,357,362,368,373,379,384,390,395,400,405,408,410,413,416,419,422,425,428,431,434,437,440,443,446,449,452,454,457,460,463,466,469,472,476,479,482,484,487,490,493,496,499,502,505,508,511,514,517,520,523,526,529,532,535,538,541,544,547,550,553,556,559,562,567,573,578,583,589,594,599,605,609,615,620,625,631,636,642,647,652,657,662,667,673,678,683,687,691,695,699,703,707,711,714,718,722,726,730,734,738,742,746,750,754,758,762,766,770,774,778,782,786,790,794,798,802,806,810,813,817,821,824,828,832,836,839,843,847,850,854,858,861,865,869,872,876,880,883,887,891,894,898,902,906,909,913,917,920,924,928,931,935,939,943,947,950,953,958,961,965,968,972,976,980,983,986,989,992,995,998,1001,1004,1007,1010,1013,1016,1019,1022,1025,1028,1031,1034,1037,1040,1043,1046,1049,1051,1054,1057,1060,1063,1066,1069,1072,1075,1078,1081,1084,1087,1090,1093,1096,1099,1102,1105,1108,1111,1114,1117,1120,1123,1126,1129,1132,1135,1138,1141,1144,1147,1150,1153,1156,1159,1162,1166,1169,1172,1177,1180,1183,1186,1189,1192,1195,1199,1203,1206,1210,1214,1217,1221,1226,1231,1236,1242,1247,1252,1258,1264,1270,1275,1283,1291,1299,1307,1314,1322,1330,1338,1346,1353,1361,1369,1377,1385,1393,1400,1408,1416,1424,1432,1439};
    private static Integer horarioGO[]={3,7,11,293,296,300,303,306,310,313,316,320,323,326,330,333,336,339,343,346,349,353,356,360,363,366,370,373,375,378,380,382,384,386,388,390,392,394,396,398,400,402,405,407,409,411,413,415,417,419,421,423,425,427,430,432,434,436,438,440,442,444,446,448,450,452,455,457,459,461,463,465,467,469,471,473,475,477,480,482,484,486,488,490,492,494,496,498,500,502,504,507,509,511,513,515,517,519,521,523,525,527,529,532,534,536,538,540,542,544,546,548,550,552,554,557,559,561,563,565,567,569,571,575,577,579,582,586,588,590,594,596,598,600,603,605,609,611,613,618,621,623,626,629,631,634,636,639,642,644,647,649,652,655,657,660,663,665,668,670,673,676,678,681,683,686,689,691,694,697,699,702,704,707,710,712,715,717,720,723,725,728,731,733,736,738,741,744,746,749,751,754,757,759,762,765,767,770,772,775,778,780,783,785,788,791,793,796,799,801,804,806,809,812,814,817,819,822,825,827,830,833,835,838,840,843,846,848,851,853,856,859,861,864,867,869,872,874,877,880,882,885,887,890,893,895,898,901,903,906,908,911,914,916,919,921,924,927,929,932,934,937,940,942,944,946,948,950,952,954,956,959,961,963,965,967,969,971,973,975,977,979,981,984,986,988,990,992,994,996,998,1000,1002,1004,1006,1009,1011,1013,1015,1017,1019,1021,1023,1025,1027,1029,1031,1033,1036,1038,1040,1042,1044,1046,1048,1050,1052,1054,1056,1058,1061,1063,1065,1067,1069,1071,1073,1075,1077,1079,1081,1083,1086,1088,1090,1092,1094,1096,1098,1100,1102,1104,1106,1108,1111,1113,1115,1117,1119,1121,1123,1125,1127,1129,1131,1133,1135,1138,1140,1142,1144,1146,1148,1150,1152,1154,1156,1158,1160,1163,1165,1167,1169,1171,1173,1175,1177,1179,1181,1183,1185,1190,1192,1196,1198,1202,1206,1208,1211,1213,1217,1219,1221,1225,1230,1233,1236,1239,1242,1245,1248,1251,1254,1257,1260,1263,1266,1269,1272,1275,1278,1281,1284,1287,1290,1293,1296,1299,1302,1305,1308,1311,1314,1317,1320,1322,1326,1329,1332,1338,1341,1347,1351,1355,1359,1363,1367,1371,1375,1379,1383,1387,1391,1395,1399,1403,1407,1411,1415,1419,1423,1427,1431,1435,1439};
    private static Integer horarioH[]={3,13,25,300,311,322,333,344,355,366,377,388,396,402,407,413,418,424,429,435,440,446,451,457,462,468,473,479,484,490,495,501,506,512,517,523,528,534,539,545,550,556,561,567,572,578,586,593,601,608,616,624,631,639,646,654,661,669,676,684,691,699,706,714,721,729,736,744,751,759,766,774,781,789,796,804,811,819,826,834,841,849,856,864,871,879,886,894,901,909,916,924,931,939,946,953,960,966,972,977,983,988,994,999,1005,1010,1016,1021,1027,1032,1038,1043,1049,1054,1060,1065,1071,1076,1082,1087,1093,1098,1104,1109,1115,1120,1126,1131,1137,1142,1148,1153,1159,1164,1170,1175,1181,1188,1196,1203,1211,1218,1226,1233,1241,1248,1256,1263,1271,1278,1286,1293,1301,1308,1316,1323,1331,1338,1346,1353,1361,1368,1376,1383,1391,1398,1406,1413,1421,1428,1436};
    private static Integer horarioGV[]={3,8,14,20,301,306,311,316,322,327,332,338,343,348,353,359,364,369,374,380,385,389,392,395,399,402,404,406,409,411,413,415,417,420,422,424,425,427,429,431,432,434,436,438,439,441,443,445,446,448,450,452,453,455,457,459,460,462,464,466,467,469,471,473,474,476,478,480,481,483,485,487,488,490,492,494,496,497,499,501,503,505,506,508,510,512,514,516,517,519,521,523,525,526,528,530,532,534,536,537,539,541,543,545,547,549,551,553,556,558,560,562,564,567,570,572,575,578,581,584,587,590,593,596,599,602,605,608,610,613,616,619,622,625,628,631,634,637,640,643,646,648,651,654,657,660,663,666,669,672,675,678,681,684,687,689,692,694,697,699,702,704,707,709,712,715,717,720,722,725,727,730,732,735,737,740,742,745,747,750,752,755,757,760,762,765,767,770,772,775,777,780,782,785,787,790,792,795,797,800,802,805,807,810,812,815,817,820,822,825,827,830,832,835,837,840,842,845,847,850,852,855,857,860,862,865,867,870,872,875,877,880,882,885,887,890,892,895,897,900,903,905,908,910,913,915,918,920,923,925,928,930,933,935,938,940,943,945,948,950,952,954,956,958,960,962,964,966,968,970,971,973,975,977,979,981,983,985,987,989,991,993,995,996,998,1000,1002,1004,1006,1008,1010,1011,1013,1015,1017,1019,1021,1023,1025,1026,1028,1030,1032,1034,1036,1038,1040,1042,1043,1045,1047,1049,1051,1053,1054,1056,1058,1060,1062,1063,1065,1067,1069,1071,1073,1074,1076,1078,1080,1082,1083,1085,1087,1089,1091,1092,1094,1096,1098,1100,1102,1103,1105,1107,1109,1111,1112,1114,1116,1118,1120,1122,1123,1125,1127,1129,1131,1132,1134,1136,1138,1140,1141,1143,1145,1147,1149,1151,1152,1154,1156,1158,1160,1161,1163,1165,1167,1169,1171,1172,1174,1176,1178,1180,1181,1183,1185,1187,1189,1191,1194,1197,1200,1204,1207,1210,1214,1217,1220,1223,1227,1230,1233,1237,1240,1245,1250,1254,1259,1264,1269,1273,1278,1283,1288,1293,1297,1302,1307,1312,1317,1321,1327,1332,1338,1343,1349,1354,1360,1365,1371,1376,1382,1387,1393,1398,1404,1409,1415,1420,1426,1431,1437};


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
            
            if(estacion1 != 5 && estacion1 != 6 && estacion1 != 25){
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
            
            if(estacion1 != 6 && estacion1 != 10 ){
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
                return tiemposD.get(estacionEfectiva)[0]; //ya estás subido al tren, va a salir a la hora
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
    static ArrayList<Integer> Aestrella(int inicio, int destino, int horaIni){

        Set<Estacion> listaAbierta = new HashSet<>();
        Set<Integer> listaCerrada = new HashSet<>();
        Map<Integer, Estacion> estacionAsociada = new HashMap<>();
        Iterator<Integer> it;
        
        listaAbierta.add(new Estacion(inicio, distHeur(inicio, destino), horaIni, -1, 0));
        estacionAsociada.put(inicio, new Estacion(inicio, distHeur(inicio, destino), horaIni, -1, 0));
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
                return recorrido(estacionAsociada, destino, posActual.getCostePorAhora());
            }

            listaCerrada.add(posActual.getPos());

            
            it = conexiones.get(posActual.getPos()).iterator(); 

            while(it.hasNext()) {
                int conexion = it.next();

                if (!listaCerrada.contains(conexion)) {

                    
                    int tiempoTardo = tiempoAdy(posActual.getPos(), conexion, posActual.getHoraLlego());
                    
                    int nuevoCoste = posActual.getCostePorAhora()+ tiempoTardo; 

                    if (!estacionAsociada.containsKey(conexion) || nuevoCoste < posActual.getCostePorAhora()) {
                        
                        int funcion = nuevoCoste + distHeur(conexion, destino)/420;  //Ojito 1 min -- 420 m 
                        int horaNueva = (posActual.getHoraLlego() + tiempoTardo )  % 1440; //evitar problemas tiempo
                        estacionAsociada.put(conexion, new Estacion(conexion, funcion, horaNueva, posActual.getPos(), nuevoCoste));
                        listaAbierta.add(new Estacion(conexion, funcion, horaNueva, posActual.getPos(), nuevoCoste));
                    }
                }

                
            }
        }

        // No se encontró un camino (no debería pasar nunca)
        return null;
    }


    //Genera el camino sabiendo las direcciones y las estaciones que están en el recorrido. La última posición del array no es una estación, es el coste (minutos)
    private static ArrayList<Integer> recorrido(Map<Integer, Estacion> estacionAsociada, int actual, int costeTotal) {
        ArrayList<Integer> caminoRe = new ArrayList<>();
        while (actual != -1) {
            caminoRe.add(actual);
            actual = estacionAsociada.get(actual).getOrigen();
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
        private int origen;
        private int costePorAhora;

        public Estacion(int posicion, int funcion, int horaLlego, int origen, int costePorAhora) {
            this.posicion = posicion;
            this.funcion = funcion;
            this.horaLlego = horaLlego;
            this.origen = origen;
            this.costePorAhora = costePorAhora;
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
        public int getOrigen() {
            return origen;
        }
        public void setOrigen(int nuevoOrigen) {
            this.origen = nuevoOrigen;
        }
        public int getCostePorAhora() {
            return costePorAhora;
        }
        public void setCostePorAhoran(int costePorAhora) {
            this.costePorAhora = costePorAhora;
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
       
            for(int i = 0; i<horarioP.length;i++){
            horarioPerrache.add(horarioP[i]);
            }
            for(int i = 0; i<horarioGO.length;i++){
            horarioGaredOullins.add(horarioGO[i]);
            }
            for(int i = 0; i<horarioH.length;i++){
            horarioHoteldeVilleLPradel.add(horarioH[i]);
            }

            for(int i = 0; i<horarioGV.length;i++){
            horarioGaredeVaise.add(horarioGV[i]);
            }
         
            
        /*try (BufferedReader br = new BufferedReader(new FileReader("./src/auxiliar/horario.txt"))) {
            
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
        }*/

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
    
    private static class MapaPanel extends JPanel {
        private int pantallaLargo = 1024;
        private int pantallaAncho = 1024;
        private ArrayList<Integer> trayecto;
        private Map<Integer, int[]> coordenadas;
        private Map<Integer, int[][]> conexiones;
        private String horaLlegada;
        private double ratio = 1;

        public MapaPanel(Dimension dimension){
            this.coordenadas = new HashMap<Integer, int[]>();
            this.conexiones = new HashMap<Integer, int[][]>();
            horaLlegada = "";

            this.setBackground(Color.white);
            this.setDoubleBuffered(true);
            this.ratio = dimension.getHeight()<dimension.getWidth() ? dimension.getHeight()/1024 : dimension.getWidth()/1024;
            pantallaAncho*=ratio;
            pantallaLargo*=ratio;
            this.setPreferredSize(new Dimension(pantallaLargo, pantallaAncho));
            
            inicializarcoordenadas();
        }

        public void setRuta (ArrayList<Integer> ruta) {
            trayecto = ruta;
        }

        private void inicializarcoordenadas() {
            //Estaciones con sus coordenadas en el JPanel
            coordenadas.put(1, new int[]{310, 45}); //Cuire
            coordenadas.put(2, new int[]{270, 130}); //Henon
            coordenadas.put(3, new int[]{300, 170}); //Croix-Rousse
            coordenadas.put(4, new int[]{330, 205}); //Croix Paquet
            coordenadas.put(5, new int[]{335, 250}); //Hotel de Ville
            coordenadas.put(6, new int[]{515, 220}); //Charpennes
            coordenadas.put(7, new int[]{505, 255}); //Brotteaux
            coordenadas.put(8, new int[]{485, 305}); //Gare Part-Dieu
            coordenadas.put(9, new int[]{420, 335}); //Place Guichard
            coordenadas.put(10, new int[]{415, 390}); //Saxe Gambetta
            coordenadas.put(11, new int[]{380, 480}); //Jean Mace
            coordenadas.put(12, new int[]{355, 545}); //Place Jean Jaures
            coordenadas.put(13, new int[]{315, 630}); //Debourg
            coordenadas.put(14, new int[]{295, 680}); //Stade de Gerland
            coordenadas.put(15, new int[]{180, 785}); //Gare d'Oullins
            coordenadas.put(16, new int[]{965, 305}); //Vaulx-en-Velin
            coordenadas.put(17, new int[]{860, 280}); //Laurent Bonnevay
            coordenadas.put(18, new int[]{790, 265}); //Cusset
            coordenadas.put(19, new int[]{720, 245}); //Flachet
            coordenadas.put(20, new int[]{655, 225}); //Gratte Ciel
            coordenadas.put(21, new int[]{605, 215}); //Republique Villeurbanne
            coordenadas.put(22, new int[]{465, 225}); //Massena
            coordenadas.put(23, new int[]{405, 235}); //Foch
            coordenadas.put(24, new int[]{335, 290}); //Cordeliers
            coordenadas.put(25, new int[]{310, 355}); //Bellecour
            coordenadas.put(26, new int[]{280, 400}); //Ampere-Victor Hugo
            coordenadas.put(27, new int[]{265, 440}); //Perrache
            coordenadas.put(28, new int[]{715, 885}); //Gare de Venissieux
            coordenadas.put(29, new int[]{710, 735}); //Parilly
            coordenadas.put(30, new int[]{705, 650}); //Mermoz-Pinel
            coordenadas.put(31, new int[]{690, 540}); //Laennec
            coordenadas.put(32, new int[]{650, 510}); //Grange Blanche
            coordenadas.put(33, new int[]{590, 480}); //Monplaisir Lumiere
            coordenadas.put(34, new int[]{545, 455}); //Sans Souci
            coordenadas.put(35, new int[]{475, 420}); //Garibaldi
            coordenadas.put(36, new int[]{385, 375}); //Guillotiere Gabriel Peri
            coordenadas.put(37, new int[]{260, 320}); //Vieux Lyon
            coordenadas.put(38, new int[]{110, 255}); //Gorge de Loup
            coordenadas.put(39, new int[]{110, 170}); //Valmy
            coordenadas.put(40, new int[]{105, 110}); //Gare de Vaise

            //Lineas que crean conexiones entre los puntos (paradas)
            conexiones.put(1, new int[][]{{coordenadas.get(1)[0]+10,coordenadas.get(1)[1]+10}, {coordenadas.get(2)[0]+10,coordenadas.get(2)[1]+10}});
            conexiones.put(2, new int[][]{{coordenadas.get(2)[0]+10,coordenadas.get(2)[1]+10}, {coordenadas.get(3)[0]+10,coordenadas.get(3)[1]+10}});
            conexiones.put(3, new int[][]{{coordenadas.get(3)[0]+10,coordenadas.get(3)[1]+10}, {coordenadas.get(4)[0]+10,coordenadas.get(4)[1]+10}});
            conexiones.put(4, new int[][]{{coordenadas.get(4)[0]+10,coordenadas.get(4)[1]+10}, {coordenadas.get(5)[0]+10,coordenadas.get(5)[1]+10}});
            conexiones.put(5, new int[][]{{coordenadas.get(6)[0]+10,coordenadas.get(6)[1]+10}, {coordenadas.get(7)[0]+10,coordenadas.get(7)[1]+10}});
            conexiones.put(6, new int[][]{{coordenadas.get(7)[0]+10,coordenadas.get(7)[1]+10}, {coordenadas.get(8)[0]+10,coordenadas.get(8)[1]+10}});
            conexiones.put(7, new int[][]{{coordenadas.get(8)[0]+10,coordenadas.get(8)[1]+10}, {coordenadas.get(9)[0]+10,coordenadas.get(9)[1]+10}});
            conexiones.put(8, new int[][]{{coordenadas.get(9)[0]+10,coordenadas.get(9)[1]+10}, {coordenadas.get(10)[0]+10,coordenadas.get(10)[1]+10}});
            conexiones.put(9, new int[][]{{coordenadas.get(10)[0]+10,coordenadas.get(10)[1]+10}, {coordenadas.get(11)[0]+10,coordenadas.get(11)[1]+10}});
            conexiones.put(10, new int[][]{{coordenadas.get(11)[0]+10,coordenadas.get(11)[1]+10}, {coordenadas.get(12)[0]+10,coordenadas.get(12)[1]+10}});
            conexiones.put(11, new int[][]{{coordenadas.get(12)[0]+10,coordenadas.get(12)[1]+10}, {coordenadas.get(13)[0]+10,coordenadas.get(13)[1]+10}});
            conexiones.put(12, new int[][]{{coordenadas.get(13)[0]+10,coordenadas.get(13)[1]+10}, {coordenadas.get(14)[0]+10,coordenadas.get(14)[1]+10}});
            conexiones.put(13, new int[][]{{coordenadas.get(14)[0]+10,coordenadas.get(14)[1]+10}, {coordenadas.get(15)[0]+10,coordenadas.get(15)[1]+10}});
            conexiones.put(14, new int[][]{{coordenadas.get(16)[0]+10,coordenadas.get(16)[1]+10}, {coordenadas.get(17)[0]+10,coordenadas.get(17)[1]+10}});
            conexiones.put(15, new int[][]{{coordenadas.get(17)[0]+10,coordenadas.get(17)[1]+10}, {coordenadas.get(18)[0]+10,coordenadas.get(18)[1]+10}});
            conexiones.put(16, new int[][]{{coordenadas.get(18)[0]+10,coordenadas.get(18)[1]+10}, {coordenadas.get(19)[0]+10,coordenadas.get(19)[1]+10}});
            conexiones.put(17, new int[][]{{coordenadas.get(19)[0]+10,coordenadas.get(19)[1]+10}, {coordenadas.get(20)[0]+10,coordenadas.get(20)[1]+10}});
            conexiones.put(18, new int[][]{{coordenadas.get(20)[0]+10,coordenadas.get(20)[1]+10}, {coordenadas.get(21)[0]+10,coordenadas.get(21)[1]+10}});
            conexiones.put(19, new int[][]{{coordenadas.get(21)[0]+10,coordenadas.get(21)[1]+10}, {coordenadas.get(6)[0]+10,coordenadas.get(6)[1]+10}});
            conexiones.put(20, new int[][]{{coordenadas.get(6)[0]+10,coordenadas.get(6)[1]+10}, {coordenadas.get(22)[0]+10,coordenadas.get(22)[1]+10}});
            conexiones.put(21, new int[][]{{coordenadas.get(22)[0]+10,coordenadas.get(22)[1]+10}, {coordenadas.get(23)[0]+10,coordenadas.get(23)[1]+10}});
            conexiones.put(22, new int[][]{{coordenadas.get(23)[0]+10,coordenadas.get(23)[1]+10}, {coordenadas.get(5)[0]+10,coordenadas.get(5)[1]+10}});
            conexiones.put(23, new int[][]{{coordenadas.get(5)[0]+10,coordenadas.get(5)[1]+10}, {coordenadas.get(24)[0]+10,coordenadas.get(24)[1]+10}});
            conexiones.put(24, new int[][]{{coordenadas.get(24)[0]+10,coordenadas.get(24)[1]+10}, {coordenadas.get(25)[0]+10,coordenadas.get(25)[1]+10}});
            conexiones.put(25, new int[][]{{coordenadas.get(25)[0]+10,coordenadas.get(25)[1]+10}, {coordenadas.get(26)[0]+10,coordenadas.get(26)[1]+10}});
            conexiones.put(26, new int[][]{{coordenadas.get(26)[0]+10,coordenadas.get(26)[1]+10}, {coordenadas.get(27)[0]+10,coordenadas.get(27)[1]+10}});
            conexiones.put(27, new int[][]{{coordenadas.get(28)[0]+10,coordenadas.get(28)[1]+10}, {coordenadas.get(29)[0]+10,coordenadas.get(29)[1]+10}});
            conexiones.put(28, new int[][]{{coordenadas.get(29)[0]+10,coordenadas.get(29)[1]+10}, {coordenadas.get(30)[0]+10,coordenadas.get(30)[1]+10}});
            conexiones.put(29, new int[][]{{coordenadas.get(30)[0]+10,coordenadas.get(30)[1]+10}, {coordenadas.get(31)[0]+10,coordenadas.get(31)[1]+10}});
            conexiones.put(30, new int[][]{{coordenadas.get(31)[0]+10,coordenadas.get(31)[1]+10}, {coordenadas.get(32)[0]+10,coordenadas.get(32)[1]+10}});
            conexiones.put(31, new int[][]{{coordenadas.get(32)[0]+10,coordenadas.get(32)[1]+10}, {coordenadas.get(33)[0]+10,coordenadas.get(33)[1]+10}});
            conexiones.put(32, new int[][]{{coordenadas.get(33)[0]+10,coordenadas.get(33)[1]+10}, {coordenadas.get(34)[0]+10,coordenadas.get(34)[1]+10}});
            conexiones.put(33, new int[][]{{coordenadas.get(34)[0]+10,coordenadas.get(34)[1]+10}, {coordenadas.get(35)[0]+10,coordenadas.get(35)[1]+10}});
            conexiones.put(34, new int[][]{{coordenadas.get(35)[0]+10,coordenadas.get(35)[1]+10}, {coordenadas.get(10)[0]+10,coordenadas.get(10)[1]+10}});
            conexiones.put(35, new int[][]{{coordenadas.get(10)[0]+10,coordenadas.get(10)[1]+10}, {coordenadas.get(36)[0]+10,coordenadas.get(36)[1]+10}});
            conexiones.put(36, new int[][]{{coordenadas.get(36)[0]+10,coordenadas.get(36)[1]+10}, {coordenadas.get(25)[0]+10,coordenadas.get(25)[1]+10}});
            conexiones.put(37, new int[][]{{coordenadas.get(25)[0]+10,coordenadas.get(25)[1]+10}, {coordenadas.get(37)[0]+10,coordenadas.get(37)[1]+10}});
            conexiones.put(38, new int[][]{{coordenadas.get(37)[0]+10,coordenadas.get(37)[1]+10}, {coordenadas.get(38)[0]+10,coordenadas.get(38)[1]+10}});
            conexiones.put(39, new int[][]{{coordenadas.get(38)[0]+10,coordenadas.get(38)[1]+10}, {coordenadas.get(39)[0]+10,coordenadas.get(39)[1]+10}});
            conexiones.put(40, new int[][]{{coordenadas.get(39)[0]+10,coordenadas.get(39)[1]+10}, {coordenadas.get(40)[0]+10,coordenadas.get(40)[1]+10}});
            }

        private void creacionMapa(Graphics2D g) {
            //Funcion que dibuja el mapa
            g.setColor(new Color(240,147,0));
            g.setStroke(new BasicStroke(7)); //Agranda las lineas que se dibujan
            for (int i = 1; i < 5; i++) {
                g.drawLine(ratioConv(conexiones.get(i)[0][0]), ratioConv(conexiones.get(i)[0][1]), ratioConv(conexiones.get(i)[1][0]), ratioConv(conexiones.get(i)[1][1]));
            }
            g.setColor(new Color(0, 148, 215));
            for (int i = 5; i < 14; i++) {
                g.drawLine(ratioConv(conexiones.get(i)[0][0]), ratioConv(conexiones.get(i)[0][1]), ratioConv(conexiones.get(i)[1][0]), ratioConv(conexiones.get(i)[1][1]));
            }
            g.setColor(new Color(224,0,26));
            for (int i = 14; i < 27; i++) {
                g.drawLine(ratioConv(conexiones.get(i)[0][0]), ratioConv(conexiones.get(i)[0][1]), ratioConv(conexiones.get(i)[1][0]), ratioConv(conexiones.get(i)[1][1]));
            }
            g.setColor(new Color(0,143,54));
            for (int i = 27; i < 41; i++) {
                g.drawLine(ratioConv(conexiones.get(i)[0][0]), ratioConv(conexiones.get(i)[0][1]), ratioConv(conexiones.get(i)[1][0]), ratioConv(conexiones.get(i)[1][1]));
            }
            g.setColor(Color.white);
            g.setStroke(new BasicStroke(2)); //Agranda las lineas que se dibujan
            for (int i = 1; i <= coordenadas.size(); i++){
                g.fillOval(ratioConv(coordenadas.get(i)[0]), ratioConv(coordenadas.get(i)[1]), ratioConv(20), ratioConv(20));
            }        
            g.setColor(Color.black); //Color de lo que se quiere dibujar
            g.setStroke(new BasicStroke(2)); //Agranda las lineas que se dibujan
            for (int i = 1; i <= coordenadas.size(); i++){
                g.drawOval(ratioConv(coordenadas.get(i)[0]), ratioConv(coordenadas.get(i)[1]), ratioConv(20), ratioConv(20));
            }
        }

        private void registrarParadas(Graphics2D g) {
            /* Funcion que registra las paradas con colores.
            ** En verde registra el inicio, en cyan las intermedias y
            ** en rojo se registra la parada destino.
            */
            g.setColor(Color.green);
            g.fillOval(ratioConv(coordenadas.get(trayecto.get(0))[0]+1), ratioConv(coordenadas.get(trayecto.get(0))[1]+1), ratioConv(19), ratioConv(19));
            g.setColor(Color.CYAN);
            for (int i = 1; i < trayecto.size()-2; i++) {
                g.fillOval(ratioConv(coordenadas.get(trayecto.get(i))[0]+1), ratioConv(coordenadas.get(trayecto.get(i))[1]+1), ratioConv(19), ratioConv(19));
            }
            g.setColor(Color.red);
            g.fillOval(ratioConv(coordenadas.get(trayecto.get(trayecto.size()-2))[0]+1), ratioConv(coordenadas.get(trayecto.get(trayecto.size()-2))[1]+1), ratioConv(19), ratioConv(19));
        }

        public void setHoraLlegada(int hora) {
            int horaEstacion = hora + trayecto.get(trayecto.size()-1);
            if ((horaEstacion%60) < 10)
                horaLlegada = horaEstacion/60 + ":0" + horaEstacion%60;
            else
                horaLlegada = horaEstacion/60 + ":" + horaEstacion%60;
        }

        @Override
        public void paintComponent(Graphics g) {
            //Codigo a mejorar
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            creacionMapa(g2);
            if (trayecto != null) {
        	registrarParadas(g2);
                g.setColor(Color.black);
                g.setFont(g.getFont().deriveFont(30.0f * Float.parseFloat(String.valueOf(this.ratio))));
                g.drawString("Hora de llegada: " + horaLlegada, ratioConv(710), ratioConv(30));
	    	g.drawString("Origen", ratioConv(125), ratioConv(970));
                g.drawString("Parada intermedia", ratioConv(265), ratioConv(970));
                g.drawString("Destino", ratioConv(575), ratioConv(970));
                
                g.setColor(Color.green);
                g.fillOval(ratioConv(100), ratioConv(950), ratioConv(19), ratioConv(19));
                g.setColor(Color.CYAN);
                g.fillOval(ratioConv(240), ratioConv(950), ratioConv(19), ratioConv(19));
                g.setColor(Color.red);
                g.fillOval(ratioConv(550), ratioConv(950), ratioConv(19), ratioConv(19));
            }
        }
        
        public void setDimension(Dimension dimension){
            this.ratio = dimension.getHeight()<dimension.getWidth() ? dimension.getHeight()/1024 : dimension.getWidth()/1024;
            pantallaAncho*=ratio;
            pantallaLargo*=ratio;
            this.setPreferredSize(new Dimension(pantallaLargo, pantallaAncho));
        }
        
        public int ratioConv(int valor){
            return (int) Math.round((valor * this.ratio));
        }
    }
    
    private static class Pantalla extends JFrame {
    
	
        private final String title;
        private Toolkit myToolkit;
        private final int width;
        private final int height;
        private int x;
        private int y;

        //Parametro busqueda
        private int hora;

        //Combos y paneles para origen y destino
        private static JComboBox<String> comboLineaOrigen;
        private static JComboBox<String> comboLineaDestino;
        private static JComboBox<String> comboEstacionesOrigen;
        private static JComboBox<String> comboEstacionesDestino;

        private JPanel panelOrigen = new JPanel(new BorderLayout(10,10));
        private JPanel panelSeleccionOrigen = new JPanel();
        private JPanel panelDestino = new JPanel(new BorderLayout(10,10));
        private JPanel panelSeleccionDestino = new JPanel();

        private MapaPanel mapaPanel;

        //Combos y panel para hora
        private JComboBox<Integer> comboHora;
        private JComboBox<Integer> comboMinuto;

        private JPanel panelHora = new JPanel(new BorderLayout(10,10));
        private JPanel panelSeleccionHora = new JPanel();

        private JButton buscar;

        int codOrigen = -1;
        int codDestino = -1;
        

        ArrayList<Integer> trayecto = new ArrayList<>();

        public Pantalla(String title){
            this.myToolkit = Toolkit.getDefaultToolkit();
            this.title = title;

            /*
             * se busca relacion 2:3 entre ancho y alto para dividir horizontalmente
             * en 2 partes con la de la derecha el doble de ancho que la de la izquierda.
             * La parte izquierda para meter parametros y la derecha para el mapa.
             * Con la relacion 2:3 la parte derecha es cuadrada.
             */

            this.width = myToolkit.getScreenSize().height*9/8;
            this.height = myToolkit.getScreenSize().height*3/4;

            this.x = (myToolkit.getScreenSize().width-this.width)/2;
            this.y = (myToolkit.getScreenSize().height-this.height)/2;
            
            this.mapaPanel = new MapaPanel(new Dimension(this.width/2,this.height));
                     
            this.comboLineaOrigen = new JComboBox(new String[]{" ","A","B","C","D"});
            this.comboLineaDestino = new JComboBox(new String[]{" ","A","B","C","D"});
            comboEstacionesOrigen = new JComboBox(new String[]{"Seleccione una linea"});
            comboEstacionesDestino = new JComboBox(new String[]{"Seleccione una linea"});

            comboLineaOrigen.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    loadCombo(comboEstacionesOrigen, comboLineaOrigen.getSelectedIndex());
                }
            });
            comboLineaDestino.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    loadCombo(comboEstacionesDestino, comboLineaDestino.getSelectedIndex());
                }
            });

            comboHora = new JComboBox<>();
            comboMinuto = new JComboBox<>();       
            IntStream enterosHora = IntStream.range(0,24);
            IntStream enterosMinuto = IntStream.range(0,60);        
            enterosHora.forEachOrdered(num -> comboHora.addItem(num));
            enterosMinuto.forEachOrdered(num -> comboMinuto.addItem(num));
            comboHora.setEditable(true);
            comboMinuto.setEditable(true);        

            buscar = new JButton("Buscar");
            buscar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String seleccionOrigen = (String) comboEstacionesOrigen.getSelectedItem();
                    String seleccionDestino = (String) comboEstacionesDestino.getSelectedItem();
                    if(seleccionOrigen.equals("Seleccione una linea")||seleccionDestino.equals("Seleccione una linea")){
                        JOptionPane.showMessageDialog(null, "Seleccione una estacion valida");
                    }else if(seleccionOrigen.equals(seleccionDestino)){
                        JOptionPane.showMessageDialog(null, "El origen y destino son el mismo");
                    }else{
                        boolean horaValida = false;
                        try{
                            int selHora = Integer.parseInt(String.valueOf(comboHora.getSelectedItem()));
                            int selMinuto = Integer.parseInt(String.valueOf(comboMinuto.getSelectedItem()));
                            if(selHora <0 || selHora > 23 || selMinuto <0 || selMinuto > 59)
                                JOptionPane.showMessageDialog(null, "Seleccione una hora valida");
                            else horaValida = true;
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Seleccione una hora valida");
                            return;
                        }
                        if(horaValida){
                            hora = 60*((int)comboHora.getSelectedItem())+((int)comboMinuto.getSelectedItem());
                            codEstacion.forEach((cod,estacion)->{
                                    if(codOrigen==-1||seleccionOrigen.equals(estacion))
                                        codOrigen = cod;
                                    else if(codDestino==-1||seleccionDestino.equals(estacion))
                                        codDestino = cod;
                                        });
                            trayecto = Aestrella(codOrigen, codDestino, hora);
                            mapaPanel.setRuta(trayecto);
                            mapaPanel.setHoraLlegada(hora);
                            mapaPanel.repaint();
                        }
                    }

                }
            });
        }

        public static void loadCombo(JComboBox<String> combo, int linea){

            combo.removeAllItems();
            switch(linea){
                case 0: //blank
                    combo.addItem("Seleccione una linea");
                    break;
                case 1: //linea A
                    estacionesLineaA.forEach(idEstacion -> {
                        combo.addItem(codEstacion.get(idEstacion));
                    });
                    break;
                case 2: //linea B
                    estacionesLineaB.forEach(idEstacion -> {
                        combo.addItem(codEstacion.get(idEstacion));
                    });
                    break;
                case 3://linea C
                    estacionesLineaC.forEach(idEstacion -> {
                        combo.addItem(codEstacion.get(idEstacion));
                    });
                    break;
                case 4: //linea D
                    estacionesLineaD.forEach(idEstacion -> {
                        combo.addItem(codEstacion.get(idEstacion));
                    });
                    break;
            }
        }

        public void inicializar(){
            this.setBounds(this.x,this.y,this.width,this.height);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setResizable(true);
            //this.setExtended...
            this.setTitle(title);
            this.getContentPane().setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();


            //Layout origen, destino y hora
            //Origen
            JLabel lineaLabelOrigen = new JLabel("Linea: ");
            JLabel estacionLabelOrigen = new JLabel("Estacion: ");

            GroupLayout layoutOrigen = new GroupLayout(panelSeleccionOrigen);
            layoutOrigen.setAutoCreateContainerGaps(true);
            layoutOrigen.setAutoCreateGaps(true);

            layoutOrigen.setHorizontalGroup(
                layoutOrigen.createSequentialGroup()
                    .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lineaLabelOrigen)
                        .addComponent(comboLineaOrigen)).addGap(20)
                    .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(estacionLabelOrigen)
                        .addComponent(comboEstacionesOrigen))
            );

            layoutOrigen.setVerticalGroup(
                layoutOrigen.createSequentialGroup()
                    .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                             .addComponent(lineaLabelOrigen)
                             .addComponent(estacionLabelOrigen))
                    .addGroup(layoutOrigen.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(comboLineaOrigen)
                            .addComponent(comboEstacionesOrigen))
            );
            panelSeleccionOrigen.setLayout(layoutOrigen);

            //Destino
            JLabel lineaLabelDestino = new JLabel("Linea: ");
            JLabel estacionLabelDestino = new JLabel("Estacion: ");

            GroupLayout layoutDestino = new GroupLayout(panelSeleccionDestino);
            layoutDestino.setAutoCreateContainerGaps(true);
            layoutDestino.setAutoCreateGaps(true);

            layoutDestino.setHorizontalGroup(
                layoutDestino.createSequentialGroup()
                    .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lineaLabelDestino)
                        .addComponent(comboLineaDestino)).addGap(20)
                    .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(estacionLabelDestino)
                        .addComponent(comboEstacionesDestino))
            );

            layoutDestino.setVerticalGroup(
                layoutDestino.createSequentialGroup()
                    .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                             .addComponent(lineaLabelDestino)
                             .addComponent(estacionLabelDestino))
                    .addGroup(layoutDestino.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(comboLineaDestino)
                            .addComponent(comboEstacionesDestino))
            );
            panelSeleccionDestino.setLayout(layoutDestino);

            //Hora      
            GroupLayout layoutHora = new GroupLayout(panelSeleccionHora);
            layoutHora.setAutoCreateContainerGaps(true);
            layoutHora.setAutoCreateGaps(true);
            JLabel separador = new JLabel(":");
            layoutHora.setHorizontalGroup(
                layoutHora.createSequentialGroup()
                        .addComponent(comboHora).addGap(10)
                        .addComponent(separador).addGap(10)
                        .addComponent(comboMinuto)
            );

            layoutHora.setVerticalGroup(
                layoutHora.createSequentialGroup()
                    .addGroup(layoutHora.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(comboHora).addGap(10)
                            .addComponent(separador).addGap(10)
                            .addComponent(comboMinuto))
            );
            panelSeleccionHora.setLayout(layoutHora);

            /*
             *  Layout del Frame principal
             */
            
            //El panel mapa empieza en 1,0 y ocupa 4 filas y 3 columnas
            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 3;
            constraints.gridheight = 4;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.weighty = 1.0;
            constraints.weightx = 1.0;
            constraints.insets = new Insets(10,10,10,20);
            this.getContentPane().add(mapaPanel,constraints);

            //Panel origen

            JLabel origenLabel = new JLabel("Origen");
            origenLabel.setVerticalAlignment(SwingConstants.CENTER);
            origenLabel.setHorizontalAlignment(SwingConstants.CENTER);
            origenLabel.setFont(new Font("Dialog",Font.BOLD,18));

            panelOrigen.add(origenLabel,BorderLayout.PAGE_START);
            panelOrigen.add(panelSeleccionOrigen,BorderLayout.CENTER);
            Border bordeOrigen = BorderFactory.createLineBorder(Color.black);
            panelOrigen.setBorder(bordeOrigen);
            //El panel origen empieza en 0,0 y ocupa una fila y columna
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 1.0; //1
            constraints.weightx = 0; //0.5
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.fill = GridBagConstraints.NONE;
            constraints.insets = new Insets(10,10,10,10);
            this.getContentPane().add(panelOrigen,constraints);

            //Panel Destino

            JLabel destinoLabel = new JLabel("Destino");
            destinoLabel.setVerticalAlignment(SwingConstants.CENTER);
            destinoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            destinoLabel.setFont(new Font("Dialog",Font.BOLD,18));

            panelDestino.add(destinoLabel,BorderLayout.PAGE_START);
            panelDestino.add(panelSeleccionDestino,BorderLayout.CENTER);
            Border bordeDestino = BorderFactory.createLineBorder(Color.black);
            panelHora.setBorder(bordeDestino);
            //El panel destino empieza en 0,1 y ocupa una fila y columna
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            this.getContentPane().add(panelDestino,constraints);

            //Panel hora

            JLabel horaLabel = new JLabel("Hora: (hh:mm)");
            horaLabel.setVerticalAlignment(SwingConstants.CENTER);
            horaLabel.setHorizontalAlignment(SwingConstants.CENTER);
            horaLabel.setFont(new Font("Dialog",Font.BOLD,18));

            panelHora.add(horaLabel,BorderLayout.PAGE_START);
            panelHora.add(panelSeleccionHora,BorderLayout.CENTER);
            Border bordeHora = BorderFactory.createLineBorder(Color.black);
            panelDestino.setBorder(bordeHora);
            //El panel otherParam empieza en 0,2 y ocupa una fila y columna
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            this.getContentPane().add(panelHora,constraints);

            //Panel buscar

            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 0;
            constraints.weighty = 0;
            this.getContentPane().add(buscar,constraints);

            this.addComponentListener(new ComponentAdapter() {
                boolean timerIsRunning = false;
                Timer myTimer;
                @Override
                public void componentResized(ComponentEvent e) {
                    //System.out.println("enter " + timerIsRunning);
                    if(!timerIsRunning){
                        timerIsRunning = true;
                        myTimer = new Timer(20,new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae){
                                mapaPanel.setDimension(new Dimension((int) mapaPanel.getSize().getWidth(), (int) mapaPanel.getSize().getHeight()));   
                                mapaPanel.repaint();
                                myTimer.stop();
                                timerIsRunning = false;
                            }
                        });
                        myTimer.start();
                    }
                }
              });
        }
    }
    
    

    public static void main(String[] args) {
        inicializa();
        Pantalla ventana = new Pantalla("Buscador metro Lyon");
        ventana.inicializar();
        ventana.pack();
        
        ventana.setVisible(true);
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
        
        //System.out.println(Aestrella(4,3,23*60+59));

        */
    }
}
