<template>
  <ContentWrap>
    <el-tabs v-model="activeTab">
      <!-- Tab1: 车辆租金费率 -->
      <el-tab-pane label="车辆租金费率" name="carRent">
        <el-form :model="carRentQuery" ref="carRentQueryRef" :inline="true" label-width="90px">
          <el-form-item label="取车门店" prop="pickupStoreId">
            <el-select v-model="carRentQuery.pickupStoreId" placeholder="请选择取车门店" clearable style="width:160px">
              <el-option label="上野店" :value="1" />
              <el-option label="成田机场店" :value="2" />
              <el-option label="羽田机场店" :value="3" />
              <el-option label="新宿店" :value="4" />
              <el-option label="大阪梅田店" :value="5" />
              <el-option label="京都店" :value="6" />
            </el-select>
          </el-form-item>
          <el-form-item label="SIPP Code" prop="sippCode">
            <el-input v-model="carRentQuery.sippCode" placeholder="如：ECMR" clearable style="width:120px" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="carRentQuery.status" placeholder="请选择" clearable style="width:100px">
              <el-option label="启用" :value="1" />
              <el-option label="禁用" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getCarRentList">查询</el-button>
            <el-button @click="carRentQueryRef?.resetFields(); getCarRentList()">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="openCarRentForm('create')">新增</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="carRentLoading" :data="carRentList">
          <el-table-column label="ID" prop="id" width="80" />
          <el-table-column label="规则名称" prop="ruleName" min-width="150" />
          <el-table-column label="加盟商" prop="franchiseeName" min-width="120" />
          <el-table-column label="取车门店" prop="pickupStoreName" min-width="130" />
          <el-table-column label="SIPP Code" prop="sippCode" width="110" />
          <el-table-column label="车型组" prop="vehicleGroup" width="100" />
          <el-table-column label="日租金" prop="dailyRate" width="120">
            <template #default="{ row }">{{ row.dailyRate }} {{ row.currency }}</template>
          </el-table-column>
          <el-table-column label="周租金" prop="weeklyRate" width="120">
            <template #default="{ row }">{{ row.weeklyRate }} {{ row.currency }}</template>
          </el-table-column>
          <el-table-column label="月租金" prop="monthlyRate" width="120">
            <template #default="{ row }">{{ row.monthlyRate }} {{ row.currency }}</template>
          </el-table-column>
          <el-table-column label="适用渠道" prop="applicableChannels" min-width="180">
            <template #default="{ row }">
              <el-tag v-for="ch in parseJson(row.applicableChannels)" :key="ch" size="small" class="mr4">{{ ch }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="有效期" min-width="200">
            <template #default="{ row }">{{ row.validFrom }} ~ {{ row.validTo }}</template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="90">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="180">
            <template #default="{ row }">
              <el-button link type="primary" @click="openCarRentForm('update', row.id)">编辑</el-button>
              <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="toggleCarRentStatus(row)">
                {{ row.status === 1 ? '禁用' : '启用' }}
              </el-button>
              <el-button link type="danger" @click="deleteCarRentRule(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="carRentTotal" v-model:page="carRentQuery.pageNo" v-model:limit="carRentQuery.pageSize" @pagination="getCarRentList" />
      </el-tab-pane>

      <!-- Tab2: 异地取还费率 -->
      <el-tab-pane label="异地取还费率" name="pickoff">
        <el-form :model="pickoffQuery" ref="pickoffQueryRef" :inline="true" label-width="90px">
          <el-form-item label="取车门店" prop="pickupStoreId">
            <el-select v-model="pickoffQuery.pickupStoreId" placeholder="请选择" clearable style="width:160px">
              <el-option label="上野店" :value="1" />
              <el-option label="成田机场店" :value="2" />
              <el-option label="羽田机场店" :value="3" />
              <el-option label="新宿店" :value="4" />
              <el-option label="大阪梅田店" :value="5" />
              <el-option label="京都店" :value="6" />
            </el-select>
          </el-form-item>
          <el-form-item label="还车门店" prop="returnStoreId">
            <el-select v-model="pickoffQuery.returnStoreId" placeholder="请选择" clearable style="width:160px">
              <el-option label="上野店" :value="1" />
              <el-option label="成田机场店" :value="2" />
              <el-option label="羽田机场店" :value="3" />
              <el-option label="新宿店" :value="4" />
              <el-option label="大阪梅田店" :value="5" />
              <el-option label="京都店" :value="6" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="pickoffQuery.status" placeholder="请选择" clearable style="width:100px">
              <el-option label="启用" :value="1" />
              <el-option label="禁用" :value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getPickoffList">查询</el-button>
            <el-button @click="pickoffQueryRef?.resetFields(); getPickoffList()">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="openPickoffForm('create')">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain @click="batchEnablePickoff" :disabled="pickoffSelectedIds.length === 0">批量启用</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain @click="batchDisablePickoff" :disabled="pickoffSelectedIds.length === 0">批量禁用</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="pickoffLoading" :data="pickoffList" @selection-change="(rows: any[]) => pickoffSelectedIds = rows.map(r => r.id)">
          <el-table-column type="selection" width="55" />
          <el-table-column label="ID" prop="id" width="80" />
          <el-table-column label="取车门店" prop="pickupStoreName" min-width="130" />
          <el-table-column label="还车门店" prop="returnStoreName" min-width="130" />
          <el-table-column label="SIPP Code" prop="sippCode" width="110" />
          <el-table-column label="异地费用" prop="pickoffFee" width="120">
            <template #default="{ row }">{{ row.pickoffFee }} {{ row.currency }}</template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="90">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="180">
            <template #default="{ row }">
              <el-button link type="primary" @click="openPickoffForm('update', row.id)">编辑</el-button>
              <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="togglePickoffStatus(row)">
                {{ row.status === 1 ? '禁用' : '启用' }}
              </el-button>
              <el-button link type="danger" @click="deletePickoffRule(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="pickoffTotal" v-model:page="pickoffQuery.pageNo" v-model:limit="pickoffQuery.pageSize" @pagination="getPickoffList" />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>

  <!-- 车辆租金费率 新增/编辑弹窗 -->
  <Dialog v-model="carRentDialogVisible" :title="carRentDialogTitle" width="700px">
    <el-form ref="carRentFormRef" :model="carRentForm" :rules="carRentFormRules" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="规则名称" prop="ruleName">
            <el-input v-model="carRentForm.ruleName" placeholder="请输入规则名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="加盟商" prop="franchiseeId">
            <el-select v-model="carRentForm.franchiseeId" style="width:100%">
              <el-option label="Relax Car" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="取车门店" prop="pickupStoreId">
            <el-select v-model="carRentForm.pickupStoreId" placeholder="请选择" style="width:100%">
              <el-option label="上野店" :value="1" />
              <el-option label="成田机场店" :value="2" />
              <el-option label="羽田机场店" :value="3" />
              <el-option label="新宿店" :value="4" />
              <el-option label="大阪梅田店" :value="5" />
              <el-option label="京都店" :value="6" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="SIPP Code" prop="sippCode">
            <el-input v-model="carRentForm.sippCode" placeholder="如：ECMR" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车型组" prop="vehicleGroup">
            <el-select v-model="carRentForm.vehicleGroup" placeholder="请选择" style="width:100%">
              <el-option label="经济型" value="Economy" />
              <el-option label="紧凑型" value="Compact" />
              <el-option label="中型" value="Midsize" />
              <el-option label="全尺寸" value="Fullsize" />
              <el-option label="SUV" value="SUV" />
              <el-option label="豪华型" value="Luxury" />
              <el-option label="厢式车" value="Van" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="币种" prop="currency">
            <el-select v-model="carRentForm.currency" style="width:100%">
              <el-option label="JPY" value="JPY" />
              <el-option label="USD" value="USD" />
              <el-option label="CNY" value="CNY" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="日租金" prop="dailyRate">
            <el-input-number v-model="carRentForm.dailyRate" :min="0" :precision="0" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="周租金" prop="weeklyRate">
            <el-input-number v-model="carRentForm.weeklyRate" :min="0" :precision="0" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="月租金" prop="monthlyRate">
            <el-input-number v-model="carRentForm.monthlyRate" :min="0" :precision="0" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="最短租期(天)" prop="minRentalDays">
            <el-input-number v-model="carRentForm.minRentalDays" :min="1" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="有效期" prop="validFrom">
            <el-date-picker v-model="carRentForm.validFrom" type="date" placeholder="开始日期" style="width:160px" value-format="YYYY-MM-DD" />
            <span style="margin:0 8px">~</span>
            <el-date-picker v-model="carRentForm.validTo" type="date" placeholder="结束日期" style="width:160px" value-format="YYYY-MM-DD" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="适用渠道">
            <el-checkbox-group v-model="carRentForm.applicableChannelsList">
              <el-checkbox label="Klook" />
              <el-checkbox label="offline" />
              <el-checkbox label="zuzuche" />
              <el-checkbox label="QEEQ" />
              <el-checkbox label="DISCOVERCARS" />
              <el-checkbox label="Gogoout" />
              <el-checkbox label="carmore" />
              <el-checkbox label="Economy Bookings" />
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="carRentDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitCarRentForm">确定</el-button>
    </template>
  </Dialog>

  <!-- 异地取还费率 新增/编辑弹窗 -->
  <Dialog v-model="pickoffDialogVisible" :title="pickoffDialogTitle" width="560px">
    <el-form ref="pickoffFormRef" :model="pickoffForm" :rules="pickoffFormRules" label-width="110px">
      <el-form-item label="取车门店" prop="pickupStoreId">
        <el-select v-model="pickoffForm.pickupStoreId" placeholder="请选择" style="width:100%">
          <el-option label="上野店" :value="1" />
          <el-option label="成田机场店" :value="2" />
          <el-option label="羽田机场店" :value="3" />
          <el-option label="新宿店" :value="4" />
          <el-option label="大阪梅田店" :value="5" />
          <el-option label="京都店" :value="6" />
        </el-select>
      </el-form-item>
      <el-form-item label="还车门店" prop="returnStoreId">
        <el-select v-model="pickoffForm.returnStoreId" placeholder="请选择" style="width:100%">
          <el-option label="上野店" :value="1" />
          <el-option label="成田机场店" :value="2" />
          <el-option label="羽田机场店" :value="3" />
          <el-option label="新宿店" :value="4" />
          <el-option label="大阪梅田店" :value="5" />
          <el-option label="京都店" :value="6" />
        </el-select>
      </el-form-item>
      <el-form-item label="SIPP Code" prop="sippCode">
        <el-input v-model="pickoffForm.sippCode" placeholder="留空=所有车型" />
      </el-form-item>
      <el-form-item label="异地费用" prop="pickoffFee">
        <el-input-number v-model="pickoffForm.pickoffFee" :min="0" :precision="0" style="width:150px" />
        <el-select v-model="pickoffForm.currency" style="width:90px;margin-left:8px">
          <el-option label="JPY" value="JPY" />
          <el-option label="USD" value="USD" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="pickoffForm.remark" type="textarea" :rows="2" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="pickoffDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitPickoffForm">确定</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import request from '@/config/axios'

const activeTab = ref('carRent')

// ===== 车辆租金费率 =====
const carRentLoading = ref(false)
const carRentList = ref([])
const carRentTotal = ref(0)
const carRentDialogVisible = ref(false)
const carRentDialogTitle = ref('')
const carRentQueryRef = ref()
const carRentFormRef = ref()

const carRentQuery = reactive({ pageNo: 1, pageSize: 10, pickupStoreId: undefined, sippCode: '', status: undefined })

const carRentForm = reactive({
  id: undefined, ruleName: '', franchiseeId: undefined, pickupStoreId: undefined,
  sippCode: '', vehicleGroup: '', currency: 'JPY', dailyRate: 0, weeklyRate: 0, monthlyRate: 0,
  minRentalDays: 1, validFrom: '', validTo: '', applicableChannelsList: [] as string[], applicableChannels: '', status: 1
})

const carRentFormRules = {
  ruleName: [{ required: true, message: '请输入规则名称', trigger: 'blur' }],
  pickupStoreId: [{ required: true, message: '请选择取车门店', trigger: 'change' }],
  sippCode: [{ required: true, message: '请输入SIPP Code', trigger: 'blur' }],
  dailyRate: [{ required: true, message: '请输入日租金', trigger: 'blur' }]
}

const parseJson = (str: string) => { try { return JSON.parse(str) || [] } catch { return [] } }

const getCarRentList = async () => {
  carRentLoading.value = true
  try {
    const res = await request.get({ url: '/rental/price/car-rent/list', params: carRentQuery })
    carRentList.value = res.data || []; carRentTotal.value = res.total || 0
  } finally { carRentLoading.value = false }
}

const openCarRentForm = async (type: string, id?: number) => {
  carRentDialogVisible.value = true
  carRentDialogTitle.value = type === 'create' ? '新增车辆租金费率' : '编辑车辆租金费率'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/price/car-rent/get', params: { id } })
    Object.assign(carRentForm, res.data)
    carRentForm.applicableChannelsList = parseJson(res.data.applicableChannels)
  }
}

const submitCarRentForm = async () => {
  await carRentFormRef.value?.validate()
  carRentForm.applicableChannels = JSON.stringify(carRentForm.applicableChannelsList)
  if (carRentForm.id) {
    await request.put({ url: '/rental/price/car-rent/update', data: carRentForm })
  } else {
    await request.post({ url: '/rental/price/car-rent/create', data: carRentForm })
  }
  carRentDialogVisible.value = false; getCarRentList()
}

const toggleCarRentStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/price/car-rent/disable' : '/rental/price/car-rent/enable'
  await request.put({ url, params: { id: row.id } }); getCarRentList()
}

const deleteCarRentRule = async (id: number) => {
  await request.delete({ url: '/rental/price/car-rent/delete', params: { id } }); getCarRentList()
}

// ===== 异地取还费率 =====
const pickoffLoading = ref(false)
const pickoffList = ref([])
const pickoffTotal = ref(0)
const pickoffDialogVisible = ref(false)
const pickoffDialogTitle = ref('')
const pickoffQueryRef = ref()
const pickoffFormRef = ref()
const pickoffSelectedIds = ref<number[]>([])

const pickoffQuery = reactive({ pageNo: 1, pageSize: 10, pickupStoreId: undefined, returnStoreId: undefined, status: undefined })

const pickoffForm = reactive({
  id: undefined, pickupStoreId: undefined, pickupStoreName: '', returnStoreId: undefined,
  returnStoreName: '', sippCode: '', pickoffFee: 0, currency: 'JPY', remark: '', status: 1
})

const pickoffFormRules = {
  pickupStoreId: [{ required: true, message: '请选择取车门店', trigger: 'change' }],
  returnStoreId: [{ required: true, message: '请选择还车门店', trigger: 'change' }],
  pickoffFee: [{ required: true, message: '请输入异地费用', trigger: 'blur' }]
}

const getPickoffList = async () => {
  pickoffLoading.value = true
  try {
    const res = await request.get({ url: '/rental/price/pickoff/list', params: pickoffQuery })
    pickoffList.value = res.data || []; pickoffTotal.value = res.total || 0
  } finally { pickoffLoading.value = false }
}

const openPickoffForm = async (type: string, id?: number) => {
  pickoffDialogVisible.value = true
  pickoffDialogTitle.value = type === 'create' ? '新增异地取还费率' : '编辑异地取还费率'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/price/pickoff/get', params: { id } })
    Object.assign(pickoffForm, res.data)
  }
}

const submitPickoffForm = async () => {
  await pickoffFormRef.value?.validate()
  if (pickoffForm.id) {
    await request.put({ url: '/rental/price/pickoff/update', data: pickoffForm })
  } else {
    await request.post({ url: '/rental/price/pickoff/create', data: pickoffForm })
  }
  pickoffDialogVisible.value = false; getPickoffList()
}

const togglePickoffStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/price/pickoff/disable' : '/rental/price/pickoff/enable'
  await request.put({ url, params: { id: row.id } }); getPickoffList()
}

const deletePickoffRule = async (id: number) => {
  await request.delete({ url: '/rental/price/pickoff/delete', params: { id } }); getPickoffList()
}

const batchEnablePickoff = async () => {
  await request.put({ url: '/rental/price/pickoff/batch-enable', data: pickoffSelectedIds.value }); getPickoffList()
}

const batchDisablePickoff = async () => {
  await request.put({ url: '/rental/price/pickoff/batch-disable', data: pickoffSelectedIds.value }); getPickoffList()
}

getCarRentList()
getPickoffList()
</script>
