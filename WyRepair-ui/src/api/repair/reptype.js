import request from '@/utils/request'

// 查询维修类型列表
export function listReptype(query) {
  return request({
    url: '/repair/reptype/list',
    method: 'get',
    params: query
  })
}

// 查询维修类型列表
export function listRepType(query) {
  return request({
    url: '/repair/reptype/reptypelist',
    method: 'get',
    params: query
  })
}

// 查询维修类型详细
export function getReptype(repairTypeId) {
  return request({
    url: '/repair/reptype/' + repairTypeId,
    method: 'get'
  })
}

// 新增维修类型
export function addReptype(data) {
  return request({
    url: '/repair/reptype',
    method: 'post',
    data: data
  })
}

// 修改维修类型
export function updateReptype(data) {
  return request({
    url: '/repair/reptype',
    method: 'put',
    data: data
  })
}

// 删除维修类型
export function delReptype(repairTypeId) {
  return request({
    url: '/repair/reptype/' + repairTypeId,
    method: 'delete'
  })
}

// 导出维修类型
export function exportReptype(query) {
  return request({
    url: '/repair/reptype/export',
    method: 'get',
    params: query
  })
}
