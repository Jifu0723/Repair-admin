import request from '@/utils/request'

// 查询报修单列表
export function listTb(query) {
  return request({
    url: '/repair/tb/list',
    method: 'get',
    params: query
  })
}

// 月酬金表数据查询
export function MonthWorkList(query) {
  return request({
    url: '/repair/tb/monthworklist',
    method: 'get',
    params: query
  })
}

// 获取用户信息（姓名、手机号码、所在二级学院等）
export function UserInformation(query) {
  return request({
    url: '/repair/tb/userInformation',
    method: 'get',
    params: query
  })
}

// 查询报修单列表
export function reptbList(query) {
  return request({
    url: '/repair/tb/reptbList',
    method: 'get',
    params: query
  })
}

// 查询报修单详细
export function getTb(repairId) {
  return request({
    url: '/repair/tb/' + repairId,
    method: 'get'
  })
}

// 新增报修单
export function addTb(data) {
  return request({
    url: '/repair/tb',
    method: 'post',
    data: data
  })
}

// 修改报修单
export function updateTb(data) {
  return request({
    url: '/repair/tb',
    method: 'put',
    data: data
  })
}

// 删除报修单
export function delTb(repairId) {
  return request({
    url: '/repair/tb/' + repairId,
    method: 'delete'
  })
}

// 导出报修单
export function exportTb(query) {
  return request({
    url: '/repair/tb/export',
    method: 'get',
    params: query
  })
}
