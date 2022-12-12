package com.example.agrochillingapi.sellers.service;

import com.example.agrochillingapi.security.domain.model.entity.Role;
import com.example.agrochillingapi.security.domain.model.enumeration.Roles;
import com.example.agrochillingapi.security.domain.persistence.RoleRepository;
import com.example.agrochillingapi.security.domain.service.communication.AuthenticateRequest;
import com.example.agrochillingapi.sellers.domain.model.entity.Seller;
import com.example.agrochillingapi.sellers.domain.persistence.SellerRepository;
import com.example.agrochillingapi.sellers.domain.service.SellerService;
import com.example.agrochillingapi.sellers.domain.service.communication.AuthenticateSellerResponse;
import com.example.agrochillingapi.sellers.domain.service.communication.RegisterSellerRequest;
import com.example.agrochillingapi.sellers.domain.service.communication.RegisterSellerResponse;
import com.example.agrochillingapi.sellers.middleware.JwtHandlerSeller;
import com.example.agrochillingapi.sellers.middleware.SellerDetailsImpl;
import com.example.agrochillingapi.sellers.resource.AuthenticateSellerResource;
import com.example.agrochillingapi.sellers.resource.SellerResource;
import com.example.agrochillingapi.shared.exception.ResourceNotFoundException;
import com.example.agrochillingapi.shared.exception.ResourceValidationException;
import com.example.agrochillingapi.shared.mapping.EnhancedModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {

    private static final Logger logger = LoggerFactory.getLogger(SellerServiceImpl.class);

    private static final String ENTITY = "Seller";
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private Validator validator;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtHandlerSeller handler;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EnhancedModelMapper mapper;

    @Override
    public List<Seller> getAll() {
        return sellerRepository.findAll();
    }

    @Override
    public Page<Seller> getAll(Pageable pageable) {
        return sellerRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> authenticate(AuthenticateRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = handler.generateToken(authentication);

            SellerDetailsImpl userDetails = (SellerDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            AuthenticateSellerResource resource = mapper.map(userDetails, AuthenticateSellerResource.class);
            resource.setRoles(roles);
            resource.setToken(token);

            AuthenticateSellerResponse response = new AuthenticateSellerResponse(resource);

            return ResponseEntity.ok(response.getResource());


        } catch (Exception e) {
            AuthenticateSellerResponse response = new AuthenticateSellerResponse(String.format("An error occurred while authenticating: %s", e.getMessage()));
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> register(RegisterSellerRequest request) {
        if (sellerRepository.existsByEmail(request.getEmail())) {
            AuthenticateSellerResponse response = new AuthenticateSellerResponse("Email is already used.");
            return ResponseEntity.badRequest()
                    .body(response.getMessage());
        }

        try {

            Set<String> rolesStringSet = request.getRoles();
            Set<Role> roles = new HashSet<>();

            if (rolesStringSet == null) {
                roleRepository.findByName(Roles.ROLE_SELLER)
                        .map(roles::add)
                        .orElseThrow(() -> new RuntimeException("Role not found."));
            } else {
                rolesStringSet.forEach(roleString ->
                        roleRepository.findByName(Roles.valueOf(roleString))
                                .map(roles::add)
                                .orElseThrow(() -> new RuntimeException("Role not found.")));
            }

            logger.info("Roles: {}", roles);

            Seller user = new Seller()
                    .withFirst_name(request.getFirst_name())
                    .withLast_name(request.getLast_name())
                    .withAge(request.getAge())
                    .withEmail(request.getEmail())
                    .withPassword(encoder.encode(request.getPassword()))
                    .withPhone(request.getPhone())
                    .withFarm_address(request.getFarm_address())
                    .withRoles(roles);


            sellerRepository.save(user);
            SellerResource resource = mapper.map(user, SellerResource.class);
            RegisterSellerResponse response = new RegisterSellerResponse(resource);
            return ResponseEntity.ok(response.getResource());

        } catch (Exception e) {

            RegisterSellerResponse response = new RegisterSellerResponse(e.getMessage());
            return ResponseEntity.badRequest().body(response.getMessage());

        }
    }

    @Override
    public Seller getById(Long directorId) {
        return sellerRepository.findById(directorId)
                .orElseThrow( () -> new ResourceNotFoundException(ENTITY, directorId));
    }

    @Override
    public Seller update(Long directorId, Seller director) {
        Set<ConstraintViolation<Seller>> violations = validator.validate(director);
        if(!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return sellerRepository.findById(directorId).map( data ->
                sellerRepository.save(
                        data.withFirst_name(director.getFirst_name())
                                .withLast_name(director.getLast_name())
                                .withAge(director.getAge())
                                .withEmail(director.getEmail())
                                .withPhone(director.getPhone()))
                                .withFarm_address(director.getFarm_address())
        ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, directorId));
    }

    @Override
    public ResponseEntity<?> delete(Long directorId) {
        return sellerRepository.findById(directorId).map(data -> {
            sellerRepository.delete(data);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, directorId));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller user = sellerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));
        return SellerDetailsImpl.build(user);
    }
}
