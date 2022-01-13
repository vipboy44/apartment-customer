package poly.com.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import poly.com.entity.Apartment;
import poly.com.repository.ApartmentRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	ApartmentRepository apartmentRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Apartment apartment = apartmentRepository.findById(username).orElse(null);
		if (apartment == null) 
			 throw new UsernameNotFoundException(username);
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
				
		 return new org.springframework.security.core.userdetails.User(
	        		apartment.getId(), apartment.getPassword(),grantedAuthorities);
	}

}
