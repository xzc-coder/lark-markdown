package com.github.lark.markdown;

import cn.hutool.log.Log;
import com.github.lark.markdown.controller.MainController;
import com.github.lark.markdown.utils.ElementUtil;
import com.github.lark.markdown.utils.FxUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

/**
 * @Author: xy-code
 * @Description: Main窗口的Application
 * @Date: 2023-10-29 16:27
 **/
public class MainApplication extends Application {

    private static final Log log = Log.get();

    @Override
    public void start(Stage stage) throws Exception {
        log.info("---程序开始启动---");
        URL resource = FxUtil.getResourcesUrl(Constants.MAIN_UI_FILE_NAME);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        Pane root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.initialize(stage);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.setTitle(Constants.MAIN_STAGE_TITLE);
        stage.getIcons().add(new Image(Constants.MAIN_ICON_FILE_NAME));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        log.info("---程序启动完成---");
    }

    @Override
    public void stop() throws Exception {
        log.info("---程序关闭---");
        try {
            super.stop();
            ElementUtil.closeCurrentDriver();
        } finally {
            //强制退出，因为某些资源可能未释放无法关闭
            System.exit(0);
        }

    }


}
