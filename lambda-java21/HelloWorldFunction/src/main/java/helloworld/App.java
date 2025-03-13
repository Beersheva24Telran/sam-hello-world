package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Map<String, String> env = System.getenv();
    
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        
        Map<String, String> path = input.getQueryStringParameters();

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {

            String output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", path);
            if (path == null || path.get("id") == null) {throw new Exception("id parameter must exist");}
           response
                    .withStatusCode(200)
                    .withBody(output);
        } 
        catch (NoSuchElementException e) {
            response
                   .withBody(e.toString())
                    .withStatusCode(404);
        }catch (Exception e) {
            response
                   .withBody(e.toString())
                    .withStatusCode(500);
        }
        return response;
    }

   
}
