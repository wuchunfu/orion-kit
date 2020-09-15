package com.orion.utils.reflect;

import com.orion.lang.collect.ConvertHashMap;
import com.orion.utils.Arrays1;
import com.orion.utils.Valid;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean map 根据getter方法获取
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/9/9 1:57
 */
public class BeanMap<T> extends ConvertHashMap<String, Object> {

    /**
     * getter方法缓存
     */
    private static final Map<Class<?>, List<Method>> GETTER_METHOD_CACHE = new ConcurrentHashMap<>();

    /**
     * 对象
     */
    private T o;

    /**
     * 忽略的字段
     */
    private String[] ignoreFields;

    /**
     * 是否添加空字段
     */
    private boolean addNull;

    public BeanMap(T o, String... ignoreFields) {
        this(o, false, ignoreFields);
    }

    public BeanMap(T o, boolean addNull, String... ignoreFields) {
        Valid.notNull(o, "object is null");
        this.o = o;
        this.addNull = addNull;
        this.ignoreFields = ignoreFields;
        this.parseClass();
    }

    /**
     * 解析getterMethod
     */
    private void parseClass() {
        Class<?> clazz = o.getClass();
        List<Method> methods = GETTER_METHOD_CACHE.get(clazz);
        if (methods == null) {
            methods = Methods.getAllGetterMethod(clazz);
            GETTER_METHOD_CACHE.put(clazz, methods);
        }
        this.invokeGetter(methods);
    }

    /**
     * 调用getter
     *
     * @param methods getterMethods
     */
    private void invokeGetter(List<Method> methods) {
        for (Method method : methods) {
            String fieldName = Fields.getFieldNameByMethodName(method.getName());
            if (this.isIgnoreField(fieldName)) {
                continue;
            }
            Object value = Methods.invokeMethod(this.o, method);
            if (value == null) {
                if (addNull) {
                    put(fieldName, null);
                }
            } else {
                put(fieldName, value);
            }
        }
    }

    /**
     * 是否为忽略的字段
     *
     * @param field field
     * @return true忽略
     */
    private boolean isIgnoreField(String field) {
        if (Arrays1.isEmpty(ignoreFields)) {
            return false;
        }
        for (String ignoreField : ignoreFields) {
            if (field.equals(ignoreField)) {
                return true;
            }
        }
        return false;
    }

    public T getValue() {
        return o;
    }

}
