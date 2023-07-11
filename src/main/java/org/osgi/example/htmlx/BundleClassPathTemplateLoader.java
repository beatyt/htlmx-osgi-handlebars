package org.osgi.example.htmlx;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import java.net.URL;

public class BundleClassPathTemplateLoader extends ClassPathTemplateLoader {
    @Override
    protected URL getResource(String location) {
        return this.getClass().getClassLoader().getResource(location);
    }
}
