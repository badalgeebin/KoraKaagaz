package infrastructure.content;

/**
 * This Interface is used for sending port of board server to content module.
 * @author Badal Kumar (111701008)
 */
public interface IServerPort {
	/**
	 * the processing module calls this method to send port of board server to each new client
	 * @param port - This is the port of board server in int datatype
	 */
	void sendPort(int port);
}