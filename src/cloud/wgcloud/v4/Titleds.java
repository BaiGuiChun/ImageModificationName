package cloud.wgcloud.v4;

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

/**
 * @author guichun
 */
public class Titleds extends Application {

    //文件保存路径
    File fileesss;

    //加载xml文件
    public static Document load(){
        /**
         * 获取桌面路径
         */
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File file1Path = fileSystemView.getHomeDirectory();

        Document document=null;
        String url=file1Path + "/PictureToName/config.xml";
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

        BorderPane borderPane = new BorderPane();
        Accordion accordion = new Accordion();//手风琴

        Button button = new Button("加载xml文件");
        Button button1 = new Button("新增自定义规则");


        HBox hBox1 = new HBox(10); //top 按钮
        hBox1.setPadding(new Insets(10,10,0,10));
        HBox hBox2 = new HBox(10);

        VBox vbox = new VBox(); //手风琴
        vbox.setPadding(new Insets(10,10,10,10));
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

            for (Element e:elementList){
                List<Element> elementList1 = e.getChildren();

                //手风琴
                TitledPane titledPane = new TitledPane(e.getAttribute("tit").getValue(), new Button("titledPane"));
                Button button2 = new Button();
                button2.setText(e.getAttribute("tit").getValue());

                titledPane.setContent(button2);

                accordion.getPanes().add(titledPane);

                button2.setOnAction((ActionEvent event1)->{
                    vbox1.getChildren().clear();
                    vbox2.getChildren().clear();

                    for (Element element : elementList1){
                        String names = element.getChildText("names");
                        String urls = element.getChildText("urls");
                        String explain = element.getChildText("explain");

                        Rectangle rectangle = new Rectangle(100, 100);
                        rectangle.setFill(Color.rgb(101, 147, 152));

                        if (names !=null){
                            System.out.println("names:"+ names);
                            TextField textField = new TextField();
                            textField.setText(names);
                            vbox2.getChildren().add(textField);

                        }if (urls != null){
                            System.out.println("urls:"+ urls);
                            TextField textField = new TextField();
                            textField.setText(urls);
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


                        //事件
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

                                        fileesss = new File(urls);

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
        });



        button1.setOnAction((ActionEvent event) ->{
            System.out.println("事件-新增自定义规则");

            Rectangle rectangle1 = new Rectangle(100, 100);
            rectangle1.setFill(Color.rgb(101, 147, 152));

            TextField textField = new TextField();
            TextField textField2 = new TextField();
            textField.setText("输入名称");
            textField2.setText("输入路径");
            Separator separator = new Separator();
            separator.setPadding(new Insets(10,10,40,10));

            vbox2.getChildren().add(textField);
            vbox2.getChildren().add(textField2);
            vbox2.getChildren().add(separator);

            //设置拖曳
            rectangle1.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    if (event.getGestureSource() != rectangle1
                            && event.getDragboard().hasFiles()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                }
            });
            rectangle1.setOnDragDropped(new EventHandler<DragEvent>() {
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

                            fileesss = new File(textField2.getText());

                            if (!fileesss.exists()) {
                                fileesss.mkdirs();
                            }
                            File oldImg = new File(file.getPath());
                            System.out.println("原图片：" + oldImg);


                            File newImg = new File(fileesss + "/" + textField.getText() + ".png");

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
            vbox1.getChildren().add(rectangle1);
        });

        hBox1.getChildren().addAll(button, button1);

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
