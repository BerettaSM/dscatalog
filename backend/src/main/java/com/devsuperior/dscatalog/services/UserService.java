package com.devsuperior.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.domain.dto.RoleDTO;
import com.devsuperior.dscatalog.domain.dto.UserDTO;
import com.devsuperior.dscatalog.domain.entities.Role;
import com.devsuperior.dscatalog.domain.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDTO::from);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::from)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public UserDTO save(UserDTO dto) {
        User user = new User();
        copyDtoToEntity(dto, user);
        User saved = userRepository.save(user);
        return UserDTO.from(saved);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        User user = userRepository.getReferenceById(id);
        copyDtoToEntity(dto, user);
        User updated = userRepository.save(user);
        return UserDTO.from(updated);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e, HttpStatus.CONFLICT);
        }
    }

    private final void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.getRoles().clear();
        entity.addRoles(
            dto.getRoles().stream()
                .map(RoleDTO::getId)
                .map(roleRepository::getReferenceById)
                .toArray(Role[]::new));
    }

}
