package ounana.projetVideo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class GroupVideos {
	
	public List<SousTitrageVideo> listeVideos = new ArrayList<>();
	
	
	//Constructeur
	public GroupVideos() {
		
	}

	public Map<Double, List<SousTitrageSimilarite>> groupVideosBySimilarite(){
		Map<Double, List<SousTitrageSimilarite>>listeVideoSimilarites = new HashMap<>();
		List<SousTitrageSimilarite> listeSimilariteTmp = new ArrayList<SousTitrageSimilarite>();
		
		for(SousTitrageVideo vid1 : listeVideos) {
			for(SousTitrageVideo vid2 : listeVideos) {
				if(!vid1.equals(vid2)) {
					
					SimilariteImpl2 similarite = new SimilariteImpl2(vid1.getSousTitre(), vid2.getSousTitre());
					Double sim =  similarite.calculerSimilarite();
					
					SousTitrageSimilarite sts1 = new SousTitrageSimilarite();
					sts1.setSimilarite(sim);
					sts1.setTitreVideo(vid1);
					if(!listeSimilariteTmp.contains(sts1))
					listeSimilariteTmp.add(sts1);
					
					SousTitrageSimilarite sts2 = new SousTitrageSimilarite();
					sts2.setSimilarite(sim);
					sts2.setTitreVideo(vid2);
					if(!listeSimilariteTmp.contains(sts2))
					listeSimilariteTmp.add(sts2);
				}
			}
		}
		
		listeVideoSimilarites = listeSimilariteTmp.stream()
				.distinct().collect(Collectors.groupingBy(SousTitrageSimilarite::getSimilarite));
		
		return listeVideoSimilarites;
	}

	public void affichageVideosGroupeSimilarite(){
		Map<Double, List<SousTitrageSimilarite>> ss = groupVideosBySimilarite();
	    for(Map.Entry<Double, List<SousTitrageSimilarite>> listEntry : ss.entrySet()){
	      System.out.println(">>Similarité : " + listEntry.getKey());
	      int i=1;
	      for(SousTitrageSimilarite video : listEntry.getValue()){
	    	  System.out.println("Video "+ i + ": "+ video.getTitreVideo());
	    	  i++;
	      }
	    }
	}
	
	
	public void setKeywordsListeVideos(){
		Map<Double, List<SousTitrageSimilarite>> listeVideoSimilarites = groupVideosBySimilarite();
		String SousTitrageEnsembleVids = "";
		
	    for(Map.Entry<Double, List<SousTitrageSimilarite>> listEntry : listeVideoSimilarites.entrySet()){
	      System.out.println("Similarité : " + listEntry.getKey());
	      int i =1;
	      for(SousTitrageSimilarite video : listEntry.getValue()){
	    	  System.out.println("Video "+ i + ": "+ video.getTitreVideo());
	    	  i++;
	    	  SousTitrageEnsembleVids += " "+video.getSousTitre();
	      }
	      System.out.println(">>Mots clés : \n");
	      chercherMotsCles(SousTitrageEnsembleVids);
	      
	    }
	}
	
	
	private void chercherMotsCles(String sousTitrageEnsembleVids) {
		//1° : on enlève les StopWords du String
		Set<String> listeStopWords = this.listeVideos.get(0).StopWordsList(); // Set de StopWords
		String[] mots = sousTitrageEnsembleVids.toLowerCase().split("\\s");
		ArrayList<String> textFileWords = new ArrayList<>();
		
		for(int i=0; i<mots.length;i++) {
			textFileWords.add(mots[i]);
		}
		
		// boucle pour vérifier si le stopword est dans l'array pour le supprimer
		for (Iterator<?> it = textFileWords.iterator(); it.hasNext();) {
			if (listeStopWords.contains(it.next())) {
				it.remove();
			}
		}
		
		//2° : Algo pour trouver les KeyWords
		
	        //Création d'une Map où le mot est la clé et le nombre d'occurence est la valeur
	    HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
	    for (String word : textFileWords){
	       //Si le mot/word est déjà présent dans la Map son nombre d'occurence sera incrémenté
	       if(wordCountMap.containsKey(word)){
	          wordCountMap.put(word, wordCountMap.get(word)+1);
	       }else{ //sinon on insère le mot comme une clé et 1 comme sa valeur
	          wordCountMap.put(word, 1);
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
	    	  System.out.println("----------------------");
	          System.out.println(list.get(i).getKey()+" | "+list.get(i).getValue());
	       }
	}

	public List<SousTitrageVideo> getListeVideos() {
		return listeVideos;
	}

	public void setListeVideos(List<SousTitrageVideo> listeVideos) {
		this.listeVideos = listeVideos;
	}
}
