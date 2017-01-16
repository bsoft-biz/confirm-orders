package biz.bsoft.confirmorders.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import biz.bsoft.confirmorders.LoadOrdersFragment;
import biz.bsoft.confirmorders.OrderRestTemplate;
import biz.bsoft.confirmorders.OrdersListArrayAdapter;
import biz.bsoft.confirmorders.R;
import biz.bsoft.confirmorders.dto.OrderDto;
import biz.bsoft.confirmorders.model.Order;

public class OrdersListActivity extends Activity implements LoadOrdersFragment.TaskCallbacks{
    public static final String TAG_FRAGMENT="LoadOrdersFragment";
    private LoadOrdersFragment loadOrdersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        FragmentManager fm = getFragmentManager();
        loadOrdersFragment = (LoadOrdersFragment) fm.findFragmentByTag(TAG_FRAGMENT);
        if(loadOrdersFragment==null){
            loadOrdersFragment=new LoadOrdersFragment();
            fm.beginTransaction().add(loadOrdersFragment,TAG_FRAGMENT).commit();
        }
        else
        {
            loadList(loadOrdersFragment.objects);
        }
    }

    public void loadList(List<Order> orders) {
        OrdersListArrayAdapter dataAdapter = new OrdersListArrayAdapter(this,orders);
        ListView listView = (ListView) findViewById(R.id.lv_items);
        listView.setAdapter(dataAdapter);
    }

    @Override
    public void onPostExecute(List<Order> orders) {
        //Log.i("onPostExecute",((Integer) orders.size()).toString());
        loadList(orders);
    }

    @Override
    public void onPreExecute() {

    }

    public void bProcessOrdersClick(View button){
        ListView listView = (ListView) findViewById(R.id.lv_items);
        List<Order> orders= ((OrdersListArrayAdapter)listView.getAdapter()).objects;
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders)
        {
            if(order.isSelected()){
                orderDtos.add(new OrderDto(order.getId(),order.isSelected()));
            }
        }
        new Task().execute(orderDtos);
    }

    public void bShowSelectedClick(View button){
        ListView listView = (ListView) findViewById(R.id.lv_items);
        List<Order> orders= ((OrdersListArrayAdapter)listView.getAdapter()).objects;

        StringBuffer responseText = new StringBuffer();
        responseText.append("The following were selected...\n");

        //Item item = itemList.get(1);
        //item.setSelected(true);
        //responseText.append("\n" + item.getItemName());
        for(Order order : orders){
            //Log.i("bShowSelectedClick",item.getItemName());
            if(order.isSelected()){
                responseText.append("\n" + order.getClientPos());
            }
        }

        Toast.makeText(getApplicationContext(),
                responseText, Toast.LENGTH_LONG).show();
    }
    private class Task extends AsyncTask<List<OrderDto>,Void,Void> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(List<OrderDto> ...orders) {
            OrderRestTemplate.processOrders(orders[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
