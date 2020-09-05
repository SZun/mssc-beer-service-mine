package guru.springframework.msscbeerservice.services.inventory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    private BeerInventoryService beerInventoryService;

    private final UUID BEER_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");

//    @BeforeEach
//    void setUp() {
//
//    }

    @Test
    void getOnhandInventory() {
        System.out.println(
                beerInventoryService.getOnhandInventory(BEER_UUID)
        );
    }
}