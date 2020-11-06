package cloud.wgcloud.v5;

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
import javafx.stage.Stage;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.swing.filechooser.FileSystemView;
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

        Button button = new Button("加载xml文件");


        HBox hBox1 = new HBox(10); //top 按钮
        hBox1.setPadding(new Insets(10,10,0,10));

        VBox vbox = new VBox(); //手风琴
        vbox.setPadding(new Insets(10,10,10,10));

        HBox hBox2 = new HBox(10); // 存放vbox1和vbox2


        VBox vbox1 = new VBox(10); //正方形
        vbox1.setPadding(new Insets(10,10,10,10));

        VBox vbox2 = new VBox(2); //输入框
        vbox2.setPadding(new Insets(10,10,20,10));


        //事件
        button.setOnAction((ActionEvent event) ->{
            System.out.println("事件-加载xml");


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
//                    System.out.println("element:"+element.getChildren());
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

                            Rectangle rectangle = new Rectangle(100, 100);
                            rectangle.setFill(Color.rgb(101, 147, 152));

                            if (names !=null){
                                System.out.println("names:"+ names);
                                TextField textField = new TextField();
                                textField.setText(names);
                                vbox2.getChildren().add(textField);

                            }if (urls != null){
                                System.out.println("urls:"+ file1Path+urls);
                                TextField textField = new TextField();
                                textField.setText(file1Path+urls);
                                vbox2.getChildren().add(textField);

                            }if (explain != null){
                                System.out.println("explain:"+ explain);
                                Text text = new Text();
                                text.setText(explain);
                                Separator separator = new Separator();
                                separator.setPadding(new Insets(10,10,20,10));
                                vbox2.getChildren().add(text);
                                vbox2.getChildren().add(separator);

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

                        }

                    });
                }
                accordion.getPanes().add(titledPane);



            }
        });


        hBox1.getChildren().addAll(button);

        borderPane.setTop(hBox1);
        vbox.getChildren().add(accordion);
        borderPane.setLeft(vbox);

        hBox2.getChildren().addAll(vbox1, vbox2);

        borderPane.setCenter(hBox2);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(1100);

        scrollPane.setContent(borderPane);


        Scene scene = new Scene(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("辅助开发ByGuiChun");
        primaryStage.setWidth(610);
        primaryStage.setHeight(1200);
        primaryStage.show();

    }
}
