import de.tilman_neumann.util.Multiset;
import de.tilman_neumann.util.Multiset_HashMapImpl;
import de.tilman_neumann.util.Pair;

public class App {
    public static void main(String[] args) {
        Pair<String, Integer> p = new Pair<String, Integer>("hassan", 10);
        Multiset<String> m = new Multiset_HashMapImpl<String>();
        m.add("hassan");
        m.add("hassan", -3);
        System.out.println(m.get("hassann"));
        System.out.println(p.getFirst());
    }
}