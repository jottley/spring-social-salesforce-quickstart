
package org.springframework.social.salesforce.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({SocialConfig.class})
public class Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);

    }

}
