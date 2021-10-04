import request from '@/utils/request'

// 维修专员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月报表(条件查询)
export function queryrepairOrderList(query) {
  return request({
    url: '/repair/tb/queryrepairOrderList',
    method: 'get',
    params: query
  })
}


//  维修专员按报修时间、报修类型、维修人员姓名、设备维修后状态、报修人姓名统计报修信息输出日、周、月报表(渲染表格数据)
export function countrepairAdminTimeByrepairType(query) {
  return request({
    url: '/repair/tb/countrepairAdminTimeByrepairType',
    method: 'get',
    params: query
  })
}



