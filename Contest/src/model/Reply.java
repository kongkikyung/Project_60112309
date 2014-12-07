package model;


public class Reply {
	private int id;
	private int writingNumber;
	private String userid;
	private String reply;
	private String replyTime;
	
	public Reply() {
		super();
	}
	
	public Reply(int id, int writingNumber, String userid, String reply, String replyTime) {
		this.id = id;
		this.writingNumber = writingNumber;
		this.userid = userid;
		this.reply = reply;
		this.replyTime = replyTime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWritingNumber() {
		return writingNumber;
	}
	public void setWritingNumber(int writingNumber) {
		this.writingNumber = writingNumber;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
}
