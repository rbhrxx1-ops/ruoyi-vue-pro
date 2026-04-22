<template>
  <ContentWrap>
    <el-form :model="queryParams" :inline="true" label-width="90px">
      <el-form-item label="政策名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入政策名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="政策类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择政策类型" clearable>
          <el-option v-for="(label, val) in POLICY_TYPE_MAP" :key="val" :label="label" :value="Number(val)" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="启用" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery"><Icon icon="ep:search" />查询</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" />重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="handleCreate" v-hasPermi="['rental:policy:create']">
          <Icon icon="ep:plus" />新增政策
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="policyList" stripe>
      <el-table-column label="政策名称" prop="name" min-width="180" />
      <el-table-column label="政策类型" width="130" align="center">
        <template #default="{ row }">
          <el-tag>{{ POLICY_TYPE_MAP[row.type] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="90" align="center">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="170" />
      <el-table-column label="操作" fixed="right" width="180" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleView(row)" v-hasPermi="['rental:policy:query']">查看</el-button>
          <el-button link type="warning" @click="handleUpdate(row)" v-hasPermi="['rental:policy:update']">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)" v-hasPermi="['rental:policy:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="formVisible" :title="formTitle" width="680px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="政策名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入政策名称" />
        </el-form-item>
        <el-form-item label="政策类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择政策类型">
            <el-option v-for="(label, val) in POLICY_TYPE_MAP" :key="val" :label="label" :value="Number(val)" />
          </el-select>
        </el-form-item>
        <el-form-item label="政策内容" prop="content">
          <el-input v-model="formData.content" type="textarea" :rows="8" placeholder="请输入政策详细内容" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确认</el-button>
      </template>
    </el-dialog>

    <!-- 查看详情对话框 -->
    <el-dialog v-model="viewVisible" title="政策详情" width="680px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="政策名称">{{ currentPolicy.name }}</el-descriptions-item>
        <el-descriptions-item label="政策类型">
          <el-tag>{{ POLICY_TYPE_MAP[currentPolicy.type!] }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="currentPolicy.status === 1 ? 'success' : 'danger'">
            {{ currentPolicy.status === 1 ? '启用' : '停用' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="政策内容">
          <div style="white-space: pre-wrap; line-height: 1.8;">{{ currentPolicy.content }}</div>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getRentalPolicyPage,
  createRentalPolicy,
  updateRentalPolicy,
  deleteRentalPolicy,
  POLICY_TYPE_MAP,
  type RentalPolicyVO,
  type RentalPolicyPageReqVO
} from '@/api/rental/policy'

defineOptions({ name: 'RentalPolicy' })

const loading = ref(false)
const total = ref(0)
const policyList = ref<RentalPolicyVO[]>([])
const formVisible = ref(false)
const viewVisible = ref(false)
const formTitle = ref('新增政策')
const formRef = ref()
const currentPolicy = ref<Partial<RentalPolicyVO>>({})

const queryParams = reactive<RentalPolicyPageReqVO>({ pageNo: 1, pageSize: 10 })
const formData = reactive<Partial<RentalPolicyVO>>({ status: 1 })

const formRules = {
  name: [{ required: true, message: '政策名称不能为空', trigger: 'blur' }],
  type: [{ required: true, message: '政策类型不能为空', trigger: 'change' }],
  content: [{ required: true, message: '政策内容不能为空', trigger: 'blur' }]
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalPolicyPage(queryParams)
    policyList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => {
  queryParams.name = undefined
  queryParams.type = undefined
  queryParams.status = undefined
  handleQuery()
}

const handleCreate = () => {
  Object.assign(formData, { id: undefined, name: '', type: undefined, content: '', status: 1 })
  formTitle.value = '新增政策'
  formVisible.value = true
}

const handleView = (row: RentalPolicyVO) => {
  currentPolicy.value = row
  viewVisible.value = true
}

const handleUpdate = (row: RentalPolicyVO) => {
  Object.assign(formData, { ...row })
  formTitle.value = '编辑政策'
  formVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (formData.id) {
    await updateRentalPolicy(formData)
    ElMessage.success('更新成功')
  } else {
    await createRentalPolicy(formData)
    ElMessage.success('新增成功')
  }
  formVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该政策吗？', '提示', { type: 'warning' })
  await deleteRentalPolicy(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
