<template>
  <ContentWrap>
    <el-form :model="queryParams" :inline="true" label-width="90px">
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入商品名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="上架" :value="1" />
          <el-option label="下架" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery"><Icon icon="ep:search" />查询</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" />重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="handleCreate" v-hasPermi="['rental:product:create']">
          <Icon icon="ep:plus" />新增商品
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="productList" stripe>
      <el-table-column label="商品名称" prop="name" min-width="180" />
      <el-table-column label="车型" prop="modelName" width="140" />
      <el-table-column label="所属门店" prop="storeName" min-width="140" />
      <el-table-column label="日租金" width="120" align="right">
        <template #default="{ row }">
          ¥{{ (row.dailyPrice / 100).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="押金" width="120" align="right">
        <template #default="{ row }">
          ¥{{ (row.depositAmount / 100).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="最少租期(天)" prop="minRentDays" width="120" align="center" />
      <el-table-column label="最多租期(天)" prop="maxRentDays" width="120" align="center" />
      <el-table-column label="状态" width="90" align="center">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'">{{ row.status === 1 ? '上架' : '下架' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="150" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleUpdate(row)" v-hasPermi="['rental:product:update']">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)" v-hasPermi="['rental:product:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog v-model="formVisible" :title="formTitle" width="620px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="110px">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="日租金（元）" prop="dailyPrice">
          <el-input-number v-model="dailyPriceYuan" :min="0" :precision="2" :step="10" @change="v => formData.dailyPrice = Math.round(v * 100)" />
        </el-form-item>
        <el-form-item label="押金（元）" prop="depositAmount">
          <el-input-number v-model="depositYuan" :min="0" :precision="2" :step="100" @change="v => formData.depositAmount = Math.round(v * 100)" />
        </el-form-item>
        <el-form-item label="最少租期(天)" prop="minRentDays">
          <el-input-number v-model="formData.minRentDays" :min="1" :max="365" />
        </el-form-item>
        <el-form-item label="最多租期(天)" prop="maxRentDays">
          <el-input-number v-model="formData.maxRentDays" :min="1" :max="365" />
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="formData.description" type="textarea" :rows="4" placeholder="请输入商品描述" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">上架</el-radio>
            <el-radio :label="0">下架</el-radio>
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
  getRentalProductPage,
  createRentalProduct,
  updateRentalProduct,
  deleteRentalProduct,
  type RentalProductVO,
  type RentalProductPageReqVO
} from '@/api/rental/product'

defineOptions({ name: 'RentalProduct' })

const loading = ref(false)
const total = ref(0)
const productList = ref<RentalProductVO[]>([])
const formVisible = ref(false)
const formTitle = ref('新增商品')
const formRef = ref()
const dailyPriceYuan = ref(0)
const depositYuan = ref(0)

const queryParams = reactive<RentalProductPageReqVO>({ pageNo: 1, pageSize: 10 })
const formData = reactive<Partial<RentalProductVO>>({ status: 1, minRentDays: 1, maxRentDays: 30 })

const formRules = {
  name: [{ required: true, message: '商品名称不能为空', trigger: 'blur' }]
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalProductPage(queryParams)
    productList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryParams.name = undefined; queryParams.status = undefined; handleQuery() }

const handleCreate = () => {
  Object.assign(formData, { id: undefined, name: '', dailyPrice: 0, depositAmount: 0, minRentDays: 1, maxRentDays: 30, description: '', status: 1 })
  dailyPriceYuan.value = 0
  depositYuan.value = 0
  formTitle.value = '新增商品'
  formVisible.value = true
}

const handleUpdate = (row: RentalProductVO) => {
  Object.assign(formData, { ...row })
  dailyPriceYuan.value = row.dailyPrice / 100
  depositYuan.value = row.depositAmount / 100
  formTitle.value = '编辑商品'
  formVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (formData.id) {
    await updateRentalProduct(formData)
    ElMessage.success('更新成功')
  } else {
    await createRentalProduct(formData)
    ElMessage.success('新增成功')
  }
  formVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该商品吗？', '提示', { type: 'warning' })
  await deleteRentalProduct(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
