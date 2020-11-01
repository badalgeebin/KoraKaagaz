package infrastructure.content;

import org.json.JSONObject;

public class ContentCommunicator implements IContentCommunicator{
	
	private String serverIPAddress;
	protected String userName;
	protected String userImage;
    
	@Override
	public void initialiseUser(String userDetails) {
    	
		JSONObject jsonObject = new JSONObject(userDetails);
		this.serverIPAddress = jsonObject.getString("ipAddress");
		this.userName = jsonObject.getString("username");
		this.userImage = jsonObject.getString("image");
		
    }
	
	@Override
	public void sendMessageToContent(String message) {
    	
    }
	
	@Override
	public void notifyUserExit() {
    	
    }
    
	@Override
	public void subscribeForNotifications(String identifier, IContentNotificationHandler handler) {
		
    }
}
