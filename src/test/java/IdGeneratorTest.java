import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    @Test
    void generateId() {
        IdGenerator ig = new IdGenerator();
        int id0 = ig.generateId();
        int id1 = ig.generateId();
        int id2 = ig.generateId();
        assertAll(
                () -> assertEquals(0, id0),
                () -> assertEquals(1, id1),
                () -> assertEquals(2, id2)
        );
    }
}