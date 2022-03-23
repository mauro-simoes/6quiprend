package appli;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Application{
	
	public static void main(String[] args) {
		try {
            Partie p = new Partie(enregistrementJoueurs("config.txt"));
            p.jouer();
		} catch (FileNotFoundException e) {
			System.out.println("Impossible d'ouvrir le fichier");
		}
    }
	
	/**
     * Retourne la liste de noms de joueurs a partir du fichier pass� en parametre
     * @param nom du fichier
     * @return liste des noms de joueurs
    */
	public static ArrayList<String> enregistrementJoueurs(String nomFichier) throws FileNotFoundException{
		ArrayList<String> joueurs= new ArrayList<>();
		Scanner in = new Scanner(new FileInputStream(nomFichier));
		while (in.hasNextLine()) joueurs.add(in.next());
		in.close();
		return joueurs;
    }
    

}
