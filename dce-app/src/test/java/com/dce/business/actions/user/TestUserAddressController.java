package com.dce.business.actions.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dce.test.BaseTest;

public class TestUserAddressController extends BaseTest {
	
    
    @Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc; 
    
    @Before  
    public void setup() {   
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build(); 
    } 
    
  
    
    @Test  
    @Rollback(false)  
	public void testAddAddress() throws Exception {  
    	MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/address/addAddress.do").param("userId", "1").param("username", "zhangsan").param("userphone", "139456").param("address", "hunan").param("addressDetails", "addressDetails23233333")).andReturn();
    	if(result != null ){
    		System.out.println(result.getResponse().getContentAsString());
    	}
                //.andExpect(MockMvcResultMatchers.view().name("user/view"))  
                //andExpect(MockMvcResultMatchers.model().attributeExists("user"))  
                //.andDo(MockMvcResultHandlers.print())  
                //.andReturn();  
        //Assert.assertNotNull(result.getModelAndView().getModel().get("user")); 
	} 
    
    
}