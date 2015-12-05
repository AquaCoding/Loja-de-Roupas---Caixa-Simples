package br.com.redline.caixasimples;

import java.io.IOException;

import br.com.redline.caixasimples.controller.CriarClienteController;
import br.com.redline.caixasimples.controller.criarUsuarioController;
import br.com.redline.caixasimples.model.Cliente;
import br.com.redline.caixasimples.model.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	private static Stage primaryStage;
	private static Stage loginStage = new Stage();
	private static BorderPane rootLayout;
	private static String pageTitle = "Caixa";

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
	public static void initLoginLayout() {
		try {
			if(loginStage == null) {
				loginStage.setResizable(false);
				loginStage.initModality(Modality.APPLICATION_MODAL);
				loginStage.setAlwaysOnTop(true);
			}
			
			if(!Usuario.haveUsuario()) {
				Alert a = new Alert(AlertType.INFORMATION);
	            a.setTitle("Primeiro acesso");
	            a.setHeaderText(null);
	            a.setContentText("É preciso criar um usuario");
	            a.showAndWait();
	            initPrimeiroAcesso();
			} else {
				// Carrega o root layout do arquivo fxml.
				Parent root = FXMLLoader.load(Main.class
						.getResource("view/Login.fxml"));
				Scene scene = new Scene(root, 300, 150);
				loginStage.setTitle(pageTitle + " - Entrar");
				loginStage.setScene(scene);
				loginStage.show();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Fecha a janela de login
	public static void endLoginLayout() {
		loginStage.close();
	}
	
	// Realiza a inicialização da janela princpal
	public static void initPrimeiroAcesso() {
		try {
			// Carrega o root layout do arquivo fxml.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CriarUsuario.fxml"));
			
			Parent root = loader.load();
			
			criarUsuarioController controller = loader.getController();
			controller.setCloseAfterCreate(true);
			
			Scene scene = new Scene(root, 273, 223);
			loginStage.setTitle(pageTitle + " - Criando primeiro usuario");
			loginStage.setScene(scene);
			loginStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	// Carrega a view 'CriarCliente' dentro do RootLayout
	public static void showViewCriarCliente(Cliente cliente) {
		try {
			primaryStage.setTitle(pageTitle + " - Cadastro de Cliente");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/CriarCliente.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			
			if(cliente != null) {
				CriarClienteController controller = loader.getController();
				controller.setCliente(cliente);
			}
			
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Carrega a view 'EntradaEstoque' dentro do RootLayout
	public static void showViewEntradaEstoque() {
		try {
			primaryStage.setTitle(pageTitle + " - Entrada no estoque");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/EntradaEstoque.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Carrega a view 'Usuario' dentro do RootLayout
	public static void showUsuarios() {
		try {
			primaryStage.setTitle(pageTitle + " - Todos os usuarios");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Usuario.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Carrega a view 'Produto' dentro do RootLayout
	public static void showProdutos() {
		try {
			primaryStage.setTitle(pageTitle + " - Todos os produtos");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Produtos.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(personOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Carrega a view 'Cliente' dentro do RootLayout
	public static void showClientes() {
		try {
			primaryStage.setTitle(pageTitle + " - Todos os clientes");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Cliente.fxml"));
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
