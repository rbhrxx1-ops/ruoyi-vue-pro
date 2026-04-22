<template>
  <ContentWrap>
    <el-form :model="queryParams" :inline="true" label-width="80px">
      <el-form-item label="门店名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入门店名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="营业" :value="1" />
          <el-option label="停业" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery"><Icon icon="ep:search" />查询</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" />重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="handleCreate" v-hasPermi="['rental:store:create']">
          <Icon icon="ep:plus" />新增门店
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="storeList" stripe>
      <el-table-column label="门店名称" prop="name" min-width="160" />
      <el-table-column label="加盟商" prop="franchiseeName" width="140" />
      <el-table-column label="详细地址" prop="address" min-width="200" show-overflow-tooltip />
      <el-table-column label="联系电话" prop="contactPhone" width="140" />
      <el-table-column label="营业时间" prop="businessHours" width="140" />
      <el-table-column label="状态" width="90" align="center">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '营业' : '停业' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="170" />
      <el-table-column label="操作" fixed="right" width="150" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleUpdate(row)" v-hasPermi="['rental:store:update']">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)" v-hasPermi="['rental:store:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog v-model="formVisible" :title="formTitle" width="600px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="门店名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入门店名称" />
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="formData.address" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="formData.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="营业时间" prop="businessHours">
          <el-input v-model="formData.businessHours" placeholder="例：09:00-18:00" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">营业</el-radio>
            <el-radio :label="0">停业</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确认</el-button>
      </template>
    </el-dialog>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getRentalStorePage,
  createRentalStore,
  updateRentalStore,
  deleteRentalStore,
  type RentalStoreVO,
  type RentalStorePageReqVO
} from '@/api/rental/store'

defineOptions({ name: 'RentalStore' })

const loading = ref(false)
const total = ref(0)
const storeList = ref<RentalStoreVO[]>([])
const formVisible = ref(false)
const formTitle = ref('新增门店')
const formRef = ref()
const queryParams = reactive<RentalStorePageReqVO>({ pageNo: 1, pageSize: 10 })
const formData = reactive<Partial<RentalStoreVO>>({ status: 1 })

const formRules = {
  name: [{ required: true, message: '门店名称不能为空', trigger: 'blur' }],
  address: [{ required: true, message: '详细地址不能为空', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '联系电话不能为空', trigger: 'blur' }]
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalStorePage(queryParams)
    storeList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryParams.name = undefined; queryParams.status = undefined; handleQuery() }

const handleCreate = () => {
  Object.assign(formData, { id: undefined, name: '', address: '', contactPhone: '', businessHours: '09:00-18:00', status: 1 })
  formTitle.value = '新增门店'
  formVisible.value = true
}

const handleUpdate = (row: RentalStoreVO) => {
  Object.assign(formData, { ...row })
  formTitle.value = '编辑门店'
  formVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (formData.id) {
    await updateRentalStore(formData)
    ElMessage.success('更新成功')
  } else {
    await createRentalStore(formData)
    ElMessage.success('新增成功')
  }
  formVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该门店吗？', '提示', { type: 'warning' })
  await deleteRentalStore(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
