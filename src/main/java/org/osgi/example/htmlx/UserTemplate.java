package org.osgi.example.htmlx;

import com.github.jknack.handlebars.TypeSafeTemplate;

public interface UserTemplate extends TypeSafeTemplate<User> {

    public UserTemplate setAge(int age);

    public UserTemplate setRole(String role);
}
