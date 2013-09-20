package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import server.model.Log;

public class Client {
	
	private static void launchClient() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://localhost:8000/test?id=1&table=2");
			HttpResponse response = client.execute(request);

			// Get the response
			BufferedReader rd = new BufferedReader
			  (new InputStreamReader(response.getEntity().getContent()));
			    
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				buffer.append(line);
			} 
			
			System.out.print(buffer.toString());
		} catch(Exception e) {
			
		}
		
	}
	
	public static void stressTest() {
		for(int i=0; i<1000; i++) {
			new Runnable() {

				@Override
				public void run() {
					launchClient();
				}
			};
		}
	}

	public static void main(String[] args) throws Exception {
//		stressTest();
		
//		HttpClient client = new DefaultHttpClient();
//		HttpGet request = new HttpGet("http://localhost:8000/test?test=ew");
//		HttpResponse response = null;
//		try {
//			response = client.execute(request);
//			// Get the response
//			BufferedReader rd = new BufferedReader
//			  (new InputStreamReader(response.getEntity().getContent()));
//			    
//			StringBuffer buffer = new StringBuffer();
//			String line = "";
//			while ((line = rd.readLine()) != null) {
//				buffer.append(line);
//			} 
//			
//			System.out.print(buffer.toString());
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		for(int i=0; i<1000; i++) {

			System.out.print(i + " ");
			
			new Runnable() {

				@Override
				public void run() {
					HttpClient client = new DefaultHttpClient();
					HttpGet request;
					// HttpGet request = new HttpGet("http://localhost:8000/test?" + Log.CONTENT_GUESTID_COLUMN + "=1&" + Log.CONTENT_TABLENUMBER_COLUMN + "=2");
					int flag = (int) (Math.random() * 100);
					if(flag % 2 == 0) {
						request = new HttpGet("http://localhost:8000/test?" + Log.CONTENT_GUESTID_COLUMN + "=1&" + Log.CONTENT_TABLENUMBER_COLUMN + "=2");
					} else {
						request = new HttpGet("http://localhost:8000/test?" + Log.CONTENT_GUESTID_COLUMN + "=2&" + Log.CONTENT_TABLENUMBER_COLUMN + "=4");
					}
					HttpResponse response = null;
					try {
						response = client.execute(request);
						// Get the response
						BufferedReader rd = new BufferedReader
						  (new InputStreamReader(response.getEntity().getContent()));
						    
						StringBuffer buffer = new StringBuffer();
						String line = "";
						while ((line = rd.readLine()) != null) {
							buffer.append(line);
						} 
						
						System.out.println(buffer.toString());

					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.run();
		}
	}
}
