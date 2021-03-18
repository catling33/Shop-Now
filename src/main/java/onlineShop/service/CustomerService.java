package onlineShop.service;

import onlineShop.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CustomerDao;
import onlineShop.entity.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    // controller will call this method
    public void addCustomer(Customer customer) {
        // have to enable the customer so that he can login
        customer.getUser().setEnabled(true);

        // relate a cart and a customer
        Cart cart = new Cart();
        customer.setCart(cart);

        customerDao.addCustomer(customer);
    }

    public Customer getCustomerByUserName(String userName) {
        return customerDao.getCustomerByUserName(userName);
    }
}

