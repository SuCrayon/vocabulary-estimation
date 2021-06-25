/*
 * @Author: Crayon
 * @Date: 2021-06-24 22:56:36
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-24 23:03:52
 */
import request from '@/utils/request'

export default {
    listEstimationWords() {
        return request({
            url: '/ve/estimation/listEstimationWords',
            method: 'get'
        })
    }
}