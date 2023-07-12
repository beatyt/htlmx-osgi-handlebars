package org.osgi.example.htmlx;

import java.util.ArrayList;
import java.util.List;

public class User {
    int id;
    String name;
    int age;

    List<User> users;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.users = new ArrayList<>();
        this.users.add(this);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
