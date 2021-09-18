import request from '@/utils/request'

// 查询转单记录列表
export function listReptransfer(query) {
  return request({
    url: '/repair/reptransfer/list',
    method: 'get',
    params: query
  })
}

// 查询转单记录详细
export function getReptransfer(reptransferId) {
  return request({
    url: '/repair/reptransfer/' + reptransferId,
    method: 'get'
  })
}

// 新增转单记录
export function addReptransfer(data) {
  return request({
    url: '/repair/reptransfer',
    method: 'post',
    data: data
  })
}

// 修改转单记录
export function updateReptransfer(data) {
  return request({
    url: '/repair/reptransfer',
    method: 'put',
    data: data
  })
}

// 删除转单记录
export function delReptransfer(reptransferId) {
  return request({
    url: '/repair/reptransfer/' + reptransferId,
    method: 'delete'
  })
}

// 导出转单记录
export function exportReptransfer(query) {
  return request({
    url: '/repair/reptransfer/export',
    method: 'get',
    params: query
  })
}
