import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

class FacesDatabase {
	Connection _con = null;
	
	boolean connect() {
		boolean connected = false;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			_con = DriverManager.getConnection("jdbc:mysql:///faces","root", "");
			connected = false == _con.isClosed();
		} catch( Exception ex ) {
			Err.err(ex);
		}
		return connected;
	}

	void disconnect() {
		try {
			_con.close();
		} catch( Exception ex ) {
			Err.err(ex);
		}
	}

	int getUserId( String email, String password ) {
		int userId = -1;
		
		try {
			PreparedStatement stmt = 
				_con.prepareStatement("SELECT userID FROM profile WHERE email = ? and password = ?");
		
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			
			if( rs.next() ) {
				userId = rs.getInt("userID");
			}
			
			rs.close();
			stmt.close();
			
		} catch( Exception ex ) {
			Err.err(ex);
		}
		
		return userId;
	}
	
	int registerUser( String name, String email, String password, Date birth, String aboutme ) {
		int userId = -1;
		try {
			PreparedStatement stmt = 
				_con.prepareStatement(
						"INSERT INTO profile (name,email,password,bdate,aboutme) VALUES (?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS );
		
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, password);
			stmt.setDate(4, new java.sql.Date(birth.getTime()));
			stmt.setString(5, aboutme);
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			if( rs.next() ) {
				userId = rs.getInt(1);
			}
			
			rs.close();
			stmt.close();
			
		} catch( Exception ex ) {
			Err.err(ex);
		}
		return userId;
	}

	Date getLastLogin( int userId ) {
		Date lastLogin = null;
		
		try {
			PreparedStatement stmt = 
				_con.prepareStatement("SELECT lastLogin FROM profile WHERE userID = ?");
		
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			if( rs.next() ) {
				lastLogin = new Date(rs.getTimestamp("lastLogin").getTime());
			}
			
			rs.close();
			stmt.close();
			
		} catch( Exception ex ) {
			Err.err(ex);
		}
		
		return lastLogin;
	}
	
	boolean setLastLogin( int userId, Date login ) {
		boolean success = false;
		try {
			PreparedStatement stmt = 
				_con.prepareStatement( "UPDATE profile SET lastLogin = ? WHERE userId = ?" );
		
			stmt.setTimestamp(1, new java.sql.Timestamp( login.getTime() ));
			stmt.setInt(2, userId);
			
			stmt.executeUpdate();
			stmt.close();
			
		} catch( Exception ex ) {
			Err.err(ex);
		}
		return success;
	}
}