package io.bootify.my_app.service;

import io.bootify.my_app.model.Appuser;
import io.bootify.my_app.repos.AppuserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AppuserService {

    private final AppuserRepository userRepository;

    public AppuserService(final AppuserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Appuser> findAll() {
        return userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toList());
    }

    public Appuser get(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Appuser getUserByUsername(final String name) {
        return userRepository.findAppuserByUsername(name);
                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final Appuser user) {
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final Appuser user) {
        userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }



}
