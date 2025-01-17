/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.datastr;


/**
 *
 * @author User
 */
import java.io.Serializable;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

public class ConcurrentLinkedHashMap<K extends Serializable, V extends Serializable> 
        extends LinkedHashMap<K, V> implements Serializable {
     
    private static final long serialVersionUID = 1L;
    
    private final Object lock = new Object();

    public ConcurrentLinkedHashMap() {
        super();
    }

    public ConcurrentLinkedHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public ConcurrentLinkedHashMap(int initialCapacity) {
        super(initialCapacity);
    }
    
    public ConcurrentLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    public ConcurrentLinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor, false);
    }

    @Override
    public V put(K key, V value) {
        synchronized (lock) {
            return super.put(key, value);
        }
    }

    @Override
    public V get(Object key) {
        synchronized (lock) {
            return super.get(key);
        }
    }

    @Override
    public V remove(Object key) {
        synchronized (lock) {
            return super.remove(key);
        }
    }

    @Override
    public void clear() {
        synchronized (lock) {
            super.clear();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        synchronized (lock) {
            return super.containsKey(key);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        synchronized (lock) {
            return super.containsValue(value);
        }
    }

    @Override
    public int size() {
        synchronized (lock) {
            return super.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lock) {
            return super.isEmpty();
        }
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        synchronized (lock) {
            return new LinkedHashSet<>(super.entrySet());
        }
    }

    @Override
    public Set<K> keySet() {
        synchronized (lock) {
            return new LinkedHashSet<>(super.keySet());
        }
    }

    @Override
    public Collection<V> values() {
        synchronized (lock) {
            return new ArrayList<>(super.values());
        }
    }

    // Additional thread-safe methods as needed
}
