import request from '@/config/axios'

export interface RentalFranchiseeVO {
  id: number
  name: string
  contactName: string
  contactPhone: string
  status: number
  remark: string
  createTime: string
}

export interface RentalFranchiseePageReqVO {
  pageNo: number
  pageSize: number
  name?: string
  contactName?: string
  status?: number
}

export const getRentalFranchiseePage = (params: RentalFranchiseePageReqVO) => {
  return request.get({ url: '/rental/franchisee/page', params })
}

export const getRentalFranchisee = (id: number) => {
  return request.get({ url: '/rental/franchisee/get', params: { id } })
}

export const getRentalFranchiseeList = () => {
  return request.get({ url: '/rental/franchisee/list' })
}

export const createRentalFranchisee = (data: Partial<RentalFranchiseeVO>) => {
  return request.post({ url: '/rental/franchisee/create', data })
}

export const updateRentalFranchisee = (data: Partial<RentalFranchiseeVO>) => {
  return request.put({ url: '/rental/franchisee/update', data })
}

export const deleteRentalFranchisee = (id: number) => {
  return request.delete({ url: '/rental/franchisee/delete', params: { id } })
}
