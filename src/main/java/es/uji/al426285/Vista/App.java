package es.uji.al426285.Vista;

import es.uji.al426285.Modelo.Cliente;
import es.uji.al426285.Controlador.Controlador;
import es.uji.al426285.Modelo.Cuenta;
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
import java.time.LocalDateTime;


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
    ///////////VENTANA ALTA//////////////////////
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
    Controlador controlador = new Controlador();
    /////////////VENTANA CONSULTA/////////////////////////////
    Stage ventana_consulta = new Stage();
    TextField dni_consulta = new TextField();
    Button confirmar_consulta = new Button("Aceptar");
    /////////////VENTANA OPERACIÓN/////////////////////////////
    Stage ventana_operacion = new Stage();
    ComboBox combo = new ComboBox();
    Button confirmar_operacion = new Button("Aceptar");
    //////////////////INGRESAR///////////////////////////
    Stage ventana_ingresar = new Stage();
    TextField dni_ingresar = new TextField();
    TextField monto_ingresar = new TextField();
    Button confirmar_ingreso = new Button("Ingresar");
    //////////////////RETIRAR///////////////////////////
    Stage ventana_retirar = new Stage();
    TextField dni_retirar = new TextField();
    TextField monto_retirar = new TextField();
    Button confirmar_retiro = new Button("Retirar");
    //////////////////TRANSFERIR///////////////////////////
    Stage ventana_transferir = new Stage();
    TextField dni_transferir = new TextField();
    TextField monto_transferir = new TextField();
    Button confirmar_transferencia = new Button("Transferir");
    TextField dni_destino = new TextField();


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
        iniciar_action_comprobaciones_dni();
        confirmar.setOnAction(e -> {
            try {
                if (opcion_alta.isSelected()) {
                    ventana_alta();
                } else if (opcion_consulta.isSelected()) {
                    ventana_consulta();

                } else if (opcion_operar.isSelected()) {
                    ventana_operacion();
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
                alerta.setContentText("Por favor, rellene todos los campos.");
                alerta.showAndWait();
            } else {
                //Crear cliente y su cuenta
                Cliente cliente = controlador.crear_cliente(nombree.getText(), apellidoss.getText(), dnii.getText(), direccionn.getText(), telefonoo.getText(), correoo.getText(), fecha_nacimientoo.getValue().toString());
                Cuenta cuenta = controlador.crear_cuenta(dnii.getText());
                //Insertar en la base de datos
                if (controlador.insertar_cliente(cliente) && controlador.insertar_cuenta(cuenta)) {
                    //Avisar de que se ha creado
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmación");
                    alerta.setHeaderText("¡CONFIRMACIÓN!");
                    alerta.setContentText("Usuario: " + nombree.getText() + " " + apellidoss.getText() + ", creado correctamente.");
                    alerta.showAndWait();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Advertencia");
                    alerta.setHeaderText("¡ADVERTENCIA!");
                    alerta.setContentText("Ha habido un problema. El cliente introducido ya se ha dado de alta previamente.");
                    alerta.showAndWait();
                }
            }
        });

        confirmar_consulta.setOnAction(e -> {
            if (dni_consulta.getText().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("Por favor, escriba su dni.");
                alerta.showAndWait();
            } else {
                try {
                    Double saldo = controlador.realizar_consulta(dni_consulta.getText());
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Confirmación");
                    alerta.setHeaderText("¡CONFIRMACIÓN!");
                    alerta.setContentText("El usuario con dni '" + dni_consulta.getText() + "' tiene un saldo de: " + saldo + "€");
                    alerta.showAndWait();
                } catch (Exception ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Advertencia");
                    alerta.setHeaderText("¡ADVERTENCIA!");
                    alerta.setContentText("Cuenta inexistente. Por favor, introduzca una cuenta válida.");
                    alerta.showAndWait();
                }

            }

        });
        confirmar_operacion.setOnAction(e -> {
            if (combo.getValue() == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("Escoja una opción antes de hacer clic sobre aceptar.");
                alerta.showAndWait();
            } else {
                String opcion = combo.getValue().toString();
                switch (opcion) {
                    case "Ingresar":
                        try {
                            ventana_ingresar();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Retirar":
                        try {
                            ventana_retirar();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Transferir":
                        try {
                            ventana_transferir();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        });
        confirmar_ingreso.setOnAction(e -> {
            if (dni_ingresar.getText().isEmpty() || !comprobar_dni(dni_ingresar.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("DNI erróneo.");
                alerta.showAndWait();
            } else if (monto_ingresar.getText().isEmpty() || !comprobar_monto(monto_ingresar.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("Monto introducido erróneo. Escriba únicamente valores numéricos con dos decimales como máximo si procede.");
                alerta.showAndWait();
            } else {
                int origen = -1;
                try {
                    origen = controlador.consultar_id(dni_ingresar.getText());
                } catch (Exception ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("¡ERROR!");
                    alerta.setContentText("DNI erróneo.");
                    alerta.showAndWait();
                }
                if (origen != -1) {
                    try {
                        controlador.realizar_transaccion(origen, Double.parseDouble(monto_ingresar.getText()), LocalDateTime.now().toString(), 0, null);
                        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("Confirmación");
                        alerta.setHeaderText("¡CONFIRMACIÓN!");
                        alerta.setContentText("Ha ingresado un total de: " + monto_ingresar.getText() + "€" + " a la cuenta cuyo dni del titular es '" + dni_ingresar.getText() + "'");
                        alerta.showAndWait();
                    } catch (Exception ex) {
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error");
                        alerta.setHeaderText("¡ERROR!");
                        alerta.setContentText("Monto introducido incorrecto. Use punto en lugar de coma para separar los decimales.");
                        alerta.showAndWait();
                    }
                }
            }
        });
        confirmar_retiro.setOnAction(e -> {
            if (dni_retirar.getText().isEmpty() || !comprobar_dni(dni_retirar.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("DNI erróneo.");
                alerta.showAndWait();
            } else if (monto_retirar.getText().isEmpty() || !comprobar_monto(monto_retirar.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("Monto introducido erróneo. Escriba únicamente valores numéricos.");
                alerta.showAndWait();
            } else {
                int origen = -1;
                try {
                    origen = controlador.consultar_id(dni_retirar.getText());
                } catch (Exception ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("¡ERROR!");
                    alerta.setContentText("DNI erróneo.");
                    alerta.showAndWait();
                }
                if (origen != -1) {
                    try {
                        controlador.realizar_transaccion(origen, Double.parseDouble(monto_retirar.getText()), LocalDateTime.now().toString(), 1, null);
                        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("Confirmación");
                        alerta.setHeaderText("¡CONFIRMACIÓN!");
                        alerta.setContentText("Ha retirado un total de: " + monto_retirar.getText() + "€" + " de la cuenta cuyo dni del titular es '" + dni_retirar.getText() + "'");
                        alerta.showAndWait();
                    } catch (Exception ex) {
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error");
                        alerta.setHeaderText("¡ERROR!");
                        alerta.setContentText(ex.getMessage());
                        alerta.showAndWait();
                    }
                }
            }
        });
        confirmar_transferencia.setOnAction(e -> {
            if (dni_transferir.getText().isEmpty() || dni_destino.getText().isEmpty() || monto_transferir.getText().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("Por favor, rellene todos los campos.");
                alerta.showAndWait();
            } else if (dni_transferir.getText().isEmpty() || !comprobar_dni(dni_transferir.getText()) || dni_destino.getText().isEmpty() || !comprobar_dni(dni_destino.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("DNI erróneo.");
                alerta.showAndWait();
            } else if (monto_transferir.getText().isEmpty() || !comprobar_monto(monto_transferir.getText())) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("¡ADVERTENCIA!");
                alerta.setContentText("Introduzca la cantidad a transferir y emplee únicamente valores numéricos usando '.' para separar los decimales si procede.");
                alerta.showAndWait();
            } else {
                int origen = -1;
                int destino = -1;
                try {
                    origen = controlador.consultar_id(dni_transferir.getText());
                } catch (Exception ignored) {
                }
                try {
                    destino = controlador.consultar_id(dni_destino.getText());
                } catch (Exception ignored) {

                }
                if (origen != -1 && destino != -1 && origen != destino) {
                    try {
                        controlador.realizar_transaccion(origen, Double.parseDouble(monto_transferir.getText()), LocalDateTime.now().toString(), 2, destino);
                        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                        alerta.setTitle("Confirmación");
                        alerta.setHeaderText("¡CONFIRMACIÓN!");
                        alerta.setContentText("Ha transferido un total de: " + monto_transferir.getText() + "€" + " desde la cuenta cuyo dni del titular es '" + dni_transferir.getText() +
                                "' a la cuenta cuyo dni del titular es " + "'" + dni_destino.getText() + "'");
                        alerta.showAndWait();
                    } catch (Exception ex) {
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error");
                        alerta.setHeaderText("¡ERROR!");
                        alerta.setContentText(ex.getMessage());
                        alerta.showAndWait();
                    }
                } else {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("Advertencia");
                    alerta.setHeaderText("¡ADVERTENCIA!");
                    alerta.setContentText("Revise los dni's introducidos o el monto introducido.");
                    alerta.showAndWait();
                }
            }
        });
        //Comprobaciones, correo, tlf,fecha, dni:
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
        fecha_nacimientoo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                fecha_nacimientoo.setStyle("-fx-border-color: red;");
                confirmar_alta.setDisable(true);
            } else {
                confirmar_alta.setDisable(false);
                fecha_nacimientoo.setStyle("");
            }
        });

        monto_ingresar.setOnAction(e -> {
            realizar_comprobacion_monto(monto_ingresar, confirmar_ingreso);
        });
        monto_retirar.setOnAction(e -> {
            realizar_comprobacion_monto(monto_retirar, confirmar_ingreso);
        });
        monto_transferir.setOnAction(e -> {
            realizar_comprobacion_monto(monto_transferir, confirmar_ingreso);
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

    private void ventana_alta() throws FileNotFoundException {

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

    private void ventana_consulta() throws FileNotFoundException {
        Label titulo = new Label("Introduzca su dni: ");
        VBox vBox = new VBox(titulo, dni_consulta, confirmar_consulta);
        vBox.setStyle("-fx-background-color: #B4D2D9;");
        vBox.setSpacing(6);
        vBox.setPadding(new Insets(20, 10, 10, 10));
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 380, 380);
        ventana_consulta.setScene(scene);
        ventana_consulta.setTitle("Consultar saldo");
        InputStream entrada = new FileInputStream(ruta_icono + "usuario.png");
        Image imagen = new Image(entrada);
        ventana_consulta.getIcons().add(imagen);
        aplicarEstiloBoton(confirmar_consulta);
        ventana_consulta.show();

    }

    private void ventana_operacion() throws FileNotFoundException {
        Label titulo = new Label("Escoja una opción: ");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 20));
        combo.getItems().addAll("Ingresar", "Retirar", "Transferir");
        combo.setPrefWidth(200);
        HBox hBox = new HBox(combo, confirmar_operacion);
        hBox.setSpacing(10);
        VBox vBox = new VBox(titulo, combo, confirmar_operacion);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 380, 380);
        aplicarEstiloBoton(confirmar_operacion);
        ventana_operacion.setScene(scene);
        ventana_operacion.setTitle("Operar");
        InputStream entrada = new FileInputStream(ruta_icono + "usuario.png");
        Image imagen = new Image(entrada);
        ventana_operacion.getIcons().add(imagen);
        vBox.setStyle("-fx-background-color: #B4D2D9;");
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        ventana_operacion.show();
    }

    public void ventana_ingresar() throws FileNotFoundException {
        Label titulo = new Label("Ingrese su dni: ");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 15));
        Label titulo_monto = new Label("Ingrese el monto a ingresar: ");
        titulo_monto.setFont(Font.font("System", FontWeight.BOLD, 15));
        aplicarEstiloBoton(confirmar_ingreso);
        VBox vBox = new VBox(titulo, dni_ingresar, titulo_monto, monto_ingresar, confirmar_ingreso);
        vBox.setStyle("-fx-background-color: #B4D2D9;");
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 380, 380);
        ventana_ingresar.setScene(scene);
        ventana_ingresar.setTitle("Ingresar");
        InputStream entrada = new FileInputStream(ruta_icono + "ingresar.png");
        Image imagen = new Image(entrada);
        ventana_ingresar.getIcons().add(imagen);
        ventana_ingresar.show();

    }

    public void ventana_retirar() throws FileNotFoundException {
        Label titulo = new Label("Ingrese su dni: ");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 15));
        Label titulo_monto = new Label("Ingrese el monto a retirar: ");
        titulo_monto.setFont(Font.font("System", FontWeight.BOLD, 15));
        aplicarEstiloBoton(confirmar_retiro);
        VBox vBox = new VBox(titulo, dni_retirar, titulo_monto, monto_retirar, confirmar_retiro);
        vBox.setStyle("-fx-background-color: #B4D2D9;");
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 380, 380);
        ventana_retirar.setScene(scene);
        ventana_retirar.setTitle("Retirar");
        InputStream entrada = new FileInputStream(ruta_icono + "retirar.png");
        Image imagen = new Image(entrada);
        ventana_retirar.getIcons().add(imagen);
        ventana_retirar.show();

    }

    public void ventana_transferir() throws FileNotFoundException {
        Label titulo = new Label("Ingrese su dni: ");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 15));
        Label titulo_monto = new Label("Ingrese el monto a transferir: ");
        titulo_monto.setFont(Font.font("System", FontWeight.BOLD, 15));
        Label titulo_dni_destino = new Label("Ingrese el dni del destinatario: ");
        titulo_dni_destino.setFont(Font.font("System", FontWeight.BOLD, 15));
        aplicarEstiloBoton(confirmar_transferencia);
        VBox vBox = new VBox(titulo, dni_transferir, titulo_dni_destino, dni_destino, titulo_monto, monto_transferir, confirmar_transferencia);
        vBox.setStyle("-fx-background-color: #B4D2D9;");
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 380, 380);
        ventana_transferir.setScene(scene);
        ventana_transferir.setTitle("Transferir");
        InputStream entrada = new FileInputStream(ruta_icono + "transferir.png");
        Image imagen = new Image(entrada);
        ventana_transferir.getIcons().add(imagen);
        ventana_transferir.show();

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

    private boolean comprobar_monto(String monto) {

        String patron = "^\\d{1,9}(\\.\\d{1,2})?$"; //Esto seria el equivalente a esto--> String patron = "^\\d{1,9}$"; y String patron2="^\\d{1,9},\\d{2}$";
        return monto != null && (monto.matches(patron));
    }

    private void realizar_comprobacion_dni(TextField campo, Button boton) {
        if (!comprobar_dni(campo.getText())) {
            campo.setStyle("-fx-border-color: red;");
            boton.setDisable(true);
            System.out.println("eooooooo");
        } else {
            boton.setDisable(false);
            campo.setStyle("");
        }
    }

    private void realizar_comprobacion_monto(TextField campo, Button boton) {
        if (campo.getText().isEmpty() || !comprobar_monto(campo.getText())) {
            campo.setStyle("-fx-border-color: red;");
            boton.setDisable(true);
        } else {
            campo.setStyle("");
            boton.setDisable(false);
        }
    }

    private void action_comprobaciones_dni(TextField campo, Button boton) {
        campo.textProperty().addListener(e -> {
            realizar_comprobacion_dni(campo, boton);

        });
    }

    private void iniciar_action_comprobaciones_dni() {
        action_comprobaciones_dni(dnii, confirmar_alta);
        action_comprobaciones_dni(dni_consulta, confirmar_consulta);
        action_comprobaciones_dni(dni_ingresar, confirmar_ingreso);
        action_comprobaciones_dni(dni_retirar, confirmar_retiro);
        action_comprobaciones_dni(dni_transferir, confirmar_transferencia);
        action_comprobaciones_dni(dni_destino, confirmar_transferencia);
    }

    public static void main(String[] args) {
        launch();
//        List<String> fontNames    = Font.getFontNames();
//        for(String item : fontNames) {
//            System.out.println(item);
//        }
    }

}
