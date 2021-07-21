package org.springframework.social.salesforce.quickstart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.salesforce.canvas.SignedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/canvas")
public class CanvasController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasController.class);
    
    
    @RequestMapping(path = "/signedrequest", method = RequestMethod.POST)
    public String quickstartSalesforce(@ModelAttribute("signed_request") String token)
    {
        LOGGER.info("POST request made");
        LOGGER.info("Body " + token);

        String json = SignedRequest.verifyAndDecodeAsJson(token, "8516A144485BB42F89F80AB86B54A6697752A0EA7A481468108ED92B2D74D3F4");
        
        LOGGER.info(json);


        return "canvas";
    }
}