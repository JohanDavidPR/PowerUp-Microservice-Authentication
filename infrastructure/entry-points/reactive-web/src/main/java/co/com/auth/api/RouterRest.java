package co.com.auth.api;

import co.com.auth.model.applicant.Applicant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "registerApplicant",
                    operation = @Operation(
                            operationId = "registerApplicant",
                            summary = "Registrar usuario",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario creado"),
                                    @ApiResponse(responseCode = "400", description = "Error de validación")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/all",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "getAllApplicants",
                    operation = @Operation(
                            operationId = "getAllApplicants",
                            summary = "Listar todos los usuarios",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                                            content = @Content(schema = @Schema(implementation = Applicant.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/usuarios"), handler::registerApplicant)
                .andRoute(GET("/api/v1/usuarios/all"), handler::getAllApplicants);
    }
}
