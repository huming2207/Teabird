package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import twitter4j.FilterQuery;
import worker.TeabirdApplication;
import worker.Worker;
import worker.config.FilterQueryGenerator;

import java.io.File;

public class HomeController
{
    private TeabirdApplication teabirdApplication;

    @FXML
    private TextField consumerTokenTextfield;

    @FXML
    private TextField consumerSecretTextfield;

    @FXML
    private TextField accessTokenTextfield;

    @FXML
    private TextField accessSecretTextfield;

    @FXML
    private TextArea keywordTextArea;

    @FXML
    private TextArea userIdTextArea;

    @FXML
    private CheckBox strictFilterCheckbox;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button settingSaveButton;

    @FXML
    private ToggleButton startStopToggleButton;

    @FXML
    private Button setOutputButton;

    @FXML
    private Button saveSettingsButton;

    @FXML
    private void handleApiSettingButton()
    {

    }

    @FXML
    private void handleStartStopToggleButton()
    {
        startStopToggleButton.setSelected(!startStopToggleButton.isSelected());

        if(startStopToggleButton.isSelected()) {
            Worker worker = new Worker(new FilterQuery());

        }
    }

    @FXML
    private void handleSetOutputButton()
    {
        File file = new DirectoryChooser().showDialog(new Stage());
    }

    @FXML
    private void handleCrawlerSettingButton()
    {

    }


    public void setMainApp(TeabirdApplication teabirdApplication)
    {
        this.teabirdApplication = teabirdApplication;
    }

    public void appendDebugText(String text)
    {
        Platform.runLater(() -> outputTextArea.appendText(text));
    }


}
