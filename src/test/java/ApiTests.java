import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTests {

    private static final String GET = "/get";
    private static final String POST = "/post";
    private static final String PUT = "/put";
    private static final String PATCH = "/patch";
    private static final String DELETE = "/delete";
    private static final String BASE_URL = "https://postman-echo.com";
    private static final String URLEN = "application/x-www-form-urlencoded; charset=UTF-8";
    private final RequestSpecification spec = given()
            .baseUri(BASE_URL)
            .filter(new ResponseLoggingFilter())
            .filter(new RequestLoggingFilter());

    @Test
    @DisplayName("GET Request")
    public void getRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");

        Response response = spec
                .contentType(ContentType.JSON)
                .params(params)
                .when()
                .get(GET);

        HashMap<String, String> args = response.path("args");
        HashMap<String, String> headers = response.path("headers");

        Assertions.assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals("bar1", args.get("foo1")),
                () -> assertEquals("bar2", args.get("foo2")),
                () -> assertEquals("postman-echo.com", headers.get("host")),
                () -> assertNotNull(headers.get("x-request-start")),
                () -> assertEquals("close", headers.get("connection")),
                () -> assertEquals("https", headers.get("x-forwarded-proto")),
                () -> assertEquals("443", headers.get("x-forwarded-port")),
                () -> assertNotNull(headers.get("x-amzn-trace-id")),
                () -> assertEquals("application/json", headers.get("content-type")),
                () -> assertEquals("*/*", headers.get("accept")),
                () -> assertEquals("Apache-HttpClient/4.5.13 (Java/11.0.26)", headers.get("user-agent")),
                () -> assertNotNull(headers.get("accept-encoding")),
                () -> assertEquals(BASE_URL + GET + "?foo1=bar1&foo2=bar2", response.path("url"))
        );
    }

    @Test
    @DisplayName("POST Raw Text")
    public void postRawText() {
        String requestBody = "{'test':'value'}";
        Response response = spec
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .post(POST);

        HashMap<String, String> headers = response.path("headers");
        String data = response.path("data");

        Assertions.assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals("{'test':'value'}", data),
                () -> assertEquals("postman-echo.com", headers.get("host")),
                () -> assertNotNull(headers.get("x-request-start")),
                () -> assertEquals("close", headers.get("connection")),
                () -> assertEquals("https", headers.get("x-forwarded-proto")),
                () -> assertEquals("443", headers.get("x-forwarded-port")),
                () -> assertNotNull(headers.get("x-amzn-trace-id")),
                () -> assertEquals("*/*", headers.get("accept")),
                () -> assertEquals("text/plain; charset=ISO-8859-1", headers.get("content-type")),
                () -> assertEquals("Apache-HttpClient/4.5.13 (Java/11.0.26)", headers.get("user-agent")),
                () -> assertNotNull(headers.get("accept-encoding")),
                () -> assertEquals(BASE_URL + POST, response.path("url"))
        );
    }

    @Test
    @DisplayName("POST Form Data")
    public void postFromData() {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("foo1", "bar1");
        formParams.put("foo2", "bar2");

        Response response = spec
                .contentType(URLEN)
                .formParams(formParams)
                .when()
                .post(POST);

        HashMap<String, String> headers = response.path("headers");
        HashMap<String, String> form = response.path("form");
        HashMap<String, String> json = response.path("json");

        Assertions.assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals("bar1", form.get("foo1")),
                () -> assertEquals("bar2", form.get("foo2")),
                () -> assertEquals("postman-echo.com", headers.get("host")),
                () -> assertNotNull(headers.get("x-request-start")),
                () -> assertEquals("close", headers.get("connection")),
                () -> assertEquals("19", headers.get("content-length")),
                () -> assertEquals("https", headers.get("x-forwarded-proto")),
                () -> assertEquals("443", headers.get("x-forwarded-port")),
                () -> assertNotNull(headers.get("x-amzn-trace-id")),
                () -> assertEquals("*/*", headers.get("accept")),
                () -> assertEquals("application/x-www-form-urlencoded; charset=UTF-8", headers.get("content-type")),
                () -> assertEquals("Apache-HttpClient/4.5.13 (Java/11.0.26)", headers.get("user-agent")),
                () -> assertNotNull(headers.get("accept-encoding")),
                () -> assertEquals("bar1", json.get("foo1")),
                () -> assertEquals("bar2", json.get("foo2")),
                () -> assertEquals(BASE_URL + POST, response.path("url"))
        );
    }

    @Test
    @DisplayName("PUT Request")
    public void putRequest() {
        String requestBody = "I am Malenia. Blade of Miquella. And I have never known defeat.";
        Response response = spec
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .put(PUT);

        HashMap<String, String> headers = response.path("headers");
        String data = response.path("data");
        HashMap<String, String> json = response.path("json");

        Assertions.assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals(requestBody, data),
                () -> assertEquals("postman-echo.com", headers.get("host")),
                () -> assertNotNull(headers.get("x-request-start")),
                () -> assertEquals("close", headers.get("connection")),
                () -> assertEquals("63", headers.get("content-length")),
                () -> assertEquals("https", headers.get("x-forwarded-proto")),
                () -> assertEquals("443", headers.get("x-forwarded-port")),
                () -> assertNotNull(headers.get("x-amzn-trace-id")),
                () -> assertEquals("*/*", headers.get("accept")),
                () -> assertEquals("text/plain; charset=ISO-8859-1", headers.get("content-type")),
                () -> assertEquals("Apache-HttpClient/4.5.13 (Java/11.0.26)", headers.get("user-agent")),
                () -> assertNotNull(headers.get("accept-encoding")),
                () -> assertNull(json),
                () -> assertEquals(BASE_URL + PUT, response.path("url"))
        );
    }

    @Test
    @DisplayName("PATCH Request")
    public void patchRequest() {
        String requestBody = "I am Malenia. Blade of Miquella. And I have never known defeat.";
        Response response = spec
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .patch(PATCH);

        HashMap<String, String> headers = response.path("headers");
        String data = response.path("data");
        HashMap<String, String> json = response.path("json");

        Assertions.assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals(requestBody, data),
                () -> assertEquals("postman-echo.com", headers.get("host")),
                () -> assertNotNull(headers.get("x-request-start")),
                () -> assertEquals("close", headers.get("connection")),
                () -> assertEquals("63", headers.get("content-length")),
                () -> assertEquals("https", headers.get("x-forwarded-proto")),
                () -> assertEquals("443", headers.get("x-forwarded-port")),
                () -> assertNotNull(headers.get("x-amzn-trace-id")),
                () -> assertEquals("*/*", headers.get("accept")),
                () -> assertEquals("text/plain; charset=ISO-8859-1", headers.get("content-type")),
                () -> assertEquals("Apache-HttpClient/4.5.13 (Java/11.0.26)", headers.get("user-agent")),
                () -> assertNotNull(headers.get("accept-encoding")),
                () -> assertNull(json),
                () -> assertEquals(BASE_URL + PATCH, response.path("url"))
        );
    }

    @Test
    @DisplayName("DELETE Request")
    public void deleteRequest() {
        String requestBody = "I am Malenia. Blade of Miquella. And I have never known defeat.";
        Response response = spec
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when()
                .delete(DELETE);

        HashMap<String, String> headers = response.path("headers");
        String data = response.path("data");
        HashMap<String, String> json = response.path("json");

        Assertions.assertAll(
                () -> assertEquals(HttpStatus.SC_OK, response.getStatusCode()),
                () -> assertEquals(requestBody, data),
                () -> assertEquals("postman-echo.com", headers.get("host")),
                () -> assertNotNull(headers.get("x-request-start")),
                () -> assertEquals("close", headers.get("connection")),
                () -> assertEquals("63", headers.get("content-length")),
                () -> assertEquals("https", headers.get("x-forwarded-proto")),
                () -> assertEquals("443", headers.get("x-forwarded-port")),
                () -> assertNotNull(headers.get("x-amzn-trace-id")),
                () -> assertEquals("*/*", headers.get("accept")),
                () -> assertEquals("text/plain; charset=ISO-8859-1", headers.get("content-type")),
                () -> assertEquals("Apache-HttpClient/4.5.13 (Java/11.0.26)", headers.get("user-agent")),
                () -> assertNotNull(headers.get("accept-encoding")),
                () -> assertNull(json),
                () -> assertEquals(BASE_URL + DELETE, response.path("url"))
        );
    }
}