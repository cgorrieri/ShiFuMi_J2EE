package enterprise.game_room_ejb.common;

/**
 * Exception quand on re trouve pas le joueur dans la base de données
 * @author Cyril
 */
public class PlayerNotFoundException extends Exception {
    
    /** Creates a new instance of SubscriptionNotFoundException */
    public PlayerNotFoundException() {
    }
    
    public PlayerNotFoundException(String message) {
        super(message);
    }
    
}
