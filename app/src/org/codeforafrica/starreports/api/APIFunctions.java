package org.codeforafrica.starreports.api;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.codeforafrica.starreports.DefaultsActivity;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class APIFunctions {
	
	private JSONParser jsonParser;
	private static String loginURL = "login.php";
	private static String registerURL = DefaultsActivity.site_url + "/api/users/register/";
	private static String updateURL = "update.php";
	private static String reportURL = "article.php";
	private static String objectURL = "attachment.php";
	private static String postsURL = "http://192.168.1.41/storymaker/api/get_recent_posts/";
	private static String api_base_url = "http://starreports.codeforafrica.net/sm-api/";
	private static String api_key = "2a80f2f6094f4e3d2e7b01ba21951f3720060454";
	private static String assignmentsURL = "http://192.168.1.43/storymaker/?json=get_category_posts&id=2";
	// constructor
	public APIFunctions(){
		jsonParser = new JSONParser();
	}
	
	public JSONObject loginUser(String firstname, String lastname, String username, String email, String location){		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("firstname", firstname));
		params.add(new BasicNameValuePair("lastname", lastname));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("api_key", api_key));
		params.add(new BasicNameValuePair("tag", "facebook"));

		JSONObject json = jsonParser.getJSONFromUrl(api_base_url + loginURL, params);
		
		// return json
		return json;
	}

	public JSONObject newReport(String token, String user_id, String title,
			String lat, String lon, String date, String description) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("token", token));
		params.add(new BasicNameValuePair("user_id", user_id));
		params.add(new BasicNameValuePair("title", title));
		params.add(new BasicNameValuePair("location", lon +", "+ lat));
		params.add(new BasicNameValuePair("date", date));
		params.add(new BasicNameValuePair("description", description));
		params.add(new BasicNameValuePair("api_key", api_key));

		JSONObject json = jsonParser.getJSONFromUrl(api_base_url + reportURL, params);
		
		// return json
		return json;
	}

	public JSONObject newObject(String token, String user_id, String ptitle,
			String preportid, String ptype, String optype, String pid,
			String path) {
		//add media
		MultipartEntity mpEntity = new MultipartEntity();
		try{				
			ContentBody content = new FileBody(new File(path), ptype);
			mpEntity.addPart("file", content);
			mpEntity.addPart("token", new StringBody(token));
			mpEntity.addPart("user_id", new StringBody(user_id));
			mpEntity.addPart("title", new StringBody(ptitle));
			mpEntity.addPart("article", new StringBody(preportid));
			mpEntity.addPart("object_type", new StringBody(optype));
			mpEntity.addPart("api_key", new StringBody( "api_key"));
			mpEntity.addPart("content", new StringBody("(empty)"));
		} catch (IOException e) {
            Log.e("mpEntity Error", e.getMessage(), e);
        }
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl_Object(api_base_url + objectURL, mpEntity);
		
		// return json
		return json;
	}

	public JSONObject loginUserClassic(String username, String password) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("tag", "classic"));

		JSONObject json = jsonParser.getJSONFromUrl(api_base_url + loginURL, params);
		
		// return json
		return json;
	}
	public JSONObject getPosts() {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		JSONObject json = jsonParser.getJSONFromUrl(postsURL, params);
		
		// return json
		return json;
	}
	public JSONObject edit_profile(String token, String user_id, String password, String email, String first_name, String last_name, String location, String phone_number, Context ctx){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_id", user_id));
		params.add(new BasicNameValuePair("token", token));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("first_name", first_name));
		params.add(new BasicNameValuePair("last_name", last_name));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("phone_number", phone_number));

		JSONObject json = jsonParser.getJSONFromUrl(api_base_url + updateURL, params);
		// return json
		return json;
	}
	
	public JSONObject registerUser(String username, String password, String email, String first_name, String last_name, String location, String phone_number){
		Log.d("passed", "passed" + email + username + password);
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("firstname", first_name));
		params.add(new BasicNameValuePair("lastname", last_name));
		params.add(new BasicNameValuePair("location", location));
		params.add(new BasicNameValuePair("phone_number", phone_number));
		params.add(new BasicNameValuePair("tag", "register"));

		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	
}