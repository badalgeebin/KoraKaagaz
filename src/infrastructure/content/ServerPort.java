package infrastructure.content;

/**
 * 
 * @author Badal Kumar (111701008)
 *
 */

public class ServerPort implements IServerPort {
	private int port = -1;
	/**
	 * This method will save the port of the board server locally to send message over networking
	 */
	public void sendPort(int port) {
		this.port = port;
	}
	
	/**
	 * This method will provide the port to its caller
	 */
	int getPort() {
		if (port != -1) {
			return port;
		}
		return -1;
	}
}