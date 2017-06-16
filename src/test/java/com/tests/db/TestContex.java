package com.tests.db;

import com.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by Tal on 14/06/2017.
 */
@Configuration
@ContextConfiguration(classes = {AppConfig.class})

public  abstract class TestContex extends AbstractLiquiBase {

}
