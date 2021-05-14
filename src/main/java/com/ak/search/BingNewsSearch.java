package com.ak.search;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import static com.ak.helper.SearchUtil.*;

import com.ak.TO.NewsTO;
import com.ak.analytics.TextAnalytics;
import com.ak.helper.ReadUrlContent;
import com.ak.helper.SearchResults;


public class BingNewsSearch {
	 static String subscriptionKey = "";
	 static String endpoint = "https://api.bing.microsoft.com" + "/v7.0/news/search";

	static String searchTerm = "Ruja Ignatova";

    public static void main(String[] args) {
        try {
            System.out.println("Searching the Web for: " + searchTerm);

            SearchResults result = SearchNews(searchTerm);

            System.out.println("\nRelevant HTTP Headers:\n");
            for (String header : result.relevantHeaders.keySet())
                System.out.println(header + ": " + result.relevantHeaders.get(header));
            printSeperator();
            
            NewsTO newsTO = transformToNewsTO(result.jsonResponse);
            
            System.out.println("\nNews Search Response:\n");
            System.out.println(newsTO);
            printSeperator();
            //Extract html body of url page 
            String contentBodyText = ReadUrlContent.readUrlBody(newsTO.getUrl());
            System.out.println("News Article content : \n" + contentBodyText);
            
            
            //Run text analytics on the content 
            TextAnalytics textAnalytics = new TextAnalytics(contentBodyText);
            printSeperator();
            System.out.println("Sentiment of the article : " + textAnalytics.sentimentAnalysis());
            printSeperator();
            System.out.println("Entities in the article : " + textAnalytics.recognizeEntities());
            printSeperator();
            System.out.println("Linked Entities in the article : " + textAnalytics.recognizeLinkedEntities());
            printSeperator();
            System.out.println("Key Phrases in the article : " + textAnalytics.extractKeyPhrases());
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

	private static void printSeperator() {
		System.out.println("**************************************************************************************************************************");
	}

    public static SearchResults SearchNews (String searchQuery) throws Exception {
        // Construct URL of search request (endpoint + query string)

        String urlQueryString = "?q=" +  URLEncoder.encode(searchQuery, "UTF-8") + "&sortBy=date&count=1";
		URL url = new URL(endpoint + urlQueryString);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

        // Receive JSON body
        InputStream stream = connection.getInputStream();
        Scanner scanner = new Scanner(stream);
        String response  = scanner.useDelimiter("\\A").next();

        // Construct result object for return
        SearchResults results = new SearchResults(new HashMap<String, String>(), response);

        // Extract Bing-related HTTP headers
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (String header : headers.keySet()) {
            if (header == null) continue;      // may have null key
            if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
                results.relevantHeaders.put(header, headers.get(header).get(0));
            }
        }

        scanner.close();
        stream.close();

        return results;
    }

}

