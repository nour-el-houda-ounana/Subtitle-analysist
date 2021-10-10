package ounana.projetVideo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Utils {
	

	
	
	/*
	 * Méthode qui à partir du temps de départ et durée returns le point d'arret
	 * dans le fichier
	 * 
	 * @Params : String représentant le temps de départ de la forme hh:mm:ss,ms
	 * 
	 * @Params : String représentant la durée de la forme hh:mm:ss,ms
	 * 
	 */
	public static String pointArret(String tmps, String d){
		long stopMS = convertirMs(tmps) + convertirMs(d);

		long millis = stopMS % 1000;
		long second = (stopMS / 1000) % 60;
		long minute = (stopMS / (1000 * 60)) % 60;
		long hour = (stopMS / (1000 * 60 * 60)) % 24;
		
		
		String stop = String.format("%02d:%02d:%02d,%03d", hour, minute, second, millis);
		return stop;
	}
	
	/*
	 * Méthode qui convertit le temps donneé en paramètre en secondes
	 * 
	 * @Params : String représentant le temps de la forme hh:mm:ss,ms
	 * 
	 */
	public static long convertirMs(String x){
		long t = 0;
		String[] r = x.split(":");
		
		t += Integer.parseInt(r[2].split(",")[1]);
		t += 1000 * Integer.parseInt(r[2].split(",")[0]); //1s = 1000ms
		t += 60000 * Integer.parseInt(r[1]);  //1min = 60000ms
		t += 3600000 * Integer.parseInt(r[0]);
		
		return t;
	}
	
	/*
	 * Méthode qui met le résultat d'une méthode dans un fichier si l'utilisateur
	 * choisi de le faire
	 * 
	 * @Params : Soustitre qui est un string représentant le contenu à écrire
	 * dans le fichier
	 * 
	 * @Params : nom qui est un string représentant le nom de ce fichier si
	 * l'utilisateur choisi un et "defaultname" sinon.
	 */
	public static void Infile(String Soustitre, String chemin, String nom) {
		
		
		
		String filenameFullNoPath = nom;
		  File myFile =  new File(System.getProperty("user.dir")  + File.separator 
		        +  File.separator + "ASSETS"+  File.separator + chemin + File.separator + filenameFullNoPath);
		  File parentDir = myFile.getParentFile();
		  if(! parentDir.exists()) 
		      parentDir.mkdirs(); // Cr�er les dossiers en cas de besoin
		  try {
			Writer w = new OutputStreamWriter(new FileOutputStream(myFile),StandardCharsets.UTF_8);
			w.write(Soustitre);
			w.flush();
			w.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
