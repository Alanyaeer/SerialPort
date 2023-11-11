package com.cy.job;

import com.cy.common.ChangeDto;
import com.cy.common.R;
import com.cy.lister.MyLister;
import com.cy.service.PortInit;
import com.cy.util.SerialPortUtil;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/msg")
public class SendJob
{
    @Autowired
    public PortInit portInit;
    @Autowired
    public MyLister myLister;
    @PostMapping("/sendMsg")
    @CrossOrigin("*")
    public R sendmsg(@RequestBody String mes){
        try{
            // 获取到 mes
            int lastindex = mes.lastIndexOf("\"");
            int lastindex1 = mes.lastIndexOf("\"", lastindex - 1);
            String cur = mes.substring(lastindex1 + 1, lastindex);
            SerialPortUtil.getSerialPortUtil().sendToPort(PortInit.serialPort, cur.getBytes("gbk"));
            return R.success("send success");}
        catch(Exception e){
            return R.error("send failure");
        }
    }
    @CrossOrigin("*")
    @PostMapping("/updateport")
    public R modifysetting(@RequestBody ChangeDto changeDto){
        SerialPortUtil serialPortUtil = SerialPortUtil.getSerialPortUtil();
        SerialPort preserialPort = portInit.serialPort;
        if(preserialPort != null){
            try{
                serialPortUtil.closePort(preserialPort);
                serialPortUtil.removeListener(preserialPort, myLister);
            }catch(Exception e){
                e.printStackTrace();
                return R.error(e.getMessage());
            }
        }

        String portname = changeDto.getPortname();
        Integer baudrate = changeDto.getBaudrate();
        Integer databits = changeDto.getDatabits();
        Integer stopbits = changeDto.getStopbits();
        try{
            // 打开某个端口
            PortInit.serialPort = serialPortUtil.openPort(portname, baudrate, databits, 0, stopbits);
        }catch( PortInUseException e1){
//            System.out.printf(myLister + " "+ preserialPort);
            serialPortUtil.addListener(preserialPort, myLister);
            e1.printStackTrace();
            return R.error("PortInUseException");
        }catch( UnsupportedCommOperationException e2){
            serialPortUtil.addListener(preserialPort, myLister);
            e2.printStackTrace();
            return R.error("UnsupportedCommOperationException");
        } catch (NoSuchPortException e) {
            serialPortUtil.addListener(preserialPort, myLister);
            e.printStackTrace();
            return R.error("NoSuchPortException");
        }
        // 加入监听器
        serialPortUtil.addListener(PortInit.serialPort, myLister);
        System.out.println("hello ww");
        return R.success("change success");
    }
}
