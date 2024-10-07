import java.util.LinkedList;
import java.util.Objects;

public class HashTable<K, V> {
    private final int[] sizes = new int[]{5, 11, 23, 47, 97, 193, 389, 769, 1543, 3072, 3079, 12289, 24593, 49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612736, 2147483629};
    private int sizes_index = 0;
    private static final float LOAD_FACTOR = 0.75f; // Коэффициент загрузки
    private LinkedList<Entry<K, V>>[] table;
    private int count;

    @SuppressWarnings("unchecked")
    public HashTable() {
        table = new LinkedList[sizes[sizes_index]];
        count = 0;
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {return key;}
        public V getValue() {return value;}
        public void setValue(V value) {this.value = value;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entry)) return false;
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key);
        }
    }

    private int hash(K key) {
        return Math.abs(Objects.hashCode(key)) % sizes[sizes_index];
    }

    public void put(K key, V value) {
        if ((float) count / table.length >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        table[index].add(new Entry<>(key, value));
        count++;
    }

    private void resize() {
        @SuppressWarnings("unchecked")
        LinkedList<Entry<K, V>>[] newTable = new LinkedList[sizes[++sizes_index]];

        for (LinkedList<Entry<K, V>> chain : table) {
            if (chain != null) {
                for (Entry<K, V> entry : chain) {
                    int newIndex = hash(entry.getKey());
                    if (newTable[newIndex] == null) {
                        newTable[newIndex] = new LinkedList<>();
                    }
                    newTable[newIndex].add(entry);
                }
            }
        }

        table = newTable;
    }

    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> chain = table[index];

        if (chain != null) {
            for (Entry<K, V> entry : chain) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> chain = table[index];

        if (chain != null) {
            for (Entry<K, V> entry : chain) {
                if (entry.getKey().equals(key)) {
                    V value = entry.getValue();
                    chain.remove(entry);
                    count--;
                    return value;
                }
            }
        }

        return null;
    }

    public int length() {
        return count;
    }

    public int size(){
        return sizes[sizes_index];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean firstEntry = true;
        for (LinkedList<Entry<K, V>> chain : table) {
            if (chain != null) {
                for (Entry<K, V> entry : chain) {
                    if (!firstEntry) {
                        sb.append(", ");
                    }
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                    firstEntry = false;
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        HashTable<String, Integer> hashTable = new HashTable<>();
        
        System.out.println("\n Table size: " + hashTable.size());

        hashTable.put("apple", 1);
        hashTable.put("banana", 2);
        hashTable.put("orange", 3);
        hashTable.put("mango", 4);
        hashTable.put("grape", 5);
        hashTable.put("peach", 6);
        System.out.println("Table size after resize: " + hashTable.size() + "\n");

        System.out.println("Table content: " + hashTable);
        System.out.println("Table length: " + hashTable.length() + "\n");

        hashTable.remove("banana");
        hashTable.put("cherry", 7);
        hashTable.put("tomato", 10);
        System.out.println("After adding 'cherry', 'tomato' and delete 'banana', table content: " + hashTable);
        System.out.println("Table length: " + hashTable.length());
    }
}
