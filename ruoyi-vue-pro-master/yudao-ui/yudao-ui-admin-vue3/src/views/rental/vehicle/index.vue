<template>
  <ContentWrap>
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="90px">
      <el-form-item label="车牌号" prop="plateNo">
        <el-input v-model="queryParams.plateNo" placeholder="请输入车牌号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="车辆状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="(label, val) in VEHICLE_STATUS_MAP" :key="val" :label="label" :value="Number(val)" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery"><Icon icon="ep:search" />查询</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" />重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="handleCreate" v-hasPermi="['rental:vehicle:create']">
          <Icon icon="ep:plus" />新增
        </el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="vehicleList" stripe>
      <el-table-column label="车牌号" prop="plateNo" width="120" />
      <el-table-column label="车架号(VIN)" prop="vin" min-width="180" />
      <el-table-column label="车型" prop="modelName" width="140" />
      <el-table-column label="所属门店" prop="storeName" min-width="140" />
      <el-table-column label="颜色" prop="color" width="80" />
      <el-table-column label="里程(km)" prop="mileage" width="100" align="right" />
      <el-table-column label="油量/电量" width="100" align="center">
        <template #default="{ row }">
          <el-progress :percentage="row.fuelLevel" :color="row.fuelLevel > 30 ? '#67c23a' : '#f56c6c'" />
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template #default="{ row }">
          <el-tag :type="VEHICLE_STATUS_TAG_MAP[row.status]">{{ VEHICLE_STATUS_MAP[row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="170" />
      <el-table-column label="操作" fixed="right" width="150" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleUpdate(row)" v-hasPermi="['rental:vehicle:update']">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)" v-hasPermi="['rental:vehicle:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="formVisible" :title="formTitle" width="560px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="车牌号" prop="plateNo">
          <el-input v-model="formData.plateNo" placeholder="请输入车牌号" />
        </el-form-item>
        <el-form-item label="车架号(VIN)" prop="vin">
          <el-input v-model="formData.vin" placeholder="请输入车架号" />
        </el-form-item>
        <el-form-item label="所属门店" prop="storeId">
          <el-input-number v-model="formData.storeId" :min="1" placeholder="门店编号" />
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="formData.color" placeholder="请输入颜色" />
        </el-form-item>
        <el-form-item label="里程数(km)" prop="mileage">
          <el-input-number v-model="formData.mileage" :min="0" />
        </el-form-item>
        <el-form-item label="油量/电量%" prop="fuelLevel">
          <el-slider v-model="formData.fuelLevel" :min="0" :max="100" show-input />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="formData.remark" type="textarea" :rows="3" placeholder="请输入备注" />
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
  getRentalVehiclePage,
  createRentalVehicle,
  updateRentalVehicle,
  deleteRentalVehicle,
  VEHICLE_STATUS_MAP,
  VEHICLE_STATUS_TAG_MAP,
  type RentalVehicleVO,
  type RentalVehiclePageReqVO
} from '@/api/rental/vehicle'

defineOptions({ name: 'RentalVehicle' })

const loading = ref(false)
const total = ref(0)
const vehicleList = ref<RentalVehicleVO[]>([])
const formVisible = ref(false)
const formTitle = ref('新增车辆')
const formRef = ref()

const queryParams = reactive<RentalVehiclePageReqVO>({ pageNo: 1, pageSize: 10 })
const formData = reactive<Partial<RentalVehicleVO>>({ mileage: 0, fuelLevel: 100 })

const formRules = {
  plateNo: [{ required: true, message: '车牌号不能为空', trigger: 'blur' }],
  vin: [{ required: true, message: '车架号不能为空', trigger: 'blur' }],
  storeId: [{ required: true, message: '所属门店不能为空', trigger: 'blur' }]
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalVehiclePage(queryParams)
    vehicleList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryParams.plateNo = undefined; queryParams.status = undefined; handleQuery() }

const handleCreate = () => {
  Object.assign(formData, { id: undefined, plateNo: '', vin: '', storeId: undefined, color: '', mileage: 0, fuelLevel: 100, remark: '' })
  formTitle.value = '新增车辆'
  formVisible.value = true
}

const handleUpdate = (row: RentalVehicleVO) => {
  Object.assign(formData, { ...row })
  formTitle.value = '编辑车辆'
  formVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (formData.id) {
    await updateRentalVehicle(formData)
    ElMessage.success('更新成功')
  } else {
    await createRentalVehicle(formData)
    ElMessage.success('新增成功')
  }
  formVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该车辆吗？', '提示', { type: 'warning' })
  await deleteRentalVehicle(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
