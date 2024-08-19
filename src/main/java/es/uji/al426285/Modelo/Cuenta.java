package es.uji.al426285.Modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Cuenta {
    private int id_cuenta;
    private double saldo;
    private String dniTitular;
    private String fecha;//fecha de alta

    // Constructor
    public Cuenta(int id_cuenta, double saldo, String dniTitular, String fecha) {
        this.id_cuenta = id_cuenta;
        this.saldo = saldo;
        this.dniTitular = dniTitular;
        this.fecha = fecha;
    }
    public Cuenta() {}

    @Override
    public String toString() {
        return "Cuenta{" + "idCuenta=" + id_cuenta + ", saldo=" + saldo + ", dniTitular='" + dniTitular + " }";
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getId_cuenta() {
        return id_cuenta;
    }

    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    public String getDniTitular() {
        return dniTitular;
    }

    public void setDniTitular(String dniTitular) {
        this.dniTitular = dniTitular;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean insertarCuenta(){
        Properties props = new Properties();
        try (FileInputStream entrada = new FileInputStream("config.properties")) {
            props.load(entrada);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("db.url");
        String usuario = props.getProperty("db.user");
        String clave = props.getProperty("db.password");
        String insertar="INSERT INTO cuentas ( saldo, dni, fecha_alta) VALUES ( ?, ?, ?)";

        try (Connection connection= DriverManager.getConnection(url,usuario,clave)){
            PreparedStatement accion=connection.prepareStatement(insertar);
            accion.setDouble(1, this.getSaldo());
            accion.setString(2,this.getDniTitular());
            accion.setString(3,this.fecha);

            int cambios= accion.executeUpdate();
            System.out.println("Cambios: "+cambios);

            return cambios>0;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
