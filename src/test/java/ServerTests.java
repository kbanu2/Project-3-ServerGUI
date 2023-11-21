import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTests {

    @Test
    public void testServerConstructorPass(){
        assertDoesNotThrow(() -> {
            Server s = new Server(new Controller(), 1000);
        });
    }

    @Test public void testServerConstructorFail(){
        assertThrows(RuntimeException.class, () ->{
            Server s = new Server(new Controller(), -1);
        });
    }


}
