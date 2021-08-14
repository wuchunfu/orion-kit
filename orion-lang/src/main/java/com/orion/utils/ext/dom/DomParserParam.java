package com.orion.utils.ext.dom;

/**
 * XML解析参数
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2020/3/24 10:02
 */
class DomParserParam {

    /**
     * dom 名称
     */
    private String name;

    /**
     * dom 下标
     */
    private int index;

    /**
     * dom 属性
     */
    private String key;

    /**
     * dom 属性值
     */
    private String value;

    DomParserParam() {
    }

    public String getName() {
        return name;
    }

    public DomParserParam setName(String name) {
        this.name = name;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public DomParserParam setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getKey() {
        return key;
    }

    public DomParserParam setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public DomParserParam setValue(String value) {
        this.value = value;
        return this;
    }

}