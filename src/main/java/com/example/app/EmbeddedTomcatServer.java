package com.example.app;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import java.io.File;

public class EmbeddedTomcatServer {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        String webAppDir = "src/main/webapp/";
        Context ctx = tomcat.addWebapp("", new File(webAppDir).getAbsolutePath());
        
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebConfig.class);

        DispatcherServlet dispatcher = new DispatcherServlet(appContext);
        Tomcat.addServlet(ctx, "dispatcher", dispatcher);
        ctx.addServletMappingDecoded("/", "dispatcher");

        System.out.println("🚀 Starting Embedded Tomcat on http://localhost:8080");
        tomcat.start();
        tomcat.getServer().await();
    }    
}
