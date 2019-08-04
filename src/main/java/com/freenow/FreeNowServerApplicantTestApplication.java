package com.freenow;

import com.freenow.util.LoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@SpringBootApplication
public class FreeNowServerApplicantTestApplication extends WebMvcConfigurerAdapter
{

    public static void main(String[] args)
    {
        SpringApplication.run(FreeNowServerApplicantTestApplication.class, args);
    }


    private static ArrayList<? extends SecurityScheme> securitySchemes()
    {

        return new ArrayList<SecurityScheme>()
        {{
            add(new ApiKey("Bearer", "Authorization", "header"));
        }};
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }


    @Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(generateApiInfo())
            .securitySchemes(securitySchemes());
    }


    private ApiInfo generateApiInfo()
    {
        return new ApiInfo("freenow Server Applicant Test Service", "This service is to check the technology knowledge of a server applicant for freenow.", "Version 1.0 - mw",
            "urn:tos", "career@freenow.com", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
    }
}
