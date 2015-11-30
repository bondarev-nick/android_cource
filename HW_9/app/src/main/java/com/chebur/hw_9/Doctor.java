package com.chebur.hw_9;

/**
 * Created by nick on 29.11.2015.
 */
public class Doctor {
    private int _id;
    private String _name;
    private int _age;

    public Doctor (String name, int age) {
        set_name(name);
        set_age(age);
    }

    public Doctor () { }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }
}