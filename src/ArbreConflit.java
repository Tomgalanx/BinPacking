import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ArbreConflit {

    private HashMap<Integer, ListConflit> conflit;


    public ArbreConflit(){

        conflit=new HashMap<>();
    }

    public void ajouterConflit(int key,int value){


        ListConflit res=conflit.get(key);

        if(res == null){
            res = new ListConflit();
            res.ajoute(value);
            conflit.put(key,res);
        }
        else{
            res.ajoute(value);
            conflit.put(key,res);
        }


    }

    public boolean checkConflit(int index1, int index2) {


        ListConflit res1=conflit.get(index1);
        ListConflit res2=conflit.get(index2);

        if(res1 != null){
            ArrayList<Integer> list1=res1.getList();
            if(list1.contains(index2))
                return true;
        }


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


    public Map<Integer, ListConflit> sortMap(){
        Map<Integer, ListConflit> result = conflit.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        /*

        for(Map.Entry<Integer, ListConflit> entry : result.entrySet()) {
            Integer key = entry.getKey();
            ListConflit value = entry.getValue();

            System.out.println("Key "+key+" Value : "+value);

            // do what you have to do here
            // In your case, another loop.
        }

         */


        return result;

   }

}
