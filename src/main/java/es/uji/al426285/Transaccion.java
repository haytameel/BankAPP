package es.uji.al426285;

import java.sql.*;
import java.time.LocalDateTime;

import static es.uji.al426285.Cliente.getFechaUSA;

public class Transaccion {
    private int idTransaccion=0;
    private int idCuenta;
    private double cantidad;
    private String fecha;

    public Transaccion( int idCuenta, double cantidad, String fecha) {
        this.idCuenta = idCuenta;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }


    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion=" + idTransaccion +
                ", idCuenta=" + idCuenta +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha +
                '}';
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

    public boolean insertarTransaccion(){
        String url="jdbc:mysql://localhost:3306/banco";
        String usuario="root";
        String clave="hayta";
        String insertar="INSERT INTO transacciones (id_cuenta, monto, fecha) VALUES (?,?, ?)";
        String actualizar_saldo="UPDATE cuentas SET saldo = ? WHERE id_cuenta = ?";
        String consulta = "SELECT saldo FROM cuentas WHERE id_cuenta = ?";

        try (Connection connection= DriverManager.getConnection(url,usuario,clave)){
            //insertar transaccion
            PreparedStatement accion=connection.prepareStatement(insertar);
            accion.setInt(1, this.getIdCuenta());
            accion.setDouble(2,this.getCantidad());
            accion.setString(3,this.getFecha());
            int cambios= accion.executeUpdate();

            //Consultar saldo actual
            PreparedStatement consulta_saldo=connection.prepareStatement(consulta);
            consulta_saldo.setInt(1, this.getIdCuenta());
            ResultSet resultado=consulta_saldo.executeQuery();
            resultado.next();
            double saldo=resultado.getDouble("saldo");

            //Actualizar
            double total=this.getCantidad()+saldo;
            PreparedStatement actualizar=connection.prepareStatement(actualizar_saldo);
            actualizar.setDouble(1, total);
            actualizar.setDouble(2, this.getIdCuenta());

            cambios+=actualizar.executeUpdate();
            System.out.println("Cambios: "+cambios);

            return cambios>0;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
