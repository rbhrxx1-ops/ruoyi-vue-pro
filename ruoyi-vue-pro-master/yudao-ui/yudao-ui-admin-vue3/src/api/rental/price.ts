import request from '@/config/axios'

export interface RentalPriceRuleVO {
  id: number
  modelId: number
  modelName: string
  storeId: number
  storeName: string
  ruleType: number
  price: number
  startDate: string
  endDate: string
  status: number
  createTime: string
}

export interface RentalPriceRulePageReqVO {
  pageNo: number
  pageSize: number
  modelId?: number
  storeId?: number
  ruleType?: number
  status?: number
}

export const PRICE_RULE_TYPE_MAP: Record<number, string> = {
  1: '基础日租金',
  2: '节假日附加费',
  3: '周末附加费',
  4: '异地取还费'
}

export const getRentalPriceRulePage = (params: RentalPriceRulePageReqVO) => {
  return request.get({ url: '/rental/price/page', params })
}

export const createRentalPriceRule = (data: Partial<RentalPriceRuleVO>) => {
  return request.post({ url: '/rental/price/create', data })
}

export const updateRentalPriceRule = (data: Partial<RentalPriceRuleVO>) => {
  return request.put({ url: '/rental/price/update', data })
}

export const deleteRentalPriceRule = (id: number) => {
  return request.delete({ url: '/rental/price/delete', params: { id } })
}
