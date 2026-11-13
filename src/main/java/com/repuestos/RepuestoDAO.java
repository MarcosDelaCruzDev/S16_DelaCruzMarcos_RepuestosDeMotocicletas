
package com.repuestos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepuestoDAO {

    // ==========================================
    // CONSULTAR TODOS LOS REPUESTOS
    // ==========================================

    public List<Repuesto> listar() {

        List<Repuesto> lista = new ArrayList<>();

        String sql = "SELECT * FROM repuestos ORDER BY id_repuesto ASC";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Repuesto repuesto = new Repuesto(
                        result.getInt("id_repuesto"),
                        result.getString("nombre"),
                        result.getString("marca"),
                        result.getString("modelo_compatible"),
                        result.getString("categoria"),
                        result.getDouble("precio"),
                        result.getInt("stock"),
                        result.getString("proveedor")
                );

                lista.add(repuesto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al consultar repuestos: " + e.getMessage(), e);
        }

        return lista;
    }

    // ==========================================
    // REGISTRAR REPUESTO
    // ==========================================

    public boolean registrar(Repuesto repuesto) {

        String sql = "INSERT INTO repuestos " +
                "(nombre, marca, modelo_compatible, categoria, precio, stock, proveedor) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, repuesto.getNombre());
            statement.setString(2, repuesto.getMarca());
            statement.setString(3, repuesto.getModeloCompatible());
            statement.setString(4, repuesto.getCategoria());
            statement.setDouble(5, repuesto.getPrecio());
            statement.setInt(6, repuesto.getStock());
            statement.setString(7, repuesto.getProveedor());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar repuesto: " + e.getMessage(), e);
        }
    }

    // ==========================================
    // ACTUALIZAR REPUESTO
    // ==========================================

    public boolean actualizar(Repuesto repuesto) {

        String sql = "UPDATE repuestos SET " +
                "nombre = ?, marca = ?, modelo_compatible = ?, " +
                "categoria = ?, precio = ?, stock = ?, proveedor = ? " +
                "WHERE id_repuesto = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, repuesto.getNombre());
            statement.setString(2, repuesto.getMarca());
            statement.setString(3, repuesto.getModeloCompatible());
            statement.setString(4, repuesto.getCategoria());
            statement.setDouble(5, repuesto.getPrecio());
            statement.setInt(6, repuesto.getStock());
            statement.setString(7, repuesto.getProveedor());
            statement.setInt(8, repuesto.getIdRepuesto());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar repuesto: " + e.getMessage(), e);
        }
    }

    // ==========================================
    // ELIMINAR REPUESTO
    // ==========================================

    public boolean eliminar(int idRepuesto) {

        String sql = "DELETE FROM repuestos WHERE id_repuesto = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idRepuesto);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar repuesto: " + e.getMessage(), e);
        }
    }
}
