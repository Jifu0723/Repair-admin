import request from '@/utils/request'

// 查询报修列表
export function repairOrderDataList(query) {
  return request({
    url: '/repair/tb/repairOrderDataList',
    method: 'get',
    params: query
  })
}

// 按时间统计报修信息输出日、周、月报表
export function countrepairOrderByType(query) {
  return request({
    url: '/repair/tb/countrepairOrderByType',
    method: 'get',
    params: query
  })
}



