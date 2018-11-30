package com.mogydan.similarity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String BASE_PACKAGE_NAME = "com.mogydan.similarity.controller";
    private static final Tag tag1 =
            new Tag("Bought Together Products", "Get products, that are often bought together", 0);
    private static final Tag tag2 =
            new Tag("Recommended Products", "Get the products, which customers like you bought", 1);
    private static final Tag tag3 =
            new Tag("Top Products", "Get the rating of the most sold products", 2);
    private static final Tag tag4 =
            new Tag("Products", "Products controller", 3);
    private static final Tag tag5 =
            new Tag("Customers", "Customers controller", 4);
    private static final Tag tag6 =
            new Tag("Orders", "Purchase Orders controller", 5);
    private static final Tag tag7 =
            new Tag("Order Details", "Order Details controller", 6);
    private static final Tag tag8 =
            new Tag("Test Data Provider", "Provide Test Data", 7);

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(metaData())
                .tags(tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Smart Recommendation Engine")
                .description("Smart products recommendations for customers")
                .version("1.0.1")
                .build();
    }
}
