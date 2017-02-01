package tr.hbtrinside.hbtrinside.model.ResultObjects;

import tr.hbtrinside.hbtrinside.model.ParameterObjects.sonuc;
import tr.hbtrinside.hbtrinside.model.ParameterObjects.mesaj;

/**
 * Created by DoGan on 11.01.2017.
 */

public class MesajGetirResultParameter {
    public mesaj[] Mesaj;
    public sonuc Sonuc;

    public mesaj[] getMesaj() {
        return Mesaj;
    }

    public void setMesaj(mesaj[] mesaj) {
        Mesaj = mesaj;
    }

    public sonuc getSonuc() {
        return Sonuc;
    }

    public void setSonuc(sonuc sonuc) {
        Sonuc = sonuc;
    }
}
