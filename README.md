emsp
=============================

emsp项目，使用传统MVC风格的项目代码布局，Maven版
## 需求反解
1. 系统以管理员的视角操作账号、卡，实现逻辑自洽。不开放注册。提供管理员登陆入口。不登录不能做任何操作，包括swagger。
2. 账号、卡片修改状态限制激活（Activated）、冻结（Deactivated），账号修改状态联动卡片的冻结、解冻。
3. 提供账号、卡片分页查询（按最后更新时间倒序）
## 技术栈与组件
| 组件      | 技术选型                             | 说明                         |
|---------|----------------------------------|----------------------------|
| 框架      | Spring Boot 3.x                  | 快速构建 RESTful API           |
| 数据库     | MySQL                            |                            |
| ORM     | MyBatisPlus                      |                            |
| 数据校验    | Hibernate Validator              |                            |
| 分页查询    | PageHelper                       | 分页和排序                      |
| 测试      | JUnit 5 + Idea HttpClient Plugin | 单元测试和 API 测试               |
| 容器化     | Docker + Docker Compose          | 本地环境与云部署                   |
| CI/CD   | GitHub Actions                   | 自动化构建、测试、部署                |
| 安全认证与授权 | Spring Security                  | 默认登录行为，基本功能，未做个性化定制，仅为效果演示 |
 | 缓存与分布式锁 | Redis                            | redisson                   |
## 架构分层
项目采用传统的MVC分层架构，主要分为以下几层：

- Controller层 ：处理HTTP请求，调用Service层，返回统一响应格式。
- Service层 ：实现核心业务逻辑，调用DAO层进行数据操作。
- DAO层 ：负责与数据库交互，使用MyBatis Plus进行数据访问。
- Model层 ：包含实体类、DTO、VO等数据模型。
- Configuration层 ：配置类，如Spring Security配置、Redis配置等。
- Global层 ：全局异常处理、统一响应格式等。
- Common层 ：公共工具类、异常类、常量等。
## 模块划分
项目采用多模块设计，主要模块包括：

- emsp-start ：启动模块，包含Controller、Service、Configuration等。
- emsp-model ：数据模型模块，包含实体类、DTO、VO等。
- emsp-dao ：数据访问模块，包含MyBatis Plus的Mapper接口。
- emsp-service ：业务逻辑模块，包含Service接口和实现类。
- emsp-common ：公共模块，包含工具类、异常类、常量等。
## 核心流程
1. 请求流程 ：

   - 客户端发起HTTP请求 → Controller层接收请求 → 调用Service层处理业务逻辑 → 调用DAO层访问数据库 → 返回统一响应格式。
2. 安全流程 ：

   - 用户登录 → Spring Security进行认证 → 根据角色授权访问资源 → 未授权时触发 CustomAccessDeniedHandler 。
3. 异常流程 ：
   - 发生异常 → GlobalExceptionHandler 捕获异常 → 返回统一错误响应。
## 架构
![架构](docs/images/Architecture.png)
## RDBMS 设计
参考 sql 文件夹init.sql

## RESTful API 设计
参数请参考[swagger](http://101.201.46.166:8080/swagger-ui/index.html#/)

| 操作      | HTTP 方法 | 路径                  | 说明                            |
|---------|---------|---------------------|-------------------------------|
| 创建账号    | POST    | /api/accounts       |                               |
| 修改账号状态  | PATCH   | /api/account/status | 只能修改两种状态：activiated,inactived |
| 查询账号及卡片 | GET     | /api/accounts       | PageHelper分页查询                |
| 创建卡片    | POST    | /api/cards          |                               |
| 修改卡片状态  | PATCH    | /api/card/status    | 只能修改两种状态：activiated,inactived |
| 分配卡     | PATCH    | /api/card/assign    |                               |
## 业务架构设计
1. 多模块： 模块化设计、依赖统一管理 ，灵活扩展
2. 事件驱动
3. 统一响应格式
4. 横切日志，非功能业务、功能业务解耦；
5. 全局异常处理兜底
6. i18n 支持国际化：错误码、验证、swagger
7. 横切缓存，按需注解
8. 权限体系：用户角色分级，细粒度权限控制(方法级)
## TODO:
1.more unit tests
