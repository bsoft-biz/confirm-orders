package biz.bsoft.confirmorders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vbabin on 24.08.2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setNamel(String name) {
        this.name = name;
    }
}
