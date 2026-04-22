<template>
  <ContentWrap>
    <el-form :model="queryParams" :inline="true" label-width="90px">
      <el-form-item label="加盟商名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入加盟商名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="联系人" prop="contactName">
        <el-input v-model="queryParams.contactName" placeholder="请输入联系人姓名" clearable />
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
        <el-button type="primary" plain @click="handleCreate" v-hasPermi="['rental:franchisee:create']">
          <Icon icon="ep:plus" />新增加盟商
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="franchiseeList" stripe>
      <el-table-column label="加盟商名称" prop="name" min-width="180" />
      <el-table-column label="联系人" prop="contactName" width="120" />
      <el-table-column label="联系电话" prop="contactPhone" width="140" />
      <el-table-column label="状态" width="90" align="center">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" min-width="160" show-overflow-tooltip />
      <el-table-column label="创建时间" prop="createTime" width="170" />
      <el-table-column label="操作" fixed="right" width="150" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleUpdate(row)" v-hasPermi="['rental:franchisee:update']">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)" v-hasPermi="['rental:franchisee:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog v-model="formVisible" :title="formTitle" width="520px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="加盟商名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入加盟商名称" />
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactName">
          <el-input v-model="formData.contactName" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系人电话" prop="contactPhone">
          <el-input v-model="formData.contactPhone" placeholder="请输入联系人电话" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
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
  getRentalFranchiseePage,
  createRentalFranchisee,
  updateRentalFranchisee,
  deleteRentalFranchisee,
  type RentalFranchiseeVO,
  type RentalFranchiseePageReqVO
} from '@/api/rental/franchisee'

defineOptions({ name: 'RentalFranchisee' })

const loading = ref(false)
const total = ref(0)
const franchiseeList = ref<RentalFranchiseeVO[]>([])
const formVisible = ref(false)
const formTitle = ref('新增加盟商')
const formRef = ref()
const queryParams = reactive<RentalFranchiseePageReqVO>({ pageNo: 1, pageSize: 10 })
const formData = reactive<Partial<RentalFranchiseeVO>>({ status: 1 })

const formRules = {
  name: [{ required: true, message: '加盟商名称不能为空', trigger: 'blur' }],
  contactName: [{ required: true, message: '联系人姓名不能为空', trigger: 'blur' }],
  contactPhone: [{ required: true, message: '联系人电话不能为空', trigger: 'blur' }]
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalFranchiseePage(queryParams)
    franchiseeList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryParams.name = undefined; queryParams.contactName = undefined; queryParams.status = undefined; handleQuery() }

const handleCreate = () => {
  Object.assign(formData, { id: undefined, name: '', contactName: '', contactPhone: '', status: 1, remark: '' })
  formTitle.value = '新增加盟商'
  formVisible.value = true
}

const handleUpdate = (row: RentalFranchiseeVO) => {
  Object.assign(formData, { ...row })
  formTitle.value = '编辑加盟商'
  formVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (formData.id) {
    await updateRentalFranchisee(formData)
    ElMessage.success('更新成功')
  } else {
    await createRentalFranchisee(formData)
    ElMessage.success('新增成功')
  }
  formVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该加盟商吗？', '提示', { type: 'warning' })
  await deleteRentalFranchisee(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
