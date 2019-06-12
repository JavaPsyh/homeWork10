package my.hash.map;

import java.util.Arrays;
import java.util.Objects;

public class MyHashMap<K, V> {


    private final static int DEFAULT_CAPACITY = 16;
    private final static double DEFAULT_LOAD_FACTOR = 0.75;
    private Node<K, V>[] arrayOfNodes;
    private Node<K, V> first;
    private int size = 0;
    private int newLength;


    private void arrayGrower() {
        Node<K, V>[] temp = null;
        if (arrayOfNodes == null){
            arrayOfNodes = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
            newLength = DEFAULT_CAPACITY;
        } else {
            if (size >= newLength * DEFAULT_LOAD_FACTOR) {
                newLength = newLength << 1;
                temp = (Node<K, V>[]) new Node[newLength];
                for (int i = 0; i < size(); i++) {
                    temp[newIndex(arrayOfNodes[i].key)] = arrayOfNodes[i];
                }
                arrayOfNodes = temp;
            }
        }
    }

    public void put(K key, V value) {
        arrayGrower();
        Node<K, V> temp;
        Node<K, V> newNode = new Node<>(key, value);
        int index = newIndex(newNode.key);
        if (arrayOfNodes[index] == null) {
            arrayOfNodes[index] = newNode;
        }else {
                temp = arrayOfNodes[index];
            if (temp.key.equals(key)) {
                temp.value = value;
                return;
            }
                while (temp.nextNode != null){
                    if (temp.nextNode.key.equals(key)) {
                        temp.nextNode.value = value;
                        return;
                    }
                    temp = temp.nextNode;
                }
            newNode.nextNode = arrayOfNodes[index];
            arrayOfNodes[index] = newNode;
        }
        size++;
    }

    public V get(K key) {
        int indexKey = newIndex(key);
        V result = null;
        if (arrayOfNodes[indexKey].nextNode == null) {
            result = arrayOfNodes[indexKey].value;
        }else {

        }

//        for (int i = 0; i < indexForNextPut; i++) {
//            if (key == values[i].getKey()) {
//                result = values[i].getValue();
//            }
//        }
        return result;
    }

    public int size() {
        return size;
    }


    private static class Node<K, V> {

        private K key;
        private V value;
        private Node<K, V> nextNode = null;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node(K key, V value, Node<K, V> nextNode) {
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }

        @Override
        public  boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key);
        }


    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int answer = 42;
        int result = answer * prime;
        result = prime * first.key.hashCode() / answer + result;
        return result;
    }

    private <K> int hash(K key) {
        if (key == null) return 0;
        int hashOfKey = key.hashCode();
        return (hashOfKey ^ (hashOfKey >>> 16));
    }

    private <K> int newIndex (K key){
        if (key == null) return 0;
        System.out.println(hash(key));
        System.out.println(newLength);
        int hashIndex = (newLength - 1) & (hash(key));
//      (n - 1) & hash]
        System.out.println(hashIndex);
    return hashIndex;
    }
}
