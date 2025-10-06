<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">EasyAdmin</h1>

<h4 align="center">基于SpringBoot+Vue前后端分离的快速开发平台</h4>

<p align="center">
	<a href="https://github.com/jixer666/easyadmin/stargazers"><img src="https://img.shields.io/github/stars/jixer666/quickpost?style=social" alt="Stars"></a>
	<a href="https://github.com/jixer666/easyadmin/forks"><img src="https://img.shields.io/github/forks/jixer666/quickpost?style=social" alt="Forks"></a>
	<a href="https://github.com/jixer666/easyadmin/issues"><img src="https://img.shields.io/github/issues/jixer666/quickpost" alt="Issues"></a>
	<a href="https://github.com/jixer666/easyadmin/blob/main/LICENSE"><img src="https://img.shields.io/github/license/jixer666/quickpost" alt="License"></a>
</p>

## 🌟 平台简介

EasyAdmin 是一个基于 SpringBoot + Vue2 前后端分离的快速开发平台，致力于为开发者提供高效、规范、易扩展的后台管理系统解决方案。平台集成了丰富的功能组件和最佳实践，让开发者能够专注于业务逻辑，快速构建企业级应用。

### ✨ 核心特性

- 权限控制
- 多种登录方式
- 多种存储方式


## 🛠️ 快速开始

### 环境要求
- JDK 1.8+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+
- Maven 3.6+

### 后端启动
```bash
# 克隆项目
git clone https://github.com/jixer666/easyadmin.git

# 导入数据库
mysql -u root -p < sql/easyadmin.sql

# 修改配置
# 配置 application.yml 中的数据库和Redis连接

# 启动项目
mvn spring-boot:run
```

### 前端启动
```bash
# 进入前端目录
cd easyadmin-web

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build
```

## 📁 项目结构

```
easyadmin/
├── easyadmin-common/          # 公共模块
├── easyadmin-system/          # 系统核心模块
└── easyadmin-web/             # 前端代码文件
```

## 🔧 配置说明

主要配置文件说明：
- `application.yml` - 主配置文件
- `application-dev.properties` - 开发环境配置
- `application-prod.properties` - 生产环境配置

## 🤝 参与贡献

我们欢迎任何形式的贡献！

1. Fork 本仓库
2. 创建 Feature 分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系我们

- 项目主页：https://github.com/jixer666/easyadmin
- 问题反馈：https://github.com/jixer666/easyadmin/issues
- 邮箱：jixer666@163.com

## 🙏 致谢

感谢以下开源项目：
- RuoYi-Vue
- Spring Boot
- Vue.js
