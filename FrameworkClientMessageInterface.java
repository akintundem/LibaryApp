import java.text.ParseException;

/**
 *
 * @author ferens
 */
public interface FrameworkClientMessageInterface {
    public abstract void handleClientMessage(
        FrameworkClientConnection
            myClientConnection, String msg) throws ParseException;
    public abstract void handleClientMessage(
            String theExceptionalEvent);
    
}
