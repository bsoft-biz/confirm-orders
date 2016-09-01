package biz.bsoft.confirmorders.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import biz.bsoft.confirmorders.ItemsListArrayAdapter;
import biz.bsoft.confirmorders.LoadOrdersFragment;
import biz.bsoft.confirmorders.OrdersListArrayAdapter;
import biz.bsoft.confirmorders.R;
import biz.bsoft.confirmorders.model.Item;
import biz.bsoft.confirmorders.model.Order;

public class OrdersListActivity extends Activity implements LoadOrdersFragment.TaskCallbacks{
    public List<Item> items;
    ItemsListArrayAdapter dataAdapter = null;

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

        //setListAdapter(new ArrayAdapter<Item>(this,R.layout.list_items,items));
        //setListAdapter(new ItemsListArrayAdapter(this,items));

    }

    public void loadList(List<Order> orders) {
        OrdersListArrayAdapter dataAdapter = new OrdersListArrayAdapter(this,orders);
        ListView listView = (ListView) findViewById(R.id.lv_items);
        listView.setAdapter(dataAdapter);
    }

    public void bShowSelectedClick(View button){
        ListView listView = (ListView) findViewById(R.id.lv_items);
        List<Item> itemList=dataAdapter.objects;
        itemList = ((ItemsListArrayAdapter) listView.getAdapter()).objects;
        //itemList.get(1).setSelected(true);

        StringBuffer responseText = new StringBuffer();
        responseText.append("The following were selected...\n");

        //Item item = itemList.get(1);
        //item.setSelected(true);
        //responseText.append("\n" + item.getItemName());
        for(int i=0;i<itemList.size();i++){
            Item item = itemList.get(i);
            //Log.i("bShowSelectedClick",item.getItemName());
            if(item.isSelected()){
                responseText.append("\n" + item.getItemName());
            }
        }

        Toast.makeText(getApplicationContext(),
                responseText, Toast.LENGTH_LONG).show();
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
