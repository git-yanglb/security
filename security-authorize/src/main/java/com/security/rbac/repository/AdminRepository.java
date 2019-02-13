package com.security.rbac.repository;


import org.springframework.stereotype.Repository;

import com.security.rbac.domain.Admin;

/**
 * @author TiHom
 *
 */
@Repository
public interface AdminRepository extends ImoocRepository<Admin> {

	Admin findByUsername(String username);

}
