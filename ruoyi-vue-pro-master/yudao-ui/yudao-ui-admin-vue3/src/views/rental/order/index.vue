<template>
  <ContentWrap>
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="90px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入订单号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="(label, val) in ORDER_STATUS_MAP" :key="val" :label="label" :value="Number(val)" />
        </el-select>
      </el-form-item>
      <el-form-item label="渠道来源" prop="channelSource">
        <el-input v-model="queryParams.channelSource" placeholder="请输入渠道来源" clearable />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">
          <Icon icon="ep:search" />查询
        </el-button>
        <el-button @click="resetQuery">
          <Icon icon="ep:refresh" />重置
        </el-button>
      </el-form-item>
    </el-form>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="orderList" stripe>
      <el-table-column label="订单号" prop="orderNo" min-width="180" />
      <el-table-column label="取车门店" prop="pickupStoreName" min-width="140" />
      <el-table-column label="还车门店" prop="returnStoreName" min-width="140" />
      <el-table-column label="车牌号" prop="plateNo" width="120" />
      <el-table-column label="订单状态" width="100" align="center">
        <template #default="{ row }">
          <el-tag :type="ORDER_STATUS_TAG_MAP[row.status]">{{ ORDER_STATUS_MAP[row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="订单金额" width="120" align="right">
        <template #default="{ row }">
          ¥{{ (row.totalAmount / 100).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="预计取车" prop="startTime" width="170" />
      <el-table-column label="预计还车" prop="endTime" width="170" />
      <el-table-column label="渠道来源" prop="channelSource" width="120" />
      <el-table-column label="创建时间" prop="createTime" width="170" />
      <el-table-column label="操作" fixed="right" width="160" align="center">
        <template #default="{ row }">
          <el-button
            link
            type="primary"
            @click="handleDetail(row)"
            v-hasPermi="['rental:order:query']"
          >详情</el-button>
          <el-button
            link
            type="success"
            @click="handleUpdateStatus(row)"
            v-hasPermi="['rental:order:update']"
          >更新状态</el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(row.id)"
            v-hasPermi="['rental:order:delete']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 订单详情对话框 -->
    <el-dialog v-model="detailVisible" title="订单详情" width="700px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ currentOrder.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag :type="ORDER_STATUS_TAG_MAP[currentOrder.status]">{{ ORDER_STATUS_MAP[currentOrder.status] }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="取车门店">{{ currentOrder.pickupStoreName }}</el-descriptions-item>
        <el-descriptions-item label="还车门店">{{ currentOrder.returnStoreName }}</el-descriptions-item>
        <el-descriptions-item label="车牌号">{{ currentOrder.plateNo }}</el-descriptions-item>
        <el-descriptions-item label="渠道来源">{{ currentOrder.channelSource }}</el-descriptions-item>
        <el-descriptions-item label="订单总金额">¥{{ currentOrder.totalAmount ? (currentOrder.totalAmount / 100).toFixed(2) : '0.00' }}</el-descriptions-item>
        <el-descriptions-item label="实付金额">¥{{ currentOrder.payAmount ? (currentOrder.payAmount / 100).toFixed(2) : '0.00' }}</el-descriptions-item>
        <el-descriptions-item label="预计取车时间">{{ currentOrder.startTime }}</el-descriptions-item>
        <el-descriptions-item label="预计还车时间">{{ currentOrder.endTime }}</el-descriptions-item>
        <el-descriptions-item label="实际取车时间">{{ currentOrder.actualStartTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="实际还车时间">{{ currentOrder.actualEndTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ currentOrder.remark || '-' }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>

    <!-- 更新状态对话框 -->
    <el-dialog v-model="statusVisible" title="更新订单状态" width="400px">
      <el-form :model="statusForm" label-width="90px">
        <el-form-item label="订单状态">
          <el-select v-model="statusForm.status" placeholder="请选择状态">
            <el-option v-for="(label, val) in ORDER_STATUS_MAP" :key="val" :label="label" :value="Number(val)" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="statusForm.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="statusVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStatusUpdate">确认</el-button>
      </template>
    </el-dialog>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getRentalOrderPage,
  deleteRentalOrder,
  updateRentalOrderStatus,
  ORDER_STATUS_MAP,
  ORDER_STATUS_TAG_MAP,
  type RentalOrderVO,
  type RentalOrderPageReqVO
} from '@/api/rental/order'

defineOptions({ name: 'RentalOrder' })

const loading = ref(false)
const total = ref(0)
const orderList = ref<RentalOrderVO[]>([])
const dateRange = ref<string[]>([])
const detailVisible = ref(false)
const statusVisible = ref(false)
const currentOrder = ref<Partial<RentalOrderVO>>({})
const statusForm = reactive({ id: 0, status: 10, remark: '' })

const queryParams = reactive<RentalOrderPageReqVO>({
  pageNo: 1,
  pageSize: 10
})

const getList = async () => {
  loading.value = true
  try {
    const params = { ...queryParams }
    if (dateRange.value?.length === 2) {
      params.createTimeStart = dateRange.value[0]
      params.createTimeEnd = dateRange.value[1]
    }
    const data = await getRentalOrderPage(params)
    orderList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

const resetQuery = () => {
  queryParams.orderNo = undefined
  queryParams.status = undefined
  queryParams.channelSource = undefined
  dateRange.value = []
  handleQuery()
}

const handleDetail = (row: RentalOrderVO) => {
  currentOrder.value = row
  detailVisible.value = true
}

const handleUpdateStatus = (row: RentalOrderVO) => {
  statusForm.id = row.id
  statusForm.status = row.status
  statusForm.remark = ''
  statusVisible.value = true
}

const submitStatusUpdate = async () => {
  await updateRentalOrderStatus(statusForm)
  ElMessage.success('状态更新成功')
  statusVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该订单吗？', '提示', { type: 'warning' })
  await deleteRentalOrder(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
