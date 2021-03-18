package onlineShop.controller;

import onlineShop.entity.Product;
import onlineShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public ModelAndView getAllProducts() {
        List<Product> products = productService.getAllProducts();
        // return a page with products
        return new ModelAndView("productList", "products", products);
    }

    // PathVariable will extract productId from URL
    @RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET)
    public ModelAndView getProductById(@PathVariable(value = "productId") int productId) {
        Product product = productService.getProductById(productId);
        return new ModelAndView("productPage", "product", product);
    }

    @RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET)
    public ModelAndView getProductForm() {
        return new ModelAndView("addProduct", "productForm", new Product());
    }

    @RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "addProduct";
        }
        productService.addProduct(product);
        return "redirect:/getAllProducts";
    }

    @RequestMapping(value = "/admin/delete/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") int productId) {
        productService.deleteProduct(productId);
        // return page with all products
        return "redirect:/getAllProducts";
    }

    @RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.GET)
    public ModelAndView getEditForm(@PathVariable(value = "productId") int productId) {
        // find the product to edit
        Product product = productService.getProductById(productId);
        // find view
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editProduct");
        // add product
        modelAndView.addObject("editProductObj", product);
        // add product primary key, so that web page knows productId
        modelAndView.addObject("productId", productId);

        return modelAndView;
    }

    @RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.POST)
    public String editProduct(@ModelAttribute Product product,
                              @PathVariable(value = "productId") int productId) {
        // DB will know we are editing an existing entry
        product.setId(productId);
        productService.updateProduct(product);
        return "redirect:/getAllProducts";
    }
}
