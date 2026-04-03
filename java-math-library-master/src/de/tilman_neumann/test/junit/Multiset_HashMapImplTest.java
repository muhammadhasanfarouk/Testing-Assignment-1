package de.tilman_neumann.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import de.tilman_neumann.util.Multiset_HashMapImpl;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Multiset_HashMapImplTest {




        // =========================
        // Method 1: add(T entry)
        // =========================

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
        }

        @Test
        void testAdd_TC4() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();

            ms.add("abc");

            assertEquals(1, ms.get("abc"));
        }

        // =========================
        // Method 2: add(T entry, int mult)
        // =========================

        @Test
        void testAddWithMult_TC1() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("abc");
            ms.add("efg");

            ms.add("abc", 2);

            assertEquals(4, ms.get("abc"));
        }

        @Test
        void testAddWithMult_TC2() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("efg");

            ms.add("abc", -1);

            assertEquals(1, ms.get("abc")); // unchanged
        }

        @Test
        void testAddWithMult_TC3() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("abc");
            ms.add("efg");

            ms.add("abc", 0);

            assertEquals(2, ms.get("abc"));
        }

        @Test
        void testAddWithMult_TC4() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("efg");

            ms.add("abc", 1);

            assertEquals(1, ms.get("abc"));
        }

        // =========================
        // Method 6: remove(Object key)
        // =========================

        @Test
        void testRemove_TC1() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("abc");
            ms.add("efg");

            ms.remove("abc");

            assertEquals(1, ms.get("abc"));
        }

        @Test
        void testRemove_TC2() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("efg");

            ms.remove("abc");

            assertNull(ms.get("abc"));
        }

        @Test
        void testRemove_TC3() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("efg");

            ms.remove("abc");

            assertEquals(1, ms.get("efg"));
            assertNull(ms.get("abc"));
        }

        // =========================
        // Method 7: remove(T key, int mult)
        // =========================

        @Test
        void testRemoveAllWithMult_TC1() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("abc");
            ms.add("abc");
            ms.add("efg");

            ms.remove("abc", 2);

            assertEquals(1, ms.get("abc"));
        }

        @Test
        void testRemoveAllWithMult_TC2() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("efg");

            ms.remove("abc", 1);

            assertNull(ms.get("abc"));
        }

        @Test
        void testRemoveAllWithMult_TC3() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("abc");
            ms.add("efg");

            ms.remove("abc", 3);

            assertNull(ms.get("abc"));
        }

        @Test
        void testRemoveAllWithMult_TC4() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("efg");

            ms.remove("abc", 2);

            assertEquals(1, ms.get("efg"));
        }

        @Test
        void testRemoveAllWithMult_TC5() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("abc");
            ms.add("efg");

            ms.remove("abc", -1);

            assertEquals(2, ms.get("abc"));
        }

        @Test
        void testRemoveAllWithMult_TC6() {
            Multiset_HashMapImpl<String> ms = new Multiset_HashMapImpl<>();
            ms.add("abc");
            ms.add("abc");
            ms.add("efg");

            ms.remove("abc", 0);

            assertEquals(2, ms.get("abc"));
        }

        // =========================
        // Method 10: totalCount()
        // =========================

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
    }