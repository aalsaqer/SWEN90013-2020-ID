package com.uom.idecide.service;

import com.uom.idecide.dao.UserDao;

import com.uom.idecide.pojo.User;
import com.uom.idecide.util.IdWorker;
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
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) throws Exception {
		User userInDb = userDao.findByEmail(user.getEmail());
		if(userInDb!=null && userInDb.getUserId()!=null){
			throw new Exception("This email address has been used");
		}

		user.setUserId(idWorker.nextId()+"");
		//密码加密
		user.setPassword(encoder.encode(user.getPassword()));
		userDao.save(user);
	}


	public User login(String email, String password) throws Exception {
		User userLogin = userDao.findByEmail(email);
		//查看数据库中的密码和用户输入的密码匹配是否相同
		if(userLogin!=null && encoder.matches(password,userLogin.getPassword())){	//prevent from null pointer
			//login successful
			return userLogin;
		}else{
			throw new Exception("wrong email or password");
		}
	}

	/**
	 * 查询全部列表
	 * admin权限
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	 * 查询全部列表
	 * admin权限
	 */
	public Page<User> findAllWithPagination(int page, int size) {
		//DB从0开始，页面从1开始
		Pageable pageable = PageRequest.of(page-1,size);
		return userDao.findAll(pageable);
	}


	/**
	 * 根据ID查询实体
	 * admin权限
	 * 当前用户权限
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 修改
	 * @当前用户权限
	 */
	public void updateById(User user) {
		userDao.save(user);
	}


	public void deleteById(String id) {
		userDao.deleteById(id);
	}


	/**
	 * 动态条件构建
	 * @param
	 * @return
	 */
/*	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("userId")!=null && !"".equals(searchMap.get("userId"))) {
                	predicateList.add(cb.like(root.get("userId").as(String.class), "%"+(String)searchMap.get("userId")+"%"));
                }
                // password
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // gender
                if (searchMap.get("gender")!=null && !"".equals(searchMap.get("gender"))) {
                	predicateList.add(cb.like(root.get("gender").as(String.class), "%"+(String)searchMap.get("gender")+"%"));
                }
				// partnerGender
				if (searchMap.get("partnerGender")!=null && !"".equals(searchMap.get("partnerGender"))) {
					predicateList.add(cb.like(root.get("partnerGender").as(String.class), "%"+(String)searchMap.get("partnerGender")+"%"));
				}
                // email
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
				// phone
				if (searchMap.get("phoneNumber")!=null && !"".equals(searchMap.get("phoneNumber"))) {
					predicateList.add(cb.like(root.get("phoneNumber").as(String.class), "%"+(String)searchMap.get("phoneNumber")+"%"));
				}
				// phone
				if (searchMap.get("postcode")!=null && !"".equals(searchMap.get("postcode"))) {
					predicateList.add(cb.like(root.get("postcode").as(String.class), "%"+(String)searchMap.get("postcode")+"%"));
				}
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}*/
}
