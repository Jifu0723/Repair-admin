import request from '@/utils/request'

// 查询期望维修类型列表
export function listType(query) {
  return request({
    url: '/repair/type/list',
    method: 'get',
    params: query
  })
}

// 查询期望维修类型详细
export function getType(repexpectTypeId) {
  return request({
    url: '/repair/type/' + repexpectTypeId,
    method: 'get'
  })
}

// 新增期望维修类型
export function addType(data) {
  return request({
    url: '/repair/type',
    method: 'post',
    data: data
  })
}

// 修改期望维修类型
export function updateType(data) {
  return request({
    url: '/repair/type',
    method: 'put',
    data: data
  })
}

// 删除期望维修类型
export function delType(repexpectTypeId) {
  return request({
    url: '/repair/type/' + repexpectTypeId,
    method: 'delete'
  })
}

// 导出期望维修类型
export function exportType(query) {
  return request({
    url: '/repair/type/export',
    method: 'get',
    params: query
  })
}
