package com.repuestos;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final RepuestoDAO dao = new RepuestoDAO();

    private final ObservableList<Repuesto> datos =
            FXCollections.observableArrayList();

    private TableView<Repuesto> tabla;

    private TextField txtNombre;
    private TextField txtMarca;
    private TextField txtModelo;
    private TextField txtCategoria;
    private TextField txtPrecio;
    private TextField txtStock;
    private TextField txtProveedor;

    private Label lblConexion;
    private Label lblTotal;

    private int idSeleccionado = -1;

    private void mostrarCRUD(Stage stage) {

        Label titulo = new Label("GESTION DE REPUESTOS");
        titulo.setStyle(
                "-fx-font-size: 25px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1e293b;"
        );

        Label subtitulo = new Label(
                "Sistema de inventario de repuestos de motocicletas"
        );

        subtitulo.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");

        Button btnVolverInicio = crearBoton("Volver al menu", "#475569");
        btnVolverInicio.setOnAction(e -> mostrarInicio(stage));

        VBox textosEncabezado = new VBox(5, titulo, subtitulo);
        HBox encabezado = new HBox(15, textosEncabezado, new Region(), btnVolverInicio);
        HBox.setHgrow(encabezado.getChildren().get(1), Priority.ALWAYS);
        encabezado.setAlignment(Pos.CENTER_LEFT);
        encabezado.setPadding(new Insets(0, 0, 15, 0));

        // =====================================
        // FORMULARIO
        // =====================================

        txtNombre = crearCampo("Nombre del repuesto");
        txtMarca = crearCampo("Marca");
        txtModelo = crearCampo("Modelo compatible");
        txtCategoria = crearCampo("Categoria");
        txtPrecio = crearCampo("Precio");
        txtStock = crearCampo("Stock");
        txtProveedor = crearCampo("Proveedor");

        GridPane formulario = new GridPane();

        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(18));

        agregarCampo(formulario, "Nombre:", txtNombre, 0, 0);
        agregarCampo(formulario, "Marca:", txtMarca, 1, 0);

        agregarCampo(formulario, "Modelo compatible:", txtModelo, 0, 1);
        agregarCampo(formulario, "Categoria:", txtCategoria, 1, 1);

        agregarCampo(formulario, "Precio (S/):", txtPrecio, 0, 2);
        agregarCampo(formulario, "Stock:", txtStock, 1, 2);

        agregarCampo(formulario, "Proveedor:", txtProveedor, 0, 3);

        formulario.getColumnConstraints().add(
                new ColumnConstraints(150)
        );

        formulario.getColumnConstraints().add(
                new ColumnConstraints(230)
        );

        Label tituloFormulario = new Label("DATOS DEL REPUESTO");

        tituloFormulario.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #334155;"
        );

        VBox panelFormulario = new VBox(
                10, tituloFormulario, formulario
        );

        panelFormulario.setPadding(new Insets(18));
        panelFormulario.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;"
        );

        // =====================================
        // BOTONES
        // =====================================

        Button btnRegistrar = crearBoton(
                "Registrar", "#2563eb"
        );

        Button btnActualizar = crearBoton(
                "Actualizar", "#d97706"
        );

        Button btnEliminar = crearBoton(
                "Eliminar", "#dc2626"
        );

        Button btnLimpiar = crearBoton(
                "Limpiar", "#64748b"
        );

        HBox botones = new HBox(
                10, btnRegistrar, btnActualizar,
                btnEliminar, btnLimpiar
        );

        botones.setAlignment(Pos.CENTER_LEFT);

        btnRegistrar.setOnAction(e -> registrar());
        btnActualizar.setOnAction(e -> actualizar());
        btnEliminar.setOnAction(e -> eliminar());
        btnLimpiar.setOnAction(e -> limpiar());

        // =====================================
        // TABLA
        // =====================================

        tabla = new TableView<>();

        tabla.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Repuesto, Number> colId =
                new TableColumn<>("ID");

        colId.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getIdRepuesto()
                )
        );

        TableColumn<Repuesto, String> colNombre =
                new TableColumn<>("Nombre");

        colNombre.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getNombre()
                )
        );

        TableColumn<Repuesto, String> colMarca =
                new TableColumn<>("Marca");

        colMarca.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getMarca()
                )
        );

        TableColumn<Repuesto, String> colModelo =
                new TableColumn<>("Modelo compatible");

        colModelo.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getModeloCompatible()
                )
        );

        TableColumn<Repuesto, String> colCategoria =
                new TableColumn<>("Categoria");

        colCategoria.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getCategoria()
                )
        );

        TableColumn<Repuesto, Number> colPrecio =
                new TableColumn<>("Precio (S/)");

        colPrecio.setCellValueFactory(data ->
                new SimpleDoubleProperty(
                        data.getValue().getPrecio()
                )
        );

        colPrecio.setCellFactory(column ->
                new TableCell<Repuesto, Number>() {

                    @Override
                    protected void updateItem(Number valor, boolean empty) {
                        super.updateItem(valor, empty);

                        if (empty || valor == null) {
                            setText(null);
                        } else {
                            setText(String.format("S/ %.2f",
                                    valor.doubleValue()));
                        }
                    }
                }
        );

        TableColumn<Repuesto, Number> colStock =
                new TableColumn<>("Stock");

        colStock.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getStock()
                )
        );

        TableColumn<Repuesto, String> colProveedor =
                new TableColumn<>("Proveedor");

        colProveedor.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getProveedor()
                )
        );

        tabla.getColumns().addAll(
                colId, colNombre, colMarca, colModelo,
                colCategoria, colPrecio, colStock, colProveedor
        );

        tabla.setItems(datos);

        tabla.setPlaceholder(
                new Label("No hay repuestos registrados.")
        );

        tabla.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {

                        idSeleccionado =
                                seleccionado.getIdRepuesto();

                        txtNombre.setText(seleccionado.getNombre());
                        txtMarca.setText(seleccionado.getMarca());
                        txtModelo.setText(
                                seleccionado.getModeloCompatible()
                        );
                        txtCategoria.setText(
                                seleccionado.getCategoria()
                        );
                        txtPrecio.setText(
                                String.valueOf(seleccionado.getPrecio())
                        );
                        txtStock.setText(
                                String.valueOf(seleccionado.getStock())
                        );
                        txtProveedor.setText(
                                seleccionado.getProveedor()
                        );
                    }
                });

        Label tituloTabla = new Label("REPUESTOS REGISTRADOS");

        tituloTabla.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #334155;"
        );

        lblTotal = new Label("Total de registros: 0");

        lblTotal.setStyle("-fx-text-fill: #64748b;");

        HBox cabeceraTabla = new HBox(
                tituloTabla, new Region(), lblTotal
        );

        HBox.setHgrow(cabeceraTabla.getChildren().get(1),
                Priority.ALWAYS);

        cabeceraTabla.setAlignment(Pos.CENTER_LEFT);

        VBox panelTabla = new VBox(
                12, cabeceraTabla, tabla
        );

        panelTabla.setPadding(new Insets(18));
        panelTabla.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;"
        );

        VBox centro = new VBox(
                15, panelFormulario, botones, panelTabla
        );

        VBox.setVgrow(panelTabla, Priority.ALWAYS);

        // =====================================
        // ESTADO DE CONEXION
        // =====================================

        lblConexion = new Label("Verificando conexion...");

        lblConexion.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-text-fill: #64748b;"
        );

        HBox pie = new HBox(lblConexion);
        pie.setAlignment(Pos.CENTER_LEFT);
        pie.setPadding(new Insets(10, 0, 0, 0));

        // =====================================
        // CONTENEDOR PRINCIPAL
        // =====================================

        VBox principal = new VBox(
                15, encabezado, centro, pie
        );

        principal.setPadding(new Insets(25));
        principal.setStyle("-fx-background-color: #f1f5f9;");

        Scene scene = new Scene(principal, 1250, 850);

        stage.setTitle("Sistema de Repuestos de Motocicletas");
        stage.setScene(scene);
        stage.setMinWidth(1050);
        stage.setMinHeight(750);
        stage.show();

        verificarConexion();
    }

    // ==========================================
    // CREAR CAMPOS
    // ==========================================

    private TextField crearCampo(String texto) {

        TextField campo = new TextField();

        campo.setPromptText(texto);
        campo.setPrefWidth(230);

        return campo;
    }

    private void agregarCampo(
            GridPane grid,
            String etiqueta,
            TextField campo,
            int columna,
            int fila) {

        VBox contenedor = new VBox(
                5, new Label(etiqueta), campo
        );

        grid.add(contenedor, columna, fila);
    }

    // ==========================================
    // CREAR BOTONES
    // ==========================================

    private Button crearBoton(String texto, String color) {

        Button boton = new Button(texto);

        boton.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 10 18 10 18;" +
                        "-fx-cursor: hand;"
        );

        return boton;
    }

    // ==========================================
    // OBTENER DATOS DEL FORMULARIO
    // ==========================================

    private Repuesto obtenerDatosFormulario() {

        String nombre = txtNombre.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String stockTexto = txtStock.getText().trim();
        String proveedor = txtProveedor.getText().trim();

        if (nombre.isEmpty() || marca.isEmpty()
                || modelo.isEmpty() || categoria.isEmpty()
                || precioTexto.isEmpty() || stockTexto.isEmpty()
                || proveedor.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Debes completar todos los campos."
            );

            return null;
        }

        try {

            double precio = Double.parseDouble(precioTexto);
            int stock = Integer.parseInt(stockTexto);

            if (!Double.isFinite(precio) || precio <= 0 || stock < 0) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Datos incorrectos",
                        "El precio debe ser mayor a cero y el stock no puede ser negativo."
                );

                return null;
            }

            return new Repuesto(
                    nombre, marca, modelo,
                    categoria, precio, stock, proveedor
            );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Formato incorrecto",
                    "El precio debe ser numerico y el stock debe ser un numero entero."
            );

            return null;
        }
    }

    // ==========================================
    // REGISTRAR
    // ==========================================

    private void registrar() {

        Repuesto repuesto = obtenerDatosFormulario();

        if (repuesto == null) {
            return;
        }

        try {

            if (dao.registrar(repuesto)) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Registro exitoso",
                        "El repuesto fue registrado correctamente."
                );

                limpiar();
                cargarDatos();
            }

        } catch (RuntimeException e) {
            mostrarError(e);
        }
    }

    // ==========================================
    // ACTUALIZAR
    // ==========================================

    private void actualizar() {

        if (idSeleccionado == -1) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Selecciona un registro",
                    "Debes seleccionar un repuesto de la tabla."
            );

            return;
        }

        Repuesto repuesto = obtenerDatosFormulario();

        if (repuesto == null) {
            return;
        }

        repuesto.setIdRepuesto(idSeleccionado);

        try {

            if (dao.actualizar(repuesto)) {

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Actualizacion exitosa",
                        "El repuesto fue actualizado correctamente."
                );

                limpiar();
                cargarDatos();
            }

        } catch (RuntimeException e) {
            mostrarError(e);
        }
    }

    // ==========================================
    // ELIMINAR
    // ==========================================

    private void eliminar() {

        if (idSeleccionado == -1) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Selecciona un registro",
                    "Debes seleccionar un repuesto para eliminar."
            );

            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Confirmar eliminacion");
        confirmacion.setHeaderText("Eliminar repuesto");
        confirmacion.setContentText(
                "¿Estas seguro de eliminar el repuesto seleccionado?"
        );

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            try {

                if (dao.eliminar(idSeleccionado)) {

                    mostrarAlerta(
                            Alert.AlertType.INFORMATION,
                            "Eliminacion exitosa",
                            "El repuesto fue eliminado correctamente."
                    );

                    limpiar();
                    cargarDatos();
                }

            } catch (RuntimeException e) {
                mostrarError(e);
            }
        }
    }

    // ==========================================
    // LIMPIAR FORMULARIO
    // ==========================================

    private void limpiar() {

        txtNombre.clear();
        txtMarca.clear();
        txtModelo.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtProveedor.clear();

        idSeleccionado = -1;

        tabla.getSelectionModel().clearSelection();
    }

    // ==========================================
    // CARGAR DATOS EN LA TABLA
    // ==========================================

    private void cargarDatos() {

        try {

            datos.setAll(dao.listar());

            lblTotal.setText(
                    "Total de registros: " + datos.size()
            );

        } catch (RuntimeException e) {
            mostrarError(e);
        }
    }

    // ==========================================
    // VERIFICAR CONEXION
    // ==========================================

    private void verificarConexion() {

        if (Conexion.testConnection()) {

            lblConexion.setText(
                    "● CONECTADO A MYSQL"
            );

            lblConexion.setStyle(
                    "-fx-text-fill: #16a34a;" +
                            "-fx-font-weight: bold;"
            );

            cargarDatos();

        } else {

            lblConexion.setText(
                    "● SIN CONEXION A MYSQL"
            );

            lblConexion.setStyle(
                    "-fx-text-fill: #dc2626;" +
                            "-fx-font-weight: bold;"
            );

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error de conexion",
                    "No se pudo conectar a MySQL. " +
                            "Verifica que Docker y el contenedor esten funcionando."
            );
        }
    }

    // ==========================================
    // MOSTRAR ALERTAS
    // ==========================================

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    private void mostrarError(RuntimeException e) {

        mostrarAlerta(
                Alert.AlertType.ERROR,
                "Error en el sistema",
                e.getMessage()
        );
    }

    // ==========================================
    // INICIAR APLICACION
    // ==========================================

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        mostrarInicio(stage);
    }

    private void mostrarInicio(Stage stage) {

        // Encabezado
        Label titulo = new Label("MOTO PARTS");
        titulo.setStyle(
                "-fx-font-size: 32px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;"
        );

        Label subtitulo = new Label(
                "SISTEMA DE GESTION DE REPUESTOS DE MOTOCICLETAS"
        );
        subtitulo.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #CBD5E1;"
        );

        VBox encabezado = new VBox(8, titulo, subtitulo);
        encabezado.setAlignment(Pos.CENTER_LEFT);
        encabezado.setPadding(new Insets(30));
        encabezado.setStyle("-fx-background-color: #172554;");

        // Bienvenida
        Label bienvenida = new Label("Bienvenido al sistema");
        bienvenida.setStyle(
                "-fx-font-size: 25px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #1E293B;"
        );

        Label descripcion = new Label(
                "Administra los repuestos de motocicletas " +
                        "de manera sencilla y organizada."
        );
        descripcion.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #64748B;"
        );

        VBox bienvenidaBox = new VBox(8, bienvenida, descripcion);
        bienvenidaBox.setPadding(new Insets(25, 0, 15, 0));

        // Boton principal
        Button btnGestionar = new Button(
                "GESTIONAR REPUESTOS"
        );

        btnGestionar.setPrefSize(260, 55);
        btnGestionar.setStyle(
                "-fx-background-color: #2563EB;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"
        );

        btnGestionar.setOnAction(e -> mostrarCRUD(stage));

        // Boton de conexion
        Button btnConexion = new Button("VERIFICAR CONEXION MYSQL");
        btnConexion.setPrefSize(260, 45);
        btnConexion.setStyle(
                "-fx-background-color: #E2E8F0;" +
                        "-fx-text-fill: #1E293B;" +
                        "-fx-font-size: 13px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"
        );

        btnConexion.setOnAction(e -> {
            boolean conectado = Conexion.testConnection();

            Alert alerta = new Alert(
                    conectado ? Alert.AlertType.INFORMATION
                            : Alert.AlertType.ERROR
            );

            alerta.setTitle("Estado de la conexion");
            alerta.setHeaderText(
                    conectado ? "Conexion exitosa"
                            : "Error de conexion"
            );

            alerta.setContentText(
                    conectado
                            ? "La aplicacion esta conectada correctamente a MySQL."
                            : "No se pudo establecer conexion con la base de datos."
            );

            alerta.showAndWait();
        });

        // Boton acerca del sistema
        Button btnAcerca = new Button("ACERCA DEL SISTEMA");
        btnAcerca.setPrefSize(260, 45);
        btnAcerca.setStyle(
                "-fx-background-color: #E2E8F0;" +
                        "-fx-text-fill: #1E293B;" +
                        "-fx-font-size: 13px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"
        );

        btnAcerca.setOnAction(e -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Acerca del sistema");
            alerta.setHeaderText("Sistema de Repuestos de Motocicletas");
            alerta.setContentText(
                    "Proyecto academico CRUD\n\n" +
                            "Desarrollado por: Marcos Daniel de la Cruz Pelaez\n" +
                            "Tecnologias: Java 17, JavaFX, JDBC y MySQL."
            );
            alerta.showAndWait();
        });

        VBox botones = new VBox(
                15,
                btnGestionar,
                btnConexion,
                btnAcerca
        );

        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(20));

        // Pie de pagina
        Label pie = new Label(
                "Sistema de gestion de repuestos | 2026"
        );
        pie.setStyle(
                "-fx-text-fill: #64748B;" +
                        "-fx-font-size: 12px;"
        );

        VBox contenido = new VBox(
                15,
                encabezado,
                bienvenidaBox,
                botones,
                pie
        );

        contenido.setAlignment(Pos.TOP_CENTER);
        contenido.setStyle("-fx-background-color: #F1F5F9;");

        VBox.setVgrow(botones, Priority.ALWAYS);

        Scene escena = new Scene(contenido, 850, 600);

        stage.setTitle("MOTO PARTS - Panel principal");
        stage.setScene(escena);
        stage.setMinWidth(750);
        stage.setMinHeight(550);
        stage.show();
    }

}