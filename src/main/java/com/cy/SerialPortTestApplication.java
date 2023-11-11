package com.cy;

import com.cy.lister.MyLister;
import com.cy.service.PortInit;
import com.cy.util.SerialPortUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

import javax.annotation.PreDestroy;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class SerialPortTestApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
{

    public static void main(String[] args)
    {
        SpringApplication.run(SerialPortTestApplication.class, args);
    }

    @PreDestroy
    public void destroy()
    {
        //关闭应用前 关闭端口
        SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
        if(serialPortUtil != null){
            serialPortUtil.removeListener(PortInit.serialPort, new MyLister());
            serialPortUtil.closePort(PortInit.serialPort);
        }
    }
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(9393);
    }
}
