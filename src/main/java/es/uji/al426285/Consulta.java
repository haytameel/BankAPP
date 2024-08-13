package es.uji.al426285;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Consulta {

    public double consultar_saldo(String dni) {
        double saldo = 0;
        //////////////////////////////////////////////////////////////////
        Properties props = new Properties();
        try (FileInputStream entrada = new FileInputStream("config.properties")) {
            props.load(entrada);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("db.url");
        String usuario = props.getProperty("db.user");
        String clave = props.getProperty("db.password");
        //////////////////////////////////////////////////////////////////
        String consulta = "SELECT saldo FROM cuentas WHERE dni = ?";
        try (Connection connection = DriverManager.getConnection(url, usuario, clave)) {
            PreparedStatement consulta_saldo = connection.prepareStatement(consulta);
            consulta_saldo.setString(1, dni);
            ResultSet resultado = consulta_saldo.executeQuery();
            resultado.next();
            saldo = resultado.getDouble("saldo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return saldo;

    }

    public int consulta_id(String dni){
        int id = -1;
        //////////////////////////////////////////////////////////////////
        Properties props = new Properties();
        try (FileInputStream entrada = new FileInputStream("config.properties")) {
            props.load(entrada);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("db.url");
        String usuario = props.getProperty("db.user");
        String clave = props.getProperty("db.password");
        //////////////////////////////////////////////////////////////////
        String consulta = "SELECT id_cuenta FROM cuentas WHERE dni = ?";
        try (Connection connection = DriverManager.getConnection(url, usuario, clave)) {
            PreparedStatement consulta_saldo = connection.prepareStatement(consulta);
            consulta_saldo.setString(1, dni);
            ResultSet resultado = consulta_saldo.executeQuery();
            resultado.next();
            id = resultado.getInt("id_cuenta");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
