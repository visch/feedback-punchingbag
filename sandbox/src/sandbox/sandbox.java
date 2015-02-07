package sandbox;

import java.util.ArrayList;

public class sandbox {
	
	static public void(String args[])
	{
		ArrayList<String> a = new ArrayList<String>();
		
		a.add("one");
		a.add("two");
		a.add("three");
		
		prepend(a, "Top");
		
		for (int i = 0; i < a.size(); i++) {
			System.out.println(i+"="+a.get(i));
		}
	}
}
