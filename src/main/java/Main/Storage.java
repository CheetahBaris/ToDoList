package Main;

import Main.model.Stuff;
import Main.model.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {

    private static int currentId = 1;

    private static HashMap<Integer,Stuff> stuffs = new HashMap();

    public static  int addStuff(Stuff stuff){
        int id;

        if(stuffs.size()==0){
            id= 1;
        }else {
            id = stuffs.size()+1;
        }
         stuff.setId(id);
        stuffs.put(id,stuff);
        return  id;
    }

    public static List<Stuff> getAllStuff(){
        ArrayList<Stuff> stuffList = new ArrayList<>();
        stuffList.addAll(stuffs.values());
        return stuffList;
    }

    public static Stuff getStuff(int stuffId){
        if(stuffs.containsKey(stuffId)){
            return stuffs.get(stuffId);
        }
        return null;
    }
    public static Stuff refactorStuff(int stuffId, String name, Type type){
        if(stuffs.containsKey(stuffId)){
            synchronized (stuffs.get(stuffId)){
             stuffs.get(stuffId).setName(name);
            stuffs.get(stuffId).setType(type);
            }

            return stuffs.get(stuffId);

        }else {
            return null;
        }
     }
    public static boolean deleteStuff(int stuffId) {
        if (stuffs.containsKey(stuffId)) {
            synchronized (stuffs.get(stuffId)){
                stuffs.remove(stuffId);
            }
             return true;

        }
            return false;
     }
    public static void deleteAllStuff() {
        stuffs.clear();
     }
}
