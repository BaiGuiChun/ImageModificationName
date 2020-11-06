package cloud.wgcloud.v6;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.swing.filechooser.FileSystemView;
import javax.swing.text.AbstractDocument;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class XmlToName extends Application {

    //文件保存路径
    File fileesss;

    //加载xml文件
    public static Document load(){
        /**
         * 获取桌面路径
         */
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File file1Path = fileSystemView.getHomeDirectory();
        String UserPath = System.getProperty("user.dir");

        System.out.println("UserPath:"+UserPath);
        System.out.println(file1Path); // /Users/guichun

        Document document=null;
        String url=UserPath + "/config.xml";

        try {
            SAXBuilder reader = new SAXBuilder();
            document=reader.build(new File(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        // 获取桌面路径
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File file1Path = fileSystemView.getHomeDirectory();

        BorderPane borderPane = new BorderPane();
        Accordion accordion = new Accordion();//手风琴


        VBox vbox = new VBox(); //手风琴
        vbox.setPadding(new Insets(10,10,10,10));


        VBox vbox1 = new VBox(5); //正方形
        vbox1.setPadding(new Insets(10,10,10,10));

        VBox vbox2 = new VBox(2); //输入框
        vbox2.setPadding(new Insets(10,10,0,10));


        //事件
        MenuBar menuBar = new MenuBar();

        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        Menu menu = new Menu("XML管理");
        MenuItem menuItem = new MenuItem("重新加载XML文件");
        menu.getItems().add(menuItem);
        menuBar.getMenus().add(menu);
        menuItem.setOnAction((event) ->{
            System.out.println("重新加载XML文件");
            accordion.getPanes().clear();

            Document document=null;
            document=load();
            Element documentRootElement = document.getRootElement();
            List<Element> elementList = documentRootElement.getChildren();

            for (Element e:elementList) {
                List<Element> elementList1 = e.getChildren();

                TitledPane titledPane = new TitledPane();
                titledPane.setText(e.getAttribute("tit").getValue());

                VBox vbox3 = new VBox(5); //手风琴内容
                vbox3.setPadding(new Insets(10,0,10,0));

                for (Element element : elementList1) {
                    List<Element> elementList2 = element.getChildren();
                    Button button2 = new Button();
                    button2.setPadding(new Insets(10,30,10,30));
                    button2.setText(element.getAttribute("tit_son").getValue());
                    System.out.println("tit_son:"+element.getAttribute("tit_son").getValue());
                    vbox3.getChildren().add(button2);
                    titledPane.setContent(vbox3);

                    //手风琴-事件
                    button2.setOnAction((ActionEvent event1) ->{
                        System.out.println("but点击事件");
                        vbox1.getChildren().clear();
                        vbox2.getChildren().clear();

                        for (Element element1 : elementList2){
                            System.out.println("names:"+element1.getChildText("names"));

                            String names = element1.getChildText("names");
                            String urls = element1.getChildText("urls");
                            String explain = element1.getChildText("explain");

                            Rectangle rectangle = new Rectangle(20,20,300, 200);
                            rectangle.setFill(Color.rgb(101, 147, 152));
                            rectangle.setArcWidth(15);
                            rectangle.setArcHeight(15);

                            HBox hBox2=new HBox();


                            if (explain != null){
                                System.out.println("explain:"+ explain);
                                Text text = new Text();
                                text.setText("备注："+explain);
                                vbox1.getChildren().add(text);
                            }if (names !=null){
                                System.out.println("names:"+ names);
                                Text text = new Text();
                                text.setText("名称："+names);
                                vbox1.getChildren().add(text);

                            }if (urls != null){
                                System.out.println("urls:"+ file1Path+urls);
                                Text text = new Text();
                                text.setText("路径："+file1Path+urls);
                                vbox1.getChildren().add(text);
                            }

                            //设置拖曳
                            rectangle.setOnDragOver(new EventHandler<DragEvent>() {
                                @Override
                                public void handle(DragEvent event) {
                                    if (event.getGestureSource() != rectangle
                                            && event.getDragboard().hasFiles()) {
                                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                                    }
                                    event.consume();
                                }
                            });
                            rectangle.setOnDragDropped(new EventHandler<DragEvent>() {
                                @Override
                                public void handle(DragEvent event) {
                                    Dragboard db = event.getDragboard();
                                    boolean success = false;

                                    List<File> list = db.getFiles();

                                    if (db.hasFiles()) {
                                        list.stream().forEach((file) -> {
                                            String str = file.getName();//8265.jpg
                                            String prefix = str.substring(str.lastIndexOf("."));
                                            int num = prefix.length();//得到后缀名长度

                                            fileesss = new File(file1Path+urls);

                                            if (!fileesss.exists()) {
                                                fileesss.mkdirs();
                                            }
                                            File oldImg = new File(file.getPath());
                                            System.out.println("原图片：" + oldImg);


                                            File newImg = new File(fileesss + "/" + names + ".png");

                                            System.out.println("修改后：" + newImg);

                                            try {
                                                Files.copy(oldImg.toPath(),newImg.toPath());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        });
                                        success = true;
                                    }
                                    event.setDropCompleted(success);

                                    event.consume();

                                }
                            });

                            vbox1.getChildren().add(rectangle);
                            Separator separator = new Separator();
                            separator.setPadding(new Insets(20,0,10,0));
                            vbox1.getChildren().add(separator);


                            hBox2.getChildren().addAll(vbox1, vbox2);

                            borderPane.setCenter(hBox2);

                        }


                    });
                }
                accordion.getPanes().add(titledPane);



            }
        });
        menuItem.fire();// 调用menuItem的点击方法



        borderPane.setTop(menuBar);
        vbox.getChildren().add(accordion);
        borderPane.setLeft(vbox);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(500);
        scrollPane.setPrefHeight(1000);

        scrollPane.setContent(borderPane);


        Scene scene = new Scene(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("辅助开发ByGuiChun");
        primaryStage.setWidth(610);
        primaryStage.setHeight(1100);
        primaryStage.show();

    }
}
