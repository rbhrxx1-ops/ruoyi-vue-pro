import request from '@/config/axios'

export interface RentalPolicyVO {
  id: number
  name: string
  type: number
  content: string
  status: number
  createTime: string
}

export interface RentalPolicyPageReqVO {
  pageNo: number
  pageSize: number
  name?: string
  type?: number
  status?: number
}

export const POLICY_TYPE_MAP: Record<number, string> = {
  1: '预定政策',
  2: '取用车政策',
  3: '取消政策'
}

export const getRentalPolicyPage = (params: RentalPolicyPageReqVO) => {
  return request.get({ url: '/rental/policy/page', params })
}

export const getRentalPolicy = (id: number) => {
  return request.get({ url: '/rental/policy/get', params: { id } })
}

export const createRentalPolicy = (data: Partial<RentalPolicyVO>) => {
  return request.post({ url: '/rental/policy/create', data })
}

export const updateRentalPolicy = (data: Partial<RentalPolicyVO>) => {
  return request.put({ url: '/rental/policy/update', data })
}

export const deleteRentalPolicy = (id: number) => {
  return request.delete({ url: '/rental/policy/delete', params: { id } })
}
