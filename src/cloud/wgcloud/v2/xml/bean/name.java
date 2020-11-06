package cloud.wgcloud.v2.xml.bean;

/**
 * @author guichun
 */
public class name {
    private int flesId;
    private int id;
    private String flName;
    private String names;
    private String urls;
    private String explain;

    public int getFlesId() {
        return flesId;
    }

    public void setFlesId(int flesId) {
        this.flesId = flesId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlName() {
        return flName;
    }

    public void setFlName(String flName) {
        this.flName = flName;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "XmlData [ FlesId:"+flesId+",flName:"+flName+"，id="+id+", name="+names+", value="+urls+",explain="+explain+" ]";
    }
}
