<template>
  <ContentWrap>
    <el-tabs v-model="activeTab">

      <!-- Tab1: 预定政策 -->
      <el-tab-pane label="预定政策" name="reserve">
        <el-form :model="reserveQuery" :inline="true" label-width="80px">
          <el-form-item label="规则名称">
            <el-input v-model="reserveQuery.ruleName" placeholder="请输入规则名称" clearable />
          </el-form-item>
          <el-form-item label="加盟商">
            <el-select v-model="reserveQuery.franchiseeId" placeholder="请选择" clearable style="width:140px">
              <el-option label="Relax Car" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="reserveQuery.status" placeholder="请选择" clearable style="width:100px">
              <el-option label="启用" :value="1" />
              <el-option label="禁用" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getReserveList">查询</el-button>
            <el-button @click="getReserveList">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="openReserveForm('create')">新增</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="reserveLoading" :data="reserveList">
          <el-table-column label="规则编号" prop="ruleNo" width="130" />
          <el-table-column label="规则名称" prop="ruleName" min-width="150" />
          <el-table-column label="加盟商" prop="franchiseeName" min-width="120" />
          <el-table-column label="适用门店" prop="storeName" min-width="120">
            <template #default="{ row }">{{ row.storeName || '所有门店' }}</template>
          </el-table-column>
          <el-table-column label="最早预定" min-width="130">
            <template #default="{ row }">{{ row.earliestBookingValue }} {{ row.earliestBookingUnit }}</template>
          </el-table-column>
          <el-table-column label="最晚预定" min-width="130">
            <template #default="{ row }">{{ row.latestBookingValue }} {{ row.latestBookingUnit }}</template>
          </el-table-column>
          <el-table-column label="最小租期" min-width="120">
            <template #default="{ row }">{{ row.minRentalValue }} {{ row.minRentalUnit }}</template>
          </el-table-column>
          <el-table-column label="驾龄/年龄限制" min-width="160">
            <template #default="{ row }">
              驾龄≥{{ row.minDrivingYears }}年 / {{ row.minAge }}~{{ row.maxAge }}岁
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="90">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="180">
            <template #default="{ row }">
              <el-button link type="primary" @click="openReserveForm('update', row.id)">编辑</el-button>
              <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="toggleReserveStatus(row)">
                {{ row.status === 1 ? '禁用' : '启用' }}
              </el-button>
              <el-button link type="danger" @click="deleteReservePolicy(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="reserveTotal" v-model:page="reserveQuery.pageNo" v-model:limit="reserveQuery.pageSize" @pagination="getReserveList" />
      </el-tab-pane>

      <!-- Tab2: 取用车政策（押金政策） -->
      <el-tab-pane label="取用车政策" name="deposit">
        <el-form :model="depositQuery" :inline="true" label-width="80px">
          <el-form-item label="规则名称">
            <el-input v-model="depositQuery.ruleName" placeholder="请输入规则名称" clearable />
          </el-form-item>
          <el-form-item label="加盟商">
            <el-select v-model="depositQuery.franchiseeId" placeholder="请选择" clearable style="width:140px">
              <el-option label="Relax Car" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="depositQuery.status" placeholder="请选择" clearable style="width:100px">
              <el-option label="启用" :value="1" />
              <el-option label="禁用" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getDepositList">查询</el-button>
            <el-button @click="getDepositList">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="openDepositForm('create')">新增</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="depositLoading" :data="depositList">
          <el-table-column label="规则名称" prop="ruleName" min-width="150" />
          <el-table-column label="加盟商" prop="franchiseeName" min-width="120" />
          <el-table-column label="适用门店" prop="storeName" min-width="120">
            <template #default="{ row }">{{ row.storeName || '所有门店' }}</template>
          </el-table-column>
          <el-table-column label="车型组" prop="vehicleGroup" width="100">
            <template #default="{ row }">{{ row.vehicleGroup || '所有' }}</template>
          </el-table-column>
          <el-table-column label="SIPP Code" prop="sippCode" width="110">
            <template #default="{ row }">{{ row.sippCode || '所有' }}</template>
          </el-table-column>
          <el-table-column label="押金方式" prop="depositMethod" min-width="150">
            <template #default="{ row }">
              <el-tag v-if="row.depositFree" type="success">免押金</el-tag>
              <span v-else>{{ depositMethodLabel(row.depositMethod) }} / {{ row.depositAmount }} {{ row.currency }}</span>
            </template>
          </el-table-column>
          <el-table-column label="超里程限制" min-width="160">
            <template #default="{ row }">
              {{ row.mileageLimit === 0 ? '不限' : row.mileageLimit + ' km/天' }}
              <span v-if="row.mileageLimit > 0">（超出 {{ row.extraMileageFee }} {{ row.currency }}/km）</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="90">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="180">
            <template #default="{ row }">
              <el-button link type="primary" @click="openDepositForm('update', row.id)">编辑</el-button>
              <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="toggleDepositStatus(row)">
                {{ row.status === 1 ? '禁用' : '启用' }}
              </el-button>
              <el-button link type="danger" @click="deleteDepositPolicy(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="depositTotal" v-model:page="depositQuery.pageNo" v-model:limit="depositQuery.pageSize" @pagination="getDepositList" />
      </el-tab-pane>

      <!-- Tab3: 取消政策 -->
      <el-tab-pane label="取消政策" name="cancel">
        <el-form :model="cancelQuery" :inline="true" label-width="80px">
          <el-form-item label="规则名称">
            <el-input v-model="cancelQuery.ruleName" placeholder="请输入规则名称" clearable />
          </el-form-item>
          <el-form-item label="加盟商">
            <el-select v-model="cancelQuery.franchiseeId" placeholder="请选择" clearable style="width:140px">
              <el-option label="Relax Car" :value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="cancelQuery.status" placeholder="请选择" clearable style="width:100px">
              <el-option label="启用" :value="1" />
              <el-option label="禁用" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getCancelList">查询</el-button>
            <el-button @click="getCancelList">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="openCancelForm('create')">新增</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="cancelLoading" :data="cancelList">
          <el-table-column label="规则名称" prop="ruleName" min-width="150" />
          <el-table-column label="加盟商" prop="franchiseeName" min-width="120" />
          <el-table-column label="适用门店" prop="storeName" min-width="120">
            <template #default="{ row }">{{ row.storeName || '所有门店' }}</template>
          </el-table-column>
          <el-table-column label="是否允许取消" prop="cancelAllowed" width="120">
            <template #default="{ row }">
              <el-tag :type="row.cancelAllowed ? 'success' : 'danger'">{{ row.cancelAllowed ? '允许' : '不允许' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="N小时内下单可退" prop="cancelWithinHours" min-width="160">
            <template #default="{ row }">
              {{ row.cancelWithinHours === 0 ? '不限制' : row.cancelWithinHours === -1 ? '不允许' : row.cancelWithinHours + ' 小时内' }}
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="90">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="180">
            <template #default="{ row }">
              <el-button link type="primary" @click="openCancelForm('update', row.id)">编辑</el-button>
              <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="toggleCancelStatus(row)">
                {{ row.status === 1 ? '禁用' : '启用' }}
              </el-button>
              <el-button link type="danger" @click="deleteCancelPolicy(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="cancelTotal" v-model:page="cancelQuery.pageNo" v-model:limit="cancelQuery.pageSize" @pagination="getCancelList" />
      </el-tab-pane>

    </el-tabs>
  </ContentWrap>

  <!-- 预定政策弹窗 -->
  <Dialog v-model="reserveDialogVisible" :title="reserveDialogTitle" width="640px">
    <el-form ref="reserveFormRef" :model="reserveForm" label-width="130px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="规则名称" prop="ruleName">
            <el-input v-model="reserveForm.ruleName" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="加盟商" prop="franchiseeId">
            <el-select v-model="reserveForm.franchiseeId" style="width:100%">
              <el-option label="Relax Car" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最早预定期">
            <el-input-number v-model="reserveForm.earliestBookingValue" :min="0" style="width:90px" />
            <el-select v-model="reserveForm.earliestBookingUnit" style="width:80px;margin-left:8px">
              <el-option label="小时" value="hour" />
              <el-option label="天" value="day" />
              <el-option label="月" value="month" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最晚预定期">
            <el-input-number v-model="reserveForm.latestBookingValue" :min="0" style="width:90px" />
            <el-select v-model="reserveForm.latestBookingUnit" style="width:80px;margin-left:8px">
              <el-option label="小时" value="hour" />
              <el-option label="天" value="day" />
              <el-option label="月" value="month" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最小租期">
            <el-input-number v-model="reserveForm.minRentalValue" :min="1" style="width:90px" />
            <el-select v-model="reserveForm.minRentalUnit" style="width:80px;margin-left:8px">
              <el-option label="小时" value="hour" />
              <el-option label="天" value="day" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最小驾龄(年)">
            <el-input-number v-model="reserveForm.minDrivingYears" :min="0" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最小年龄">
            <el-input-number v-model="reserveForm.minAge" :min="18" :max="99" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最大年龄">
            <el-input-number v-model="reserveForm.maxAge" :min="18" :max="99" style="width:100%" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="reserveDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitReserveForm">确定</el-button>
    </template>
  </Dialog>

  <!-- 押金政策弹窗 -->
  <Dialog v-model="depositDialogVisible" :title="depositDialogTitle" width="600px">
    <el-form ref="depositFormRef" :model="depositForm" label-width="130px">
      <el-form-item label="规则名称" prop="ruleName">
        <el-input v-model="depositForm.ruleName" />
      </el-form-item>
      <el-form-item label="加盟商" prop="franchiseeId">
        <el-select v-model="depositForm.franchiseeId" style="width:100%">
          <el-option label="Relax Car" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="车型组">
        <el-select v-model="depositForm.vehicleGroup" placeholder="留空=所有车型组" clearable style="width:100%">
          <el-option label="经济型" value="Economy" />
          <el-option label="紧凑型" value="Compact" />
          <el-option label="中型" value="Midsize" />
          <el-option label="SUV" value="SUV" />
          <el-option label="豪华型" value="Luxury" />
        </el-select>
      </el-form-item>
      <el-form-item label="SIPP Code">
        <el-input v-model="depositForm.sippCode" placeholder="留空=所有车型" />
      </el-form-item>
      <el-form-item label="是否免押金">
        <el-switch v-model="depositForm.depositFree" />
      </el-form-item>
      <template v-if="!depositForm.depositFree">
        <el-form-item label="押金收取方式">
          <el-select v-model="depositForm.depositMethod" style="width:100%">
            <el-option label="信用卡预授权" value="credit_card" />
            <el-option label="现金" value="cash" />
            <el-option label="线上支付" value="online" />
          </el-select>
        </el-form-item>
        <el-form-item label="押金金额">
          <el-input-number v-model="depositForm.depositAmount" :min="0" :precision="0" style="width:150px" />
          <el-select v-model="depositForm.currency" style="width:90px;margin-left:8px">
            <el-option label="JPY" value="JPY" />
            <el-option label="USD" value="USD" />
          </el-select>
        </el-form-item>
      </template>
      <el-form-item label="超里程限制(km/天)">
        <el-input-number v-model="depositForm.mileageLimit" :min="0" style="width:150px" />
        <span style="margin-left:8px;color:#999">0=不限</span>
      </el-form-item>
      <el-form-item label="超里程费用(/km)">
        <el-input-number v-model="depositForm.extraMileageFee" :min="0" :precision="0" style="width:150px" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="depositDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitDepositForm">确定</el-button>
    </template>
  </Dialog>

  <!-- 取消政策弹窗 -->
  <Dialog v-model="cancelDialogVisible" :title="cancelDialogTitle" width="560px">
    <el-form ref="cancelFormRef" :model="cancelForm" label-width="150px">
      <el-form-item label="规则名称" prop="ruleName">
        <el-input v-model="cancelForm.ruleName" />
      </el-form-item>
      <el-form-item label="加盟商" prop="franchiseeId">
        <el-select v-model="cancelForm.franchiseeId" style="width:100%">
          <el-option label="Relax Car" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否允许取消">
        <el-switch v-model="cancelForm.cancelAllowed" />
      </el-form-item>
      <el-form-item label="N小时内下单可退">
        <el-input-number v-model="cancelForm.cancelWithinHours" :min="-1" style="width:150px" />
        <span style="margin-left:8px;color:#999">0=不限 / -1=不允许</span>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="cancelDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitCancelForm">确定</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import request from '@/config/axios'

const activeTab = ref('reserve')

// ===== 预定政策 =====
const reserveLoading = ref(false)
const reserveList = ref([])
const reserveTotal = ref(0)
const reserveDialogVisible = ref(false)
const reserveDialogTitle = ref('')
const reserveFormRef = ref()
const reserveQuery = reactive({ pageNo: 1, pageSize: 10, ruleName: '', franchiseeId: undefined, status: undefined })
const reserveForm = reactive({
  id: undefined, ruleName: '', franchiseeId: undefined, storeId: undefined,
  earliestBookingValue: 0, earliestBookingUnit: 'hour',
  latestBookingValue: 30, latestBookingUnit: 'day',
  minRentalValue: 1, minRentalUnit: 'day',
  minDrivingYears: 1, minAge: 21, maxAge: 70, status: 1
})

const getReserveList = async () => {
  reserveLoading.value = true
  try {
    const res = await request.get({ url: '/rental/policy/reserve/list', params: reserveQuery })
    reserveList.value = res.data || []; reserveTotal.value = res.total || 0
  } finally { reserveLoading.value = false }
}

const openReserveForm = async (type: string, id?: number) => {
  reserveDialogVisible.value = true
  reserveDialogTitle.value = type === 'create' ? '新增预定政策' : '编辑预定政策'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/policy/reserve/get', params: { id } })
    Object.assign(reserveForm, res.data)
  }
}

const submitReserveForm = async () => {
  if (reserveForm.id) {
    await request.put({ url: '/rental/policy/reserve/update', data: reserveForm })
  } else {
    await request.post({ url: '/rental/policy/reserve/create', data: reserveForm })
  }
  reserveDialogVisible.value = false; getReserveList()
}

const toggleReserveStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/policy/reserve/disable' : '/rental/policy/reserve/enable'
  await request.put({ url, params: { id: row.id } }); getReserveList()
}

const deleteReservePolicy = async (id: number) => {
  await request.delete({ url: '/rental/policy/reserve/delete', params: { id } }); getReserveList()
}

// ===== 押金政策 =====
const depositLoading = ref(false)
const depositList = ref([])
const depositTotal = ref(0)
const depositDialogVisible = ref(false)
const depositDialogTitle = ref('')
const depositFormRef = ref()
const depositQuery = reactive({ pageNo: 1, pageSize: 10, ruleName: '', franchiseeId: undefined, status: undefined })
const depositForm = reactive({
  id: undefined, ruleName: '', franchiseeId: undefined, storeId: undefined,
  vehicleGroup: '', sippCode: '', depositFree: false,
  depositMethod: 'credit_card', depositAmount: 0, currency: 'JPY',
  mileageLimit: 0, extraMileageFee: 0, status: 1
})

const depositMethodLabel = (m: string) => {
  const map: Record<string, string> = { credit_card: '信用卡预授权', cash: '现金', online: '线上支付' }
  return map[m] || m
}

const getDepositList = async () => {
  depositLoading.value = true
  try {
    const res = await request.get({ url: '/rental/policy/deposit/list', params: depositQuery })
    depositList.value = res.data || []; depositTotal.value = res.total || 0
  } finally { depositLoading.value = false }
}

const openDepositForm = async (type: string, id?: number) => {
  depositDialogVisible.value = true
  depositDialogTitle.value = type === 'create' ? '新增押金政策' : '编辑押金政策'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/policy/deposit/get', params: { id } })
    Object.assign(depositForm, res.data)
  }
}

const submitDepositForm = async () => {
  if (depositForm.id) {
    await request.put({ url: '/rental/policy/deposit/update', data: depositForm })
  } else {
    await request.post({ url: '/rental/policy/deposit/create', data: depositForm })
  }
  depositDialogVisible.value = false; getDepositList()
}

const toggleDepositStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/policy/deposit/disable' : '/rental/policy/deposit/enable'
  await request.put({ url, params: { id: row.id } }); getDepositList()
}

const deleteDepositPolicy = async (id: number) => {
  await request.delete({ url: '/rental/policy/deposit/delete', params: { id } }); getDepositList()
}

// ===== 取消政策 =====
const cancelLoading = ref(false)
const cancelList = ref([])
const cancelTotal = ref(0)
const cancelDialogVisible = ref(false)
const cancelDialogTitle = ref('')
const cancelFormRef = ref()
const cancelQuery = reactive({ pageNo: 1, pageSize: 10, ruleName: '', franchiseeId: undefined, status: undefined })
const cancelForm = reactive({
  id: undefined, ruleName: '', franchiseeId: undefined, storeId: undefined,
  cancelAllowed: true, cancelWithinHours: 0, status: 1
})

const getCancelList = async () => {
  cancelLoading.value = true
  try {
    const res = await request.get({ url: '/rental/policy/cancel/list', params: cancelQuery })
    cancelList.value = res.data || []; cancelTotal.value = res.total || 0
  } finally { cancelLoading.value = false }
}

const openCancelForm = async (type: string, id?: number) => {
  cancelDialogVisible.value = true
  cancelDialogTitle.value = type === 'create' ? '新增取消政策' : '编辑取消政策'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/policy/cancel/get', params: { id } })
    Object.assign(cancelForm, res.data)
  }
}

const submitCancelForm = async () => {
  if (cancelForm.id) {
    await request.put({ url: '/rental/policy/cancel/update', data: cancelForm })
  } else {
    await request.post({ url: '/rental/policy/cancel/create', data: cancelForm })
  }
  cancelDialogVisible.value = false; getCancelList()
}

const toggleCancelStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/policy/cancel/disable' : '/rental/policy/cancel/enable'
  await request.put({ url, params: { id: row.id } }); getCancelList()
}

const deleteCancelPolicy = async (id: number) => {
  await request.delete({ url: '/rental/policy/cancel/delete', params: { id } }); getCancelList()
}

getReserveList()
getDepositList()
getCancelList()
</script>
