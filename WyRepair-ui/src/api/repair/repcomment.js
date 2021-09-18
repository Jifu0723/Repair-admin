import request from '@/utils/request'

// 查询报修单评论列表
export function listRepcomment(query) {
  return request({
    url: '/repair/repcomment/list',
    method: 'get',
    params: query
  })
}

// 查询报修单评论详细
export function getRepcomment(repcommentId) {
  return request({
    url: '/repair/repcomment/' + repcommentId,
    method: 'get'
  })
}

// 新增报修单评论
export function addRepcomment(data) {
  return request({
    url: '/repair/repcomment',
    method: 'post',
    data: data
  })
}

// 修改报修单评论
export function updateRepcomment(data) {
  return request({
    url: '/repair/repcomment',
    method: 'put',
    data: data
  })
}

// 删除报修单评论
export function delRepcomment(repcommentId) {
  return request({
    url: '/repair/repcomment/' + repcommentId,
    method: 'delete'
  })
}

// 导出报修单评论
export function exportRepcomment(query) {
  return request({
    url: '/repair/repcomment/export',
    method: 'get',
    params: query
  })
}
