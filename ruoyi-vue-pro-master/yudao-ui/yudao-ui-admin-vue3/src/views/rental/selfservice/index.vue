<template>
  <ContentWrap>
    <el-form :model="queryParams" :inline="true" label-width="90px">
      <el-form-item label="设备编号" prop="deviceNo">
        <el-input v-model="queryParams.deviceNo" placeholder="请输入设备编号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="设备类型" prop="deviceType">
        <el-select v-model="queryParams.deviceType" placeholder="请选择设备类型" clearable>
          <el-option v-for="(label, val) in DEVICE_TYPE_MAP" :key="val" :label="label" :value="Number(val)" />
        </el-select>
      </el-form-item>
      <el-form-item label="设备状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="(label, val) in DEVICE_STATUS_MAP" :key="val" :label="label" :value="Number(val)" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery"><Icon icon="ep:search" />查询</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" />重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="handleCreate" v-hasPermi="['rental:self-device:create']">
          <Icon icon="ep:plus" />新增设备
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="deviceList" stripe>
      <el-table-column label="设备编号" prop="deviceNo" width="160" />
      <el-table-column label="所属门店" prop="storeName" min-width="140" />
      <el-table-column label="设备类型" width="110" align="center">
        <template #default="{ row }">
          <el-tag type="primary">{{ DEVICE_TYPE_MAP[row.deviceType] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="安装位置" prop="location" min-width="160" show-overflow-tooltip />
      <el-table-column label="设备状态" width="100" align="center">
        <template #default="{ row }">
          <el-tag :type="DEVICE_STATUS_TAG_MAP[row.status]">{{ DEVICE_STATUS_MAP[row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" min-width="140" show-overflow-tooltip />
      <el-table-column label="创建时间" prop="createTime" width="170" />
      <el-table-column label="操作" fixed="right" width="150" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleUpdate(row)" v-hasPermi="['rental:self-device:update']">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)" v-hasPermi="['rental:self-device:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog v-model="formVisible" :title="formTitle" width="520px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="设备编号" prop="deviceNo">
          <el-input v-model="formData.deviceNo" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备类型" prop="deviceType">
          <el-select v-model="formData.deviceType" placeholder="请选择设备类型">
            <el-option v-for="(label, val) in DEVICE_TYPE_MAP" :key="val" :label="label" :value="Number(val)" />
          </el-select>
        </el-form-item>
        <el-form-item label="安装位置" prop="location">
          <el-input v-model="formData.location" placeholder="请输入安装位置描述" />
        </el-form-item>
        <el-form-item label="设备状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择设备状态">
            <el-option v-for="(label, val) in DEVICE_STATUS_MAP" :key="val" :label="label" :value="Number(val)" />
          </el-select>
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
  getRentalSelfDevicePage,
  createRentalSelfDevice,
  updateRentalSelfDevice,
  deleteRentalSelfDevice,
  DEVICE_TYPE_MAP,
  DEVICE_STATUS_MAP,
  DEVICE_STATUS_TAG_MAP,
  type RentalSelfDeviceVO,
  type RentalSelfDevicePageReqVO
} from '@/api/rental/selfservice'

defineOptions({ name: 'RentalSelfService' })

const loading = ref(false)
const total = ref(0)
const deviceList = ref<RentalSelfDeviceVO[]>([])
const formVisible = ref(false)
const formTitle = ref('新增设备')
const formRef = ref()

const queryParams = reactive<RentalSelfDevicePageReqVO>({ pageNo: 1, pageSize: 10 })
const formData = reactive<Partial<RentalSelfDeviceVO>>({ status: 1 })

const formRules = {
  deviceNo: [{ required: true, message: '设备编号不能为空', trigger: 'blur' }],
  deviceType: [{ required: true, message: '设备类型不能为空', trigger: 'change' }]
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalSelfDevicePage(queryParams)
    deviceList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => {
  queryParams.deviceNo = undefined
  queryParams.deviceType = undefined
  queryParams.status = undefined
  handleQuery()
}

const handleCreate = () => {
  Object.assign(formData, { id: undefined, deviceNo: '', deviceType: undefined, location: '', status: 1, remark: '' })
  formTitle.value = '新增设备'
  formVisible.value = true
}

const handleUpdate = (row: RentalSelfDeviceVO) => {
  Object.assign(formData, { ...row })
  formTitle.value = '编辑设备'
  formVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (formData.id) {
    await updateRentalSelfDevice(formData)
    ElMessage.success('更新成功')
  } else {
    await createRentalSelfDevice(formData)
    ElMessage.success('新增成功')
  }
  formVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该设备吗？', '提示', { type: 'warning' })
  await deleteRentalSelfDevice(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
