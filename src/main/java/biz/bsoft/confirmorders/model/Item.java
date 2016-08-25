package biz.bsoft.confirmorders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vbabin on 24.08.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String ItemName;

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        this.ItemName = itemName;
    }
}
