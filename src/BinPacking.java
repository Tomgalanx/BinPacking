import java.util.*;

public class BinPacking {


    private int taille;

    public BinPacking(int taille){

        this.taille = taille;
    }


    public int FractionalPacking(ArrayList<Objet> list){

        int res=0;

        // Si il y a des objets dans la liste
        if(list.size() != 0){

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


    public int FirstFitDecreasing(ArrayList<Objet> list,ArbreConflit arbre){

        // Liste triée par ordre décroissant
        Collections.sort(list, Collections.reverseOrder());

        ArrayList<Boite> res = new ArrayList<>();
        res.add(new Boite(taille));

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

    private boolean conflit(int indexObjet, Boite tmp, ArbreConflit arbre) {


        boolean reponse=false;
        for(Objet o : tmp.getListe()){

            reponse=arbre.checkConflit(o.getIndex(),indexObjet);

            if(reponse)
                return true;
        }


        return false;


    }


    public int BestFitDecreasingPacking(ArrayList<Objet> list, ArbreConflit arbre){

        // Liste triée par ordre décroissant
        Collections.sort(list, Collections.reverseOrder());

        ArrayList<Boite> res = new ArrayList<>();
        res.add(new Boite(taille));

        for(int i =0;i<list.size();i++) {

            Objet objet = list.get(i);

            // Si l'objet peut rentrer dans une boite
            if (objet.getHauteur() <= taille) {

                // On cherche la premiere boite qui peux accueillir l'objet
                boolean trouve =false;
                int maxBoite=-1;
                Boite boiteMax =null;
                int boite=0;

                while(boite<res.size()){

                    Boite tmp = res.get(boite);
                    int tailleDispo = tmp.getTailleDispo();

                    // Si l'objet peut rentrer dans la boite
                    if(tailleDispo >= objet.getHauteur() && !conflit(objet.getIndex(),tmp,arbre)){

                        if(tailleDispo - objet.getHauteur() > maxBoite){
                            maxBoite = tailleDispo-objet.getHauteur();
                            boiteMax=tmp;
                        }
                    }
                    boite++;
                }

                if(maxBoite != -1){
                    boiteMax.ajouterObjet(objet);
                }
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

        ArrayList<Objet> C = new ArrayList<>();
        ArrayList<Objet> V = new ArrayList<>();
        ArrayList<Objet> V1 = new ArrayList<>();

        Map<Integer, ListConflit> integerListConflitMap = conflit.sortMap();

        HashMap<Integer,Objet> mapObjet = new HashMap<>();

        for(Objet o : U){
            mapObjet.put(o.getIndex(),o);
        }

        for(Map.Entry<Integer, ListConflit> entry : conflit.sortMap().entrySet()) {
            Integer key = entry.getKey();
            V.add(mapObjet.get(key));
            V1.add(mapObjet.get(key));

        }


        Objet premier = V.get(0);
        premier.colorier(1);
        V.remove(premier);
        C.add(premier);

        while(!C.equals(V1)){

            int maxSat = -1;
            int maxDegres=-1;
            int index=0;

            for(Objet o : V){
                int saturation=0;

                if(integerListConflitMap.get(o) != null) {
                    for (int i : integerListConflitMap.get(o).getList()) {
                        if (C.contains(new Objet(i)))
                            saturation++;
                    }
                    if (saturation >= maxSat) {
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

            while(!trouve){
                trouve=true;
                couleur++;

                if(integerListConflitMap.get(v) != null) {
                    for (int i : integerListConflitMap.get(v).getList()) {

                        Objet tmp = new Objet(i);

                        if (C.contains(tmp)) {
                            if (C.get(C.indexOf(tmp.getColor())).getColor() == couleur) {

                                trouve = false;
                                break;

                            }
                        }
                    }
                }
            }

            v.colorier(couleur);
            C.add(v);
            V.remove(v);
        }

        return C;
    }
}
