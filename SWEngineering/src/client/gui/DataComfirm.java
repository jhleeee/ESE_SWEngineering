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
	          conn =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/member", "root", "1234"); //������� �ݵ�� �޾ƾ� ��
	          stmt = (Statement)conn.createStatement(); // SQL�� ó���� Statement ��ü����
	                
	          ResultSet srs = stmt.executeQuery("select * from guest where id in ('" + id + "')");
	          printData(srs, "id","passoword", "win","lose");
	          
	     }
	     catch(ClassNotFoundException e)
	     {
	          System.out.println("JDBC ����̹� �ε� ����");
	     }
	     catch(SQLException e)
	     {
	          System.out.println("DB ���� ����");
	     }
	     catch(UnsupportedEncodingException e)
	     { //������ �´��� Ȯ��
	          System.out.println("�������� �ʴ� ������ Ÿ��");
	     }
	     
		return false;	     
		
	}
	 private static void printData(ResultSet srs, String col1, String col2, String col3,String col4) throws UnsupportedEncodingException, SQLException
	 {
		  while(srs.next())
		  {
			  //�������� ���� ������ true, �ƴϸ� false 
			   if(!col1.equals(""))
			   {
			    System.out.print(new String(srs.getString("id").getBytes("ISO-8859-1")));//�ѱ� �ڵ� ��ȯ
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
