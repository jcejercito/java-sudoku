package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private static Stage guiStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			guiStage = primaryStage;
			
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("Basic Sudoku by James Ejercito");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	//Useful to switch scenes
	public static Stage getStage() {
        return guiStage;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
