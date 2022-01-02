package coursera._05_binary_search_tree;

public class BinarySearchTree<K extends Comparable<K>, V> {

    Node<K, V> root;

    public V get(K k) {
        return get(root, k);
    }

    public V get(Node<K, V> node, K k) {
        if (node == null) return null;
        int cmp = k.compareTo(node.key);
        if (cmp == 0) return node.value;
        if (cmp < 0) return get(node.left, k);
        else return get(node.right, k);
    }

    public void put(K k, V v) {
        put(root, k, v);
    }

    private void put(Node<K, V> node, K k, V v) {
        if (node == null) {
            node = new Node<>();
            node.key = k;
            node.value = v;
        }
        int cmp = node.key.compareTo(k);
        if (cmp == 0) node.value = v;
        else if (cmp < 0) put(node.left, k, v);
        else put(node.right, k, v);
    }


    public void deleteMin() {
        // TODO - vado fino al nodo piu' a sx e lo cancello
    }

    public void deleteMax() {
        // TODO - vado fino al nodo piu' a destra e lo cancello
    }

    public void rank() {

    }

    public void floor() {

    }

    public void treeTraverse() {
        // preorder - the visit of the node it's first than the visit of the children
        // inorder - first visit the left, then the middle, then the right
        // postorder - first visit the left, then the right and then the middle
    }


    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
    }
}
