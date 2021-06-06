
public class Departments 
{
	int dept_id;
	String dept_name;
	public Departments(int id, String name) {
		// TODO Auto-generated constructor stub
		dept_id = id;
		dept_name = name;
	}
	void display()
	{
		System.out.println(dept_id + " " + dept_name);
	}
}
