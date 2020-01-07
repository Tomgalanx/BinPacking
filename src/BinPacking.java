import java.util.*;

public class BinPacking {


    private int taille;

    public BinPacking(int taille){

        this.taille = taille;
    }


    // Méthode qui ne prend pas en compte les conflits dans l'arbre et les objets ne sont pas entiers
    public int FractionalPacking(ArrayList<Objet> list){

        // Le compteur du nombre de sac
        int res=0;

        // Si il y a des objets dans la liste
        if(list.size() != 0){

            // Compteur de la somme des hauteurs des objets
            int objets=0;

            // On fait la somme des hauteurs des objets
            for(Objet i : list){
                objets=objets+i.getHauteur();
            }

            // On calcule le nombre de boite
            res=objets / taille;

            // Si ce n'est pas une division entière, on découpe l'objet pour le mettre dans une autre boite
            if(taille % objets != 0)
                res++;
        }



        return res;
    }


    // Range les objets par ordre décroissant en hauteur
    public int FirstFitDecreasing(ArrayList<Objet> list,ArbreConflit arbre){

        // Liste triée par ordre décroissant
        Collections.sort(list, Collections.reverseOrder());

        // Liste de toutes les boites
        ArrayList<Boite> res = new ArrayList<>();

        //On ajoute la premiere boite
        res.add(new Boite(taille));

        // on parcourt les objets
        for(int i =0;i<list.size();i++){

            Objet objet = list.get(i);

            // Si l'objet peut rentrer dans une boite
            if(objet.getHauteur() <= taille){


                // On cherche la premiere boite qui peux accueillir l'objet
                boolean trouve =false;
                int indexBoite=0;

                // On parcourt toutes les boites
                while(indexBoite<res.size()){

                    Boite tmp = res.get(indexBoite);
                    int tailleDispo = tmp.getTailleDispo();

                    // On ajoute si la taille est dispo, que l'objet n'a pas encore été ajouté et qu'il n'y a pas de conflit dans l'arbre
                    if(tailleDispo >= objet.getHauteur() && !trouve && !conflit(objet.getIndex(),tmp,arbre)){
                        tmp.ajouterObjet(objet);
                        trouve=true;
                    }

                    indexBoite++;
                }

                // Si il n'y a pas de place, on ajoute une nouvelle boite
                if(!trouve){
                    Boite b =new Boite(taille);
                    b.ajouterObjet(objet);
                    res.add(b);
                }
            }

        }

        /*
        int index =1;
        for(Boite b : res){
            System.out.println("La boite "+index);
            for(Objet i :b.getListe()){
                System.out.println("Element : "+i.getIndex());
            }

            System.out.println("----------------------");
            index++;
        }

         */

        return res.size();

    }

    // Méthode qui vérifie si il y a un conflit avec l'objet et la boite d'après l'arbre
    private boolean conflit(int indexObjet, Boite tmp, ArbreConflit arbre) {


        boolean reponse=false;

        // On parcourt tous les objets de la boites
        for(Objet o : tmp.getListe()){

            // on vérifie si il y a un conflit
            reponse=arbre.checkConflit(o.getIndex(),indexObjet);

            // Si il y a un conflit on arrete et on retourne vrai
            if(reponse)
                return true;
        }

        // sinon on retourne faux
        return false;


    }


    // Méthode qui insert les objets par ordre décroissant de hauteur et avec la capacite restante de la boite la plus grande possible
    public int BestFitDecreasingPacking(ArrayList<Objet> list, ArbreConflit arbre){

        // Liste triée par ordre décroissant
        Collections.sort(list, Collections.reverseOrder());

        // Liste de boites
        ArrayList<Boite> res = new ArrayList<>();
        // Première boite
        res.add(new Boite(taille));

        // On parcourt les objets
        for(int i =0;i<list.size();i++) {

            Objet objet = list.get(i);

            // Si l'objet peut rentrer dans une boite
            if (objet.getHauteur() <= taille) {

                // On cherche la premiere boite qui peux accueillir l'objet
                boolean trouve =false;
                int maxBoite=-1;
                Boite boiteMax =null;
                int boite=0;

                // On parcourt les boites
                while(boite<res.size()){

                    Boite tmp = res.get(boite);
                    int tailleDispo = tmp.getTailleDispo();

                    // Si l'objet peut rentrer dans la boite
                    if(tailleDispo >= objet.getHauteur() && !conflit(objet.getIndex(),tmp,arbre)){

                        // Si la taille max de la boite est plus grande, on change
                        if(tailleDispo - objet.getHauteur() > maxBoite){
                            maxBoite = tailleDispo-objet.getHauteur();
                            boiteMax=tmp;
                        }
                    }
                    boite++;
                }

                // Si on a trouvé une boite
                if(maxBoite != -1){
                    boiteMax.ajouterObjet(objet);
                }

                // Si il n'y a pas de boite qui respecte nos conditions, on crée une nouvelle
                else{
                    Boite tmp= new Boite(taille);
                    tmp.ajouterObjet(objet);
                    res.add(tmp);
                }


            }

        }

        return res.size();

    }


    public ArrayList<Objet> Dsatur(ArbreConflit conflit, ArrayList<Objet> U){

        // Liste des C
        ArrayList<Objet> C = new ArrayList<>();

        // Liste de V
        ArrayList<Objet> V = new ArrayList<>();

        // Copie de la Liste des V
        ArrayList<Objet> V1 = new ArrayList<>();

        // Liste des sommets triés par ordre de degres.
        Map<Integer, ListConflit> integerListConflitMap = conflit.sortMap();

        // Map Key : index objet et Value : Objet
        HashMap<Integer,Objet> mapObjet = new HashMap<>();

        // On remplie la map
        for(Objet o : U){
            mapObjet.put(o.getIndex(),o);
        }


        // On remplit les liste V triées par degres max.
        for(Map.Entry<Integer, ListConflit> entry : conflit.sortMap().entrySet()) {
            Integer key = entry.getKey();
            V.add(mapObjet.get(key));
            V1.add(mapObjet.get(key));

        }


        // On récupert le premier objet
        Objet premier = V.get(0);
        // On le colorie
        premier.colorier(1);
        V.remove(premier);
        C.add(premier);

        // Tant qu'on a pas parcourut tous les sommets
        while(!C.equals(V1)){

            int maxSat = -1;
            int maxDegres=-1;
            int index=0;


            // On parcourt les sommets restant de V
            for(Objet o : V){

                int saturation=0;

                // si il y a un conflit
                if(integerListConflitMap.get(o) != null) {
                    for (int i : integerListConflitMap.get(o).getList()) {
                        if (C.contains(new Objet(i)))
                            saturation++;
                    }

                    if (saturation >= maxSat) {

                        // Si le degres du sommet est plus grand que le max degres
                        if (integerListConflitMap.get(o).getList().size() > maxDegres) {
                            index = o.getIndex();
                            maxDegres = integerListConflitMap.get(o).getList().size();
                            maxSat = saturation;

                        }
                    }
                }

            }
            int couleur=0;
            boolean trouve =false;
            Objet v = V.get(index);

            // On cherche la plus petite couleur possible
            while(!trouve){
                trouve=true;
                couleur++;

                if(integerListConflitMap.get(v) != null) {

                    // On parcourt tous les objets triés
                    for (int i : integerListConflitMap.get(v).getList()) {

                        Objet tmp = new Objet(i);

                        // Si C contient tmp
                        if (C.contains(tmp)) {

                            // On vérifie la couleur de tmp
                            if (C.get(C.indexOf(tmp.getColor())).getColor() == couleur) {

                                // si c'est la meme on continue
                                trouve = false;
                            }
                        }
                    }
                }
            }

            // sinon on colorie
            v.colorier(couleur);
            // et on ajoute a C
            C.add(v);
            V.remove(v);
        }

        return C;
    }

    // Méthode de firstFit mais avec une liste d'objets triées par ordre de degres
    public int DsaturWithFFDpacking(ArrayList<Objet> objets,ArbreConflit arbre){


        // liste de la boite de objets
        ArrayList<Boite> boites =new ArrayList<>();
        // première boite
        boites.add(new Boite(taille));

        boolean trouve;

        // On parcourt les objets triés
        for(int i =0;i<objets.size();i++) {

            Objet o = objets.get(i);

            // Si l'objet peut rentrer dans une boite
            if (o.getHauteur() <= taille) {
            trouve=false;
            for(Boite b : boites){

                // si la boite a assez de place pour l'objet
                if(b.getTailleDispo() >= o.getHauteur()){

                    // si il n'y a pas de conflit et que l'objet n'a pas encore trouvé de boite
                    if(!conflit(o.getIndex(),b,arbre) && !trouve){
                        // On ajoute l'objet
                        b.ajouterObjet(o);
                        trouve=true;
                    }

                }
            }

            // Si a la fin de toutes les boites, il n' a pas trouvé de boite
            if(!trouve){
                // On crée une nouvelle boite pour lui
                Boite b = new Boite(taille);
                // et on l'ajoute
                b.ajouterObjet(o);
                boites.add(b);
            }

            }
        }

        return boites.size();
    }

    // Méthode BestFit mais avec une liste d'objet triées par ordre de degres
    public int DsaturWithBFDpacking(ArrayList<Objet> objets,ArbreConflit arbre){

        // Liste des boites
        ArrayList<Boite> boites =new ArrayList<>();
        // Première boite
        boites.add(new Boite(taille));

        boolean trouve;
        Boite meilleurBoite=null;
        int maxDispo=0;

        // On parcourt la liste des objets
        for(int i =0;i<objets.size();i++) {

            Objet o = objets.get(i);

            // Si l'objet peut rentrer dans une boite
            if (o.getHauteur() <= taille) {

                trouve = false;
                maxDispo = -1;
                meilleurBoite = null;

                // on parcourt les boites deja disponible
                for (Boite b : boites) {

                    // si la boite a assez de place pour l'objet et qu'il n'y a pas de conflit
                    if (b.getTailleDispo() >= o.getHauteur() && !conflit(o.getIndex(), b, arbre)) {

                        // Si la taille max de la boite restant est superieur que le max
                        if (b.getTailleDispo() - o.getHauteur() > maxDispo) {
                                maxDispo = b.getTailleDispo() - o.getHauteur();
                                meilleurBoite = b;
                                trouve = true;
                        }

                    }
                }

                // Si on a pas trouvé de boite
                if (maxDispo == -1) {
                    // On crée une nouvelle boite
                    Boite b = new Boite(taille);
                    // Et on ajoute l'objet
                    b.ajouterObjet(o);
                    boites.add(b);

                    // sinon on ajoute dans la boite qu'on a trouvé
                } else {
                    meilleurBoite.ajouterObjet(o);
                }


            }
        }

        return boites.size();
    }
}
