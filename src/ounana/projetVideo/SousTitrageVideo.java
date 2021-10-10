package ounana.projetVideo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.util.InvalidFormatException;

public class SousTitrageVideo implements Iterable<String> {
	public String titre;
	public String format;
	public String sousTitre = ""; // attribut où on stocke le soustitrage sans indication du temps
	public String duree;

	public String sousTitreNonConjuge;

	static Map<String,String> verbPhrases = new HashMap<String, String>(); 
	
	protected BufferedReader r;
	protected Iterator<String> i;

	public SousTitrageVideo(BufferedReader r) {
		this.r = r;
		i = new SousTitrageVideoIterator(r);

	}

	public SousTitrageVideo(String fichier) throws FormatException {
		this.titre = fichier;
		String[] split = this.titre.split("\\.(?=[^\\.]+$)");
		if (!split[1].equals("srt")) {
			throw new FormatException("Le fichier choisi ne correspond pas au format srt");
		} else {
			this.format = split[1];
		}

	}

	/*
	 * Méthode qui récupère le contenu du fichier de sous titres choisi
	 * et la stocke dans l'attribut sousTitre sans indication du temps
	 * 
	 * @Params : String représentant le titre de la vidéo
	 */
	public void setSousTitre(String soustitre) {
				
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("/SousTitresSrt/" + this.titre);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

			int type = 1;
			for (String line : new SousTitrageVideo(br)) {
				switch (type) {
				case 1: {
					type++;
					break;
				}
				case 2: {
					type++;
					break;
				}
				case 3: {
					if (line.isEmpty()) {
						type = 1;
					} else {
						this.sousTitre = this.sousTitre + line + "\n";
					}
				}
				}
			}
			br.close();

		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}
	}

	/*
	 * Méthode qui épure le sous titre de la vidéo
	 */
	public void epurerVideo() throws IOException {
		
		
		
		Set<String> listeStopWords = this.StopWordsList(); // Set de StopWords		
		ArrayList<String> newText = new ArrayList<String>();// ArrayList de mots qui composent les lignes du fichier
															// après suppresion de SW
		String text = "";
		String result = "";
		try {
			
			InputStream inputStream = this.getClass().getResourceAsStream("/SousTitresSrt/"+this.titre);
			BufferedReader Br = new BufferedReader(new InputStreamReader(inputStream));

			int type = 1;
			for (String line : new SousTitrageVideo(Br)) {
				String oldText = "";
				switch (type) {
				case 1:// récupérer le numéro (lignes 1, 2, 3...)
					type++;
					break;
				case 2:
					type++;
					break;
				case 3:
					if (line.isEmpty()) {
						type = 1;
					} else {
						oldText = oldText + line;
						String[] textFileWords = oldText.split(" ");// Séparer les mots de la ligne (format token) par
																	// un espace
						for (String w : textFileWords) {
							newText.add(w);
						}
						// boucle pour vérifier si le stopword est dans le fichier pour le supprimer
						for (Iterator<?> it = newText.iterator(); it.hasNext();) {
							if (listeStopWords.contains(it.next())) {
								it.remove();
							}
						}
						for (String w : newText) {
							text = text + w + " ";
						}
						
						result = result + text + "\n";
						
						//myWriter.println(text);
						text = "";
						newText.clear();
					}
				}
			}
			Utils.Infile(result, "SousTitresEpureeSrt", this.titre);
			Br.close();
			//myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Fonction qui pose tout le contenu du fichier(stopWords.txt)contenant 
	 * les stopwords dans un Set 
	 * j'ai choisi le set car il n y a pas de duplication au niveau des StopWords
	 * 
	 */
	public Set<String> StopWordsList() {
		Set<String> stopWords = new LinkedHashSet<String>();
		try (InputStream inputStream = this.getClass().getResourceAsStream("/stopWords.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				List<String> contents = reader.lines()
				  .collect(Collectors.toList());
				
				for(String line : contents) {
					stopWords.add(line.trim());
				}
		} catch (IOException e) {
			System.out.println("Exception : " + e);
		}
		return stopWords;
	}

	/* À optimiser */
	public String getPartieSousTitre(String t, String d) {

		String stop = Utils.pointArret(t, d);

		int type = 1;
		boolean finFound = false;
		boolean debutFound = false;

		String content = "";

		InputStream inputStream = this.getClass().getResourceAsStream("/SousTitresSrt/" + this.getTitre());
		BufferedReader Br = new BufferedReader(new InputStreamReader(inputStream));
					for (String line : new SousTitrageVideo(Br)) {
			switch (type) {
			case 1:
				type++;
				break;
			case 2:
				String[] marge = line.split(" --> ");
				if (marge[0].equals(t) && !finFound) {
					debutFound = true;
				} else if (marge[1].equals(stop)) {
					finFound = true;
				}
				type++;
				break;
			case 3:
				if (line.isEmpty()) {
					type = 1;
				} else {
					if (debutFound && !finFound) {
						content = content + "\n" + line;
					} else if (debutFound && finFound) {
						content = content + "\n" + line;
						debutFound = false;
					}
				}
			}
		}


		return content;
	}

	/*
	 * Méthode qui calcule la durée de la vidéo
	 */
	public String calculerDurreeVideo() {
		try {
			
			InputStream inputStream = this.getClass().getResourceAsStream("/SousTitresSrt/" + this.titre);
			BufferedReader Br = new BufferedReader(new InputStreamReader(inputStream));
			
			int type = 1;
			for (String line : new SousTitrageVideo(Br)) {
				switch (type) {
				case 1:
					type++;
					break;
				case 2:
					String[] marge = line.split(" --> ");
					this.duree = marge[1];
					type++;
					break;
				case 3:
					if (line.isEmpty()) {
						type = 1;
					}
				}
			}
			Br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.duree;
	}

	/*
	 * Méthode qui supprime les marques du pluriel contenu dans les sous titres
	 * de la vidéo sans indication du temps
	 */
	public String supprimerPluriel() {

		String sousTitre = this.getSousTitre();
		String sousTitreModifie = "";

		for (String word : sousTitre.split(" ")) {
			sousTitreModifie = sousTitreModifie + " " + word.replaceAll("s$", "");
		}

		return sousTitreModifie;
	}

	/*
	 * Méthode qui supprime les marques des verbes conjugés du contenu des sous titres
	 * de la vidéo sans indication du temps
	 */
	public String supprimerConjuguee() throws InvalidFormatException, IOException {

		InputStream is = this.getClass().getResourceAsStream("assets/en-parser-chunking.bin");
		ParserModel model = new ParserModel(is);
		Parser parser = ParserFactory.create(model);
		Parse topParses[] = ParserTool.parseLine(this.sousTitre, parser, 1);
		for (Parse p : topParses) {
			// p.show();
			getVerbs(p);
		}
		
		getStrNonConjuge();
		return this.sousTitreNonConjuge;
	}

	public void getVerbs(Parse p) {
		if (p.getType().equals("VB") || p.getType().equals("VBP") || p.getType().equals("VBG")
				|| p.getType().equals("VBD") || p.getType().equals("VBN")) {
			
			String verbe = p.getCoveredText();
			//Suppression es
			verbe = verbe.replaceAll("s$", "");
			//Suppression es
			verbe = verbe.replaceAll("ed$", "e");
			
			verbPhrases.put(p.getCoveredText(), verbe);
			
		} 
		
		for (Parse child : p.getChildren()) {
			getVerbs(child);
	  }
	}
	
	public void getStrNonConjuge() {
		
		for(String mot : this.sousTitre.split("\\s+")) {
			
			for(String key : verbPhrases.keySet()) {
				  if(mot.equals(key)) {
			        	mot = verbPhrases.get(key);
			        }		        
			}
			this.sousTitreNonConjuge = this.sousTitreNonConjuge +" " + mot; 
		} 
	}
	
	/*
	 * Méthode pour chargement de toutes les videos au d�mmarage de l'application
	 */	
	public List<SousTitrageVideo> chargerVideos() {
		
		List<SousTitrageVideo> listeVideos = new ArrayList<>();
		
		InputStream inputStream = this.getClass().getResourceAsStream("/SousTitresSrt/");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<String> contents = reader.lines()
		  .collect(Collectors.toList());
		
		System.out.println("Chargement des videos ... : " + contents);
		for(String titre:contents) {
			System.out.println("Charg�  :> " + titre);
		SousTitrageVideo vid = new SousTitrageVideo(titre);
				vid.setSousTitre(vid.getTitre());
				vid.setDuree(vid.calculerDurreeVideo());
				listeVideos.add(vid);
		}
		
		return listeVideos;		
	}
	

	@Override
	public Iterator<String> iterator() {
		return i;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getSousTitreNonConjuge() {
		return sousTitreNonConjuge;
	}

	public void setSousTitreNonConjuge(String sousTitreNonConjuge) {
		this.sousTitreNonConjuge = sousTitreNonConjuge;
	}
	
	public SousTitrageVideo() {
	}

	public String getSousTitre() {
		return sousTitre;
	}
	

}