import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Initialize Server");
        try{
            Parent root = FXMLLoader.load(getClass().getResource("initializeFXML.fxml"));
            Scene scene = new Scene(root, 750, 750);
            //scene.getStylesheets().add("style.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
