package com.orion.lang;

import java.io.Serializable;

/**
 * void
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/2/6 23:15
 */
public class Void implements Serializable {

    private static final long serialVersionUID = 1079019592799661L;

    public static final Void VALUE = new Void();

    private Void() {
    }

}
