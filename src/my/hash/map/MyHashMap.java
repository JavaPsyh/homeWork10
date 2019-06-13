package my.hash.map;

import java.util.Objects;

public class MyHashMap<K, V> {

    private final static int DEFAULT_CAPACITY = 16;
    private final static double DEFAULT_LOAD_FACTOR = 0.75;
    private Node<K, V>[] arrayOfNodes;
    private int size = 0;
    private int newLength;

    private void arrayGrower() {
        Node<K, V>[] temp;
        Node<K, V> tempNode;
        int index;
        if (arrayOfNodes == null) {
            arrayOfNodes = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
            newLength = DEFAULT_CAPACITY;
        } else {
            if (size >= newLength * DEFAULT_LOAD_FACTOR) {
                newLength = newLength << 1;
                temp = (Node<K, V>[]) new Node[newLength];
                for (int i = 0; i < arrayOfNodes.length; i++) {
                    if (arrayOfNodes[i] == null) {
                        continue;
                    }
                    if (arrayOfNodes[i].nextNode == null) {
                        index = newIndex(arrayOfNodes[i].key);
                        if(temp[index] == null) {
                            temp[index] = arrayOfNodes[i];
                        }else{
                            arrayOfNodes[i].nextNode = temp[index];
                            temp[index] = arrayOfNodes[i];
                        }
                    } else {
                        tempNode = arrayOfNodes[i];
                        while (tempNode.nextNode != null) {
                            index = newIndex(tempNode.key);
                            temp[index] = tempNode.nextNode;
                            tempNode = tempNode.nextNode;
                        }
                    }
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
        } else {
            temp = arrayOfNodes[index];
            if (key == null && temp.key == null || key.equals(temp.key)) {
                temp.value = value;
                return;
            }
            while (temp.nextNode != null) {
                if (key.equals(temp.nextNode.key)) {
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
        Node<K, V> temp;
        int indexKey = newIndex(key);
        V result = null;
        if (arrayOfNodes[indexKey] == null) {
            System.out.println("Not values with key: " + key);
        } else {
            temp = arrayOfNodes[indexKey];
            if (temp.nextNode == null) {
                result = temp.value;
            } else {
                while (temp.nextNode != null) {
                    if (key == null && temp.key == null || key.equals(temp.key)) {
                        result = temp.value;
                        break;
                    }
                    temp = temp.nextNode;
                    if (temp.nextNode == null && key.equals(temp.key)) {
                            result = temp.value;
                    }else {
                        System.out.println("Not values with key: " + key);
                    }
                }
            }
        }
        return result;
    }

    public int size() {
        return size;
    }

    private static class Node<K, V> {

        private K key;
        private V value;
        private Node<K, V> nextNode = null;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?, ?> node = (Node<?, ?>) o;
        return Objects.equals(this, node.key);
    }

    private <K> int hash(K key) {
        if (key == null) return 0;
        int hashOfKey = key.hashCode();
        return (hashOfKey ^ (hashOfKey >>> 16));
    }

    private <K> int newIndex(K key) {
        int hashIndex = (newLength - 1) & (hash(key));
        return hashIndex;
    }
}
