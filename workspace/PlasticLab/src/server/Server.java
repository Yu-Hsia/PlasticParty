package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import java.sql.*;

import server.model.Guest;
import server.model.Log;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
	
	private OnResponseListener mListener;
	private HttpServer mServer;
	
	public interface OnResponseListener {
		public void onGetResponse(String guestId, String tableNumber);
	}

	public Server(OnResponseListener l) {
		mListener = l;
	}
	
	public void startServer() throws IOException {
		mServer = HttpServer.create(new InetSocketAddress(8000), 0);
		mServer.createContext("/test", new ResponseHandler());
		mServer.setExecutor(null); // creates a default executor
		mServer.start();
    }
	
	public void stopServer() throws IOException {
		mServer.stop(0);
	}
	
	public Map<String, String> queryToMap(String query){
	    Map<String, String> result = new HashMap<String, String>();
	    for (String param : query.split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
	    return result;
	}

    class ResponseHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            Calendar calendar = Calendar.getInstance();
            DatabaseHelper helper = DatabaseHelper.getInstance();
            try {
            	String id = params.get(Log.CONTENT_GUESTID_COLUMN);
            	String table = params.get(Log.CONTENT_TABLENUMBER_COLUMN);
				helper.insertLog(id, table, calendar.getTimeInMillis());
				mListener.onGetResponse(id, table);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
}
