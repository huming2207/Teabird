import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application
{
    private Stage primaryStage;

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
            loader.setLocation(MainApplication.class.getResource("view/Home.fxml"));

            // Show the scene from the layout
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
