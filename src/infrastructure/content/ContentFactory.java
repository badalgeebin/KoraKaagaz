package infrastructure.content;

/**
 * This is a factory class of content module which contains instantiation of
 * ContentCommunicator class and ServerPort class in two different methods from
 * which other modules can get the object of those classes.
 * @author Badal Kumar (111701008)
 */
public final class ContentFactory {
	
	private static IContentCommunicator instance1;
	private static IServerPort instance2;
	
	/**
	 * A private constructor so that another instance of ContentFactory can't be created.
	 */
	private ContentFactory() {}
	
	/**
	 * This method will create an instance of ContentCommunicator class and returns it.
	 * @return instance1 - An instance of ContentCommunicator class
	 */
	public static IContentCommunicator getContentCommunicator() {
		if (instance1 == null) {
			instance1 = new ContentCommunicator();
		}
		return instance1;
	}
	
	/**
	 * This method will create an instance of ServerPort class and returns it.
	 * @return instance2 - An instance of ServerPort class
	 */
	public static IServerPort getServerPort() {
		if (instance2 == null) {
			instance2 = new ServerPort();
		}
		return instance2;
	}
}