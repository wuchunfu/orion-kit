package com.orion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ljh15
 * @version 1.0.0
 * @since 2020/6/2 12:32
 */
public class Tests {

    /**
     * LOG
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Tests.class);

    public static void main(String[] args) throws Exception {
        long s = System.currentTimeMillis();

        long e = System.currentTimeMillis();
        System.out.println(e - s);
    }

}
