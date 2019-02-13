package com.security.rbac.repository;


import org.springframework.stereotype.Repository;

import com.security.rbac.domain.Resource;

/**
 * @author TiHom
 *
 */
@Repository
public interface ResourceRepository extends ImoocRepository<Resource> {

	Resource findByName(String name);

}
