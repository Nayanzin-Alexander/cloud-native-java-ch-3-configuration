package com.nayanzin.configurationbasics;

import com.nayanzin.configurationbasics.boot.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class ConfigurationBasicsApplicationTests {

    @Test
    public void contextLoads() {
    }

}

