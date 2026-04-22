import request from '@/config/axios'

export interface RentalStoreVO {
  id: number
  name: string
  franchiseeId: number
  franchiseeName: string
  cityId: number
  address: string
  longitude: number
  latitude: number
  contactPhone: string
  businessHours: string
  status: number
  createTime: string
}

export interface RentalStorePageReqVO {
  pageNo: number
  pageSize: number
  name?: string
  franchiseeId?: number
  cityId?: number
  status?: number
}

export const getRentalStorePage = (params: RentalStorePageReqVO) => {
  return request.get({ url: '/rental/store/page', params })
}

export const getRentalStore = (id: number) => {
  return request.get({ url: '/rental/store/get', params: { id } })
}

export const getRentalStoreList = () => {
  return request.get({ url: '/rental/store/list' })
}

export const createRentalStore = (data: Partial<RentalStoreVO>) => {
  return request.post({ url: '/rental/store/create', data })
}

export const updateRentalStore = (data: Partial<RentalStoreVO>) => {
  return request.put({ url: '/rental/store/update', data })
}

export const deleteRentalStore = (id: number) => {
  return request.delete({ url: '/rental/store/delete', params: { id } })
}
