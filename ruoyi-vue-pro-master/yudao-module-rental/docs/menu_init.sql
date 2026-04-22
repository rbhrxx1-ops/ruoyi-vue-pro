-- ========================================
-- 租车系统菜单及权限初始化 SQL
-- 模块：yudao-module-rental
-- 创建时间：2026-04-22
-- ========================================

-- 一级菜单：租车管理
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('租车管理', '', 1, 50, 0, '/rental', 'car', NULL, 0, 1, 1, 1);

-- 获取刚插入的一级菜单 ID（假设为 2000，实际以自增 ID 为准）
SET @rental_menu_id = LAST_INSERT_ID();

-- ========================================
-- 二级菜单：租车订单管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('租车订单管理', '', 1, 10, @rental_menu_id, 'order', 'list', 'rental/order/index', 0, 1, 1, 1);
SET @order_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('订单查询', 'rental:order:query', 3, 1, @order_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('订单更新', 'rental:order:update', 3, 2, @order_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('订单删除', 'rental:order:delete', 3, 3, @order_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：自助取还管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('自助取还管理', '', 1, 20, @rental_menu_id, 'selfservice', 'monitor', 'rental/selfservice/index', 0, 1, 1, 1);
SET @self_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('设备查询', 'rental:self-device:query', 3, 1, @self_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('设备新增', 'rental:self-device:create', 3, 2, @self_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('设备编辑', 'rental:self-device:update', 3, 3, @self_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('设备删除', 'rental:self-device:delete', 3, 4, @self_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：租车库存管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('租车库存管理', '', 1, 30, @rental_menu_id, 'inventory', 'goods', 'rental/inventory/index', 0, 1, 1, 1);
SET @inventory_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('库存查询', 'rental:inventory:query', 3, 1, @inventory_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('库存调整', 'rental:inventory:update', 3, 2, @inventory_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：租车价格中心
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('租车价格中心', '', 1, 40, @rental_menu_id, 'price', 'money', 'rental/price/index', 0, 1, 1, 1);
SET @price_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('价格查询', 'rental:price:query', 3, 1, @price_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('价格新增', 'rental:price:create', 3, 2, @price_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('价格编辑', 'rental:price:update', 3, 3, @price_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('价格删除', 'rental:price:delete', 3, 4, @price_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：租车商品管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('租车商品管理', '', 1, 50, @rental_menu_id, 'product', 'shop', 'rental/product/index', 0, 1, 1, 1);
SET @product_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('商品查询', 'rental:product:query', 3, 1, @product_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('商品新增', 'rental:product:create', 3, 2, @product_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('商品编辑', 'rental:product:update', 3, 3, @product_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('商品删除', 'rental:product:delete', 3, 4, @product_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：租车车辆管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('租车车辆管理', '', 1, 60, @rental_menu_id, 'vehicle', 'car', 'rental/vehicle/index', 0, 1, 1, 1);
SET @vehicle_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('车辆查询', 'rental:vehicle:query', 3, 1, @vehicle_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('车辆新增', 'rental:vehicle:create', 3, 2, @vehicle_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('车辆编辑', 'rental:vehicle:update', 3, 3, @vehicle_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('车辆删除', 'rental:vehicle:delete', 3, 4, @vehicle_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：租车政策管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('租车政策管理', '', 1, 70, @rental_menu_id, 'policy', 'document', 'rental/policy/index', 0, 1, 1, 1);
SET @policy_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('政策查询', 'rental:policy:query', 3, 1, @policy_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('政策新增', 'rental:policy:create', 3, 2, @policy_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('政策编辑', 'rental:policy:update', 3, 3, @policy_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('政策删除', 'rental:policy:delete', 3, 4, @policy_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：门店管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('门店管理', '', 1, 80, @rental_menu_id, 'store', 'location', 'rental/store/index', 0, 1, 1, 1);
SET @store_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('门店查询', 'rental:store:query', 3, 1, @store_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('门店新增', 'rental:store:create', 3, 2, @store_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('门店编辑', 'rental:store:update', 3, 3, @store_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('门店删除', 'rental:store:delete', 3, 4, @store_menu_id, '', '', '', 0, 1, 1, 1);

-- ========================================
-- 二级菜单：加盟商管理
-- ========================================
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('加盟商管理', '', 1, 90, @rental_menu_id, 'franchisee', 'peoples', 'rental/franchisee/index', 0, 1, 1, 1);
SET @franchisee_menu_id = LAST_INSERT_ID();

INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('加盟商查询', 'rental:franchisee:query', 3, 1, @franchisee_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('加盟商新增', 'rental:franchisee:create', 3, 2, @franchisee_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('加盟商编辑', 'rental:franchisee:update', 3, 3, @franchisee_menu_id, '', '', '', 0, 1, 1, 1);
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, always_show)
VALUES ('加盟商删除', 'rental:franchisee:delete', 3, 4, @franchisee_menu_id, '', '', '', 0, 1, 1, 1);
