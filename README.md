emsp
=============================

emsp项目，使用传统MVC风格的项目代码布局，Maven版
## 需求反解
1.系统以管理员的视角操作账号、卡，实现逻辑自洽。不开放注册。提供管理员登陆入口。不登录不能做任何操作，包括swagger。
2.账号、卡片修改状态限制激活（Activated）、冻结（Deactivated），账号修改状态联动卡片的冻结、解冻。
3.提供账号、卡片分页查询（按最后更新时间倒序）
## 技术栈与组件
| 组件    | 技术选型                             | 说明                |
|-------|----------------------------------|-------------------|
| 框架    | Spring Boot 3.x                  | 快速构建 RESTful API  |
| 数据库   | MySQL                            |                   |
| ORM   | MyBatisPlus                      |                   |
| 数据校验  | Hibernate Validator              |                   |
| 分页查询  | PageHelper                       | 分页和排序             |
| 测试    | JUnit 5 + Idea HttpClient Plugin | 单元测试和 API 测试      |
| 容器化   | Docker + Docker Compose          | 本地环境与云部署          |
| CI/CD | GitHub Actions                   | 自动化构建、测试、部署       |
| 安全    | Spring Security                  | 默认行为，基本功能，未做个性化定制 |

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
4. 横切日志，非功能业务、功能业务解耦
5. 全局异常处理兜底
## TODO:
1. 增加缓存层
2. 增加i18n
3. 细粒度权限控制