package infrastructure.content;

import org.json.JSONObject;
import networking.CommunicatorFactory;
import networking.ICommunicator;
import java.util.HashMap;

/**
 * This class ContentCommunicator implements the IContentCommunicator interface and all the 
 * methods inside it. The ideal purpose of this class is to initialise any new client, send 
 * message from UI to networking module, handle client exit with respect to content module
 * and handle the subscribers of content module.
 * 
 * @author Badal Kumar (111701008)
 */
public class ContentCommunicator implements IContentCommunicator{
	/**
	 * This variable will store the username/userid of client as a String.
	 */
	private String userName;
	
	/**
	 * This variable will store the image of client in String format.
	 */
	private String userImage;
	
	/**
	 * This variable will store the server's ipAddress as a String.
	 */
	private String serverIPAddress;
	
	/**
	 * This variable will store the port number of the board which the client is accessing.
	 */
	private int port = 0;
	
	/**
	 * Creating a HashMap to store userName and userImage of all active clients of a particular board.
	 * Both userName and userImage are of String type
	 */
	private HashMap<String, String> mapImage = new HashMap<String, String>();
	
	/**
	 * Creating a HashMap to store the identifier and handler of subscriber of this module,
	 * identifier is of type String while handler is of type IContentNotificationHandler.
	 */
	private HashMap<String, IContentNotificationHandler> mapHandler = new HashMap<String, IContentNotificationHandler>();
	
	/**
	 * This variable will store the communicator of networking module to send data over the network.
	 */
	private ICommunicator communicator;
	
	/**
	 * This method provides userName to other classes within this package.
	 * @return userName - username of the client.
	 */
	protected String getUserName() {
		return userName;
	}
	
	/**
	 * This method provides HashMap mapImage to other classes within this package.
	 * @return mapImage - The HashMap which stores the userName and userImage.
	 */
	protected HashMap<String, String> getMapImage() {
		return mapImage;
	}
	
	/**
	 * This method allows other classes within this package to set the HashMap mapImage.
	 * @param mapImage - The HashMap which stores the userName and userImage of all clients.
	 */
	protected void setMapImage(HashMap<String, String> mapImage) {
		this.mapImage = mapImage;
	}
	
	/**
	 * This method provides HashMap mapHandler to other classes within this package.
	 * @return mapHandler - The HashMap which stores the identifier and handler of subscriber of this module.
	 */
	protected HashMap<String, IContentNotificationHandler> getMapHandler() {
		return mapHandler;
	}
	
	/**
	 * This method initializes a user by storing its username and userimage in a map as Strings.
	 * This method stores these values locally too and generates server's ipAddress by getting
	 * the port value of the board from ServerPort class.
	 * Finally, some manipulation on JSON string is done and then sent to server via networking.
	 * @param userDetails - This is a JSON string which contains a JSON object with three fields, 
	 * namely ipAddress, username and image.
	 */
	public void initialiseUser(String userDetails) {
		JSONObject jsonObject = new JSONObject(userDetails);
		serverIPAddress = jsonObject.getString("ipAddress");
		userName = jsonObject.getString("username");
		userImage = jsonObject.getString("image");
		ServerPort serverPort = new ServerPort();
		port = serverPort.getPort();
		communicator = CommunicatorFactory.getCommunicator(port);
		serverIPAddress += ":";
		serverIPAddress += String.valueOf(port);
		mapImage.put(userName, userImage);
		jsonObject.put("meta", "newUser");
		jsonObject.remove("ipAddress");
		communicator.send(serverIPAddress, jsonObject.toString(), "contentServer");
	}
	
	/**
	 * This method gets the message from UI module and then send it via networking module.
	 * A meta field is added to it indicating that it is message before sending to server.
	 * @param message - This is a JSON string which contains a JSON object with key "message"
	 */
	public void sendMessageToContent(String message) {
		JSONObject jsonObject = new JSONObject(message);
		jsonObject.put("meta", "message");
		jsonObject.put("username",userName);
		communicator.send(serverIPAddress, jsonObject.toString(), "contentServer");
	}
	
	/**
	 * This method clears all the maps created by content module locally and informs the
	 * server that the client is leaving the board by sending a JSON String which contains
	 * a JSON object with two keys, meta and username via networking module.
	 */
	public void notifyUserExit() {
		mapImage.clear();
		mapHandler.clear();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("meta", "userExit");
		jsonObject.put("username", userName);
		communicator.send(serverIPAddress, jsonObject.toString(), "contentServer");
	}
	
	/**
	 * UI will subscribe to us in order to receive any updates coming from other users.
	 * In this method, identifier and handler will be updated in the HashMap mapHandler.
	 * @param identifier - This will be a string and unique too
	 * @param handler - By using this handler, methods of IContentNotificationHandler will be called
	 */
	public void subscribeForNotifications(String identifier, IContentNotificationHandler handler) {
		mapHandler.put(identifier, handler);
	}
}