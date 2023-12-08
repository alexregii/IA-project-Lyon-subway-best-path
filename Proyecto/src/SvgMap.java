package practicaia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SvgMap {
    private Integer id;
    private Integer origen;
    private Integer destino; 
    private String titulo;
    private Integer viewBox_X;
    private Integer viewBox_Y;
    private ArrayList<String> lineas;

    public SvgMap (Integer argId,Integer argOrigen,Integer argDestino,String argTitulo){
        this.id = argId;
        this.origen = argOrigen;
        this.destino = argDestino;
        this.titulo = argTitulo;
        this.viewBox_X = 911;
        this.viewBox_Y = 900;
        this.lineas = new ArrayList<String>();

        this.addCabecera();
    }

    public ArrayList<String> getLines(){
        return this.lineas;
    }
    public void addLinea(String linea){
        lineas.add(linea);
    }
    public void addCabecera(){
        this.addLinea("<svg xmlns=\"http://www.w3.org/2000/svg\"");
        this.addLinea("     fill=\"none\" viewBox=\"0 30 " + this.viewBox_X + " " + this.viewBox_Y + "\">");
    }

    public void cerrarSvg(){
        this.addLinea("</svg>");
    }
    public void limpiar(){
        this.lineas.clear();
    }
    public void print(){
        this.lineas.forEach(System.out::println);
    }
    
    public void createFile() throws IOException{
        String fileLocation = System.getProperty("user.dir").concat(String.valueOf(File.separatorChar).concat("svgMapa"));
        File nuevo = new File(fileLocation);
        try{
            nuevo.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(nuevo));
            Iterator<String> it = this.lineas.iterator();
            while(it.hasNext()){
                    bw.write(it.next());
                    bw.newLine();
            }
            bw.close();
        }catch(IOException e){
            System.err.println("Error al crear el mapa");
        }
        
    }

    public void crearFichero(String destino){
        //code
    }

}
