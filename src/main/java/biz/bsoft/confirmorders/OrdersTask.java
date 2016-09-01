package biz.bsoft.confirmorders;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import biz.bsoft.confirmorders.model.User;

/**
 * Created by vbabin on 24.08.2016.
 */
public class OrdersTask extends AsyncTask<Void, Void, User> {
    Activity mainActivity;
    public OrdersTask(Activity activity){
        super();
        mainActivity = activity;
    }

    public OrdersTask(){
        super();
    }

    /*public void execute(Activity activity){
        mainActivity = activity;
        super.execute();
    }*/

    @Override
    protected User doInBackground(Void... params) {
        String username="q", password="1", url;
        User user = OrderRestTemplate.auth(username, password);

        //url = "http://192.168.96.7:8080/users/user";
//        url = "https://www.tirhleb.com/orders/users/user";
//        // Set the username and password for creating a Basic Auth request
//        HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setAuthorization(authHeader);
//        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
//
//        // get RestTemplate instance
//        RestTemplate restTemplate = OrderRestTemplate.getInstance(); //new RestTemplate();
//
//        // Add the String message converter
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
//
//        try {
//            // Make the HTTP GET request to the Basic Auth protected URL
//            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class);
//            /*User*/ user = response.getBody();
//            return user;
//        } catch (HttpClientErrorException e) {
//            // Handle 401 Unauthorized response
//            Log.e("Orders 401!", e.getLocalizedMessage(), e);
//            //throw new RuntimeException(e.getLocalizedMessage());
//        }
//        catch (Exception e) {
//            Log.e("Orders exception!", e.getLocalizedMessage());
//        }
        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        /*if (user != null)
        {
            TextView greetingContentText = (TextView) mainActivity.findViewById(R.id.content_value);
            greetingContentText.setText(user.getName());
        }*/
    }

}