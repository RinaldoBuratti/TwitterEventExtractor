package it.uniroma3.sii.persistence;

public class TweetEntity {

	private int tweetId;
	private String userName;
	private String userLocation;
	private String text;

	public TweetEntity(int tweetId, String userName, String userLocation, String text) {
		this.tweetId = tweetId;
		this.userName = userName;
		this.userLocation = userLocation;
		this.text = text;
	}

	public TweetEntity() {
	}

	public int getId() {
		return this.tweetId;
	}

	public void setId(int id) {
		this.tweetId = id;
	}

	public String getUsername() {
		return this.userName;
	}

	public void setUsername(String name) {
		this.userName = name;
	}

	public String getUserLocation() {
		return this.userLocation;
	}

	public void setUserLocation(String location) {
		this.userLocation = location;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String txt) {
		this.text = txt;
	}
}