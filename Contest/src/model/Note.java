package model;

public class Note {
	private int notenumber;
	private int myid;
	private int yourid;
	private String notetitle;
	private String content;
	private String notetime;
	
	public Note() {
		super();
	}
	public Note(int notenumber, int myid, int yourid, String notetitle, String content, String notetime) {
		this.notenumber = notenumber;
		this.myid = myid;
		this.yourid = yourid;
		this.notetitle = notetitle;
		this.content = content;
		this.notetime = notetime;
	}
	
	public int getNotenumber() {
		return notenumber;
	}
	public void setNotenumber(int notenumber) {
		this.notenumber = notenumber;
	}
	public String getNotetitle() {
		return notetitle;
	}
	public void setNotetitle(String notetitle) {
		this.notetitle = notetitle;
	}
	public int getMyid() {
		return myid;
	}
	public void setMyid(int myid) {
		this.myid = myid;
	}
	public int getYourid() {
		return yourid;
	}
	public void setYourid(int yourid) {
		this.yourid = yourid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNotetime() {
		return notetime;
	}
}
