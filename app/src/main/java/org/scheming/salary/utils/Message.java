package org.scheming.salary.utils;

/**
 * Created by Scheming on 2015/9/27.
 */
public class Message {
    public final int what;
    public final Class clazz;

    public Message(int what, Class clazz) {
        this.what = what;
        this.clazz = clazz;
    }
}
