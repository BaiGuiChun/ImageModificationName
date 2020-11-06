package cloud.wgcloud.v3.xmlRead;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.List;

/**
 * @author guichun
 */
public class xmlRead {
    //加载xml文件
    public static Document load(){
        Document document=null;
        String url="/Users/guichun/PictureToName/config.xml";
        try {
            SAXBuilder reader = new SAXBuilder();
            document=reader.build(new File(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void main(String[] args) {
//        Document document=null;
        Document document=load();
        Element documentRootElement = document.getRootElement();

        List<Element> elementList = documentRootElement.getChildren();

        for (Element e:elementList){

            List<Element> elementList1 = e.getChildren();

            System.out.println(e.getAttribute("tit").getValue());


            for (Element element : elementList1){

                String tit = element.getAttributeValue("tit");
            }
        }
    }
}
