package biz.bsoft.confirmorders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import biz.bsoft.confirmorders.model.Order;

/**
 * Created by vbabin on 01.09.2016.
 */
public class OrdersListArrayAdapter extends ArrayAdapter <Order> {
private final Context context;
public final List<Order> objects;


public OrdersListArrayAdapter(Context context, List<Order> objects) {
        super(context, R.layout.list_items, objects);
        this.context = context;
        this.objects = objects;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_items,parent,false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.cb_item);
        textView.setText(objects.get(position).getClientPos());
        checkBox.setChecked(objects.get(position).isSelected());
        checkBox.setTag(objects.get(position));
        //Log.i("getView",objects.get(position).getClientPos());
        checkBox.setOnClickListener(new View.OnClickListener(){

@Override
public void onClick(View view) {
        //((Item) view.getTag()).setSelected(((CheckBox) view).isSelected());

        CheckBox cb = (CheckBox) view;
        Order order = (Order) cb.getTag();
        order.setSelected(cb.isChecked());
        Toast.makeText(context,
        "Clicked on Checkbox: " + cb.getText() +
        " isChecked= " + cb.isChecked()+ "" +
        " itemName="+order.getClientPos()+" isSelected="+order.isSelected(),
        Toast.LENGTH_SHORT).show();
        }
        });

        return rowView;
        }
        }