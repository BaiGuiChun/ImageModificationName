package cloud.wgcloud;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import cloud.wgcloud.v2.xml.bean.name;
import cloud.wgcloud.v2.xml.dom4j.ReadXMLByDom4j;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author guichun
 * 设置拖曳
 */
public class Drag extends Application {

    /**
     * 获取桌面路径
     */
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    File file1Path = fileSystemView.getHomeDirectory();
    Label label = new Label();



    File files = new File(file1Path + "/PictureToName/config.xml");
    List<name> nameList = new ReadXMLByDom4j().getBooks(files);

    //获取桌面分辨率
    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screensize.getWidth();
    int height = (int) screensize.getHeight();

    Button buttonRule = new Button("新增自定义规则");
    Button buttonLoad = new Button("加载xml规则");

    TextField textField = new TextField();
    TextField textField1 = new TextField();


    Group group = new Group();


    File fileesss;
    Integer x = 20, y = 60;
    Integer tx = 40, ty = 70;
    Integer ux = 40, uy = 100;
    Integer ux6 = 40, uy6 = 140;


    int is = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /**
         * 声明
         */
        primaryStage.setTitle("批量修改图片名称");
        primaryStage.setAlwaysOnTop(true);
        Scene scene = new Scene(group, 340, height - 200);

        buttonRule.setStyle("-fx-font: 20 arial; -fx-base: #0076ee; ");
        buttonRule.setLayoutX(160.0);
        buttonRule.setLayoutY(20.0);

        buttonLoad.setStyle("-fx-font: 20 arial; -fx-base: #a3ee64; ");
        buttonLoad.setLayoutX(10.0);
        buttonLoad.setLayoutY(20.0);

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


                        Rectangle rect = new Rectangle(x, y, 200, 200);
                        textField2.setLayoutX(tx);
                        textField2.setLayoutY(ty);
                        textField3.setLayoutX(ux);
                        textField3.setLayoutY(uy);
                        textField6.setLayoutX(ux6);
                        textField6.setLayoutY(uy6);
                        textField6.setFill(Color.WHITE);
                        textField6.setWrappingWidth(180);

                        ty += 230;
                        y += 230;
                        uy += 230;
                        uy6 += 230;

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

                                        fileesss = new File(textField3.getText());

                                        if (!fileesss.exists()) {
                                            fileesss.mkdirs();
                                        }
                                        File oldImg = new File(file.getPath());
                                        System.out.println("原图片：" + oldImg);


                                        File newImg = new File(fileesss + "/" + textField2.getText() + ".png");

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

                        group.getChildren().add(rect);
                        group.getChildren().add(textField2);
                        group.getChildren().add(textField3);
                        group.getChildren().add(textField6);

                    }
                });

        //按钮点击事件
        buttonRule.setOnAction(
                (final ActionEvent event) -> {

                    System.out.println("按钮点击事件");

                    Rectangle rect = new Rectangle(x, y, 200, 200);
                    TextField textField4 = new TextField();
                    textField4.setLayoutX(tx);
                    textField4.setLayoutY(ty);
                    textField4.setText("输入需要修改的名称.");

                    TextField textField5 = new TextField();
                    textField5.setLayoutX(ux);
                    textField5.setLayoutY(uy);
                    textField5.setText("输入图片保存的路径.");

                    ty += 230;
                    y += 230;
                    uy += 230;


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


                    group.getChildren().add(rect);
                    group.getChildren().add(textField4);
                    group.getChildren().add(textField5);

                });

        group.getChildren().
                addAll(buttonRule, buttonLoad, label);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //文件格式
    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

    }

    public static void main(String[] args) {
        launch(args);
    }

}
