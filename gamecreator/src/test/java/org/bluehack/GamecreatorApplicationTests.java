package org.bluehack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GamecreatorApplication.class)
@WebAppConfiguration
public class GamecreatorApplicationTests {

	@Test
	public void contextLoads() {
	}

}
