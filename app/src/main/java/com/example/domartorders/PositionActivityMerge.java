package com.example.domartorders;

public class PositionActivityMerge {

    private final OrderActivity orderActivity;
    private final OrderPosition orderPosition;
    private Order order;


    public PositionActivityMerge(OrderPosition orderPosition, OrderActivity orderActivity) {
        this.orderActivity = orderActivity;
        this.orderPosition = orderPosition;
    }


    public String getNazwa() {
        return this.orderPosition != null ? this.orderPosition.getNazwa() : "";
    }


    public int getIlosc() {
        return this.orderPosition != null ? this.orderPosition.getIlosc() : Integer.parseInt("");
    }


    public float getCena_brutto() {
        return this.orderPosition != null ? this.orderPosition.getCena_brutto() : Float.parseFloat("");
    }

    public String getWaluta() {
        return this.orderPosition != null ? this.orderPosition.getWaluta() : "";
    }

    public String getCzynnosc() {
        return this.orderActivity != null ? this.orderActivity.getCzynnosc() : "";
    }

    public String getCechy() {
        return this.orderPosition != null ? this.orderPosition.getCechy() : "";
    }
}
