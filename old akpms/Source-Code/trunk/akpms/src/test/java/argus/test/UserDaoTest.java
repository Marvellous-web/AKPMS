package argus.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import argus.domain.Department;
import argus.domain.Role;
import argus.domain.User;
import argus.repo.user.UserDao;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:test-context.xml",
//"classpath:/META-INF/spring/applicationContext.xml"})
//@Transactional
//@TransactionConfiguration(defaultRollback=true)
public class UserDaoTest
{
    @Autowired
    private UserDao userDao;

    @Test
    public void testFindById()
    {
    	try {
    		User user = userDao.findById(0l,false);

            Assert.assertEquals("John Smith", user.getFirstName());
            Assert.assertEquals("john.smith@mailinator.com", user.getEmail());
            Assert.assertEquals("2125551212", user.getContact());
		} catch (Exception e) {
			// TODO: handle exception
		}

        return;
    }

    @Test
    public void testFindByEmail()
    {
    	try {
    		User user = userDao.findByEmail("john.smith@mailinator.com",true);

            Assert.assertEquals("John Smith", user.getFirstName());
            Assert.assertEquals("john.smith@mailinator.com", user.getEmail());
            Assert.assertEquals("2125551212", user.getContact());
		} catch (Exception e) {
			// TODO: handle exception
		}

        return;
    }

    @Test
    public void testRegister()
    {
        User user = new User();
        user.setEmail("jane.doe@mailinator.com");
        user.setFirstName("Jane Doe");
        user.setLastName("ab");
        user.setContact("2125552121");

        Role r1 = new Role();
        r1.setId(new Long(1));
        user.setRole(r1);

        List<Department> deptList = new ArrayList<Department>();

        Department dept = new Department();
        dept.setId(new Long(1));
        deptList.add(dept);
        user.setDepartments(deptList);

        try {
        	userDao.register(user);
            Long id = user.getId();
            Assert.assertNotNull(id);

            Assert.assertEquals(2, userDao.findAll("firstName").size());
            User newUser = userDao.findById(id,false);

            Assert.assertEquals("Jane Doe", newUser.getFirstName());
            Assert.assertEquals("jane.doe@mailinator.com", newUser.getEmail());
            Assert.assertEquals("2125552121", newUser.getContact());
		} catch (Exception e) {
			// TODO: handle exception
		}

        return;
    }

    @Test
    public void testFindAllOrderedByName()
    {
        User user = new User();
        user.setEmail("jane.doe@mailinator.com");
        user.setFirstName("Jane Doe");
        user.setLastName("ab");
        user.setContact("2125552121");

        Role r1 = new Role();
        r1.setId(new Long(1));
        user.setRole(r1);

        List<Department> deptList = new ArrayList<Department>();

        Department dept = new Department();
        dept.setId(new Long(1));
        deptList.add(dept);
        user.setDepartments(deptList);

        try{
        userDao.register(user);

        List<User> users = userDao.findAll("firstName");
        Assert.assertEquals(2, users.size());
        User newUser = users.get(0);

        Assert.assertEquals("Jane Doe", newUser.getFirstName());
        Assert.assertEquals("jane.doe@mailinator.com", newUser.getEmail());
        Assert.assertEquals("2125552121", newUser.getContact());
        }catch (Exception e) {
			// TODO: handle exception
		}
        return;
    }
}
