import dk.ecosolutions.oms.domain.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User(1, "Jens Christensen", "jens@eco.dk", "1234");
    }

    @After
    public void tearDown() {
        System.out.println("Test Completed!");
    }

    @Test
    public void testUserDetails() {
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("Jens Christensen", user.getName());
        Assert.assertEquals("jens@eco.dk", user.getEmail());
        Assert.assertEquals("1234", user.getPassword());
    }
}