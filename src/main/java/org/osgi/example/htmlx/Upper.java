package org.osgi.example.htmlx;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardResource;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Component(service = Upper.class)
@JaxrsResource
@HttpWhiteboardResource(pattern = "/quickstart/*", prefix = "static")
@org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect("(osgi.http.whiteboard.context.name=org.osgi.service.http)")
public class Upper {

    User user;

    @Activate
    public void start() {
        System.out.println("Started");
        this.user = new User("Tom", 30);
    }

    @Path("rest/upper/{param}")
    @GET
    public String toUpper(@PathParam("param") String param) {
        return param.toUpperCase();
    }

    @Path("/home")
    @GET
    public String homePage() throws IOException {
        TemplateLoader loader = new BundleClassPathTemplateLoader();
        loader.setPrefix("templates");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("home");

        return template.apply(this.user);
    }

    @Path("clicked")
    @POST
    public String click(@PathParam("param") String param) throws IOException {
        System.out.println("Clicked");

        TemplateLoader loader = new BundleClassPathTemplateLoader();
        loader.setPrefix("templates");
        Handlebars handlebars = new Handlebars(loader);

        UserTemplate template = handlebars.compile("mytemplate").as(UserTemplate.class);

        return template.apply(this.user);
    }

    @Path("edit")
    @GET
    public String getEdit() throws IOException {
        TemplateLoader loader = new BundleClassPathTemplateLoader();
        loader.setPrefix("templates");
        Handlebars handlebars = new Handlebars(loader);

        UserTemplate template = handlebars.compile("edit-mytemplate").as(UserTemplate.class);

        return template.apply(this.user);
    }

    @Path("save")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String saveEdit(
            @FormParam("name") String name,
            @FormParam("age") Integer age,
            @Context HttpServletResponse servletResponse
    ) throws IOException {
        TemplateLoader loader = new BundleClassPathTemplateLoader();
        loader.setPrefix("templates");
        Handlebars handlebars = new Handlebars(loader);

        UserTemplate template = handlebars.compile("mytemplate").as(UserTemplate.class);

        this.user.name = name;
        this.user.age = age;

        servletResponse.setHeader("HX-Trigger", "save");
        return template.apply(this.user);
    }

    @Path("/user")
    @GET
    public String getUser() throws IOException {
        Handlebars handlebars = new Handlebars();
        UserTemplate template = handlebars.compileInline("{{name}}").as(UserTemplate.class);

        return template.apply(this.user);
    }

    @Path("/users")
    @GET
    public String getUsers() throws IOException {
        TemplateLoader loader = new BundleClassPathTemplateLoader();
        loader.setPrefix("templates");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("user-list");

        return template.apply(this.user);
    }

    @Path("/users")
    @POST
    public String createUser(
            @FormParam("name") String name,
            @FormParam("age") Integer age,
            @Context HttpServletResponse servletResponse
    ) throws IOException {
        TemplateLoader loader = new BundleClassPathTemplateLoader();
        loader.setPrefix("templates");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("create-user");

        this.user.users.add(new User(name, age));

        servletResponse.setHeader("HX-Trigger", "create-user");

        return template.apply(this.user);
    }

    @Path("/users/:id")
    @GET
    public String getUserById(@PathParam("id") Integer id) throws IOException {
        Handlebars handlebars = new Handlebars();
        UserTemplate template = handlebars.compileInline("{{name}}").as(UserTemplate.class);

        return template.apply(this.user);
    }
}
