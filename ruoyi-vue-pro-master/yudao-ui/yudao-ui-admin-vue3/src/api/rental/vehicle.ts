import request from '@/config/axios'

export interface RentalVehicleVO {
  id: number
  plateNo: string
  vin: string
  modelId: number
  modelName: string
  storeId: number
  storeName: string
  status: number
  color: string
  mileage: number
  fuelLevel: number
  remark: string
  createTime: string
}

export interface RentalVehiclePageReqVO {
  pageNo: number
  pageSize: number
  plateNo?: string
  modelId?: number
  storeId?: number
  status?: number
}

export const getRentalVehiclePage = (params: RentalVehiclePageReqVO) => {
  return request.get({ url: '/rental/vehicle/page', params })
}

export const getRentalVehicle = (id: number) => {
  return request.get({ url: '/rental/vehicle/get', params: { id } })
}

export const createRentalVehicle = (data: Partial<RentalVehicleVO>) => {
  return request.post({ url: '/rental/vehicle/create', data })
}

export const updateRentalVehicle = (data: Partial<RentalVehicleVO>) => {
  return request.put({ url: '/rental/vehicle/update', data })
}

export const deleteRentalVehicle = (id: number) => {
  return request.delete({ url: '/rental/vehicle/delete', params: { id } })
}

export const VEHICLE_STATUS_MAP: Record<number, string> = {
  10: '空闲',
  20: '已预订',
  30: '租赁中',
  40: '维修/保养',
  50: '停运'
}

export const VEHICLE_STATUS_TAG_MAP: Record<number, string> = {
  10: 'success',
  20: 'primary',
  30: 'warning',
  40: 'danger',
  50: 'info'
}
