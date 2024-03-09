package argus.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:test-context.xml",
//		"classpath:/META-INF/spring/applicationContext.xml" })
//@Transactional
//@TransactionConfiguration(defaultRollback=true)
public class InsuranceDaoTest
{
    @Autowired
	// private InsuranceDao insuranceDao;

    @Test
    public void testFindById()
    {
		// Insurance insurance = insuranceDao.findById(0l);

		// Assert.assertEquals("insurance1", insurance.getName());
//        Assert.assertEquals("john.smith@mailinator.com", user.getEmail());
//        Assert.assertEquals("2125551212", user.getPhoneNumber());
		// return;
    }

    @Test
    public void testFindByName()
    {
		// Insurance insurance = insuranceDao.findByName("insurance1");

		// Assert.assertEquals("insurance1", insurance.getName());
//        Assert.assertEquals("john.smith@mailinator.com", user.getEmail());
//        Assert.assertEquals("2125551212", user.getPhoneNumber());
		// return;
    }

    @Test
    public void testAddInsurance()
    {
		// Insurance insurance = new Insurance();
		// insurance.setName("insurance1");
		// insurance.setDesc("test descripton for insurance1");
		// insurance.setType("AR");
		// insurance.setDeleted(false);
		// Map<String, String> orderClauses = new HashMap<String,String>();
		// Map<String, String> whereClauses = null;
		// orderClauses.put("orderBy", "name");
    	//insuranceDao.addInsurance(insurance);
		// Long id = insurance.getId();
        //Assert.assertNotNull(id);

        //Assert.assertEquals(2, insuranceDao.findAll(orderClauses, whereClauses).size());
        //Insurance newInsurance = insuranceDao.findById(id);

        //Assert.assertEquals("insurance1", newInsurance.getName());
//        Assert.assertEquals("jane.doe@mailinator.com", newUser.getEmail());
//        Assert.assertEquals("2125552121", newUser.getPhoneNumber());
		// return;
    }

    @Test
    public void testFindAllOrderedByName()
    {
		// Insurance insurance = new Insurance();
		// insurance.setName("insurance1");
		// insurance.setType("AR");
		// insurance.setDeleted(false);
//        user.setPhoneNumber("2125552121");
    //	insuranceDao.addInsurance(insurance);

		// Map<String, String> orderClauses = new HashMap<String,String>();
		// Map<String, String> whereClauses = null;
		// orderClauses.put("orderBy", "name");

		// List<Insurance> insurances = insuranceDao.findAll(orderClauses,
		// whereClauses);
       // Assert.assertEquals(2, insurances.size());
		// Insurance newInsurance = insurances.get(0);

       // Assert.assertEquals("insurance1", newInsurance.getName());
//        Assert.assertEquals("jane.doe@mailinator.com", newUser.getEmail());
//        Assert.assertEquals("2125552121", newUser.getPhoneNumber());
		// return;
    }
}
