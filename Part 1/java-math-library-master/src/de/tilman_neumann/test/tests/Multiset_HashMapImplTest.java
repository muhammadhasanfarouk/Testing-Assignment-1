package de.tilman_neumann.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import de.tilman_neumann.util.Multiset;
import de.tilman_neumann.util.Multiset_HashMapImpl;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Multiset_HashMapImplTest {

    //region Method 1: add(T entry)
    @Test
    void testAdd_TC1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");

        ms.add("abc");

        assertEquals(2, ms.get("abc"));
        assertEquals(1, ms.get("efg"));
    }

    @Test
    void testAdd_TC2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("efg");

        ms.add("abc");

        assertEquals(1, ms.get("abc"));
        assertEquals(1, ms.get("efg"));
    }

    @Test
    void testAdd_TC3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("abc");
        ms.add("efg");

        ms.add("abc");

        assertEquals(3, ms.get("abc"));
        assertEquals(1, ms.get("efg"));
    }

    @Test
    void testAdd_TC4() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

        ms.add("abc");

        assertEquals(1, ms.get("abc"));
    }
    //endregion

    //region Method 2: add(T entry, int mult)
    @Test
    void testAddWithMult_TC1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("abc");
        ms.add("efg");

        ms.add("abc", 2);

        assertEquals(4, ms.get("abc"));
        assertEquals(1, ms.get("efg"));
    }

    @Test
    void testAddWithMult_TC2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");

        ms.add("abc", -1);

        assertEquals(1, ms.get("abc")); // unchanged
        assertEquals(1, ms.get("efg"));
    }

    @Test
    void testAddWithMult_TC3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("abc");
        ms.add("efg");

        ms.add("abc", 0);

        assertEquals(2, ms.get("abc"));
        assertEquals(1, ms.get("efg"));
    }

    @Test
    void testAddWithMult_TC4() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("efg");

        ms.add("abc", 1);

        assertEquals(1, ms.get("abc"));
        assertEquals(1, ms.get("efg"));
    }
    //endregion

    //region Method 3: addAll(Multiset<T>)
    @Test
    public void addAllMultiset_T1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");
        ms.add("abc");

        Multiset_HashMapImpl<String> values = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("hij");

        ms.addAll(values);

        assertEquals(3, ms.size());
        assertEquals(Integer.valueOf(3), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
        assertEquals(Integer.valueOf(1), ms.get("hij"));
    }

    @Test
    public void addAllMultiset_T2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");

        Multiset_HashMapImpl<String> values = new Multiset_HashMapImpl<>();

        ms.addAll(values);

        assertEquals(2, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
    }

    @Test
    public void addAllMultiset_T3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");

        Multiset_HashMapImpl<String> values = null;

        assertEquals(2, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
    }

    @Test
    public void addAllMultiset_T4() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

        Multiset_HashMapImpl<String> values = new Multiset_HashMapImpl<>();
        ms.add("abc");

        ms.addAll(values);

        assertEquals(1, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
    }
    //endregion

    //region Method 4: addAll(Collection<T>)
    @Test
    public void addAllCollection_T1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");
        ms.add("abc");
        Collection<String> values = Arrays.asList("abc", "hij");

        ms.addAll(values);

        assertEquals(3, ms.size());
        assertEquals(Integer.valueOf(3), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
        assertEquals(Integer.valueOf(1), ms.get("hij"));
    }

    @Test
    public void addAllCollection_T2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");
        Collection<String> values = new ArrayList<>();

        ms.addAll(values);

        assertEquals(2, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
    }

    @Test
    public void addAllCollection_T3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");

        ms.addAll((Collection<String>) null);

        assertEquals(2, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
    }

    @Test
    public void addAllCollection_T4() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

        Collection<String> values = List.of("abc");
        ms.addAll(values);

        assertEquals(1, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
    }
    //endregion

    //region Method 5: addAll(T[])
    @Test
    public void addAllArray_T1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("abc");
        ms.add("efg");

        String[] values = {"abc", "hij"};
        ms.addAll(values);

        assertEquals(3, ms.size());
        assertEquals(Integer.valueOf(3), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
        assertEquals(Integer.valueOf(1), ms.get("hij"));
    }

    @Test
    public void addAllArray_T2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");

        String[] values = {};

        ms.addAll(values);

        assertEquals(2, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));

    }

    @Test
    public void addAllArray_T3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("efg");
        ms.addAll((String[]) null);

        assertEquals(2, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
        assertEquals(Integer.valueOf(1), ms.get("efg"));
    }

    @Test
    public void addAllArray_T4() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

        String[] values = {"abc"};
        ms.addAll(values);

        assertEquals(1, ms.size());
        assertEquals(Integer.valueOf(1), ms.get("abc"));
    }
    //endregion

    //region Method 6: remove(Object key)
    @Test
    void testRemove_TC1() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc",2); m.add("efg");

        Integer r = m.remove("abc");
        assertEquals(2, r);
        assertEquals(2, m.size());
        assertEquals(1, m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemove_TC2() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc"); m.add("efg");

        Integer r = m.remove("abc");
        assertEquals(1, r);
        assertEquals(1, m.size());
        assertNull(m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemove_TC3() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("efg");

        Integer r = m.remove("abc");
        assertNull(r);
        assertEquals(1, m.size());
        assertEquals(1, m.get("efg"));
    }
    //endregion

    //region Method 7: remove(T key, int mult)
    @Test
    void testRemoveWithMult_TC1() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc",3); m.add("efg");

        Integer r = m.remove("abc", 2);
        assertEquals(3, r);
        assertEquals(2, m.size());
        assertEquals(1, m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemoveWithMult_TC2() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc"); m.add("efg");

        Integer r = m.remove("abc", 1);
        assertEquals(1, r);
        assertEquals(1, m.size());
        assertNull(m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemoveWithMult_TC3() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc",2); m.add("efg");

        Integer r = m.remove("abc", 3);
        assertEquals(2, r);
        assertEquals(1, m.size());
        assertNull(m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemoveWithMult_TC4() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("efg");

        Integer r = m.remove("abc", 2);
        assertEquals(0, r);
        assertEquals(1, m.size());
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemoveAllWithMult_TC5() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc",2); m.add("efg");

        Integer r = m.remove("abc", -1);
        assertEquals(2, r);
        assertEquals(2, m.size());
        assertEquals(2, m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemoveAllWithMult_TC6() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc",2); m.add("efg");

        Integer r = m.remove("abc", 0);
        assertEquals(2, r);
        assertEquals(2, m.size());
        assertEquals(2, m.get("abc"));
        assertEquals(1, m.get("efg"));
    }
    //endregion

    //region Method 8: removeAll(T key)
    @Test
    void testRemoveAll_TC1() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc",2); m.add("efg");

        Integer r = m.removeAll("abc");
        assertEquals(2, r);
        assertEquals(1, m.size());
        assertNull(m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemoveAll_TC2() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("efg");

        Integer r = m.removeAll("abc");
        assertEquals(0, r);
        assertEquals(1, m.size());
        assertNull(m.get("abc"));
        assertEquals(1, m.get("efg"));
    }

    @Test
    void testRemoveAll_TC3() {
        Multiset_HashMapImpl<String> m = new Multiset_HashMapImpl<>();
        m.add("abc"); m.add("efg");

        Integer r = m.removeAll("abc");
        assertEquals(1, r);
        assertEquals(1, m.size());
        assertNull(m.get("abc"));
        assertEquals(1, m.get("efg"));
    }
    //endregion

    //region Method 9: intersect(Multiset<T>)
    @Test
    public void intersect_T1() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc", 2);
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("abc", 2);
        ms2.add("hij");

        Multiset<String> result = ms1.intersect(ms2);

        assertEquals(2, result.get("abc"));
        assertNull(result.get("efg"));
        assertNull(result.get("hij"));
    }

    @Test
    public void intersect_T2() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("abc", 2);
        ms2.add("efg");

        Multiset<String> result = ms1.intersect(ms2);

        assertEquals(1, result.get("abc"));
        assertEquals(1, result.get("efg"));
    }

    @Test
    public void intersect_T3() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc", 2);
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("abc");

        Multiset<String> result = ms1.intersect(ms2);

        assertEquals(1, result.get("abc"));
        assertNull(result.get("efg"));
    }

    @Test
    public void intersect_T4() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");

        Multiset_HashMapImpl<String> ms2 = null;

        Multiset<String> result = ms1.intersect(ms2);

        assertEquals(0,result.size());
    }

    @Test
    public void intersect_T5() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();

        Multiset<String> result = ms1.intersect(ms2);

        assertEquals(0,result.size());
    }
    //endregion

    //region Method 10: totalCount()
    @Test
    void testTotalCount_TC1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        ms.add("abc");
        ms.add("efg");

        assertEquals(3, ms.totalCount());
    }

    @Test
    void testTotalCount_TC2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

        assertEquals(0, ms.totalCount());
    }

    @Test
    void testTotalCount_TC3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");

        assertEquals(1, ms.totalCount());
    }
    //endregion

    //region Method 11: toList()
    @Test
    public void toList_T1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc", 2);
        ms.add("efg");

        List<String> list = ms.toList();

        assertEquals(3, list.size());
        assertEquals(2, Collections.frequency(list, "abc"));
        assertEquals(1, Collections.frequency(list, "efg"));
    }

    @Test
    public void toList_T2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

        List<String> list = ms.toList();

        assertEquals(0, list.size());
    }

    @Test
    public void toList_T3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        List<String> list = ms.toList();

        assertEquals(1, list.size());
        assertEquals(1, Collections.frequency(list, "abc"));
    }
    //endregion

    //region Method 12: toString()
    @Test
    public void toString_T1() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc", 2);
        ms.add("efg");

        String result = ms.toString();

        assertTrue(result.contains("abc^2"));
        assertTrue(result.contains("efg"));
    }

    @Test
    public void toString_T2() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

        String result = ms.toString();

        assertEquals("{}", result);
    }

    @Test
    public void toString_T3() {
        Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
        ms.add("abc");
        String result = ms.toString();

        assertTrue(result.contains("abc"));
    }
    //endregion

    //region Method 13: equals(Object o)
    @Test
    public void equals_T1() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc", 2);
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("efg");
        ms2.add("abc", 2);

        assertTrue(ms1.equals(ms2));
    }

    @Test
    public void equals_T2() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("abc");
        ms2.add("efg");

        assertFalse(ms1.equals(ms2));
    }

    @Test
    public void equals_T3(){
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");
        ms1.add("efg");

        String[] values = {"abc", "efg"};

        assertFalse(ms1.equals(values));

    }

    @Test
    public void equals_T4() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");

        assertFalse(ms1.equals(null));
    }

    @Test
    public void equals_T5() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc", 2);
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("abc");
        ms2.add("efg");

        assertFalse(ms1.equals(ms2));
    }

    @Test
    public void equals_T6() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("abc", 2);
        ms2.add("efg");

        assertFalse(ms1.equals(ms2));
    }

    @Test
    public void equals_TC7() {
        Multiset_HashMapImpl<String> ms1 = new Multiset_HashMapImpl<>();
        ms1.add("abc");
        ms1.add("efg");

        Multiset_HashMapImpl<String> ms2 = new Multiset_HashMapImpl<>();
        ms2.add("abc");

        assertFalse(ms1.equals(ms2));
    }
    //endregion
}
