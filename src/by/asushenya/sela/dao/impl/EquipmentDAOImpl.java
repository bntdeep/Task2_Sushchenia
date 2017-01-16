package by.asushenya.sela.dao.impl;

import by.asushenya.sela.dao.EquipmentsDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import by.asushenya.sela.bean.Equipment;
import by.asushenya.sela.bean.User;
import by.asushenya.sela.bean.Ower;
import by.asushenya.sela.dao.exception.DAOException;
import by.asushenya.sela.dao.util.ConnectionManager;

import java.util.List;
import java.util.ArrayList;

public class EquipmentDAOImpl implements  EquipmentsDAO{
	
	static Connection con = null;
	static Statement st = null;
	static ResultSet rs = null;
		
	@Override
	public void addEquipment(Equipment equipment) throws DAOException{	
		
		try{
			con = ConnectionManager.getDBEqupmentConnection();
			String sql = "insert into equipment ( name, kind, cost, quantity) values(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, equipment.getName());
			ps.setString(2, equipment.getKind());
			ps.setFloat (3, equipment.getCost());
			ps.setInt   (4, equipment.getQuantity());
		
			ps.executeUpdate();
		} catch (SQLException e){
			
			throw new DAOException ("DAOException addEquipment: "+e.getMessage());
			
			} finally{
				
				ConnectionManager.disconnectFromDB(rs, st, con);
			}	
	}
	
	@Override 
	public List<Ower> getOwerReport() throws DAOException{
		List<Ower> owerList = new ArrayList<Ower>();
		try{
			con = ConnectionManager.getDBEqupmentConnection();
			st = con.createStatement();
			rs = st.executeQuery("select user_id from rent where is_rented = true group by user_id;");
			
			while(rs.next()){ //перебираем пользователей которые должны вернуть снаряжение
				Statement getUserByIdStatement = con.createStatement();
				ResultSet userResultSet = getUserByIdStatement.executeQuery("select * from user where id = "+rs.getInt(1)); //получим информацию о пользователе
				
				userResultSet.next();
				User user = new User(userResultSet.getInt(1),   //создадим объект пользователя
									 userResultSet.getString(2),
									 userResultSet.getString(3));
				
				Statement getOwerEquipment = con.createStatement(); //получим снаряжение которое должен вернуть пользователь
				ResultSet owerEquipmentResultSet = getOwerEquipment.executeQuery("select equipment_id,equipment.name, equipment.kind,  equipment.cost , equipment_amount  from rent join equipment on equipment.id = rent.equipment_id where user_id = "+user.getId()+" and is_rented = true;");
			
				owerEquipmentResultSet.last();
				int i= owerEquipmentResultSet.getRow();	// столько едениц снаряжения должен вернуть пользователь		
				Equipment[] owerEquipmentArray = new Equipment[i];
				
				owerEquipmentResultSet.beforeFirst();
				
				int equipmentCounter = 0;
				while(owerEquipmentResultSet.next()){ // заполним массив снаряжением
					owerEquipmentArray[equipmentCounter++] = new Equipment(owerEquipmentResultSet.getInt(1),
																		   owerEquipmentResultSet.getString(2),
																		   owerEquipmentResultSet.getString(3),
																		   owerEquipmentResultSet.getFloat(4),
																		   owerEquipmentResultSet.getInt(5));
				}
				
				Ower ower = new Ower(); // создадим объект должника
				ower.setUser(user);		
				ower.setEquipments(owerEquipmentArray);
				
				owerList.add(ower); // поместим должника в список должников
			}
			
			return owerList;
		} catch (SQLException e){
			
			throw new DAOException("DAOException getOwerReport: "+e.getMessage());
			
			} finally{	
				
					ConnectionManager.disconnectFromDB(rs, st, con);			      
			}		
	}
	
	@Override 
	public String getAllEquipment() throws DAOException{
		
		StringBuilder response = new StringBuilder();
		
		try{
			con = ConnectionManager.getDBEqupmentConnection();
			st  = con.createStatement();
			String sql = "select * from equipment";
			rs  = st.executeQuery(sql);			
					
			while(rs.next()){
				response.append(rs.getInt(1));
				response.append(" "+rs.getString(2));
				response.append(" "+rs.getString(3));
				response.append(" "+rs.getFloat(4));
				response.append(" "+rs.getInt(5));
				
				response.append("\n");
			}
			return new String(response);
			
		} catch(SQLException e){
			
			throw new DAOException (e);
		}
	}
	
}
