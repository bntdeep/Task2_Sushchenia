package by.asushenya.sela.dao.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import by.asushenya.sela.dao.exception.DAOException;
public class ConnectionManager {

	
	static {
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch(ClassNotFoundException e){
			e.printStackTrace();// драйвер, конечно, не загрузился
			// но мы же никому и ничего не скажем
			// шшшш, может так пронесет и ничего не сломается
		}		
	}
	
	public static Connection getDBEqupmentConnection() throws DAOException{
		try {
		
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/equipments","root","1111");
			return con;
			
		} catch (SQLException e) {
		
			throw new DAOException (e);// а тут оборачивать исключение - излишне
			// на выходе со слоя оно у тебя дважды в DAOException обернется
			// ему что - холодно?
		}
	}
	
	public static void disconnectFromDB(ResultSet rs, Statement st, Connection con)throws DAOException{
			
		try{
			
			if(rs != null){rs.close();}// и тут показывала, что надо в несколлько try-catch блоков оформлять код
			// и даже расскажывала почему
			if(st != null){st.close();}
			if(con != null){con.close();}
		} catch (SQLException e){
	    	   throw new DAOException("DAOException getOwerReport: "+e.getMessage());			
	}
				
	}
}
