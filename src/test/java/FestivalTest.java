import org.junit.Test;
import run.zhinan.time.festival.Festival;

import java.time.LocalDate;
import java.util.List;

public class FestivalTest {
    public final static LocalDate date = LocalDate.of(2023, 11, 24);

    @Test
    public void testFestival() {
        for (LocalDate date = LocalDate.of(2023, 12, 31);
                date.isBefore(LocalDate.of(2024,  1,  3)); date = date.plusDays(1)) {
            List<String> festivals = Festival.of(date);
            if (!festivals.isEmpty()) System.out.println(date.toString() + festivals);
        }
    }
}
