package es.uji.al426285;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.FileSystems;


/**
 * JavaFX App
 */
public class App extends Application {
    String sep = FileSystems.getDefault().getSeparator();
    private String ruta_icono = "src" + sep + "Files" + sep;
    private Button confirmar;
    ToggleGroup opciones = new ToggleGroup();
    RadioButton opcion_alta = new RadioButton("Registrar un usuario");
    RadioButton opcion_consulta = new RadioButton("Realizar una consulta");
    RadioButton opcion_operar = new RadioButton("Realizar una operación");
    ////////////////////////////////////////
    Stage ventana_alta = new Stage();
    Button confirmar_alta = new Button("Aceptar");
    Label titulo = new Label("Introduce los datos del nuevo usuario");
    Label nombre = new Label("Nombre:");
    TextField nombree = new TextField();
    Label apellidos = new Label("Apellidos:");
    TextField apellidoss = new TextField();
    Label correo = new Label("Correo:");
    TextField correoo = new TextField();
    Label telefono = new Label("Teléfono:");
    TextField telefonoo = new TextField();
    Label dni = new Label("DNI:");
    TextField dnii = new TextField();
    Label direccion = new Label("Dirección:");
    TextField direccionn = new TextField();
    Label fecha_nacimiento = new Label("Fecha de nacimiento:");
    DatePicker fecha_nacimientoo = new DatePicker();
///////////////////////////////////////////////


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //icono de la ventana principal
        InputStream entrada = new FileInputStream(ruta_icono + "imagen.png");
        Image imagen = new Image(entrada);
        stage.getIcons().add(imagen);
        //nombre de ventana principal
        stage.setTitle("Haytam's Central Bank");
        //caja vertical donde irá el titulo y caja vertical donde iran las opciones
        VBox izquierda = primera_vista_izquierda();
        VBox derecha = primera_vista_derecha();
        HBox hbox = new HBox(izquierda, derecha);
        // para que ambas VBox crezcan y ocupen el mismo espacio disponible
        HBox.setHgrow(izquierda, Priority.ALWAYS);
        HBox.setHgrow(derecha, Priority.ALWAYS);
        //permitir que ambas VBox  crezcan hasta ocupar el ancho máximo disponible.
        izquierda.setMaxWidth(Double.MAX_VALUE);
        derecha.setMaxWidth(Double.MAX_VALUE);
//        BorderPane borderPane=new BorderPane();
//        borderPane.setLeft(izquierda);borderPane.setRight(derecha);borderPane.setCenter(new Label("HOLAAAAAAA"));
        //boton aceptar
//        confirmar.setDisable(true);
//        opcion_alta.setOnAction(e -> confirmar.setDisable(false));
//        opcion_consulta.setOnAction(e -> confirmar.setDisable(false));
//        opcion_operar.setOnAction(e -> confirmar.setDisable(false));
        confirmar.setOnAction(e -> {
            try {
                if (opcion_alta.isSelected()) {
                    ventana2();
                } else if (opcion_consulta.isSelected()) {
                    System.out.println("ventana3");

                } else if (opcion_operar.isSelected()) {
                    System.out.println("ventana4");
                } else {// no haria falta esto si uso el disable
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Advertencia");
                    alerta.setHeaderText("¡ADVERTENCIA!");
                    alerta.setContentText("Por favor, selecciona una opción.");
                    alerta.showAndWait();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        confirmar_alta.setOnAction(e -> {
            if (nombree.getText().isEmpty() || apellidoss.getText().isEmpty() || correoo.getText().isEmpty() || telefonoo.getText().isEmpty() || dni.getText().isEmpty()
                    || fecha_nacimientoo.getValue() == null || direccionn.getText().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("Por favor, rellena todos los campos");
                alerta.showAndWait();
            }
            else {
                //AQUI VIENE LA ACCION
            }
        });
        //Comprobaciones, correo, tlf, dni:
        correoo.textProperty().addListener(e -> {
            if (!comprobar_correo(correoo.getText())) {
                correoo.setStyle("-fx-border-color: red;");
                confirmar_alta.setDisable(true);
            } else {
                confirmar_alta.setDisable(false);
                correoo.setStyle("");
            }
        });
        telefonoo.textProperty().addListener(e -> {
            if (!comprobar_telefono(telefonoo.getText())) {
                telefonoo.setStyle("-fx-border-color: red;");
                confirmar_alta.setDisable(true);
            } else {
                confirmar_alta.setDisable(false);
                telefonoo.setStyle("");
            }
        });
        dnii.textProperty().addListener(e -> {
            if (!comprobar_dni(dnii.getText())) {
                dnii.setStyle("-fx-border-color: red;");
                confirmar_alta.setDisable(true);
            } else {
                confirmar_alta.setDisable(false);
                dnii.setStyle("");
            }
        });

        fecha_nacimientoo.valueProperty().addListener((observable, oldValue, newValue)->{
            if (newValue==null) {
                fecha_nacimientoo.setStyle("-fx-border-color: red;");
                confirmar_alta.setDisable(true);
            } else {
                confirmar_alta.setDisable(false);
                fecha_nacimientoo.setStyle("");
            }
        });

        Scene scene = new Scene(hbox, 700, 540);
        stage.setScene(scene);
        stage.show();
    }


    private VBox primera_vista_derecha() {
        VBox vbox = new VBox();
        Label etiqueta = new Label("¿Qué deseas realizar?");
        confirmar = new Button("Aceptar");
        opcion_alta.setToggleGroup(opciones);
        opcion_consulta.setToggleGroup(opciones);
        opcion_operar.setToggleGroup(opciones);
        vbox.getChildren().addAll(etiqueta, opcion_alta, opcion_consulta, opcion_operar, confirmar);
        //Estilos y posiciones
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        aplicarEstiloBoton(confirmar);
        vbox.setStyle("-fx-background-color: #B4D2D9;");
        vbox.setAlignment(Pos.CENTER);
        etiqueta.setFont(Font.font("System", FontWeight.BOLD, 20));

        return vbox;
    }

    private VBox primera_vista_izquierda() {
        VBox vbox = new VBox();
        Label titulo = new Label("Bienvenido/a a Haytam's \nCentral Bank");
        vbox.getChildren().add(titulo);
        //Estilos y posiciones
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setStyle("-fx-background-color: #025159;");
        vbox.setAlignment(Pos.CENTER);//centrar
        titulo.setFont(Font.font("Yu Gothic UI Bold", FontWeight.BOLD, 30));//fuente
        titulo.setTextFill(Color.BEIGE);//o Color.rgb(255,255,255)

        return vbox;
    }

    private void ventana2() throws FileNotFoundException {

        aplicarEstiloBoton(confirmar_alta);
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        VBox vBox = new VBox(titulo, nombre, nombree, apellidos, apellidoss, correo, correoo, telefono, telefonoo, dni, dnii, direccion, direccionn, fecha_nacimiento, fecha_nacimientoo, confirmar_alta);
        BorderPane borderPane = new BorderPane();
        vBox.setSpacing(6);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setStyle("-fx-background-color: #B4D2D9;");
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 380, 520);
        ventana_alta.setScene(scene);
        ventana_alta.setTitle("Nuevo usuario");
        InputStream entrada = new FileInputStream(ruta_icono + "cuenta.png");
        Image imagen = new Image(entrada);
        ventana_alta.getIcons().add(imagen);
        ventana_alta.show();

    }

    private void aplicarEstiloBoton(Button boton) {
        boton.setStyle("-fx-background-color:#025159" + ";    -fx-background-radius: 5,4,3,5;\n"
                + "    -fx-background-insets: 0,1,2,0;\n"
                + "    -fx-text-fill: #F5F5DC;\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n"
                + "    -fx-font-size: 12px;\n"
                + "-fx-font-weight: bold;\n"
                + "     -fx-background-radius: 35px;\n "
                + "    -fx-padding: 10 20 10 20;");
//        boton.setCancelButton(true);
//        boton.borderProperty(Border.EMPTY);
    }

    private boolean comprobar_correo(String correo) {
        String patron = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return correo != null && correo.matches(patron);
    }

    private boolean comprobar_telefono(String telefono) {
        String patron = "^\\d{9}$";
        return telefono != null && telefono.matches(patron);
    }

    private boolean comprobar_dni(String dni) {
        String patron = "^\\d{8}[a-zA-Z]$";//podria hacer que no acepte minusculas
        return dni != null && dni.matches(patron);
    }

//    private boolean comprobar_fecha(String fecha){
////        String patron="^\\d{2}/\\d{2}/\\d{4}";
//      //  return fecha!=null && fecha.matches(patron);
//        String[] partes=fecha.split("/");
//        return partes[1].length()<=2 && partes[2].length()<=2 && partes[3].length()==4;
//    }
    public static void main(String[] args) {
        launch();
//        List<String> fontNames    = Font.getFontNames();
//        for(String item : fontNames) {
//            System.out.println(item);
//        }
    }

}