package com.teamteach.gateway.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
                List<SwaggerResource> resources = new ArrayList<>();
                resources.add(swaggerResource("users-docs", "/users-docs/v2/api-docs", "2.0"));
                resources.add(swaggerResource("profiles-docs", "/profiles-docs/v2/api-docs", "2.0"));
                resources.add(swaggerResource("journals-docs", "/journals-docs/v2/api-docs", "2.0"));
                resources.add(swaggerResource("recommendations-docs", "/recommendations-docs/v2/api-docs", "2.0"));
                resources.add(swaggerResource("notifications-docs", "/notifications-docs/v2/api-docs", "2.0"));
                resources.add(swaggerResource("learnings-docs", "/learnings-docs/v2/api-docs", "2.0"));
                return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setName(name);
                swaggerResource.setLocation(location);
                swaggerResource.setSwaggerVersion(version);
                return swaggerResource;
        }

}
