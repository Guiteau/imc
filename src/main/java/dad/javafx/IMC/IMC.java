package dad.javafx.IMC;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	private TextField pesoText;
	private TextField alturaText;
	private DoubleProperty pesoDouble = new SimpleDoubleProperty();
	private DoubleProperty alturaDouble = new SimpleDoubleProperty();
	private DoubleProperty resultado = new SimpleDoubleProperty();
	private StringProperty cantidadString = new SimpleStringProperty("");
	private Label resultadoLabel, clasificacionLabel, pesoLabel, kgLabel, alturaLabel, cmLabel;

	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoText = new TextField("0");
		pesoText.setPrefWidth(70);
		alturaText = new TextField("0");
		alturaText.setPrefWidth(70);

		alturaLabel = new Label("Altura: ");
		pesoLabel = new Label("Peso: ");
		kgLabel = new Label(" kg");
		cmLabel = new Label(" cm");
		resultadoLabel = new Label();
		clasificacionLabel = new Label();

		HBox upperHbox = new HBox(pesoLabel, pesoText, kgLabel);
		upperHbox.setAlignment(Pos.CENTER);
		HBox middleHBox = new HBox(alturaLabel, alturaText, cmLabel);
		middleHBox.setAlignment(Pos.CENTER);
		HBox bottomHbox = new HBox(resultadoLabel);
		bottomHbox.setAlignment(Pos.CENTER);

		VBox root = new VBox(5, upperHbox, middleHBox, bottomHbox, clasificacionLabel);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();

		// BINDEOS

		Bindings.bindBidirectional(pesoText.textProperty(), pesoDouble, new NumberStringConverter());

		Bindings.bindBidirectional(alturaText.textProperty(), alturaDouble, new NumberStringConverter());

		DoubleBinding resultadoIMC = ((pesoDouble.divide(alturaDouble.multiply(alturaDouble))).multiply(10000)); // para
																													// que
																													// salga
																													// en
																													// decenas
		
		resultado.bind(resultadoIMC);
		
		cantidadString.set("IMC: ");

		resultadoLabel.textProperty().bind(cantidadString.concat(resultado.asString("%.2f\n"))); // formateamos la salida del resultado para
																			// que salga con dos decimales
		

		//LISTENER
		
		resultadoIMC.addListener((observable, oldValue, newValue) -> clasificaPeso(newValue));
		
	}
	
	public void clasificaPeso(Number newValue) {
		
		if (newValue.doubleValue() < 18.5) {

			clasificacionLabel.setText("Bajo peso");
			
		} else if (newValue.doubleValue() >= 18.5 && newValue.doubleValue() < 25) {
			
			clasificacionLabel.setText("Normal");
			
		} else if (newValue.doubleValue() >= 25 && newValue.doubleValue() < 30) {
			
			clasificacionLabel.setText("Sobrepeso");

		} else {
			
			clasificacionLabel.setText("Obeso");

		}
			
	}

	public static void main(String[] args) {

		launch(args);

	}

}
