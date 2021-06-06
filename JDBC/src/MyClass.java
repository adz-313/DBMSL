import java.sql.*;
import java.util.Scanner;
public class MyClass 
{
	Connection con;
	Statement st;
	ResultSet rs;
	MyClass()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/professor", "root", "root");
		    st = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void display(String tableName) {
        try {
            while(rs.next()) 
            {
                if(tableName.equals("Departments"))
                {
                	int dept_id = rs.getInt(1);
                    String dept_name = rs.getString(2);
                    System.out.println(dept_id+ " " +dept_name);
                }
                else if(tableName.equals("Professors"))
                {
                	int prof_id = rs.getInt(1);
                	String prof_fname = rs.getString(2);
                	String prof_lname = rs.getString(3);
                	int dept_id = rs.getInt(4);
                	String designation = rs.getString(5);
                	int salary = rs.getInt(6);
                	String doj = rs.getString(7);
                	String email = rs.getString(8);
                	long phone = rs.getInt(9);
                	String city = rs.getString(10);
                	System.out.println(prof_id + " " + prof_fname + " " + prof_lname + " " + dept_id + " " + designation + " " + salary + " " + doj + " " + email + " " + phone + " " + city);            	 
                }
                else if(tableName.equals("Works"))
                {
                	int prof_id = rs.getInt(1);
                	int dept_id = rs.getInt(2);
                	int duration = rs.getInt(3);
                	System.out.println(prof_id + " " + dept_id + " " + duration);
                }
                else
                {
                	int prof_id = rs.getInt(1);
                	String shift = rs.getString(2);
                	int working_hours = rs.getInt(3);
                	System.out.println(prof_id + " " + shift + " " + working_hours);
                }
            }
            //con.close();
        }
        catch(Exception e) 
        {
            System.out.println(e);
        }
    }
    
    void add(String tableName) 
    {
    	Scanner sc =  new Scanner(System.in);
    	String query = "";
    	if(tableName.equals("Departments"))
    	{
    		int dept_id = sc.nextInt();
    		sc.nextLine();
            String dept_name = sc.nextLine();
            query = "INSERT INTO departments " + "VALUES (" + dept_id + ","+ "'" +dept_name+ "'"+ ");";
    	}
    	else if(tableName.equals("Professors"))
        {
        	int prof_id = sc.nextInt();
        	sc.nextLine();
        	String prof_fname = sc.nextLine();
        	String prof_lname = sc.nextLine();
        	int dept_id = sc.nextInt();
        	sc.nextLine();
        	String designation = sc.nextLine();
        	int salary = sc.nextInt();
        	sc.nextLine();
        	String doj = sc.nextLine();
        	String email = sc.nextLine();
        	long phone = sc.nextLong();
        	sc.nextLine();
        	String city = sc.nextLine();
        	query = "INSERT INTO professors " + "VALUES (" + prof_id + ",'" + prof_fname + "','" + prof_lname + "'," + dept_id + ",'" + designation + "', " + salary + " ,'"+ doj + "','" + email + "', " + phone + " ,'" + city+ "'"+ ");";            	 
        }
    	else if(tableName.equals("Works"))
        {
        	int prof_id = sc.nextInt();
        	int dept_id = sc.nextInt();
        	int duration = sc.nextInt();
        	query = "INSERT INTO works " + "VALUES (" + prof_id + ", " + dept_id + ", " + duration + ");";
        }
    	else
    	{
    		int prof_id = sc.nextInt();
        	int shift = sc.nextInt();
        	int working_hours = sc.nextInt();
        	query = "INSERT INTO shift " + "VALUES (" + prof_id + ", " + shift + ", " + working_hours + ");";
    	}
        try 
        {
            st.executeUpdate(query);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    void createTableDepartments() throws SQLException
    {
    	String query = "create table departments( "
    			+ "dept_id integer(10) primary key auto_increment, "
    			+ "dept_name varchar(20));";
    	st.executeUpdate(query);
    }
    
    void createTableProfessors() throws SQLException
    {
    	String query = "create table professors( "
    			+ "prof_id integer(10) primary key auto_increment, "
    			+ "prof_fname varchar(30), "
    			+ "prof_lname varchar(30), "
    			+ "dept_id integer(10) not null, "
    			+ "designation varchar(15), "
    			+ "salary int(10), "
    			+ "doj date, "
    			+ "email varchar(30), "
    			+ "phone int(10), "
    			+ "city varchar(15), "
    			+ "foreign key (dept_id) "
    			+ "references departments(dept_id) on delete cascade); ";
    	st.executeUpdate(query);
    }
    
    void createTableWorks() throws SQLException
    {
    	String query = "create table works( "
    			+ "prof_id integer(10) not null , "
    			+ "dept_id integer(10) not null , "
    			+ "duration integer(5), "
    			+ "foreign key (dept_id) "
    			+ "references departments(dept_id) on delete cascade,"
    			+ "foreign key (prof_id) "
    			+ "references professors(prof_id) on delete cascade);";
    	st.executeUpdate(query);
    }
    
    void createTableShift() throws SQLException
    {
    	String query = "create table shift( "
    			+ "prof_id integer(10) not null , "
    			+ "shift integer(5) not null , "
    			+ "working_hours integer(5) not null, "
    			+ "primary key(prof_id,shift,working_hours));";
    	st.executeUpdate(query);
    }
    
    void query1() throws SQLException 
    {
    	System.out.println("1. Display all customer details with city pune and mumbai and customer first name starting with\r\n" + 
    			"'p' or 'h'.");
        String query = "select * from professors where (city = 'Pune' or city = 'Mumbai') and (prof_fname like 'a%' or prof_fname like 'd%');";
        rs = st.executeQuery(query);
        display("Professors");
    }
    
    void query2() throws SQLException
    {
    	System.out.println("2. List the number of different customer cities.");
    	String query = "select count(distinct city) as total from professors;";
    	rs = st.executeQuery(query);
    	rs.next();
    	int cnt = rs.getInt("total");
    	System.out.println(cnt);
    }
    
    void query3() throws SQLException
    {
    	System.out.println("3. Give 5% increase in salary of the professors with date of joining 1-1-2015.");
    	String query = "update professors set salary = (salary * 1.05) where doj = '2015-01-01';"; 
    	rs = st.executeQuery(query);
    }
    
    void query4() throws SQLException
    {
    	System.out.println("4. Delete professor details living in pune.");
    	String query = "delete from professors where city = 'Pune';"; 
    	rs = st.executeQuery(query);
    	display("Professors");
    }
    
    void query5() throws SQLException
    {
    	System.out.println("5. Find the names of professors belonging to pune or mumbai.");
    	String query = "select prof_fname, prof_lname from professors where city in ('Pune','Mumbai');"; 
    	rs = st.executeQuery(query);
    	while(rs.next())
    	{
    		System.out.println(rs.getString(1) + " " + rs.getString(2));
    	}
    }
    
    void query6() throws SQLException
    {
    	System.out.println("6. Find the professors who joined on date 1-1-2015 as well as in 1-1-2016.");
    	String query = "select * from professors where doj in ('2015-01-01','2016-01-01');"; 
    	rs = st.executeQuery(query);
    	display("Professors");
    }
    
    //Not done
    /*void query7() throws SQLException
    {
    	System.out.println("7. Find the professor having maximum salary and names of professors having salary between 10,000 and 20,000.");
    	String query = "select * from professors where doj in ('2015-01-01','2016-01-01');"; 
    	rs = st.executeQuery(query);
    	display("Professors");
    }*/
    
    void query8() throws SQLException
    {
    	System.out.println("8. Display all professors name with salary and date of joining with decreasing order of salary.");
    	String query = "select prof_fname, prof_lname, salary, doj from professors order by salary desc;"; 
    	rs = st.executeQuery(query);
    	while(rs.next())
    	{
    		System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4));
    	}
    }
    
    void query9() throws SQLException
    {
    	System.out.println("9. Display professors name, date of joining and dept_id with salary 30000, 40000 and 50000.");
    	String query = "select prof_fname, prof_lname, doj, dept_id from professors where salary in (30000, 40000, 50000);"; 
    	rs = st.executeQuery(query);
    	while(rs.next())
    	{
    		System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
    	}
    }
    
    void displayAll(String tableName) throws SQLException
    {
    	String query = "select * from " + tableName +";" ;
        rs = st.executeQuery(query);
        display(tableName);
    }
    
    void createView() throws SQLException
    {
    	String query = "create view prof_details as select prof_id, prof_fname, prof_lname from professors;";
    	st.executeUpdate(query);
    	query = "select * from professors;" ;
        rs = st.executeQuery(query);
        while(rs.next())
        {
        	System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
        }
    }
    
    void createIndex() throws SQLException
    {
    	String query = "create index idx on professors(prof_id, prof_fname, prof_lname);";
    	st.executeUpdate(query);
    	query = "select * from professors;" ;
        rs = st.executeQuery(query);
    	displayAll("Professors");
    }
    
    void closeConnection()
    {
    	try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args)
    {    	
    	try {
    		MyClass obj = new MyClass();
    		Scanner sc = new Scanner(System.in);
        	while(true)
        	{
        		System.out.println("****JDBC****");
        		System.out.println("1. Enter data");
        		System.out.println("2. Display tables");
        		System.out.println("3. Show view");
        		System.out.println("4. Show index");
        		System.out.println("5. Exit");
        		int choice = sc.nextInt();
        		switch(choice)
        		{
        			case 1:
        				sc.nextLine();
        			 	String str = sc.nextLine();
        				obj.add(str);
        				break;
        			case 2:
        				obj.displayAll("Departments");
        				obj.displayAll("Professors");
        				obj.displayAll("Works");
        				obj.displayAll("Shift");
        				break;
        			case 3:
        				obj.createView();
        				break;
        			case 4:
        				obj.createIndex();
        				break;
        			case 5:
        				obj.closeConnection();
        				return;
        		}
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
