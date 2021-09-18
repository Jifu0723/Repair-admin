import request from '@/utils/request'

// 查询维修过程描述列表
export function listProcess(query) {
  return request({
    url: '/repair/process/list',
    method: 'get',
    params: query
  })
}

// 查询维修过程描述详细
export function getProcess(processId) {
  return request({
    url: '/repair/process/' + processId,
    method: 'get'
  })
}

// 新增维修过程描述
export function addProcess(data) {
  return request({
    url: '/repair/process',
    method: 'post',
    data: data
  })
}

// 修改维修过程描述
export function updateProcess(data) {
  return request({
    url: '/repair/process',
    method: 'put',
    data: data
  })
}

// 删除维修过程描述
export function delProcess(processId) {
  return request({
    url: '/repair/process/' + processId,
    method: 'delete'
  })
}

// 导出维修过程描述
export function exportProcess(query) {
  return request({
    url: '/repair/process/export',
    method: 'get',
    params: query
  })
}
