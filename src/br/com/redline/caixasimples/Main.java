package br.com.redline.caixasimples;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	private static Stage primaryStage;
	private static Stage loginStage = new Stage();
	private static BorderPane rootLayout;
	private String pageTitle = "Caixa";

	@Override
	public void start(Stage stage) {
		// Inicia a janela principal
		primaryStage = stage;
		primaryStage.setFullScreen(false);
		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(new KeyCodeCombination(
				KeyCode.E, KeyCombination.CONTROL_DOWN));

		// Defini titulo
		primaryStage.setTitle(pageTitle);

		initLoginLayout();
	}

	// Realiza a inicialização da janela de login
	public void initLoginLayout() {
		try {
			// Carrega o root layout do arquivo fxml.
			Parent root = FXMLLoader.load(Main.class
					.getResource("view/Login.fxml"));
			Scene scene = new Scene(root, 300, 150);
			loginStage.setTitle(pageTitle + " - Entrar");
			loginStage.setScene(scene);
			loginStage.setResizable(false);
			loginStage.initModality(Modality.APPLICATION_MODAL);
			loginStage.setAlwaysOnTop(true);
			loginStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Fecha a janela de login
	public static void endLoginLayout() {
		loginStage.hide();
	}

	// Realiza a inicialização da janela princpal
	public static void initRootLayout() {
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
	public static void showViewCaixa() {
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
