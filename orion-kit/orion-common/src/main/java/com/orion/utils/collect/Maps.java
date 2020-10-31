package com.orion.utils.collect;

import com.orion.lang.collect.ConcurrentReferenceHashMap;
import com.orion.lang.collect.EmptyMap;
import com.orion.lang.collect.MutableHashMap;
import com.orion.lang.collect.SingletonMap;
import com.orion.lang.wrapper.Args;
import com.orion.lang.wrapper.Pair;
import com.orion.utils.Arrays1;
import com.orion.utils.Randoms;
import com.orion.utils.Valid;

import java.util.Collections;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Map工具类
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/4/29 16:47
 */
@SuppressWarnings("ALL")
public class Maps {

    private Maps() {
    }

    // --------------- new ---------------

    public static <K, V> Map<K, V> newMap() {
        return new HashMap<>(16);
    }

    public static <K, V> Map<K, V> newMap(int capacity) {
        return new HashMap<>(capacity);
    }

    public static <K, V> Map<K, V> newMap(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return new HashMap<>(16);
        }
        return new HashMap<>(m);
    }

    public static <K, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    public static <K, V> TreeMap<K, V> newTreeMap(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return new TreeMap<>();
        }
        return new TreeMap<>(m);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedMap() {
        return new LinkedHashMap<>(16);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedMap(int capacity) {
        return new LinkedHashMap<>(capacity);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedMap(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return new LinkedHashMap<>(16);
        }
        return new LinkedHashMap<>(m);
    }

    public static <K, V> ConcurrentHashMap<K, V> newCurrentMap() {
        return new ConcurrentHashMap<>(16);
    }

    public static <K, V> ConcurrentHashMap<K, V> newCurrentMap(int capacity) {
        return new ConcurrentHashMap<>(capacity);
    }

    public static <K, V> ConcurrentHashMap<K, V> newCurrentMap(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return new ConcurrentHashMap<>(16);
        }
        return new ConcurrentHashMap<>(m);
    }

    public static <K, V> MutableHashMap<K, V> newMutableMap() {
        return new MutableHashMap<>(16);
    }

    public static <K, V> MutableHashMap<K, V> newMutableMap(int capacity) {
        return new MutableHashMap<>(capacity);
    }

    public static <K, V> MutableHashMap<K, V> newMutableMap(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return new MutableHashMap<>(16);
        }
        return new MutableHashMap<>(m);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<>(16);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int capacity) {
        return new IdentityHashMap<>(capacity);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return new IdentityHashMap<>(16);
        }
        return new IdentityHashMap<>(m);
    }

    public static <K, V> WeakHashMap<K, V> newWeakHashMap() {
        return new WeakHashMap<>(16);
    }

    public static <K, V> WeakHashMap<K, V> newWeakHashMap(int capacity) {
        return new WeakHashMap<>(capacity);
    }

    public static <K, V> WeakHashMap<K, V> newWeakHashMap(Map<? extends K, ? extends V> m) {
        if (m == null) {
            return new WeakHashMap<>(16);
        }
        return new WeakHashMap<>(m);
    }

    public static <K, V> ConcurrentReferenceHashMap<K, V> newConcurrentWeakHashMap() {
        return new ConcurrentReferenceHashMap<>(16, ConcurrentReferenceHashMap.ReferenceType.WEAK);
    }

    public static <K, V> ConcurrentReferenceHashMap<K, V> newConcurrentWeakHashMap(int capacity) {
        return new ConcurrentReferenceHashMap<>(capacity, ConcurrentReferenceHashMap.ReferenceType.WEAK);
    }

    public static <K, V> ConcurrentReferenceHashMap<K, V> newConcurrentSoftHashMap() {
        return new ConcurrentReferenceHashMap<>(16, ConcurrentReferenceHashMap.ReferenceType.SOFT);
    }

    public static <K, V> ConcurrentReferenceHashMap<K, V> newConcurrentSoftHashMap(int capacity) {
        return new ConcurrentReferenceHashMap<>(capacity, ConcurrentReferenceHashMap.ReferenceType.SOFT);
    }

    public static <K, V> Map<K, V> newSynchronizedMap() {
        return Collections.synchronizedMap(new HashMap<>(16));
    }

    public static <K, V> Map<K, V> newSynchronizedMap(Map<K, V> m) {
        if (m == null) {
            return Collections.synchronizedMap(new HashMap<>(16));
        }
        return Collections.synchronizedMap(m);
    }

    public static <K, V> Map<K, V> singleton(K k, V v) {
        return new SingletonMap<>(k, v);
    }

    public static <K, V> Map<K, V> empty() {
        return (Map<K, V>) EmptyMap.EMPTY;
    }

    // --------------- function ---------------

    public static <K, V> Map<K, V> def(Map<K, V> map) {
        return isEmpty(map) ? new HashMap<>() : map;
    }

    public static <K, V> Map<K, V> def(Map<K, V> map, Map<K, V> def) {
        return isEmpty(map) ? def : map;
    }

    public static <K, V> Map<K, V> of(Pair<K, V>... entries) {
        int len = Arrays1.length(entries);
        if (len == 0) {
            return new HashMap<>(16);
        }
        Map<K, V> map = new HashMap<>(len * 4 / 3);
        for (int i = 0; i < len; i++) {
            map.put(entries[i].getKey(), entries[i].getValue());
        }
        return map;
    }

    public static <K, V> Map<K, V> of(Args.Entry<K, V>... entries) {
        int len = Arrays1.length(entries);
        if (len == 0) {
            return new HashMap<>(16);
        }
        Map<K, V> map = new HashMap<>(len * 4 / 3);
        for (int i = 0; i < len; i++) {
            map.put(entries[i].getKey(), entries[i].getValue());
        }
        return map;
    }

    public static <K, V> Map<K, V> of(Object... kv) {
        if (kv == null) {
            return new HashMap<>(16);
        }
        int c = kv.length / 2;
        int hn = kv.length % 2;
        Map<K, V> res = new HashMap<>(c * 4 / 3);
        for (int i = 0; i < c; i++) {
            res.put(((K) kv[i * 2]), ((V) kv[i * 2 + 1]));
        }
        if (hn == 1) {
            res.put(((K) kv[c * 2]), null);
        }
        return res;
    }

    public static <K1, V1, K2, V2> Map<K2, V2> map(Map<K1, V1> map, Function<K1, K2> kf, Function<V1, V2> vf) {
        Valid.notNull(kf, "key convert function is null");
        Valid.notNull(vf, "value convert function is null");
        int size = size(map);
        if (size == 0) {
            return new HashMap<>(16);
        }
        Map<K2, V2> res = new HashMap<>(size * 4 / 3);
        map.forEach((k, v) -> {
            res.put(kf.apply(k), vf.apply(v));
        });
        return res;
    }

    /**
     * 合并map
     *
     * @param map 合并到的map
     * @param ms  需要合并的map
     * @param <K> ignore
     * @param <V> ignore
     * @return 合并后的map
     */
    public static <K, V> Map<K, V> merge(Map<K, V> map, Map<K, V>... ms) {
        if (map == null) {
            map = new HashMap<>(16);
        }
        if (ms == null) {
            return map;
        }
        for (Map<K, V> m : ms) {
            map.putAll(m);
        }
        return map;
    }

    /**
     * map长度
     *
     * @param m map
     * @return 长度
     */
    public static int size(Map m) {
        return m == null ? 0 : m.size();
    }

    /**
     * map是否为空
     *
     * @param m map
     * @return true为空
     */
    public static boolean isEmpty(Map m) {
        return size(m) == 0;
    }

    /**
     * map是否不为空
     *
     * @param m map
     * @return true不为空
     */
    public static boolean isNotEmpty(Map m) {
        return !isEmpty(m);
    }

    /**
     * map是否全为空
     *
     * @param ms map
     * @return true全为空
     */
    public static boolean isAllEmpty(Map... ms) {
        if (ms == null) {
            return true;
        }
        for (Map m : ms) {
            if (!isEmpty(m)) {
                return false;
            }
        }
        return true;
    }

    /**
     * map是否全不为空
     *
     * @param ms map
     * @return true全不为空, 参数为空false
     */
    public static boolean isNoneEmpty(Map... ms) {
        if (ms == null) {
            return false;
        }
        for (Map m : ms) {
            if (isEmpty(m)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 多层转单层
     *
     * @param map 多层map
     * @return 单层map
     */
    public static Map<String, Object> multiToSingleMap(Map<String, ?> map) {
        Map<String, Object> result = new LinkedHashMap<>();
        multiToSingleMap(map, "", result);
        return result;
    }

    private static void multiToSingleMap(Map<String, ?> map, String nowKey, Map<String, Object> result) {
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                multiToSingleMap(((Map<String, ?>) value), nowKey + key + ".", result);
            } else {
                result.put(nowKey + key, value);
            }
        }
    }

    /**
     * 从map随机获取一个元素
     *
     * @param map map
     * @param <K> ignore
     * @param <V> ignore
     * @return 元素
     */
    public static <K, V> Pair<K, V> random(Map<K, V> map) {
        int size = size(map);
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return Pair.toMapEntry(map.entrySet().iterator().next());
        } else {
            Object randomKey = map.keySet().toArray()[Randoms.RANDOM.nextInt(size)];
            return new Pair(randomKey, map.get(randomKey));
        }
    }

    /**
     * 将指定元素装配给集合
     *
     * @param map   集合
     * @param value 元素
     * @param <K>   ignore
     * @param <V>   ignore
     */
    public static <K, V> void fill(Map<K, V> map, V value) {
        if (!isEmpty(map)) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                entry.setValue(value);
            }
        }
    }

    public static <K, V> V get(Map<K, V> map, K k) {
        int size = size(map);
        if (size == 0) {
            return null;
        }
        return map.get(k);
    }

    public static <K, V> void set(Map<K, V> map, K k, V v) {
        sets(map, k, v, true);
    }

    public static <K, V> void set(Map<K, V> map, K k, V v, boolean force) {
        sets(map, k, v, force);
    }

    public static <K, V> void setex(Map<K, V> map, K k, V v) {
        sets(map, k, v, false);
    }

    public static <K, V> V getSet(Map<K, V> map, K k, V v) {
        return sets(map, k, v, true);
    }

    private static <K, V> V sets(Map<K, V> map, K k, V v, boolean force) {
        int size = size(map);
        if (size == 0) {
            return null;
        }
        V o = map.get(k);
        if (force) {
            map.put(k, v);
            return o;
        } else {
            if (o == null) {
                map.put(k, v);
            }
            return null;
        }
    }

    /**
     * 获取第一个元素
     *
     * @param m   集合
     * @param <K> ignore
     * @param <V> ignore
     * @return 第一个元素
     */
    public static <K, V> Map.Entry<K, V> getFirst(Map<K, V> m) {
        if (size(m) == 0) {
            return null;
        }
        return Lists.getFirst(m.entrySet());
    }

    /**
     * 获取最后一个元素
     *
     * @param m   集合
     * @param <K> ignore
     * @param <V> ignore
     * @return 最后一个元素
     */
    public static <K, V> Map.Entry<K, V> getLast(Map<K, V> m) {
        if (size(m) == 0) {
            return null;
        }
        return Lists.getLast(m.entrySet());
    }

}
