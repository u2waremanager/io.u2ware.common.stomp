package io.u2ware.common.stomp.server.api.users;

import io.u2ware.common.stomp.server.domain.User;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface UserRepository extends RestfulJpaRepository<User,String>{

}
