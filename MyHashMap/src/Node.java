public class Node<K,V> {
    K key;
    V value;
    Node<K,V> next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("key=").append(key).append(", value=").append(value).append("}");

        if (next != null) {
            sb.append(", ").append(next);
        }
        return sb.toString();
    }
}
