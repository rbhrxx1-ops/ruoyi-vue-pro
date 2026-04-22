<template>
  <ContentWrap>
    <el-form :model="queryParams" :inline="true" label-width="90px">
      <el-form-item label="规则类型" prop="ruleType">
        <el-select v-model="queryParams.ruleType" placeholder="请选择规则类型" clearable>
          <el-option v-for="(label, val) in PRICE_RULE_TYPE_MAP" :key="val" :label="label" :value="Number(val)" />
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
        <el-button type="primary" plain @click="handleCreate" v-hasPermi="['rental:price:create']">
          <Icon icon="ep:plus" />新增价格规则
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="priceList" stripe>
      <el-table-column label="车型" prop="modelName" width="140" />
      <el-table-column label="门店" prop="storeName" min-width="140" />
      <el-table-column label="规则类型" width="130" align="center">
        <template #default="{ row }">
          <el-tag>{{ PRICE_RULE_TYPE_MAP[row.ruleType] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="价格" width="120" align="right">
        <template #default="{ row }">
          ¥{{ (row.price / 100).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="生效开始日期" prop="startDate" width="130" />
      <el-table-column label="生效结束日期" prop="endDate" width="130" />
      <el-table-column label="状态" width="90" align="center">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '启用' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="150" align="center">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleUpdate(row)" v-hasPermi="['rental:price:update']">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row.id)" v-hasPermi="['rental:price:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <Pagination :total="total" v-model:page="queryParams.pageNo" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog v-model="formVisible" :title="formTitle" width="520px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="110px">
        <el-form-item label="规则类型" prop="ruleType">
          <el-select v-model="formData.ruleType" placeholder="请选择规则类型">
            <el-option v-for="(label, val) in PRICE_RULE_TYPE_MAP" :key="val" :label="label" :value="Number(val)" />
          </el-select>
        </el-form-item>
        <el-form-item label="价格（元）" prop="price">
          <el-input-number v-model="priceYuan" :min="0" :precision="2" :step="1" @change="onPriceChange" />
        </el-form-item>
        <el-form-item label="生效开始日期" prop="startDate">
          <el-date-picker v-model="formData.startDate" type="date" value-format="YYYY-MM-DD" placeholder="请选择开始日期" />
        </el-form-item>
        <el-form-item label="生效结束日期" prop="endDate">
          <el-date-picker v-model="formData.endDate" type="date" value-format="YYYY-MM-DD" placeholder="请选择结束日期" />
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
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getRentalPriceRulePage,
  createRentalPriceRule,
  updateRentalPriceRule,
  deleteRentalPriceRule,
  PRICE_RULE_TYPE_MAP,
  type RentalPriceRuleVO,
  type RentalPriceRulePageReqVO
} from '@/api/rental/price'

defineOptions({ name: 'RentalPrice' })

const loading = ref(false)
const total = ref(0)
const priceList = ref<RentalPriceRuleVO[]>([])
const formVisible = ref(false)
const formTitle = ref('新增价格规则')
const formRef = ref()
const priceYuan = ref(0)

const queryParams = reactive<RentalPriceRulePageReqVO>({ pageNo: 1, pageSize: 10 })
const formData = reactive<Partial<RentalPriceRuleVO>>({ status: 1 })

const formRules = {
  ruleType: [{ required: true, message: '规则类型不能为空', trigger: 'change' }]
}

const onPriceChange = (val: number) => {
  formData.price = Math.round(val * 100)
}

const getList = async () => {
  loading.value = true
  try {
    const data = await getRentalPriceRulePage(queryParams)
    priceList.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => { queryParams.pageNo = 1; getList() }
const resetQuery = () => { queryParams.ruleType = undefined; queryParams.status = undefined; handleQuery() }

const handleCreate = () => {
  Object.assign(formData, { id: undefined, ruleType: undefined, price: 0, startDate: '', endDate: '', status: 1 })
  priceYuan.value = 0
  formTitle.value = '新增价格规则'
  formVisible.value = true
}

const handleUpdate = (row: RentalPriceRuleVO) => {
  Object.assign(formData, { ...row })
  priceYuan.value = row.price / 100
  formTitle.value = '编辑价格规则'
  formVisible.value = true
}

const submitForm = async () => {
  await formRef.value.validate()
  if (formData.id) {
    await updateRentalPriceRule(formData)
    ElMessage.success('更新成功')
  } else {
    await createRentalPriceRule(formData)
    ElMessage.success('新增成功')
  }
  formVisible.value = false
  getList()
}

const handleDelete = async (id: number) => {
  await ElMessageBox.confirm('确认删除该价格规则吗？', '提示', { type: 'warning' })
  await deleteRentalPriceRule(id)
  ElMessage.success('删除成功')
  getList()
}

onMounted(() => getList())
</script>
