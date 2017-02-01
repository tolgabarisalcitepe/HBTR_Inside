package tr.hbtrinside.hbtrinside.model.ResultObjects;

import tr.hbtrinside.hbtrinside.model.ParameterObjects.sonuc;

/**
 * Created by DoGan on 11.01.2017.
 */

public class MesajGonderResultParameter {
    public int MesajId ;
    public sonuc Sonuc ;

    public int getMesajId() {
        return MesajId;
    }

    public void setMesajId(int mesajId) {
        MesajId = mesajId;
    }

    public sonuc getSonuc() {
        return Sonuc;
    }

    public void setSonuc(sonuc sonuc) {
        Sonuc = sonuc;
    }
}
