package test;

import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DataComfirm 
{
	 public static void main(String[] args) 
	 {
		 
	 }
	 public static boolean create(DTO dto) throws Exception 
	{
		 java.sql.Connection conn;
		 Statement stmt = null;
		 String id = dto.getId();
	     try 
	     {
	          Class.forName("com.mysql.jdbc.Driver");
	          conn =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/member", "root", "1234"); //결과값을 반드시 받아야 함
	          stmt = (Statement)conn.createStatement(); // SQL문 처리용 Statement 객체생성
	                
	          ResultSet srs = stmt.executeQuery("select * from guest where id in ('" + id + "')");
	          printData(srs, "id","passoword", "win","lose");
	          
	     }
	     catch(ClassNotFoundException e)
	     {
	          System.out.println("JDBC 드라이버 로드 오류");
	     }
	     catch(SQLException e)
	     {
	          System.out.println("DB 연결 오류");
	     }
	     catch(UnsupportedEncodingException e)
	     { //인코팅 맞는지 확인
	          System.out.println("지원되지 않는 인코팅 타입");
	     }
	     
		return false;	     
		
	}
	 private static void printData(ResultSet srs, String col1, String col2, String col3,String col4) throws UnsupportedEncodingException, SQLException
	 {
		  while(srs.next())
		  {
			  //다음으로 갈수 있으면 true, 아니면 false 
			   if(!col1.equals(""))
			   {
			    System.out.print(new String(srs.getString("id").getBytes("ISO-8859-1")));//한글 코드 변환
			   }
			   
			   if(!col2.equals(""))
			   {
			    System.out.print("\t|\t"+new String(srs.getString("password").getBytes("ISO-8859-1")));
			   }
			   
			   if(!col3.equals(""))
			   {
			    System.out.print("\t|\t"+new String(srs.getString("win")));
			   }
			   if(!col3.equals(""))
			   {
				    System.out.println("\t|\t"+new String(srs.getString("lose")));
			   }
		  }
	}		 
}
