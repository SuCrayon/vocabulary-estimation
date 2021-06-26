/*
 * @Author: Crayon
 * @Date: 2021-06-26 10:58:52
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-26 11:19:04
 */
import router from './router'
import { deviceDispatcher } from './utils/guardFunc'

// 设备路由分发
router.beforeEach(deviceDispatcher)