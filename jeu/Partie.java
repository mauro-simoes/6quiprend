package jeu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import util.Console;
import java.util.Collections;

/** Type representant une partie */
public class Partie {
	private static Scanner sc = new Scanner(System.in);
	// nombre de cartes dans une partie 
	private static final int NB_CARTES = 104;
	// difference maximale entre deux cartes
	private static final int MAX_DIFFERENCE = 103;
	// nombre maximum de joueurs dans une partie
    private static final int MAX_JOUEURS = 10;
    //nombre minimum de joueurs dans une partie
    private static final int MIN_JOUEURS = 2;
    //nombre de series dans une partie
    private static final int NB_SERIES = 4;
  //nombre de tours dans une partie
    private static final int NB_TOURS=10;
    // liste de joueurs
    private ArrayList<Joueur> joueurs;
    // liste de series
    private ArrayList<Série> séries;
    // la pioche
    private ArrayList<Carte> cartes;
    // hashmap des cartes choises et leur joueur (reinitialisé a chaque tour)
    private HashMap<Carte,Joueur> cartesChoisies;
    
    public Partie(ArrayList<String> listeJoueurs){
    	assert(listeJoueurs.size()>=MIN_JOUEURS && listeJoueurs.size()<=MAX_JOUEURS);
    	initialiserJoueurs(listeJoueurs);
        initialiserCartes();
        distribuer();
        initialiserséries();
        cartesChoisies= new HashMap<>();
    } 
    
    /**
     * Initialise la liste de joueurs
     * @param liste contenant les noms des joueurs
    */
    private void initialiserJoueurs(ArrayList<String> nomsJoueurs){
        joueurs = new ArrayList<>();
    	for (String s : nomsJoueurs) joueurs.add(new Joueur(s));
    }
    
    /**
     * Initialise les 104 cartes de la partie
    */
    private void initialiserCartes(){
        cartes= new ArrayList<>();
        for(int i=1;i<=NB_CARTES;++i) cartes.add(new Carte());
    }
    
    /**
     * Initialise la liste de séries
    */
    private void initialiserséries() {
    	séries = new ArrayList<Série>();
        for(int i=0;i<NB_SERIES;i++) séries.add(new Série(derniereCartePioche()));
    }
    
    /**
     * Distribue les cartes aux joueurs
    */
    private void distribuer(){
        Collections.shuffle(cartes);
        for(int i=0;i<MAX_JOUEURS;++i){
            for(int j=0;j<joueurs.size();++j) joueurs.get(j).prendre(derniereCartePioche());
        }
    }
    

    /**
     * Retourne la derniere carte de la pioche
     * @return la carte
    */
    private Carte derniereCartePioche(){
        Carte c = new Carte();
        c = cartes.get(0);
        cartes.remove(0);
        return c;
    }
    
    /**
     * Permet de jouer une partie entière du jeu
    */
    public void jouer() {
    	System.out.println(this);
    	for (int tours=0;tours<NB_TOURS;tours++) {
            for (int j=0;j<joueurs.size();j++) {
            	System.out.print("A "+joueurs.get(j)+ " de jouer.\n");
            	Console.pause();
                System.out.print(afficherSeries());
                System.out.println(joueurs.get(j).afficherCartes());
                System.out.print("Saisissez votre choix : ");
                selectionCarte(j,choixDeCarte(j));
                Console.clearScreen();
            }
            rangementSeries();
        }
        System.out.print(scoreFinal());
        sc.close();
    }
    
    /**
     * Met en correspondance la carte ainsi que le joueur qui l'a choisi pour le tour en cours / le joueur pose la carte
     * @param index du joueur
     * @param numero de la carte
    */
    private void selectionCarte(int idxJoueur, int nombre){
    	cartesChoisies.put(joueurs.get(idxJoueur).poser(nombre),joueurs.get(idxJoueur));
    }
    
    /**
     * Verifie si la carte peut etre posée dans au moins une serie de la partie
     * @param numero de la carte
     * @return vrai si la carte peut etre posée dans au moins une serie de la partie
    */
    private boolean carteValide(Carte c) {
    	boolean estValide=false;
    	for (Série sr : séries) {
    		if (sr.getDerniereCarte().estPlusPetit(c)) estValide=true;
    	}
    	return estValide;
    }

    /**
     * Range les cartes choisies par les joueurs dans les series
    */
    private void rangementSeries() {
    	ArrayList<Integer> numCartes = new ArrayList<>();
    	for (Carte c : cartesChoisies.keySet()) numCartes.add(c.getNuméro());
    	Collections.sort(numCartes);
    	for (int num : numCartes) {
    		// on parcourt les cartes choisies par les joueurs au tour actuel
    		for (Carte c : cartesChoisies.keySet()) {
        		if (c.correspond(num)) {
    				Joueur joueur=cartesChoisies.get(c);
        			if(carteValide(c)) {
                        int idxSerie =serieAPoser(c);
            			// si la série n'est pas pleine on ajoute la carte sinon on remplace la série
                        if (!séries.get(idxSerie).estPleine()) séries.get(idxSerie).ajouter(c);
                        else remplacerSerie(idxSerie,joueur);
        			}
        			else {
        				System.out.println(cartesAPoser());
            			System.out.println("Pour poser la carte " + c.getNuméro() + ", " + joueur + " doit choisir la série qu'il va ramasser.");
            			System.out.print(afficherSeries());
            	    	System.out.print("Saisissez votre choix : ");
        				remplacerSerie(choixDeSerie()-1,joueur);
        			}
        			break;
        		}
        	}
    	}
    	System.out.println(cartesPosees());
        System.out.print(afficherSeries());
        System.out.print(tetesDeBoeufs());
        nouveauTour();
    }
    
    /**
     * Retourne l'index de la série ou la carte doit etre posée
     * @return l'index
    */
    private int serieAPoser(Carte c) {
    	// si une carte peut etre posee dans une série on remplace false par 
		// true a la position correspondant a celle de la serie
		boolean[] table= {false,false,false,false};
		for (int i=0;i<NB_SERIES;i++) {
			table[i]= séries.get(i).peutEtrePosee(c);
		}
		// on calcule ensuite les differences entre la carte et la derniere carte de chaque serie
		// pour recuperer la serie qui a la plus petite difference avec la carte
		int[] differences= {MAX_DIFFERENCE,MAX_DIFFERENCE,MAX_DIFFERENCE,MAX_DIFFERENCE};
		for (int i=0;i<NB_SERIES;i++) {
			if (table[i]) differences[i]=c.difference(séries.get(i).getDerniereCarte());
		}
       return indexValeurMin(differences);
    }
    
    /**
     * Retourne l'index de la plus petite valeur du tableau
     * @return index de la plus petite valeur
    */
    private static int indexValeurMin(int[] tab) {
    	int indexMin=0;
    	for (int i=0;i<tab.length;i++) if (tab[i]<tab[indexMin]) indexMin=i;
    	return indexMin;
    }
    
    
    /**
     * Remplace la serie avec la carte choisie par le joueur entre en parametre
     * @param index de la serie
     * @param le joueur
    */
    private void remplacerSerie(int idxSerie,Joueur j) {
    	for (Carte c: cartesChoisies.keySet()) {
    		if (cartesChoisies.get(c).toString().equals(j.toString())) {
    			ArrayList<Carte> cartes = new ArrayList<> (séries.get(idxSerie).remplacer(c));
    			for (Carte cte : cartes) cartesChoisies.get(c).ramasser(cte);
    			break;
    		}
    	}
    }
    
    /**
     * Reinitialise les cartes posees par les joueurs ainsi que les points qu'ils ont ramassé
    */
    private void nouveauTour() {
    	cartesChoisies.clear();
    	for (Joueur j : joueurs) j.reinitialiserPoints();
    }
    
    /**
     * Trie les joueurs en fonction des points qu'ils ont ramassé pendant le tour en cours
    */
	private static void triJoueursPoints(Joueur[] joueurs) {
	    int i, j;
	    Joueur tmp;
	    if (joueurs.length > 1) {
		    for (i = 1; i < joueurs.length; i++) {
		        Joueur joueur = joueurs[i];
		        j = i;
                while (j > 0 && (joueurs[j - 1].getNbTetes() > joueur.getNbTetes())) {
		            tmp = joueurs[j];
		            joueurs[j] = joueurs[j - 1];
		            joueurs[j - 1] = tmp;
		            j--;
		        }
		        joueurs[j] = joueur;
		        // si les joueurs on le meme nombre de tetes on les trie par ordre lexicographique
		        while(j>0 && joueurs[j - 1].getNbTetes() == joueur.getNbTetes()) {
		            if (joueurs[j - 1].toString().compareTo(joueur.toString()) > 0) {
		                tmp = joueurs[j];
		                joueurs[j] = joueurs[j - 1];
		                joueurs[j - 1] = tmp;
		            }
		            j--;
		        }
		    }
	    }
	}

	/**
     * Trie les joueurs en fonction des pénalités qu'ils ont recuperés
    */
    private static void triJoueursPénalités(Joueur[] joueurs) {
	    int i, j;
	    Joueur tmp;
	    if (joueurs.length > 1) {
		    for (i = 1; i < joueurs.length; i++) {
		        Joueur joueur = joueurs[i];
		        j = i;
                while (j > 0 && (joueurs[j - 1].getPénalités() > joueur.getPénalités())) {
		            tmp = joueurs[j];
		            joueurs[j] = joueurs[j - 1];
		            joueurs[j - 1] = tmp;
		            j--;
		        }
		        joueurs[j] = joueur;
		        while(j>0 && joueurs[j - 1].getPénalités() == joueur.getPénalités()) {
		            if (joueurs[j - 1].toString().compareTo(joueur.toString()) > 0) {
		                tmp = joueurs[j];
		                joueurs[j] = joueurs[j - 1];
		                joueurs[j - 1] = tmp;
		            }
		            j--;
		        }
		    }
	    }
	}
    
    /**
     * Retourne une chaine de caracteres contenant les nombres de tetes de boeufs que les joueurs ont ramassées pendant un tour
     * @return chaine de caracteres
    */
    private String tetesDeBoeufs() {
		StringBuilder s= new StringBuilder();
		ArrayList<Joueur> arrayJ= new ArrayList<>();
		for (int i=0;i<joueurs.size();i++) {
			if (joueurs.get(i).getNbTetes()>0) arrayJ.add(joueurs.get(i));
		}
		Joueur[] listeJ = arrayJ.toArray(new Joueur[0]);
		if (listeJ.length==0) return "Aucun joueur ne ramasse de tête de boeufs.\n";
		triJoueursPoints(listeJ);
		for (int i=0;i<listeJ.length;i++) {
			Joueur j = listeJ[i];
			s.append(j + " a ramassé ");
			if (j.getNbTetes()==1) s.append("1 tête de boeufs\n");
			else s.append(j.getNbTetes() + " têtes de boeufs\n");
		}
		return s.toString();
	}
    
    /**
     * Retourne le numero de la carte choisie par le joueur
     * @param index du joueur qui doit choisir une carte
     * @param la partie
     * @return numéro de la carte
    */
    private int choixDeCarte(int idxJoueur){
        boolean choix =false;
        while (!choix){
            if (sc.hasNextInt()){
                int nombre= sc.nextInt();
                if (joueurs.get(idxJoueur).possede(nombre)) {
                    choix=true;
                	return nombre;
                }
                else System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
            }
            else {
                System.out.print("Vous n'avez pas cette carte, saisissez votre choix : ");
                sc.next();
            }
        }
        return -1;
    }
    
    /**
     * Retourne le numero de la serie choisie par le joueur
     * @return numéro de la serie
    */
    private static int choixDeSerie() {
    	boolean choix =false;
    	while (!choix){
        	if (sc.hasNextInt()){
                int nombre= sc.nextInt();
                if (nombre>0 && nombre<=NB_SERIES) {
                	choix=true;
                	return nombre;
                }
                else System.out.print("Ce n'est pas une série valide, saisissez votre choix : ");
            }
            else {
                System.out.print("Ce n'est pas une série valide, saisissez votre choix : ");
                sc.next();
            }
        }
    	return -1;
    }

    /**
     * Retourne une chaine de caracteres contenant les nombres de tetes de boeufs que les joueurs ont ramassées a la fin de la partie
     * @return chaine de caracteres
    */
    private String scoreFinal(){
        StringBuilder s = new StringBuilder ("** Score final\n");
        Joueur[] Liste = joueurs.toArray(new Joueur[0]);
        triJoueursPénalités(Liste);
        for (int i=0; i<joueurs.size() ;i++){
            s.append(Liste[i].toString() + " a ramassé " + Liste[i].getPénalités());
            if (Liste[i].getPénalités() == 0) s.append(" tête de boeufs\n");
            else if (Liste[i].getPénalités() == 1) s.append(" tête de boeufs\n");
            else s.append(" têtes de boeufs\n");    
        }
        return s.toString();
    }
    
    /**
     * Retourne une chaine de caracteres contenant les nombres des cartes qui vont etre posées
     * @return chaine de caracteres
    */
    private String cartesAPoser() {
    	TreeMap<Integer,String> cartes =new TreeMap<>();
    	for (Carte c : cartesChoisies.keySet()) cartes.put(c.getNuméro(), cartesChoisies.get(c).toString());
    	StringBuilder s= new StringBuilder("Les cartes ");
    	int nb=1;
    	for (int i : cartes.keySet()) {
    		  s.append(i + " (" + cartes.get(i) + ")");
    		  if (nb<=joueurs.size()-2) s.append(", ");
    		  else if(nb==joueurs.size()-1) s.append(" et ");
    		  nb++;
    	}
    	s.append(" vont être posées.");
    	return s.toString();
    }

    /**
     * Retourne une chaine de caracteres contenant les nombres des cartes qui ont été posées
     * @return chaine de caracteres
    */
	private String cartesPosees() {
		TreeMap<Integer,String> cartes =new TreeMap<>();
    	for (Carte c : cartesChoisies.keySet()) cartes.put(c.getNuméro(), cartesChoisies.get(c).toString());
    	StringBuilder s= new StringBuilder("Les cartes ");
    	int nb=1;
    	for (int i : cartes.keySet()) {
    		  s.append(i + " (" + cartes.get(i) + ")");
    		  if (nb<=joueurs.size()-2) s.append(", ");
    		  else if(nb==joueurs.size()-1) s.append(" et ");
    		  nb++;
    	}
    	s.append(" ont été posées.");
    	return s.toString();
    }
    
	
	/**
     * Retourne une chaine de caracteres contenant les 4 séries de la partie
     * @return chaine de caracteres
    */
	private String afficherSeries() {
    	StringBuilder s = new StringBuilder();
    	for (Série sr : séries) s.append(sr.toString());
    	return s.toString();
    }

    public String toString(){
        StringBuilder s=new StringBuilder();
        s.append("Les "+  joueurs.size() + " joueurs sont ");
        for (int i=0; i<joueurs.size();++i){
            s.append(joueurs.get(i));
            if( i==joueurs.size()-2) s.append(" et ");
            else if( i<joueurs.size()-2) s.append(", ");
        }
        s.append(". Merci de jouer à 6 qui prend !");
        return s.toString();
    }
    

}
