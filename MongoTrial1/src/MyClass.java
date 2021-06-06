import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.*;

public class MyClass {
	
	public static void main(String args[]) {
		//Establishing a  connection
		MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("assgnb6");
		MongoCollection coll = db.getCollection("student");
		FindIterable<Document> docs = coll.find();
		System.out.println("\nBefore insert");
		ArrayList<Document> arr = new ArrayList<Document>();
		for(Document doc : docs) 
		{
			arr.add(doc);
			System.out.println(doc);
		}
		
		
		//Encoding
		JSONObject g_data = new JSONObject();
		g_data.put("name", "g");
		g_data.put("age", 17);
		g_data.put("div", "g");
		JSONObject h_data = new JSONObject();
		JSONObject h_name = new JSONObject();
		h_name.put("h_first", "hh");
		h_name.put("h_last", "hhhh");
		h_data.put("name", h_name);
		h_data.put("age", 18);
		h_data.put("div", "h");
		JSONArray newData = new JSONArray();
		newData.put(g_data);
		newData.put(h_data);
		for(int i=0; i<newData.length(); i++)
		{
			Document doc = Document.parse(newData.get(i).toString());
			coll.insertOne(doc);
		}
		System.out.println("\nAfter insert");
		for(Document docss : docs) 
		{
			System.out.println(docss);
		}
		
		
		//Decoding
		JSONObject obj2 = new JSONObject(arr.get(0));
		Student s1 = new Student();
		s1.name = (String) obj2.get("name");
		s1.age = (Double) obj2.get("age");
		s1.div = (String) obj2.get("div");
		s1.display();
	}
	
}
