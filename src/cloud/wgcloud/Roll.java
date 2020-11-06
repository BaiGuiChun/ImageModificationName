package cloud.wgcloud;

import cloud.wgcloud.v2.xml.bean.name;
import cloud.wgcloud.v2.xml.dom4j.ReadXMLByDom4j;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author guichun
 */
public class Roll extends Application {


    /**
     * 获取桌面路径
     */
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    File file1Path = fileSystemView.getHomeDirectory();

    File files = new File(file1Path + "/PictureToName/config.xml");
    List<name> nameList = new ReadXMLByDom4j().getBooks(files);


    Button buttonRule = new Button("新增自定义规则");
    Button buttonLoad = new Button("加载xml规则");

    javafx.scene.control.TextField textField = new javafx.scene.control.TextField();
    javafx.scene.control.TextField textField1 = new TextField();


    File fileesss;

    int is = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();

        /**
         * 声明
         */
        primaryStage.setAlwaysOnTop(true);

        buttonRule.setStyle("-fx-font: 20 arial; -fx-base: #0076ee; ");
        buttonRule.setLayoutX(160.0);
        buttonRule.setLayoutY(20.0);

        buttonLoad.setStyle("-fx-font: 20 arial; -fx-base: #a3ee64; ");
        buttonLoad.setLayoutX(10.0);
        buttonLoad.setLayoutY(20.0);

        HBox hBox = new HBox();


        VBox vbox = new VBox(10);
        VBox vbox1 = new VBox();


        vbox.getChildren().add(buttonLoad);
        vbox1.getChildren().add(buttonRule);

        /**
         * 事件
         */
        //加载xml并生成相应的输入框
        buttonLoad.setOnAction(
                (ActionEvent event) -> {
                    for (name namess : nameList) {

                        is = namess.getId();

                        System.out.println("namess:" + namess.getNames());
                        textField.setText(namess.getNames());
                        textField1.setText(namess.getUrls());


                        System.out.println("循环加载xml事件");

                        TextField textField2 = new TextField();
                        TextField textField3 = new TextField();
                        Text textField6 = new Text();

                        textField2.setText(namess.getNames());
                        textField3.setText(namess.getUrls());
                        textField6.setText(namess.getExplain());


                        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(100, 100);

                        rect.setArcHeight(15);
                        rect.setArcWidth(15);


                        rect.setFill(javafx.scene.paint.Color.rgb(101, 147, 152));

                        //设置拖曳
                        rect.setOnDragOver(new EventHandler<DragEvent>() {
                            @Override
                            public void handle(DragEvent event) {
                                if (event.getGestureSource() != rect
                                        && event.getDragboard().hasFiles()) {
                                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                                }
                                event.consume();
                            }
                        });
                        rect.setOnDragDropped(new EventHandler<DragEvent>() {
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

                                        fileesss = new File(textField3.getText());

                                        if (!fileesss.exists()) {
                                            fileesss.mkdirs();
                                        }
                                        File oldImg = new File(file.getPath());
                                        System.out.println("原图片：" + oldImg);


                                        File newImg = new File(fileesss + "/" + textField2.getText() + ".png");

                                        System.out.println("修改后：" + newImg);

                                        try {
                                            Files.copy(oldImg.toPath(), newImg.toPath());
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


                        vbox.getChildren().add(rect);
                        vbox.setPadding(new Insets(0, 10, 20, 10));

                        VBox vbox2 = new VBox();

                        vbox2.getChildren().add(textField2);
                        vbox2.getChildren().add(textField3);
                        vbox2.getChildren().add(textField6);

                        vbox2.setPadding(new Insets(10, 0, 10, 0));
                        vbox2.setSpacing(10);

                        vbox1.getChildren().add(vbox2);
                    }
                });

        //按钮点击事件
        buttonRule.setOnAction(
                (final ActionEvent event) -> {

                    System.out.println("按钮点击事件");

                    javafx.scene.shape.Rectangle rect = new Rectangle(100, 100);
                    TextField textField4 = new TextField();
                    textField4.setText("输入需要修改的名称.");

                    TextField textField5 = new TextField();
                    textField5.setText("输入图片保存的路径.");

                    vbox.getChildren().add(rect);
                    vbox.setPadding(new Insets(0, 10, 20, 10));

                    VBox vbox2 = new VBox();

                    vbox2.getChildren().add(textField4);
                    vbox2.getChildren().add(textField5);

                    vbox2.setPadding(new Insets(10, 0, 40, 0));
                    vbox2.setSpacing(10);

                    vbox1.getChildren().add(vbox2);


                    rect.setArcHeight(15);
                    rect.setArcWidth(15);
                    rect.setFill(Color.rgb(101, 147, 152));

                    //设置拖曳
                    rect.setOnDragOver(new EventHandler<DragEvent>() {
                        @Override
                        public void handle(DragEvent event) {
                            if (event.getGestureSource() != rect
                                    && event.getDragboard().hasFiles()) {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            }
                            event.consume();
                        }
                    });
                    rect.setOnDragDropped(new EventHandler<DragEvent>() {
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

                                    fileesss = new File(textField5.getText());

                                    if (!fileesss.exists()) {
                                        fileesss.mkdirs();
                                    }
                                    File oldImg = new File(file.getPath());
                                    System.out.println("原图片：" + oldImg);

                                    File newImg = new File(fileesss + "/" + textField4.getText() + ".png");

                                    System.out.println("修改后：" + newImg);

                                    try {
                                        Files.copy(oldImg.toPath(), newImg.toPath());
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

                });


        hBox.getChildren().addAll(vbox, vbox1);


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(1100);

        scrollPane.setContent(hBox);
        anchorPane.getChildren().add(scrollPane);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("辅助开发ByGuiChun");
        primaryStage.setWidth(610);
        primaryStage.setHeight(1200);
        primaryStage.show();

    }
}
