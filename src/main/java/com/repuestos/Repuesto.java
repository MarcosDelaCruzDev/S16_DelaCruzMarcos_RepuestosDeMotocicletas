
package com.repuestos;

public class Repuesto {

    private int idRepuesto;
    private String nombre;
    private String marca;
    private String modeloCompatible;
    private String categoria;
    private double precio;
    private int stock;
    private String proveedor;

    public Repuesto() {
    }

    public Repuesto(int idRepuesto, String nombre, String marca,
                    String modeloCompatible, String categoria,
                    double precio, int stock, String proveedor) {

        this.idRepuesto = idRepuesto;
        this.nombre = nombre;
        this.marca = marca;
        this.modeloCompatible = modeloCompatible;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    public Repuesto(String nombre, String marca,
                    String modeloCompatible, String categoria,
                    double precio, int stock, String proveedor) {

        this.nombre = nombre;
        this.marca = marca;
        this.modeloCompatible = modeloCompatible;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModeloCompatible() {
        return modeloCompatible;
    }

    public void setModeloCompatible(String modeloCompatible) {
        this.modeloCompatible = modeloCompatible;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
