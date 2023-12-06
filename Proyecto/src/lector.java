
import java.io.*;
import java.util.ArrayList;


public class lector {
    
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("proyecto/src/auxiliar/horario.txt"));

		ArrayList<Integer> hola= new ArrayList<Integer>();
		String line=br.readLine();
		
		String horario[]=line.split(";");
		for(int i = 0; i<horario.length;i++){
		hola.add(Integer.parseInt(horario[i]));
		}
		
		System.out.println();
		}
		
		
		
		
	
}

