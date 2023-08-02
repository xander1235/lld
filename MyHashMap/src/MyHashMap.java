import java.util.Arrays;
import java.util.Objects;

public class MyHashMap<K,V> {
    private int capacity = 10;
    private int size = 0;

    public MyHashMap(){

    }

    private Node<K,V>[] nodes = new Node[capacity];

    public void put(K key, V value) {
        Node<K,V> node = new Node<K,V>(key, value);
        int hash = calculateHash(key);
        if (nodes[hash] == null) {
            nodes[hash] = node;
            ++size;
        } else {
            addNodeToBucket(node, hash);
            int length = getLengthOfList(nodes[hash]);
            if (length+1 > capacity) {
                resize();
            }
        }
    }

    public V get(K key) {
        Node<K,V> node = searchInBucket(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    public void remove(K key) {
        int hash = calculateHash(key);
        Node<K,V> root = nodes[hash];
        if (root.key == key) {
            nodes[hash] = root.next;
            --size;
            return;
        }
        while(root.next.key != key) {
            root = root.next;
        }
        Node<K,V> temp = root.next;
        root.next = root.next.next;
        temp.next = null;
        --size;
    }
    private void resize() {
        capacity = 2*capacity;
        Node<K,V>[] newNodes = new Node[capacity];
        for (Node<K,V> node: nodes) {
            Node<K,V> root = node;
            while(root.next != null) {
                put(newNodes, root);
                Node<K,V> temp = root.next;
                root.next = null;
                root = temp;
            }
            put(newNodes, node);
        }
        nodes = newNodes;
    }

    private void put(Node<K,V>[] newNodes, Node<K,V> node) {
        Node<K,V> root = newNodes[calculateHash(node.key)];
        while(root.next != null) {
            root = root.next;
        }
        root.next = node;
    }

    private int getLengthOfList(Node<K,V> root) {
        int len = 0;
        while(root != null) {
            len++;
            root = root.next;
        }
        return len;
    }

    private int calculateHash(K key) {
        return Math.abs(key.hashCode())%(capacity-1);
    }

    public boolean containsKey(K key) {
         return nodes[calculateHash(key)] != null && searchInBucket(key) != null;
    }

    private Node<K,V> searchInBucket(K key) {
        Node<K,V> root = nodes[calculateHash(key)];
        while (root != null) {
            if (root.key == key) {
                return root;
            }
            root = root.next;
        }
        return null;
    }

    private void addNodeToBucket(Node<K,V> node, int hash) {
        Node<K,V> root = nodes[hash];
        while(root != null) {
            if (root.key == node.key) {
                root.value = node.value;
                return;
            }
            if (root.next == null) {
                root.next = node;
                break;
            }
            root = root.next;
        }
        ++size;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "MyHashMap{" + Arrays.toString(Arrays.stream(nodes).filter(Objects::nonNull).toArray()) +
                '}';
    }
}
