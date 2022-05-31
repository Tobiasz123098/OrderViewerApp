package com.example.domartorders;

public class OrderActivity {
    long _last_modified;
    String czynnosc;
    long data_wyk;
    int ilosc;
    long key_id;
    int poz_id;
    long zlec_id;

    public OrderActivity() {

    }

    public long get_last_modified() {
        return _last_modified;
    }

    public void set_last_modified(long _last_modified) {
        this._last_modified = _last_modified;
    }

    public String getCzynnosc() {
        return czynnosc;
    }

    public void setCzynnosc(String czynnosc) {
        this.czynnosc = czynnosc;
    }

    public long getData_wyk() {
        return data_wyk;
    }

    public void setData_wyk(long data_wyk) {
        this.data_wyk = data_wyk;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public long getKey_id() {
        return key_id;
    }

    public void setKey_id(long key_id) {
        this.key_id = key_id;
    }

    public int getPoz_id() {
        return poz_id;
    }

    public void setPoz_id(int poz_id) {
        this.poz_id = poz_id;
    }

    public long getZlec_id() {
        return zlec_id;
    }

    public void setZlec_id(long zlec_id) {
        this.zlec_id = zlec_id;
    }

//    public List<PozZlecInformation> getPozZlecItems() {
//        return pozZlecItems;
//    }
//
//    public List<CzynnosciInformation> getCzynnItems() {
//        return czynnItems;
//    }
//
//    public void addPozZlecItems(PozZlecInformation pozZlecInformation) {
//        if (pozZlecInformation != null) {
//            this.pozZlecItems.add(pozZlecInformation);
//        }
//    }
//
//    public void addCzynnItems(CzynnosciInformation czynnosciInformation) {
//        if(czynnosciInformation!= null) {
//            this.czynnItems.add(czynnosciInformation);
//        }
//    }
}
