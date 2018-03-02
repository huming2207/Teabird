import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TeabirdApplication extends Application
{
    private Stage primaryStage;

    public static void main(String[] args)
    {
        Application.launch(TeabirdApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Teabird");
        initRootLayout();
    }

    public void initRootLayout()
    {
        try {

            // Load layout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TeabirdApplication.class.getResource("view/Home.fxml"));

            // Show the scene from the layout
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
