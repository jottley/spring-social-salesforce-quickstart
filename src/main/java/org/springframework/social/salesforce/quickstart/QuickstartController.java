package org.springframework.social.salesforce.quickstart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class QuickstartController
{
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
    
}
