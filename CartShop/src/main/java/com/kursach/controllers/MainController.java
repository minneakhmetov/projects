package com.kursach.controllers;

import com.kursach.app.Constants;
import com.kursach.dto.ProductCartDto;
import com.kursach.dto.UserDto;
import com.kursach.forms.ServiceForm;
import com.kursach.models.Auth;
import com.kursach.models.Order;
import com.kursach.models.Product;
import com.kursach.models.ProductCart;
import com.kursach.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.kursach.dto.ProductDtoString.from;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AuthService authService;

    @GetMapping({"/main", ""})
    public String getMain(ModelMap modelMap, HttpServletRequest httpServletRequest) {

        Cookie[] cookies = httpServletRequest.getCookies();
        Auth auth = new Auth();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("vk_id"))
                    auth.setUserId(Integer.valueOf(cookie.getValue()));
                if (cookie.getName().equals("auth"))
                    auth.setAuth(cookie.getValue());
            }
        }
        if(auth.isNotNull() && authService.isExistByCookie(auth)) {
            modelMap.addAttribute("create", true);
            List<Order> buyer = ordersService.showByBuyer(auth.getUserId());
            List<Order> seller = ordersService.showBySeller(auth.getUserId());
            if (buyer.size() != 0)
                modelMap.addAttribute("orders", buyer);
            if (seller.size() != 0)
                modelMap.addAttribute("sales", seller);
        }

        modelMap.addAttribute("products", from(productService.getAll()));
        return "main";
    }

    @GetMapping("/auth")
    public String authRedirect() {
        return "redirect:https://oauth.vk.com/authorize?client_id=" + Constants.APP_ID +
                "&display=page&redirect_uri=" + Constants.REDIRECT_URI + "/login" +
                "&scope=friends&response_type=code&v=5.87";
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(@RequestParam("productId") Long productId, @CookieValue("vk_id") Integer userId) {
        cartService.addToCart(ProductCartDto.builder()
                .productId(productId)
                .userId(userId)
                .build());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @PostMapping("/deleteallcart")
    public ResponseEntity<?> deleteAllCart(@CookieValue("vk_id") Integer userId) {
        cartService.deleteAllCart(userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/deletecart")
    public ResponseEntity<?> deleteCart(@CookieValue("vk_id") Integer userId, @RequestParam("productId") Long productId) {
        cartService.deleteFromCart(productId, userId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String getCode(@RequestParam("code") String code, HttpServletResponse response) {
        UserDto dto = loginService.loginOrRegister(code);
        Cookie vkId = new Cookie("vk_id", dto.getVkId().toString());
        vkId.setMaxAge(60 * 60 * 24);
        Cookie auth = new Cookie("auth", dto.getAuth());
        auth.setMaxAge(60 * 60 * 24);
        response.addCookie(vkId);
        response.addCookie(auth);
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(@CookieValue("vk_id") Integer vkId, @CookieValue("auth") String auth, HttpServletRequest request, HttpServletResponse response) {
        loginService.logout(Auth.builder().userId(vkId).auth(auth).build());
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("vk_id")) {
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
            if (cookies[i].getName().equals("auth")) {
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        }
        return "redirect:/main";
    }

    @PostMapping("/search")
    public String search(ModelMap modelMap, @RequestParam("query") String query) {
        List<Product> productActivities = productService.find(query);
        modelMap.addAttribute("products", productActivities);
        modelMap.addAttribute("query", query);
        modelMap.addAttribute("size", productActivities.size());
        return "search";
    }

    @PostMapping("/createservice")
    public String createService(@CookieValue("vk_id") Integer vkId, @RequestParam("price") String price, @RequestParam("serviceName") String serviceName) {
        ServiceForm form = ServiceForm.builder()
                .price(price)
                .serviceName(serviceName)
                .build();

        productService.createService(form, vkId);
        return "redirect:/main";
    }

    @PostMapping(value = "/usercart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductCart> userCart(@CookieValue("vk_id") Integer userId) {
        return cartService.getProductsInCart(userId);
    }

    @PostMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto getUser(@CookieValue("vk_id") Integer vkId) {
        return loginService.getUser(vkId);
    }

    @GetMapping("/checkouted")
    public String checkouted(@CookieValue("vk_id") Integer vkId) {
        List<ProductCart> cart = cartService.getProductsInCart(vkId);
        ordersService.buyServices(cart, vkId);
        cartService.deleteAllCart(vkId);
        return "redirect:/main";
    }

    @PostMapping("/deleteSeller")
    public ResponseEntity<?> deleteSeller(@CookieValue("vk_id") Integer vkId, @RequestParam("productId") Long productId) {
        ordersService.deleteBySeller(vkId, productId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/deleteBuyer")
    public ResponseEntity<?> deleteBuyer(@CookieValue("vk_id") Integer vkId, @RequestParam("productId") Long productId) {
        ordersService.deleteByBuyer(vkId, productId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
