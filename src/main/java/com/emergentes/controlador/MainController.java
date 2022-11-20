package com.emergentes.controlador;

import com.emergentes.modelo.Producto;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String op;
            op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";
            ArrayList<Producto> lista = new ArrayList<Producto>();
            ConexionDB canal = new ConexionDB();

            Connection conn = canal.conectar();
            PreparedStatement ps;
            ResultSet rs;

            if (op.equals("list")) {

                //Obtener la lista de registro
                String sql = "select * from productos";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Producto prod = new Producto();
                    prod.setId(rs.getInt("id"));
                    prod.setNombre(rs.getString("nombre"));
                    prod.setPrecio(rs.getFloat("precio"));
                    prod.setCantidad(rs.getInt("cantidad"));

                    lista.add(prod);

                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("index.jsp").forward(request, response);

            }

            if (op.equals("nuevo")) {
                //Nuevo registro
                Producto pro = new Producto();

                request.setAttribute("prod", pro);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            }
            if (op.equals("eliminar")) {
                //Eliminar
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "delete from libros where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                ps.executeUpdate();
                response.sendRedirect("mainController");

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            Float precio = Float.parseFloat(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("categoria"));

            Producto prod = new Producto();

            prod.setNombre(nombre);
            prod.setPrecio(precio);
            prod.setCantidad(cantidad);

            ConexionDB canal = new ConexionDB();
            Connection conn = canal.conectar();

            PreparedStatement ps;
            if (id == 0) {
                //Nuevo registro
                String sql = "insert into libros(isbn,titulo,categoria) value(?,?,?)";

                ps = conn.prepareStatement(sql);
                ps.setString(1, prod.getNombre());
                ps.setFloat(2, prod.getPrecio());
                ps.setInt(3, prod.getCantidad());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error en SQL" + e.getMessage());
        }
    }

}
