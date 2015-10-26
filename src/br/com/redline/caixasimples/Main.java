package br.com.redline.caixasimples;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private String pageTitle = "Caixa";

	@Override
	public void start(Stage primaryStage) {
		//String  originalPassword = "password";
        //String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        //System.out.println(generatedSecuredPasswordHash);
         
        //boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
        
		// Inicia a janela principal
		this.primaryStage = primaryStage;
		this.primaryStage.setFullScreen(false);
		this.primaryStage.setFullScreenExitHint("");
		this.primaryStage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		
		// Defini titulo
		this.primaryStage.setTitle(pageTitle);
		
		// Inicia o RootLayout
		initRootLayout();
		
		// Exibe a view do caixa
		showViewCaixa();
	}

	// Realiza a inicialização da janela princpal
	public void initRootLayout() {
		try {
			// Carrega o root layout do arquivo fxml.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Mostra a scene contendo o root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Carrega a view 'Caixa' dentro do RootLayout
	public void showViewCaixa() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Caixa.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
