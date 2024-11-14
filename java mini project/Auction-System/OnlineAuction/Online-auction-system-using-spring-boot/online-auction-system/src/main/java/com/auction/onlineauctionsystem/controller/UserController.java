package com.auction.onlineauctionsystem.controller;

import java.security.Principal;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auction.onlineauctionsystem.entities.item;
import com.auction.onlineauctionsystem.entities.user;
import com.auction.onlineauctionsystem.repositories.ItemRepository;
import com.auction.onlineauctionsystem.repositories.UserRepository;
import com.auction.onlineauctionsystem.services.authenticateUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

   

    @ModelAttribute("user")
    public user createUser() {
        return new user();
    }

    @GetMapping("/")
    public String index(Model model) {
       
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new user());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") user user, HttpServletRequest request) {

        if(user.getUsername()=="" || user.getEmail()=="" || user.getPassword()=="")
        {
            return "redirect:/login";
        }
        // Authenticate the user
        authenticateUser authenticateUser = new authenticateUser(userRepository);
        boolean isAuthenticated = authenticateUser.authenticate(user.getUsername(), user.getPassword());



        // If authentication fails, redirect back to the login page with an error message
        if (!isAuthenticated) {
            request.setAttribute("error", "Invalid username or password");
            return "login";
        }

        // If authentication succeeds, create a session and redirect to the home page
        HttpSession session = request.getSession();
        session.setAttribute("userName", user.getUsername());
        return "start";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            @RequestParam("confirmPassword") String confirmPassword){
        authenticateUser authenticateUser = new authenticateUser(userRepository);
        boolean isAuthenticated = authenticateUser.authenticate(username);

        if(username=="" || email=="" || password=="" || confirmPassword=="")
        {
            return "redirect:/signup";
        }
        if (isAuthenticated) {
            return "redirect:/signup";
        }
        if(!password.equals(confirmPassword)){
            return "redirect:/signup";
        }
        user user=new user(username,email,password);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/sellitem")
    public String sellItem(Model model) {
        return "sellitem";
    }

    @PostMapping("/sellitem")
    public String sellitem(@RequestParam("name") String name,
                            @RequestParam("description") String description,
                            @RequestParam("price") String price){
        // authenticateUser authenticateUser = new authenticateUser(userRepository);
        // boolean isAuthenticated = authenticateUser.authenticate(username);

        if(name=="" || price=="" ||description=="")
        {
            return "redirect:/sellitem";
        }
        // if (isAuthenticated) {
        //     return "redirect:/signup";
        // }
        // if(!password.equals(confirmPassword)){
        //     return "redirect:/signup";
        // }
        // user user=new user(username,email,password);
        item item=new item(name,Double.parseDouble(price),description,Double.parseDouble(price));
        itemRepository.save(item);
        return "redirect:/sellitem";
    }

    @GetMapping("/start")
    public String start(Model model) {
        return "start";
    }

    @GetMapping("/showitem")
    public String listItems(Model model) {
        List<item> items = (List<item>) itemRepository.findAll();
        model.addAttribute("items", items);
        return "showitem";
    }

    @GetMapping("/item/{id}")
    public String getItemDetails(@PathVariable("id") Long itemId, Model model) {
        // Retrieve the item from the database using the item id
        item item = itemRepository.findById(itemId).orElse(null);

        if (item == null) {
            // Handle case where item with the specified id is not found
            return "error";
        }

        // Add the retrieved item to the model
        model.addAttribute("item", item);

        // Return the view to display the item details page
        return "item";
    }

    @PostMapping("/item/{id}")
    public String placeBid(@PathVariable("id") Long itemId, @RequestParam("bid-amount") Double bidAmount, Model model) {        
        // Retrieve the item from the database using the item id
        item item = itemRepository.findById(itemId).orElse(null);

        if (item == null) {
            // Handle case where item with the specified id is not found
            return "error";
        }

        // Check if the bid amount is greater than the current max price of the item
        if (bidAmount <= item.getMaxPrice()) {
            // Add an error message to the model
            model.addAttribute("error", "Your bid must be higher than the current maximum price.");

            // Add the retrieved item to the model
            model.addAttribute("item", item);

            // Return the view to display the item details page with the error message
            return "item";
        }

        // Update the item's max price with the new bid amount and save it to the database
        item.setMaxPrice(bidAmount);
        itemRepository.save(item);

        // Add a success message to the model
        model.addAttribute("message", "Your bid has been placed successfully!");

        // Add the retrieved item to the model
        model.addAttribute("item", item);

        // Return the view to display the item details page with the success message
        return "item";
    }

    @GetMapping("logout")
    public String logout(Model model)
    {
        return "index";
    }

    
}

