import request from '@/utils/request'

// 查询配件信息列表
export function listParts(query) {
  return request({
    url: '/repair/parts/list',
    method: 'get',
    params: query
  })
}

// 查询配件信息详细
export function getParts(partsId) {
  return request({
    url: '/repair/parts/' + partsId,
    method: 'get'
  })
}

// 新增配件信息
export function addParts(data) {
  return request({
    url: '/repair/parts',
    method: 'post',
    data: data
  })
}

// 修改配件信息
export function updateParts(data) {
  return request({
    url: '/repair/parts',
    method: 'put',
    data: data
  })
}

// 删除配件信息
export function delParts(partsId) {
  return request({
    url: '/repair/parts/' + partsId,
    method: 'delete'
  })
}

// 导出配件信息
export function exportParts(query) {
  return request({
    url: '/repair/parts/export',
    method: 'get',
    params: query
  })
}
