<template>
  <ContentWrap>
    <el-form :model="queryParams" :inline="true" label-width="90px">
      <el-form-item label="门店" prop="storeId">
        <el-input-number v-model="queryParams.storeId" :min="1" placeholder="门店编号" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery"><Icon icon="ep:search" />查询</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" />重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="inventoryList" stripe>
      <el-table-column label="门店" prop="storeName" min-width="140" />
      <el-table-column label="车型" prop="modelName" min-width="140" />
      <el-table-column label="总数量" prop="totalCount" width="100" align="center" />
      <el-table-column label="可用数量" prop="availableCount" width="100" align="center">
        <template #default="{ row }">
          <el-tag type="success">{{ row.availableCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="已预订" prop="reservedCount" width="100" align="center">
        <template #default="{ row }">
          <el-tag type="primary">{{ row.reservedCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="使用中" prop="inUseCount" width="100" align="center">
        <template #default="{ row }">
          <el-tag type="warning">{{ row.inUseCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="维修中" prop="maintenanceCount" width="100" align="center">
        <template #default="{ row }">
          <el-tag type="danger">{{ row.maintenanceCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" width="170" />
      <el-table-column label="操作" fixed="right" width="120" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleAdjust(row)" v-hasPermi="['rental:inventory:update']">调整库存</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 调整库存对话框 -->
    <el-dialog v-model="adjustVisible" title="调整库存" width="440px">
      <el-form :model="adjustForm" :rules="adjustRules" ref="adjustFormRef" label-width="100px">
        <el-form-item label="当前库存">
          <span>总计 {{ currentInventory.totalCount }} 辆（可用 {{ currentInventory.availableCount }} 辆）</span>
        </el-form-item>
        <el-form-item label="调整数量" prop="adjustCount">
          <el-input-number v-model="adjustForm.adjustCount" placeholder="正数增加，负数减少" />
          <span class="ml-2 text-gray-500">（正数增加，负数减少）</span>
        </el-form-item>
        <el-form-item label="调整原因" prop="reason">
          <el-input v-model="adjustForm.reason" type="textarea" :rows="3" placeholder="请输入调整原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="adjustVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAdjust">确认</el-button>
      </template>
    </el-dialog>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import {
  getRentalInventoryPage,
  adjustRentalInventory,
  type RentalInventoryVO,
  type RentalInventoryPageReqVO
} from '@/api/rental/inventory'

defineOptions({ name: 'RentalInventory' })

const loading = ref(false)
const total = ref(0)
const inventoryList = ref<RentalInventoryVO[]>([])
const adjustVisible = ref(false)
const adjustFormRef = ref()
const currentInventory = ref<Partial<RentalInventoryVO>>({})

const queryParams = reactive<RentalInventoryPageReqVO>({ pageNo: 1, pageSize: 10 })
const adjustForm = reactive({ id: 0, adjustCount: 0, reason: '' })

const adjustRules = {
  adjustCount: [{ required: true, message: '调整数量不能为空', trigger: 'blur' }],
  reason: [{ required: true, message: '调整原因不能为空', trigger: 'blur' }]
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalInventoryPage(queryParams)
    inventoryList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryParams.storeId = undefined; queryParams.modelId = undefined; handleQuery() }

const handleAdjust = (row: RentalInventoryVO) => {
  currentInventory.value = row
  adjustForm.id = row.id
  adjustForm.adjustCount = 0
  adjustForm.reason = ''
  adjustVisible.value = true
}

const submitAdjust = async () => {
  await adjustFormRef.value.validate()
  await adjustRentalInventory(adjustForm)
  ElMessage.success('库存调整成功')
  adjustVisible.value = false
  getList()
}

onMounted(() => getList())
</script>
