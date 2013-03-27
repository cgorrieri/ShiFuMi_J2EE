package enterprise.game_room_ejb.common;

/**
 *
 * @author user
 */
public class PlayerNotFoundException extends Exception {
    
    /** Creates a new instance of SubscriptionNotFoundException */
    public PlayerNotFoundException() {
    }
    
    public PlayerNotFoundException(String message) {
        super(message);
    }
    
}
