package biz.bsoft.confirmorders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import biz.bsoft.confirmorders.model.Item;

/**
 * Created by vbabin on 26.08.2016.
 */
public class ItemsListArrayAdapter extends ArrayAdapter<Item> {
    private final Context context;
    public final List<Item> objects;


    public ItemsListArrayAdapter(Context context, List<Item> objects) {
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
        textView.setText(objects.get(position).getItemName());
        checkBox.setChecked(objects.get(position).isSelected());
        checkBox.setTag(objects.get(position));
        checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //((Item) view.getTag()).setSelected(((CheckBox) view).isSelected());

                CheckBox cb = (CheckBox) view;
                Item item = (Item) cb.getTag();
                item.setSelected(cb.isChecked());
                Toast.makeText(context,
                        "Clicked on Checkbox: " + cb.getText() +
                                " isChecked= " + cb.isChecked()+ "" +
                        " itemName="+item.getItemName()+" isSelected="+item.isSelected(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}
