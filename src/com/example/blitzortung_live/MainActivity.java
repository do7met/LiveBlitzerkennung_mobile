package com.example.blitzortung_live;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {
	  private TextView latituteField;
	  private TextView longitudeField;
	  private TextView distanceField;
	  private TextView bearingField;
	  private TextView directionField;
	  
	  private LocationManager locationManager;
	  private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    latituteField = (TextView) findViewById(R.id.editText1);
	    longitudeField = (TextView) findViewById(R.id.editText2);
	    distanceField = (TextView) findViewById(R.id.editText3);
	    bearingField = (TextView) findViewById(R.id.editText3);
	    directionField = (TextView) findViewById(R.id.editText3);

	    
	    

	    // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    
	  }
	
	 /* Request updates at startup */
	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		 // double lat = (location.getLatitude());
		 //   double lng = (location.getLongitude());
		 //   latituteField.setText(String.valueOf(lat));
		 //   longitudeField.setText(String.valueOf(lng));
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Disabled provider " + provider,
		        Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		  Toast.makeText(this, "Enabled new provider " + provider,
			        Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void theButtonClicked(View view) {
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		double lat = 0.0;
		double lng = 0.0;
		
		double lat_now;
		double lon_now = 0.0;
		
		double min_dist = 99999.9;
		double min_bearing = 0.0;
		
		
		 // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
		
		// Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	     // onLocationChanged(location);
	       lat = (location.getLatitude());
		     lng = (location.getLongitude());
		    
		    double distance = GetGeoData.Haversine(lat, lng, lat, lng);
		    
		 
	    } else {
	      latituteField.setText("Location not available");
	      longitudeField.setText("Location not available");
	    }
		
		
		
		
		 

		   
		    JSONArray jsonArray = null;
		    JSONObject jsonObj = null;
		    try {
				jsonObj = new JSONObject(getText("http://www.lightningmaps.org/live/?r=1"));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		    String jss = "";
		    
		    try {
				jss =  ReadHttpResponse("http://www.lightningmaps.org/live/?r=1");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		    
		  
		 
		   //String lines[] = jss.split("\\r?\\n");
		   
		 

ArrayList<String> list = new ArrayList<String>();     
 
if (jsonArray != null) { 
   int len = jsonArray.length();
   for (int i=0;i<len;i++){ 
    try {
		list.add(jsonArray.get(i).toString());
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   } 
} 




	 

	String test = null;
	try {
		test = jsonObj.getString("d");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	double val = 0.0;
	double lat_read = 0.0;
	double lon_read = 0.0;
	

	
	JSONArray array = null;
	try {
		array = new JSONArray(test);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	JSONArray array_read = null;
	
	for ( int i = 0; i < array.length(); i++ ) {
		

		
	
	try {
		array_read = array.getJSONArray(i);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	try {
		lat_read = array_read.getDouble(1);
		lon_read = array_read.getDouble(2);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		 
		

	
	
	if ( GetGeoData.Haversine(lat_read, lon_read, lat, lng) <= min_dist ) {
		
		min_dist = GetGeoData.Haversine(lat_read, lon_read, lat, lng);
		// Attention: For Bearing different order (we want the bearing from _us_ _to_ the lightning event)
		min_bearing = GetGeoData.Bearing(lat, lng, lat_read, lon_read);   
		
	}
	
	}
	
			//	Toast.makeText(this, String.valueOf(min_dist) ,
			//		        Toast.LENGTH_SHORT).show();
		
		
		   
		   
		   //String[][] values_str = (String[][]) values;
		   
	//	for (int i=0; i < values_str.length; i++) {
			
		
			
		//		lat_now = Double.valueOf(values_str[i][2]);
			
			
			
			   
		//	   if (GetGeoData.Haversine(lat_now, lon_now, lat, lng) <= min_dist) {
				   
		//		   min_dist = GetGeoData.Haversine(lat_now, lon_now, lat, lng);
			//	   min_bearing = GetGeoData.Bearing(lat_now, lon_now, lat, lng);
				   
			//   }
		   
	
	setContentView(R.layout.activity_main);
    latituteField = (TextView) findViewById(R.id.editText1);
    longitudeField = (TextView) findViewById(R.id.editText2);
    distanceField = (TextView) findViewById(R.id.editText3);
    bearingField = (TextView) findViewById(R.id.editText4);
    directionField = (TextView) findViewById(R.id.editText5);
	
    
				   latituteField.setText(String.valueOf(lat));
				    longitudeField.setText(String.valueOf(lng));
		   
		   distanceField.setText(String.valueOf(Math.round(min_dist))+ " km");
		    bearingField.setText(String.valueOf(Math.round(min_bearing))+" °");
		    directionField.setText(getDirection(min_bearing));
			
		
	}
	
	
	   public static String getText(String url) throws Exception {
	        URL website = new URL(url);
	        URLConnection connection = website.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                    connection.getInputStream()));

	        StringBuilder response = new StringBuilder();
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) 
	            response.append(inputLine);

	        in.close();

	        return response.toString();
	    }
	   
	   
	   public static String getJson(String url) throws Exception {
	    // DOWNLOAD THE PROJECT JSON FILE
		   String responseBody = null;
	    HttpClient httpclient = new DefaultHttpClient();
	    try {
	    HttpGet httpget = new HttpGet(url);
	     
	    String TAG = "getJson";
		Log.d(TAG, "executing request " + httpget.getURI());
	     
	    // Create a response handler
	    ResponseHandler<String> responseHandler = new BasicResponseHandler();
	    responseBody = httpclient.execute(httpget, responseHandler);
	     
	    //
	    Log.i(TAG, "DOWNLOADED " + responseBody);
	     
	    } catch (ClientProtocolException e) {
	    e.printStackTrace();
	    } catch (IOException e) {
	    e.printStackTrace();
	    } finally {
	    // When HttpClient instance is no longer needed,
	    // shut down the connection manager to ensure
	    // immediate deallocation of all system resources
	    httpclient.getConnectionManager().shutdown();
	    }
	    
	    
		return responseBody;
	    
	   }
	   
	   
	   public String ReadHttpResponse(String url){
	        StringBuilder sb= new StringBuilder();
	        HttpClient client= new DefaultHttpClient();     
	        HttpGet httpget = new HttpGet(url);     
	        try {
	            HttpResponse response = client.execute(httpget);
	            StatusLine sl = response.getStatusLine();
	            int sc = sl.getStatusCode();
	            if (sc==200)
	            {
	                HttpEntity ent = response.getEntity();
	                InputStream inpst = ent.getContent();
	                BufferedReader rd= new BufferedReader(new InputStreamReader(inpst));
	                String line;
	                while ((line=rd.readLine())!=null)
	                {
	                    sb.append(line);
	                }
	            }
	            else
	            {
	                Log.e("log_tag","I didn't  get the response!");
	            }
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return sb.toString();
	    }
	   
	   
	   
	   public static Point findEnd(char[][] a){
			for(int row = 0; row < a.length; row++){
				for(int col = 0; col < a[row].length; col++){
					if(a[row][col]=='e'){ return new Point(col,row); }
				}
			}
			return null;
		}
	   
	   
	   public static String getDirection(double bearing) {
		   
		   String direction = "xxx";
		   
		   
		   if ( bearing >= 337.5 || bearing < 22.5 ) 
			   direction = "Norden";
           if ( bearing >= 22.5 && bearing < 67.5 ) 
        	   direction = "Nordosten";               
           if (bearing >= 67.5 &&bearing < 112.5)
               direction = "Osten";
           if (bearing >= 112.5 &&bearing < 157.5)
               direction = "Südosten";
           if (bearing >= 157.5 &&bearing < 202.5)
               direction = "Süden";
           if (bearing >= 202.5 &&bearing < 247.5)
               direction = "Südwesten";
           if (bearing >= 247.5 &&bearing < 292.5)
               direction = "Westen";
           if (bearing >= 292.5 &&bearing < 337.5) 
        	   direction = "Nordwesten";
		   
		   
		   return direction;
		   
		   
	   }



}
