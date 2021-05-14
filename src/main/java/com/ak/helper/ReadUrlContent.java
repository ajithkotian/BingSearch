package com.ak.helper;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ReadUrlContent {

	public static void main(String[] args) {
		String url = "https://www.theguardian.com/technology/2021/may/10/melinda-bill-gates-divorce-jeffrey-epstein-meetings";
		System.out.println(readUrlBody(url));

	}

	public static String readUrlBody(String url) {
		try {

			Document doc = Jsoup.connect(url).get();
//			System.out.println(doc.title());
			
			//TODO: Do a better job at splitting 5100 characters 
			//Azure text analytics can only process 5120 characters
			String fullContent = doc.body().text();
			String conciseContent = fullContent.substring(0, Math.min(fullContent.length(), 5100));
			
			return conciseContent;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		return null;
	}
}
