import request from '@/config/axios'

// 租车订单 VO
export interface RentalOrderVO {
  id: number
  orderNo: string
  userId: number
  storeId: number
  storeName: string
  vehicleId: number
  plateNo: string
  status: number
  totalAmount: number
  payAmount: number
  startTime: string
  endTime: string
  actualStartTime: string
  actualEndTime: string
  pickupStoreId: number
  pickupStoreName: string
  returnStoreId: number
  returnStoreName: string
  channelSource: string
  remark: string
  createTime: string
}

// 订单分页查询参数
export interface RentalOrderPageReqVO {
  pageNo: number
  pageSize: number
  orderNo?: string
  status?: number
  storeId?: number
  vehicleId?: number
  channelSource?: string
  createTimeStart?: string
  createTimeEnd?: string
}

// 获取订单分页
export const getRentalOrderPage = (params: RentalOrderPageReqVO) => {
  return request.get({ url: '/rental/order/page', params })
}

// 获取订单详情
export const getRentalOrder = (id: number) => {
  return request.get({ url: '/rental/order/get', params: { id } })
}

// 更新订单状态
export const updateRentalOrderStatus = (data: { id: number; status: number; remark?: string }) => {
  return request.put({ url: '/rental/order/update-status', data })
}

// 删除订单
export const deleteRentalOrder = (id: number) => {
  return request.delete({ url: '/rental/order/delete', params: { id } })
}

// 订单状态枚举
export const ORDER_STATUS_MAP: Record<number, string> = {
  10: '待支付',
  20: '待取车',
  30: '用车中',
  40: '待结算',
  50: '已完成',
  60: '已取消'
}

export const ORDER_STATUS_TAG_MAP: Record<number, string> = {
  10: 'warning',
  20: 'primary',
  30: 'success',
  40: 'warning',
  50: 'info',
  60: 'danger'
}
