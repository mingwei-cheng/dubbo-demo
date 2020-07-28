# Dubbo-Demo
模拟Dubbo的简单Demo

### 手写RPC框架的一个简单Demo
  * 使用了http和netty两种方式进行实现，项目内有三个module，分别是工具包、生产者和消费者。
  * 你可以在protocol中，新增其他方式实现。
  * 只需要在ProtocolFactory类中，新增一个case，然后在启动项目时增加一个启动方式的指定-Dtype=xxx（你的实现方式），即可。
  * 默认不填是以http方式启动，你也可以指定为-Dtype=netty，这样就会以netty的方式启动了。
  * 远程服务注册，使用了redis来进行，所以你需要在你的服务器中安装redis，然后在生产者和消费者module的utils.JedisUtils.class中，指定你的redis地址即可。
