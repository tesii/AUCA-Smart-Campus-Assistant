import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.*;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest

class RequestTest {

    @Autowired
    private RequestRepository repo;

    @Test
    void testCreateRequest() {
        Request r = new Request();
        r.setTitle("Test");
        r.setStatus("NEW");

        Request saved = repo.save(r);

        assertNotNull(saved.getId());
    }
}