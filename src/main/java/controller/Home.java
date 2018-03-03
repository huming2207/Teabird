package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import worker.TeabirdApplication;

public class Home
{
    @FXML
    private TextField consumerTokenTextfield;

    @FXML
    private TextField consumerSecretTextfield;

    @FXML
    private TextField accessTokenTextfield;

    @FXML
    private TextField accessSecretTextfield;

    @FXML
    private TextField keywordListPathTextfield;

    @FXML
    private TextField useridListPathTextfield;

    @FXML
    private TextField outputPathTextfield;

    @FXML
    private TextArea outputTextarea;

    private TeabirdApplication teabirdApplication;

    public void setMainApp(TeabirdApplication teabirdApplication)
    {
        this.teabirdApplication = teabirdApplication;
    }

    public void appendDebugText(String text)
    {
        Platform.runLater(() -> outputTextarea.appendText(text));
    }
}
