package com.hbtrinside.hbtrinside;

public class Kisi {
    private String  isim;
    private String sicil;

    public Kisi(String isim, String sicil) {
        super();
        this.isim = isim;
        this.sicil = sicil;
    }

    @Override
    public String toString() {
        return isim;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSicil() {
        return sicil;
    }

    public void setSicil(String sicil) {
        this.sicil = sicil;
    }
}