package es.uji.al426285;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        //fecha
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha_actual = currentDate.format(formatter);

        int opcion = 1;
        while (0 < opcion && opcion < 4) {

            System.out.println("Bienvenido/a----\n");
            System.out.print("Elija una opción:\n" +
                    "\t1.-Crear una nueva cuenta\n" + "\t2.-Consultar información\n" + "\t3.-Realizar una operación\n" + "\t4.-Salir\n");
            System.out.println("Opción escogida: ");
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    //OBTENCIÓN DE DATOS
                    System.out.print("Ingrese el nombre: ");
                    String nombre = sc.next();
                    System.out.print("Ingrese los apellidos: ");
                    String apellidos = sc.next();
                    System.out.print("Ingrese el correo: ");
                    String correo = sc.next();
                    System.out.print("Ingrese el teléfono: ");
                    String telefono = sc.next();
                    System.out.print("Ingrese el dni: ");
                    String dni = sc.next();
                    System.out.print("Ingrese la dirección: ");
                    String direccion = sc.next();
                    System.out.print("Ingrese la fecha de nacimiento (DD/MM/YYYY): ");
                    String fechaNacimiento = sc.next();
                    //CREACIÓN Y ASIGNACIÓN DE DATOS AL CLIENTE
                    Cliente cliente = new Cliente();
                    cliente.setNombre(nombre);
                    cliente.setApellidos(apellidos);
                    cliente.setDni(dni);
                    cliente.setDireccion(direccion);
                    cliente.setTelefono(telefono);
                    cliente.setEmail(correo);
                    cliente.setFechaNacimiento(fechaNacimiento);
                    //CREACIÓN DE SU CUENTA
                    Cuenta cuenta = new Cuenta();
                    cuenta.setSaldo(0.0); // Saldo inicial 0, BARAJR OPCION DE AÑADIR DINERO AL CREAR LA CUENTA
                    cuenta.setDniTitular(dni); // DNI del titular
                    cuenta.setFecha(fecha_actual);

                    //AÑADIR CLIENTE
                    if (cliente.insertarCliente()) {
                        System.out.println("¡Cliente insertado correctamente!");
                    } else {
                        System.out.println("¡UPS! Ha habido un error al insertar el cliente");
                    }

                    //AÑADIR CUENTA
                    if (cuenta.insertarCuenta()) {
                        System.out.println("¡Cuenta insertada correctamente!");
                    } else {
                        System.out.println("¡UPS! Ha habido un error al insertar la cuenta");

                    }
                    break;

                case 2:
                    Consulta consulta = new Consulta();
                    System.out.print("Ingrese el dni: ");
                    dni = sc.next();
                    double saldo_actual = consulta.consultar_saldo(dni);
                    System.out.println("Su saldo actual es de " + saldo_actual + "€");
                    break;

                case 3:
                    System.out.print("\n\nElija una operación:\n" +
                            "\t1.-Ingresar dinero\n" + "\t2.-Retirar dinero\n" + "\t3.-Realizar una transferencia\n" + "\t4.-Volver atrás\n");
                    System.out.println("Operación escogida: ");
                    int operacion = sc.nextInt();
                    System.out.print("Ingrese el dni: ");
                    dni = sc.next();
                    double monto = 0;
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String fecha_hora = currentDateTime.format(formatter);
                    Transaccion trans;
                    Consulta consulta_id_origen = new Consulta();
                    Consulta consulta_id_destino = new Consulta();
                    int id_origen = consulta_id_origen.consulta_id(dni);
                    switch (operacion) {
                        case 1:
                            System.out.print("Escriba el monto a ingresar: ");
                            monto = sc.nextDouble();
                            trans = new Transaccion(id_origen, monto, fecha_hora, Transaccion.INGRESO, null);
                            trans.insertarTransaccion();
                            break;
                        case 2:
                            System.out.print("Escriba el monto a retirar: ");
                            monto = sc.nextDouble();
                            trans = new Transaccion(id_origen, monto, fecha_hora, Transaccion.RETIRO, null);
                            trans.insertarTransaccion();
                            break;
                        case 3:
                            System.out.print("Escriba el monto a enviar: ");
                            monto = sc.nextDouble();
                            System.out.println("Escriba el dni del titular de la cuenta destino");
                            String dni_destino = sc.next();
                            int id_destino = consulta_id_destino.consulta_id(dni_destino);
                            trans = new Transaccion(id_origen, monto, fecha_hora, Transaccion.TRANSFERENCIA, id_destino);
                            trans.insertarTransaccion();

                    }
                    System.out.println();
            }
            System.out.println("Hasta la próxima");
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
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String fecha = currentDateTime.format(formatter);
//        Transaccion transaccion=new Transaccion(1,500,fecha,2,2);
//        //prueba transaccion
//        if (transaccion.insertarTransaccion())
//            System.out.println("¡Transacción realizada correctamente!");
//        else
//            System.out.println("¡UPS! Ha habido un error al realizar la transacción");

        }
    }
}
