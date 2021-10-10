package ounana.projetVideo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class SousTitrageEpureVideo extends SousTitrageVideo {

    public String SousTitreEpure;
    public String[] keyWords;
    
    public SousTitrageEpureVideo(String fichier) {
		super(fichier);
	}

    public void getKeyWords(){
    	
    	this.keyWords = new String[5];
//      Création d'une Map où le mot est la clé et le nombre d'occurence est la valeur
    	HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
    			
		try (InputStream inputStream = this.getClass().getResourceAsStream("/SousTitresEpureeSrt/"+super.titre);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//			List<String> contents = reader.lines()
//			  .collect(Collectors.toList());
//			
			for(String line : new SousTitrageVideo(reader)) {
				String[] words = line.toLowerCase().split(" ");
              for (String word : words){
                  //Si le mot est déjà présent dans le tableau words son nombre d'occurence sera incrémenté
                  if(wordCountMap.containsKey(word)){
                      wordCountMap.put(word, wordCountMap.get(word)+1);
                  }
                  else{ //sinon on insère le mot comme une clé et 1 comme sa valeur
                      wordCountMap.put(word, 1);
                  }
              }
                
			}
			
		//récupérer toutes les entrées de wordCountMap dans un Set
          Set<Entry<String, Integer>> entrySet = wordCountMap.entrySet();
          //Création d'une List d'entrySet pour convertir la HashMap en une List
          List<Entry<String, Integer>> list = new ArrayList<Entry<String,Integer>>(entrySet);
          //trier la Liste par ordre decroissant de valeurs
          Collections.sort(list, new Comparator<Entry<String, Integer>>(){
              @Override
              public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
                  return (arg0.getValue().compareTo(arg1.getValue()));
              }
          });
          Collections.reverse(list);

          System.out.println("Keyword | occurence");
          for(int i=0;i<5;i++) {
              this.keyWords[i]=list.get(i).getKey();
          	System.out.println("----------------------");
          	System.out.println(list.get(i).getKey()+" | "+list.get(i).getValue());
          }
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getSousTitreEpure() {
		return SousTitreEpure;
	}

	public void setSousTitreEpure(String SousTitreEpure) {
		this.SousTitreEpure = SousTitreEpure;
	}
}


