package com.cy.lister;

import com.cy.common.R;
import com.cy.service.PortInit;
import com.cy.util.SerialPortUtil;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@RequestMapping("/msg")
@RestController
@Component
public class MyLister implements SerialPortEventListener
{

    private static final Logger logger = LoggerFactory.getLogger(MyLister.class);
    private static String datas;
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] bytes = new byte[0];
                try {
                    bytes = SerialPortUtil.getSerialPortUtil().readFromPort(PortInit.serialPort);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                String byteStr = new String(bytes, 0, bytes.length).trim();
                try {
                    byteStr = new String(bytes, "gbk");
                    datas = byteStr;

                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("===========start===========");
                System.out.println(new Date() + "【读到的字符串】：-----" + byteStr);
                System.out.println(new Date() + "【字节数组转16进制字符串】：-----" + printHexString(bytes));
                System.out.println("===========end===========");
                break;
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                logger.error("输出缓冲区已清空");
                break;
            case SerialPortEvent.CTS:
                logger.error("清除待发送数据");
                break;
            case SerialPortEvent.DSR:
                logger.error("待发送数据准备好了");
                break;
            case SerialPortEvent.BI:
                logger.error("与串口设备通讯中断");
                break;
            default:
                break;
        }
    }
    /**
     * 字节数组转16进制字符串
     *
     * @param b 字节数组
     * @return 16进制字符串
     */
    public static String printHexString(byte[] b)
    {
        StringBuilder sbf = new StringBuilder();
        for (byte value : b)
        {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sbf.append(hex.toUpperCase()).append(" ");
        }
        return sbf.toString().trim();
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param hex 16进制字符串
     * @return 字节数组
     */
    public static byte[] hex2byte(String hex)
    {
        if (!isHexString(hex))
        {
            return null;
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++)
        {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    /**
     * 校验是否是16进制字符串
     *
     * @param hex
     * @return
     */
    public static boolean isHexString(String hex)
    {
        if (hex == null || hex.length() % 2 != 0)
        {
            return false;
        }
        for (int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            if (!isHexChar(c))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验是否是16进制字符
     *
     * @param c
     * @return
     */
    private static boolean isHexChar(char c)
    {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }
    @PostMapping("/getMsg")
    @CrossOrigin("*")
    public R<String> sendmsg(){
        if(datas == null || datas.trim().isEmpty()){

            return R.error("");
        }
        else{
            String pps = datas;
            datas = null;
            return R.success(pps);
        }
    }
}
