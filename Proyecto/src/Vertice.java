package practicaia;

import java.io.IOException;
import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Vertice {
    private Integer id;
    private String shortName;
    private String longName;
    private Integer coordenadaX;
    private Integer coordenadaY;
    private String svg_type; //circle
    private String svg_r; //radio
    private String svg_fill;
    private String svg_stroke;
    private String svg_stroke_width;
    private String dPath; //if svg creator should use this path instead of default.

    public Vertice(Integer id,String shortName,String longName,Integer coordenadaX,Integer coordenadaY){
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.svg_type = "circle";
        this.svg_r = "7";
        this.svg_fill = "white";
        this.svg_stroke = "#3B393A";
        this.svg_stroke_width = "2";
    }

    public Vertice(Integer id,String shortName,String longName,Integer coordenadaX,Integer coordenadaY,String dPath){
        this(id, shortName, longName, coordenadaX, coordenadaY);
        this.dPath = dPath;
    }

    public String toString(){
        return this.id + "-" 
             + this.shortName + "-"  
             + this.longName + "-" 
             + this.coordenadaX + "-" 
             + this.coordenadaY + "-"
             + this.svg_type + "-"
             + this.svg_r + "-"
             + this.svg_fill + "-"
             + this.svg_stroke + "-"
             + this.svg_stroke_width;
    }

    //getters
    public Integer getId(){
        return this.id;
    }
    public Integer getCoordenadaX(){
        return this.coordenadaX;
    }
    public Integer getCoordenadaY(){
        return this.coordenadaY;
    }
    public void pintaVerticeSVG(SvgMap mapa){
        /*white circle + text*/
        mapa.addLinea("");
        mapa.addLinea("           <" + this.svg_type + " cx=\"" + this.coordenadaX + "\" cy=\"" + this.coordenadaY + "\" r=\"" + this.svg_r + "\"");
        mapa.addLinea("               fill = \"" + this.svg_fill + " \"");
        mapa.addLinea("               stroke = \"" + this.svg_stroke + " \"");
        mapa.addLinea("               stroke-width = \"" + this.svg_stroke_width + "\"/>");
    }
}
