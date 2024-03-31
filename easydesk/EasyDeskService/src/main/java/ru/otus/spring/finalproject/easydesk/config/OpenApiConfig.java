package ru.otus.spring.finalproject.easydesk.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "EasyDesk",
                description = "Easy example of ticket management system", version = "1.0.0",
                contact = @Contact(
                        name = "Alexey Fadeev",
                        email = "fadeevay84@gmail.com",
                        url = "https://fadeev.alexey.dev"
                )
        )
)
public class OpenApiConfig {

}
