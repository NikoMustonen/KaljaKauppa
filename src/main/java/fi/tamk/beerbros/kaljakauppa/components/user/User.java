package fi.tamk.beerbros.kaljakauppa.components.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.review.Review;
import fi.tamk.beerbros.kaljakauppa.components.shoppingcart.Order;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 * Entity class for user entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity(name = "userr")
public class User implements Serializable {

    /**
     * User id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    /**
     * Users first name.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Uses last name.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Username.
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Users password.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Users phone number.
     */
    @Column(name = "phone", nullable = true)
    private String phone;

    /**
     * Users email.
     */
    @Column(name = "email", nullable = true)
    private String email;

    /**
     * Users street address.
     */
    @Column(name = "street", nullable = false)
    private String street;

    /**
     * Users city
     */
    @Column(name = "city", nullable = false)
    private String city;

    /**
     * Usres area code.
     */
    @Column(name = "area_code", nullable = false)
    private String areaCode;

    /**
     * Whether user is admin or not.
     */
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    /**
     * List of orders by user.
     */
    @OneToMany(mappedBy = "user")
    private Collection<Order> orders;

    /**
     * Generates empty user.
     */
    public User() {
    }

    /**
     * Generates user with given id.
     *
     * Is used to fetch user data from the database.
     *
     * @param id
     */
    public User(Integer id) {
        this.id = id;
    }

    /**
     * Gets user id.
     *
     * @return Id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets user id.
     *
     * @param id Id number.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets users first name.
     *
     * @return Users firs name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets users first name.
     *
     * @param firstName Users first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets users last name.
     *
     * @return Users last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets users last name.
     *
     * @param lastName Users last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets users username.
     *
     * @return Username String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets users username.
     *
     * @param username Username String.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets users password.
     *
     * Is hidden from JSON response.
     *
     * @return Password String.
     */
    //@JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Sets user password.
     *
     * @param password Password String.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets users phone number.
     *
     * @return Phone number String.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets users phone number.
     *
     * @param phone Phone number Stirng.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets users email.
     *
     * @return Email String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets users Email.
     *
     * @param email Email String.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets street name.
     *
     * @return Street name String.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets street name.
     *
     * @param street Street name String.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets users city.
     *
     * @return City String.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets users city.
     *
     * @param city City String.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets users area code.
     *
     * @return Area code String.
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets users area code.
     * 
     * @param areaCode Area Code String.
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * Gets users admin level.
     * 
     * @return Whether user is admin or not.
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Sets users admin level.
     * 
     * @param isAdmin Whether user is admin or not.
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Returns all orders by user object.
     * 
     * @return List of orders.
     */
    @JsonIgnore
    public Collection<Order> getOrders() {
        return orders;
    }

    /**
     * Sets all orders by user object.
     * 
     * @param orders List 
     */
    @JsonIgnore
    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }
}
