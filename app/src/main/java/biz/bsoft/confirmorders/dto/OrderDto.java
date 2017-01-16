package biz.bsoft.confirmorders.dto;

public class OrderDto {
    private Integer id;
    private Boolean selected;

    public OrderDto(Integer id, Boolean selected) {
        this.id = id;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", selected=" + selected +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
