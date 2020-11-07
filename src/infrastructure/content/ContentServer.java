package infrastructure.content;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import networking.CommunicatorFactory;
import networking.ICommunicator;
import networking.INotificationHandler;
import processing.server.board.IClientIP;
import processing.server.board.ServerFactory;
import processing.utility.IpAddress;
import processing.utility.Username;

/**
 * 
 * @author Badal Kumar (111701008)
 */
public class ContentServer implements INotificationHandler{
	
	private String meta;
	private String time;
	private String userName;
	private String userImage;
	private IpAddress ipAddress;
	private HashMap<String, String> imageMap = new HashMap<String, String>();
	private HashMap<Username, IpAddress> userIpMap = new HashMap<Username, IpAddress>();
	private ServerPort serverport = new ServerPort();
	private int port = serverport.getPort();
	private ICommunicator communicator = CommunicatorFactory.getCommunicator(port);
	private IClientIP clientIP = ServerFactory.getIPHandler();
	
	public void onMessageReceived(String message) {
		JSONObject jsonObject = new JSONObject(message);
		meta = jsonObject.getString("meta");
		if (meta.equals("newUser")) {
			userName = jsonObject.getString("username");
			userImage = jsonObject.getString("image");
			imageMap.put(userName, userImage);
			JSONArray jsonArray = new JSONArray();
			JSONObject tempJsonObject = new JSONObject();
			for (String name : imageMap.keySet()) {
				userName = name;
				userImage = imageMap.get(userName);
				tempJsonObject.put("username", userName);
				tempJsonObject.put("image", userImage);
				jsonArray.put(tempJsonObject);
				tempJsonObject.remove("username");
				tempJsonObject.remove("image");
			}
			sendToAll(jsonArray.toString());
		}
		else if (meta.equals("message")) {
			LocalTime now = LocalTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			time = dtf.format(now).toString();
			jsonObject.put("time",time);
			sendToAll(jsonObject.toString());
		}
		else if (meta.equals("userExit")) {
			userName = jsonObject.getString("username");
			imageMap.remove(userName);
			sendToAll(message);
		}
		else {}
	}
	
	private void sendToAll(String message) {
		userIpMap = (HashMap<Username, IpAddress>) clientIP.getClientIP();
		for (Username name: userIpMap.keySet()) {
			ipAddress = userIpMap.get(name);
			communicator.send(ipAddress.toString(), message, "content");
		}
	}
}
