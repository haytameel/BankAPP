package es.uji.al426285;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Cliente {
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
    private String fechaNacimiento;//barajar tipo DATE

    public Cliente() {

    }
    public Cliente(String nombre, String apellidos, String dni, String direccion, String telefono, String email, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    @Override
    public String toString(){
        return "Cliente--> "+apellidos+", "+nombre+ " con dni '"+dni+"'.";
    }

    public static String getFechaUSA(String fecha){//transformar fecha europea en fecha usa
        String[] vector =fecha.split("/");
        String dia=vector[0];
        String mes=vector[1];
        String ano=vector[2];
        return ano+"/"+mes+"/"+dia;
    }

    public boolean insertarCliente(){
        String url="jdbc:mysql://localhost:3306/banco";
        String usuario="root";
        String clave="hayta";
        String insertar="INSERT INTO clientes (nombre, apellidos, email, telefono, dni, direccion, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection= DriverManager.getConnection(url,usuario,clave)){
            PreparedStatement accion=connection.prepareStatement(insertar);

            accion.setString(1,this.getNombre());
            accion.setString(2,this.getApellidos());
            accion.setString(3,this.getEmail());
            accion.setString(4,this.getTelefono());
            accion.setString(5,this.getDni());
            accion.setString(6,this.getDireccion());
            accion.setString(7, getFechaUSA(this.getFechaNacimiento()));

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
