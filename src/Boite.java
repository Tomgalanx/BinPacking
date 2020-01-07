import java.util.ArrayList;

public class Boite {


    private int capacite,taille;

    // Liste des objets dans la boite
    private ArrayList<Objet> liste;

    // Constructeur de la classe boite avec en parametre la capacite de la boite
    public Boite(int capacite) {
        this.capacite = capacite;
        this.taille = 0;
        this.liste = new ArrayList<>();
    }

    // Méthode pour ajouter un objet
    public void ajouterObjet(Objet objet){
        liste.add(objet);
        taille = taille+objet.getHauteur();

    }

    public int getTailleDispo() {
        return capacite-taille;
    }

    public ArrayList<Objet> getListe() {
        return liste;
    }
}
