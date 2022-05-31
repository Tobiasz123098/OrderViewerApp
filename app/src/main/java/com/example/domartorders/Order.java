package com.example.domartorders;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    String adres_dostawy;
    float cena_brutto;
    long data_dost;
    long data_send;
    long data_transp;
    long data_zmiana;
    String forma_zap;
    long id;
    String nr;
    String odbiorca;
    String pracownik;
    String rodzaj_dost;
    String status;
    long termin_zap;
    String uwagi_dost;
    String waluta;
    float wartosc_netto;
    boolean zaplacono;
    boolean expanded;
    private List<OrderPosition> orderItems = new ArrayList<>();
    private List<OrderActivity> czynnosciItems = new ArrayList<>();


    public Order() {

    }

    Order(String message) {
        this.nr = message;
    }

    public String getAdres_dostawy() {
        return adres_dostawy;
    }

    public void setAdres_dostawy(String adres_dostawy) {
        this.adres_dostawy = adres_dostawy;
    }

    public float getCena_brutto() {
        return cena_brutto;
    }

    public void setCena_brutto(float cena_brutto) {
        this.cena_brutto = cena_brutto;
    }

    public String getData_dost() {
        return simpleDateFormat.format(this.data_dost);
    }

    public void setData_dost(Long data_dostawy) {
        this.data_dost = data_dostawy;
    }

    public String getData_send() {
        return simpleDateFormat.format(this.data_send);
    }

    public void setData_send(long data_send) {
        this.data_send = data_send;
    }

    public String getData_transp() {
        return simpleDateFormat.format(this.data_transp);
    }

    public void setData_transp(long data_transp) {
        this.data_transp = data_transp;
    }

    public String getData_zmiana() {
        return simpleDateFormat.format(this.data_zmiana);
    }

    public void setData_zmiana(long data_zmiana) {
        this.data_zmiana = data_zmiana;
    }

    public String getForma_zap() {
        return forma_zap;
    }

    public void setForma_zap(String forma_zap) {
        this.forma_zap = forma_zap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getOdbiorca() {
        return odbiorca;
    }

    public void setOdbiorca(String odbiorca) {
        this.odbiorca = odbiorca;
    }

    public String getPracownik() {
        return pracownik;
    }

    public void setPracownik(String pracownik) {
        this.pracownik = pracownik;
    }

    public String getRodzaj_dost() {
        return rodzaj_dost;
    }

    public void setRodzaj_dost(String rodzaj_dost) {
        this.rodzaj_dost = rodzaj_dost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTermin_zap() {
        return termin_zap;
    }

    public void setTermin_zap(long termin_zap) {
        this.termin_zap = termin_zap;
    }

    public String getUwagi_dost() {
        return uwagi_dost;
    }

    public void setUwagi_dost(String uwagi_dost) {
        this.uwagi_dost = uwagi_dost;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public float getWartosc_netto() {
        return wartosc_netto;
    }

    public void setWartosc_netto(float wartosc_netto) {
        this.wartosc_netto = wartosc_netto;
    }

    public boolean isZaplacono() {
        return zaplacono;
    }

    public void setZaplacono(boolean zaplacono) {
        this.zaplacono = zaplacono;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<OrderPosition> getOrderItems() {
        return orderItems;
    }

    public List<OrderActivity> getCzynnosciItems() {
        return czynnosciItems;
    }

    @Override
    public String toString() {
        return nr;
    }

    public void addOrderItem(OrderPosition orderItem) {
        if(orderItem!= null) {
            this.orderItems.add(orderItem);
        }
    }

    public void addCzynnosciItem(OrderActivity czynnosciOrder) {
        if (czynnosciOrder != null) {
            this.czynnosciItems.add(czynnosciOrder);
        }
    }

    public void autoFillingMethod(TextView textView) {
        if(textView.getText().equals("")) {
            textView.setText("----");
        }
    }
}
/*
if (holder.titleTextView.getText().equals("")) {
        holder.titleTextView.setText("----");
        } else {
        holder.titleTextView.setText(information.getNr());
        }
 */



