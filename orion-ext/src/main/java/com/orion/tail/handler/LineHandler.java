package com.orion.tail.handler;

import com.orion.tail.Tracker;

/**
 * 读取到行的操作
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2020/5/14 16:37
 */
@FunctionalInterface
public interface LineHandler {

    /**
     * 读取到行
     *
     * @param line    行
     * @param index   当前行
     * @param tracker 追踪器
     */
    void readLine(String line, int index, Tracker tracker);

}
