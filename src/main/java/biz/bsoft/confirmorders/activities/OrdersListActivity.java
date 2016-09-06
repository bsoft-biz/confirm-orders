package biz.bsoft.confirmorders.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import biz.bsoft.confirmorders.LoadOrdersFragment;
import biz.bsoft.confirmorders.OrdersListArrayAdapter;
import biz.bsoft.confirmorders.R;
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
        Log.i("onPostExecute",((Integer) orders.size()).toString());
        loadList(orders);
    }

    @Override
    public void onPreExecute() {

    }
}
