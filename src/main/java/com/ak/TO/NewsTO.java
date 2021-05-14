package com.ak.TO;
import java.util.Calendar;

public class NewsTO {
	private String heading;
	private String description;
	private String newsProvider;
	private String url;
	private Calendar datePublished;
	private String prettyPrint;
	
	public NewsTO() {
		this.heading = null;
		this.description = null;
		this.newsProvider = null;
		this.url = null;
		this.datePublished = null;
		this.prettyPrint = null;
	}
	
	public NewsTO(String heading, String description, String newsProvider, String url, Calendar datePublished, String prettyPrint) {
		super();
		this.heading = heading;
		this.description = description;
		this.newsProvider = newsProvider;
		this.url = url;
		this.datePublished = datePublished;
		this.prettyPrint = prettyPrint;
	}
	
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNewsProvider() {
		return newsProvider;
	}
	public void setNewsProvider(String newsProvider) {
		this.newsProvider = newsProvider;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Calendar getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(Calendar datePublished) {
		this.datePublished = datePublished;
	}
	
	
	
	public String getPrettyPrint() {
		return prettyPrint;
	}

	public void setPrettyPrint(String prettyPrint) {
		this.prettyPrint = prettyPrint;
	}

	@Override
	public String toString() {
		return "NewsTO [ \n\n heading=" + heading + " \n\n description=" + description + " \n\n newsProvider=" + newsProvider
				+ " \n\n url=" + url + " \n\n datePublished=" + datePublished + " \n\n prettyPrint=" + prettyPrint + "]";
	}

}
