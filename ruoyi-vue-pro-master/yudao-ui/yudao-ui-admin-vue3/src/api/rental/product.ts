import request from '@/config/axios'

export interface RentalProductVO {
  id: number
  name: string
  modelId: number
  modelName: string
  storeId: number
  storeName: string
  coverImage: string
  dailyPrice: number
  depositAmount: number
  minRentDays: number
  maxRentDays: number
  status: number
  description: string
  createTime: string
}

export interface RentalProductPageReqVO {
  pageNo: number
  pageSize: number
  name?: string
  modelId?: number
  storeId?: number
  status?: number
}

export const getRentalProductPage = (params: RentalProductPageReqVO) => {
  return request.get({ url: '/rental/product/page', params })
}

export const getRentalProduct = (id: number) => {
  return request.get({ url: '/rental/product/get', params: { id } })
}

export const createRentalProduct = (data: Partial<RentalProductVO>) => {
  return request.post({ url: '/rental/product/create', data })
}

export const updateRentalProduct = (data: Partial<RentalProductVO>) => {
  return request.put({ url: '/rental/product/update', data })
}

export const deleteRentalProduct = (id: number) => {
  return request.delete({ url: '/rental/product/delete', params: { id } })
}
