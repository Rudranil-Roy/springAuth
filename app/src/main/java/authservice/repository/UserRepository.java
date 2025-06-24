package authservice.repository;

import authservice.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<UserInfo, String> {

    public UserInfo findByUsername(String username);

}
