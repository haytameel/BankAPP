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
        //cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Pepito");cliente.setApellidos("Ramirez");cliente.setDni("11111111p");
        cliente.setDireccion("BURRIANA");cliente.setTelefono("62713133");
        cliente.setEmail("pepito.perez@example.com");cliente.setFechaNacimiento("15/12/2001");
        //saldo
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(10.0); // Saldo inicial
        cuenta.setDniTitular("11111111p"); // DNI del titular
        cuenta.setFecha("20/06/2024");
        //transaccion
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        Transaccion transaccion=new Transaccion(46,20, formattedDateTime);

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

        //prueba transaccion
        if (transaccion.insertarTransaccion())
            System.out.println("¡Transacción realizada correctamente!");
        else
            System.out.println("¡UPS! Ha habido un error al realizar la transacción");

    }
}
