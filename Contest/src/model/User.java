package model;

public class User implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] majorNames = {"웹개발자", "개발자", "웹디자이너", "그래픽디자이너"};
	private static final String[][] genders = {{"M", "남성"}, {"F", "여성"}};

	private int id;
	private String userid;
	private String pwd;
	private String name;
	private String photo;
	private String gender;
	private String major;
	private String phone;
	private String email;
		
	// No-arg constructor 가 있어야 한다.
	public User() {
	}
	public User(int id, String userid, String name, String photo, String pwd, String gender, String major,
			String phone, String email) {
		super();
		this.id = id;
		this.userid = userid;
		this.name = name;
		this.photo = photo;
		this.pwd = pwd;
		this.gender = gender;
		this.major = major;
		this.phone = phone;
		this.email = email;
	}

	// getter & setter 가 있어야 한다. (Eclipse 에서 자동 생성 가능)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getMajorNames() {
		return majorNames;
	}

	public String[][] getGenders() {
		return genders;
	}
	
	public String checkMajor(String majorName) {
		return (majorName.equals(major)) ? "selected" : "";
	}
		
	public String checkGender(String genderName) {
		return (genderName.equals(gender)) ? "checked" : "";
	}
	
	public String getGenderStr() {
		if (gender.equals("M")) {
			return "남성";
		} else {
			return "여성";
		}
	}
}
