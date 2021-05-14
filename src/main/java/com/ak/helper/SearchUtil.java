package com.ak.helper;

import java.util.Calendar;

import com.ak.TO.NewsTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SearchUtil {

	public static String prettyPrint(String json_text) {
		JsonObject json = new JsonParser().parse(json_text).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

	public static NewsTO transformToNewsTO(String json_text) {
		JsonObject json = new JsonParser().parse(json_text).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject jsonObj = json.getAsJsonArray("value").get(0).getAsJsonObject();

		return new NewsTO(jsonObj.get("name").getAsString(), jsonObj.get("description").getAsString(),
				jsonObj.getAsJsonArray("provider").get(0).getAsJsonObject().get("name").getAsString(),
				jsonObj.get("url").getAsString(), Calendar.getInstance(), gson.toJson(json));
	}

}
