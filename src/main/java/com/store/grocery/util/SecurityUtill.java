package com.store.grocery.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtill {
	public String getUserDetails() {
		
		// Get the current authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Retrieve user details from the authentication object
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                
                // Access user details
                String username = userDetails.getUsername();
                
                return username;
            }
        }
        return null;
	}
}
