package com.example.domartorders;

public class OrderPosition {

    String cechy;
    float cena_brutto;
    long id;
    int ilosc;
    String jm;
    String nazwa;
    int poz_id;
    String uwagi_klienta;
    String waluta;
    long zlec_id;

    public OrderPosition() {
    }

    public OrderPosition(String s) {}

    public String getCechy() {
        return cechy;
    }

    public void setCechy(String cechy) {
        this.cechy = cechy;
    }

    public float getCena_brutto() {
        return cena_brutto;
    }

    public void setCena_brutto(float cena_brutto) {
        this.cena_brutto = cena_brutto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String getJm() {
        return jm;
    }

    public void setJm(String jm) {
        this.jm = jm;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getPoz_id() {
        return poz_id;
    }

    public void setPoz_id(int poz_id) {
        this.poz_id = poz_id;
    }

    public String getUwagi_klienta() {
        return uwagi_klienta;
    }

    public void setUwagi_klienta(String uwagi_klienta) {
        this.uwagi_klienta = uwagi_klienta;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public long getZlec_id() {
        return zlec_id;
    }

    public void setZlec_id(long zlec_id) {
        this.zlec_id = zlec_id;
    }

    @Override
    public String toString() {
        return "PozZlecInformation{}";
    }
}
