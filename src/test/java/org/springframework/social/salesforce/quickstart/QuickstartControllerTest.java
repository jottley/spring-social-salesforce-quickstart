package org.springframework.social.salesforce.quickstart;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuickstartControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    
    @Value("${salesforce.consumer.key}")
    private String appId;
    
    @Test
    public void homePageRedirects() throws Exception
    {
        mockMvc.perform(get("/"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/connect/salesforce")); 
    }
    
    @Test
    public void connectPageHasLink() throws Exception {
        mockMvc.perform(get("/connect/salesforce"))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Connect to Salesforce")));
    }
    
    @Test
    public void connectHandlerRedirects() throws Exception {
        if (appId.startsWith("{{")) {
            // User hasn't configured app
            MvcResult result = mockMvc.perform(post("/connect/salesforce"))
                    .andExpect(redirectedUrl("/connect/salesforce")).andReturn();
            assertThat(result.getRequest().getSession()
                    .getAttribute("social_provider_error")).isNotNull();
        }
        else {
            mockMvc.perform(post("/connect/salesforce"))
                    .andExpect(redirectedUrlPattern("https://login.salesforce.com/**"));
        }
    }

}
