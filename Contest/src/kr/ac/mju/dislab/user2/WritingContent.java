package kr.ac.mju.dislab.user2;



public class WritingContent implements java.io.Serializable {
	private static final long serialVersionUID = 2193897931951340673L;

	public void setTime(String time) {
		this.time = time;
	}

	private int id;
	private String title;
	private String userName;
	private String text;
	private String time;

	// No-arg constructor 가 있어야 한다.
	public WritingContent() {
	}

	public WritingContent(int id, String title, String userName, String text, String time) {
		super();
		this.id = id;
		this.title = title;
		this.userName = userName;
		this.text = text;
		this.time = time;
	}
	// getter & setter 가 있어야 한다. (Eclipse 에서 자동 생성 가능)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getTime() {
		return time;
	}
}
