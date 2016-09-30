package biz.bsoft.confirmorders.model;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.LocalDate;

/**
 * Created by vbabin on 01.09.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private Integer Id;

    @JsonFormat(pattern="dd.MM.yyyy")
    private LocalDate shipDate;

    private String client;

    private Integer clientId;

    private String clientPos;

    private Integer clientPosId;

    private String route;

    private Integer routeId;

    private boolean selected;

    /*public Order() {}

    public Order(String shipDate) {
        //this.shipDate = shipDate;
        Log.i("ORDER CONSTRUCTOR",shipDate);
    }*/

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientPos() {
        return clientPos;
    }

    public void setClientPos(String clientPos) {
        this.clientPos = clientPos;
    }

    public Integer getClientPosId() {
        return clientPosId;
    }

    public void setClientPosId(Integer clientPosId) {
        this.clientPosId = clientPosId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}