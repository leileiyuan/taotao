# taotao
淘淘商城，个人学习 </br>
技术栈、工具服务：</br>
Spring、SpringMVC、Mybatis</br>
JSP、JSTL、jQuery、jQuery plugin、EasyUI、KindEditor（富文本编辑器）、CSS+DIV、freemarker</br>
Redis（缓存服务器）</br>
Lucene、Solr（搜索）</br>
httpclient（调用系统服务）</br>
Mysql</br>
MyCat mysql分布式集群解决方案</br>
LVS+Nginx（web服务器）</br>
Quartz（定时任务）</br>
ActiveMQ（消息队列）</br>
Alibaba Dubbo服务接口集群</br>
Fastdfs 分布式文件系统（图片服务器）</br>


1、淘淘上传采用当前最流行的ssm（springmvc+spring+mybatis）框架开发，是当前电商网站首选的技术架构。</br>
2、系统后台使用jsp+easyUI作为视图层，操作简便用户体验好，使用KindEditor作为富文本编辑器操作简便界面美观。</br>
3、系统前台使用freemarker做静态化页面来提高系统的性能，以应对大规模的用户量的并发。</br>
4、使用HttpClient以及Restful风格的接口来实现各个系统之间的相互通信。</br>
5、使用Lvs+Nginx服务器来处理图片、静态页面以及系统之间的负载均衡，可以应付大并发的压力。</br>
6、使用Redis集群做缓存服务器，提高系统的响应速度。</br>
7、使用Solr集群提高商品信息的查询服务，提高系统的查询速度以及准确率，极大提高了用户体验。</br>
8、使用FastDFS分布式文件系统作为图片服务器。实现图片的分布式存储。</br>
9、使用Alibaba Dubbo作为SOA服务化治理方案的核心框架。系统之间使用ActiveMQ消息队列实现消息服务。</br>
10、后台数据库采用mysql数据库，使用mycat作为中间件实现主从复制、分库分表以实现大数据量的存储，是当今电商行业主流解决方案。</br>
11、整个项目采用最流行的Maven来管理项目，达到项目的标准化，易于项目的构建。</br>
12、使用git-github来管理项目的代码和文档。</br>
13、使用hudson来管理项目开发过程中的持续集成。</br>


拓扑结构：
![image](https://github.com/leileiyuan/taotao/blob/master/拓扑结构.jpg)
