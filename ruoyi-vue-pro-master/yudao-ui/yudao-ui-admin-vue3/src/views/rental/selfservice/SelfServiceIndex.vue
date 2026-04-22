<template>
  <ContentWrap>
    <el-tabs v-model="activeTab">

      <!-- Tab1: 设备管理（Qube） -->
      <el-tab-pane label="设备管理" name="device">
        <el-form :model="deviceQuery" :inline="true" label-width="80px">
          <el-form-item label="设备名称">
            <el-input v-model="deviceQuery.deviceName" placeholder="请输入设备名称" clearable />
          </el-form-item>
          <el-form-item label="所属门店">
            <el-select v-model="deviceQuery.storeId" placeholder="请选择" clearable style="width:150px">
              <el-option label="上野店" :value="1" />
              <el-option label="成田机场店" :value="2" />
              <el-option label="羽田机场店" :value="3" />
              <el-option label="新宿店" :value="4" />
              <el-option label="大阪梅田店" :value="5" />
              <el-option label="京都店" :value="6" />
            </el-select>
          </el-form-item>
          <el-form-item label="设备状态">
            <el-select v-model="deviceQuery.deviceStatus" placeholder="请选择" clearable style="width:120px">
              <el-option label="在线" value="online" />
              <el-option label="离线" value="offline" />
              <el-option label="维护中" value="maintenance" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getDeviceList">查询</el-button>
            <el-button @click="getDeviceList">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="openDeviceForm('create')">新增设备</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="deviceLoading" :data="deviceList">
          <el-table-column label="设备编号" prop="deviceNo" width="130" />
          <el-table-column label="设备名称" prop="deviceName" min-width="150" />
          <el-table-column label="所属门店" prop="storeName" min-width="130" />
          <el-table-column label="设备型号" prop="deviceModel" width="120" />
          <el-table-column label="序列号" prop="serialNo" width="150" />
          <el-table-column label="安装位置" prop="locationDesc" min-width="150" />
          <el-table-column label="设备状态" prop="deviceStatus" width="100">
            <template #default="{ row }">
              <el-tag :type="deviceStatusType(row.deviceStatus)">{{ deviceStatusLabel(row.deviceStatus) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="启用状态" prop="status" width="90">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="180">
            <template #default="{ row }">
              <el-button link type="primary" @click="openDeviceForm('update', row.id)">编辑</el-button>
              <el-button link :type="row.status === 1 ? 'warning' : 'success'" @click="toggleDeviceStatus(row)">
                {{ row.status === 1 ? '禁用' : '启用' }}
              </el-button>
              <el-button link type="danger" @click="deleteDevice(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="deviceTotal" v-model:page="deviceQuery.pageNo" v-model:limit="deviceQuery.pageSize" @pagination="getDeviceList" />
      </el-tab-pane>

      <!-- Tab2: 服务区域管理 -->
      <el-tab-pane label="服务区域管理" name="region">
        <el-form :model="regionQuery" :inline="true" label-width="80px">
          <el-form-item label="区域名称">
            <el-input v-model="regionQuery.regionName" placeholder="请输入区域名称" clearable />
          </el-form-item>
          <el-form-item label="所属门店">
            <el-select v-model="regionQuery.storeId" placeholder="请选择" clearable style="width:150px">
              <el-option label="上野店" :value="1" />
              <el-option label="成田机场店" :value="2" />
              <el-option label="羽田机场店" :value="3" />
              <el-option label="新宿店" :value="4" />
              <el-option label="大阪梅田店" :value="5" />
              <el-option label="京都店" :value="6" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getRegionList">查询</el-button>
            <el-button @click="getRegionList">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="openRegionForm('create')">新增区域</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="regionLoading" :data="regionList">
          <el-table-column label="区域编码" prop="regionCode" width="130" />
          <el-table-column label="区域名称" prop="regionName" min-width="150" />
          <el-table-column label="所属门店" prop="storeName" min-width="130" />
          <el-table-column label="区域地址" prop="address" min-width="200" />
          <el-table-column label="服务半径(m)" prop="serviceRadius" width="120" />
          <el-table-column label="状态" prop="status" width="90">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '禁用' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="150">
            <template #default="{ row }">
              <el-button link type="primary" @click="openRegionForm('update', row.id)">编辑</el-button>
              <el-button link type="danger" @click="deleteRegion(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="regionTotal" v-model:page="regionQuery.pageNo" v-model:limit="regionQuery.pageSize" @pagination="getRegionList" />
      </el-tab-pane>

      <!-- Tab3: 自助取还任务 -->
      <el-tab-pane label="自助取还任务" name="task">
        <el-form :model="taskQuery" :inline="true" label-width="80px">
          <el-form-item label="任务编号">
            <el-input v-model="taskQuery.taskNo" placeholder="请输入任务编号" clearable />
          </el-form-item>
          <el-form-item label="订单号">
            <el-input v-model="taskQuery.orderNo" placeholder="请输入订单号" clearable />
          </el-form-item>
          <el-form-item label="任务类型">
            <el-select v-model="taskQuery.taskType" placeholder="请选择" clearable style="width:120px">
              <el-option label="自助取车" value="pickup" />
              <el-option label="自助还车" value="return" />
            </el-select>
          </el-form-item>
          <el-form-item label="任务状态">
            <el-select v-model="taskQuery.taskStatus" placeholder="请选择" clearable style="width:120px">
              <el-option label="待执行" value="pending" />
              <el-option label="执行中" value="in_progress" />
              <el-option label="已完成" value="completed" />
              <el-option label="失败" value="failed" />
              <el-option label="已取消" value="cancelled" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getTaskList">查询</el-button>
            <el-button @click="getTaskList">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="taskLoading" :data="taskList">
          <el-table-column label="任务编号" prop="taskNo" width="150" />
          <el-table-column label="关联订单号" prop="orderNo" width="160" />
          <el-table-column label="设备编号" prop="deviceNo" width="130" />
          <el-table-column label="门店" prop="storeName" min-width="130" />
          <el-table-column label="车牌号" prop="licensePlate" width="110" />
          <el-table-column label="客户姓名" prop="customerName" width="110" />
          <el-table-column label="联系电话" prop="customerPhone" width="130" />
          <el-table-column label="任务类型" prop="taskType" width="100">
            <template #default="{ row }">
              <el-tag :type="row.taskType === 'pickup' ? 'primary' : 'warning'">
                {{ row.taskType === 'pickup' ? '自助取车' : '自助还车' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="任务状态" prop="taskStatus" width="100">
            <template #default="{ row }">
              <el-tag :type="taskStatusType(row.taskStatus)">{{ taskStatusLabel(row.taskStatus) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="计划执行时间" prop="scheduledTime" width="180" />
          <el-table-column label="实际执行时间" prop="actualTime" width="180" />
          <el-table-column label="操作" fixed="right" width="150">
            <template #default="{ row }">
              <el-button link type="success" @click="completeTask(row.id)" v-if="row.taskStatus === 'in_progress'">完成</el-button>
              <el-button link type="danger" @click="cancelTask(row.id)" v-if="['pending', 'in_progress'].includes(row.taskStatus)">取消</el-button>
            </template>
          </el-table-column>
        </el-table>
        <Pagination :total="taskTotal" v-model:page="taskQuery.pageNo" v-model:limit="taskQuery.pageSize" @pagination="getTaskList" />
      </el-tab-pane>

    </el-tabs>
  </ContentWrap>

  <!-- 设备弹窗 -->
  <Dialog v-model="deviceDialogVisible" :title="deviceDialogTitle" width="600px">
    <el-form ref="deviceFormRef" :model="deviceForm" label-width="110px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="设备编号" prop="deviceNo">
            <el-input v-model="deviceForm.deviceNo" placeholder="请输入设备编号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备名称" prop="deviceName">
            <el-input v-model="deviceForm.deviceName" placeholder="请输入设备名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属门店" prop="storeId">
            <el-select v-model="deviceForm.storeId" style="width:100%">
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
          <el-form-item label="设备型号" prop="deviceModel">
            <el-input v-model="deviceForm.deviceModel" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="序列号" prop="serialNo">
            <el-input v-model="deviceForm.serialNo" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="设备状态" prop="deviceStatus">
            <el-select v-model="deviceForm.deviceStatus" style="width:100%">
              <el-option label="在线" value="online" />
              <el-option label="离线" value="offline" />
              <el-option label="维护中" value="maintenance" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="安装位置" prop="locationDesc">
            <el-input v-model="deviceForm.locationDesc" placeholder="请描述设备安装位置" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="经度" prop="longitude">
            <el-input-number v-model="deviceForm.longitude" :precision="6" style="width:100%" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="纬度" prop="latitude">
            <el-input-number v-model="deviceForm.latitude" :precision="6" style="width:100%" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="deviceDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitDeviceForm">确定</el-button>
    </template>
  </Dialog>

  <!-- 服务区域弹窗 -->
  <Dialog v-model="regionDialogVisible" :title="regionDialogTitle" width="560px">
    <el-form ref="regionFormRef" :model="regionForm" label-width="110px">
      <el-form-item label="区域编码" prop="regionCode">
        <el-input v-model="regionForm.regionCode" placeholder="请输入区域编码" />
      </el-form-item>
      <el-form-item label="区域名称" prop="regionName">
        <el-input v-model="regionForm.regionName" placeholder="请输入区域名称" />
      </el-form-item>
      <el-form-item label="所属门店" prop="storeId">
        <el-select v-model="regionForm.storeId" style="width:100%">
          <el-option label="上野店" :value="1" />
          <el-option label="成田机场店" :value="2" />
          <el-option label="羽田机场店" :value="3" />
          <el-option label="新宿店" :value="4" />
          <el-option label="大阪梅田店" :value="5" />
          <el-option label="京都店" :value="6" />
        </el-select>
      </el-form-item>
      <el-form-item label="区域地址" prop="address">
        <el-input v-model="regionForm.address" placeholder="请输入区域地址" />
      </el-form-item>
      <el-form-item label="服务半径(m)" prop="serviceRadius">
        <el-input-number v-model="regionForm.serviceRadius" :min="0" style="width:100%" />
      </el-form-item>
      <el-form-item label="区域描述" prop="regionDesc">
        <el-input v-model="regionForm.regionDesc" type="textarea" :rows="2" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="regionDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitRegionForm">确定</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import request from '@/config/axios'

const activeTab = ref('device')

// ===== 设备管理 =====
const deviceLoading = ref(false)
const deviceList = ref([])
const deviceTotal = ref(0)
const deviceDialogVisible = ref(false)
const deviceDialogTitle = ref('')
const deviceFormRef = ref()
const deviceQuery = reactive({ pageNo: 1, pageSize: 10, deviceName: '', storeId: undefined, deviceStatus: '' })
const deviceForm = reactive({
  id: undefined, deviceNo: '', deviceName: '', storeId: undefined, storeName: '',
  deviceModel: '', serialNo: '', locationDesc: '', longitude: 0, latitude: 0,
  deviceStatus: 'online', status: 1
})

const deviceStatusLabel = (s: string) => ({ online: '在线', offline: '离线', maintenance: '维护中' }[s] || s)
const deviceStatusType = (s: string) => ({ online: 'success', offline: 'danger', maintenance: 'warning' }[s] || 'info')

const getDeviceList = async () => {
  deviceLoading.value = true
  try {
    const res = await request.get({ url: '/rental/self-service/device/list', params: deviceQuery })
    deviceList.value = res.data || []; deviceTotal.value = res.total || 0
  } finally { deviceLoading.value = false }
}

const openDeviceForm = async (type: string, id?: number) => {
  deviceDialogVisible.value = true
  deviceDialogTitle.value = type === 'create' ? '新增自助取还设备' : '编辑自助取还设备'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/self-service/device/get', params: { id } })
    Object.assign(deviceForm, res.data)
  }
}

const submitDeviceForm = async () => {
  if (deviceForm.id) {
    await request.put({ url: '/rental/self-service/device/update', data: deviceForm })
  } else {
    await request.post({ url: '/rental/self-service/device/create', data: deviceForm })
  }
  deviceDialogVisible.value = false; getDeviceList()
}

const toggleDeviceStatus = async (row: any) => {
  const url = row.status === 1 ? '/rental/self-service/device/disable' : '/rental/self-service/device/enable'
  await request.put({ url, params: { id: row.id } }); getDeviceList()
}

const deleteDevice = async (id: number) => {
  await request.delete({ url: '/rental/self-service/device/delete', params: { id } }); getDeviceList()
}

// ===== 服务区域 =====
const regionLoading = ref(false)
const regionList = ref([])
const regionTotal = ref(0)
const regionDialogVisible = ref(false)
const regionDialogTitle = ref('')
const regionFormRef = ref()
const regionQuery = reactive({ pageNo: 1, pageSize: 10, regionName: '', storeId: undefined })
const regionForm = reactive({
  id: undefined, regionCode: '', regionName: '', storeId: undefined, storeName: '',
  address: '', longitude: 0, latitude: 0, serviceRadius: 500, regionDesc: '', status: 1
})

const getRegionList = async () => {
  regionLoading.value = true
  try {
    const res = await request.get({ url: '/rental/self-service/region/list', params: regionQuery })
    regionList.value = res.data || []; regionTotal.value = res.total || 0
  } finally { regionLoading.value = false }
}

const openRegionForm = async (type: string, id?: number) => {
  regionDialogVisible.value = true
  regionDialogTitle.value = type === 'create' ? '新增服务区域' : '编辑服务区域'
  if (type === 'update' && id) {
    const res = await request.get({ url: '/rental/self-service/region/get', params: { id } })
    Object.assign(regionForm, res.data)
  }
}

const submitRegionForm = async () => {
  if (regionForm.id) {
    await request.put({ url: '/rental/self-service/region/update', data: regionForm })
  } else {
    await request.post({ url: '/rental/self-service/region/create', data: regionForm })
  }
  regionDialogVisible.value = false; getRegionList()
}

const deleteRegion = async (id: number) => {
  await request.delete({ url: '/rental/self-service/region/delete', params: { id } }); getRegionList()
}

// ===== 自助取还任务 =====
const taskLoading = ref(false)
const taskList = ref([])
const taskTotal = ref(0)
const taskQuery = reactive({ pageNo: 1, pageSize: 10, taskNo: '', orderNo: '', taskType: '', taskStatus: '' })

const taskStatusLabel = (s: string) => ({
  pending: '待执行', in_progress: '执行中', completed: '已完成', failed: '失败', cancelled: '已取消'
}[s] || s)

const taskStatusType = (s: string) => ({
  pending: 'info', in_progress: 'primary', completed: 'success', failed: 'danger', cancelled: 'warning'
}[s] || 'info')

const getTaskList = async () => {
  taskLoading.value = true
  try {
    const res = await request.get({ url: '/rental/self-service/task/list', params: taskQuery })
    taskList.value = res.data || []; taskTotal.value = res.total || 0
  } finally { taskLoading.value = false }
}

const completeTask = async (id: number) => {
  await request.put({ url: '/rental/self-service/task/complete', params: { id } }); getTaskList()
}

const cancelTask = async (id: number) => {
  await request.put({ url: '/rental/self-service/task/cancel', params: { id } }); getTaskList()
}

getDeviceList()
getRegionList()
getTaskList()
</script>
