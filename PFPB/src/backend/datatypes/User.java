package backend.datatypes;
import java.util.ArrayList;
import java.util.List;

//TODO: needs a goal percentage for force and and a goal percentage for frequency to be stored 
public class User {
	private double minFreqGoalDefaultPercentage=.99; //Amount the min Freq goal is set to by default
	private double minForceGoalDefaultPercentage=.99; //Amount the min force goal is set to by deafult
	private int id=-1; //Set these to -1 to state they haven't been set
	private String name;
	private String age;
	private String weight;
	private String armSpan;
	private String height;
	private Double averageForceFromLastSessionRounds;
	private Double averageFrequencyFromLastSessionRounds;
	private Double minGoalForce;
	private Double maxGoalForce;
	private Double minGoalFrequency;
	private Double maxGoalFrequency;
	private String userThoughtLevel;
	private List<String> music=new ArrayList<String>();

	public User() {
		this.id=-1;
		this.name = "";
		this.age = "";
		this.weight = "";
		this.armSpan = "";
		this.height = "";
		this.userThoughtLevel = "";
	}
	public User(String Unme, String uAge, String uWeight, String uArSpan, String uHeight, String uLvl) {
		this.id = -1;
		this.name = Unme;
		this.age = uAge;
		this.weight = uWeight;
		this.armSpan = uArSpan;
		this.height = uHeight;
		this.userThoughtLevel = uLvl;
		this.minGoalForce = (double) 0;
		this.maxGoalForce = (double) 99999;
		this.minGoalFrequency = (double) 0;
		this.maxGoalFrequency = (double) 99999;
		music.add(" ");
		music.add(" ");
		music.add(" ");
		music.add(" ");
		music.add(" ");
		music.add(" ");
		music.add(" ");
		music.add(" ");
		music.add(" ");
		music.add(" ");
	}
	public int getId() {
		return id;
	}
	
	//Dont ever set this by yourself, this is used to know if the user needs to be updated or added 
	public void setId(int id) {
		this.id = id;
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
	
	public void clearMusic() {
		this.music.clear();
	}
	
	public double getAverageForceFromLastSessionRounds() {
		return averageForceFromLastSessionRounds;
	}

	public void setAverageForceFromLastSessionRounds(
		double averageForceFromLastSessionRounds) {
		this.averageForceFromLastSessionRounds = averageForceFromLastSessionRounds;
	}

	public double getAverageFrequencyFromLastSessionRounds() {
		return averageFrequencyFromLastSessionRounds;
	}

	public void setAverageFrequencyFromLastSessionRounds(
			double averageFrequencyFromLastSessionRounds) {
		this.averageFrequencyFromLastSessionRounds = averageFrequencyFromLastSessionRounds;
	}
	
	public String getUserThoughtLevel()
	{
		return this.userThoughtLevel;
	}
	
	
	public void setUserThoughtLevel(String level) {
		this.userThoughtLevel = level;
	}

	
	
//	//Takes the last generated ID for Music ad populates our list
//	public updateMusicIds(int lastID)
//	{
//		
//	}
	

	public double getMinGoalForce() {
		return minGoalForce;
	}
	public void setMinGoalForce(double minGoalForce) {
		this.minGoalForce = minGoalForce;
	}
	public void setMinGoalForce(String minGoalForce) {
		this.minGoalForce = Double.parseDouble(minGoalForce);
	}
	
	public double getMaxGoalForce() {
		return maxGoalForce;
	}
	public void setMaxGoalForce(double maxGoalForce) {
		this.maxGoalForce = maxGoalForce;
	}
	public void setMaxGoalForce(String maxGoalForce) {
		this.maxGoalForce = Double.parseDouble(maxGoalForce);
	}
	public double getMinGoalFrequency() {
		return minGoalFrequency;
	}
	public void setMinGoalFrequency(double minGoalFrequency) {
		this.minGoalFrequency = minGoalFrequency;
	}
	public void setMinGoalFrequency(String minGoalFrequency) {
		this.minGoalFrequency = Double.parseDouble(minGoalFrequency);
	}
	public double getMaxGoalFrequency() {
		return maxGoalFrequency;
	}
	public void setMaxGoalFrequency(double maxGoalFrequency) {
		this.maxGoalFrequency = maxGoalFrequency;
	}
	public void setMaxGoalFrequency(String maxGoalFrequency) {
		this.maxGoalFrequency = Double.parseDouble(maxGoalFrequency);
	}
	
	public void generateDefaultGoals()
	{
		setMinGoalFrequency(getAverageFrequencyFromLastSessionRounds()*minFreqGoalDefaultPercentage);
		setMinGoalForce(getAverageForceFromLastSessionRounds()*minForceGoalDefaultPercentage);
		setMaxGoalForce(9999999999.9);
		setMaxGoalFrequency(9999999999.9);
	}
	
	public String generateNewUserSQL()
	{
		String UserSql;
		UserSql = "INSERT INTO USERS(Name,Age,Weight,Arm_Span,Height,Music_Paths, userThought, MinGoalForce, MaxGoalForce, MinGoalFrequency, MaxGoalFrequency) VALUES ("+
		"\""+this.getName()+"\","+
		"\""+this.getAge()+"\","+
		"\""+this.getWeight()+"\","+
		"\""+this.getArmSpan()+"\","+
		"\""+this.getHeight()+"\","+
		"\""+this.getMusicCSV()+"\","+
		"\""+this.getUserThoughtLevel()+"\","+
		"\""+this.getMinGoalForce()+"\","+
		"\""+this.getMaxGoalForce()+"\","+
		"\""+this.getMinGoalFrequency()+"\","+
		"\""+this.getMaxGoalFrequency()+" \")";
		return UserSql;	
	}
	
	public String generateUserUpdateSQL()
	{
		String updateSQL;
		updateSQL="UPDATE \"Users\" SET "+
		"'Name'='"+this.getName()+"',"+
		 "Age='"+this.getAge()+"',"+
		 "Weight='"+this.getWeight()+"',"+
		 "Arm_Span='"+this.getArmSpan()+"',"+
		 "Height='"+this.getHeight()+"'," +
		 "Music_Paths='"+this.getMusicCSV()+"'," +
		 "userThought='"+this.getUserThoughtLevel()+"'," +
		 "MinGoalForce='"+this.getMinGoalForce()+"'," +
		 "MaxGoalForce='"+this.getMaxGoalForce()+"'," +
		 "MinGoalFrequency='"+this.getMinGoalFrequency()+"'," +
		 "MaxGoalFrequency='"+this.getMaxGoalFrequency()+"' "+
		 "WHERE ROWID = "+this.getId();
		return updateSQL;
	}
	
	
	private String getMusicCSV()
	{
		String musicCSV="";
		for (String musicPath : music) {
			musicCSV+=musicPath+",";
		}
		musicCSV=musicCSV.substring(0,musicCSV.length()-1);
		return musicCSV; 
	}

}
