package client.gui;
import java.sql.Connection;
import java.sql.DriverManager; 
import com.mysql.jdbc.Statement;
 
public class insertDAO 
{
	public static void main(String[] args) 
	{
 
	}
	public static boolean create(DTO dto) throws Exception 
	{
 
	   boolean flag = false;
	   Connection con = null;
	   Statement stmt = null; // 데이터를 전송하는 객체
	   String id = dto.getId();
	   String password = dto.getPassword();
	   String win = dto.getWin();
	   String lose = dto.getLose();
	   
	   String sql = "INSERT INTO guest(id, password, win, lose) VALUES";
 
	   try 
	   {
		   sql += "('" + new String(id.getBytes(), "ISO-8859-1") + "','"  
				   + new String(password.getBytes(), "ISO-8859-1") + "','"
				   + new String(win.getBytes(), "ISO-8859-1") + "','"
				   + new String(lose.getBytes(), "ISO-8859-1") + "')";
    
		   Class.forName("com.mysql.jdbc.Driver");
		   con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/member", "root", "1234");
		   stmt = (Statement) con.createStatement();
		   stmt.executeUpdate(sql);
 
		   flag = true;
	   } 
	   catch (Exception e) 
	   {
		   System.out.println(e);
		   flag = false;
	   } 
	   finally 
	   {
 
		   try 
		   {
 
			   if (stmt != null)
				   stmt.close();
			   if (con != null)
				   con.close();
		   } 
		   catch (Exception e) 
		   {
			   System.out.println(e.getMessage());
		   }
	   }
 
	   return flag;
	}
}