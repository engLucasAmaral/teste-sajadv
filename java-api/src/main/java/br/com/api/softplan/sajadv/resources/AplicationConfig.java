/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.api.softplan.sajadv.resources;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import java.util.Set;
import javax.servlet.annotation.WebFilter;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author lucas
 */
@ApplicationPath("v1")
@WebFilter(asyncSupported = true)
public class AplicationConfig extends Application {

    private final Logger log = LogManager.getLogger();


    public AplicationConfig() {
        try {
            BeanConfig conf = new BeanConfig();
            conf.setTitle("API - Softplan...");
            conf.setDescription("Desafio Técnico Softplan (SAJADV)");
            conf.setBasePath("sajadv/v1");
            conf.setSchemes(new String[]{"http"});
            conf.setResourcePackage("br.com.api.softplan.sajadv.resources");
            conf.setScan(true);
            
        } catch (Exception e) {
            log.error("Erro ao iniciar swagger....", e);
        }

    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        try {
            Class jsonProvider = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
            resources.add(jsonProvider);

        } catch (ClassNotFoundException ex) {
            log.trace("Erro ao adicionar o provider do Json", ex);
        }
        //classes do swagger...
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.api.softplan.sajadv.resources.CORSFilter.class);
        resources.add(br.com.api.softplan.sajadv.resources.SoftplanResources.class);
    }
}
