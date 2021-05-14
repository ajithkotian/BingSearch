package com.ak.search;

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

import com.ak.helper.SearchResults;

import static com.ak.helper.SearchUtil.*;

/**
* Web search using Azure Bing search service
*/
public class BingWebSearch {

 static String subscriptionKey = "";
 static String endpoint = "https://api.bing.microsoft.com" + "/v7.0/search";

 static String searchTerm = "Elon musk on Tesla not accepting bitcoin";

 public static void main(String[] args) {
     try {
         System.out.println("Searching the Web for: " + searchTerm);

         SearchResults result = SearchWeb(searchTerm);

         System.out.println("\nRelevant HTTP Headers:\n");
         for (String header : result.relevantHeaders.keySet())
             System.out.println(header + ": " + result.relevantHeaders.get(header));

         System.out.println("\nJSON Response:\n");
         System.out.println(prettyPrint(result.jsonResponse));
     } catch (Exception e) {
         e.printStackTrace(System.out);
         System.exit(1);
     }
 }

 public static SearchResults SearchWeb (String searchQuery) throws Exception {

     URL url = new URL(endpoint + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8"));
     HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
     connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);


     InputStream stream = connection.getInputStream();
     Scanner scanner = new Scanner(stream);
     String response = scanner.useDelimiter("\\A").next();


     SearchResults results = new SearchResults(new HashMap<String, String>(), response);


     Map<String, List<String>> headers = connection.getHeaderFields();
     for (String header : headers.keySet()) {
         if (header == null) continue;      // may have null key
         if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
             results.relevantHeaders.put(header, headers.get(header).get(0));
         }
     }
     stream.close();
     scanner.close();

     return results;
 }


}
