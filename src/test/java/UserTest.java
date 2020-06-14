import dk.ecosolutions.oms.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User model test.
 */
public class UserTest {
    private User user;

    // Instantiate user class with constructor parameters.
    @Before
    public void setUp() {
        user = new User(1, "Jens Christensen", "12345678", "jens@eco.dk", "1234");
    }

    // Confirm that the test is completed.
    @After
    public void tearDown() {
        System.out.println("Test Completed!");
    }

    // Validate the expected values match getters values.
    @Test
    public void testUserDetails() {
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("Jens Christensen", user.getName());
        Assert.assertEquals("12345678", user.getPhone());
        Assert.assertEquals("jens@eco.dk", user.getEmail());
        Assert.assertEquals("1234", user.getPassword());
    }
}