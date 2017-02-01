package tr.hbtrinside.hbtrinside.model.ResultObjects;

import tr.hbtrinside.hbtrinside.model.ParameterObjects.sonuc;
import tr.hbtrinside.hbtrinside.model.ParameterObjects.gen_organizasyon;

/**
 * Created by DoGan on 11.01.2017.
 */

public class OrganizasyonBilgisiGetirResultParameter {
    public gen_organizasyon[] OrganizasyonBilgi;
    public sonuc Sonuc;

    public gen_organizasyon[] getOrganizasyonBilgi() {
        return OrganizasyonBilgi;
    }

    public void setOrganizasyonBilgi(gen_organizasyon[] organizasyonBilgi) {
        OrganizasyonBilgi = organizasyonBilgi;
    }

    public sonuc getSonuc() {
        return Sonuc;
    }

    public void setSonuc(sonuc sonuc) {
        Sonuc = sonuc;
    }
}
