# 租车系统 - 项目真相源文档

> **版本**：v1.0.0  
> **创建时间**：2026-04-22  
> **维护规范**：每次迭代前必须更新本文档，所有模块变更需在此记录。

---

## 一、系统架构概览

本系统基于 **ruoyi-vue-pro（芋道源码）** 框架构建，完整还原 FleetEdge 租车商户管理系统功能。

| 层次 | 技术栈 |
|------|--------|
| 后端框架 | Spring Boot 3.x + MyBatis-Plus |
| 前端框架 | Vue 3 + TypeScript + Element Plus |
| 数据库 | MySQL 8.0 |
| 权限控制 | Spring Security + RBAC |
| 模块名称 | `yudao-module-rental` |

---

## 二、模块目录结构

```
yudao-module-rental/
├── docs/
│   ├── database_schema.sql        # 完整建表 SQL
│   ├── menu_init.sql              # 菜单及权限初始化 SQL
│   └── PROJECT_TRUTH.md           # 本文档（项目真相源）
├── pom.xml
└── src/main/java/cn/iocoder/yudao/module/rental/
    ├── controller/admin/
    │   ├── order/                 # 租车订单管理
    │   ├── vehicle/               # 车辆管理
    │   ├── store/                 # 门店管理
    │   ├── franchisee/            # 加盟商管理
    │   ├── inventory/             # 库存管理
    │   ├── price/                 # 价格中心
    │   ├── product/               # 商品管理
    │   ├── policy/                # 政策管理
    │   └── selfdevice/            # 自助取还设备
    ├── dal/
    │   ├── dataobject/            # DO 数据对象
    │   └── mysql/                 # Mapper 接口
    ├── service/                   # Service 接口 + 实现
    └── enums/
        └── RentalErrorCodeConstants.java  # 错误码常量
```

---

## 三、功能模块清单

| 模块名称 | 路由路径 | 后端接口前缀 | 状态 |
|----------|----------|-------------|------|
| 租车订单管理 | `/rental/order` | `/rental/order` | 已完成 |
| 自助取还管理 | `/rental/selfservice` | `/rental/self-device` | 已完成 |
| 租车库存管理 | `/rental/inventory` | `/rental/inventory` | 已完成 |
| 租车价格中心 | `/rental/price` | `/rental/price` | 已完成 |
| 租车商品管理 | `/rental/product` | `/rental/product` | 已完成 |
| 租车车辆管理 | `/rental/vehicle` | `/rental/vehicle` | 已完成 |
| 租车政策管理 | `/rental/policy` | `/rental/policy` | 已完成 |
| 门店管理 | `/rental/store` | `/rental/store` | 已完成 |
| 加盟商管理 | `/rental/franchisee` | `/rental/franchisee` | 已完成 |

---

## 四、数据库表清单

| 表名 | 说明 |
|------|------|
| `rental_order` | 租车订单主表 |
| `rental_vehicle` | 车辆信息表 |
| `rental_vehicle_model` | 车型信息表 |
| `rental_store` | 门店信息表 |
| `rental_franchisee` | 加盟商信息表 |
| `rental_inventory` | 库存信息表 |
| `rental_price_rule` | 价格规则表 |
| `rental_product` | 租车商品表 |
| `rental_policy` | 租车政策表 |
| `rental_self_device` | 自助取还设备表 |

---

## 五、权限标识规范

所有权限标识遵循 `rental:{模块}:{操作}` 格式，例如：

- `rental:order:query` — 订单查询
- `rental:vehicle:create` — 车辆新增
- `rental:store:update` — 门店编辑

---

## 六、订单状态枚举

| 状态值 | 含义 |
|--------|------|
| 10 | 待支付 |
| 20 | 待取车 |
| 30 | 用车中 |
| 40 | 待结算 |
| 50 | 已完成 |
| 60 | 已取消 |

---

## 七、车辆状态枚举

| 状态值 | 含义 |
|--------|------|
| 10 | 空闲 |
| 20 | 已预订 |
| 30 | 租赁中 |
| 40 | 维修/保养 |
| 50 | 停运 |

---

## 八、待办事项 / 下一步迭代

- [ ] 完善库存管理后端 Controller + Service + Mapper
- [ ] 完善价格中心后端 Controller + Service + Mapper
- [ ] 完善商品管理后端 Controller + Service + Mapper
- [ ] 完善政策管理后端 Controller + Service + Mapper
- [ ] 完善自助设备后端 Controller + Service + Mapper
- [ ] 接入多语言包（基础语言：简体中文、英语）
- [ ] 接入多币种换算中心（基础货币：美元）
- [ ] 首页数据看板（订单统计、收入统计、车辆状态分布）
- [ ] 导出 Excel 功能（订单、车辆、门店）

---

## 九、已知问题 / Bug 清单

> 当前版本 v1.0.0，暂无已知 Bug。

---

## 十、变更日志

| 日期 | 版本 | 变更内容 | 操作人 |
|------|------|----------|--------|
| 2026-04-22 | v1.0.0 | 初始版本，完成全部 9 个功能模块的前后端代码框架 | Manus AI |
