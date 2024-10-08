package es.uji.al426285.Controlador;

import es.uji.al426285.Modelo.Cliente;
import es.uji.al426285.Modelo.Consulta;
import es.uji.al426285.Modelo.Cuenta;
import es.uji.al426285.Modelo.Transaccion;

import java.time.LocalDate;

public class Controlador {

    public Cliente crear_cliente(String nombre, String apellidos, String dni, String direccion, String telefono, String email, String fechaNacimiento) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellidos(apellidos);
        cliente.setDni(dni);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        cliente.setFechaNacimiento(fechaNacimiento);

        return cliente;
    }

    public Cuenta crear_cuenta(String dni) {
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(0.0);
        cuenta.setDniTitular(dni);
        cuenta.setFecha(LocalDate.now().toString());
        return cuenta;
    }

    public boolean insertar_cliente(Cliente cliente) {
        if (cliente.insertarCliente()) {
            System.out.println("¡Cliente insertado correctamente!");
            return true;
        }
        System.out.println("¡UPS! Ha habido un error al insertar el cliente");
        return false;
    }

    public boolean insertar_cuenta(Cuenta cuenta) {
        if (cuenta.insertarCuenta()) {
            System.out.println("¡Cuenta insertada correctamente!");
            return true;
        }
        System.out.println("¡UPS! Ha habido un error al insertar la cuenta");
        return false;

    }

    public Double realizar_consulta(String dni) throws Exception {
        Consulta consulta=new Consulta();
         try {
            Double saldo= consulta.consultar_saldo(dni);
            return saldo;
         }
         catch(Exception e){
             throw new Exception("Cuenta inexistente.");
         }
    }
    public boolean realizar_transaccion(int idCuenta, double cantidad, String fecha, int tipo, Integer idDestino) throws Exception {
        Transaccion transaccion=new Transaccion(idCuenta, cantidad, fecha, tipo, idDestino);
        try {
        transaccion.insertarTransaccion();
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return true;
    }
    public int consultar_id(String dni) throws Exception {
        Consulta consulta=new Consulta();
        int id;
        try {
           id= consulta.consulta_id(dni);
        }
        catch(Exception e){
            throw new Exception("Dni no encontrado.");
        }
        return id;
    }
}
