package biz.bsoft.confirmorders;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import biz.bsoft.confirmorders.dto.OrderDto;
import biz.bsoft.confirmorders.model.Order;
import biz.bsoft.confirmorders.model.User;

public class OrderRestTemplate {
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders requestHeaders = new HttpHeaders();
    private static String jsessionid;
    private static String xsrfToken;
    private static String serverUrl = PreferenceManager.getDefaultSharedPreferences(App.getContext()).getString("server_url", "NA");


    public static RestTemplate getInstance(){
        return restTemplate;
    }

    public static HttpHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public static String getJsessionid() {
        return jsessionid;
    }

    private static void extractCookies(HttpHeaders responseHeaders, Integer i){
        Log.i("HEADERS",responseHeaders.toString());
        //Log.i("HEADERS\\Set-Cookie",responseHeaders.get("Set-Cookie").get(1));// getClass().toString()
        //responseHeaders.get("Set-Cookie").stream().forEach(System.out::println);
        try {
            for (String cookie :
                    responseHeaders.get("Set-Cookie")) {
                if (i==0 && cookie.contains("JSESSIONID")) {
                    int startPos = cookie.indexOf("=");
                    int endPos = cookie.indexOf(";");
                    jsessionid = cookie.substring(startPos+1, endPos);
                    requestHeaders.add("Cookie", "JSESSIONID=" + jsessionid);
                    //requestHeaders.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
                }
                if (i==1 && cookie.contains("XSRF-TOKEN")) {
                    int startPos = cookie.indexOf("=");
                    int endPos = cookie.indexOf(";");
                    xsrfToken = cookie.substring(startPos+1, endPos);
                    requestHeaders.add("X-XSRF-TOKEN", xsrfToken);
                    requestHeaders.get("Cookie").add("XSRF-TOKEN=" + xsrfToken);
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
            String url = serverUrl+"/users/user";
            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            // Set the username and password for creating a Basic Auth request
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            requestHeaders.setAuthorization(authHeader);
            HttpEntity<?> requestEntity = null;
            // Make the HTTP GET request to the Basic Auth protected URL
            ResponseEntity<User> response = null;

            //1) get sessionId
            //2) get XSRF-TOKEN using new/real sessionId
            for(int i=0;i<2;i++){
                requestEntity = new HttpEntity<Object>(requestHeaders);
                response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class);
                //extract cookies
                HttpHeaders responseHeaders = response.getHeaders();
                extractCookies(responseHeaders,i);
            }

            User user = response.getBody();
            return user;
        } catch (HttpClientErrorException e) {
            // Handle 401 Unauthorized response
            Log.e("Orders 403!", e.getLocalizedMessage(), e);
            //throw new RuntimeException(e.getLocalizedMessage());
        }
        catch (Exception e) {
            Log.e("Orders exception!", e.getLocalizedMessage(), e);
        }
        return null;
    }

    public static List<Order> getOrders() {
        try {
            Log.i("getOrders","requestHeaders="+requestHeaders.toString());
            String url = serverUrl+"/orders/";
            //HttpHeaders requestHeaders = OrderRestTemplate.getRequestHeaders();
            //RestTemplate restTemplate = OrderRestTemplate.getInstance();
            //HttpHeaders requestHeaders = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            //ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            //Log.e("response.getBody()",resp.getBody());
            ResponseEntity<List<Order>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Order>>() {});
            List<Order> orders = response.getBody();
            return orders;
            //
        } catch (HttpClientErrorException e) {
            // Handle 401 Unauthorized response
            Log.e("Orders 401!", e.getLocalizedMessage(), e);
            //throw new RuntimeException(e.getLocalizedMessage());
        }
        catch (Exception e) {
            Log.e("Orders exception!", e.getLocalizedMessage(),e.getCause());
        }
        return new ArrayList<>();
    }

    public static void processOrders(List<OrderDto> orders) {
        Log.i("processOrders =", orders.toString());

        try {
            String url = serverUrl+"/orders/process";
            Log.i("process","requestHeaders="+requestHeaders.toString());
            HttpEntity<List<OrderDto>> requestEntity = new HttpEntity<>(orders,requestHeaders);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response.hasBody())
                Log.i("response.getBody()",response.getBody());
        } catch (HttpClientErrorException e) {
            Log.e("Orders 403!", e.getLocalizedMessage(), e);
        }
        catch (Exception e) {
            Log.e("Orders exception!", e.getLocalizedMessage(),e);
        }
    }
}
