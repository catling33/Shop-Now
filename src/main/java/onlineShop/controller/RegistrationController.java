package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.entity.Customer;
import onlineShop.service.CustomerService;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerService customerService;

    // get registration page
    @RequestMapping(value = "/customer/registration", method = RequestMethod.GET) // get registration page
    public ModelAndView getRegistrationForm() {
        Customer customer = new Customer(); // create a new row in "customer" table in DB
        return new ModelAndView("register", "customer", customer);
    }

    // post info on registration page
    @RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
    // ModelAttribute : bind form data to a bean
    public ModelAndView registerCustomer(@ModelAttribute Customer customer,
                                         BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        // if registration failed, return registration page
        if (result.hasErrors()) {
            modelAndView.setViewName("register");
            return modelAndView;
        }
        // if successfully registered, return login page
        customerService.addCustomer(customer);
        modelAndView.setViewName("login");
        modelAndView.addObject("registrationSuccess", "Registered Successfully. Login using username and password");
        return modelAndView;
    }
}
