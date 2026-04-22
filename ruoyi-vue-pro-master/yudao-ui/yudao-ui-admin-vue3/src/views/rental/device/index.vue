<template>
  <ContentWrap>
    <!-- 搜索栏 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input v-model="queryParams.deviceName" placeholder="请输入设备名称" clearable />
      </el-form-item>
      <el-form-item label="适用渠道" prop="channel">
        <el-select v-model="queryParams.channel" placeholder="请选择" clearable>
          <el-option label="Klook" value="Klook" />
          <el-option label="offline" value="offline" />
          <el-option label="zuzuche" value="zuzuche" />
          <el-option label="QEEQ" value="QEEQ" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="openForm('create')">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain @click="handleBatchEnable" :disabled="selectedIds.length === 0">批量启用</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain @click="handleBatchDisable" :disabled="selectedIds.length === 0">批量禁用</el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="id" width="80" />
      <el-table-column label="设备名称" prop="deviceName" min-width="150" />
      <el-table-column label="加盟商" prop="franchiseeName" min-width="120" />
      <el-table-column label="设备类型" prop="deviceType" min-width="120">
        <template #default="{ row }">
          <el-tag type="info">{{ deviceTypeLabel(row.deviceType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="单价" prop="unitPrice" min-width="120">
        <template #default="{ row }">
          {{ row.unitPrice }} {{ row.currency }} / {{ row.priceUnit }}
        </template>
      </el-table-column>
      <el-table-column label="适用渠道" prop="applicableChannels" min-width="200">
        <template #default="{ row }">
          <el-tag v-for="ch in parseJson(row.applicableChannels)" :key="ch" size="small" class="mr4">{{ ch }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status" width="90">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="{ row }">
          <el-button link type="primary" @click="openForm('update', row.id)">编辑</el-button>
          <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="handleToggleStatus(row)">
            {{ row.status === 1 ? '禁用' : '启用' }}
          </el-button>
          <el-button link type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </ContentWrap>

  <!-- 新增/编辑弹窗 -->
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
    <el-form ref="formRef" :model="formData" :rules="formRules" label-width="110px">
      <el-form-item label="设备名称" prop="deviceName">
        <el-input v-model="formData.deviceName" placeholder="如：ETC卡、防滑链、儿童座椅" />
      </el-form-item>
      <el-form-item label="加盟商" prop="franchiseeId">
        <el-select v-model="formData.franchiseeId" placeholder="请选择加盟商" style="width:100%">
          <el-option label="Relax Car" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="设备类型" prop="deviceType">
        <el-select v-model="formData.deviceType" placeholder="请选择" style="width:100%">
          <el-option label="ETC卡" value="etc" />
          <el-option label="防滑链" value="snow_chain" />
          <el-option label="儿童座椅" value="child_seat" />
          <el-option label="雪地胎" value="snow_tire" />
          <el-option label="导航仪" value="navigation" />
          <el-option label="其他" value="other" />
        </el-select>
      </el-form-item>
      <el-form-item label="单价" prop="unitPrice">
        <el-input-number v-model="formData.unitPrice" :min="0" :precision="0" style="width:150px" />
        <el-select v-model="formData.currency" style="width:90px;margin-left:8px">
          <el-option label="JPY" value="JPY" />
          <el-option label="USD" value="USD" />
          <el-option label="CNY" value="CNY" />
        </el-select>
        <span style="margin-left:8px">/ </span>
        <el-select v-model="formData.priceUnit" style="width:80px;margin-left:4px">
          <el-option label="次" value="次" />
          <el-option label="天" value="天" />
          <el-option label="个" value="个" />
        </el-select>
      </el-form-item>
      <el-form-item label="适用渠道" prop="applicableChannels">
        <el-checkbox-group v-model="formData.applicableChannelsList">
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
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" type="textarea" :rows="2" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">确定</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import request from '@/config/axios'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryFormRef = ref()
const formRef = ref()
const selectedIds = ref<number[]>([])

const queryParams = reactive({ pageNo: 1, pageSize: 10, deviceName: '', channel: '', status: undefined })

const formData = reactive({
  id: undefined,
  deviceName: '',
  franchiseeId: undefined,
  franchiseeName: '',
  deviceType: '',
  unitPrice: 0,
  currency: 'JPY',
  priceUnit: '次',
  applicableChannelsList: [] as string[],
  applicableChannels: '',
  remark: '',
  status: 1
})

const formRules = {
  deviceName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  franchiseeId: [{ required: true, message: '请选择加盟商', trigger: 'change' }],
  deviceType: [{ required: true, message: '请选择设备类型', trigger: 'change' }],
  unitPrice: [{ required: true, message: '请输入单价', trigger: 'blur' }]
}

const deviceTypeLabel = (type: string) => {
  const map: Record<string, string> = { etc: 'ETC卡', snow_chain: '防滑链', child_seat: '儿童座椅', snow_tire: '雪地胎', navigation: '导航仪', other: '其他' }
  return map[type] || type
}

const parseJson = (str: string) => { try { return JSON.parse(str) || [] } catch { return [] } }

const getList = async () => {
  loading.value = true
  try {
    const res = await request.get({ url: '/rental/device/list', params: queryParams })
    list.value = res.data || []
    total.value = res.total || 0
  } finally { loading.value = false }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }
const handleSelectionChange = (rows: any[]) => { selectedIds.value = rows.map(r => r.id) }

const openForm = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type === 'create' ? '新增附加设备' : '编辑附加设备'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/device/get', params: { id } })
    Object.assign(formData, res.data)
    formData.applicableChannelsList = parseJson(res.data.applicableChannels)
  }
}

const submitForm = async () => {
  await formRef.value?.validate()
  formData.applicableChannels = JSON.stringify(formData.applicableChannelsList)
  if (formData.id) {
    await request.put({ url: '/rental/device/update', data: formData })
  } else {
    await request.post({ url: '/rental/device/create', data: formData })
  }
  dialogVisible.value = false
  getList()
}

const handleToggleStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/device/disable' : '/rental/device/enable'
  await request.put({ url, params: { id: row.id } })
  getList()
}

const handleDelete = async (id: number) => {
  await request.delete({ url: '/rental/device/delete', params: { id } })
  getList()
}

const handleBatchEnable = async () => {
  await request.put({ url: '/rental/device/batch-enable', data: selectedIds.value })
  getList()
}

const handleBatchDisable = async () => {
  await request.put({ url: '/rental/device/batch-disable', data: selectedIds.value })
  getList()
}

getList()
</script>
