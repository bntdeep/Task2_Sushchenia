package by.asushenya.sela.dao.impl;

import by.asushenya.sela.dao.UserDAO;
import by.asushenya.sela.dao.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.asushenya.sela.bean.Good;
import by.asushenya.sela.bean.User;
import by.asushenya.sela.dao.exception.DAOException;

public class UserDAOImpl implements UserDAO {

	static Connection con = null;// и атрибуты доступа потерял
	static Statement st = null;// и замем-то вынес в поля класса и разделил из не то, чтобы для всех пользователей объекта, а вообща для всех объектов
	// голову явно не включал
	static ResultSet rs = null;
		
	@Override 
	public void registeredNewUser(User user) throws DAOException{
		try{
			con = ConnectionManager.getDBEqupmentConnection();
			String sql = "insert into user ( login, password) values(?,?)";// вот это надо было вынести в private static final поля
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
		
			ps.executeUpdate();
		} catch (SQLException e){		
	
			throw new DAOException ("DAOException registeredNewUser: "+e.getMessage());// и какого лешего ты избавляешься от SQLException
			// я же показывала Антипаттерн
			// и ты именно тот, кто его и реализовал
			// поздравляю!
			
			} finally{
				ConnectionManager.disconnectFromDB(rs, st, con);
		}	
	}
	

	@Override
	public void rentEquipment(User user, Good good) throws DAOException {
		try{
			con = ConnectionManager.getDBEqupmentConnection();
			String sql = "insert rent ( user_id, equipment_id, equipment_amount, rent_cost, date_from, is_rented) values(?,?,?,?,?,?)";
		
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, user.getId());
			ps.setInt(2, good.getId());
			ps.setInt(3, good.getAmount());
			ps.setFloat(4, good.getAmount() * good.getPrice());
			ps.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
			ps.setBoolean(6, true);
		
			ps.executeUpdate();
		} catch (SQLException e){
			throw new DAOException ("DAOException rentEquipment: "+e.getMessage());
			
			} finally{
				
			ConnectionManager.disconnectFromDB(rs, st, con);
		}			
	}
	
	@Override
	public String signIn(User user) throws DAOException{
		
		String response = "there is no user in the database";
		try{
			con = ConnectionManager.getDBEqupmentConnection();
			st = con.createStatement();
			String sql = "select password, id from user where login like '"+user.getLogin()+"'";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				if ( rs.getString(1).equals(user.getPassword())){
					
					response = rs.getInt(2)+ " Sign In Successfful";
				} else{
					response = "Sign In Failed Password is not correct";
				}
			}	
			
		} catch(SQLException e){
			
			throw new DAOException (e);
		} finally{
			
			ConnectionManager.disconnectFromDB(rs, st, con);
		}
		
		return response;
	}
}
