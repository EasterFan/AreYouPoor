//package com.easter;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//import reap.ocal.api.TestBase;
//
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//public abstract class IntegrationBaseTest {
//
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected WebApplicationContext wac;
//
//    @BeforeEach
//    public void setup() {
//        setupMockMvc();
//    }
//
//    private void setupMockMvc() {
//        mockMvc = webAppContextSetup(wac)
//                .addFilter((request, response, chain) -> {
//                    response.setCharacterEncoding("UTF-8");
//                    chain.doFilter(request, response);
//                })
//                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
//    }
//}
