package com.cy.service;

import com.cy.lister.MyLister;
import com.cy.util.SerialPortUtil;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import static com.cy.lister.MyLister.hex2byte;


@Component
public class PortInit implements ApplicationRunner
{
    public static SerialPort serialPort = null;
    @Autowired
    public MyLister myLister;
    // 配置文件中修改
    public String portName = "COM1";
    public Integer baudrate = 9600;

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public Integer getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(Integer baudrate) {
        this.baudrate = baudrate;
    }

    @Override
    public void run(ApplicationArguments args) {
        //TestA();
        //查看所有串口
        SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
        ArrayList<String> port = serialPortUtil.findPort();
        System.out.println("发现全部串口：" + port);
        System.out.println("打开指定portName:" + portName);

        //打开该对应portName名字的串口
        try {
            PortInit.serialPort = serialPortUtil.openPort(
                    portName,
                    baudrate,
                    SerialPort.DATABITS_8,
                    SerialPort.PARITY_NONE,
                    SerialPort.PARITY_ODD);
            serialPortUtil.addListener(PortInit.serialPort, myLister);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        } catch (PortInUseException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (UnsupportedCommOperationException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        //给对应的serialPort添加监听器

    }

}