import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

// Classe qui represente les arbre conflit
public class ArbreConflit {

    private HashMap<Integer, ListConflit> conflit;


    public ArbreConflit(){

        conflit=new HashMap<>();
    }

    // Ajoute un conflit a l'arbre
    // Key sommet , Value deuxieme sommet
    public void ajouterConflit(int key,int value){


        // On récupert si il y a deja un conflit
        ListConflit res=conflit.get(key);

        // si il n'y a pas de conflit
        if(res == null){
            // On crée une nouvelle liste
            res = new ListConflit();
            // et on ajoute la valeur
            res.ajoute(value);
            conflit.put(key,res);
        }
        // sinon
        else{
            // On ajoute la valeur a la liste existante
            res.ajoute(value);
            // et on remet la nouvelle liste
            conflit.put(key,res);
        }


    }

    // Méthode qui regarde si il y a un conflit entre deux objets
    public boolean checkConflit(int index1, int index2) {


        ListConflit res1=conflit.get(index1);
        ListConflit res2=conflit.get(index2);

        // Si l'index 1 est en conflit avec l'index2
        if(res1 != null){
            ArrayList<Integer> list1=res1.getList();
            if(list1.contains(index2))
                return true;
        }


        // Si l'index 2 est en conflit avec l'index 2
        if(res2 != null){
            ArrayList<Integer> list2=res2.getList();
            if(list2.contains(index1))
                return true;
        }

        return false;

    }



    public String toString(){

        String tmp="";
        for(Entry<Integer, ListConflit> entry : conflit.entrySet()) {

            Integer key = entry.getKey();
            ListConflit value = entry.getValue();

            tmp+=key+"  ";
            for(Integer i : value.getList()){
                tmp+=i+" ";
            }

            tmp+="\n";

        }

        return tmp;


    }



    // Méthode pour générer une map triée par ordre de degres
    public Map<Integer, ListConflit> sortMap(){
        Map<Integer, ListConflit> result = conflit.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        return result;

   }

}
