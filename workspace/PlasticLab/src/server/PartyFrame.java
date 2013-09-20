package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.http.util.TextUtils;

import server.Server.OnResponseListener;
import server.model.Guest;
import ui.Table;

public class PartyFrame extends JFrame implements OnResponseListener {
	
	private final Server mServer;

	public static String DEFAULT_TABLE_NUMBER = "0";
	/* guestId, Guest Info */
	public static HashMap<String, Guest> mGuestMap;
	/* guestId, tableNumber */
	public static HashMap<String, String> mGuestAtTable;
	/* tableNumber, table */
	public static HashMap<String, Table> mTableMap;
	

	public PartyFrame() throws HeadlessException {

        DatabaseHelper helper = DatabaseHelper.getInstance();
        mServer = new Server(this);
        try {
            helper.createTablesIfNeed();
//			helper.insertGuest(1, "Erin Waldie", "Air Miles", "Associate Director, Digital Marketing", "Wilfred Laurier University", "", "");
//	        helper.insertGuest(2, "Amar Narain", "Pizza Pizza", "Director IT", "Dawson College", "", "");
	        mGuestMap = helper.getGuestMap();
	        mGuestAtTable = helper.getGuestTableMap();
	        
	        
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        setBounds(0,0,screenSize.width, screenSize.height);
	        setVisible(true);

			Table table = new Table(1);
		    getContentPane().add(table);
		        
	        mServer.startServer();
	        	
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	public static void main(String args[]) {
		PartyFrame frame = new PartyFrame();
		frame.setVisible(true);
	}

	@Override
	public void onGetResponse(String guestId, String tableNumber) {
		
		String preTable = mGuestAtTable.get(guestId);
//		if(preTable.equals(tableNumber)) {
			// TODO update UI
		        
			
//		} 

				System.out.println(mGuestMap.get(guestId).name);
		mGuestAtTable.put(guestId, tableNumber);
		
	}
	
	public void setWindowEvent() {
		this.addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e) {
                System.exit(1);
                try {
					mServer.stopServer();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
		});
	}

}
