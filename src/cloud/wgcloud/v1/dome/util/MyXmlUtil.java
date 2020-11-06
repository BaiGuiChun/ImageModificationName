package cloud.wgcloud.v1.dome.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * @author guichun
 */
public class MyXmlUtil {

    public static void main(String[] args) {
        Document document=null;
        document=load();
        Element documentRootElement = document.getRootElement();//xmlData

        List<Element> elementList = documentRootElement.getChildren("fles");

        for (Element element : elementList){
            List<Element> s = element.getChildren("name");

            for (Element element1 : s){
                System.out.println(element1.getChild("names").getValue());
//                System.out.println(element1.getValue());
            }
        }
    }


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

    /**
     * 将xml文件转换为String串
     * @return
     */
    public static String XmlToString(){
        Document document=null;
        document=load();

        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");//设置编码格式

        StringWriter out=null; //输出对象
        String sReturn =""; //输出字符串
        XMLOutputter outputter =new XMLOutputter();
        out=new StringWriter();
        try {
            outputter.output(document,out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sReturn=out.toString();
        return sReturn;
    }


}
