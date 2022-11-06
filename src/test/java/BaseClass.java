import client.OrderClient;
import client.UserClient;
import model.Order;
import model.User;

public class BaseClass {

    protected static final String invalidOrderId = "6d58a2d7-77b3-4c67-8004-dbd31e937fce";
    protected final OrderClient orderClient = new OrderClient();
    protected Order orderIngredientsIdList;
    protected final UserClient userClient = new UserClient();
    protected String accessToken;
    protected User user;
}
