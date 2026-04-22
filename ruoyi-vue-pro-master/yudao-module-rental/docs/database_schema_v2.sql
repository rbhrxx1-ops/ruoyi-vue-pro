-- =====================================================
-- 租车系统数据库建表脚本 v2.0
-- 基于 FleetEdge 系统 100% 还原
-- 包含所有 9 大功能模块的数据表
-- =====================================================

-- 1. 加盟商表
CREATE TABLE IF NOT EXISTS `rental_franchisee` (
  `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_name`   VARCHAR(100) NOT NULL COMMENT '加盟商名称',
  `franchisee_code`   VARCHAR(50)  NOT NULL COMMENT '加盟商编码',
  `contact_person`    VARCHAR(50)  COMMENT '联系人',
  `contact_phone`     VARCHAR(20)  COMMENT '联系电话',
  `contact_email`     VARCHAR(100) COMMENT '联系邮箱',
  `address`           VARCHAR(255) COMMENT '地址',
  `country`           VARCHAR(50)  COMMENT '国家',
  `currency`          VARCHAR(10)  DEFAULT 'JPY' COMMENT '默认币种',
  `status`            TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`           VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`           VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`           TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='加盟商表';

-- 2. 门店表
CREATE TABLE IF NOT EXISTS `rental_store` (
  `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_id`     BIGINT       NOT NULL COMMENT '加盟商ID',
  `franchisee_name`   VARCHAR(100) COMMENT '加盟商名称',
  `store_name`        VARCHAR(100) NOT NULL COMMENT '门店名称',
  `store_code`        VARCHAR(50)  NOT NULL COMMENT '门店编码',
  `store_name_en`     VARCHAR(100) COMMENT '门店英文名称',
  `address`           VARCHAR(255) COMMENT '门店地址',
  `address_en`        VARCHAR(255) COMMENT '门店英文地址',
  `city`              VARCHAR(50)  COMMENT '城市',
  `country`           VARCHAR(50)  DEFAULT 'Japan' COMMENT '国家',
  `phone`             VARCHAR(20)  COMMENT '联系电话',
  `email`             VARCHAR(100) COMMENT '联系邮箱',
  `longitude`         DECIMAL(10,6) COMMENT '经度',
  `latitude`          DECIMAL(10,6) COMMENT '纬度',
  `open_time`         VARCHAR(20)  COMMENT '营业开始时间（如 08:00）',
  `close_time`        VARCHAR(20)  COMMENT '营业结束时间（如 20:00）',
  `is_airport`        TINYINT      DEFAULT 0 COMMENT '是否机场门店：0否 1是',
  `airport_code`      VARCHAR(10)  COMMENT '机场代码（如 NRT、HND）',
  `status`            TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`           VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`           VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`           TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门店表';

-- 3. 车辆表
CREATE TABLE IF NOT EXISTS `rental_vehicle` (
  `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_id`     BIGINT       NOT NULL COMMENT '加盟商ID',
  `store_id`          BIGINT       COMMENT '所属门店ID',
  `store_name`        VARCHAR(100) COMMENT '所属门店名称',
  `license_plate`     VARCHAR(20)  NOT NULL COMMENT '车牌号',
  `sipp_code`         VARCHAR(10)  COMMENT 'SIPP Code（如 ECMR）',
  `vehicle_group`     VARCHAR(50)  COMMENT '车型组（Economy/Compact/Midsize/SUV等）',
  `brand`             VARCHAR(50)  COMMENT '品牌（如 Toyota）',
  `model`             VARCHAR(50)  COMMENT '车型（如 Aqua）',
  `year`              INT          COMMENT '车辆年份',
  `color`             VARCHAR(30)  COMMENT '颜色',
  `seats`             INT          DEFAULT 5 COMMENT '座位数',
  `transmission`      VARCHAR(20)  DEFAULT 'AT' COMMENT '变速箱：AT自动 MT手动',
  `fuel_type`         VARCHAR(20)  DEFAULT 'gasoline' COMMENT '燃料类型：gasoline/hybrid/electric',
  `fuel_level`        INT          DEFAULT 100 COMMENT '当前油量百分比（0-100）',
  `mileage`           INT          DEFAULT 0 COMMENT '当前里程数（km）',
  `vehicle_status`    VARCHAR(20)  DEFAULT 'available' COMMENT '车辆状态：available/rented/maintenance/unavailable',
  `status`            TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`           VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`           VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`           TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆表';

-- 4. 车型库存表
CREATE TABLE IF NOT EXISTS `rental_inventory` (
  `id`                BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_id`     BIGINT       NOT NULL COMMENT '加盟商ID',
  `store_id`          BIGINT       NOT NULL COMMENT '门店ID',
  `store_name`        VARCHAR(100) COMMENT '门店名称',
  `sipp_code`         VARCHAR(10)  NOT NULL COMMENT 'SIPP Code',
  `vehicle_group`     VARCHAR(50)  COMMENT '车型组',
  `total_stock`       INT          NOT NULL DEFAULT 0 COMMENT '总库存',
  `available_stock`   INT          NOT NULL DEFAULT 0 COMMENT '可用库存',
  `rented_stock`      INT          NOT NULL DEFAULT 0 COMMENT '已租出库存',
  `maintenance_stock` INT          NOT NULL DEFAULT 0 COMMENT '维修中库存',
  `creator`           VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`           VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`           TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_store_sipp` (`store_id`, `sipp_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车型库存表';

-- 5. 保险商品表
CREATE TABLE IF NOT EXISTS `rental_insurance` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_id`        BIGINT       NOT NULL COMMENT '加盟商ID',
  `franchisee_name`      VARCHAR(100) COMMENT '加盟商名称',
  `insurance_name`       VARCHAR(100) NOT NULL COMMENT '保险名称',
  `insurance_type`       VARCHAR(30)  NOT NULL COMMENT '保险类型：basic/comprehensive/full',
  `applicable_channels`  JSON         COMMENT '适用渠道列表（JSON数组）',
  `insurance_items`      JSON         COMMENT '包含保险项目（JSON数组）',
  `last_update_time`     DATETIME     COMMENT '最后更新时间',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保险商品表';

-- 6. 附加设备表
CREATE TABLE IF NOT EXISTS `rental_device` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_id`        BIGINT       NOT NULL COMMENT '加盟商ID',
  `franchisee_name`      VARCHAR(100) COMMENT '加盟商名称',
  `device_name`          VARCHAR(100) NOT NULL COMMENT '设备名称（如：ETC卡、防滑链）',
  `device_type`          VARCHAR(30)  NOT NULL COMMENT '设备类型：etc/snow_chain/child_seat/snow_tire/navigation/other',
  `unit_price`           DECIMAL(10,0) NOT NULL DEFAULT 0 COMMENT '单价',
  `currency`             VARCHAR(10)  DEFAULT 'JPY' COMMENT '币种',
  `price_unit`           VARCHAR(10)  DEFAULT '次' COMMENT '计价单位：次/天/个',
  `applicable_channels`  JSON         COMMENT '适用渠道列表（JSON数组）',
  `remark`               VARCHAR(255) COMMENT '备注',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附加设备表（ETC卡、防滑链、儿童座椅等）';

-- 7. 车辆租金费率规则表
CREATE TABLE IF NOT EXISTS `rental_car_rent_rule` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_id`        BIGINT       NOT NULL COMMENT '加盟商ID',
  `franchisee_name`      VARCHAR(100) COMMENT '加盟商名称',
  `rule_name`            VARCHAR(100) NOT NULL COMMENT '规则名称',
  `pickup_store_id`      BIGINT       COMMENT '取车门店ID（null=所有门店）',
  `pickup_store_name`    VARCHAR(100) COMMENT '取车门店名称',
  `sipp_code`            VARCHAR(10)  COMMENT 'SIPP Code（null=所有车型）',
  `vehicle_group`        VARCHAR(50)  COMMENT '车型组',
  `currency`             VARCHAR(10)  DEFAULT 'JPY' COMMENT '币种',
  `daily_rate`           DECIMAL(10,0) DEFAULT 0 COMMENT '日租金',
  `weekly_rate`          DECIMAL(10,0) DEFAULT 0 COMMENT '周租金',
  `monthly_rate`         DECIMAL(10,0) DEFAULT 0 COMMENT '月租金',
  `min_rental_days`      INT          DEFAULT 1 COMMENT '最短租期（天）',
  `valid_from`           DATE         COMMENT '有效期开始',
  `valid_to`             DATE         COMMENT '有效期结束',
  `applicable_channels`  JSON         COMMENT '适用渠道列表（JSON数组）',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆租金费率规则表';

-- 8. 异地取还费率规则表
CREATE TABLE IF NOT EXISTS `rental_pickoff_rule` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `franchisee_id`        BIGINT       COMMENT '加盟商ID',
  `pickup_store_id`      BIGINT       NOT NULL COMMENT '取车门店ID',
  `pickup_store_name`    VARCHAR(100) COMMENT '取车门店名称',
  `return_store_id`      BIGINT       NOT NULL COMMENT '还车门店ID',
  `return_store_name`    VARCHAR(100) COMMENT '还车门店名称',
  `sipp_code`            VARCHAR(10)  COMMENT 'SIPP Code（null=所有车型）',
  `pickoff_fee`          DECIMAL(10,0) NOT NULL DEFAULT 0 COMMENT '异地取还费用',
  `currency`             VARCHAR(10)  DEFAULT 'JPY' COMMENT '币种',
  `remark`               VARCHAR(255) COMMENT '备注',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='异地取还费率规则表';

-- 9. 预定政策表
CREATE TABLE IF NOT EXISTS `rental_reserve_policy` (
  `id`                       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rule_no`                  VARCHAR(50)  COMMENT '规则编号',
  `rule_name`                VARCHAR(100) NOT NULL COMMENT '规则名称',
  `franchisee_id`            BIGINT       COMMENT '加盟商ID',
  `franchisee_name`          VARCHAR(100) COMMENT '加盟商名称',
  `store_id`                 BIGINT       COMMENT '适用门店ID（null=所有门店）',
  `store_name`               VARCHAR(100) COMMENT '适用门店名称',
  `earliest_booking_value`   INT          DEFAULT 0 COMMENT '最早预定期数值',
  `earliest_booking_unit`    VARCHAR(10)  DEFAULT 'hour' COMMENT '最早预定期单位：hour/day/month',
  `latest_booking_value`     INT          DEFAULT 30 COMMENT '最晚预定期数值',
  `latest_booking_unit`      VARCHAR(10)  DEFAULT 'day' COMMENT '最晚预定期单位：hour/day/month',
  `min_rental_value`         INT          DEFAULT 1 COMMENT '最小租期数值',
  `min_rental_unit`          VARCHAR(10)  DEFAULT 'day' COMMENT '最小租期单位：hour/day',
  `min_driving_years`        INT          DEFAULT 1 COMMENT '最小驾龄（年）',
  `min_age`                  INT          DEFAULT 21 COMMENT '最小年龄',
  `max_age`                  INT          DEFAULT 70 COMMENT '最大年龄',
  `status`                   TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`                  VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`                  VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`                  TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预定政策表';

-- 10. 取用车政策（押金政策）表
CREATE TABLE IF NOT EXISTS `rental_deposit_policy` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rule_name`            VARCHAR(100) NOT NULL COMMENT '规则名称',
  `franchisee_id`        BIGINT       COMMENT '加盟商ID',
  `franchisee_name`      VARCHAR(100) COMMENT '加盟商名称',
  `store_id`             BIGINT       COMMENT '适用门店ID（null=所有门店）',
  `store_name`           VARCHAR(100) COMMENT '适用门店名称',
  `vehicle_group`        VARCHAR(50)  COMMENT '车型组（null=所有车型组）',
  `sipp_code`            VARCHAR(10)  COMMENT 'SIPP Code（null=所有）',
  `deposit_free`         TINYINT      DEFAULT 0 COMMENT '是否免押金：0否 1是',
  `deposit_method`       VARCHAR(30)  COMMENT '押金收取方式：credit_card/cash/online',
  `deposit_amount`       DECIMAL(10,0) DEFAULT 0 COMMENT '押金金额',
  `currency`             VARCHAR(10)  DEFAULT 'JPY' COMMENT '押金币种',
  `mileage_limit`        INT          DEFAULT 0 COMMENT '超里程限制（km/天，0=不限）',
  `extra_mileage_fee`    DECIMAL(10,0) DEFAULT 0 COMMENT '超里程费用（每公里）',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='取用车政策（押金政策）表';

-- 11. 取消政策表
CREATE TABLE IF NOT EXISTS `rental_cancel_policy` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rule_name`            VARCHAR(100) NOT NULL COMMENT '规则名称',
  `franchisee_id`        BIGINT       COMMENT '加盟商ID',
  `franchisee_name`      VARCHAR(100) COMMENT '加盟商名称',
  `store_id`             BIGINT       COMMENT '适用门店ID（null=所有门店）',
  `store_name`           VARCHAR(100) COMMENT '适用门店名称',
  `cancel_allowed`       TINYINT      DEFAULT 1 COMMENT '是否允许取消：0否 1是',
  `cancel_within_hours`  INT          DEFAULT 0 COMMENT 'N小时内下单允许退单（0=不限 -1=不允许）',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='取消政策表';

-- 12. 租车订单表
CREATE TABLE IF NOT EXISTS `rental_order` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no`             VARCHAR(50)  NOT NULL UNIQUE COMMENT '订单号',
  `channel`              VARCHAR(50)  COMMENT '渠道（Klook/offline/zuzuche等）',
  `channel_order_no`     VARCHAR(100) COMMENT '渠道订单号',
  `franchisee_id`        BIGINT       COMMENT '加盟商ID',
  `franchisee_name`      VARCHAR(100) COMMENT '加盟商名称',
  `pickup_store_id`      BIGINT       COMMENT '取车门店ID',
  `pickup_store_name`    VARCHAR(100) COMMENT '取车门店名称',
  `return_store_id`      BIGINT       COMMENT '还车门店ID',
  `return_store_name`    VARCHAR(100) COMMENT '还车门店名称',
  `sipp_code`            VARCHAR(10)  COMMENT 'SIPP Code',
  `vehicle_group`        VARCHAR(50)  COMMENT '车型组',
  `vehicle_id`           BIGINT       COMMENT '实际分配车辆ID',
  `license_plate`        VARCHAR(20)  COMMENT '实际分配车牌号',
  `customer_name`        VARCHAR(100) COMMENT '客户姓名',
  `customer_name_en`     VARCHAR(100) COMMENT '客户英文姓名',
  `customer_phone`       VARCHAR(20)  COMMENT '客户电话',
  `customer_email`       VARCHAR(100) COMMENT '客户邮箱',
  `customer_nationality` VARCHAR(50)  COMMENT '客户国籍',
  `driver_license_no`    VARCHAR(50)  COMMENT '驾照号码',
  `driver_license_country` VARCHAR(50) COMMENT '驾照国家',
  `pickup_time`          DATETIME     COMMENT '计划取车时间',
  `return_time`          DATETIME     COMMENT '计划还车时间',
  `actual_pickup_time`   DATETIME     COMMENT '实际取车时间',
  `actual_return_time`   DATETIME     COMMENT '实际还车时间',
  `rental_days`          INT          COMMENT '租期（天）',
  `base_amount`          DECIMAL(12,0) DEFAULT 0 COMMENT '基础租金',
  `insurance_amount`     DECIMAL(12,0) DEFAULT 0 COMMENT '保险费用',
  `device_amount`        DECIMAL(12,0) DEFAULT 0 COMMENT '附加设备费用',
  `pickoff_amount`       DECIMAL(12,0) DEFAULT 0 COMMENT '异地取还费用',
  `total_amount`         DECIMAL(12,0) DEFAULT 0 COMMENT '订单总金额',
  `currency`             VARCHAR(10)  DEFAULT 'JPY' COMMENT '币种',
  `deposit_amount`       DECIMAL(12,0) DEFAULT 0 COMMENT '押金金额',
  `deposit_status`       VARCHAR(20)  COMMENT '押金状态：pending/held/released',
  `order_status`         VARCHAR(30)  NOT NULL DEFAULT 'pending' COMMENT '订单状态：pending/confirmed/picked_up/returned/completed/cancelled',
  `payment_status`       VARCHAR(20)  DEFAULT 'unpaid' COMMENT '支付状态：unpaid/paid/refunded',
  `is_self_service`      TINYINT      DEFAULT 0 COMMENT '是否自助取还：0否 1是',
  `remark`               TEXT         COMMENT '备注',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_channel_order_no` (`channel_order_no`),
  KEY `idx_pickup_store` (`pickup_store_id`),
  KEY `idx_order_status` (`order_status`),
  KEY `idx_pickup_time` (`pickup_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租车订单表';

-- 13. 自助取还设备表
CREATE TABLE IF NOT EXISTS `rental_self_service_device` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_no`            VARCHAR(50)  NOT NULL COMMENT '设备编号',
  `device_name`          VARCHAR(100) NOT NULL COMMENT '设备名称',
  `store_id`             BIGINT       COMMENT '所属门店ID',
  `store_name`           VARCHAR(100) COMMENT '所属门店名称',
  `franchisee_id`        BIGINT       COMMENT '加盟商ID',
  `device_model`         VARCHAR(50)  COMMENT '设备型号',
  `serial_no`            VARCHAR(100) COMMENT '设备序列号',
  `location_desc`        VARCHAR(255) COMMENT '安装位置描述',
  `longitude`            DECIMAL(10,6) COMMENT '经度',
  `latitude`             DECIMAL(10,6) COMMENT '纬度',
  `device_status`        VARCHAR(20)  DEFAULT 'online' COMMENT '设备状态：online/offline/maintenance',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '启用状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助取还设备（Qube）表';

-- 14. 自助取还服务区域表
CREATE TABLE IF NOT EXISTS `rental_self_service_region` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `region_name`          VARCHAR(100) NOT NULL COMMENT '区域名称',
  `region_code`          VARCHAR(50)  COMMENT '区域编码',
  `store_id`             BIGINT       COMMENT '所属门店ID',
  `store_name`           VARCHAR(100) COMMENT '所属门店名称',
  `franchisee_id`        BIGINT       COMMENT '加盟商ID',
  `region_desc`          VARCHAR(255) COMMENT '区域描述',
  `address`              VARCHAR(255) COMMENT '区域地址',
  `longitude`            DECIMAL(10,6) COMMENT '经度',
  `latitude`             DECIMAL(10,6) COMMENT '纬度',
  `service_radius`       INT          DEFAULT 500 COMMENT '服务半径（米）',
  `status`               TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助取还服务区域表';

-- 15. 自助取还任务表
CREATE TABLE IF NOT EXISTS `rental_self_service_task` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_no`              VARCHAR(50)  NOT NULL COMMENT '任务编号',
  `order_id`             BIGINT       COMMENT '关联订单ID',
  `order_no`             VARCHAR(50)  COMMENT '关联订单号',
  `device_id`            BIGINT       COMMENT '设备ID',
  `device_no`            VARCHAR(50)  COMMENT '设备编号',
  `store_id`             BIGINT       COMMENT '门店ID',
  `store_name`           VARCHAR(100) COMMENT '门店名称',
  `license_plate`        VARCHAR(20)  COMMENT '车牌号',
  `customer_name`        VARCHAR(100) COMMENT '客户姓名',
  `customer_phone`       VARCHAR(20)  COMMENT '客户联系电话',
  `task_type`            VARCHAR(20)  NOT NULL COMMENT '任务类型：pickup自助取车 / return自助还车',
  `task_status`          VARCHAR(20)  NOT NULL DEFAULT 'pending' COMMENT '任务状态：pending/in_progress/completed/failed/cancelled',
  `scheduled_time`       DATETIME     COMMENT '计划执行时间',
  `actual_time`          DATETIME     COMMENT '实际执行时间',
  `remark`               VARCHAR(255) COMMENT '备注',
  `creator`              VARCHAR(64)  DEFAULT '' COMMENT '创建者',
  `create_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater`              VARCHAR(64)  DEFAULT '' COMMENT '更新者',
  `update_time`          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`              TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_task_status` (`task_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自助取还任务表';

-- =====================================================
-- 初始化数据：门店（基于 FleetEdge 真实数据）
-- =====================================================
INSERT INTO `rental_franchisee` (`id`, `franchisee_name`, `franchisee_code`, `contact_person`, `currency`, `status`, `creator`, `updater`, `deleted`)
VALUES (1, 'Relax Car', 'RELAX_CAR', 'Admin', 'JPY', 1, 'admin', 'admin', 0)
ON DUPLICATE KEY UPDATE `franchisee_name` = VALUES(`franchisee_name`);

INSERT INTO `rental_store` (`id`, `franchisee_id`, `store_name`, `store_code`, `store_name_en`, `city`, `country`, `is_airport`, `status`, `creator`, `updater`, `deleted`) VALUES
(1, 1, '上野店', 'UENO', 'Ueno', '东京', 'Japan', 0, 1, 'admin', 'admin', 0),
(2, 1, '成田机场店', 'NRT', 'Narita Airport', '千叶', 'Japan', 1, 1, 'admin', 'admin', 0),
(3, 1, '羽田机场店', 'HND', 'Haneda Airport', '东京', 'Japan', 1, 1, 'admin', 'admin', 0),
(4, 1, '新宿店', 'SHINJUKU', 'Shinjuku', '东京', 'Japan', 0, 1, 'admin', 'admin', 0),
(5, 1, '大阪梅田店', 'UMEDA', 'Osaka Umeda', '大阪', 'Japan', 0, 1, 'admin', 'admin', 0),
(6, 1, '京都店', 'KYOTO', 'Kyoto', '京都', 'Japan', 0, 1, 'admin', 'admin', 0)
ON DUPLICATE KEY UPDATE `store_name` = VALUES(`store_name`);
