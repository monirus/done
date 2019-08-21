import java.util.Random;

public class Util
{
    public static int generateRandomPrice()
    {
        Random random = new Random();
        return random.nextInt(100);
    }
}
