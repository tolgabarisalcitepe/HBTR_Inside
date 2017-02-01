package tr.hbtrinside.hbtrinside.model.ParameterObjects;

/**
 * Created by DoGan on 11.01.2017.
 */

public class sonuc {
    public int SonucKod;
    public String  SonucMesaj;
    public int Ekran;
    public String  ExceptionStackTrace;

    public int getSonucKod() {
        return SonucKod;
    }

    public void setSonucKod(int sonucKod) {
        SonucKod = sonucKod;
    }

    public String getSonucMesaj() {
        return SonucMesaj;
    }

    public void setSonucMesaj(String sonucMesaj) {
        SonucMesaj = sonucMesaj;
    }

    public int getEkran() {
        return Ekran;
    }

    public void setEkran(int ekran) {
        Ekran = ekran;
    }

    public String getExceptionStackTrace() {
        return ExceptionStackTrace;
    }

    public void setExceptionStackTrace(String exceptionStackTrace) {
        ExceptionStackTrace = exceptionStackTrace;
    }
}
