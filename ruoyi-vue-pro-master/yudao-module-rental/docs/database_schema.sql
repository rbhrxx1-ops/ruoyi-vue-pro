-- 租车系统数据库表设计
-- 基于 FleetEdge 业务模块进行还原，采用 ruoyi-vue-pro 规范设计

-- 1. 租车订单管理 (Rental Orders)
CREATE TABLE `rental_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `store_id` bigint NOT NULL COMMENT '所属门店编号',
  `vehicle_id` bigint NOT NULL COMMENT '车辆编号',
  `status` tinyint NOT NULL COMMENT '订单状态（10-待支付, 20-已支付/待取车, 30-用车中, 40-已还车/待结算, 50-已完成, 60-已取消）',
  `total_amount` int NOT NULL COMMENT '订单总金额（分）',
  `pay_amount` int NOT NULL COMMENT '实际支付金额（分）',
  `start_time` datetime NOT NULL COMMENT '预计取车时间',
  `end_time` datetime NOT NULL COMMENT '预计还车时间',
  `actual_start_time` datetime DEFAULT NULL COMMENT '实际取车时间',
  `actual_end_time` datetime DEFAULT NULL COMMENT '实际还车时间',
  `pickup_store_id` bigint NOT NULL COMMENT '取车门店编号',
  `return_store_id` bigint NOT NULL COMMENT '还车门店编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租车订单表';

-- 2. 租车车辆管理 (Rental Vehicles)
CREATE TABLE `rental_vehicle` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '车辆编号',
  `plate_no` varchar(32) NOT NULL COMMENT '车牌号',
  `vin` varchar(64) NOT NULL COMMENT '车架号(VIN)',
  `model_id` bigint NOT NULL COMMENT '车型编号',
  `store_id` bigint NOT NULL COMMENT '所属门店编号',
  `status` tinyint NOT NULL COMMENT '车辆状态（10-空闲, 20-已预订, 30-租赁中, 40-维修/保养, 50-停运）',
  `color` varchar(32) DEFAULT NULL COMMENT '车辆颜色',
  `mileage` int NOT NULL DEFAULT '0' COMMENT '当前里程数(km)',
  `fuel_level` tinyint NOT NULL DEFAULT '100' COMMENT '当前油量/电量百分比',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_plate_no` (`plate_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租车车辆信息表';

-- 3. 租车库存管理 (Rental Inventory - 车型库存)
CREATE TABLE `rental_model_inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存编号',
  `model_id` bigint NOT NULL COMMENT '车型编号',
  `store_id` bigint NOT NULL COMMENT '门店编号',
  `date` date NOT NULL COMMENT '日期',
  `total_stock` int NOT NULL DEFAULT '0' COMMENT '总库存数',
  `used_stock` int NOT NULL DEFAULT '0' COMMENT '已占用库存数',
  `available_stock` int NOT NULL DEFAULT '0' COMMENT '可用库存数',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_model_store_date` (`model_id`,`store_id`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车型每日库存表';

-- 4. 租车价格中心 (Rental Pricing)
CREATE TABLE `rental_price_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规则编号',
  `model_id` bigint NOT NULL COMMENT '车型编号',
  `store_id` bigint NOT NULL COMMENT '门店编号',
  `rule_type` tinyint NOT NULL COMMENT '规则类型（1-基础日租金, 2-节假日附加费, 3-周末附加费）',
  `price` int NOT NULL COMMENT '价格/附加费金额（分）',
  `start_date` date DEFAULT NULL COMMENT '生效开始日期',
  `end_date` date DEFAULT NULL COMMENT '生效结束日期',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0-停用, 1-启用）',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租车价格规则表';

-- 5. 门店管理 (Rental Stores)
CREATE TABLE `rental_store` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '门店编号',
  `name` varchar(128) NOT NULL COMMENT '门店名称',
  `franchisee_id` bigint DEFAULT NULL COMMENT '加盟商编号（为空则为直营）',
  `city_id` int NOT NULL COMMENT '所在城市编号',
  `address` varchar(255) NOT NULL COMMENT '详细地址',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `contact_phone` varchar(32) NOT NULL COMMENT '联系电话',
  `business_hours` varchar(64) DEFAULT '09:00-18:00' COMMENT '营业时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0-停业, 1-营业）',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租车门店表';

-- 6. 加盟商管理 (Rental Franchisees)
CREATE TABLE `rental_franchisee` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '加盟商编号',
  `name` varchar(128) NOT NULL COMMENT '加盟商名称',
  `contact_name` varchar(64) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(32) NOT NULL COMMENT '联系人电话',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0-停用, 1-启用）',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='加盟商信息表';

-- 7. 租车商品管理 (Rental Products)
CREATE TABLE `rental_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `name` varchar(128) NOT NULL COMMENT '商品名称',
  `type` tinyint NOT NULL COMMENT '商品类型（1-车型, 2-保险, 3-附加设备如儿童座椅）',
  `model_id` bigint DEFAULT NULL COMMENT '关联车型编号（type=1时必填）',
  `price` int NOT NULL COMMENT '基础价格（分）',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0-下架, 1-上架）',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租车商品信息表';

-- 8. 租车政策管理 (Rental Policies)
CREATE TABLE `rental_policy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '政策编号',
  `name` varchar(128) NOT NULL COMMENT '政策名称',
  `type` tinyint NOT NULL COMMENT '政策类型（1-预定政策, 2-取用车政策, 3-取消政策）',
  `content` text NOT NULL COMMENT '政策详细内容',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0-停用, 1-启用）',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租车政策表';

-- 9. 自助取还设备管理 (Self-Service Devices)
CREATE TABLE `rental_self_device` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备编号',
  `device_no` varchar(64) NOT NULL COMMENT '设备SN码',
  `vehicle_id` bigint NOT NULL COMMENT '绑定车辆编号',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '设备状态（0-离线, 1-在线, 2-故障）',
  `last_heartbeat` datetime DEFAULT NULL COMMENT '最后心跳时间',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_device_no` (`device_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助取还设备表';
