package cloud.wgcloud.v2.xml.dom4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import cloud.wgcloud.v2.xml.bean.name;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author guichun
 */
public class ReadXMLByDom4j {
    private List<name> namesList = null;
    private name names = null;

    public List<name> getBooks(File file){

        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element bookstore = document.getRootElement();
            Iterator storeit = bookstore.elementIterator();

            namesList = new ArrayList<name>();
            while(storeit.hasNext()){

                names = new name();
                Element bookElement = (Element) storeit.next();
                //遍历bookElement的属性
                List<Attribute> attributes = bookElement.attributes();
                for(Attribute attribute : attributes){
                    if(attribute.getName().equals("ids")){
                        String ids = attribute.getValue();
                        names.setFlesId(Integer.parseInt(ids));
                        System.out.println("ids:"+ids);
                    }
                }

                Iterator bookit = bookElement.elementIterator();
                while(bookit.hasNext()){
                    Element child = (Element) bookit.next();
                    List<Attribute> attributes1 = child.attributes();
                    for (Attribute attribute : attributes1){
                        if (attribute.getName().equals("id")){
                            String id = attribute.getValue();
                            names.setId(Integer.parseInt(id));
                            System.out.println("id:"+id);
                        }
                    }

                    String nodeName = child.getName();
                    if(nodeName.equals("flName")){
                        String name = child.getStringValue();
                        names.setFlName(name);
                        System.out.println("name:"+name);
                    }
                }
//                Iterator bookit1 = child.elementIterator();
//                while (bookit1.hasNext()){
//                    Element child1 = (Element) bookit1.next();
//                    String nodeName = child1.getName();
//                    System.out.println("nodeName"+nodeName);
//
//                }



                namesList.add(names);
                names = null;

            }
        } catch (DocumentException e) {

            e.printStackTrace();
        }


        return namesList;

    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File file = new File("/Users/guichun/PictureToName/config.xml");
        List<name> nameList = new ReadXMLByDom4j().getBooks(file);
        for(name namess : nameList){
            System.out.println(namess);
        }
    }
}
