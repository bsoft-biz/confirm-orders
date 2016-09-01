package biz.bsoft.confirmorders;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import biz.bsoft.confirmorders.activities.OrdersListActivity;
import biz.bsoft.confirmorders.model.Item;

/**
 * Created by vbabin on 25.08.2016.
 */
public class OrderLoadItemsTask extends AsyncTask<Void, Void, List<Item>> {
    Activity mainActivity;
    List<Item> activityItems;



    public void execute(Activity activity){
        mainActivity = activity;
        super.execute();
    }

    public void execute(Activity activity,List<Item> items){
        mainActivity = activity;
        this.activityItems = items;
        super.execute();
    }

    @Override
    protected List<Item> doInBackground(Void... voids) {
        try {
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(App.getContext());
            String serverUrl = SP.getString("server_url", "NA");
            String url = serverUrl+"/orders/items";
            //String url = "https://www.tirhleb.com/orders/orders/items";
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
            //activityItems == null
            if(mainActivity.getClass()!=OrdersListActivity.class) {
                super.onPostExecute(items);
                /*LinearLayout ll = (LinearLayout) mainActivity.findViewById(R.id.items_list);
                for (Item item :
                        items) {
                    TextView textView = new TextView(mainActivity);
                    textView.setText(item.getItemName());
                    ll.addView(textView);
                }*/
            }
            else{
                //activityItems.addAll(items);
                //OrdersListActivity act = (OrdersListActivity) mainActivity;
                Log.i("OrderLoadItemsTask","items loaded. adding them to activity");
                ((OrdersListActivity) mainActivity).items=items;//.addAll(items);
                ((OrdersListActivity) mainActivity).refreshList();
            }
        }
    }
}
