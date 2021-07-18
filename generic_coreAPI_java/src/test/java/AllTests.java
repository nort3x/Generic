import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestR1_Persons.class, TestR2_Hubs.class, TestR3_Reading.class, TestR4_Orario.class,
		TestR5_Pianificazione.class, TestR6_Statistiche.class, TestR7_ReadingNotification.class })
public class AllTests {

}
