package lector;
import java.io.*;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
public class lector {
    //ARCHIVO USADO PARA CREAR HORARIO.TXT
	public static void main(String[] args) throws InvalidFormatException, IOException {
		File file = new File ("C:\\Users\\palom\\Downloads\\Lineas Francia.xlsx");
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0);
		ArrayList<Integer> hola= new ArrayList<Integer>();
		int aux=0;
		for(int i=0;i<24;i++) {
			for(int j=1;j<22;j++) {
				aux=(int) sheet.getRow(j).getCell(i).getNumericCellValue();
				if(aux==0&&j!=1||(i==1||i==2||i==3||i==4)) {
					break;
				}else {
					aux=aux+i*60;
					System.out.print(aux+";");
				}
				
			}
		}
		System.out.println("");
		sheet = wb.getSheetAt(1);
		for(int i=0;i<24;i++) {
			for(int j=1;j<31;j++) {
				aux=(int) sheet.getRow(j).getCell(i).getNumericCellValue();
				if(aux==0&&j!=1||(i==1||i==2||i==3)) {
					break;
				}else {
					aux=aux+i*60;
					System.out.print(+aux+";");
				}
				
			}
		}
		System.out.println("");
		sheet = wb.getSheetAt(2);
		for(int i=0;i<24;i++) {
			for(int j=1;j<13;j++) {
				aux=(int) sheet.getRow(j).getCell(i).getNumericCellValue();
				if(aux==0&&j!=1||(i==1||i==2||i==3||i==4)) {
					break;
				}else {
					aux=aux+i*60;
					System.out.print(+aux+";");
				}
				
			}
		}
		System.out.println("");
		sheet = wb.getSheetAt(3);
		for(int i=0;i<24;i++) {
			for(int j=1;j<36;j++) {
				aux=(int) sheet.getRow(j).getCell(i).getNumericCellValue();
				if(aux==0&&j!=1||(i==1||i==2||i==3||i==4)) {
					break;
				}else {
					aux=aux+i*60;
					System.out.print(+aux+";");
				}
				
			}
		}
		
		
		
		
	}
}

