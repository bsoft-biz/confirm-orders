package biz.bsoft.confirmorders.model;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import biz.bsoft.confirmorders.OrderRestTemplate;
import biz.bsoft.confirmorders.R;

/**
 * Created by vbabin on 25.08.2016.
 */
public class OrderLoadItemsTask extends AsyncTask<Void, Void, List<Item>> {
    Activity mainActivity;

    public void execute(Activity activity){
        mainActivity = activity;
        super.execute();
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        try {
            String url = "https://www.tirhleb.com/orders/orders/items";
            HttpHeaders requestHeaders = OrderRestTemplate.getRequestHeaders();
            RestTemplate restTemplate = OrderRestTemplate.getInstance();
            //HttpHeaders requestHeaders = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            ResponseEntity<List<Item>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Item>>() {});
            List<Item> items = response.getBody();
            return items;
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

    @Override
    protected void onPostExecute(List<Item> items) {
        if (items != null){
            super.onPostExecute(items);
            LinearLayout ll = (LinearLayout) mainActivity.findViewById(R.id.items_list);
            for (Item item :
                    items) {
                TextView textView = new TextView(mainActivity);
                textView.setText(item.getItemName());
                ll.addView(textView);
            }
        }
    }
}
