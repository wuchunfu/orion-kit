package com.orion.lang.collect;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 固长队列
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/11/6 11:30
 */
public class LimitDeque<E> extends ConcurrentLinkedDeque<E> {

    private int limit;

    public LimitDeque(int limit) {
        if (limit == 0) {
            this.limit = 10;
        } else {
            this.limit = limit;
        }
    }

    @Override
    public boolean offerFirst(E s) {
        if (full()) {
            pollLast();
        }
        return super.offerFirst(s);
    }

    @Override
    public boolean offerLast(E s) {
        if (full()) {
            pollFirst();
        }
        return super.offerLast(s);
    }

    public boolean full() {
        return size() > limit;
    }

}