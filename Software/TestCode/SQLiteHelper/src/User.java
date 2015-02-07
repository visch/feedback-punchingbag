import java.util.ArrayList;
import java.util.List;


public class User {
	private int id=-1, music_id=-1;
	private String name;
	private String age;
	private String weight;
	private String armSpan;
	private String height;
	private List<String> music=new ArrayList<String>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMusic_id() {
		return music_id;
	}
	public void setMusic_id(int music_id) {
		this.music_id = music_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getArmSpan() {
		return armSpan;
	}
	public void setArmSpan(String armSpan) {
		this.armSpan = armSpan;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
	public List<String> getMusic() {
		return music;
	}
	public void addMusic(String music) {
		this.music.add(music); //Add some music
	}
	
	public String generateUserSQL()
	{
		String UserSql;
//		String sqlInsert = "INSERT INTO Users ("Name\",\"Age\",\"Weight\",\"Arm_Span\",\"Height\",\"Music_id\") VALUES (NULL,'Test1','22','123','132','123','2')";
		
		UserSql = "INSERT INTO USERS(Name,Age,Weight,Arm_Span,Height) VALUES ("+
		"\""+this.getName()+"\","+
		"\""+this.getAge()+"\","+
		"\""+this.getWeight()+"\","+
		"\""+this.getArmSpan()+"\","+
		"\""+this.getHeight()+"\")";
		return UserSql;	
	}

	public List<String> generateMusicSQL() throws Exception
	{
		List<String> musicSQList=new ArrayList<>();
		String musicSQL;
		if (this.getId() != -1)
		{
			while (music.size() != 0)
			{
				musicSQL="";
				musicSQL="INSERT INTO Music (User_id,Path) VALUES ("+
						this.getId()+",";
				musicSQL += "\""+music.get(0)+"\"";
				music.remove(0);
				musicSQL += ")";
				musicSQList.add(musicSQL);
				
				
			}
		}
		else { throw new Exception("Need User ID to be grabbed from DB"); }

		return musicSQList;
	}

}
