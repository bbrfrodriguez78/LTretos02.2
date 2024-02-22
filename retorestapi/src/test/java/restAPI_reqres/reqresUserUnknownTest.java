package restAPI_reqres;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.JsonSchemaValidator;

public class reqresUserUnknownTest {
    HttpClient clientHttp;

    @BeforeEach
    public void setup(){
        System.out.println("Inicio de la Configuracion de la Prueba REST API - Reqres.in");
        clientHttp = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    }

    @Test //Consulta usuario inexistente
    public void restAPIUserUnknown() throws URISyntaxException, IOException, InterruptedException{
        URIBuilder uri = new URIBuilder()
        .setScheme("https")
        .setHost("reqres.in")
        .setPath("api/users/"+78);

        System.out.println("Endpoint: " + uri.build());
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .uri(uri.build())
            .build();

        HttpResponse<String> response = clientHttp.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response: " + response.body());
        System.out.println("Estado de la petición: " + response.statusCode());
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());

        String validationSchema =  JsonSchemaValidator.validateJsonAgainstSchema(response.body(), "ReqresUserUnknownSchema.json");

        System.out.println("Resultado de la validacion del Schema: " + validationSchema);

        assertEquals("", validationSchema, "Resultado de la validacion del schema");
    }  
}
