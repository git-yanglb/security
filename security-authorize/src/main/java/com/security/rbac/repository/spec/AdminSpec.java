package com.security.rbac.repository.spec;


import com.security.rbac.domain.Admin;
import com.security.rbac.dto.AdminCondition;
import com.security.rbac.repository.support.ImoocSpecification;
import com.security.rbac.repository.support.QueryWraper;

/**
 * @author TiHom
 *
 */
public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {

	private static final long serialVersionUID = 1L;

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
