package org.springframework.social.salesforce.quickstart;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.salesforce.api.LimitsResults;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class QuickstartController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QuickstartController.class);
    
    private Salesforce salesforce;
    @Autowired
    private ConnectionRepository connectionRepository;
    
    @RequestMapping(method=RequestMethod.GET)
    public String quickstartSalesforce(Model model)
    {
        Connection<Salesforce> connection = connectionRepository.findPrimaryConnection(Salesforce.class);
        
        if (connection == null)
        {
            return "redirect:/connect/salesforce";
        }
        else
        {
            salesforce = connection.getApi();
        }
        
        model.addAttribute(salesforce.userOperations().getSalesforceUserDetails());
        return "quickstart";
    }


    @RequestMapping(value="/Limits", method=RequestMethod.GET)
    public String getLimits(Model model) {
        
        Connection<Salesforce> connection = connectionRepository.findPrimaryConnection(Salesforce.class);
        
        if (connection == null)
        {
            return "redirect:/connect/salesforce";
        }
        else
        {
            salesforce = connection.getApi();
        }
        LimitsResults results = salesforce.limitsOperations().getLimits();
        model.addAttribute(results);

        ObjectMapper mapper = new ObjectMapper();
        try {
            LOGGER.info(mapper.writeValueAsString(results));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        LOGGER.info("API Used: " + salesforce.limitsOperations().getDailyApiUsed());
        LOGGER.info("API Max: " + salesforce.limitsOperations().getDailyApiLimit());


        return "limits";

    }
    
    
}
