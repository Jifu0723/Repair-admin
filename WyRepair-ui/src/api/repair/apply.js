import request from '@/utils/request'

// 查询报修申请单列表
export function listApply(query) {
  return request({
    url: '/repair/apply/list',
    method: 'get',
    params: query
  })
}

// 查询报修申请单详细
export function getApply(applyId) {
  return request({
    url: '/repair/apply/' + applyId,
    method: 'get'
  })
}

// 新增报修申请单
export function addApply(data) {
  return request({
    url: '/repair/apply',
    method: 'post',
    data: data
  })
}

// 修改报修申请单
export function updateApply(data) {
  return request({
    url: '/repair/apply',
    method: 'put',
    data: data
  })
}

// 删除报修申请单
export function delApply(applyId) {
  return request({
    url: '/repair/apply/' + applyId,
    method: 'delete'
  })
}

// 导出报修申请单
export function exportApply(query) {
  return request({
    url: '/repair/apply/export',
    method: 'get',
    params: query
  })
}
