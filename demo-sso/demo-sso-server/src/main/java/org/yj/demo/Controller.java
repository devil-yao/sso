package org.yj.demo;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin/login")
    public void login(@RequestParam("username")String user,@RequestParam("password")String pass){
        UserDetails details = userDetailsService.loadUserByUsername(user);

//        details.getPassword()
        Authentication authentication = new UsernamePasswordAuthenticationToken(user,details.getPassword(),details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return;
    }

    @GetMapping("/user")
    public Object user(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/")
    public Object index(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // public static void main(String[] args) {
    //     System.out.println(new BCryptPasswordEncoder().encode("sso"));
    // }

}
