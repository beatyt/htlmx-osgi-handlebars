package org.osgi.example.htmlx;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardResource;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.TimeZone;

@Component(service = Upper.class)
@JaxrsResource
@HttpWhiteboardResource(pattern = "/quickstart/*", prefix = "static")
@org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect("(osgi.http.whiteboard.context.name=org.osgi.service.http)")
public class Upper {

    @Activate
    public void start() throws IOException {
        System.out.println("Started");
    }

    @Path("rest/upper/{param}")
    @GET
    public String toUpper(@PathParam("param") String param) {
        return param.toUpperCase();
    }

    @Path("clicked")
    @POST
    public String click(@PathParam("param") String param) throws IOException {
        System.out.println("Clicked");

        TemplateLoader loader = new BundleClassPathTemplateLoader();
        loader.setPrefix("templates");
//        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("mytemplate");

        System.out.println(template.apply("Handlebars.java"));

        return template.apply("Upper.java");
    }
}
