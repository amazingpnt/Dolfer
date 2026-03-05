import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader=new FXMLLoader(new File("ui.fxml").toURI().toURL());
        String css=new File("ui.css").toURI().toURL().toExternalForm();

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root, 500, 400);

        scene.getStylesheets().add(css);
        stage.setTitle("Main");
        root.setScaleX(1.5);
        root.setScaleY(1.5);
        

        stage.setScene(scene);
        stage.show();
    }

    public static void main(){
        launch();
    }
}

