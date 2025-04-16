emsp
=============================

emsp项目，使用传统MVC风格的项目代码布局，Maven版

## 技术栈与组件
| 组件   | 技术选型                             | 说明 |
|------|----------------------------------|----  |
| 框架   | Spring Boot 3.x                  |快速构建 RESTful API |
| 数据库  | MySQL                            |  |
| ORM  | MyBatisPlus                      |  |
| 数据校验 | Hibernate Validator              |  |
| 分页查询 | PageHelper                       | 分页和排序 |
| 测试   | JUnit 5 + Idea HttpClient Plugin | 单元测试和 API 测试|
| 容器化  | Docker + Docker Compose  | 本地环境与云部署 |
| CI/CD  | GitHub Actions  | 自动化构建、测试、部署 |

## RDBMS 设计
参考 sql 文件夹init.sql

## RESTful API 设计
参数请参考[swagger](http://101.201.46.166:8080/swagger-ui/index.html#/)

| 操作      | HTTP 方法                         |  路径                 | 说明                            |
|---------|---------------------------------|---------------------|-------------------------------|
| 创建账号    | POST                            | /api/account/create |                               |
| 修改账号状态  | POST                            | /api/account/status | 只能修改两种状态：activiated,inactived |
| 查询账号及卡片 | GET                             | /api/account/list | PageHelper分页查询                |
| 创建卡片    |   POST            | /api/card/create |                               |
| 修改卡片状态  | POST                      | /api/card/status | 只能修改两种状态：activiated,inactived |
| 分配卡     | JUnit 5 + Idea HttpClient Plugin | /api/card/assign       |                               |
## 业务架构设计
1. 横切日志，非功能业务、功能业务解耦
2. 事件驱动
3. 统一响应格式