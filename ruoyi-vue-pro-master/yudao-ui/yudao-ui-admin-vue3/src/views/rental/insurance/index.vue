<template>
  <ContentWrap>
    <!-- 搜索栏 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="80px">
      <el-form-item label="保险名称" prop="insuranceName">
        <el-input v-model="queryParams.insuranceName" placeholder="请输入保险名称" clearable />
      </el-form-item>
      <el-form-item label="保险类型" prop="insuranceType">
        <el-select v-model="queryParams.insuranceType" placeholder="请选择" clearable>
          <el-option label="基本险(CDW)" value="basic" />
          <el-option label="综合险" value="comprehensive" />
          <el-option label="全额险(CDW+TPL+TP)" value="full" />
        </el-select>
      </el-form-item>
      <el-form-item label="适用渠道" prop="channel">
        <el-select v-model="queryParams.channel" placeholder="请选择" clearable>
          <el-option label="Klook" value="Klook" />
          <el-option label="offline" value="offline" />
          <el-option label="zuzuche" value="zuzuche" />
          <el-option label="QEEQ" value="QEEQ" />
          <el-option label="DISCOVERCARS" value="DISCOVERCARS" />
          <el-option label="Gogoout" value="Gogoout" />
          <el-option label="carmore" value="carmore" />
          <el-option label="Economy Bookings" value="Economy Bookings" />
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
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="ID" prop="id" width="80" />
      <el-table-column label="保险名称" prop="insuranceName" min-width="150" />
      <el-table-column label="加盟商" prop="franchiseeName" min-width="120" />
      <el-table-column label="保险类型" prop="insuranceType" min-width="120">
        <template #default="{ row }">
          <el-tag :type="row.insuranceType === 'full' ? 'danger' : row.insuranceType === 'comprehensive' ? 'warning' : 'info'">
            {{ insuranceTypeLabel(row.insuranceType) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="适用渠道" prop="applicableChannels" min-width="200">
        <template #default="{ row }">
          <el-tag v-for="ch in parseJson(row.applicableChannels)" :key="ch" size="small" class="mr4">{{ ch }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="包含保险项目" prop="insuranceItems" min-width="200">
        <template #default="{ row }">
          <span>{{ parseJson(row.insuranceItems).join(' / ') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status" width="90">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="最后更新时间" prop="lastUpdateTime" width="180">
        <template #default="{ row }">{{ formatDate(row.lastUpdateTime) }}</template>
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
      <el-form-item label="保险名称" prop="insuranceName">
        <el-input v-model="formData.insuranceName" placeholder="请输入保险名称" />
      </el-form-item>
      <el-form-item label="加盟商" prop="franchiseeId">
        <el-select v-model="formData.franchiseeId" placeholder="请选择加盟商" style="width:100%">
          <el-option label="Relax Car" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="保险类型" prop="insuranceType">
        <el-select v-model="formData.insuranceType" placeholder="请选择" style="width:100%">
          <el-option label="基本险(CDW)" value="basic" />
          <el-option label="综合险" value="comprehensive" />
          <el-option label="全额险(CDW+TPL+TP)" value="full" />
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
      <el-form-item label="包含保险项目" prop="insuranceItems">
        <el-checkbox-group v-model="formData.insuranceItemsList">
          <el-checkbox label="CDW-车辆碰撞险" />
          <el-checkbox label="TPL-第三者责任险" />
          <el-checkbox label="TP-盗窃险" />
          <el-checkbox label="PAI-人身意外险" />
        </el-checkbox-group>
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
import { formatDate } from '@/utils/formatTime'
import request from '@/config/axios'

const loading = ref(false)
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const queryFormRef = ref()
const formRef = ref()

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  insuranceName: '',
  insuranceType: '',
  channel: '',
  status: undefined
})

const formData = reactive({
  id: undefined,
  insuranceName: '',
  franchiseeId: undefined,
  franchiseeName: '',
  insuranceType: '',
  applicableChannelsList: [] as string[],
  insuranceItemsList: [] as string[],
  applicableChannels: '',
  insuranceItems: '',
  status: 1
})

const formRules = {
  insuranceName: [{ required: true, message: '请输入保险名称', trigger: 'blur' }],
  franchiseeId: [{ required: true, message: '请选择加盟商', trigger: 'change' }],
  insuranceType: [{ required: true, message: '请选择保险类型', trigger: 'change' }]
}

const insuranceTypeLabel = (type: string) => {
  const map: Record<string, string> = { basic: '基本险(CDW)', comprehensive: '综合险', full: '全额险(CDW+TPL+TP)' }
  return map[type] || type
}

const parseJson = (str: string) => {
  try { return JSON.parse(str) || [] } catch { return [] }
}

const getList = async () => {
  loading.value = true
  try {
    const res = await request.get({ url: '/rental/insurance/list', params: queryParams })
    list.value = res.data || []
    total.value = res.total || 0
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryFormRef.value?.resetFields(); handleQuery() }

const openForm = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type === 'create' ? '新增保险商品' : '编辑保险商品'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/insurance/get', params: { id } })
    Object.assign(formData, res.data)
    formData.applicableChannelsList = parseJson(res.data.applicableChannels)
    formData.insuranceItemsList = parseJson(res.data.insuranceItems)
  }
}

const submitForm = async () => {
  await formRef.value?.validate()
  formData.applicableChannels = JSON.stringify(formData.applicableChannelsList)
  formData.insuranceItems = JSON.stringify(formData.insuranceItemsList)
  if (formData.id) {
    await request.put({ url: '/rental/insurance/update', data: formData })
  } else {
    await request.post({ url: '/rental/insurance/create', data: formData })
  }
  dialogVisible.value = false
  getList()
}

const handleToggleStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/insurance/disable' : '/rental/insurance/enable'
  await request.put({ url, params: { id: row.id } })
  getList()
}

const handleDelete = async (id: number) => {
  await request.delete({ url: '/rental/insurance/delete', params: { id } })
  getList()
}

getList()
</script>
