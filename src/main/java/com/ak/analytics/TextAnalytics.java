package com.ak.analytics;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.ai.textanalytics.models.*;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.TextAnalyticsClient;

public class TextAnalytics {

	private static String KEY = "afe846e8d29241feb26f60ac98aee293";
	private static String ENDPOINT = "https://akcontentanalysis.cognitiveservices.azure.com/";
	public TextAnalyticsClient client;
	public String text;
	
	public TextAnalytics(String text){
		super();
		this.text = text;
		this.client = authenticateClient(KEY, ENDPOINT);
	}

	public static void main(String[] args) {
		// You will create these methods later in the quickstart.
		TextAnalytics analytics = new TextAnalytics("This is test");
		analytics.sentimentAnalysis();
		analytics.detectLanguage();
		analytics.recognizeEntities();
		analytics.recognizeLinkedEntities();
		analytics.extractKeyPhrases();
	}

	private TextAnalyticsClient authenticateClient(String key, String endpoint) {
		return new TextAnalyticsClientBuilder().credential(new AzureKeyCredential(key)).endpoint(endpoint)
				.buildClient();
	}

	public String sentimentAnalysis() {
		String result = null;

		DocumentSentiment documentSentiment = client.analyzeSentiment(text);
		result = String.format("Recognized document sentiment: %s, positive score: %s, neutral score: %s, negative score: %s.%n",
				documentSentiment.getSentiment(), documentSentiment.getConfidenceScores().getPositive(),
				documentSentiment.getConfidenceScores().getNeutral(),
				documentSentiment.getConfidenceScores().getNegative());

//		for (SentenceSentiment sentenceSentiment : documentSentiment.getSentences()) {
//			System.out.printf(
//					"Recognized sentence sentiment: %s, positive score: %s, neutral score: %s, negative score: %s.%n",
//					sentenceSentiment.getSentiment(), sentenceSentiment.getConfidenceScores().getPositive(),
//					sentenceSentiment.getConfidenceScores().getNeutral(),
//					sentenceSentiment.getConfidenceScores().getNegative());
//		}
		return result;
	}

	public String detectLanguage() {
		String result = null;
		DetectedLanguage detectedLanguage = client.detectLanguage(text);
		result = String.format("Detected primary language: %s, ISO 6391 name: %s, score: %.2f.%n",
				detectedLanguage.getName(), detectedLanguage.getIso6391Name(), detectedLanguage.getConfidenceScore());
		return result;
	}

	public String recognizeEntities() {
		String result = null;
		
		for (CategorizedEntity entity : client.recognizeEntities(text)) {
			result += String.format("Recognized entity: %s, entity category: %s, entity sub-category: %s, score: %s.%n%n ",
					entity.getText(), entity.getCategory(), entity.getSubcategory(), entity.getConfidenceScore());
		}
		return result;
	}

	public String recognizeLinkedEntities() {

		String result = String.format("Linked Entities:%n");
		for (LinkedEntity linkedEntity : client.recognizeLinkedEntities(text)) {
			result += String.format("Name: %s, ID: %s, URL: %s, Data Source: %s.%n ", linkedEntity.getName(),
					linkedEntity.getDataSourceEntityId(), linkedEntity.getUrl(), linkedEntity.getDataSource());
			result += String.format("Matches:%n");
			for (LinkedEntityMatch linkedEntityMatch : linkedEntity.getMatches()) {
				result += String.format("Text: %s, Score: %.2f%n%n", linkedEntityMatch.getText(),
						linkedEntityMatch.getConfidenceScore());
			}
		}
		return result;
	}

	public String extractKeyPhrases() {
		String result = String.format("Recognized phrases: %n");
		for (String keyPhrase : client.extractKeyPhrases(text)) {
			result += String.format("%s%n", keyPhrase);
		}
		return result;
	}

}
