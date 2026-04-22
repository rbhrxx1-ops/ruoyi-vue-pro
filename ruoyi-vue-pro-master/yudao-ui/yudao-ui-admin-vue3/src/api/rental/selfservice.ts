import request from '@/config/axios'

export interface RentalSelfDeviceVO {
  id: number
  deviceNo: string
  storeId: number
  storeName: string
  deviceType: number
  status: number
  location: string
  remark: string
  createTime: string
}

export interface RentalSelfDevicePageReqVO {
  pageNo: number
  pageSize: number
  deviceNo?: string
  storeId?: number
  deviceType?: number
  status?: number
}

export const DEVICE_TYPE_MAP: Record<number, string> = {
  1: '取车柜',
  2: '还车柜',
  3: '综合柜'
}

export const DEVICE_STATUS_MAP: Record<number, string> = {
  0: '离线',
  1: '在线',
  2: '故障'
}

export const DEVICE_STATUS_TAG_MAP: Record<number, string> = {
  0: 'info',
  1: 'success',
  2: 'danger'
}

export const getRentalSelfDevicePage = (params: RentalSelfDevicePageReqVO) => {
  return request.get({ url: '/rental/self-device/page', params })
}

export const createRentalSelfDevice = (data: Partial<RentalSelfDeviceVO>) => {
  return request.post({ url: '/rental/self-device/create', data })
}

export const updateRentalSelfDevice = (data: Partial<RentalSelfDeviceVO>) => {
  return request.put({ url: '/rental/self-device/update', data })
}

export const deleteRentalSelfDevice = (id: number) => {
  return request.delete({ url: '/rental/self-device/delete', params: { id } })
}
