package com.uom.idecide.service;

import com.uom.idecide.dao.AdminDao;
import com.uom.idecide.pojo.Admin;

import com.uom.idecide.util.IdWorker;
import com.uom.idecide.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * 增加
	 * @param admin
	 */
	public void add(Admin admin) throws Exception {
		Admin adminInDb = adminDao.findByEmail(admin.getEmail());
		if(adminInDb!=null && adminInDb.getAdminId()!=null){
			throw new Exception("This email address has been used");
		}
		admin.setAdminId( idWorker.nextId()+"" );
		//密码加密
		admin.setPassword(encoder.encode(admin.getPassword()));
		adminDao.save(admin);
	}


	public Admin login(String email, String password) {
		Admin adminLogin = adminDao.findByEmail(email);
		//2.然后那数据库中的密码和用户输入的密码匹配是否相同
		if(adminLogin!=null && encoder.matches(password,adminLogin.getPassword())){	//prevent from null pointer
			//login successful
			return adminLogin;
		}
		return null;
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public Page<Admin> findAllWithPagination(int page, int size) {
		//DB从0开始，页面从1开始
		Pageable pageable = PageRequest.of(page-1,size);
		return adminDao.findAll(pageable);
	}


	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Admin findById(String id) {
		return adminDao.findById(id).get();
	}

	/**
	 * 修改
	 * @param admin
	 */
	public void updateById(Admin admin) {
		adminDao.save(admin);
	}

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private JwtUtil jwtUtil;
	/**
	 * 删除：必须有admin角色才能删除
	 * @param id
	 */
	public void deleteById(String id) {
/*		//把token从header中取出
		String token = (String)request.getAttribute("claims_admin");
		if(token==null || "".equals(token)){
			throw new RuntimeException("权限不足！");
		}*/
		//只有通过上面的层层判断全部通过后，才能删除用户
		adminDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
/*	private Specification<Admin> createSpecification(Map searchMap) {

		return new Specification<Admin>() {

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("adminId")!=null && !"".equals(searchMap.get("adminId"))) {
                	predicateList.add(cb.like(root.get("adminId").as(String.class), "%"+(String)searchMap.get("adminId")+"%"));
                }
                // password
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // email
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

	}*/


}
