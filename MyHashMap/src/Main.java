
public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> mp = new MyHashMap<String, Integer>();
        mp.put("Maxi", 101);
        mp.put("Veerender", 26);
        mp.put("Praveen", 29);
        mp.put("Veerender", 27);
        mp.put("Rajesh", 28);
        mp.remove("Rajesh");
        System.out.println(mp);
        System.out.println(mp.size());
        System.out.println(mp.containsKey("Praveen"));
    }
}