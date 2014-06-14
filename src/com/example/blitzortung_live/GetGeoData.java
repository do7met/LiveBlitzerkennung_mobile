package com.example.blitzortung_live;

public class GetGeoData {

	public static double Haversine (double lat1, double lon1, double lat2, double lon2) {
	
		double lat1_rad = lat1 / 360. * 2.*Math.PI;
		double lon1_rad = lon1 / 360. * 2.*Math.PI;
		
		double lat2_rad = lat2 / 360. * 2.*Math.PI;
		double lon2_rad = lon2 / 360. * 2.*Math.PI;
		
		double dlon = lon2_rad - lon1_rad;
		double dlat = lat2_rad - lat1_rad;
		
		double a = Math.sin(dlat/2.)*Math.sin(dlat/2.) + Math.cos(lat1_rad) * Math.cos(lat2_rad) * Math.sin(dlon/2.)*Math.sin(dlon/2.);
		double c = 2. * Math.asin(Math.sqrt(a));
		
		
		double distance = 6367. * c;
		
		return distance;
		
	}
	
	
	
	
	public static double Bearing (double lat1, double lon1, double lat2, double lon2) {
		
		double lat1_rad = lat1 / 360. * 2.*Math.PI;
		double lon1_rad = lon1 / 360. * 2.*Math.PI;
		
		double lat2_rad = lat2 / 360. * 2.*Math.PI;
		double lon2_rad = lon2 / 360. * 2.*Math.PI;
		
		double dlon = lon2_rad - lon1_rad;
		
		double x = Math.sin(dlon) * Math.cos(lat2_rad);
		double y = Math.cos(lat1_rad) * Math.sin(lat2_rad) - ( Math.sin(lat1_rad) * Math.cos(lat2_rad) * Math.cos(dlon) );
		
		double initial_bearing = Math.atan2(x, y);
		
		double bearing = (initial_bearing / (2. * Math.PI) * 360. + 360.) % 360.; 
		
	
		return bearing;
		
	}
	
}
