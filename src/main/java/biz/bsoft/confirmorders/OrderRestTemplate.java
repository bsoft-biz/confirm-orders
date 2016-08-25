package biz.bsoft.confirmorders;

import android.util.Log;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

/**
 * Created by vbabin on 24.08.2016.
 */
public class OrderRestTemplate {
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders requestHeaders = new HttpHeaders();
    private static String jsessionid;

    public static RestTemplate getInstance(){
        return restTemplate;
    }

    public static HttpHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public static String getJsessionid() {
        return jsessionid;
    }

    private static void extractCookies(HttpHeaders responseHeaders){
        //Log.i("HEADERS",responseHeaders.toString());
        //Log.i("HEADERS\\Set-Cookie",responseHeaders.get("Set-Cookie").get(1));// getClass().toString()
        //responseHeaders.get("Set-Cookie").stream().forEach(System.out::println);
        try {
            for (String cookie :
                    responseHeaders.get("Set-Cookie")) {
                if (cookie.contains("JSESSIONID")) {
                    int startPos = cookie.indexOf("=");
                    int endPos = cookie.indexOf(";");
                    jsessionid = cookie.substring(startPos, endPos);
                    requestHeaders.add("Cookie", "JSESSIONID=" + jsessionid);
                    //requestHeaders.set("Cookie",cookies.stream().collect(Collectors.joining(";")));

                }
            }
        }
        catch (Exception e)
        {
            Log.e("extractCookies",e.toString());// getMessage()+e.getClass()
        }
    }

    public static User auth(String username, String password) {
        try {
            String url = "https://www.tirhleb.com/orders/users/user";
            // Set the username and password for creating a Basic Auth request
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            requestHeaders.setAuthorization(authHeader);
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            // Make the HTTP GET request to the Basic Auth protected URL
            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class);
            User user = response.getBody();
            //extract cookies
            HttpHeaders responseHeaders = response.getHeaders();
            extractCookies(responseHeaders);
            return user;
        } catch (HttpClientErrorException e) {
            // Handle 401 Unauthorized response
            Log.e("Orders 401!", e.getLocalizedMessage(), e);
            //throw new RuntimeException(e.getLocalizedMessage());
        }
        catch (Exception e) {
            Log.e("Orders exception!", e.getLocalizedMessage());
        }
        return null;
    }
}
