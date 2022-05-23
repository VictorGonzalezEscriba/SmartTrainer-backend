import java.util.*;

public class IdGenerator {
    private final List<Integer> m_listIds;

    public IdGenerator(){
        this.m_listIds = new LinkedList<>();
    }

    public int generateId(){
        int id = 0;
        for (int i = 0; i <this.m_listIds.size(); i++){
            if (this.m_listIds.get(i) == id) {
                id++;
            }
        }
        this.m_listIds.add(id);
        return id;
    }
}
