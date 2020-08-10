package org.o7planning.springmvcshoppingcart.controller;

import org.o7planning.springmvcshoppingcart.dao.AccountDAO;
import org.o7planning.springmvcshoppingcart.dao.DiscountDAO;
import org.o7planning.springmvcshoppingcart.dao.OrderDAO;
import org.o7planning.springmvcshoppingcart.dao.ProductDAO;
import org.o7planning.springmvcshoppingcart.entity.Account;
import org.o7planning.springmvcshoppingcart.entity.Product;
import org.o7planning.springmvcshoppingcart.model.CartInfo;
import org.o7planning.springmvcshoppingcart.model.CustomerInfo;
import org.o7planning.springmvcshoppingcart.model.PaginationResult;
import org.o7planning.springmvcshoppingcart.model.ProductInfo;
import org.o7planning.springmvcshoppingcart.util.Utils;
import org.o7planning.springmvcshoppingcart.validator.CustomerInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;


@Controller

@Transactional

@EnableWebMvc
public class  MainController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private DiscountDAO discountDAO;

    @Autowired
    private CustomerInfoValidator customerInfoValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        if (target.getClass() == CartInfo.class) {

        }

        else if (target.getClass() == CustomerInfo.class) {
            dataBinder.setValidator(customerInfoValidator);
        }

    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "productList";
    }

//    @RequestMapping(value = "/SignUp", method = RequestMethod.POST)
//    public ModelAndView userAccountForm(
//            @Valid @ModelAttribute("account") Account acc,
//            BindingResult result) {
//
//        if (result.hasErrors()) {
//            return "SignUp";
//        }
//
//        ModelAndView model1 = new ModelAndView("UserAccount");
//        return model1;
//    }
@RequestMapping(value = {"/SignUp"},method =RequestMethod.POST)
public String signUpFormProcess(@Validated @ModelAttribute("account") Account account,BindingResult bindingResult,HttpServletRequest request) {


    if (bindingResult.hasErrors()) {
        return "SignUp";
    }
    if(accountDAO.findAccount(account.getUserName()) == null)
    accountDAO.saveAccount(account);
    else
        return "duplicateUserName";

    return "index";
}
@RequestMapping(value = {"/SignUp"},method = RequestMethod.GET)
public String signUpForm(Model model,HttpServletRequest request) {
    model.addAttribute("account", new Account());
    return "SignUp";
}

@RequestMapping(value = "shoppingCartConfirmationDiscount", method = RequestMethod.POST)
public String discountProcess(HttpServletRequest request, Model model){
    CartInfo cartInfo = Utils.getCartInSession(request);
    if(discountDAO.findDiscount(request.getParameter("discount"))!=null) {
        cartInfo.setDiscount(discountDAO.findDiscount(request.getParameter("discount")).getAmount());
        model.addAttribute("discount",discountDAO.findDiscount(request.getParameter("discount")));
    }
    return "redirect:/shoppingCartConfirmation";
}

    @RequestMapping({ "/productList" ,"/"})
    public String listProductHandler(Model model,
                                     @RequestParam(value = "name", defaultValue = "") String likeName,
                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "log", defaultValue = "false") boolean log,
                                     HttpServletRequest request, Principal principal) {
        HttpSession session=request.getSession();
        final int maxResult = 15;
        final int maxNavigationPage = 10;
        boolean admin = false;
        String username;
//        if (principal instanceof UserDetails) {
//            admin = session.getAttribute("SPRING_SECURITY_CONTEXT").toString().contains("ROLE_MANAGER");
//        } else {
//            admin = session.getAttribute("SPRING_SECURITY_CONTEXT").toString().contains("ROLE_MANAGER");
//        }
        PaginationResult<ProductInfo> result;
        if (request.getParameter("cat")!=null && !(request.getParameter("cat").equals("All")))
         result = productDAO.queryProducts(page,
                maxResult, maxNavigationPage, likeName, request.getParameter("cat"),admin);
        else result = productDAO.queryProducts(page,maxResult, maxNavigationPage, likeName, admin);

        model.addAttribute("paginationProducts", result);
        return "productList";
    }

    @RequestMapping({ "/buyProduct" })
    public String listProductHandler(HttpServletRequest request, Model model,
                                     @RequestParam(value = "code", defaultValue = "") String code) {

        Product product = null;
        if (code != null && code.length() > 0) {
            product = productDAO.findProduct(code);
        }
        if (product != null) {


            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cartInfo.addProduct(productInfo, 1);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model,
                                       @RequestParam(value = "code", defaultValue = "") String code) {
        Product product = null;
        if (code != null && code.length() > 0) {
            product = productDAO.findProduct(code);
        }
        if (product != null) {


            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cartInfo.removeProduct(productInfo);

        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request,
                                        Model model,
                                        @ModelAttribute("cartForm") CartInfo cartForm) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);


        return "redirect:/shoppingCart";
    }


    @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = Utils.getCartInSession(request);

        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }


    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

        CartInfo cartInfo = Utils.getCartInSession(request);


        if (cartInfo.isEmpty()) {


            return "redirect:/shoppingCart";
        }

        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        if (customerInfo == null) {
            customerInfo = new CustomerInfo();
        }

        model.addAttribute("customerForm", customerInfo);

        return "shoppingCartCustomer";
    }


    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request,
                                           Model model,
                                           @ModelAttribute("customerForm") @Validated CustomerInfo customerForm,
                                           BindingResult result,
                                           final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            customerForm.setValid(false);

            return "shoppingCartCustomer";
        }

        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);

        cartInfo.setCustomerInfo(customerForm);


        return "redirect:/shoppingCartConfirmation";
    }

    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);


        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {

            return "redirect:/shoppingCartCustomer";
        }
        model.addAttribute("shippingPrice",cartInfo.getShippingPrice());
        model.addAttribute("total",cartInfo.getShippingPrice()+cartInfo.getAmountTotal());
        model.addAttribute("discount",cartInfo.getDiscount());
        return "shoppingCartConfirmation";
    }


    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model, Principal principal) {
        CartInfo cartInfo = Utils.getCartInSession(request);

        // Cart have no products.
        if (cartInfo.isEmpty()) {
            // Redirect to shoppingCart page.
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            // Enter customer info.
            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderDAO.saveOrder(cartInfo, principal.getName() );
        } catch (Exception e) {

            return "loginPlz";
        }

        Utils.removeCartInSession(request);


        Utils.storeLastOrderedCartInSession(request, cartInfo);

        return "redirect:/Paying";
    }
    @RequestMapping(value = "/Paying",method = RequestMethod.POST)
    public String payPost(){
        return "redirect:/shoppingCartFinalize";
    }

    @RequestMapping(value = "/Paying",method = RequestMethod.GET)
    public String payGet(HttpServletRequest request, Model model) {
        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

        model.addAttribute("Amount",lastOrderedCart.getAmountTotal()+lastOrderedCart.getShippingPrice());

        return "Paying";
    }


        @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {

        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }

        return "shoppingCartFinalize";
    }

    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("code") String code) throws IOException {
        Product product = null;
        if (code != null) {
            product = this.productDAO.findProduct(code);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }

}
