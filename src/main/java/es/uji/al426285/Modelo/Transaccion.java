package es.uji.al426285.Modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Transaccion {
    private int idTransaccion = 0;
    private int idCuenta;
    private double cantidad;
    private String fecha;
    private int tipo;
    private Integer idDestino;
    public static final int INGRESO = 0;
    public static final int RETIRO = 1;
    public static final int TRANSFERENCIA = 2;


    public Transaccion(int idCuenta, double cantidad, String fecha, int tipo, Integer idDestino) {
        if (cantidad <= 0 || fecha == null || (tipo != INGRESO && tipo != RETIRO && tipo != TRANSFERENCIA)) {
            throw new IllegalArgumentException("Datos inválidos para la transacción");
        }
        if (tipo == TRANSFERENCIA && idDestino == null) {
            throw new IllegalArgumentException("Falta intoducir la cuenta destino");
        }
        this.idCuenta = idCuenta;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.tipo = tipo;
        this.idDestino = idDestino;
    }


    @Override
    public String toString() {
        String mensaje = "";
        switch (this.tipo) {
            case INGRESO:
                mensaje = "Ingreso de " + cantidad + "€ a la cuenta " + idCuenta + " a fecha de " + fecha + ".";
                break;
            case RETIRO:
                mensaje = "Retiro de " + cantidad + "€ de la cuenta " + idCuenta + " a fecha de " + fecha + ".";
                break;
            case TRANSFERENCIA:
                mensaje = "Transferencia de " + cantidad + "con origen" + idCuenta + "a la cuenta destino " + idDestino + " a fecha de " + fecha + ".";
                break;
            default:
                mensaje = "Tipo de transacción desconocido.";

        }
        return mensaje;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean insertarTransaccion() {
        Properties props = new Properties();
        try (FileInputStream entrada = new FileInputStream("config.properties")) {
            props.load(entrada);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        String url = props.getProperty("db.url");
        String usuario = props.getProperty("db.user");
        String clave = props.getProperty("db.password");

        //otra opcion--> System.getenv("DB_USER")y System.getenv("DB_PASSWORD")
        String insertar = "INSERT INTO transacciones (id_cuenta,id_cuenta_destino,tipo, monto, fecha) VALUES (?,?, ?,?,?)";
        String consulta = "SELECT saldo FROM cuentas WHERE id_cuenta = ?";
        String actualizar_saldo = "UPDATE cuentas SET saldo = ? WHERE id_cuenta = ?";
        int cambios = 0;
        try (Connection connection = DriverManager.getConnection(url, usuario, clave)) {

            //Consultar saldo actual del origen
            PreparedStatement consulta_saldo = connection.prepareStatement(consulta);
            consulta_saldo.setInt(1, this.getIdCuenta());
            ResultSet resultado = consulta_saldo.executeQuery();
            resultado.next();//importante
            double saldo_origen = resultado.getDouble("saldo");

            //Si es tipo 0, es decir, ingreso
            if (this.getTipo() == INGRESO) {
                double total = this.getCantidad() + saldo_origen;
                PreparedStatement actualizar = connection.prepareStatement(actualizar_saldo);
                actualizar.setDouble(1, total);
                actualizar.setDouble(2, this.getIdCuenta());

                cambios += actualizar.executeUpdate();
                System.out.println("Cambios: " + cambios);
            } else if (this.getTipo() == RETIRO) {//Si es tipo 1, es decir, retirar
                if (saldo_origen < this.cantidad) {
                    throw new Exception("Saldo insuficiente para hacer la retirada ");
                } else {
                    double total = saldo_origen - this.getCantidad();
                    PreparedStatement actualizar = connection.prepareStatement(actualizar_saldo);
                    actualizar.setDouble(1, total);
                    actualizar.setDouble(2, this.getIdCuenta());

                    cambios += actualizar.executeUpdate();
                    System.out.println("Cambios: " + cambios);
                }


            } else if (this.getTipo() == TRANSFERENCIA) { //Si es tipo 2, es decir, transferencia

                if (saldo_origen < this.cantidad) {
                    //  System.out.println("Saldo insuficiente para hacer la retirada ");
                    throw new Exception("Saldo insuficiente para hacer la retirada ");

                } else {
                    try {
                        //Consultar saldo destino
                        PreparedStatement consulta_saldo_destino = connection.prepareStatement(consulta);
                        consulta_saldo_destino.setInt(1, this.getIdDestino());
                        ResultSet resultado_destino = consulta_saldo_destino.executeQuery();
                        resultado_destino.next();//importante
                        double saldo_destino = resultado_destino.getDouble("saldo");

                        //actualizar origen
                        double total = saldo_origen - this.getCantidad();
                        PreparedStatement actualizar = connection.prepareStatement(actualizar_saldo);
                        actualizar.setDouble(1, total);
                        actualizar.setDouble(2, this.getIdCuenta());
                        cambios += actualizar.executeUpdate();

                        //actualizar destino
                        double total_destino = saldo_destino + this.getCantidad();
                        PreparedStatement actualizar_destino = connection.prepareStatement(actualizar_saldo);
                        actualizar_destino.setDouble(1, total_destino);
                        actualizar_destino.setDouble(2, this.getIdDestino());
                        cambios += actualizar_destino.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage() + ". O posible cuenta destino inexistente");
                    }

                    System.out.println("Cambios: " + cambios);
                }


            } else {
                throw new SQLException("Tipo de transaccion no valido");
            }

            //insertar transaccion si han habido cambios. MUY IMPORTANTE HACERLO AL FINAL, dado que en ciertas ocasiones no se completa la transaccion por lo que no
            //es necesario tener un registro de ella
            if (cambios > 0) {
                PreparedStatement accion = connection.prepareStatement(insertar);
                accion.setInt(1, this.getIdCuenta());
                if (this.getIdDestino() == null)
                    accion.setNull(2, java.sql.Types.INTEGER);//ojo, no se puede poner null directamente
                else accion.setInt(2, this.getIdDestino());
                accion.setInt(3, this.getTipo());
                accion.setDouble(4, this.getCantidad());
                accion.setString(5, this.getFecha());
                cambios += accion.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cambios > 0;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }
}
