package practicaia;

import java.io.IOException;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;

public class Ruta {
    private String id;
    private String nombre;
    private String svg_stroke; //stroke
    private String svg_stroke_linecap;
	private String svg_stroke_linejoin;
	private String svg_stroke_width;
    private String svg_stroke_dasharray;
    //private String svg_fill; // en previsión.
    private ArrayList<Integer> recorrido;
    private Vertices vertices;

    public Ruta(String id,String name,String stroke,String svg_stroke_width,ArrayList<String> recorrido,Vertices vertices){
        this.id = id;
        this.nombre = id;
        this.svg_stroke = stroke;
        this.svg_stroke_linecap ="round";
        this.svg_stroke_linejoin = "round";
        this.svg_stroke_width = svg_stroke_width;
        //this.recorrido = new ArrayList(Arrays.asList(recorrido));
        try {
            this.recorrido = recorrido.stream()
                        .map(el -> Integer.parseInt(el))
                        .collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.vertices = vertices;
        //this.svg_stroke_dasharray = "1 5 3"; reservado para los recorridos  qe no sean lineas
    }

    public String toString(){
        return this.id + "-" 
             + this.nombre + "-"  
             + this.svg_stroke + "-"
             + this.svg_stroke_linecap + "-"
             + this.svg_stroke_linejoin + "-"
             + this.svg_stroke_width + "-"
             + this.recorrido;
    }

    //getters
    public String getId(){
        return this.id;
    }

    public void pintaRutaSVG(SvgMap mapa){
        Vertice vOrg = this.vertices.buscarPorId(recorrido.get(0));
        for (int i=1;i<this.recorrido.size();i++) {
            Vertice vDest = this.vertices.buscarPorId(recorrido.get(i));
            mapa.addLinea("");
            mapa.addLinea("           <path stroke=\"" + this.svg_stroke + "\"");
            mapa.addLinea("               stroke-linecap = \"" + this.svg_stroke_linecap + " \"");
            mapa.addLinea("               stroke-lineajoin = \"" + this.svg_stroke_linejoin + " \"");
            mapa.addLinea("               stroke-width = \"" + this.svg_stroke_width + " \"");
            mapa.addLinea("               d = \"M" + vOrg.getCoordenadaX() + " " + vOrg.getCoordenadaY() + " L" + vDest.getCoordenadaX() + " " + vDest.getCoordenadaY() + "\"/>");
            vOrg = vDest;
        };
    }
}
