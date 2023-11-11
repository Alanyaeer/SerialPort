# 串口实验

## 描述

* 基于Springboot + vue的串口通信

## jar包

选择jdk8

[Java Downloads | Oracle](https://www.oracle.com/java/technologies/downloads/#java8-windows)

## 下载RXTX包

[RXTX for Java (fizzed.com)](http://fizzed.com/oss/rxtx-for-java)

往下翻 

![image-20231112002720857](https://cdn.jsdelivr.net/gh/Alanyaeer/ImgSummary@master/img/202311120028604.png)

> 如果你是window系统选择第一个就好了



最后你会得到这三个文件

![image-20231112003004624](https://cdn.jsdelivr.net/gh/Alanyaeer/ImgSummary@master/img/202311120030653.png)



![image-20231112003136809](https://cdn.jsdelivr.net/gh/Alanyaeer/ImgSummary@master/img/202311120039689.png)

把 第二个和第三个 文件放入到以 你刚刚下载的jdk为根目录 的bin文件夹下面

把第一个文件放入到以 你刚刚下载的jdk为根目录 的lib 下面的ext文件夹里面

然后程序就可以正常跑动了， 默认是`COM1`

访问网址是http://localhost:9393/dist/index.html#/communite

