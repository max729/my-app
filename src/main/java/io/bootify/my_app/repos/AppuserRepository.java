package io.bootify.my_app.repos;

import io.bootify.my_app.model.Appuser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppuserRepository extends JpaRepository<Appuser, Long> {
    Appuser findAppuserByUsername(String username);
}
