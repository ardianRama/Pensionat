package org.example.pensionat.Service;

import org.springframework.stereotype.Service;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// service
@Service
public class SebCService {

    //private static final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

/**

    @Autowired
    private CustomerRepository customerRepository;


    public boolean existsUsername(String username){
        return customerRepository.findByUsername(username).isPresent();
    }

    public Optional<String> findByUsername(String username) {
        return customerRepository.findByUsername(username).map(Customer::getPassword);
    }

    /*
    public String encryptPassword(String password) {
        return encoder.encode(password);
    }

    public boolean matchPassword(String okrypterat, String krypterat) {
        return encoder.matches(okrypterat, krypterat);
    }

     */

/**
    public Customer registerUsername(String username, String password, String name, String email, String phoneNumber, String address) {
        Customer c = new Customer();
        c.setUsername(username);
        c.setPassword(password);
        c.setName(name);
        c.setEmail(email);
        c.setPhoneNumber(phoneNumber);
        c.setAddress(address);
        return customerRepository.save(c);
    }
 */
}
