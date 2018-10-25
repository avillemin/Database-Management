
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class JdbcCOL362{
 
    private final String url = "jdbc:postgresql://localhost/design";
    private final String user = "postgres";
    private final String password = "col362";
    private static int taille = 500000;
    
    static int[][] randomMatrix = new int [taille][2];
    static Random rand = new Random(); 
    
    
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }
 

    public static void main(String[] args) {
    	JdbcCOL362 app = new JdbcCOL362();
        
        long millis4 = System.currentTimeMillis();
        for (int i = 0; i < taille; i++) {     
            for (int j = 0; j < 2; j++) {
                randomMatrix[i][j] = ThreadLocalRandom.current().nextInt(0, 1000000000 + 1);;
            }

        }
        long millis3 = System.currentTimeMillis();
    	System.out.println(millis3-millis4);
        app.insertRegisters();
    }
    
    
	public void insertRegisters() {
	        String SQL = "INSERT INTO registers(student_id,course_id) "
	                + "VALUES(?,?)";
	 
	        try (Connection conn = connect();
	                PreparedStatement pstmt = conn.prepareStatement(SQL)) {
	        	long millis = System.currentTimeMillis();
	        	System.out.println(millis);
	        	for (int i=0;i<taille;i++) {
	            pstmt.setString(1, String.valueOf(randomMatrix[i][0]));
	            pstmt.setString(2, String.valueOf(randomMatrix[i][1]));
	 
	            pstmt.executeUpdate();
	        	}
	        	long millis2 = System.currentTimeMillis();
	        	System.out.println(millis2);
	        	System.out.println(millis2-millis);

	            
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
}