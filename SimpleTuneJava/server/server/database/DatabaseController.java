package server.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.Types;
import org.springframework.jdbc.core.JdbcTemplate;

import simpleTune.gui.data.TableData;
import simpleTune.romEntity.romParse.RomImage;

public class DatabaseController {
	private Log logger = LogFactory.getLog(getClass());
	private static DatabaseController databaseController = null;	
	
	// Database specific
	private BasicDataSource ds = null;
	private JdbcTemplate jdbcTemplate = null;
	
	public static DatabaseController getInstance(){
		if(databaseController == null){
			databaseController = new DatabaseController();
		}
		
		return databaseController;
	}

	public DatabaseController(){
		// Define database connection information
		String driver = "org.hsqldb.jdbcDriver";
		String url = "jdbc:hsqldb:file:database/st;shutdown=true";
		String username = "sa";
		String password = "";

		// Build up datasource to be used by spring jdbc template
		ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		
		// Setup template used for all sorts of query goodness
		jdbcTemplate = new JdbcTemplate(ds);
	}
	

	public void insertROM(String userName, String fileName, byte[] romBytes){
		Object[] params = new Object[]{
				new java.sql.Timestamp(new java.util.Date().getTime()),
				userName,
				fileName,
				romBytes};
		
		int[] types = new int[]{Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR, Types.BINARY};
		
		String sql = "insert into ROMFILES (added, username, filename, dataobject) values (?,?,?,?)";
		
		jdbcTemplate.update(sql, params, types);
	}
	
	public void tesetROMInsert(){
		Object[] params = new Object[]{1,new RomImage("/home/botman/Desktop/32bitlarge.hex").getBytes()};
		
		String sql = "insert into romimages (id,file ) values (?,?)";
		
		int[] types = new int[2];
		types[0] = Types.INTEGER;
		types[1] = Types.BINARY;
		
		
		jdbcTemplate.update(sql, params, types);
	}
	
	public void testTableQuery(){
		int rowCount = this.jdbcTemplate.queryForInt("select count(0) from ROMIMAGES");
		logger.debug("Found this many rows in ROMIMAGES: "+rowCount);
	}
	
	// Utility methods for serialization of java objects
	private byte[] serialise(TableData state) {
	    byte[] data = null;
		try {
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(bos);
	        oos.writeObject(state);
	        oos.flush();
	        data = bos.toByteArray();
	    } catch (IOException e) {
	        logger.error(e);
	    }
	    
	    return data;
	}
	
	protected TableData deserialise(byte[] byteArray) {
		TableData data = null;
	    try {
	        ObjectInputStream oip = new ObjectInputStream(new ByteArrayInputStream(byteArray));
	        data = (TableData)oip.readObject();
	    } catch (IOException e) {
	        throw new IllegalArgumentException(e);
	    } catch (ClassNotFoundException e) {
	        throw new IllegalArgumentException(e);
	    }
	    return data;
	}
}
