package fi.tamk.beerbros.kaljakauppa.security;

/**
 * Account object for authentication and login attempts.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
public class Account {

    /**
     * User name String.
     */
    private String username;
    
    /**
     * Password String.
     */
    private String password;

    /**
     * Constructs empty account object.
     */
    public Account() {}
    
    /**
     * Returns username in String format.
     * 
     * @return Username String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username for Account.
     * 
     * @param username Username String.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password in String format.
     * 
     * @return Password String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password for Account.
     * 
     * @param password Password String.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
