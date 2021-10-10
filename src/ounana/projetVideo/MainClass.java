package ounana.projetVideo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import opennlp.tools.util.InvalidFormatException;

public class MainClass {

	public static List<SousTitrageVideo> listeVideos = new ArrayList<>();

	public static void main(String[] args) throws NumberFormatException, InvalidFormatException, IOException {

		// Chargement de toutes les videos
		SousTitrageVideo stv = new SousTitrageVideo();
		listeVideos = stv.chargerVideos();
		
		while (true) {
			System.out.println("******************************** Menu principal ********************************");

			System.out.println("Voulez vous traiter� :\n"
					+ "1-l'ensemble de vidéos ? \n2-video par video ?");

			Scanner sc = new Scanner(System.in);
			System.out.println("/=\\ Si vous voulez la première option veuillez saisir 1 sinon 2");
			int choix = sc.nextInt();

			switch (choix) {
			
			// Traitements sur l'ensemble des vidéos
			case 1:
				
				System.out.println("a.Calculer la similarité entre les sous-titres de deux vidéos ");
				System.out.println("b.Afficher le groupe de vidéos classés par sujet");
				System.out.println("c.Afficher la dur�e moyenne des vid�os");
				System.out.println("d.Afficher les mots clés des groupes similaires");


				Scanner scan = new Scanner(System.in);
				System.out.println("===>Veuillez choisir une option :");
				String option = scan.nextLine();

				switch (option) {
				case "a":// Similarité
					System.out.flush();
					System.out.println("Vous avez choisis de calculer la similarité entre deux vidéos");
					afficherVideos();

					// choix du premier titre
					Scanner Scan1 = new Scanner(System.in);
					System.out.println("=>Veuillez indiquer le titre de la première video");
					String objet1 = Scan1.nextLine();
					String SousTitre1 = listeVideos.get(Integer.parseInt(objet1)).getSousTitre();

					// choix du deuxième titre
					Scanner Scan2 = new Scanner(System.in);
					System.out.println("=>Veuillez indiquer le titre de la vidéo avec laquelle vous voulez comparer");
					String objet2 = Scan2.nextLine();
					String SousTitre2 = listeVideos.get(Integer.parseInt(objet2)).getSousTitre();

					// Distance Lavenshtein 
					SimilariteImpl2 simi2 = new SimilariteImpl2(SousTitre1, SousTitre2);
					double resultat2 = simi2.calculerSimilarite();
					System.out.println(String.format(
							"%.3f est la similarity entre les deux sous-titres(Distance de Lavenshtein)", resultat2));

					// Similarité cosinus
					SimilariteImpl1 simi1 = new SimilariteImpl1(SousTitre1, SousTitre2);
					double result = simi1.calculerSimilarite();
					System.out.println(
							String.format("%.3f est la similarity entre les deux sous-titres(similarité Cosinus)", result));

					break;

				case "b": // groupe de vidéo avec meme Similarité
					
					System.out.flush();
					System.out.println("vous avez choisis 6");
					GroupVideos gv = new GroupVideos();
					gv.setListeVideos(listeVideos);
					gv.affichageVideosGroupeSimilarite();
					break;

				case "c":// Afficher la durÃ©e moyenne des vidÃ©os
					System.out.flush();
					System.out.println("vous avez choisis c");
					System.out.println("La durrée moyenne des vidéos est: " + DurreMoyenne());
					break;
					
				case "d":// Afficher les mots clés des groupes similaires
					System.out.flush();
					System.out.println("vous avez choisis d");
					GroupVideos gv2 = new GroupVideos();
					gv2.setListeVideos(listeVideos);
					System.out.println("Les mots clés des groupes similaires: ");
					gv2.setKeywordsListeVideos();
					break;
				}

				break;

			// Traitements sur chaque vidéo
			case 2:
				afficherVideos();
				Scanner Scan = new Scanner(System.in);
				System.out.println("===>Veuillez saisir le numero de la video à  traiter:");
				String objet = Scan.nextLine();
				protocole(listeVideos.get(Integer.parseInt(objet)));
			
			default:
				System.out.println("Mauvais choix, veuillez ressayer:");
			}
		}
	}
	
	
	/*
	 * Méthode pour l'affichage des titres des vidéos sur le menu
	 */
		public static void afficherVideos() {
			int i = 0;
			System.out.println("Liste des videos ==>");
			for(SousTitrageVideo vid : listeVideos) {
				System.out.println("" + i + " : " + vid.getTitre());
				i++;
			}
		}

		
	/*
	 * Méthode qui met print le protocle à faire sur l'objet choisit par
	 * l'utilisateur
	 * 
	 * @Params : Objet de type SousTitrageVideo
	 */
	public static void protocole(SousTitrageVideo stv) throws InvalidFormatException, IOException {
		System.out.println("1.Afficher les sous-titres de la video");
		System.out.println("2.Afficher une partie des sous-titres de la video");
		System.out.println("3.Afficher les mots clés de la video");
		System.out.println("4.Afficher la durée d'une vidéo");
		System.out.println("5.Suppression des marques de pluriel des noms");
		System.out.println("6.Suppression des formes conjuguées évidentes des verbes");
		System.out.println("99.Quitter ");

		Scanner scan = new Scanner(System.in);
		System.out.println("===>Veuillez choisir une option :");
		int option = scan.nextInt();

		switch (option) {
		case 1: { // Afficher les sous-titres de la video
			System.out.println("vous avez choisis 1");
			System.out.println(
					"Indiquez \"F\" ou \"f\" si vous voulez récupérer le résultat dans un fichier et un autre caractÃ¨re sinon");
			Scanner cc = new Scanner(System.in);
			String c = cc.nextLine();
			if (c.equalsIgnoreCase("F")) {
				System.out.println("Vous trouverez le résultat dans le dossier \"SousTitres\"");
				System.out.println("==>Dans ce cas,veuillez choisir un nom pour ce fichier(en faisant entrer un nom sera généré automatiquement)");
				Scanner cc2 = new Scanner(System.in);
				String nom = cc2.nextLine();
				if (nom.isEmpty()) {
					nom = "Soustitre - "+stv.getTitre();
				}
				Utils.Infile(stv.getSousTitre(),"SousTitres", nom);
			} else {
				System.out.println(stv.getSousTitre());
			}
			break;
		}
		case 2: {// Afficher une partie des sous-titres de la video
			System.out.flush();
			System.out.println("Vous avez choisis d'afficher une partie des sous-titres de la video");
			///////
			System.out.println(
					"Veuillez indiquer le temps de départ sous la forme HH:MM:SS,MS (par exemple 00:00:07,00 pour indiquer un départ à 7s après le début de la vidéo)");

			Scanner sc1 = new Scanner(System.in);
			String tDepart = sc1.nextLine();

			System.out.println("Veuillez indiquer une durée (par exemple 00:00:30,00 pour indiquer 30s)");
			Scanner sc2 = new Scanner(System.in);
			String tDuree = sc2.nextLine();
		
			System.out.println(
					"Indiquez \"F\" ou \"f\" si vous voulez récupérer le résultat dans un fichier et un autre caractére sinon");
			Scanner sc3 = new Scanner(System.in);
			String c = sc3.nextLine();
			if (c.equalsIgnoreCase("F")) {
				System.out.println("Vous trouverez le résultat dans le dossier \"SousTitres\"");
				System.out.println("==>Dans ce cas,veuillez choisir un nom pour ce fichier(en faisant entrer un nom sera généré automatiquement)");
				Scanner sc4 = new Scanner(System.in);
				String nom = sc4.nextLine();
				if (nom.isEmpty()) {
					nom = "defaultName";
				}
				Utils.Infile(stv.getPartieSousTitre(tDepart, tDuree),"SousTitres", nom);
			} else {
				
				System.out.println(stv.getPartieSousTitre(tDepart, tDuree));
			}
			break;
		}
		case 3: { // keyWords
			System.out.flush();
			System.out.println("vous avez choisis d'afficher les mots-clés de la video");
			try {
				stv.epurerVideo();
			} catch (IOException e) {
				e.printStackTrace();
			}

			SousTitrageEpureVideo sv = new SousTitrageEpureVideo(stv.titre);
			sv.getKeyWords();
			break;
		}
		case 4: { // Afficher la durrée d'une vidéo
			System.out.flush();
			System.out.println("vous avez choisis 4");
			System.out.println("La durrÃ©e de la vidéo ( " + stv.titre + " )est : \n" + stv.getDuree());
			break;
		}

		case 5: {// Suppression des marques de pluriel des noms
			System.out.flush();
			System.out.println("vous avez choisis 7");
			System.out.println("Indiquez \"F\" ou \"f\" si vous voulez récupérer le résultat dans un fichier et un autre caractÃ¨re sinon");
			Scanner sc6 = new Scanner(System.in);
			String c = sc6.nextLine();
			if (c.equalsIgnoreCase("F")) {
				System.out.println("Vous trouverez le résultat dans le dossier \"SousTitres\"");
				System.out.println("==>Dans ce cas,veuillez choisir un nom pour ce fichier(en faisant entrer un nom sera généré automatiquement)");
				String nom = scan.nextLine();
				if (nom.isEmpty()) {
					nom = "Pluriel supprimé - " + stv.titre;
				}
				Utils.Infile(stv.supprimerPluriel(),"SousTitres", nom);
			} else {
				System.out.println(
						"Voici le contenu avec les marques de pluriel des noms supprimé: \n" + stv.supprimerPluriel());
			}
			break;

		}
		case 6: { // Suppression des formes conjuguées évidentes des verbes
			System.out.flush();
			System.out.println("vous avez choisis 8");
			System.out.println("Indiquez \"F\" ou \"f\" si vous voulez récupérer le résultat dans un fichier et un autre caractÃ¨re sinon");
			Scanner sc7 = new Scanner(System.in);
			String c = sc7.nextLine();
			if (c.equalsIgnoreCase("F")) {
				System.out.println("Vous trouverez le résultat dans le dossier \"SousTitres\"");
				System.out.println("==>Dans ce cas,veuillez choisir un nom pour ce fichier(en faisant entrer un nom sera généré automatiquement)");
				Scanner sc8 = new Scanner(System.in);
				String nom = sc8.nextLine();
				if (nom.isEmpty()) {
					nom = "conjuguaison supprimée- " + stv.titre;
				}
				Utils.Infile(stv.supprimerConjuguee(),"SousTitres", nom);
			} else {
				System.out.println("Veuillez patienter ça peut prendre du temps");
				System.out.println("Voici le contenu avec les formes conjuguées supprimé: \n" + stv.supprimerConjuguee());
				
			}
			
			break;
		}
		case 99: {
			break;
		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + option);
		}
	}

	/*
	 * Méthode pour Calculer la durée moyenne de toute la liste de videos
	 * et retourne le résultat sous format hh:mm:ss,ms
	 */
	public static String DurreMoyenne() {
		long dureeGlobale = 0;
		String dureeMoyenne;

		ArrayList<String> tempsListe = new ArrayList<>();

		// Extraire chaque partie de temps de la forme hh:mm:ss,ms et la mettre dans le
		// tableau qui lui convient
		for (SousTitrageVideo objet : listeVideos) {
			tempsListe.add(objet.getDuree());
			dureeGlobale += Utils.convertirMs(objet.getDuree());
		}

		// Avant de convertir le temps on divise par le nombre de vidéos existant
		long dureeMoyenneEnMs = dureeGlobale / listeVideos.size() - 1;

		long millis = dureeMoyenneEnMs % 1000;
		long second = (dureeMoyenneEnMs / 1000) % 60;
		long minute = (dureeMoyenneEnMs / (1000 * 60)) % 60;
		long hour = (dureeMoyenneEnMs / (1000 * 60 * 60)) % 24;

		dureeMoyenne = String.format("%02d:%02d:%03d,%d", hour, minute, second, millis);

		return dureeMoyenne;
	}

}
