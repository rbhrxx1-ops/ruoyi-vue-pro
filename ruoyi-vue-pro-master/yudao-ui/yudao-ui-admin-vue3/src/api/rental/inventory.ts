import request from '@/config/axios'

export interface RentalInventoryVO {
  id: number
  storeId: number
  storeName: string
  modelId: number
  modelName: string
  totalCount: number
  availableCount: number
  reservedCount: number
  inUseCount: number
  maintenanceCount: number
  updateTime: string
}

export interface RentalInventoryPageReqVO {
  pageNo: number
  pageSize: number
  storeId?: number
  modelId?: number
}

export const getRentalInventoryPage = (params: RentalInventoryPageReqVO) => {
  return request.get({ url: '/rental/inventory/page', params })
}

export const getRentalInventoryDetail = (id: number) => {
  return request.get({ url: '/rental/inventory/get', params: { id } })
}

export const adjustRentalInventory = (data: { id: number; adjustCount: number; reason: string }) => {
  return request.put({ url: '/rental/inventory/adjust', data })
}
