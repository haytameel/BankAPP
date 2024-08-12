package es.uji.al426285;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws SQLException {

        System.out.println("Bienvenido----\n");
        /*
        //cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("ISMA");cliente.setApellidos("Ramirez");cliente.setDni("2121131P");
        cliente.setDireccion("BURRIANA");cliente.setTelefono("62713133");
        cliente.setEmail("pepito.perez@example.com");cliente.setFechaNacimiento("15/12/2001");
        //saldo
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(10.0); // Saldo inicial
        cuenta.setDniTitular("2121131P"); // DNI del titular
        cuenta.setFecha("20/06/2024");
        //transaccion
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = currentDateTime.format(formatter);
        Transaccion transaccion=new Transaccion(46,20,fecha,0,null);

        //prueba cliente
        if (cliente.insertarCliente()){
            System.out.println("¡Cliente insertado correctamente!");
        }
        else{
            System.out.println("¡UPS! Ha habido un error al insertar el cliente");
        }

        //prueba cuenta
        if (cuenta.insertarCuenta()){
            System.out.println("¡Cuenta insertada correctamente!");
        }
        else{
            System.out.println("¡UPS! Ha habido un error al insertar la cuenta");

        }
*/
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fecha = currentDateTime.format(formatter);
        Transaccion transaccion=new Transaccion(1,500,fecha,2,2);
        //prueba transaccion
        if (transaccion.insertarTransaccion())
            System.out.println("¡Transacción realizada correctamente!");
        else
            System.out.println("¡UPS! Ha habido un error al realizar la transacción");

    }
}
