/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Usuario;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import edu.eci.pdsw.samples.persistence.DaoUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoUsuario implements DaoUsuario {

    Connection con;

    public JDBCDaoUsuario(Connection con) {
        this.con = con;
    }
        

    @Override
    public Usuario load(String email) throws PersistenceException {
        PreparedStatement ps;        
        String nombre="";
        Usuario u;
        String load = "SELECT nombre FROM"
                + " usuarios"
                + " WHERE email = ?";
        
            

      
        try {
            ps=con.prepareStatement(load);
            ps.setString(1, email);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                nombre=rs.getString("nombre");
            }          
            if (nombre.equals("")){
                throw new PersistenceException("no existe un usuario");
            }
            u = new Usuario(email,nombre);
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading "+email,ex);
        }
        return u;
        
        //throw new RuntimeException("No se ha implementado el metodo 'load' del DAOUsuarioJDBC");
    }

    @Override
    public void save(Usuario u) throws PersistenceException {
        PreparedStatement ps;
        boolean usuarioExistente=false;
        String insertString = "insert into "+"USUARIOS "+
                                   "values ( ? , ? )"; 
        try{
            Usuario nu=load(u.getEmail());
            usuarioExistente=true;
            
        }
        catch(PersistenceException e){
            try {
                ps= con.prepareStatement(insertString);
                ps.setString(1,u.getEmail());
                ps.setString(2,u.getNombre());
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCDaoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(usuarioExistente){
            throw new PersistenceException("Ya existe el usuario");
        }
        
        
        
        
        
        
        
    }

    @Override
    public void update(Usuario u) throws PersistenceException {
        PreparedStatement ps;
        /*try {
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while updating Usuario.",ex);
        } */
        throw new RuntimeException("No se ha implementado el metodo 'update' del DAOPAcienteJDBC");
    }
    
}
