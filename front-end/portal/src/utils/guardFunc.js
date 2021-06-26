/*
 * @Author: Crayon
 * @Date: 2021-06-26 10:55:18
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-26 11:19:24
 */
// 路由拦截处理的函数
import { isPC } from '@/utils/validate/validateFunc'
// 设备检测分发路由
export function deviceDispatcher(to, from, next) {
    // to and from are both route objects. must call `next`.
    const flag = isPC()
    const prefix = "M_";
    console.log(to.name);
    console.log("是否PC", flag);
    if (flag && to.name.indexOf(prefix) === 0) {
        // 是PC端却想去移动端页面
        // 去掉移动端前缀导航到PC页面
        console.log("重新导航到PC端")
        next({ name: to.name.split(prefix)[1] });
    } else if (!flag && to.name.indexOf(prefix) < 0) {
        // 是移动端却想去PC页面
        // 加上移动端前缀导肮到移动端页面
        console.log("重新导航到移动端")
        next({ name: prefix + to.name });
    } else {
        // 放行
        console.log("常规放行")
        next()
    }
}