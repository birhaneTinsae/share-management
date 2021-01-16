package com.enat.sharemanagement.user;

import com.enat.sharemanagement.exceptions.EntityNotFoundException;
import com.enat.sharemanagement.exceptions.PasswordMisMatchException;
import com.enat.sharemanagement.exceptions.UserAlreadyExistsException;
import com.enat.sharemanagement.role.Role;
import com.enat.sharemanagement.role.RoleService;
import com.enat.sharemanagement.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements Common<User, User,Long> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;


    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Username", username));
    }

    public User passwordRest(UserPasswordDTO u) {
        User user = userRepository.findByUsername(u.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Username", u.getUsername()));

        if (user.getPassword() != null) {
            String hashedPassword = passwordEncoder.encode(u.getNewPassword());
            if (passwordEncoder.matches(u.getOldPassword(), user.getPassword())) {
                user.setPassword(hashedPassword);
            } else {
                throw new PasswordMisMatchException("Incorrect old password " + u.getOldPassword());
            }

        }
        user.setFirstLogin(false);
        return userRepository.save(user);
    }

    @Override
    public User store(@Valid User user) {

        if (usernameExist(user.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with username '" + user.getUsername() + "'");
        }
        List<Role> roleList = null;
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            roleList = user.getRoles().stream().map(role -> roleService.show(role.getId()))
                    .collect(Collectors.toList());
            user.setRoles(roleList);
        } else {
            Role role = roleService.getRoleByName("Reseller");
            user.setRoles(Collections.singletonList(role));
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFirstLogin(true);
        user.setEnabled(!user.isEnabled() || user.isEnabled());
        user.setActive(!user.isActive() || user.isActive());
        user.setAccountNonExpired(!user.isAccountNonExpired() || user.isAccountNonExpired());
        user.setAccountNonLocked(!user.isAccountNonLocked() || user.isAccountNonLocked());
        user.setCredentialsNonExpired(!user.isCredentialsNonExpired() || user.isCredentialsNonExpired());


        return userRepository.save(user);
    }

    @Override
    public Iterable<User> store(List<@Valid User> t) {
        return null;
    }

    @Override
    public User show(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", String.valueOf(id)))
                ;
    }

    public User update(Long id, User userDto) {
        User user = show(id);
//        if (userDto.getRoles() != null && userDto.getRoles().size() > 0) {
//            List<Role> roleList = userDto.getRoles().stream().map(role -> roleService.show(role.getId()))
//                    .collect(Collectors.toList());
//            user.setRoles(roleList);
//        } else {
//            ArrayList<Role> roles = new ArrayList<>();
//            Role role = roleService.getRoleByName("Reseller");
//            roles.add(role);
//            user.setRoles(roles);
//        }

        user.setFullName(userDto.getFullName());

        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setFirstLogin(true);
        }
        user.setEnabled(userDto.isEnabled());
        user.setActive(userDto.isActive());
        user.setAccountNonExpired(userDto.isAccountNonExpired());
        user.setAccountNonLocked(userDto.isAccountNonLocked());
        user.setCredentialsNonExpired(userDto.isCredentialsNonExpired());

        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        User user = show(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    public Page<User> getAll(Pageable pageable) {

        return userRepository.findAllByDeletedAtNull(pageable);
    }


}
