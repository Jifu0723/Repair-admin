import request from '@/utils/request'

// 查询报修单列表
export function listTb(query) {
  return request({
    url: '/repair/tb/list',
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
